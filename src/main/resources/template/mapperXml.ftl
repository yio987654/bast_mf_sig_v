<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${packageName}.mapper.${modelName}Mapper">
    <resultMap id="${modelName2}Map" type="${packageName}.model.${modelName}" autoMapping="true">
    </resultMap>
    <!-- 用于select查询公用抽取的列 -->
    <sql id="columns">
        <#list data as var>
            <#if var_index != 0>,</#if>x1.${var.columeName}<#if var.mod!='default' && var.attrName!='id'>,${var.modModule2}${var_index}.${var.modColValue} as ${var.modValueAlias}</#if>
             <#if  var.attrName=='id'>
             <#list var.mulTableVoList as vl >
                 <#if vl.modTable=='sys_dic_item'>
                     ,IFNULL(sxds${var_index}.dic_code${var_index}s,'') dic_code${var_index}s
                 </#if>
             </#list>
             </#if>
        </#list>
        <#if isGenDeptMul=='true'>,d.dept_name</#if>
    </sql>
    <sql id="middles">
        from ${tableName} x1
        <#list data as var>
            <#if var.mod!='default' && var.attrName!='id'>
                left join (select * from ${var.modTable} where is_deleted=0) ${var.modModule2}${var_index}  on x1.${var.columeName}=${var.modModule2}${var_index}.${var.modColKey}
            </#if>
            <#if  var.attrName=='id'>
                <#list var.mulTableVoList as vl >
                    <#if vl.mulSecColumData=='dept_id' && isGenDeptMul=='true'>
               left join (select group_concat(sd.name order by sud.create_time asc) as dept_name,
               sud.${vl.mulMainColumData} from sys_dept sd
               inner join ${vl.mulTableData} sud on sd.id=sud.dept_id
               where sd.is_deleted = 0 and sud.is_deleted=0
               group by sud.${vl.mulMainColumData}) d on x1.id = d.${vl.mulMainColumData}
                    </#if>
                    <#if vl.modTable=='sys_dic_item'>
               left join (select group_concat(sdm${var_index}.code) as dic_code${var_index}s,
               sdrms${var_index}.${vl.mulMainColumData} from sys_dic_item sdm${var_index}
               inner join ${vl.mulTableData} sdrms${var_index} on sdrms${var_index}.${vl.mulSecColumData}=sdm${var_index}.code
               where sdm${var_index}.is_deleted = 0 and sdrms${var_index}.is_deleted=0
               group by sdrms${var_index}.${vl.mulMainColumData}) sxds${var_index} on x1.id = sxds${var_index}.${vl.mulMainColumData}
                    </#if>
                </#list>
            </#if>
        </#list>
    </sql>
    <sql id="conditions">
        <#list data as var>
            <#if (var.dataType=='char' || var.dataType=='varchar' || var.dataType=='text' || var.dataType=='tinytext') && var.attrName!='id' && var.attrName!='deptId'  >
                <if test="vo.${var.attrName} != null and vo.${var.attrName} != ''">
                    <#if var.attrName?contains("Id") >
              and x1.${var.columeName}=${r"#"}{vo.${var.attrName}}
                    <#else>
              and x1.${var.columeName} like CONCAT('%',${r"#"}{vo.${var.attrName}},'%')
                    </#if>
                </if>
                <#if var.mod!='default'>
                    <if test="vo.${var.modValueAlias} != null and vo.${var.modValueAlias} != ''">
              and ${var.modModule2}${var_index}.${var.modColValue} like CONCAT('%',${r"#"}{vo.${var.modValueAlias}},'%')
                    </if>
                </#if>
            </#if>
            <#if  var.attrName=='id'>
                <#list var.mulTableVoList as vl >
                    <#if isGenDeptMul=='true'  && vl.mulSecColumData=='dept_id'>
                        <choose>
                            <when test="vo.deptId != null and vo.deptId != ''">
                and  exists (select 1 from ${vl.mulTableData} xs inner join sys_dept xd
                on xs.dept_id=xd.id where x1.id=xs.${vl.mulMainColumData}
                and xs.is_deleted=0 and xd.is_deleted=0 and xd.tree_path
                like CONCAT('%,',${r"#"}{vo.deptId},',%'))
                            </when>
                            <when test="vo.curDeptIds!= null">
               and  exists (select 1 from ${vl.mulTableData} xs inner join sys_dept xd
               on xs.dept_id=xd.id where x1.id=xs.${vl.mulMainColumData}
               and xs.is_deleted=0 and xd.is_deleted=0  and
                                <foreach item="item" index="index" collection="vo.curDeptIds" open="(" separator=" or" close="))">
               xd.tree_path like CONCAT('%,',${r"#"}{item},',%')
                                </foreach>
                            </when>
                        </choose>
                    <#else>
                        <if test="vo.${vl.mulSecColum} != null and vo.${vl.mulSecColum} != ''">
               and exists (select 1 from ${vl.mulTableData} ${vl.mulTable2} where ${vl.mulTable2}.is_deleted=0
               and ${vl.mulTable2}.${vl.mulMainColumData}=x1.id and ${vl.mulTable2}.${vl.mulSecColumData}=${r"#"}{vo.${vl.mulSecColum}})
                        </if>
                    </#if>
                </#list>
            </#if>
        </#list>
        <#if isGenDeptMul!='true' && isGenDept=='true'>
            <choose>
                <when test="vo.deptId != null and vo.deptId != ''">
                    and  exists (select 1 from  sys_dept xd
                    where x1.dept_id=xd.id
                    and  xd.is_deleted=0 and xd.tree_path
                    like CONCAT('%,',${r"#"}{vo.deptId},',%'))
                </when>
                <when test="vo.curDeptIds!= null">
                    and  exists (select 1 from  sys_dept xd
                    where x1.dept_id=xd.id
                    and  xd.is_deleted=0 and
                    <foreach item="item" index="index" collection="vo.curDeptIds" open="(" separator=" or" close="))">
                        xd.tree_path like CONCAT('%,',${r"#"}{item},',%')
                    </foreach>
                </when>
            </choose>
        </#if>
        <if test="vo.createTimeBegin != null and vo.createTimeBegin != ''">
              and x1.create_time >= ${r"#"}{vo.createTimeBegin}
        </if>
        <if test="vo.createTimeEnd != null and vo.createTimeEnd != ''">
             and x1.create_time &lt;= ${r"#"}{vo.createTimeEnd}
        </if>
        <if test="vo.updateTimeBegin != null and vo.updateTimeBegin != ''">
             and x1.create_time >= ${r"#"}{vo.updateTimeBegin}
        </if>
        <if test="vo.updateTimeEnd != null and vo.updateTimeEnd != ''">
             and x1.create_time &lt;= ${r"#"}{vo.updateTimeEnd}
        </if>
             and x1.is_deleted = 0 order by x1.create_time desc
    </sql>
    <select id="selectPage" resultMap="${modelName2}Map">
        select <include refid="columns" />
        <include refid="middles" />
        <where>
            <include refid="conditions" />
        </where>
    </select>
    <select id="queryList" resultMap="${modelName2}Map">
        select <include refid="columns" />
        <include refid="middles" />
        <where>
            <include refid="conditions" />
        </where>
    </select>
    <#if isGenDept=='true' && isGenDeptMul!='true'>
        <select id="getById" resultMap="${modelName2}Map">
       select x.*,d.name as dept_name
       from ${tableName} x left join (select id,name from sys_dept where is_deleted = 0)  d on x.dept_id = d.id
       where x.id = ${r"#"}{id}
        </select>
    </#if>
</mapper>
