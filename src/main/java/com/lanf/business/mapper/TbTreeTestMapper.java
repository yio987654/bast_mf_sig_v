package com.lanf.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanf.business.model.TbTreeTest;
import com.lanf.business.vo.TbTreeTestQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 树测试 Mapper层
 * @date 2023-11-23 16:57:00
 */
@Repository
@Mapper
public interface TbTreeTestMapper extends BaseMapper<TbTreeTest> {
    IPage<TbTreeTest> selectPage(Page<TbTreeTest> page, @Param("vo") TbTreeTestQueryVo tbTreeTestQueryVo);

    List<TbTreeTest> queryList(@Param("vo") TbTreeTestQueryVo tbTreeTestQueryVo);
}