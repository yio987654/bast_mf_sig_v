package com.lanf.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanf.business.model.TbStudent;
import com.lanf.business.vo.TbStudentQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 学生 Mapper层
 * @date 2023-11-24 00:02:09
 */
@Repository
@Mapper
public interface TbStudentMapper extends BaseMapper<TbStudent> {
    IPage<TbStudent> selectPage(Page<TbStudent> page, @Param("vo") TbStudentQueryVo tbStudentQueryVo);

    List<TbStudent> queryList(@Param("vo") TbStudentQueryVo tbStudentQueryVo);

    TbStudent getById(@Param("id") String id);
}