package com.lanf.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lanf.business.model.TbArea;
import com.lanf.business.vo.TbAreaQueryVo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 地区 Service接口
 * @date 2023-11-23 16:56:37
 */
public interface TbAreaService extends IService<TbArea> {
    IPage<TbArea> selectPage(Page<TbArea> pageParam, TbAreaQueryVo queryVo);

    List<TbArea> queryList(TbAreaQueryVo queryVo);

    public void exportData(HttpServletResponse response);

    public boolean save(TbArea tbArea);

    public boolean updateById(TbArea tbArea);

    public TbArea getById(String id);

    public List<TbArea> getByIds(List<String> ids);
}
