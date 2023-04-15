package com.yueqiu.system.mapper;

import com.yueqiu.system.domain.SysLogininfor;

import java.util.List;

public interface LogininforMapper {
    void insertLogininfor(SysLogininfor logininfor);

    List<SysLogininfor> selectLogininforList(SysLogininfor sysLogininfor);
}
