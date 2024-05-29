package com.lanf.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanf.system.model.SysUserDept;
import com.lanf.system.vo.SysUserDeptQueryVo;
import com.lanf.system.mapper.SysUserDeptMapper;
import com.lanf.system.service.SysUserDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.util.List;
/**
* @author tanlingfei
* @version 1.0
* @description 用户和部门中间表 Service实现类
* @date 2020-04-20 13:13:24
*/
@Transactional
@Service
public class SysUserDeptServiceImpl extends ServiceImpl
<SysUserDeptMapper, SysUserDept> implements SysUserDeptService {
    @Autowired
    private SysUserDeptMapper sysUserDeptMapper;

    @Override
    public IPage<SysUserDept> selectPage(Page<SysUserDept> pageParam, SysUserDeptQueryVo sysUserDeptQueryVo) {
        return sysUserDeptMapper.selectPage(pageParam,sysUserDeptQueryVo);
    }

    @Override
    public List<SysUserDept> queryList(SysUserDeptQueryVo queryVo){
        return sysUserDeptMapper.queryList(queryVo);
    }
    @Override
    public boolean save(SysUserDept sysUserDept){
        int result = this.sysUserDeptMapper.insert(sysUserDept);
        return result>0;
    }
    @Override
    public boolean updateById(SysUserDept sysUserDept){
        int row = this.sysUserDeptMapper.updateById(sysUserDept);
        return row>0;
    }
    @Override
    public SysUserDept getById(String id){
        SysUserDept sysUserDept = sysUserDeptMapper.selectById(id);
        return sysUserDept;
    }
}
