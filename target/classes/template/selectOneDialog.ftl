<template>
        <el-dialog
                title="${modRemark}"
                width="1000px"
                :close-on-click-modal="false"
                :append-to-body="true"
                v-dialogDrag
                :visible.sync="visible">
           <span slot="title" class="dialog-footer">
          <el-button
                  size="small"
                  @click="visible = false"
                  icon="el-icon-circle-close"
          >关闭</el-button>
          <el-button
                  size="small"
                  type="primary"
                  icon="el-icon-circle-check"
                  @click="doSubmit()"
          >确定</el-button>
        </span>
                <div class="search-div" style="height: 55px;margin-top: -25px">
                    <el-form
                            size="small"
                            ref="searchForm"
                            :model="searchForm">
                        <el-row>
                            <#list data as var>
                                <#if (var.dataType=='char' || var.dataType=='varchar' || var.dataType=='text' || var.dataType=='tinytext') && var.attrName!='id' && !var.attrName?contains("Id")  >
                                    <el-col :span="5" style="margin-left: 5px">
                                        <el-form-item prop="${var.attrName}">
                                            <el-input
                                                    size="small"
                                                    v-model="searchForm.${var.attrName}"
                                                    placeholder="${var.remarks}"
                                                    @input="changeInput($event)"
                                                    clearable>
                                            </el-input>
                                        </el-form-item>
                                    </el-col>
                                </#if>
                            </#list>
                            <el-col :span="5" style="margin-left: 5px">
                                <el-form-item>
                                    <el-button
                                            type="primary"
                                            @click="refreshList()"
                                            size="small"
                                            icon="el-icon-search">搜索
                                    </el-button>
                                    <el-button
                                            @click="resetSearch()"
                                            size="small"
                                            icon="el-icon-refresh-right">重置
                                    </el-button>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </el-form>
                </div>
            <#if modTabType=='0'>
                <el-table
                        :data="dataList"
                        v-loading="loading"
                        size="small"
                        border
                        ref="${modelName2}Table"
                        highlight-current-row
                        @current-change="propertySelectionChange"
                        style="width: 100%;margin-top: 10px;">
            <#else>
                    <el-table
                            :data="dataList"
                            style="width: 100%;margin-top: 10px;"
                            row-key="id"
                            border
                            ref="${modelName2}Table"
                            highlight-current-row
                            :default-expand-all="false"
                            @current-change="propertySelectionChange"
                            :tree-props="{children: 'children'}">
            </#if>
                    <#list data as var>
                        <#if var.attrName!='isDeleted' && var.attrName!='id'>
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
                    </#list>
                </el-table>
            <#if modTabType=='0'>
                <el-pagination
                        @size-change="sizeChangeHandle"
                        @current-change="currentChangeHandle"
                        :current-page="pageNo"
                        :page-sizes="[5, 10, 50, 100]"
                        :page-size="pageSize"
                        :total="total"
                        style="padding: 30px 0; text-align: center;"
                        layout="total, sizes, prev, pager, next, jumper"
                >
                </el-pagination>
              </#if>
        </el-dialog>
</template>
<script>
    import ${modelName2}Api from '@/api/${pageDir}/${modelName2}'

    export default {
        data() {
            return {
                searchForm: {},
                dataListAllSelections: {}, // 所有选中的数据包含跨页数据
                dataList: [],
                pageNo: 1,
                pageSize: 5,
                total: 0,
                loading: false,
                visible: false,
            };
        },
        props: {
            selectData: {
                type: Object,
                default: () => {
                    return {};
                },
            },

            where: {
                type: String,
                default: ''
            }
        },
        methods: {
            changeInput() {
                this.$forceUpdate();
            },

            init() {
                this.visible = true;
                this.$nextTick(() => {
                    this.dataListAllSelections = this.selectData;
                    this.$refs.${modelName2}Table.clearSelection()
                    this.resetSearch();
                });
            },
            // 获取数据列表
            refreshList() {
                if(this.where != null && this.where != ''){
                    let queryObj = JSON.parse(this.where)
                   this.searchForm = Object.assign(queryObj,this.searchForm)
                }
                <#if modTabType=='0'>
                ${modelName2}Api.getPageList(this.pageNo, this.pageSize, this.searchForm).then(
                    response => {
                        //this.list = response.data.list
                        this.dataList = response.data.records
                        this.total = response.data.total
                        // 数据加载并绑定成功
                        this.loading = false
                    }
                )
                <#else>
                ${modelName2}Api.getAll${modelName}(this.pageNo, this.pageSize, this.searchForm).then(
                    response => {
                        //this.list = response.data.list
                        this.dataList = response.data
                        // 数据加载并绑定成功
                        this.loading = false
                    }
                )
                </#if>
            },
            // 每页数
            sizeChangeHandle(val) {
                this.pageSize = val;
                this.pageNo = 1;
                this.refreshList();
            },
            // 当前页
            currentChangeHandle(val) {
                this.pageNo = val;
                this.refreshList();
            },
            // 排序
            resetSearch() {
                this.$refs.searchForm.resetFields();
                this.refreshList();
            },
            //监听选中
            propertySelectionChange(newVal,oldVal) {
                this.dataListAllSelections = newVal
            },
            doSubmit() {
                this.visible = false;
                this.$emit("doSubmit", this.dataListAllSelections);
            },
        },
    };
</script>
<style lang="scss">
</style>
