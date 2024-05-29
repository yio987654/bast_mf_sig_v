package com.lanf.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanf.business.model.Test02;
import com.lanf.business.vo.Test02QueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
/**
* @author tanlingfei
* @version 1.0
* @description 测试表2 Mapper层
* @date 2024-05-09 23:57:31
*/
@Repository
@Mapper
public interface Test02Mapper extends BaseMapper<Test02> {
    IPage<Test02> selectPage(Page<Test02> page, @Param("vo") Test02QueryVo test02QueryVo);
    List<Test02> queryList(@Param("vo") Test02QueryVo test02QueryVo);
}