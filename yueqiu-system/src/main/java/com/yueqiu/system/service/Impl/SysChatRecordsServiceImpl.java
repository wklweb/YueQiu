package com.yueqiu.system.service.Impl;

import com.yueqiu.common.domain.entity.SysChatRecords;
import com.yueqiu.system.mapper.SysChatRecordsMapper;
import com.yueqiu.system.service.SysChatRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("chatRecordService")
public class SysChatRecordsServiceImpl implements SysChatRecordsService {
    @Autowired
    private SysChatRecordsMapper sysChatRecordsMapper;
    @Override
    public void insertChatRecords(SysChatRecords sysChatRecords) {
        sysChatRecordsMapper.insertChatRecords(sysChatRecords);
    }

    @Override
    public List<SysChatRecords> getAllRecords(Long activityId) {
       return sysChatRecordsMapper.selectAllRecords(activityId);
    }
}
