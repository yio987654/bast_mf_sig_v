package com.lanf.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanf.business.model.TbStudentTeacher;
import com.lanf.business.vo.TbStudentTeacherQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 学生讲师关系表 Mapper层
 * @date 2023-11-23 16:56:48
 */
@Repository
@Mapper
public interface TbStudentTeacherMapper extends BaseMapper<TbStudentTeacher> {
    IPage<TbStudentTeacher> selectPage(Page<TbStudentTeacher> page, @Param("vo") TbStudentTeacherQueryVo tbStudentTeacherQueryVo);

    List<TbStudentTeacher> queryList(@Param("vo") TbStudentTeacherQueryVo tbStudentTeacherQueryVo);
}