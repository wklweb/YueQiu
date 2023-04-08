package com.yueqiu.system.mapper;

import com.yueqiu.common.domain.entity.SysForm;

import java.util.List;

public interface SysFormMapper {
    List<SysForm> selectFormList(SysForm sysForm);

    int insertNewForm(SysForm sysForm);

    int updateForm(SysForm sysForm);
}
