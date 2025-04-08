package com.vincent.pay.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pay {
    private Integer payId;

    private Double totalPrice;

    private Integer UserId;

    private String payMaster;


}