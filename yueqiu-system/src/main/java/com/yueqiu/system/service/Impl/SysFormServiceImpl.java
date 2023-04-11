package com.yueqiu.system.service.Impl;

import com.yueqiu.common.constant.ApplyFormTypeConstants;
import com.yueqiu.common.constant.Constants;
import com.yueqiu.common.constant.UserConstants;
import com.yueqiu.common.domain.entity.SysForm;
import com.yueqiu.system.mapper.SysFormMapper;
import com.yueqiu.system.service.SysFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysFormServiceImpl implements SysFormService {
    @Autowired
    private SysFormMapper sysFormMapper;
    /**
     * 添加商户申请表
     * @param sysForm
     * @return
     */
    @Override
    public int addShopApplyForm(SysForm sysForm,String applicationType) {
            //查询一个用户是否已提交过同类型申请
            List<SysForm> form = sysFormMapper.selectFormList(sysForm,applicationType);
            if(form.size()>0){
                return -1;
            }
        return sysFormMapper.insertNewForm(sysForm);
    }

    /**
     * 根据条件查询出入驻申请
     * @param sysForm
     * @return
     */
    @Override
    public List<SysForm> selectShopApplyList(SysForm sysForm,String applicationType) {
        return sysFormMapper.selectFormList(sysForm,applicationType);
    }


    @Override
    public int updateForm(SysForm sysForm) {
        return sysFormMapper.updateForm(sysForm);
    }

    /**
     * 检查有无重复申请
     * @param username
     * @param target
     * @return
     */
    @Override
    public boolean checkResubmit(String username, String target) {
        SysForm sysForm = new SysForm();
        sysForm.setApplicant(username);
        sysForm.setReviewer(target);
        sysForm.setApplicationType(ApplyFormTypeConstants.APPOINT_WAR);
        return sysFormMapper.selectFormList(sysForm,ApplyFormTypeConstants.APPOINT_WAR).size()>0?true:false;
    }
}
