package com.yc.utils;

import com.google.gson.Gson;
import com.yc.web.servlets.model.JsonModel;


import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class WriterJson {

    public static void writerJson(JsonModel jm, HttpServletResponse resp)throws Exception{
        resp.setContentType("text/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        Gson gson = new Gson();
        writer.println(gson.toJson(jm));
        writer.flush();
        writer.close();
    }
}
