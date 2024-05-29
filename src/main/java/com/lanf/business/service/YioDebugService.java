package com.lanf.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lanf.business.model.YioDebug;
import com.lanf.business.vo.YioDebugQueryVo;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
/**
* @author tanlingfei
* @version 1.0
* @description yio测试 Service接口
* @date 2024-05-09 23:38:22
*/
public interface YioDebugService extends IService<YioDebug> {
    IPage<YioDebug> selectPage(Page<YioDebug> pageParam, YioDebugQueryVo queryVo);
    List<YioDebug> queryList(YioDebugQueryVo queryVo);
    public void exportData(HttpServletResponse response);
    public boolean save(YioDebug yioDebug);
    public boolean updateById(YioDebug yioDebug);
    public YioDebug getById(String id);
    public List<YioDebug> getByIds(List<String> ids);
}
