package com.lanf.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanf.business.model.TbEmployee;
import com.lanf.business.vo.TbEmployeeQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
/**
* @author generator
* @version 1.0
* @description 员工信息 Mapper层
* @date 2023-11-30 10:44:29
*/
@Repository
@Mapper
public interface TbEmployeeMapper extends BaseMapper<TbEmployee> {
    IPage<TbEmployee> selectPage(Page<TbEmployee> page, @Param("vo") TbEmployeeQueryVo tbEmployeeQueryVo);
    List<TbEmployee> queryList(@Param("vo") TbEmployeeQueryVo tbEmployeeQueryVo);
    TbEmployee getById(@Param("id")  String id);
}