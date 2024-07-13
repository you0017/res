package com.yc.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Resorder implements Serializable {
    private Integer roid;
    private Integer userid;
    private String address;
    private String tel;
    private String ordertime;
    private String deliverytime;
    private String ps;
    private Integer status;
}
