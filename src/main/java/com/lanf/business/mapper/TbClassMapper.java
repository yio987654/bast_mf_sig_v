package com.lanf.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanf.business.model.TbClass;
import com.lanf.business.vo.TbClassQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 班级 Mapper层
 * @date 2023-11-23 16:56:22
 */
@Repository
@Mapper
public interface TbClassMapper extends BaseMapper<TbClass> {
    IPage<TbClass> selectPage(Page<TbClass> page, @Param("vo") TbClassQueryVo tbClassQueryVo);

    List<TbClass> queryList(@Param("vo") TbClassQueryVo tbClassQueryVo);
}