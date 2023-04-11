package com.yueqiu.system.mapper;

import com.yueqiu.common.domain.entity.SysForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysFormMapper {
    List<SysForm> selectFormList(@Param(value = "sysForm") SysForm sysForm,@Param(value = "type") String applicationType);

    int insertNewForm(SysForm sysForm);

    int updateForm(SysForm sysForm);
}
