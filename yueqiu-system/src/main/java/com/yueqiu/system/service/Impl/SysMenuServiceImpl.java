package com.yueqiu.system.service.Impl;

import com.yueqiu.common.domain.entity.SysMenu;
import com.yueqiu.common.domain.entity.SysRole;
import com.yueqiu.common.domain.entity.SysUser;
import com.yueqiu.common.domain.model.Mtree;
import com.yueqiu.common.utils.StringUtils;
import com.yueqiu.system.mapper.SysMenuMapper;
import com.yueqiu.system.mapper.SysRoleMapper;
import com.yueqiu.system.service.SysMenuService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;

    /**
     * 根据角色id查询出权限集合
     *
     * @param roleId
     * @return
     */
    @Override
    public Set<String> selectPermissionsByRoleId(Long roleId) {
        List<String> perms = sysMenuMapper.selectPermissionsByRoleId(roleId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotEmpty(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 根据用户id查询出权限set集合
     *
     * @param userId
     * @return
     */
    @Override
    public Set<String> selectMenuPermsByUserId(Long userId) {
        List<String> perms = sysMenuMapper.selectMenuPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotEmpty(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 根据用户id查询出menu列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<SysMenu> selectMenuList(Long userId) {
        return selectMenuList(new SysMenu(), userId);
    }

    /**
     * 构建前端的菜单列表序列
     *
     * @param sysMenuList
     * @return
     */
    @Override
    public List<Mtree> buildMtree(List<SysMenu> sysMenuList) {
        List<SysMenu> sysMenus = buildMenuTree(sysMenuList);
        List<Mtree> s = sysMenus.stream().map(Mtree::new).collect(Collectors.toList());
        return s;
    }

    /**
     * 构建连接关系的SysMenu集合
     * @param roleId
     * @return
     */
    @Override
    public List<Long> buildMenuTree(Long roleId) {
        SysRole sysRole = sysRoleMapper.selectRoleByRoleId(roleId);
        List<Long> sysMenus = sysMenuMapper.selectMenuListByRoleId(roleId,sysRole.isMenuCheckStrictly());
        return sysMenus;
    }

    /**
     * 根据菜单id查询菜单数据
     * @param menuId
     * @return
     */
    @Override
    public SysMenu selectMenuById(Long menuId) {
        return sysMenuMapper.selectMenuById(menuId);
    }


    /**
     * 将每个SysMenu节点连接起来
     *
     * @param sysMenuList
     * @return
     */
    private List<SysMenu> buildMenuTree(List<SysMenu> sysMenuList) {
        List<Long> menuIds = null;
        List<SysMenu> sysMenusHead = new ArrayList<>();
        if (sysMenuList.size() > 0) {
            menuIds = sysMenuList.stream().map(SysMenu::getMenuId).collect(Collectors.toList());
            for (Iterator iterator = sysMenuList.iterator(); iterator.hasNext(); ) {
                SysMenu sysMenu = (SysMenu) iterator.next();
                if (!menuIds.contains(sysMenu.getParentId())) {
                    //找到顶级节点,遍历出所有子节点
                    ergodicNode(sysMenu, sysMenuList);
                    sysMenusHead.add(sysMenu);
                }
            }
            if(sysMenusHead.isEmpty()){
                sysMenusHead = sysMenuList;
            }
        }
        return sysMenusHead;
    }

    private void ergodicNode(SysMenu sysMenu, List<SysMenu> SysMenus) {
        List<SysMenu> newMenus = getChildList(sysMenu,SysMenus);
        sysMenu.setChildren(newMenus);
        for (SysMenu sonMenu : newMenus) {
            if (hasSonNode(sonMenu, SysMenus)) {
                ergodicNode(sonMenu,SysMenus);
            }
        }
    }

    /**
     * 判断有无子节点
     *
     * @param sonMenu
     * @param sysMenus
     * @return
     */
    private boolean hasSonNode(SysMenu sonMenu, List<SysMenu> sysMenus) {
        return getChildList(sonMenu, sysMenus).size() > 0;
    }

    private List<SysMenu> getChildList(SysMenu sonMenu, List<SysMenu> sysMenus) {
        List<SysMenu> sysMenuList = new ArrayList<>();
        Iterator iterator = sysMenus.iterator();
        while (iterator.hasNext()) {
            SysMenu sysMenu = (SysMenu) iterator.next();
            if(sonMenu.getMenuId().longValue()==sysMenu.getParentId().longValue()){
                sysMenuList.add(sysMenu);
            }
        }
        return sysMenuList;

    }


    /**
     * 根据用户id查出所关联的菜单集
     *
     * @param sysMenu
     * @param userId
     * @return
     */
    private List<SysMenu> selectMenuList(SysMenu sysMenu, Long userId) {
        List<SysMenu> sysMenuList = null;
        if (SysUser.isAdmin(userId)) {
            sysMenuList = sysMenuMapper.selectMenuList(sysMenu);
        } else {
            sysMenu.getParams().put("userId", userId);
            sysMenuList = sysMenuMapper.selectMenuListByUserId(sysMenu);
        }
        return sysMenuList;
    }

}
