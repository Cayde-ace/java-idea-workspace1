package com.ckr.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

/**
 * @author Shadowckr
 * @create 2021-09-21 19:05
 */
public class FileUploadServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 判断用户上传的文件是普通表单还是带文件的表单，如果是普通表单则直接返回
        if (!ServletFileUpload.isMultipartContent(req)) {
            return;
        }
        // 创建上传文件的保存路径（目录），为了安全建议在WEB-INF目录下，用户无法访问
        String uploadPath = this.getServletContext().getRealPath("WEB-INF/upload");
        System.out.println(uploadPath);
        File uploadFile = new File(uploadPath);
        if (!uploadFile.exists()) {
            uploadFile.mkdir();
        }
        //临时目录，如果上传的文件超过了预期的大小，我们将它存放到一个临时目录中，过几天自动删除，或者提醒用户转存为永久
        String tempPath = this.getServletContext().getRealPath("WEB-INF/temp");
        File tempFile = new File(tempPath);
        if (!tempFile.exists()) {
            tempFile.mkdir();
        }
        // 处理上传的文件一般需要通过流来获取，我们可以通过req.getInputStream()原生态文件上传流获取，但是会十分麻烦。
        // 我们都建议使用Apache的文件上传组件来实现，commons-fileupload，它需要依赖于commons-io的组件。

        try {

            // 1.创建DiskFileItemFactory对象，处理文件上传路径或限制文件大小
            DiskFileItemFactory factory = getDiskFileItemFactory(tempFile);
            // 2.获取ServletFileUpload对象
            ServletFileUpload upload = getServletFileUpload(factory);
            // 3.处理上传文件
            String msg = uploadParseRequest(upload, req, uploadPath);

            if (msg.equals("文件上传成功！")) {
                req.setAttribute("message", msg);
                req.getRequestDispatcher("info.jsp").forward(req, resp);
            } else {
                msg = "文件上传失败，请重新上传！";
                req.setAttribute("message", msg);
                req.getRequestDispatcher("info.jsp").forward(req, resp);
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
    }

    public static DiskFileItemFactory getDiskFileItemFactory(File file) {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 通过这个工厂设置一个缓冲区，当上传的文件大小大于缓冲区大小的时候，将它放到临时文件目录中
        factory.setSizeThreshold(1024 * 1024);// 设置缓冲区大小：1M
        factory.setRepository(file);// 设置临时文件保存的目录
        return factory;
    }

    public static ServletFileUpload getServletFileUpload(DiskFileItemFactory factory) {
        ServletFileUpload upload = new ServletFileUpload(factory);
        // 监听文件上传进度
        // pBytesRead:已经读取到的文件大小
        // pContentLength:文件总大小
        upload.setProgressListener(new ProgressListener() {
            public void update(long pBytesRead, long pContentLength, int pItems) {
                System.out.println("文件总大小：" + pContentLength + "已上传文件大小" + pBytesRead);
            }
        });
        // 处理乱码问题
        upload.setHeaderEncoding("UTF-8");
        // 设置单个文件大小的最大值
        upload.setFileSizeMax(1024 * 1024 * 10);
        // 设置总共能够上传文件的大小
        upload.setSizeMax(1024 * 1024 * 20);

        return upload;
    }

    public static String uploadParseRequest(ServletFileUpload upload, HttpServletRequest req, String uploadPath) throws FileUploadException, IOException {
        String message = "";
        // 将表单中的每个输入项都封装成一个FileItem对象（表单输入项）
        List<FileItem> fileItems = upload.parseRequest(req);
        System.out.println("表单中的输入项的个数：" + fileItems.size());// 3
        System.out.println(fileItems.toString());
        /*
        [name=null, StoreLocation=null, size=12 bytes, isFormField=true, FieldName=username,
        name=dark soul 3.jpg, StoreLocation=null, size=819734 bytes, isFormField=false, FieldName=file1,
        name=ycqs.jpg, StoreLocation=null, size=24086 bytes, isFormField=false, FieldName=file1]
         */
        for (FileItem fileItem : fileItems) {
            // 判断是普通表单域还是文件上传表单域，如果是普通表单域，返回true否则返回false
            if (fileItem.isFormField()) {
                // 获取前端表单字段元素的name属性值，如name="username"中的"username"
                String name = fileItem.getFieldName();
                // 将FileItem对象中保存的主体内容作为一个字符串返回，并使用参数指定的字符集编码将主体内容转换成字符串。
                String value = fileItem.getString("UTF-8");
                System.out.println(name + ":" + value);
            } else {

                // ============处理文件==============

                // String getName();获取文件上传字段中的文件名，注意：
                // 如果用户使用Windows系统上传文件，浏览器将传递该文件的完整路径。
                // 如果用户使用Linux或者Unix系统上传文件，浏览器将只传递该文件的名称部分。
                String uploadFileName = fileItem.getName();
                System.out.println("上传的文件名：" + uploadFileName);
                if (uploadFileName.trim().equals("") || uploadFileName == null) {
                    continue;
                }
                // 获得上传文件的文件名
                String fileName = uploadFileName.substring(uploadFileName.lastIndexOf("/") + 1);
                // 获得上传文件的后缀名
                String fileExtName = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1);
                // 如果文件的后缀名fileExtName不是我们所需要的，就直接return不处理，告诉用户文件类型不对。
                System.out.println("文件信息[文件名：" + fileName + " 文件类型：" + fileExtName + "]");
                // 可以使用UUID(唯一识别的通用码)，保证文件名唯一
                // UUID.randomUUID()，随机生一个唯一识别的通用码
                String uuidPath = UUID.randomUUID().toString();

                // ================处理文件完毕==============

                // 文件真实存在的路径：realPath
                String realPath = uploadPath + "/" + uuidPath;
                // 给每个文件创建一个对应的文件夹
                File realPathFile = new File(realPath);
                if (!realPathFile.exists()) {
                    realPathFile.mkdir();
                }

                // ==============存放地址完毕==============

                // 获取文件上传的流
                InputStream inputStream = fileItem.getInputStream();
                // 创建一个文件输出流
                // realPath为真实的文件夹
                FileOutputStream fileOutputStream = new FileOutputStream(realPath + "/" + fileName);
                System.out.println("path:" + realPath + "/" + fileName);
                // 创建一个缓冲区
                byte[] buffer = new byte[1024 * 1024];
                int len = 0;
                while ((len = inputStream.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, len);
                }
                // 关闭流
                fileOutputStream.close();
                inputStream.close();

                message = "文件上传成功！";
                // 上传成功，清除临时文件
                fileItem.delete();

                // =============文件传输完成=============
            }
        }
        return message;
    }
}
