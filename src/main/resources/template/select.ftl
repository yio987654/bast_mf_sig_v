<template>
    <div>
        <el-input placeholder="请选择" :size="size" :disabled="inpDisabled" style="line-hight:40px" v-model="name" class="input-with-select">
            <el-button slot="append" :disabled="btnDisabled" @click="showUserSelect" icon="el-icon-search"></el-button>
        </el-input>
        <${modelName}SelectDialog
                ref="${modelName2}Select"
                @doSubmit="selectionsToInput"
                :selectData="selectData"
                :single="single"  :where="where"
        />
    </div>
</template>
<script>
    import ${modelName}SelectDialog from './${modelNameMv2}${modelName}SelectDialog'
    import ${modelName2}Api from '@/api/${pageDir}/${modelName2}'
    export default {
        data () {
            return {
                name: '',
                selectData: [],
                ${modelName2}Service: null
            }
        },
        props: {
            size: {
                type: String,
                default: 'small'
            },
            myvals: {
                type: Array,
                default: () => {
                    return [];
                },
            },
            btnDisabled: {
                type: Boolean,
                default: false
            },
            inpDisabled: {
                type: Boolean,
                default: true
            },
            single: {  // 是否启用单选
                type: Boolean,
                default: false
            },
            where: {  // 查询条件
                type: String,
                default: ''
            }
        },
        components: {
            ${modelName}SelectDialog
        },
        created () {
        },
        watch: {
            myvals: {
                handler (newVal) {
                    this.selectData = []
                    if (newVal && newVal.length > 0) {
                        ${modelName2}Api.getByIds(newVal).then(
                            response => {
                                if (response && response.data) {
                                    this.selectData = response.data
                                }
                            }
                        )
                    }
                },
                immediate: true,
                deep: false
            },
            selectData: {
                handler (newVal) {
                    this.name = newVal.map(item => item.${modValuePro}).join(',')
                },
                immediate: false,
                deep: false
            }
        },
        methods: {
            // 设置选中
            selectionsToInput (selections) {
                this.selectData = selections
                let idList = []
                for (var i = 0; i < this.selectData.length; i++) {
                    var obj = this.selectData[i]
                    //id值
                    var id = obj.${modKey}
                    //放到数组里面
                    idList.push(id)
                }
                this.$emit('getInfo', idList)
            },
            // 显示列表
            showUserSelect () {
                this.$refs.${modelName2}Select.init()
            }
        }
    }
</script>
<style>
    .el-form-item__content .el-input-group {
        vertical-align: middle;
    }
    .el-tag + .el-tag {
        margin-left: 5px;
        margin-bottom: 5px;
    }
</style>
