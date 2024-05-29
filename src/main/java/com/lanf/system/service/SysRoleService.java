package com.lanf.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lanf.model.system.SysRole;
import com.lanf.model.vo.AssginRoleVo;
import com.lanf.model.vo.SysRoleQueryVo;

import java.util.Map;

public interface SysRoleService extends IService<SysRole> {
    IPage<SysRole> selectPage(Page<SysRole> pageParam, SysRoleQueryVo roleQueryVo);

    public Map<String, Object> getRolesByUserId(String userId);

    void doAssign(AssginRoleVo assginRoleVo);
}
