package com.yc.web.filters;

import com.yc.bean.Resuser;
import com.yc.utils.WriterJson;
import com.yc.web.servlets.model.JsonModel;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(value={"/resorder.action","/custOp.action","/resfood.action"})
public class RightFilter extends CommonFilter{
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        //类似于servlet的service方法
        //配置的请求都会经过这个方法
        //FilterChain过滤器链
        System.out.println("doFilter");


        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String requestURI = req.getRequestURI();
        HttpSession session = req.getSession();
        Resuser resuser = (Resuser) session.getAttribute("resuser");
        String op = req.getParameter("op");

        JsonModel jm = new JsonModel();
        if (resuser!=null){
            //登录放行
            chain.doFilter(request,response);
        } else if (op.equals("findAllFoodsByPage")||op.equals("findAllFoods")) {
            //resfood.action有两个方法不需要登录也能过
            chain.doFilter(request,response);
        }else{
            //要过滤的地址，但是未登录
            jm.setCode(0);
            jm.setError("未登录");
            try {
                WriterJson.writerJson(jm,resp);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    public RightFilter() {
        System.out.println("RightFilter权限过滤器的构造方法：调用了几次");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //Filter.super.init(filterConfig);
        System.out.println("init");
    }

    @Override
    public void destroy() {
        //Filter.super.destroy();
        System.out.println("destroy");
    }
}
