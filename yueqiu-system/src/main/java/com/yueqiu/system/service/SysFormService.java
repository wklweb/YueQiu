package com.yueqiu.system.service;

import com.yueqiu.common.domain.entity.SysForm;

import java.util.List;

public interface SysFormService {
    int addShopApplyForm(SysForm sysForm,String applicationType);

    List<SysForm> selectShopApplyList(SysForm sysForm);

    boolean checkFormTypeUnique(SysForm sysForm);

    int updateForm(SysForm sysForm);

    boolean checkResubmit(String username, String target);
}
