package com.yueqiu.system.service;

import com.yueqiu.common.domain.entity.SysTime;
import com.yueqiu.common.utils.StringUtils;
import com.yueqiu.common.utils.date.DateUtils;
import com.yueqiu.system.mapper.SysTimeMapper;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class SysTimeServiceImpl implements SysTimeService {

    @Autowired
    private SysTimeMapper sysTimeMapper;

    @Override
    public List<SysTime> selectTimeList(SysTime sysTime) {
        return sysTimeMapper.selectTimeList(sysTime);
    }

    /**
     * 检查时间段的正确性
     * @param sysTime
     * @return
     */
    @Override
    public String checkSysTime(SysTime sysTime) {
        String msg = null;
        if (sysTime.getBeginTime() == null || sysTime.getEndTime() == null || sysTime.getAreaId() == null) {
            msg = "相关属性不能为空";
            return msg;
        } else if (StringUtils.isNull(sysTime.getDays())) {
            msg = "日期不能为空";
            return msg;
        } else if (StringUtils.isNotNull(sysTimeMapper.selectTimeOnly(sysTime))) {
            msg = "时间段重复";
            return msg;
        }
        return msg;
    }

    @Override
    public int addAreaTime(SysTime sysTime) {
        int result = sysTimeMapper.insertAreaTime(sysTime);
        return result;
    }

    @Override
    public int removeAreaTime(Long timeId) {
        return sysTimeMapper.deleteTimeById(timeId);
    }
}
