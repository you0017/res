package com.yc.web.servlets;

import com.google.gson.Gson;
import com.yc.dao.DbHelper;
import com.yc.web.servlets.model.JsonModel;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {
    protected DbHelper db = new DbHelper();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{

        String op = req.getParameter("op");
        JsonModel jm = new JsonModel();
        try {
            if (op==null||op.equals("")){
                jm.setCode(0);
                jm.setError("op不能为空");
                writerJson(jm,resp);
                return;
            }

            Method[] methods = this.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().equals(op)){
                    method.invoke(this,req,resp);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            jm.setCode(0);
            jm.setError(e.getMessage());
            try {
                writerJson(jm,resp);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doPost(req, resp);
        doGet(req, resp);
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        //这个在过滤器中设置
        //req.setCharacterEncoding("utf-8");
        //res.setCharacterEncoding("utf-8");

        //res.setContentType("text/html;");
        super.service(req, res);
    }

    protected void writerJson(JsonModel jm,HttpServletResponse resp)throws Exception{
        resp.setContentType("text/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        Gson gson = new Gson();
        writer.println(gson.toJson(jm));
        writer.flush();
        writer.close();
    }
}
