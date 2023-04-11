package com.yueqiu.system.mapper;

import com.yueqiu.common.domain.entity.SysArea;
import com.yueqiu.common.domain.entity.SysShop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysAreaMapper {
    List<SysArea> selectAreaList(@Param("area") SysArea area,@Param("shopId")Long shopId);

    int insertArea(SysArea sysArea);

    SysArea selectAreaListByArea(SysArea sysArea);
}
