package com.yc.web.servlets;

import com.yc.bean.PageBean;
import com.yc.bean.Resfood;
import com.yc.bean.Resuser;
import com.yc.dao.DbHelper;
import com.yc.dao.RedisHelper;
import com.yc.utils.YcConstants;
import com.yc.web.ResfoodBiz;
import com.yc.web.servlets.model.JsonModel;
import redis.clients.jedis.Jedis;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/resfood.action")
public class FoodServlet extends BaseServlet{
    private DbHelper db = new DbHelper();

    //点赞
    protected void clickPraise(HttpServletRequest req,HttpServletResponse resp) throws Exception{
        JsonModel jm = new JsonModel();
        Resfood resfood = new Resfood();
        Jedis jedis = RedisHelper.getRedisInstance();
        try {
            //取fid
            String fid = req.getParameter("fid");
            HttpSession session = req.getSession();
            //权限判断这一功能写到rightFilter中去
            Resuser resuser = (Resuser) session.getAttribute(YcConstants.LOGINUSER);
            int userid = resuser.getUserid();

            //菜品号_praise Set<用户编号>
            if (jedis.sismember(fid+YcConstants.REDIS_FOOD_PRAISE,userid+"")){
                //此用户已经点赞过这道菜，再点就是取消
                jedis.srem(fid+YcConstants.REDIS_FOOD_PRAISE,userid+"");
                //用户编号_praise Set<菜品号> 此处也要删除
                jedis.srem(userid+YcConstants.REDIS_PRAISE,fid+"");
            }else{
                //没有点赞过
                jedis.sadd(fid+YcConstants.REDIS_FOOD_PRAISE,userid+"");
                jedis.sadd(userid+YcConstants.REDIS_PRAISE,fid+"");
            }
            //取出点赞数
            long praise = jedis.scard(fid+YcConstants.REDIS_FOOD_PRAISE);
            resfood.setPraise(praise);
            jm.setCode(1);
            jm.setObj(resfood);
        }catch (Exception e){
            e.printStackTrace();
            jm.setCode(0);
            jm.setError(e.getMessage());
        }
        jedis.close();
        writerJson(jm,resp);
    }
    protected void getHistory(HttpServletRequest req,HttpServletResponse resp) throws Exception{
        JsonModel jm = new JsonModel();
        HttpSession session = req.getSession();
        Resuser resuser = (Resuser) session.getAttribute(YcConstants.LOGINUSER);
        if (resuser==null){
            jm.setCode(0);
            jm.setError("未登录");
            writerJson(jm,resp);
            return;
        }

        Integer userid = resuser.getUserid();
        //Jedis jedis = new Jedis("localhost",6379);
        Jedis jedis = RedisHelper.getRedisInstance();
        List<String> zrange = jedis.zrevrange(userid + "_visited", 0, 100);
        StringBuffer sqlBuffer = new StringBuffer("select * from resfood where fid in (");
        for (String s : zrange) {
            sqlBuffer.append("?,");
        }
        String sql = sqlBuffer.toString();
        sql = sql.substring(0,sql.length()-1);
        sql += ")";
        System.out.println(sql);
        List<Resfood> select = db.select(Resfood.class, sql, zrange.toArray());
        /*List<Resfood> list = new ArrayList<>();
        for (String s : zrange) {
            List<Resfood> select = db.select(Resfood.class, "select * from resfood where fid=?", s);
            list.add(select.get(0));
        }*/
        jm.setCode(1);
        jm.setObj(select);
        jedis.close();
        writerJson(jm,resp);
    }

    protected void traceBrowser(HttpServletRequest req,HttpServletResponse resp) throws Exception{
        JsonModel jm = new JsonModel();
        String fid = req.getParameter("fid");
        //是否登录
        HttpSession session = req.getSession();
        Resuser resuser = (Resuser) session.getAttribute(YcConstants.LOGINUSER);
        if (resuser == null){
            jm.setCode(-1);
            jm.setError("未登录不记录");
            writerJson(jm,resp);
            return;
        }

        Integer id = resuser.getUserid();
        //Jedis jedis = new Jedis("localhost",6379);
        Jedis jedis = RedisHelper.getRedisInstance();

        //历史记录只保留五个  默认升序
        if (jedis.zcard(id+"_visited")>=5){
            jedis.zremrangeByRank(id+"_visited",0,0);
        }

        Date date = new Date();
        jedis.zadd(id+"_visited",date.getTime(),fid);
        jedis.expire(id+"_visited",60*60*24*30);
        jedis.close();

        jm.setCode(1);
        writerJson(jm,resp);
    }

    protected void findAllFoodsByPage(HttpServletRequest req,HttpServletResponse resp) throws Exception {
        JsonModel jm = new JsonModel();
        PageBean pb = null;
        try {
            //传入的四个值存入PageBean
            int pageno = Integer.parseInt(req.getParameter("pageno"));
            int pagesize = Integer.parseInt(req.getParameter("pagesize"));
            String sortby = req.getParameter("sortby");
            String sort = req.getParameter("sort");
            pb = new PageBean();
            pb.setPageno(pageno);
            pb.setPagesize(pagesize);
            pb.setSortby(sortby);
            pb.setSort(sort);

            //分页查找
            ResfoodBiz resfoodBiz = new ResfoodBiz();
            pb = resfoodBiz.findByPage(pb);

            HttpSession session = req.getSession();
            session.setAttribute("foodList",pb.getDataset());//菜品列表存入session，后面方便调用

            jm.setCode(1);
            jm.setObj(pb);
        }catch (Exception e){
            e.printStackTrace();
            jm.setCode(0);
            jm.setError("分页查询失败");
        }

        writerJson(jm,resp);
    }
    protected void findAllFoods(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String sql = "select * from resfood";
        List<Resfood> select = db.select(Resfood.class, sql);
        if (select==null || select.size()<=0){
            select = new ArrayList<>();
        }
        //tomcat提供的缓存 => redis做缓存
        HttpSession session = req.getSession();
        session.setAttribute("foodList",select);

        JsonModel jm = new JsonModel();
        jm.setObj(select);
        jm.setCode(1);
        writerJson(jm,resp);
    }
}
