package com.lanf.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanf.business.model.YioDebug;
import com.lanf.business.vo.YioDebugQueryVo;
import com.lanf.business.vo.YioDebugExportVo;
import com.lanf.business.mapper.YioDebugMapper;
import com.lanf.business.service.YioDebugService;
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
* @description yio测试 Service实现类
* @date 2024-05-09 23:38:22
*/
@Transactional
@Service
public class YioDebugServiceImpl extends ServiceImpl
<YioDebugMapper, YioDebug> implements YioDebugService {
    @Autowired
    private YioDebugMapper yioDebugMapper;
    @Resource
    private ExcelHandler excelHandler;

    @Override
    public IPage<YioDebug> selectPage(Page<YioDebug> pageParam, YioDebugQueryVo yioDebugQueryVo) {
        //QueryWrapper<YioDebug> queryWrapper = new QueryWrapper<>();
        //return yioDebugMapper.selectPage(pageParam,queryWrapper);
        return yioDebugMapper.selectPage(pageParam,yioDebugQueryVo);
    }

    @Override
    public List<YioDebug> queryList(YioDebugQueryVo yioDebugQueryVo){
     List<YioDebug> result = yioDebugMapper.queryList(yioDebugQueryVo);
     return result;
   }

    @Override
    public boolean save(YioDebug yioDebug){
        int result = this.yioDebugMapper.insert(yioDebug);
        return result>0;
    }
    @Override
    public boolean updateById(YioDebug yioDebug){
        int row = this.yioDebugMapper.updateById(yioDebug);
        if(row <= 0){
            throw new LanfException(ResultCodeEnum.UPDATE_ERROR);
         }
         return row>0;
    }
    @Override
    public YioDebug getById(String id){
         YioDebug yioDebug = yioDebugMapper.selectById(id);
         return yioDebug;
    }
   @Override
   public List<YioDebug> getByIds(List<String> ids) {
      List<YioDebug> list = this.yioDebugMapper.selectBatchIds(ids);
      return list;
   }
   @Override
   public void exportData(HttpServletResponse response) {
   List<YioDebug> list = this.selectPage(new Page<YioDebug>(1, 10000), new YioDebugQueryVo()).getRecords();
   List<YioDebugExportVo> expList = new ArrayList<>();
   list.forEach(po -> {
       YioDebugExportVo vo = new YioDebugExportVo();
       BeanUtils.copyProperties(po, vo);
          expList.add(vo);
      });
      try {
          this.excelHandler.exportExcel(response, expList, YioDebugExportVo.class, "yio测试数据", "yio测试数据");
      } catch (Exception e) {
          e.printStackTrace();
          throw new LanfException(9005, "导出失败");
      }
   }
}
