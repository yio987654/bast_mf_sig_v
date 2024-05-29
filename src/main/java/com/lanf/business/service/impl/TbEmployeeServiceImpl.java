package com.lanf.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanf.business.model.TbEmployee;
import com.lanf.business.vo.TbEmployeeQueryVo;
import com.lanf.business.vo.TbEmployeeExportVo;
import com.lanf.business.mapper.TbEmployeeMapper;
import com.lanf.business.service.TbEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.BeanUtils;
import com.lanf.common.result.ResultCodeEnum;
import com.lanf.system.exception.LanfException;
import com.lanf.system.easyexcel.ExcelHandler;
import com.lanf.system.utils.UserUtil;
import org.springframework.util.CollectionUtils;
import com.lanf.model.system.SysUser;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
/**
* @author generator
* @version 1.0
* @description 员工信息 Service实现类
* @date 2023-11-30 10:44:29
*/
@Transactional
@Service
public class TbEmployeeServiceImpl extends ServiceImpl
<TbEmployeeMapper, TbEmployee> implements TbEmployeeService {
    @Autowired
    private TbEmployeeMapper tbEmployeeMapper;
    @Resource
    private ExcelHandler excelHandler;

    @Override
    public IPage<TbEmployee> selectPage(Page<TbEmployee> pageParam, TbEmployeeQueryVo tbEmployeeQueryVo) {
        //QueryWrapper<TbEmployee> queryWrapper = new QueryWrapper<>();
        //return tbEmployeeMapper.selectPage(pageParam,queryWrapper);
            //只查询当前登录所属部门数据
        SysUser sysUser = UserUtil.getUserInfo();
        if ("1".equals(sysUser.getId())) {
            tbEmployeeQueryVo.setCurDeptIds(null);
        } else {
            if (CollectionUtils.isEmpty(sysUser.getDeptIds())) {
             return null;
            }
            tbEmployeeQueryVo.setCurDeptIds(sysUser.getDeptIds());
        }
        return tbEmployeeMapper.selectPage(pageParam,tbEmployeeQueryVo);
    }

    @Override
    public List<TbEmployee> queryList(TbEmployeeQueryVo tbEmployeeQueryVo){
            //只查询当前登录所属部门数据
       SysUser sysUser = UserUtil.getUserInfo();
       if ("1".equals(sysUser.getId())) {
            tbEmployeeQueryVo.setCurDeptIds(null);
        } else {
            if (CollectionUtils.isEmpty(sysUser.getDeptIds())) {
            return null;
        }
            tbEmployeeQueryVo.setCurDeptIds(sysUser.getDeptIds());
       }
     List<TbEmployee> result = tbEmployeeMapper.queryList(tbEmployeeQueryVo);
     return result;
   }

    @Override
    public boolean save(TbEmployee tbEmployee){
        int result = this.tbEmployeeMapper.insert(tbEmployee);
        return result>0;
    }
    @Override
    public boolean updateById(TbEmployee tbEmployee){
        int row = this.tbEmployeeMapper.updateById(tbEmployee);
        if(row <= 0){
            throw new LanfException(ResultCodeEnum.UPDATE_ERROR);
         }
         return row>0;
    }
    @Override
    public TbEmployee getById(String id){
        TbEmployee tbEmployee = tbEmployeeMapper.getById(id);
         return tbEmployee;
    }
   @Override
   public List<TbEmployee> getByIds(List<String> ids) {
      List<TbEmployee> list = this.tbEmployeeMapper.selectBatchIds(ids);
      return list;
   }
   @Override
   public void exportData(HttpServletResponse response) {
   List<TbEmployee> list = this.selectPage(new Page<TbEmployee>(1, 10000), new TbEmployeeQueryVo()).getRecords();
   List<TbEmployeeExportVo> expList = new ArrayList<>();
   list.forEach(po -> {
       TbEmployeeExportVo vo = new TbEmployeeExportVo();
       BeanUtils.copyProperties(po, vo);
          expList.add(vo);
      });
      try {
          this.excelHandler.exportExcel(response, expList, TbEmployeeExportVo.class, "员工信息数据", "员工信息数据");
      } catch (Exception e) {
          e.printStackTrace();
          throw new LanfException(9005, "导出失败");
      }
   }
}
