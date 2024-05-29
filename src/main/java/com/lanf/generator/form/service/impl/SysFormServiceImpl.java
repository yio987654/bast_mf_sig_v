package com.lanf.generator.form.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanf.common.result.ResultCodeEnum;
import com.lanf.generator.form.mapper.SysFormMapper;
import com.lanf.generator.form.model.SysForm;
import com.lanf.generator.form.model.SysFormItem;
import com.lanf.generator.form.service.SysFormItemService;
import com.lanf.generator.form.service.SysFormService;
import com.lanf.generator.form.vo.SysFormItemQueryVo;
import com.lanf.generator.form.vo.SysFormQueryVo;
import com.lanf.model.system.SysUser;
import com.lanf.system.exception.LanfException;
import com.lanf.system.utils.UserUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 表单 Service实现类
 * @date 2023-10-06 22:40:26
 */
@Transactional
@Service
public class SysFormServiceImpl extends ServiceImpl<SysFormMapper, SysForm> implements SysFormService {
    @Autowired
    private SysFormMapper sysFormMapper;

    @Autowired
    private SysFormItemService sysFormItemService;

    @Override
    public IPage<SysForm> selectPage(Page<SysForm> pageParam, SysFormQueryVo sysFormQueryVo) {
        //QueryWrapper<SysForm> queryWrapper = new QueryWrapper<>();
        //return sysFormMapper.selectPage(pageParam,queryWrapper);
        //只查询当前登录所属部门数据
        SysUser sysUser = UserUtil.getUserInfo();
        if ("1".equals(sysUser.getId())) {
            sysFormQueryVo.setCurDeptIds(null);
        } else {
            if (CollectionUtils.isEmpty(sysUser.getDeptIds())) {
                return null;
            }
            sysFormQueryVo.setCurDeptIds(sysUser.getDeptIds());
        }
        return sysFormMapper.selectPage(pageParam, sysFormQueryVo);
    }

    @Override
    public List<SysForm> queryList(SysFormQueryVo sysFormQueryVo) {
        //只查询当前登录所属部门数据
        SysUser sysUser = UserUtil.getUserInfo();
        if ("1".equals(sysUser.getId())) {
            sysFormQueryVo.setCurDeptIds(null);
        } else {
            if (CollectionUtils.isEmpty(sysUser.getDeptIds())) {
                return null;
            }
            sysFormQueryVo.setCurDeptIds(sysUser.getDeptIds());
        }
        List<SysForm> result = sysFormMapper.queryList(sysFormQueryVo);
        return result;
    }


    @Override
    public boolean save(SysForm sysForm) {
        if ("true".equals(sysForm.getIsFause())) {
            QueryWrapper<SysForm> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("name", sysForm.getName());
            int row = this.sysFormMapper.delete(queryWrapper);
        } else {
            SysFormQueryVo sysFormQueryVo = new SysFormQueryVo();
            sysFormQueryVo.setName(sysForm.getName());
            List list = this.sysFormMapper.queryList(sysFormQueryVo);
            if (list != null && list.size() > 0) {
                throw new LanfException(5240, "表名(" + sysForm.getName() + ")在数据库中已存在");
            }
        }
        int result = this.sysFormMapper.insert(sysForm);
        List<SysFormItem> itemList = sysForm.getItemList();
        List<SysFormItem> saveList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(itemList)) {
            for (SysFormItem vo : itemList) {
                if (StringUtils.isEmpty(vo.getName()) || StringUtils.isEmpty(vo.getDescription()) || StringUtils.isEmpty(vo.getItemType()) || StringUtils.isEmpty(vo.getIsAllowNull())) {
                    throw new LanfException(5240, "字段信息必须填写完整");
                }
                SysFormItem item = new SysFormItem();
                BeanUtils.copyProperties(vo, item, "id", "createTime", "updateTime", "isDeleted", "version");
                item.setFormId(sysForm.getId());
                saveList.add(item);
            }
            if (!CollectionUtils.isEmpty(saveList)) {
                sysFormItemService.saveBatch(saveList);
            }
        }
        List<String> passTbList = new ArrayList();
        passTbList.add("sys_dept");
        passTbList.add("sys_dic");
        passTbList.add("sys_dic_item");
        passTbList.add("sys_form");
        passTbList.add("sys_form_item");
        passTbList.add("sys_login_log");
        passTbList.add("sys_menu");
        passTbList.add("sys_oper_log");
        passTbList.add("sys_post");
        passTbList.add("sys_role");
        passTbList.add("sys_role_menu");
        passTbList.add("sys_user");
        passTbList.add("sys_user_dept");
        passTbList.add("sys_user_role");
        //框架基础表不做改动
        if (passTbList.contains(sysForm.getName().toLowerCase())) {
            return result > 0;
        }
        try {
            this.createTableByForm(sysForm);
        } catch (Exception e) {
            Throwable cause = e.getCause();
            String message = cause.getMessage();
            String cq = "Table '" + sysForm.getName() + "' already exists";
            if (cq.equals(message)) {
                throw new LanfException(5240, "表(" + sysForm.getName() + ")在数据库中已存在");
            }
        }
        return result > 0;
    }

    @Override
    public boolean updateById(SysForm sysForm) {
        int row = this.sysFormMapper.updateById(sysForm);
        if (row <= 0) {
            throw new LanfException(ResultCodeEnum.UPDATE_ERROR);
        }
        QueryWrapper<SysFormItem> tb = new QueryWrapper<SysFormItem>();
        tb.eq("form_id", sysForm.getId());
        sysFormItemService.remove(tb);
        List<SysFormItem> itemList = sysForm.getItemList();
        List<SysFormItem> saveList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(itemList)) {
            for (SysFormItem vo : itemList) {
                if (StringUtils.isEmpty(vo.getName()) || StringUtils.isEmpty(vo.getDescription()) || StringUtils.isEmpty(vo.getItemType()) || StringUtils.isEmpty(vo.getIsAllowNull())) {
                    throw new LanfException(5240, "字段信息必须填写完整");
                }
                SysFormItem item = new SysFormItem();
                BeanUtils.copyProperties(vo, item, "id", "createTime", "updateTime", "isDeleted", "version");
                item.setFormId(sysForm.getId());
                saveList.add(item);
            }
            if (!CollectionUtils.isEmpty(saveList)) {
                sysFormItemService.saveBatch(saveList);
            }
        }
        List<String> passTbList = new ArrayList();
        passTbList.add("sys_dept");
        passTbList.add("sys_dic");
        passTbList.add("sys_dic_item");
        passTbList.add("sys_form");
        passTbList.add("sys_form_item");
        passTbList.add("sys_login_log");
        passTbList.add("sys_menu");
        passTbList.add("sys_oper_log");
        passTbList.add("sys_post");
        passTbList.add("sys_role");
        passTbList.add("sys_role_menu");
        passTbList.add("sys_user");
        passTbList.add("sys_user_dept");
        passTbList.add("sys_user_role");
        //框架基础表不做改动
        if (passTbList.contains(sysForm.getName().toLowerCase())) {
            return row > 0;
        }
        try {
            sysForm.setIsFause("true");
            this.createTableByForm(sysForm);
        } catch (Exception e) {
            Throwable cause = e.getCause();
            String message = cause.getMessage();
            String cq = "Table '" + sysForm.getName() + "' already exists";
            if (cq.equals(message)) {
                throw new LanfException(5240, "表(" + sysForm.getName() + ")在数据库中已存在");
            }
        }
        return row > 0;
    }

    @Override
    public SysForm getById(String id) {
        SysForm sysForm = sysFormMapper.selectById(id);
        SysFormItemQueryVo queryVo = new SysFormItemQueryVo();
        queryVo.setFormId(id);
        List<SysFormItem> itemList = sysFormItemService.queryList(queryVo);
        sysForm.setItemList(itemList);
        return sysForm;
    }

    @Override
    public List<SysForm> getByIds(List<String> ids) {
        List<SysForm> list = this.sysFormMapper.selectBatchIds(ids);
        return list;
    }

    public void createTableByForm(SysForm sysForm) {
        StringBuilder ddl = new StringBuilder();
        if ("true".equals(sysForm.getIsFause())) {
            StringBuilder dropDl = new StringBuilder();
            dropDl.append("DROP TABLE IF EXISTS `");
            dropDl.append(sysForm.getName());
            dropDl.append("`;");
            this.sysFormMapper.createTableByForm(dropDl.toString());
        }
        ddl.append("CREATE TABLE `");
        ddl.append(sysForm.getName());
        ddl.append("`  (");
        ddl.append("`id` varchar(100) CHARACTER SET  utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,");
        List<SysFormItem> itemList = sysForm.getItemList();
        if (!CollectionUtils.isEmpty(itemList)) {
            for (SysFormItem item : itemList) {
                if ("id".equals(item.getName().toLowerCase())) {
                    continue;
                }
                ddl.append("`");
                ddl.append(item.getName());
                ddl.append("`");
                ddl.append(" ");
                ddl.append(item.getItemType());
                ddl.append(" ");
                if ("1".equals(item.getIsAllowNull())) {
                    ddl.append("NOT NULL");
                } else {
                    ddl.append("NULL");
                }
                ddl.append(" ");
                if (!StringUtils.isEmpty(item.getDescription())) {
                    ddl.append("COMMENT '");
                    ddl.append(item.getDescription());
                    ddl.append("'");
                }
                ddl.append(",");
            }
        }
        if ("1".equals(sysForm.getTabType())) {
            ddl.append("`parent_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '上级',");
            ddl.append("`level` tinyint NULL DEFAULT NULL,");
            ddl.append("`tree_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,");
            ddl.append("`sort_value` int NOT NULL COMMENT '排序',");
        }
        ddl.append("`create_time` timestamp NOT NULL COMMENT '创建时间',");
        ddl.append("`update_time` timestamp NOT NULL COMMENT '修改时间',");
        ddl.append("`is_deleted` tinyint NOT NULL,");
        ddl.append("`version` bigint NOT NULL DEFAULT 0 COMMENT '版本号',");
        ddl.append("PRIMARY KEY (`id`) USING BTREE");
        ddl.append(") ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '");
        ddl.append(sysForm.getDescription());
        ddl.append("' ROW_FORMAT = DYNAMIC;");
        System.out.println("ddl====" + ddl.toString());
        this.sysFormMapper.createTableByForm(ddl.toString());
    }
}
