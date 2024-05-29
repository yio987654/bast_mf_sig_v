package com.lanf.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lanf.business.model.Test02;
import com.lanf.business.vo.Test02QueryVo;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
/**
* @author tanlingfei
* @version 1.0
* @description 测试表2 Service接口
* @date 2024-05-09 23:57:31
*/
public interface Test02Service extends IService<Test02> {
    IPage<Test02> selectPage(Page<Test02> pageParam, Test02QueryVo queryVo);
    List<Test02> queryList(Test02QueryVo queryVo);
    public void exportData(HttpServletResponse response);
    public boolean save(Test02 test02);
    public boolean updateById(Test02 test02);
    public Test02 getById(String id);
    public List<Test02> getByIds(List<String> ids);
}
