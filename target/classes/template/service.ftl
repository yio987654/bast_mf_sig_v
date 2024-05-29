package ${packageName}.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import ${packageName}.model.${modelName};
import ${packageName}.vo.${modelName}QueryVo;
import java.util.List;
<#if genType=='1'>
import com.lanf.model.base.TreeEntity;
import java.io.Serializable;
<#else>
import javax.servlet.http.HttpServletResponse;
</#if>
/**
* @author ${author}
* @version 1.0
* @description ${tableRemark} Service接口
* @date ${createTime}
*/
public interface ${modelName}Service extends IService<${modelName}> {
    IPage<${modelName}> selectPage(Page<${modelName}> pageParam, ${modelName}QueryVo queryVo);
    <#if genType=='1'>
    List<TreeEntity> queryList(${modelName}QueryVo queryVo);
    public boolean removeById(Serializable id);
    <#else>
    List<${modelName}> queryList(${modelName}QueryVo queryVo);
    public void exportData(HttpServletResponse response);
    </#if>
    public boolean save(${modelName} ${modelName2});
    public boolean updateById(${modelName} ${modelName2});
    public ${modelName} getById(String id);
    public List<${modelName}> getByIds(List<String> ids);
}
