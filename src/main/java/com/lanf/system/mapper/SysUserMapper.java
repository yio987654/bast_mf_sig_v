package com.lanf.system.mapper;

import com.lanf.model.system.SysUser;
import com.lanf.model.vo.SysUserQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanf.system.model.SysDicItem;
import com.lanf.system.vo.SysDicItemQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    IPage<SysUser> selectPage(Page<SysUser> page, @Param("vo") SysUserQueryVo userQueryVo);

    List<SysUser> queryList(@Param("vo") SysUserQueryVo sysUserQueryVo);
}

