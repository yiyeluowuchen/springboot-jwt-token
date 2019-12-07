package com.yiyezhiqiu.jwt.jwt.controller;

import com.github.pagehelper.PageHelper;
import com.yiyezhiqiu.jwt.jwt.annotation.OtherPermit;
import com.yiyezhiqiu.jwt.jwt.domain.PageHelpParam;
import com.yiyezhiqiu.jwt.jwt.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    IGoodsService goodsService;

    @RequestMapping(value = "/findGoods",method = RequestMethod.GET)
    public Map   findGoodsPageHelper(PageHelpParam pageHelpParam){

        Map<String,Object> map = goodsService.findPageHelp(pageHelpParam);

        return map;
    }

}
