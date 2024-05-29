package com.lanf.system.service;

import com.lanf.model.system.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lanf.model.vo.AssginMenuVo;
import com.lanf.model.vo.RouterVo;

import java.util.List;

public interface SysMenuService extends IService<SysMenu> {

    /**
     * 菜单树形数据
     *
     * @return
     */
    List<SysMenu> findNodes();

    public List<SysMenu> findDir();

    List<SysMenu> findSysMenuByRoleId(String roleId);

    void doAssign(AssginMenuVo assginMenuVo);

    /**
     * 获取用户菜单
     *
     * @param userId
     * @return
     */
    List<RouterVo> findUserMenuList(String userId);

    /**
     * 获取用户按钮权限
     * @param userId
     * @return
     */
    List<String> findUserPermsList(String userId);

}
