package com.ckr.filter;

import com.ckr.util.Constant;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Shadowckr
 * @create 2021-09-13 12:11
 */
public class LimitFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // ServletRequest  HttpServletRequest
        // public interface HttpServletRequest extends ServletRequest
        // ServletRequest -> HttpServletRequest
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 进入主页的时候要判断用户是否已经登录，若用户没有登录则过滤器进行过滤，重定向到error.jsp页面。
        if(request.getSession().getAttribute(Constant.USER_SESSION) == null){
            response.sendRedirect("/error.jsp");
        }

        filterChain.doFilter(request, response);
    }

    public void destroy() {

    }
}
