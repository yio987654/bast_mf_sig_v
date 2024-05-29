package com.lanf.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanf.business.model.TbTreeTest2;
import com.lanf.business.vo.TbTreeTest2QueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 树测试2 Mapper层
 * @date 2023-11-24 00:28:41
 */
@Repository
@Mapper
public interface TbTreeTest2Mapper extends BaseMapper<TbTreeTest2> {
    IPage<TbTreeTest2> selectPage(Page<TbTreeTest2> page, @Param("vo") TbTreeTest2QueryVo tbTreeTest2QueryVo);

    List<TbTreeTest2> queryList(@Param("vo") TbTreeTest2QueryVo tbTreeTest2QueryVo);
}