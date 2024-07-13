package com.yc.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resorderitem implements Serializable {
    private Integer roiid;
    private Integer roid;
    private Integer fid;
    private Double dealprice;
    private Integer num;
}
