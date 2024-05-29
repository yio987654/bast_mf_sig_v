    import request from '@/utils/request'
    const api_name = '/${packageSub}/${modelName2}'
    export default {
        getPageList(page, limit, searchObj) {
            return request({
            url: `${r'${api_name}'}/${r'${page}'}/${r'${limit}'}`,
            method: 'get',
            params: searchObj // url查询字符串或表单键值对
            })
        },
        getAll${modelName}(searchObj) {
            return request({
            url: `${r'${api_name}'}/list`,
            method: 'get',
            params: searchObj
        })
        },
        getById(id) {
            return request({
            url: `${r'${api_name}'}/get/${r'${id}'}`,
            method: 'get'
            })
        },
        getByIds(ids) {
            return request({
            url: `${r'${api_name}'}/getByIds`,
            method: 'post',
            data:ids
            })
        },
        save(data) {
            return request({
            url: `${r'${api_name}'}/save`,
            method: 'post',
            data: data
            })
        },
        updateById(data) {
            return request({
            url: `${r'${api_name}'}/update`,
            method: 'put',
            data: data
            })
        },
        removeById(id) {
            return request({
            url: `${r'${api_name}'}/remove/${r'${id}'}`,
            method: 'delete'
            })
        },
        //批量删除
        batchRemove(idList) {
            return request({
            url: `${r'${api_name}'}/batchRemove`,
            method: 'delete',
            data: idList
            })
        },
        updateStatus(id, status) {
            return request({
            url: `${r'${api_name}'}/updateStatus/${r'${id}'}/${r'${status}'}`,
            method: 'get'
            })
        }
    }