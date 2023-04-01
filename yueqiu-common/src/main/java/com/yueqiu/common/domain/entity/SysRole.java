package com.yueqiu.common.domain.entity;

import com.yueqiu.common.annotation.Excel;
import com.yueqiu.common.domain.BaseEntity;

import java.util.Set;

public class SysRole extends BaseEntity {
    public static final Long serialVersionUID = 1L;

    @Excel(name = "角色序号",cellType = Excel.ColumnType.NUMERIC,prompt = "角色编号")
    public Long roleId;

    @Excel(name = "角色姓名")
    public String roleName;

    @Excel(name = "角色权限")
    public String roleKey;

    @Excel(name = "角色排序", cellType = Excel.ColumnType.NUMERIC)
    public String roleSort;

    @Excel(name = "数据权限",readConverterExp = "1=所有数据权限,2=自定义数据权限,3=本部门数据权限,4=本部门及以下数据权限,5=仅本人数据权限")
    public String dataScope;

    @Excel(name = "是否删除")
    public String delFlag;

    @Excel(name = "账号状态",readConverterExp = "0=正常,1=禁用")
    public String status;


    public boolean isHave = false;
    public Long[] menuIds;
    /**
     * 权限列表
     */
    public Set<String> permissions;

    public SysRole(){

    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleKey() {
        return roleKey;
    }

    public void setRoleKey(String roleKey) {
        this.roleKey = roleKey;
    }

    public String getRoleSort() {
        return roleSort;
    }

    public void setRoleSort(String roleSort) {
        this.roleSort = roleSort;
    }

    public String getDataScope() {
        return dataScope;
    }

    public void setDataScope(String dataScope) {
        this.dataScope = dataScope;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isHave() {
        return isHave;
    }

    public void setHave(boolean have) {
        isHave = have;
    }

    public Long[] getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(Long[] menuIds) {
        this.menuIds = menuIds;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }
}
