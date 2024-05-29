package com.lanf.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lanf.business.model.TbStudent;
import com.lanf.business.vo.TbStudentQueryVo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 学生 Service接口
 * @date 2023-11-24 00:02:09
 */
public interface TbStudentService extends IService<TbStudent> {
    IPage<TbStudent> selectPage(Page<TbStudent> pageParam, TbStudentQueryVo queryVo);

    List<TbStudent> queryList(TbStudentQueryVo queryVo);

    public void exportData(HttpServletResponse response);

    public boolean save(TbStudent tbStudent);

    public boolean updateById(TbStudent tbStudent);

    public TbStudent getById(String id);

    public List<TbStudent> getByIds(List<String> ids);
}
