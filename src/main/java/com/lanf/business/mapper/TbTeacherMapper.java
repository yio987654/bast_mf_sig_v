package com.lanf.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanf.business.model.TbTeacher;
import com.lanf.business.vo.TbTeacherQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 讲师 Mapper层
 * @date 2023-11-24 00:27:34
 */
@Repository
@Mapper
public interface TbTeacherMapper extends BaseMapper<TbTeacher> {
    IPage<TbTeacher> selectPage(Page<TbTeacher> page, @Param("vo") TbTeacherQueryVo tbTeacherQueryVo);

    List<TbTeacher> queryList(@Param("vo") TbTeacherQueryVo tbTeacherQueryVo);
}