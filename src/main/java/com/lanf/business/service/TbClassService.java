package com.lanf.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lanf.business.model.TbClass;
import com.lanf.business.vo.TbClassQueryVo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 班级 Service接口
 * @date 2023-11-23 16:56:22
 */
public interface TbClassService extends IService<TbClass> {
    IPage<TbClass> selectPage(Page<TbClass> pageParam, TbClassQueryVo queryVo);

    List<TbClass> queryList(TbClassQueryVo queryVo);

    public void exportData(HttpServletResponse response);

    public boolean save(TbClass tbClass);

    public boolean updateById(TbClass tbClass);

    public TbClass getById(String id);

    public List<TbClass> getByIds(List<String> ids);
}
