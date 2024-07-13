package com.yc.dao;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;

public class DbHelper {

    //获取一个Connection
    public Connection getConnection() throws Exception{
        DbProperties p = DbProperties.getInstance();
        Class.forName(p.getProperty("driverClassName"));
        Connection con = DriverManager.getConnection(p.getProperty("url"), p.getProperty("uname"), p.getProperty("pwd"));
        return con;
    }

    /**
     * 封装更新（insert,update,delete)
     * sql是要执行的更新语句，这语句有n个?占位符，及对应的n个参数
     * Object...代表动态数组，长度不确定，这种参数只能加载一个方法参数列表的最后
     * sql: update emp set ename = ?,mgr=? where empno=?
     */
    public int doUpdate(String sql,Object... params){
        int result=0;
        try (Connection con = getConnection();//获取连接
             PreparedStatement pstmt = con.prepareStatement(sql);//预编译语句对象
            ){
            //问题一：?对应的参数类型，这个类型是什么，则setXxx()??
            //将所有的参数指定为Object  =>setObject()
            //问题二：总共有几个参数???params到底有几个
            //params是动态数组，length
            extracted(pstmt, params);
            result = pstmt.executeUpdate();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 查询
     * @param sql
     * @param params
     */
    public List<Map<String,Object>> select(String sql, Object... params){
        ResultSet result=null;
        List<Map<String,Object>> list = new ArrayList<>();
        try (Connection con = getConnection();//获取连接
             PreparedStatement pstmt = con.prepareStatement(sql);//预编译语句对象
        ){
            //问题一：?对应的参数类型，这个类型是什么，则setXxx()??
            //将所有的参数指定为Object  =>setObject()
            //问题二：总共有几个参数???params到底有几个
            //params是动态数组，length
            extracted(pstmt, params);
            result = pstmt.executeQuery();

            //jdbc中规范  ResultSet中有关于结果集的一切信息
            ResultSetMetaData rsmd = result.getMetaData();  //结果集元数据  =>有多少个列，每个列叫什么名字
            int columnCount = rsmd.getColumnCount();    //列的数量

            //循环结果集的行
            while (result.next()){
                Map<String,Object> map = new HashMap<>();
                //有几个列?
                for (int i = 0; i < columnCount; i++) {
                    //数据类型
                    //System.out.print(result.getObject(i+1) + "\t"); //这个数据不能这样处理
                    map.put(rsmd.getColumnName(i+1),result.getObject(i+1)); //数据存到map
                }
                list.add(map);  //map存到list
                //System.out.println();
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    /** 方法名相同，参数不同，重载
     * 查询返回值是一个List<T> T代表任意的类的对象
     * T类标准javaBean：属性封装(private)，对外提供get/set  setXxx(参数)
     * @param c 代表  T类的反射类的对象(T的基因)
     * @param sql
     * @param params
     * @return
     * @param <T>
     */
    public <T> List<T> select(Class<T> c,String sql,Object... params) throws Exception {
        List<T> result = new ArrayList<>();
        //1.sql,params=>查询得到数据表数据
        List<Map<String, Object>> list = select(sql, params);

        //得到所有方法
        Method[] methods = c.getDeclaredMethods();

        //得到set方法
        List<Method> sets = getSets(methods);

        for (Map<String, Object> map : list) {
            T t = c.newInstance();
            //2.将Map<String,Object>转换成T对象
            Set<Map.Entry<String, Object>> set = map.entrySet();
            Iterator<Map.Entry<String, Object>> iterator = set.iterator();


            while (iterator.hasNext()){
                Map.Entry<String, Object> entry = iterator.next();
                String key = entry.getKey().substring(0,1).toUpperCase()+entry.getKey().substring(1).toLowerCase();
                Object value = entry.getValue();
                //System.out.println(key + "  :  " + value);

                //得到对应的set方法
                Method method = getSet(key,sets);

                //激活对应的set方法
                Class<?> parameterType = method.getParameterTypes()[0];
                String name = parameterType.getTypeName();

                if (name.equals("int") || name.equals("java.lang.Integer")){
                    method.invoke(t,Integer.parseInt(value.toString()));
                } else if (name.equals("short") || name.equals("java.lang.Short")){
                    method.invoke(t,Short.parseShort(value.toString()));
                }else if (name.equals("double") || name.equals("java.lang.Double")){
                    method.invoke(t,Double.parseDouble(value.toString()));
                }else if (name.equals("float") || name.equals("java.lang.Float")){
                    method.invoke(t,Float.parseFloat(value.toString()));
                }else if (name.equals("long") || name.equals("java.lang.Long")){
                    method.invoke(t,Integer.parseInt(value.toString()));
                }else if (name.equals("byte") || name.equals("java.lang.Byte")){
                    method.invoke(t,Byte.parseByte(value.toString()));
                }else if (name.equals("boolean") || name.equals("java.lang.Boolean")){
                    method.invoke(t,Boolean.parseBoolean(value.toString()));
                }else if (name.equals("java.lang.String")){
                    method.invoke(t,value.toString());
                }else {
                    method.invoke(t,value);
                }
            }
            //3.将T对象存到List中
            result.add(t);
        }
        return result;
    }

    /**
     * 基于模板设计模式的查询方法
     * @param rowMapper 对一行结果集的除了，返回一个对应的对象
     * @param sql
     * @param params
     * @return
     * @param <T>
     */
    public <T> List<T> select(RowMapper<T> rowMapper,String sql,Object... params) throws Exception {
        List<T> list = new ArrayList<>();
        //查询步骤的模板
        try (Connection con=getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);){
            this.extracted(pstmt,params);
            ResultSet rs = pstmt.executeQuery();
            int num=0;
            while (rs.next()){
                T t = rowMapper.mapRow(rs,num); //结果集每一行的处理，由RowMapper接口的实现决定
                num++;
                list.add(t);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    private Method getSet(String key, List<Method> sets) {
        for (int i = 0; i < sets.size(); i++) {
            if (sets.get(i).getName().equals("set" + key)){
                return sets.get(i);
            }
        }
        return null;
    }

    private List<Method> getSets(Method[] methods) {
        List<Method> list = new ArrayList<>();
        for (int i = 0; i < methods.length; i++) {
            String name = methods[i].getName();
            if (name.startsWith("set")){
                list.add(methods[i]);
            }
        }
        return list;
    }

    private void extracted(PreparedStatement pstmt, Object... params) throws Exception {
        if (params !=null && params.length>0){
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i+1, params[i]);
            }
        }
    }

    public boolean insert(String sql,Object... params) throws Exception{
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        extracted(preparedStatement,params);
        boolean execute = preparedStatement.execute();
        connection.close();
        preparedStatement.close();
        return execute;
    }
}
