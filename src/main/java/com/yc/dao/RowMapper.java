package com.yc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper<T> {

    /**
     * 由用户最终决定如何处理ResultSet中的第rowNum行
     * @param rs
     * @param rowNum
     * @return
     */
    T mapRow(ResultSet rs,int rowNum) throws SQLException;
}
