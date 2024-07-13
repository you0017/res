package com.yc.web.servlets.model;

import com.yc.bean.Resfood;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private Resfood resfood;
    private Double smallCount;
    private Integer num;

    public Double getSmallCount() {
        if (this.resfood != null){
            smallCount = this.num * this.resfood.getRealprice();
        }
        return smallCount;
    }

}
