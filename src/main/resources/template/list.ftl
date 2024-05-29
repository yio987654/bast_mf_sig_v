<template>
    <#if isGenLeftDept=='true'>
    <el-container style="height: 100%; border: 1px solid #eee">
        <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
            <el-input v-model="searchDeptValue" placeholder="请输入关键字过滤" style="margin-bottom:10px;"/>
            <el-tag>当前选择：{{selectedDeptName}}</el-tag>
            <#if isLazyDept!='true'>
            <el-tree
                    ref="eltree"
                    :data="dataDeptTree"
                    node-key="id"
                    :default-expand-all="true"
                    :expand-on-click-node="false"
                    :default-expanded-keys="defaultDeptExpandedKeys"
                    :filter-node-method="filterDeptNode"
                    @current-change="handleDeptTreeSelectChange"
            />
            <#else>
                <el-tree
                        ref="eltree"
                        node-key="id"
                        :expand-on-click-node="false"
                        :load="loadNodeByParent"
                         lazy
                        :props="props"
                        :filter-node-method="filterDeptNode"
                        @current-change="handleDeptTreeSelectChange"
                />
            </#if>
        </el-aside>
  <el-container>
   <el-main>
        </#if>
    <div class="app-container">
        <#if genType!='1' >
        <div class="search-div">
            <el-form label-width="70px" size="small">
                <el-row>
                    <#list data as var>
                        <#if  genType!='1' && (var.dataType=='char' || var.dataType=='varchar' || var.dataType=='text' || var.dataType=='tinytext') && var.attrName!='id'  >
                            <el-col :span="8">
                                <el-form-item label="${var.remarks}">
                                    <#if var.mod=='pop'>
                                        <el-input v-model="searchObj.${var.modValueAlias}"
                                                  placeholder="${var.modValueReamrk}" clearable/>
                                    <#elseif var.mod=='select'>
                                        <#if var.modModuleType=='0'>
                                            <el-select
                                                    v-model="searchObj.${var.attrName}"
                                                    placeholder="${var.remarks}">
                                                <el-option
                                                        v-for="${var.modModule2} in ${var.modModule2}${var.whereName}List"
                                                        :key="${var.modModule2}.${var.modKey}"
                                                        :label="${var.modModule2}.${var.modValue}"
                                                        :value="${var.modModule2}.${var.modKey}"/>
                                            </el-select>
                                        <#else>
                                            <el-cascader
                                                    v-model="searchObj.${var.attrName}"
                                                    :props="{ label: '${var.modValue}', value: '${var.modKey}', checkStrictly: true, emitPath: false }"
                                                    :options="${var.modModule2}${var.whereName}List"
                                                    clearable
                                                    filterable
                                            ></el-cascader>
                                        </#if>
                                    <#elseif var.mod=='radio'>
                                        <el-radio v-model="searchObj.${var.attrName}"
                                                  v-for="l in ${var.modModule2}${var.whereName}List"
                                                  :label="l.${var.modKey}">{{l.${var.modValue}}}
                                        </el-radio>
                                    <#elseif var.mod=='checkbox'>
                                        <el-checkbox-group v-model="searchObj.${var.attrName}">
                                            <el-checkbox v-for="l in ${var.modModule2}${var.whereName}List"
                                                         :label="l.${var.modKey}">{{l.${var.modValue}}}
                                            </el-checkbox>
                                        </el-checkbox-group>
                                    <#else>
                                        <el-input v-model="searchObj.${var.attrName}" placeholder="${var.remarks}" clearable/>
                                    </#if>
                                </el-form-item>
                            </el-col>
                        </#if>
                    </#list>
                </el-row>

                <el-row style="display:flex">
                    <el-button type="primary" icon="el-icon-search" size="mini" @click="fetchData()">搜索</el-button>
                    <el-button icon="el-icon-refresh" size="mini" @click="resetData">重置</el-button>
                </el-row>
            </el-form>
        </div>
        </#if>
        <!-- 工具条 -->
        <div class="tools-div">
            <#if genType!='1'>
            <el-button type="success" icon="el-icon-plus" size="mini" @click="add" :disabled="$hasBP('bnt.${modelName2}.add')  === false">添 加</el-button>
            <#else>
            <el-button type="success" icon="el-icon-plus" size="mini" @click="add()" :disabled="$hasBP('bnt.${modelName2}.add')  === false">添 加</el-button>
            </#if>
            <#if genType!='1'>
            <el-button class="btn-add" size="mini" @click="batchRemove()" :disabled="$hasBP('bnt.${modelName2}.remove')  === false">批量删除</el-button>
            <el-button style="margin-left:8px" size="mini" :disabled="$hasBP('bnt.${modelName2}.list')  === false" type="primary" @click="exportData">导出数据</el-button>
            </#if>
        </div>
        <!-- 列表 -->
        <#if genType=='1'>
        <el-table
                v-loading="listLoading"
                :data="list"
                style="width: 100%;margin-bottom: 20px;margin-top: 10px;"
                row-key="id"
                border
                :default-expand-all="false"
                :tree-props="{children: 'children'}">
        <#else>
            <el-table
                    v-loading="listLoading"
                    :data="list"
                    :row-key="getRowKeys"
                    stripe
                    border
                    style="width: 100%;margin-top: 10px;"
                    @selection-change="handleSelectionChange">
        </#if>
        <#if genType!='1'>
            <el-table-column
                    label="序号"
                    width="70"
                    align="center">
                <template slot-scope="scope">
                    {{ (page - 1) * limit + scope.$index + 1 }}
                </template>
            </el-table-column>
            <el-table-column type="selection"  :reserve-selection="true"/>
        </#if>
            <#assign index=1 >
            <#list data as var>
                <#if index<=8>
                <#if var.attrName!='isDeleted' && var.attrName!='id' && (var.attrName!='parentId' || genType!='1')>
                     <#assign index=index+1>
                    <#if var.mod=='default'>
                        <el-table-column sortable prop="${var.attrName}" label="${var.remarks}"/>
                    <#else>
                        <#if var.modValueReamrk!='名称'>
                            <el-table-column sortable prop="${var.modValueAlias}" label="${var.modValueReamrk}"/>
                        <#else>
                            <el-table-column sortable prop="${var.modValueAlias}" label="${var.remarks}"/>
                        </#if>
                    </#if>
                </#if>
                 <#if var.attrName=='id'>
                     <#list var.mulTableVoList as vl >
                     <#if index<=8>
                      <#if vl.isMul=='true'>
                        <#assign index=index+1>
                         <#if vl.modValueReamrk!='名称'>
                            <el-table-column sortable  label="${vl.modValueReamrk}">
                         <#else>
                            <el-table-column sortable  label="${vl.remarks}" >
                         </#if>
                         <template slot-scope="scope">
                             <#if vl.modTable=='sys_dic_item'>
                                 <SysDicItemView :dicCodes="scope.row.dicCode${var_index}s"></SysDicItemView>
                              <#else>
                                  <${vl.modModule}View :${vl.mulMainColum}="scope.row.id"></${vl.modModule}View>
                             </#if>
                         </template>
                     </el-table-column>
                                </#if>
                             </#if>
                     </#list>
                 </#if>
                    <#if index<=8>
                    <#if isGenDeptMul=='true' && var.attrName=='id'>
                        <#assign index=index+1>
                        <el-table-column sortable prop="deptName" label="所属部门"/>
                    </#if>
                    </#if>
                  </#if>
            </#list>
                                <el-table-column type="expand" label="...">
                                    <template slot-scope="props">
                                        <el-form label-position="left" inline class="demo-table-expand">
                                            <#list data as var>
                                            <#if var.attrName!='isDeleted' && var.attrName!='id' && (var.attrName!='parentId' || genType!='1')>
                                                <#if var.mod=='default'>
                                                    <el-form-item label="${var.remarks}">
                                                        <span>{{ props.row.${var.attrName} }}</span>
                                                    </el-form-item>
                                                <#else>
                                                    <#if var.modValueReamrk!='名称'>
                                                        <el-form-item label="${var.modValueReamrk}">
                                                            <span>{{ props.row.${var.modValueAlias} }}</span>
                                                        </el-form-item>
                                                    <#else>
                                                        <el-form-item label="${var.remarks}">
                                                            <span>{{ props.row.${var.modValueAlias} }}</span>
                                                        </el-form-item>
                                                    </#if>
                                                </#if>
                                            </#if>
                                            <#if var.attrName=='id'>
                                            <#list var.mulTableVoList as vl >
                                            <#if vl.isMul=='true'>
                                            <#if vl.modValueReamrk!='名称'>
                                                <el-form-item label="${vl.modValueReamrk}">
                                                <#else>
                                                    <el-form-item label="${vl.remarks}">
                                                    </#if>
                                                        <#if vl.modTable=='sys_dic_item'>
                                                            <span><SysDicItemView :dicCodes="props.row.dicCode${var_index}s"></SysDicItemView></span>
                                                        <#else>
                                                        <span><${vl.modModule}View :${vl.mulMainColum}="props.row.id"></${vl.modModule}View></span>
                                                        </#if>
                                                  </el-form-item>
                                                </#if>
                                                </#list>
                                                </#if>
                                                    <#if isGenDeptMul=='true' && var.attrName=='id'>
                                                        <el-form-item label="所属部门">
                                                            <span>{{ props.row.deptName }}</span>
                                                        </el-form-item>
                                                    </#if>
                                                </#list>
                                        </el-form>
                                    </template>
                                </el-table-column>
            <el-table-column label="操作" align="center" fixed="right">
                <template slot-scope="scope">
                    <#if genType=='1'>
                        <el-button type="success" v-if="scope.row.type !== 2" icon="el-icon-plus" size="mini" @click="add(scope.row)"
                                   title="添加下级"/>
                    </#if>
                    <el-button type="primary" icon="el-icon-edit" size="mini" @click="edit(scope.row.id)" :disabled="$hasBP('bnt.${modelName2}.update')  === false" title="修改"/>
                    <el-button type="danger" icon="el-icon-delete" size="mini" @click="removeDataById(scope.row.id)"
                               :disabled="$hasBP('bnt.${modelName2}.remove')  === false"     title="删除"/>
                </template>
            </el-table-column>
        </el-table>
     <#if genType!='1'>
        <!-- 分页组件 -->
        <el-pagination
                @size-change="sizeChangeHandle"
                :current-page="page"
                :total="total"
                :page-size="limit"
                style="padding: 30px 0; text-align: center;"
                :page-sizes="[5, 10, 50, 100]"
                layout="total,sizes, prev, pager, next, jumper"
                @current-change="fetchData"
        />
     </#if>
        <el-dialog title="添加/修改" :visible.sync="dialogVisible" width="40%" :close-on-click-modal="false">
            <el-form ref="dataForm" :model="${modelName2}" :rules="rules" label-width="100px" size="small"
                     style="padding-right: 40px;">
                <#if genType=='1'>
                    <el-form-item label="上级">
                        <select-parent-tree @saveTreeId="saveParentId" treeTitle="选择上级" :dataTree="dataParentTree"
                                          :treeVal="${modelName2}.parentName" :treeId="${modelName2}.parentId"></select-parent-tree>
                    </el-form-item>
                </#if>
                <#list data as var>
                <#if var.attrName!='isDeleted' && var.attrName!='parentId' && var.attrName!='createTime' && var.attrName!='updateTime' && (var.attrName!='id' || var.isMul=='true')>
                    <#if var.isMul!='true' && var.attrName!='id' >
                    <el-form-item label="${var.remarks}" prop="${var.attrName}">
                        <#if var.mod=='pop'>
                                <#if var.attrName!='deptId'>
                                    <${var.modModule}SelectOne
                                            :myvals="${modelName2}.${var.attrName}"
                                            @getInfo="get${var.modModule}${var.whereName}" where="${var.where}"  />
                                <#elseif var.attrName=='deptId'>
                                      <#if isLazyDept=='true'>
                                          <select-dept-tree ref="zdept" @saveTreeId="saveDeptId" treeTitle="${var.modRemark}" :isLazy="true"
                                                            :treeVal="${modelName2}.deptName" :treeId="${modelName2}.deptId"></select-dept-tree>
                                       <#else>
                                           <select-dept-tree  ref="zdept" @saveTreeId="saveDeptId" treeTitle="${var.modRemark}" :isLazy="false" :dataTree="dataDeptTree"
                                                             :treeVal="${modelName2}.deptName" :treeId="${modelName2}.deptId"></select-dept-tree>
                                      </#if>
                                </#if>
                        <#elseif var.mod=='select'>
                            <#if var.modModuleType=='0'>
                                <el-select
                                        v-model="${modelName2}.${var.attrName}"
                                        placeholder="${var.remarks}">
                                    <el-option
                                            v-for="${var.modModule2} in ${var.modModule2}${var.whereName}List"
                                            :key="${var.modModule2}.${var.modKey}"
                                            :label="${var.modModule2}.${var.modValue}"
                                            :value="${var.modModule2}.${var.modKey}"/>
                                </el-select>
                            <#else>
                                <el-cascader
                                        v-model="${modelName2}.${var.attrName}"
                                        :props="{ label: '${var.modValue}', value: '${var.modKey}', checkStrictly: true, emitPath: false }"
                                        :options="${var.modModule2}${var.whereName}List"
                                        clearable
                                        filterable
                                ></el-cascader>
                            </#if>
                        <#elseif var.mod=='radio'>
                            <el-radio v-model="${modelName2}.${var.attrName}" v-for="l in ${var.modModule2}${var.whereName}List" :label="l.${var.modKey}" >{{l.${var.modValue}}}</el-radio>
                        <#elseif var.mod=='checkbox'>
                            <#if var.attrName=='id' && var.isMul=='true'>
                                <el-checkbox-group v-model="${modelName2}.${var.mulSecColum}List">
                                    <el-checkbox v-for="l in ${var.modModule2}${var.whereName}List" :label="l.${var.modKey}">{{l.${var.modValue}}}</el-checkbox>
                                </el-checkbox-group>
                            </#if>
                        <#else>
                            <#if  var.typeName =='Integer' || var.typeName =='java.math.BigDecimal' >
                                <el-input-number :min="0" v-model="${modelName2}.${var.attrName}" controls-position="right"  clearable/>
                            <#elseif var.typeName=='java.util.Date' || var.typeName=='Date'>
                                <el-date-picker
                                        v-model="${modelName2}.${var.attrName}"
                                        type="datetime"
                                        value-format="yyyy-MM-dd HH:mm:ss"
                                        placeholder="选择日期">
                                </el-date-picker>
                            <#else>
                                <el-input v-model="${modelName2}.${var.attrName}" clearable/>
                            </#if>
                        </#if>
                    </el-form-item>
                    <#else>
                        <#list var.mulTableVoList as vl >
                            <el-form-item label="${vl.modRemark}" prop="${vl.mulSecColum}">
                                <#if vl.mod=='pop'>
                                    <#if vl.attrName=='id' && vl.isMul=='true' && vl.mulSecColum!='deptId'>
                                        <${vl.modModule}Select
                                                :myvals="${modelName2}.${vl.mulSecColum}List"
                                                @getInfo="get${vl.modModule}${vl.whereName}" :single="false" where="${vl.where}"   />
                                    <#else>
                                        <#if vl.attrName!='deptId' && vl.mulSecColum!='deptId'>
                                            <${vl.modModule}Select
                                                    :myvals="${modelName2}.${vl.attrName}"
                                                    @getInfo="get${vl.modModule}${vl.whereName}" :single="true" where="${vl.where}"  />
                                        <#elseif vl.mulSecColum=='deptId'>
                                             <#if isLazyDept=='true'>
                                                 <el-tree
                                                         ref="eltreeDept"
                                                         node-key="id"
                                                         :props="props"
                                                         :load="loadNodeByParent"
                                                         check-strictly="true"
                                                         lazy
                                                         show-checkbox>
                                                 </el-tree>
                                             <#else>
                                            <el-tree
                                                    ref="eltreeDept"
                                                    :data="dataDeptTree"
                                                    node-key="id"
                                                    show-checkbox
                                                    :expand-on-click-node="false"
                                                     check-strictly="true"
                                                    :default-checked-keys="${modelName2}.${vl.mulSecColum}List"
                                            />
                                            </#if>
                                        </#if>
                                    </#if>
                                <#elseif vl.mod=='select'>
                                  <#if vl.modModuleType=='0'>
                                    <el-select multiple
                                            v-model="${modelName2}.${vl.mulSecColum}List"
                                            placeholder="${vl.remarks}">
                                        <el-option
                                                v-for="${vl.modModule2} in ${vl.modModule2}${vl.whereName}List"
                                                :key="${vl.modModule2}.${vl.modKey}"
                                                :label="${vl.modModule2}.${vl.modValue}"
                                                :value="${vl.modModule2}.${vl.modKey}"/>
                                    </el-select>
                                  <#else>
                                    <el-cascader
                                              v-model="${modelName2}.${vl.mulSecColum}List"
                                              :props="{ label: '${vl.modValue}', multiple:'true', value: '${vl.modKey}', checkStrictly: true, emitPath: false }"
                                              :options="${vl.modModule2}${vl.whereName}List"
                                              clearable
                                              filterable></el-cascader>
                                  </#if>
                                <#elseif vl.mod=='radio'>
                                    <el-radio v-model="${modelName2}.${vl.attrName}" v-for="l in ${vl.modModule2}${vl.whereName}List" :label="l.${vl.modKey}" >{{l.${vl.modValue}}}</el-radio>
                                <#elseif vl.mod=='checkbox'>
                                    <#if vl.attrName=='id' && vl.isMul=='true'>
                                        <el-checkbox-group v-model="${modelName2}.${vl.mulSecColum}List">
                                            <el-checkbox v-for="l in ${vl.modModule2}${vl.whereName}List" :label="l.${vl.modKey}">{{l.${vl.modValue}}}</el-checkbox>
                                        </el-checkbox-group>
                                    </#if>
                                <#else>
                                    <#if  vl.typeName =='Integer' || vl.typeName =='java.math.BigDecimal' >
                                        <el-input-number :min="0" v-model="${modelName2}.${vl.attrName}" controls-position="right" />
                                    <#elseif vl.typeName=='java.util.Date' || vl.typeName=='Date'>
                                        <el-date-picker
                                                v-model="${modelName2}.${vl.attrName}"
                                                type="date"
                                                placeholder="选择日期" clearable>
                                        </el-date-picker>
                                    <#else>
                                        <el-input v-model="${modelName2}.${vl.attrName}" clearable/>
                                    </#if>
                                </#if>
                            </el-form-item>
                        </#list>
                    </#if>
                    </#if>
                 </#list>
            </el-form>
            <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false" size="small" icon="el-icon-refresh-right">取 消</el-button>
        <el-button type="primary" icon="el-icon-check" @click="saveOrUpdate()" size="small">确 定</el-button>
      </span>
        </el-dialog>
    </div>
<#if isGenLeftDept=='true'>
            </el-main>
        </el-container>
    </el-container>
            </#if>
</template>
<script>
    import {getToken} from '@/utils/auth'
    import api from '@/api/${packageSub}/${modelName2}'
    <#list data as var>
    <#if var.isMul=='true' && var.attrName=='id'>
    <#list var.mulTableVoList as vl>
    <#if vl.mod=='pop' && vl.attrName!='deptId' && vl.mulSecColum!='deptId' && vl.isReapted=='false'>
    import ${vl.modModule}Select from './${modelName2}${vl.modModule}Select'
    <#elseif vl.mod=='pop' && (vl.attrName=='deptId' || vl.mulSecColum=='deptId') >
    import sysDeptApi from '@/api/${vl.pageDir}/sysDept'
    <#elseif (vl.mod=='select' || vl.mod=='radio' || vl.mod=='checkbox') && vl.isReapted=='false' >
    import ${vl.modModule2}Api from '@/api/${vl.pageDir}/${vl.modModule2}'
    </#if>
    <#if   vl.isViewReapted!='true' && vl.isMul=='true'  >
    <#if vl.modTable!='sys_dic_item'>
    import ${vl.modModule}View from './${modelName2}${vl.modModule}View'
    <#else>
    import SysDicItemView from '@/components/Custom/SysDic/SysDicItemView'
    </#if>

    </#if>
    </#list>
    <#else>
    <#if var.mod=='pop' && var.attrName!='deptId' && var.isReapted=='false'>
    import ${var.modModule}SelectOne from './${modelName2}${var.modModule}SelectOne'
    <#elseif var.mod=='pop' && var.attrName=='deptId' >
    import sysDeptApi from '@/api/${var.pageDir}/sysDept'
    <#elseif (var.mod=='select' || var.mod=='radio' || var.mod=='checkbox') && var.isReapted=='false' >
    import ${var.modModule2}Api from '@/api/${var.pageDir}/${var.modModule2}'
    </#if>
    </#if>
    </#list>
    const defaultForm = {
        <#list data as var>
        <#if var.isMul=='true' && var.attrName=='id'>
        <#list var.mulTableVoList as vl>
        <#if vl.mod=='checkbox' && vl.isMul=='true'>
        ${vl.mulSecColum}List: [],
        </#if>
        </#list>
        <#else>
        <#if var.mod=='checkbox' && var.isMul=='true'>
        ${var.mulSecColum}List: [],
        </#if>
        </#if>
        </#list>
    }
    export default {
        components: {
            <#if genType=='1'>
            //导入树形选择组件
            SelectParentTree: () => import('@/components/Custom/TreeSelect/TreeSelect'),
            </#if>
            <#list data as var>
            <#if var.isMul=='true' && var.attrName=='id'>
            <#list var.mulTableVoList as vl>
            <#if vl.mod=='pop' && vl.attrName!='deptId' && vl.mulSecColum!='deptId' && vl.isReapted=='false'>
            ${vl.modModule}Select,
            </#if>
            <#if vl.isViewReapted!='true' && vl.isMul=='true'>
            <#if vl.modTable!='sys_dic_item'>
            ${vl.modModule}View,
            <#else>
             SysDicItemView,
            </#if>
            </#if>

            </#list>
            <#else>
            <#if var.mod=='pop' && var.attrName!='deptId'>
            ${var.modModule}SelectOne,
            </#if>
            </#if>
            </#list>
            <#if isGenDept=='true'>
            SelectDeptTree: () => import('@/components/Custom/TreeSelect/TreeSelect')
            </#if>
        },
        data() {
            return {
               <#if isLazyDept=='true'>
                props: {
                    label: 'name',
                    id:'id',
                    children: 'childlist',
                    isLeaf: 'leaf'
                },
                </#if>
                listLoading: true, // 数据是否正在加载
                list: [], // banner列表
                total: 0, // 数据库中的总记录数
                page: 1, // 默认页码
                limit: 5, // 每页记录数
                searchObj: {}, // 查询表单对象
                dialogVisible: false,
                ${modelName2}:defaultForm,
                saveBtnDisabled:false,
                selectValue:[] //复选框选择内容封装数组
                <#list data as var>
                <#if var.isMul=='true' && var.attrName=='id'>
                <#list var.mulTableVoList as vl>
                <#if (vl.mod=='select' || vl.mod=='radio' || vl.mod=='checkbox') >
                , ${vl.modModule2}${vl.whereName}List:[]
                </#if>
                </#list>
                <#else>
                <#if (var.mod=='select' || var.mod=='radio' || var.mod=='checkbox') >
                , ${var.modModule2}${var.whereName}List:[]
                </#if>
                </#if>
                </#list>
                <#if isGenDept=='true'>
                , dataDeptTree:[]//树形选择数据
                </#if>
                , rules:{
                <#list requireList as var>
                <#if var_index != 0>,</#if>
                ${var.attrName}:
                [
                    {required: true, message: '请输入${var.remarks}', trigger: 'blur'}
                ]
                </#list>
            }
            <#if genType=='1'>
            ,dataParentTree:null
            </#if>
            <#if isGenLeftDept=='true'>
            //左侧树begin
              ,searchDeptValue: '', //左侧部门搜索绑定
              defaultDeptExpandedKeys: [],
              selectedDeptValue: '',
              selectedDeptName: ''
            //左侧树end
            </#if>
        }
        },
        <#if isGenLeftDept=='true'>
        watch: {
            //监听左侧树查询事件
            searchDeptValue(value) {
                this.$refs.eltree.filter(value)
            }
        },
        </#if>
        // 生命周期函数：内存准备完毕，页面尚未渲染
        created() {
            console.log('list created......')
            this.fetchData()
            <#if genType=='1'>
            this.fetchPopParent()
            </#if>
            <#list data as var>
            <#if var.isMul=='true' && var.attrName=='id'>
            <#list var.mulTableVoList as vl>
            <#if (vl.mod=='select' || vl.mod=='radio' || vl.mod=='checkbox')>
            this.getAll${vl.modModule}${vl.whereName}()
            </#if>
            </#list>
            <#else>
            <#if (var.mod=='select' || var.mod=='radio' || var.mod=='checkbox')>
            this.getAll${var.modModule}${var.whereName}()
            </#if>
            </#if>
            </#list>
            <#if isGenDept=='true' && isLazyDept!='true' >
            this.fetchPopDept()
            </#if>
        },
        // 生命周期函数：内存准备完毕，页面渲染成功
        mounted() {
            console.log('list mounted......')
        },
        methods: {
            getRowKeys(row) {
                return row.id
            },
            <#if genType=='1'>
            //回调保存选择的部门id
            saveParentId(treeId) {
                if (treeId == this.${modelName2}.id) {
                    this.$alert('上级不能和当前相同', '提示', {
                        confirmButtonText: '确定',
                        callback: action => {
                            this.${modelName2}.parentId = '';
                            this.${modelName2}.parentName = '';
                            api.getById(this.${modelName2}.id).then(response => {
                                this.${modelName2} = response.data
                                //this.${modelName2}.parentName = row.name
                            })
                        }
                    });
                } else {
                    this.${modelName2}.parentId = treeId
                    this.${modelName2}.parentName= treeName
                }
            },

            fetchPopParent() {
                //加载弹出选择部门数据
                api.getAll${modelName}().then(response => {
                    this.dataParentTree = response.data
                })

            },

            </#if>
            //导出数据
            exportData() {
                window.open(process.env.VUE_APP_BASE_API + '/${packageSub}/${modelName2}/export?token=' + getToken());
            },
            <#if isGenLeftDept=='true'>
            //左侧树形方法begin
            // 树节点选中改变
            handleDeptTreeSelectChange(data) {
                this.selectedDeptValue = data.id
                if(data.label){
                    this.selectedDeptName = data.label
                }else{
                    this.selectedDeptName = data.name
                }
                //重新加载主数据
                this.searchObj.deptId = data.id
                this.fetchData(this.page)
            },
            filterDeptNode(value, data) {
                if (!value) return true
                if(data.label){
                    return data.label.indexOf(value) !== -1
                }else{
                    return data.name.indexOf(value) !== -1
                }
            },
            //左侧树形方法end
            </#if>
            <#if isLazyDept=='true'>
            loadNodeByParent(node, resolve) {
                if (node.level === 0) {
                    //调用后台查询第一级别
                    sysDeptApi.findNodesByParent('0').then(response => {
                        resolve(response.data);
                        this.$nextTick(function () {
                            if(this.$refs.eltreeDept){
                                if(!this.${modelName2}.deptIdList){
                                    this.${modelName2}.deptIdList=[]
                                }
                                this.$refs.eltreeDept.setCheckedKeys(this.${modelName2}.deptIdList);
                            }

                        })

                    })
                }else{
                    let parentId = node.data.id;
                    sysDeptApi.findNodesByParent(parentId).then(response => {
                        resolve(response.data);
                        this.$nextTick(function () {
                            if(this.$refs.eltreeDept){
                                if(!this.${modelName2}.deptIdList){
                                    this.${modelName2}.deptIdList=[]
                                }
                                this.$refs.eltreeDept.setCheckedKeys(this.${modelName2}.deptIdList);
                            }
                        })
                    })
                }
            },
            </#if>
            // 每页数
            sizeChangeHandle(val) {
                this.limit = val;
                this.page = 1;
                this.fetchData(this.page);
            },
            <#list data as var>
            <#if var.isMul=='true' && var.attrName=='id'>
            <#list var.mulTableVoList as vl>
            <#if vl.mod=='pop' && vl.attrName!='deptId' && vl.mulSecColum!='deptId'>
            get${vl.modModule}${vl.whereName}(selections) {
                this.${modelName2}.${vl.mulSecColum}List = selections
            },
            <#elseif (vl.mod=='select' || vl.mod=='radio' || vl.mod=='checkbox')>
            getAll${vl.modModule}${vl.whereName}() {
                let queryObj = {}
                <#if vl.where!='null' && vl.where!=''>
                queryObj = JSON.parse('${vl.where}')
                </#if>
                ${vl.modModule2}Api.getAll${vl.modModule}(queryObj)
                    .then(response => {
                        this.${vl.modModule2}${vl.whereName}List = response.data
                    })
            },
            </#if>
            </#list>
            <#else>
            <#if var.mod=='pop' && var.attrName!='deptId'>
            get${var.modModule}${var.whereName}(selections) {
                this.${modelName2}.${var.attrName} = selections
                this.$refs["dataForm"].clearValidate("${var.attrName}")
                this.rules.${var.attrName} = []
            },
            <#elseif (var.mod=='select' || var.mod=='radio' || var.mod=='checkbox')>
            getAll${var.modModule}${var.whereName}() {
                let queryObj = {}
                <#if var.where!='null' && var.where!=''>
                 queryObj = JSON.parse('${var.where}')
                </#if>
                ${var.modModule2}Api.getAll${var.modModule}(queryObj)
                    .then(response => {
                        this.${var.modModule2}${var.whereName}List = response.data
                    })
            },
            </#if>
            </#if>
            </#list>
            <#if isGenDept=='true'>
            //回调保存选择的部门id
            saveDeptId(treeId, treeName) {
                this.${modelName2}.deptId = treeId
                this.$refs["dataForm"].clearValidate("deptId")
                this.rules.deptId = []
            },
            <#if isLazyDept!='true'>
            fetchPopDept() {
                //加载弹出选择部门数据
                sysDeptApi.findSelectNodes().then(response => {
                    this.dataDeptTree = response.data
                })
            },
            </#if>
            </#if>
            // 加载banner列表数据
            fetchData(page = 1) {
                debugger
                this.page = page
                <#if genType == '1'>
                api.getAll${modelName}(this.searchObj).then(
                    response => {
                        //this.list = response.data.list
                        this.list = response.data
                        this.listLoading = false
                    }
                )
                <#else>
                api.getPageList(this.page, this.limit, this.searchObj).then(
                    response => {
                        //this.list = response.data.list
                        this.list = response.data.records
                        this.total = response.data.total
                        // 数据加载并绑定成功
                        this.listLoading = false
                    }
                )
                </#if>
            },
            // 重置查询表单
            resetData() {
                console.log('重置查询表单')
                this.searchObj = {}
                this.fetchData()
            },
            // 根据id删除数据
            removeDataById(id) {
                // debugger
                this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => { // promise
                    // 点击确定，远程调用ajax
                    return api.removeById(id)
                }).then((response) => {
                    this.fetchData(this.page)
                    this.$message.success(response.message || '删除成功')
                }).catch(() => {
                    this.$message.info('取消删除')
                })
            },
            //复选框发生变化执行方法
            handleSelectionChange(selection) {
                this.selectValue = selection
                //console.log(this.selectValue)
            },
            //批量删除
            batchRemove() {
                //判断
                if (this.selectValue.length == 0) {
                    this.$message.warning('请选择要删除的记录！')
                    return
                }
                this.$confirm('此操作将永久删除, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    //数组
                    var idList = []
                    //获取多个复选框对应id，封装到数组里面
                    // [1,2,3]
                    for (var i = 0; i < this.selectValue.length; i++) {
                        var obj = this.selectValue[i]
                        //id值
                        var id = obj.id
                        //放到数组里面
                        idList.push(id)
                    }
                    api.batchRemove(idList).then(response => {
                        //提示
                        this.$message({
                            type: 'success',
                            message: '删除成功!'
                        });
                        //刷新
                        this.fetchData()
                    })
                })
            },
            //弹出添加表单
            add(row) {
                this.dialogVisible = true
                if(this.$refs.zdept){
                    this.$refs.zdept.reset_data()
                }
                this.${modelName2} = {
                    <#list data as var>
                    <#if var.isMul=='true' && var.attrName=='id'>
                    <#list var.mulTableVoList as vl>
                    <#if vl.mod=='checkbox' && vl.isMul=='true'>
                    ${vl.mulSecColum}List: [],
                    </#if>
                    </#list>
                    <#else>
                    <#if var.mod=='checkbox' && var.isMul=='true'>
                    ${var.mulSecColum}List: [],
                    </#if>
                    </#if>
                    </#list>
                }
                <#if genType=='1'>
                this.dialogTitle = '添加下级'
                if (row) {
                    this.${modelName2}.parentId = row.id
                    this.${modelName2}.parentName = row.name
                } else {
                    this.dialogTitle = '添加顶级'
                    this.${modelName2}.parentId = '0'
                }
                </#if>
               <#list data as var>
               <#if var.isMul=='true' && var.attrName=='id'>
               <#list var.mulTableVoList as vl>
               <#if vl.mulSecColum=='deptId'>
                if(this.$refs.eltreeDept){
                    this.$refs.eltreeDept.setCheckedKeys([]);
                }
                </#if>
                </#list>
                </#if>
                </#list>
                <#list requireList as var>
                this.rules.${var.attrName} = [{required: true, message: '请输入${var.remarks}', trigger: 'blur'}]
                </#list>
            },
            //编辑
            edit(id) {
                this.dialogVisible = true
                <#list requireList as var>
                this.rules.${var.attrName} = [{required: true, message: '请输入${var.remarks}', trigger: 'blur'}]
                </#list>
                api.getById(id).then(response => {
                    this.$nextTick(function () {
                        <#list data as var>
                        <#if var.isMul=='true' && var.attrName=='id'>
                        <#list var.mulTableVoList as vl>
                        <#if vl.mulSecColum=='deptId'>
                        if(this.$refs.eltreeDept){
                            this.$refs.eltreeDept.setCheckedKeys([]);
                        }
                        </#if>
                        </#list>
                        </#if>
                        </#list>
                        this.${modelName2} = response.data
                        if(this.$refs.zdept){
                            this.$refs.zdept.reset_data(this.${modelName2}.deptName)
                        }
                        if(this.$refs.eltreeDept){
                            this.$refs.eltreeDept.setCheckedKeys(this.${modelName2}.deptIdList);
                        }
                    })
                })
            },
            //添加或更新
            saveOrUpdate() {
                this.$refs.dataForm.validate(valid => {
                    if (valid) {
                        <#list data as var>
                        <#if var.isMul=='true' && var.attrName=='id' && var.mod=='pop'>
                        <#list var.mulTableVoList as vl>
                        <#if vl.mulSecColum=='deptId'>
                        this.${modelName2}.deptIdList = this.$refs.eltreeDept.getCheckedKeys();
                        </#if>
                        </#list>
                        </#if>
                        </#list>
                        if (!this.${modelName2}.id) {
                            this.save()
                        } else {
                            this.update()
                        }
                    }
                })
            },
            //添加
            save() {
                api.save(this.${modelName2}).then(response => {
                    this.$message.success('操作成功')
                    this.dialogVisible = false
                    this.fetchData(this.page)
                })
            },
            //更新
            update() {
                api.updateById(this.${modelName2}).then(response => {
                    this.$message.success(response.message || '操作成功')
                    this.dialogVisible = false
                    this.fetchData(this.page)
                })
            }
        }
    }
</script>
<style>
    .demo-table-expand {
        font-size: 0;
    }
    .demo-table-expand label {
        width: 90px;
        color: #99a9bf;
    }
    .demo-table-expand .el-form-item {
        margin-right: 0;
        margin-bottom: 0;
        width: 50%;
    }
</style>
