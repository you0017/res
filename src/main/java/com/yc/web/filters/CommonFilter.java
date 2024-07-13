package com.yc.web.filters;

import com.google.gson.Gson;
import com.yc.utils.WriterJson;
import com.yc.web.servlets.model.JsonModel;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class CommonFilter implements Filter {
    public static void writerJson(JsonModel jm, HttpServletResponse resp)throws Exception{
        resp.setContentType("text/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        Gson gson = new Gson();
        writer.println(gson.toJson(jm));
        writer.flush();
        writer.close();
    }
}
