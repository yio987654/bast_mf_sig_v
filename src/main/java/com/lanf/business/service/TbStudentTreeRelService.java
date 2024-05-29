package com.lanf.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lanf.business.model.TbStudentTreeRel;
import com.lanf.business.vo.TbStudentTreeRelQueryVo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 学生树形关系表 Service接口
 * @date 2023-11-23 16:57:09
 */
public interface TbStudentTreeRelService extends IService<TbStudentTreeRel> {
    IPage<TbStudentTreeRel> selectPage(Page<TbStudentTreeRel> pageParam, TbStudentTreeRelQueryVo queryVo);

    List<TbStudentTreeRel> queryList(TbStudentTreeRelQueryVo queryVo);

    public void exportData(HttpServletResponse response);

    public boolean save(TbStudentTreeRel tbStudentTreeRel);

    public boolean updateById(TbStudentTreeRel tbStudentTreeRel);

    public TbStudentTreeRel getById(String id);

    public List<TbStudentTreeRel> getByIds(List<String> ids);
}
