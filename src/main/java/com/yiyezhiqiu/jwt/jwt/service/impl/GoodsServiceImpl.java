package com.yiyezhiqiu.jwt.jwt.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yiyezhiqiu.jwt.jwt.dao.GoodsDao;
import com.yiyezhiqiu.jwt.jwt.domain.Goods;
import com.yiyezhiqiu.jwt.jwt.domain.PageHelpParam;
import com.yiyezhiqiu.jwt.jwt.service.IGoodsService;
import com.yiyezhiqiu.jwt.jwt.service.IUsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实现分页
 */
@Service
@Slf4j
public class GoodsServiceImpl implements IGoodsService {


    @Autowired
    IGoodsService goodsService;
    @Autowired
    GoodsDao goodsDao;
    @Override
    public Map findPageHelp(PageHelpParam pageHelpParam) {
        String currentPageParam = pageHelpParam.getCurrentPageParam();
        String pageSizeParam   =  pageHelpParam.getPageSizeParam();
        log.info("currentPageParam:"+currentPageParam);
        log.info("pageSizeParam:"+pageSizeParam);
        int currenPage = 1;
        int pageSize = 10;
        if(StringUtils.isEmpty(currentPageParam)){
         currenPage = 1;
        }else{
            currenPage = Integer.parseInt(currentPageParam);
        }
        if(StringUtils.isEmpty(pageSizeParam)){
            pageSize = 10;
        }else{
            pageSize = Integer.parseInt(pageSizeParam);

        }

        //分页条件查询参数
        PageHelper.startPage(currenPage,pageSize);//页数和每页大小

        /**
         * 获得要分页的集合，
         *    1.这里获得集合，如果你要给参数到数据库，比如模糊查询，就可以在PageHelpParam中添加相关字段（在实现了分页后，做了下模糊查询）
         *   2.这里是返回某个表的实体类，当然你也可以用一个vo类，也就是不同表字段返回在一个vo类中使用
         */
        String fuzzy = pageHelpParam.getFuzzy();
        String nextFuzzy = pageHelpParam.getNextFuzzy();
        Map<String,Object>  mapParam = new HashMap<>();
        if(null != fuzzy && fuzzy  != ""){
            mapParam.put("fuzzy",fuzzy);
        }

        if(null != nextFuzzy && fuzzy != ""){
            mapParam.put("nextFuzzy",nextFuzzy);
        }

        List<Goods> goodsList = goodsDao.findAll(mapParam);


        //这里可以看下PageInfo为啥会和PageHelper有关系，因为startPage会返回page，PageInfo中使用page，这就是联系，可以去看源码
        PageInfo<Goods> pageInfo = new PageInfo<Goods>(goodsList);

        List<Goods> goods = new ArrayList<>();
        if(!CollectionUtils.isEmpty(goodsList)){
            for(Goods good:pageInfo.getList()){
                goods.add(good);
            }
        }

        Map<String,Object>  map = new HashMap<>();
        map.put("total",pageInfo.getTotal());
        map.put("currentPage",pageInfo.getPageNum());
        map.put("pageSize", pageInfo.getPageSize());

        Map<String ,Object> maps = new HashMap<>();
        maps.put("pageInfo",map);
        maps.put("goods",goods);

        return maps;
    }
}
