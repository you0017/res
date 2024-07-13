package com.yc.web.listener;

import com.yc.dao.RedisHelper;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.*;

@WebListener
//应用启动时，记录当前系统的信息
public class SystemInfoListener implements ServletContextListener {
    Timer timer;    //全局变量，方便销毁
    @Override//容器初始化，在应用程序  在服务中加载时调用
    public void contextInitialized(ServletContextEvent sce) {
        //记录系统信息
        Properties properties = System.getProperties();

        System.out.println("应用程序启动时间："+new Date());
        System.out.println(properties.get("os.name"));
        System.out.println(properties.get("os.version"));
        System.out.println("空闲内存大小："+Runtime.getRuntime().freeMemory());

        //启动一个Timer定时任务（设置为后台线程，主线程tomcat销毁，定时任务就销毁
        timer = new Timer(true);//true设置为后台线程， 默认精密线程，不销毁

        //设置第一次执行是明天的00:00:00
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE,1);
        c.set(Calendar.HOUR_OF_DAY,0);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0);

        timer.schedule(new MyTimerTask(),c.getTime(),24*60*60*1000);//明天0点开始，间隔1天再次执行

    }

    @Override//销毁方法，在服务器关闭时调用
    public void contextDestroyed(ServletContextEvent sce) {
        if (timer!=null){
            timer.cancel();
        }
    }
}

class MyTimerTask extends TimerTask{

    @Override
    public void run() {
        Calendar c = Calendar.getInstance();
        int weekday = c.get(Calendar.DAY_OF_WEEK);
        Jedis jedis = RedisHelper.getRedisInstance();
        jedis.del(weekday+"");  //每次启动就会删这个键值对
        jedis.close();
    }
}