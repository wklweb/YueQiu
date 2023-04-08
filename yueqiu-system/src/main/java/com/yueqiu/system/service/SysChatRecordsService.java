package com.yueqiu.system.service;

import com.yueqiu.common.domain.entity.SysChatRecords;

import java.util.List;

public interface SysChatRecordsService {
    void insertChatRecords(SysChatRecords sysChatRecords);

    List<SysChatRecords> getAllRecords(Long activityId);
}
