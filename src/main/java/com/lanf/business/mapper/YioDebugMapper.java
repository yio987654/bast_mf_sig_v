package com.lanf.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanf.business.model.YioDebug;
import com.lanf.business.vo.YioDebugQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
/**
* @author tanlingfei
* @version 1.0
* @description yio测试 Mapper层
* @date 2024-05-09 23:38:22
*/
@Repository
@Mapper
public interface YioDebugMapper extends BaseMapper<YioDebug> {
    IPage<YioDebug> selectPage(Page<YioDebug> page, @Param("vo") YioDebugQueryVo yioDebugQueryVo);
    List<YioDebug> queryList(@Param("vo") YioDebugQueryVo yioDebugQueryVo);
}