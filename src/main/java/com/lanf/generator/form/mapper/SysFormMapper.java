package com.lanf.generator.form.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanf.generator.form.model.SysForm;
import com.lanf.generator.form.vo.SysFormQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 表单 Mapper层
 * @date 2023-10-06 22:40:26
 */
@Repository
@Mapper
public interface SysFormMapper extends BaseMapper<SysForm> {
    IPage<SysForm> selectPage(Page<SysForm> page, @Param("vo") SysFormQueryVo sysFormQueryVo);

    List<SysForm> queryList(@Param("vo") SysFormQueryVo sysFormQueryVo);

    public void createTableByForm(String createTableByForm);
}
