package com.yc.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resfood implements Serializable {
    private Integer fid;
    private String fname;
    private Double normprice;
    private Double realprice;
    private String detail;
    private String fphoto;

    //点赞数，redis提供
    private Long praise;
}
