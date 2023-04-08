package com.yueqiu.common.filter;

import com.alibaba.fastjson2.filter.SimplePropertyPreFilter;

/**
 * jsonString的属性过滤器
 */
public class PropertyPreExcludeFilter extends SimplePropertyPreFilter {
    public PropertyPreExcludeFilter()
    {
    }

    public PropertyPreExcludeFilter addExcludes(String[] addAll) {
        //将字符串数组的每一个字符串放入（过滤set集合）
        for(int i = 0;i<addAll.length;i++){
            this.getExcludes().add(addAll[i]);
        }
        return this;
    }
}
