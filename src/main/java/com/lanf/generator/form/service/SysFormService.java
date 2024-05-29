package com.lanf.generator.form.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lanf.generator.form.model.SysForm;
import com.lanf.generator.form.vo.SysFormQueryVo;

import java.util.List;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 表单 Service接口
 * @date 2023-10-06 22:40:26
 */
public interface SysFormService extends IService<SysForm> {
    IPage<SysForm> selectPage(Page<SysForm> pageParam, SysFormQueryVo queryVo);

    List<SysForm> queryList(SysFormQueryVo queryVo);

    public boolean save(SysForm sysForm);

    public boolean updateById(SysForm sysForm);

    public SysForm getById(String id);

    public List<SysForm> getByIds(List<String> ids);

    public void createTableByForm(SysForm sysForm);
}
