package com.yueqiu.common.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yueqiu.common.domain.entity.SysMenu;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单树节点
 */
public class Mtree implements Serializable {

    public static final Long serialVersionUID = 1L;
    /**
     * 父id节点
     */
    private Long menuId;
    /**
     * 子节点
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Mtree> sonList;
    private String label;
    public Mtree(SysMenu sysMenu){
        this.menuId = sysMenu.getMenuId();
        this.label = sysMenu.getMenuName();
        this.sonList = sysMenu.getChildren().stream().map(Mtree::new).collect(Collectors.toList());
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long getFid() {
        return menuId;
    }

    public void setFid(Long fid) {
        this.menuId = fid;
    }

    public List<Mtree> getSonList() {
        return sonList;
    }

    public void setSonList(List<Mtree> sonList) {
        this.sonList = sonList;
    }
}
