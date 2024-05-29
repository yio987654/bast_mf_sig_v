package com.lanf.generator.form.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lanf.generator.form.model.SysFormItem;
import com.lanf.generator.form.vo.SysFormItemQueryVo;
import java.util.List;
/**
* @author tanlingfei
* @version 1.0
* @description 表单选项 Service接口
* @date 2023-10-08 10:50:46
*/
public interface SysFormItemService extends IService<SysFormItem> {
    IPage<SysFormItem> selectPage(Page<SysFormItem> pageParam, SysFormItemQueryVo queryVo);
    List<SysFormItem> queryList(SysFormItemQueryVo queryVo);
    public boolean save(SysFormItem sysFormItem);
    public boolean updateById(SysFormItem sysFormItem);
    public SysFormItem getById(String id);
    public List<SysFormItem> getByIds(List<String> ids);
    //void updateStatus(String id, Integer status);
}
