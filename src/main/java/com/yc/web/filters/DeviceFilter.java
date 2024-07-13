package com.yc.web.filters;

import com.yc.bean.Resuser;
import com.yc.dao.RedisHelper;
import com.yc.utils.YcConstants;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import redis.clients.jedis.Jedis;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(value = "/resuser.action")
public class DeviceFilter extends CommonFilter{
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        filterChain.doFilter(servletRequest,servletResponse);

        //只针对login
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String op = req.getParameter("op");
        if (op.equals("login")){
            //登录操作
            //登录成功，session一定存了对象
            HttpSession session = req.getSession();
            if (session.getAttribute(YcConstants.LOGINUSER) != null){
                Resuser resuser = (Resuser) session.getAttribute(YcConstants.LOGINUSER);
                String uid = resuser.getUserid()+"";
                //取出request中记录的设备信息
                //redis-> 键的设计 + 值得类型
                //用户id_device   List<设备名>
                Browser browser = UserAgent.parseUserAgentString(req.getHeader("User-Agent")).getBrowser();
                String name = browser.getName();//设备名
                Jedis jedis = RedisHelper.getRedisInstance();
                jedis.sadd(name+YcConstants.REDIS_DEVICE_USERS,uid);    //一种浏览器，有哪些用户用过
                jedis.lpush(uid+YcConstants.REDIS_USER_DEVICE,name);    //一个用户，用过哪些类型的浏览器
                jedis.close();
            }
        }
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        super.init(filterConfig);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
