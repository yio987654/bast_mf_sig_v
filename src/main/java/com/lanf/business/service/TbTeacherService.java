package com.lanf.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lanf.business.model.TbTeacher;
import com.lanf.business.vo.TbTeacherQueryVo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 讲师 Service接口
 * @date 2023-11-24 00:27:34
 */
public interface TbTeacherService extends IService<TbTeacher> {
    IPage<TbTeacher> selectPage(Page<TbTeacher> pageParam, TbTeacherQueryVo queryVo);

    List<TbTeacher> queryList(TbTeacherQueryVo queryVo);

    public void exportData(HttpServletResponse response);

    public boolean save(TbTeacher tbTeacher);

    public boolean updateById(TbTeacher tbTeacher);

    public TbTeacher getById(String id);

    public List<TbTeacher> getByIds(List<String> ids);
}
