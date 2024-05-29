package com.lanf.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanf.business.model.TbStudentTreeRel;
import com.lanf.business.vo.TbStudentTreeRelQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 学生树形关系表 Mapper层
 * @date 2023-11-23 16:57:09
 */
@Repository
@Mapper
public interface TbStudentTreeRelMapper extends BaseMapper<TbStudentTreeRel> {
    IPage<TbStudentTreeRel> selectPage(Page<TbStudentTreeRel> page, @Param("vo") TbStudentTreeRelQueryVo tbStudentTreeRelQueryVo);

    List<TbStudentTreeRel> queryList(@Param("vo") TbStudentTreeRelQueryVo tbStudentTreeRelQueryVo);
}