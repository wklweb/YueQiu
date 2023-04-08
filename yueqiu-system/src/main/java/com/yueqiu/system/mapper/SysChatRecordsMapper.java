package com.yueqiu.system.mapper;

import com.yueqiu.common.domain.entity.SysChatRecords;

import java.util.List;

public interface SysChatRecordsMapper {
    void insertChatRecords(SysChatRecords sysChatRecords);

    List<SysChatRecords> selectAllRecords(Long activityId);
}
