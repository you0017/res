package com.yc.web.servlets;

import com.yc.bean.Resuser;
import com.yc.dao.DbHelper;
import com.yc.utils.EncryptUtils;
import com.yc.utils.YcConstants;
import com.yc.web.servlets.model.JsonModel;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@WebServlet("/resuser.action")
public class UserServlet extends BaseServlet{
    private DbHelper db = new DbHelper();

    protected void checkLogin(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        HttpSession session = req.getSession();
        Resuser resuser = (Resuser) session.getAttribute(YcConstants.LOGINUSER);
        JsonModel jm = new JsonModel();
        if (resuser == null){
            jm.setCode(0);
            jm.setObj(null);
            writerJson(jm,resp);
        }else {
            jm.setCode(1);
            jm.setObj(resuser);
            writerJson(jm,resp);
        }
    }

    protected void logout(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        req.getSession().removeAttribute(YcConstants.LOGINUSER);
        JsonModel jm = new JsonModel();
        jm.setCode(1);
        writerJson(jm,resp);
    }

    protected void login(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String username = req.getParameter("username");
        String pwd = EncryptUtils.encryptToMD5(EncryptUtils.encryptToMD5(req.getParameter("pwd")));
        String valcode = req.getParameter("valcode");
        HttpSession session = req.getSession();

        JsonModel jm = new JsonModel();
        boolean validate = false;
        if (validate && !session.getAttribute("code").equals(valcode)){
            jm.setCode(0);
            jm.setError("验证码错误");
            writerJson(jm,resp);
            return;
        }
        String sql = "select * from resuser where username = ?";
        List<Resuser> select = db.select(Resuser.class, sql ,username);
        if (select == null || select.size()<=0){
            jm.setCode(0);
            jm.setError("用户不存在");
            writerJson(jm,resp);
            return;
        }
        if (!select.get(0).getPwd().equals(pwd)){
            jm.setCode(0);
            jm.setError("密码错误");
            writerJson(jm,resp);
            return;
        }
        session.setAttribute(YcConstants.LOGINUSER,select.get(0));
        select.get(0).setPwd("密码你猜");
        jm.setCode(1);
        jm.setObj(select.get(0));
        writerJson(jm,resp);
    }

}
