package com.yueqiu.system.service;

import com.yueqiu.common.domain.entity.SysArea;
import com.yueqiu.common.domain.entity.SysDictData;

import java.util.List;
import java.util.Set;

public interface SysDictDataService {
    Set<String> selectAreaValues(String dictType);

    List<SysDictData> selectDictDataList(String dictType);

    SysDictData selectDictDataByKeyAndValue(String key, String value);
}
