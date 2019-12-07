package com.yiyezhiqiu.jwt.jwt.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Goods {

    private int id;

    private  String goodsType;

    private BigDecimal goodsPrice;

    private Double     goodsWeight;

    private String goodsShape;
}
