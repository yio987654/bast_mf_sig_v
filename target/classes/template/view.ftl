<template>
    <div>
        <el-button type="primary"  size="small" @click="showUserSelect">查看</el-button>
        <${modelName}ViewDialog
                ref="${modelName2}ViewDialog"
                :${mulMainColum}="${mulMainColum}"
        />
    </div>
</template>
<script>
    import ${modelName}ViewDialog from './${modelNameMv2}${modelName}ViewDialog'
    export default {
        props: {
            ${mulMainColum}: {
                type: String,
                default: ''
            }
        },
        components: {
            ${modelName}ViewDialog
        },
        methods: {
            // 显示列表
            showUserSelect () {
                this.$refs.${modelName2}ViewDialog.init()
            }
        }
    }
</script>
<style>

</style>
