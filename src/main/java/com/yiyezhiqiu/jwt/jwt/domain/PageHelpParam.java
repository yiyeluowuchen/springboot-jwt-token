package com.yiyezhiqiu.jwt.jwt.domain;

import lombok.Data;

/**
 * 分页实体类
 */

@Data
public class PageHelpParam {

    /**
     * 当前页
     */
    private String currentPageParam ;

    /**
     * 当前页条数
     */
    private String pageSizeParam;

    /**
     * 模糊查询参数fuzzy
     */
    private String fuzzy;

    /**
     * 模糊查询参数 nextFuzzy
     */

    private String nextFuzzy;
}
