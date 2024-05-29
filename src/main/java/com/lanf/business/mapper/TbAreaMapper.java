package com.lanf.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanf.business.model.TbArea;
import com.lanf.business.vo.TbAreaQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 地区 Mapper层
 * @date 2023-11-23 16:56:37
 */
@Repository
@Mapper
public interface TbAreaMapper extends BaseMapper<TbArea> {
    IPage<TbArea> selectPage(Page<TbArea> page, @Param("vo") TbAreaQueryVo tbAreaQueryVo);

    List<TbArea> queryList(@Param("vo") TbAreaQueryVo tbAreaQueryVo);
}