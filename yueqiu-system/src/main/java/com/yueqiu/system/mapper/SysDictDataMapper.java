package com.yueqiu.system.mapper;

import com.yueqiu.common.domain.entity.SysDictData;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface SysDictDataMapper {
    Set<String> selectDictDateByDictType(@Param("dictType") String dictType);

    List<SysDictData> selectDictDateList(String dictType);

    SysDictData selectDictDataOnly(SysDictData sysDictData);
}
