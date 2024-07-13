package com.yc.web;

import com.yc.bean.PageBean;
import com.yc.bean.Resfood;
import com.yc.dao.DbHelper;

import java.util.List;
import java.util.Map;

public class ResfoodBiz {
    private DbHelper db = new DbHelper();
    public PageBean findByPage(PageBean pb) throws Exception {
        //分页查询，并返回数据
        List<Resfood> list = findByPage(pb.getPageno(),pb.getPagesize(),pb.getSortby(),pb.getSort());

        pb.setTotal(countAll());//总记录数
        //总页数
        pb.setTotalpages((int)(pb.getTotal()%pb.getPagesize() == 0?pb.getTotal()/pb.getPagesize():pb.getTotal()/pb.getPagesize()+1));
        pb.setDataset(list);//查到的数据存入PageBean

        //上一页
        if (pb.getPageno()<=1){
            pb.setPre(1);
        }else{
            pb.setPre(pb.getPageno()-1);
        }
        //下一页
        if (pb.getPageno()>=pb.getTotalpages()){
            pb.setNext((int) pb.getTotalpages());
        }else{
            pb.setNext(pb.getPageno()+1);
        }
        return pb;
    }
    private long countAll(){
        String sql = "select count(fid) from resfood";
        return (long) db.select(sql).get(0).get("count(fid)");

    }

    private List<Resfood> findByPage(int pageno, int pagesize,String sortby,String sort) throws Exception {
        //注jdbc不支持占位符替换desc和asc，所以这里排序默认了
        /*String sql = "select * from resfood order by ? ? limit ?,?";
        if (sort.toLowerCase().equals("desc")){
            sql = "select * from resfood order by ? desc limit ?,?";
        }else if(sort.toLowerCase().equals("asc")){
            sql = "select * from resfood order by ? asc limit ?,?";
        }*/

        String sql = "select * from resfood order by fid desc limit 0,5";
        if (sort.toLowerCase().equals("desc")){
            sql = "select * from resfood order by "+  sortby+   " desc limit ?,?";
        }else if(sort.toLowerCase().equals("asc")){
            sql = "select * from resfood order by "+sortby+ " asc limit ?,?";
        }

        List<Resfood> select = db.select(Resfood.class, sql,  (pageno-1)*pagesize, pagesize);
        System.out.println(select);
        return select;
    }
}
