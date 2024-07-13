package com.yc.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resuser implements Serializable {
    private Integer userid;
    private String username;
    private String pwd;
    private String email;
}
