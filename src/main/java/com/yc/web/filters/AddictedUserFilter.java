package com.yc.web.filters;

import com.yc.bean.Resuser;
import com.yc.dao.RedisHelper;
import com.yc.utils.YcConstants;
import com.yc.web.listener.SystemInfoListener;
import redis.clients.jedis.Jedis;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Calendar;

public class AddictedUserFilter extends CommonFilter{
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest,servletResponse);
        //只针对login操作的响应做处理
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String op = req.getParameter("op");
        if (op.equals("login")){
            //登录操作一定有resuser
            HttpSession session = req.getSession();
            Resuser resuser = (Resuser) session.getAttribute(YcConstants.LOGINUSER);
            if (resuser != null){
                //登录成功
                Integer userid = resuser.getUserid();
                Jedis jedis = RedisHelper.getRedisInstance();
                //取出服务器时间，周几，作为建存
                int weekday= Calendar.getInstance().get(Calendar.DAY_OF_WEEK);//周日是1，周六是7
                jedis.setbit(String.valueOf(weekday),userid,true);//将二进制的这个位设置为1
                jedis.close();
                //周一用户1登录  2    1
                //周二用户2登录  3   10
                //周二用户1登录  4   01...
                //谁登录谁1
                //第七天按照与运算，七天全1则满签

                //TODO 这里的问题，1.如果到了下一周 这个weekday中的数据是如何清空？需要一个定时器定点24：00清空倒数第七天的记录，来执行
                //  定时器：这里是一个后台java端的定时器，可以用java.util中的Timer+TimerTash来完成，这个类实际就是一个线程
                //2.如何计算连续七天登录的用户  bitop and  x1  x2 x3
                //3.什么地方要用到上面的计算结果(系统管理员的后台)  如何取出计算结果bitop的返回值为long型
                //将long转为二进制，按位取数据
                //SystemInfoListener中处理

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
