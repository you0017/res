package com.yc.web.servlets;

import com.yc.bean.Resorder;
import com.yc.bean.Resuser;
import com.yc.dao.DbHelper;
import com.yc.utils.YcConstants;
import com.yc.web.OrderBiz;
import com.yc.web.servlets.model.CartItem;
import com.yc.web.servlets.model.JsonModel;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@WebServlet("/custOp.action")
public class CustopServlet extends BaseServlet{
    private DbHelper db = new DbHelper();

    //提交订单
    protected void confirmOrder(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        JsonModel jm = new JsonModel();
        String address = req.getParameter("address");
        String tel = req.getParameter("tel");
        String deliverytime = req.getParameter("deliverytime");
        String ps = req.getParameter("ps");
        HttpSession session = req.getSession();
        Map<Integer, CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");
        Set<CartItem> values = new HashSet<>(cart.values());

        Resorder resorder = new Resorder();
        resorder.setUserid(((Resuser) session.getAttribute(YcConstants.LOGINUSER)).getUserid());
        resorder.setAddress(address);
        resorder.setTel(tel);
        resorder.setDeliverytime(deliverytime);
        resorder.setPs(ps);

        OrderBiz ob = new OrderBiz();
        int result = ob.order(resorder, values);

        //移除购物车
        session.removeAttribute("cart");
        jm.setCode(1);
        writerJson(jm,resp);
    }
}
