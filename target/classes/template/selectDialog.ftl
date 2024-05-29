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
                <el-table
                        :data="dataList"
                        v-loading="loading"
                        size="small"
                        stripe
                        border
                        ref="${modelName2}Table"
                        :row-key="getRowKeys"
                        @select="handleSelectionChange"
                        @select-all="handleSelectionChange"
                        @selection-change="propertySelectionChange"
                        style="width: 100%;margin-top: 10px;">
                    <el-table-column
                            type="selection"
                            :reserve-selection="true"
                            header-align="center"
                            align="center"
                            width="50"
                    >
                    </el-table-column>
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
        </el-dialog>
</template>
<script>
    import ${modelName2}Api from '@/api/${pageDir}/${modelName2}'

    export default {
        data() {
            return {
                searchForm: {},
                dataListAllSelections: [], // 所有选中的数据包含跨页数据
                idKey: "id", // 标识列表数据中每一行的唯一键的名称(需要按自己的数据改一下)
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
                type: Array,
                default: () => {
                    return [];
                },
            },
            // 是否启用单选
            single: {
                type: Boolean,
                default: false
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
            getRowKeys(row) {
                return row.${modKey}
            },

            init() {
                this.visible = true;
                this.$nextTick(() => {
                    this.dataListAllSelections = JSON.parse(JSON.stringify(this.selectData));
                    this.$refs.${modelName2}Table.clearSelection()
                    this.resetSearch();
                });
            },
            // 获取数据列表
            refreshList() {
                this.loading = true;
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
                        if (this.selectData && this.selectData.length <= 10000) {
                            //test begin
                            this.$nextTick(function () {
                                let arr = []
                                // this.$refs.studentTable.clearSelection()
                                console.log('selectData')
                                console.log(this.selectData)
                                this.dataList.forEach((item) => {
                                    this.selectData.forEach(val => {  //遍历接口返回的id数组
                                        if (val.${modKey} === item.${modKey}) {
                                            arr.push(item)
                                        }
                                    })
                                })
                                if (arr) {
                                    arr.forEach((row) => {
                                        this.$refs.${modelName2}Table.toggleRowSelection(row, true)
                                    })
                                }
                            })
                            //test end
                        }

                        // 数据加载并绑定成功
                        this.loading = false
                    }
                )
                <#else>
                ${modelName2}Api.getAll${modelName}(this.pageNo, this.pageSize, this.searchForm).then(
                    response => {
                        //this.list = response.data.list
                        this.dataList = response.data
                        if (this.selectData && this.selectData.length <= 10000) {
                            //test begin
                            this.$nextTick(function () {
                                let arr = []
                                // this.$refs.studentTable.clearSelection()
                                console.log('selectData')
                                console.log(this.selectData)
                                this.dataList.forEach((item) => {
                                    this.selectData.forEach(val => {  //遍历接口返回的id数组
                                        if (val.${modKey} === item.${modKey}) {
                                            arr.push(item)
                                        }
                                    })
                                })
                                if (arr) {
                                    arr.forEach((row) => {
                                        this.$refs.${modelName2}Table.toggleRowSelection(row, true)
                                    })
                                }
                            })
                            //test end
                        }
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
            propertySelectionChange(selection) {
                this.dataListAllSelections = selection
            },
            // 手动选中数据事件
            handleSelectionChange(selection, row) {
                if (row) {
                    if (this.selectData && this.selectData.length <= 10000) {
                        this.$nextTick(function () {
                            let selected = selection.length && selection.indexOf(row) !== -1;
                            console.log('selected=' + selected);
                            if (!selected) { //取消选中把默认值移除掉
                                this.selectData.forEach((val, index) => {
                                    if (val.${modKey} == row.${modKey}) {
                                        this.selectData[index] = {${modKey}: ''};
                                    }
                                })

                            }
                        })
                    }
                } else {
                    let isCheckAll = this.${r'$'}refs.${modelName2}Table.store.states.isAllSelected;
                    //反选的时候 移除默认
                    if (isCheckAll == false) {
                        this.$nextTick(function () {
                            if (this.selectData && this.selectData.length <= 10000) {
                                this.dataList.forEach((item) => {
                                    this.selectData.forEach((val, index) => {
                                        if (val.${modKey} == item.${modKey}) {
                                            this.selectData[index] = {${modKey}: ''};
                                            console.log("has removed " + val.${modValue})
                                        }
                                    })
                                })
                            }
                        })
                    } else {
                        console.log('点击了全选')
                    }
                }
            },
            doSubmit() {
                this.visible = false;
                //翻页的问题 反选的时候存在默认值不能保留的问题
                if (this.selectData && this.selectData.length <= 10000) {
                    this.$nextTick(function(){
                        console.log('last selectData')
                        console.log(this.selectData)
                        this.selectData.forEach((item) => {
                            let isIn = false;
                            this.dataListAllSelections.forEach((val, index) => {
                                if (val.${modKey} == item.${modKey}) {
                                    isIn = true;
                                }
                            })
                            if(isIn == false && item.${modKey}!=''){
                                this.dataListAllSelections.push(item);
                                console.log("has push")
                                console.log(item);
                            }
                        })
                        this.$emit("doSubmit", this.dataListAllSelections);
                    })
                }
            },
        },
    };
</script>
<style lang="scss">
</style>
