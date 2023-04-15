package com.yueqiu.system.service.Impl;

import com.yueqiu.common.domain.entity.SysArea;
import com.yueqiu.common.domain.entity.SysDictData;
import com.yueqiu.system.mapper.SysDictDataMapper;
import com.yueqiu.system.service.SysDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class SysDictDataServiceImpl implements SysDictDataService {
    @Autowired
    private SysDictDataMapper sysDictDataMapper;

    @Override
    public Set<String> selectAreaValues(String dictType) {
        return sysDictDataMapper.selectDictDateByDictType(dictType);
    }

    @Override
    public List<SysDictData> selectDictDataList(String dictType) {
        return sysDictDataMapper.selectDictDateList(dictType);
    }

    @Override
    public SysDictData selectDictDataByKeyAndValue(String key, String value) {
        SysDictData sysDictData = new SysDictData(key,value);
        return sysDictDataMapper.selectDictDataOnly(sysDictData);
    }


}
