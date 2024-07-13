package com.yc.web;

import com.yc.bean.Resorder;
import com.yc.dao.DbHelper;
import com.yc.web.servlets.model.CartItem;

import java.sql.*;
import java.util.Set;

public class OrderBiz {
    private DbHelper dbHelper = new DbHelper();
    public int order(Resorder resorder, Set<CartItem> cartItems) throws Exception {
        int result=0;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        //以事务处理方式添加订单信息
        try{
            connection = dbHelper.getConnection();
            connection.setAutoCommit(false);//关闭隐式提交  防止完成一次就提交一次
            String sql = "insert into resorder(userid,address,tel,ordertime,deliverytime,ps,status) values(?,?,?,now(),?,?,0)";
            //后面那个常量表示自动生成的id返回
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setObject(1,resorder.getUserid());
            preparedStatement.setObject(2,resorder.getAddress());
            preparedStatement.setObject(3,resorder.getTel());
            preparedStatement.setObject(4,resorder.getDeliverytime());
            preparedStatement.setObject(5,resorder.getPs());

            preparedStatement.executeUpdate();

            int roid=0;
            //取自动生成的id
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()){
                roid = generatedKeys.getInt(1);
            }

            //循环所有的订单项，添加到resorderItem表
            if (cartItems == null || cartItems.size()<=0){
                throw new RuntimeException("购物车为空");
            }

            sql = "insert into resorderitem(roid,fid,dealprice,num) values(?,?,?,?)";
            for (CartItem cartItem : cartItems) {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setObject(1,roid);
                preparedStatement.setObject(2,cartItem.getResfood().getFid());
                preparedStatement.setObject(3,cartItem.getResfood().getRealprice());
                preparedStatement.setObject(4,cartItem.getNum());
                preparedStatement.executeUpdate();
            }
            //事务提交
            connection.commit();
        }catch (Exception e){
            if (connection!=null){
                connection.rollback();
            }
            throw e;
        }finally {
            if (connection!=null){
                connection.close();
            }
            if (preparedStatement!=null){
                preparedStatement.close();
            }
        }
        StringBuilder sb = new StringBuilder();


        return result;
    }
}
