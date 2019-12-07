package com.yiyezhiqiu.jwt.jwt.dao;

import com.yiyezhiqiu.jwt.jwt.domain.Goods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


/**
 *dao
 */

@Mapper
public interface GoodsDao {

    public List<Goods> findAll(Map<String,Object>params);

}
