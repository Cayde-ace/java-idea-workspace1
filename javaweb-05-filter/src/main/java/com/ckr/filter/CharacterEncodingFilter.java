package com.ckr.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author Shadowckr
 * @create 2021-09-12 15:37
 */
public class CharacterEncodingFilter implements Filter {

    // init（初始化）：web服务器启动，过滤器就已经初始化了，随时等待过滤对象出现！
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("CharacterEncodingFilter初始化！");
    }

    // chain：链，连锁
    /*
    1.过滤器中的所有代码，在过滤特定请求的时候都会执行。
    2.必须要让过滤器继续通行，往下转交。filterChain.doFilter(servletRequest, servletResponse);
    */
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("utf-8");
        servletResponse.setCharacterEncoding("utf-8");
        servletResponse.setContentType("text/html;charset=UTF-8");

        System.out.println("CharacterEncodingFilter执行前...");
        // 让请求继续走下去，如果不写，请求（程序）到这里就被拦截停止！
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("CharacterEncodingFilter执行后...");

    }

    // destroy（销毁）：web服务器关闭的时候，过滤器就会销毁。
    public void destroy() {
        System.out.println("CharacterEncodingFilter销毁！");
    }
}
