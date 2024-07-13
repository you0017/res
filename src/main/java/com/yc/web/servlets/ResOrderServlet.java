package com.yc.web.servlets;

import com.yc.bean.Resfood;
import com.yc.dao.DbHelper;
import com.yc.utils.YcConstants;
import com.yc.web.servlets.model.CartItem;
import com.yc.web.servlets.model.JsonModel;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/resorder.action")
public class ResOrderServlet extends BaseServlet{

    private DbHelper db = new DbHelper();

    protected void getCartInfo(HttpServletRequest req,HttpServletResponse resp) throws Exception {
        JsonModel jm = new JsonModel();
        HttpSession session = req.getSession();
        Map<Integer,CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");

        if (cart==null||cart.size()<=0){
            cart = new HashMap<>();
        }
        jm.setCode(1);
        jm.setObj(cart.values());
        writerJson(jm,resp);
    }

    protected void clearAll(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        HttpSession session = req.getSession();
        session.removeAttribute("cart");
        JsonModel jm = new JsonModel();
        jm.setCode(1);
        writerJson(jm,resp);
    }

    //添加到购物车
    protected void order(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        JsonModel jm = new JsonModel();
        HttpSession session = req.getSession();
        Integer fid = Integer.valueOf(req.getParameter("fid"));
        Integer num = Integer.valueOf(req.getParameter("num"));

        //1.判断是否登录
        if (session.getAttribute(YcConstants.LOGINUSER)==null){
            jm.setCode(-1);
            writerJson(jm,resp);
            return;
        }

        //根据fid查出resfood对象，再放到购物车
        //a.根据fid查询数据库
        String sql = "select * from resfood where fid=?";
        List<Resfood> select = db.select(Resfood.class, sql, fid);
        Resfood resfood = null;
        if (select.size()<=0 && select==null){
            jm.setCode(0);
            jm.setError("没有此商品");
            return;
        }
        resfood = select.get(0);

        //b.根据fid在session中取
        //List<Resfood> foodList = (List<Resfood>) session.getAttribute("foodList");

        //2.添加购物车
        //session一个客户端一个session(存 Map<fid,购物项对象>)
        Map<Integer, CartItem> map = (Map<Integer, CartItem>) session.getAttribute("cart");
        if (map==null){
            map = new HashMap<>();
        }
        //购物项对象包含：Resfood对象 数量  小计
        //逻辑：1.先将session取出map  2，判断这个map中是否有要购物的fid(containsKey)
        CartItem cartItem = null;
        if (map.containsKey(fid)){
            //3.如果有则数量+1
            cartItem = map.get(fid);
            cartItem.setNum(cartItem.getNum()+num);
        }else{
            // 没有要创建一个购物项对象  以fid作为键
            cartItem = new CartItem();
            cartItem.setResfood(resfood);
            cartItem.setNum(1);
        }
        if (cartItem.getNum()<=0){
            map.remove(fid);
        }else{
            //计算小计
            cartItem.getSmallCount();
            map.put(fid,cartItem);
        }



        //4.将map存到session
        session.setAttribute("cart",map);

        jm.setCode(1);
        jm.setObj(map.values());
        writerJson(jm,resp);
    }
}
