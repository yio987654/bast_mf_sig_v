package com.lanf.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lanf.model.system.SysUser;
import com.lanf.model.vo.SysUserQueryVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface SysUserService extends IService<SysUser> {

    IPage<SysUser> selectPage(Page<SysUser> pageParam, SysUserQueryVo adminQueryVo);

    List<SysUser> queryList(SysUserQueryVo queryVo);

    public void updateStatus(String id, Integer status);

    public boolean save(SysUser sysUser);

    void importData(MultipartFile multipartFile);

    public void exportData(HttpServletResponse response);

    public boolean updateById(SysUser sysUser);

    SysUser getByUsername(String username);

    /**
     * 根据用户名获取用户登录信息
     * @param username
     * @return
     */
    Map<String, Object> getUserInfo(String username);
    public SysUser getById(String id);
}
