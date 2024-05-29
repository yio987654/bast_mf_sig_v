package com.lanf.generator.form.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanf.generator.form.model.SysFormItem;
import com.lanf.generator.form.vo.SysFormItemQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
/**
* @author tanlingfei
* @version 1.0
* @description 表单选项 Mapper层
* @date 2023-10-08 10:50:46
*/
@Repository
@Mapper
public interface SysFormItemMapper extends BaseMapper<SysFormItem> {
    IPage<SysFormItem> selectPage(Page<SysFormItem> page, @Param("vo") SysFormItemQueryVo sysFormItemQueryVo);
    List<SysFormItem> queryList(@Param("vo") SysFormItemQueryVo sysFormItemQueryVo);
}