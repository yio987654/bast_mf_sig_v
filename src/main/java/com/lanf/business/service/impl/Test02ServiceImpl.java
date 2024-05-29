package com.lanf.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanf.business.model.Test02;
import com.lanf.business.vo.Test02QueryVo;
import com.lanf.business.vo.Test02ExportVo;
import com.lanf.business.mapper.Test02Mapper;
import com.lanf.business.service.Test02Service;
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
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
/**
* @author tanlingfei
* @version 1.0
* @description 测试表2 Service实现类
* @date 2024-05-09 23:57:31
*/
@Transactional
@Service
public class Test02ServiceImpl extends ServiceImpl
<Test02Mapper, Test02> implements Test02Service {
    @Autowired
    private Test02Mapper test02Mapper;
    @Resource
    private ExcelHandler excelHandler;

    @Override
    public IPage<Test02> selectPage(Page<Test02> pageParam, Test02QueryVo test02QueryVo) {
        //QueryWrapper<Test02> queryWrapper = new QueryWrapper<>();
        //return test02Mapper.selectPage(pageParam,queryWrapper);
        return test02Mapper.selectPage(pageParam,test02QueryVo);
    }

    @Override
    public List<Test02> queryList(Test02QueryVo test02QueryVo){
     List<Test02> result = test02Mapper.queryList(test02QueryVo);
     return result;
   }

    @Override
    public boolean save(Test02 test02){
        int result = this.test02Mapper.insert(test02);
        return result>0;
    }
    @Override
    public boolean updateById(Test02 test02){
        int row = this.test02Mapper.updateById(test02);
        if(row <= 0){
            throw new LanfException(ResultCodeEnum.UPDATE_ERROR);
         }
         return row>0;
    }
    @Override
    public Test02 getById(String id){
         Test02 test02 = test02Mapper.selectById(id);
         return test02;
    }
   @Override
   public List<Test02> getByIds(List<String> ids) {
      List<Test02> list = this.test02Mapper.selectBatchIds(ids);
      return list;
   }
   @Override
   public void exportData(HttpServletResponse response) {
   List<Test02> list = this.selectPage(new Page<Test02>(1, 10000), new Test02QueryVo()).getRecords();
   List<Test02ExportVo> expList = new ArrayList<>();
   list.forEach(po -> {
       Test02ExportVo vo = new Test02ExportVo();
       BeanUtils.copyProperties(po, vo);
          expList.add(vo);
      });
      try {
          this.excelHandler.exportExcel(response, expList, Test02ExportVo.class, "测试表2数据", "测试表2数据");
      } catch (Exception e) {
          e.printStackTrace();
          throw new LanfException(9005, "导出失败");
      }
   }
}
