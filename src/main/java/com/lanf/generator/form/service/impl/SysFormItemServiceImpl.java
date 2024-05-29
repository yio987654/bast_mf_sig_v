package com.lanf.generator.form.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanf.generator.form.model.SysFormItem;
import com.lanf.generator.form.vo.SysFormItemQueryVo;
import com.lanf.generator.form.mapper.SysFormItemMapper;
import com.lanf.generator.form.service.SysFormItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.util.List;
import org.springframework.beans.BeanUtils;
import com.lanf.common.result.ResultCodeEnum;
import com.lanf.system.exception.LanfException;
/**
* @author tanlingfei
* @version 1.0
* @description 表单选项 Service实现类
* @date 2023-10-08 10:50:46
*/
@Transactional
@Service
public class SysFormItemServiceImpl extends ServiceImpl
<SysFormItemMapper, SysFormItem> implements SysFormItemService {
    @Autowired
    private SysFormItemMapper sysFormItemMapper;

    @Override
    public IPage<SysFormItem> selectPage(Page<SysFormItem> pageParam, SysFormItemQueryVo sysFormItemQueryVo) {
        //QueryWrapper<SysFormItem> queryWrapper = new QueryWrapper<>();
        //return sysFormItemMapper.selectPage(pageParam,queryWrapper);
        return sysFormItemMapper.selectPage(pageParam,sysFormItemQueryVo);
    }

    @Override
    public List<SysFormItem> queryList(SysFormItemQueryVo sysFormItemQueryVo){
     List<SysFormItem> result = sysFormItemMapper.queryList(sysFormItemQueryVo);
     return result;
    }


    @Override
    public boolean save(SysFormItem sysFormItem){
        int result = this.sysFormItemMapper.insert(sysFormItem);
        return result>0;
    }
    @Override
    public boolean updateById(SysFormItem sysFormItem){
        int row = this.sysFormItemMapper.updateById(sysFormItem);
        if(row <= 0){
            throw new LanfException(ResultCodeEnum.UPDATE_ERROR);
         }
        return row>0;
    }
    @Override
    public SysFormItem getById(String id){
         SysFormItem sysFormItem = sysFormItemMapper.selectById(id);
        return sysFormItem;
    }
   @Override
   public List<SysFormItem> getByIds(List<String> ids) {
      List<SysFormItem> list = this.sysFormItemMapper.selectBatchIds(ids);
      return list;
   }
    /**
    @Override
    public void updateStatus(String id, Integer status) {
        SysFormItem sysFormItem = sysFormItemMapper.selectById(id);
        sysFormItem.setStatus(status);
        sysFormItemMapper.updateById(sysFormItem);
    }**/
}
