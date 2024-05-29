package ${packageName}.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${packageName}.model.${modelName};
import ${packageName}.vo.${modelName}QueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
/**
* @author ${author}
* @version 1.0
* @description ${tableRemark} Mapper层
* @date ${createTime}
*/
@Repository
@Mapper
public interface ${modelName}Mapper extends BaseMapper<${modelName}> {
    IPage<${modelName}> selectPage(Page<${modelName}> page, @Param("vo") ${modelName}QueryVo ${modelName2}QueryVo);
    List<${modelName}> queryList(@Param("vo") ${modelName}QueryVo ${modelName2}QueryVo);
<#if isGenDept=='true' && isGenDeptMul!='true'>
    ${modelName} getById(@Param("id")  String id);
</#if>
}