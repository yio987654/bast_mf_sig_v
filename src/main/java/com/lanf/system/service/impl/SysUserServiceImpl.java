package com.lanf.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanf.common.result.ResultCodeEnum;
import com.lanf.common.utils.MD5;
import com.lanf.model.system.SysDept;
import com.lanf.model.system.SysUser;
import com.lanf.model.vo.RouterVo;
import com.lanf.model.vo.SysUserQueryVo;
import com.lanf.system.easyexcel.ExcelHandler;
import com.lanf.system.exception.LanfException;
import com.lanf.system.mapper.SysUserMapper;
import com.lanf.system.model.SysPost;
import com.lanf.system.model.SysUserDept;
import com.lanf.system.service.*;
import com.lanf.system.utils.UserUtil;
import com.lanf.system.vo.UserExcelVo;
import com.lanf.system.vo.UserExportVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.function.Function;

@Transactional
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysUserDeptService sysUserDeptService;

    @Autowired
    private FileService fileService;

    @Autowired
    private SysDeptService sysDeptService;

    @Autowired
    private SysPostService sysPostService;

    @Resource
    private ExcelHandler excelHandler;

    @Override
    public IPage<SysUser> selectPage(Page<SysUser> pageParam, SysUserQueryVo userQueryVo) {
        //只查询当前登录所属部门数据
        SysUser sysUser = UserUtil.getUserInfo();
        if ("1".equals(sysUser.getId())) {
            userQueryVo.setCurDeptIds(null);
        } else {
            if (CollectionUtils.isEmpty(sysUser.getDeptIds())) {
                return null;
            }
            userQueryVo.setCurDeptIds(sysUser.getDeptIds());
        }
        return sysUserMapper.selectPage(pageParam, userQueryVo);
    }

    @Override
    public void updateStatus(String id, Integer status) {
        SysUser sysUser = sysUserMapper.selectById(id);
        sysUser.setStatus(status);
        sysUserMapper.updateById(sysUser);
    }

    @Override
    public SysUser getByUsername(String username) {
        SysUser sysUser = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("username", username));
        QueryWrapper<SysUserDept> queryWrapper = new QueryWrapper<SysUserDept>();
        queryWrapper.select("dept_id");
        queryWrapper.eq("user_id", sysUser.getId());
        Function<Object, String> f = (o -> o.toString());
        List<String> deptIds = sysUserDeptService.listObjs(queryWrapper, f);
        if (deptIds == null) {
            deptIds = new ArrayList<String>();
        }
        sysUser.setDeptIds(deptIds);
        return sysUser;
    }

    @Override
    public boolean save(SysUser sysUser) {
        MultipartFile file = sysUser.getFile();
        String filePath = null;
        if (file != null) {
            try {
                filePath = fileService.upload(file);
            } catch (Exception e) {
                e.printStackTrace();
                throw new LanfException(ResultCodeEnum.UPLOAD_ERROR);
            }
        }
        if (filePath != null) {
            sysUser.setHeadUrl(filePath);
        }
        int result = this.sysUserMapper.insert(sysUser);
        List<String> deptIds = sysUser.getDeptIds();
        if (deptIds != null && deptIds.size() > 0) {
            for (String deptId : deptIds) {
                SysUserDept sysUserDept = new SysUserDept();
                sysUserDept.setUserId(sysUser.getId());
                sysUserDept.setDeptId(deptId);
                sysUserDeptService.save(sysUserDept);
            }
        }
        return result > 0;
    }

    @Override
    public boolean updateById(SysUser sysUser) {
        MultipartFile file = sysUser.getFile();
        String filePath = null;
        if (file != null) {
            try {
                filePath = fileService.upload(file);
            } catch (Exception e) {
                e.printStackTrace();
                throw new LanfException(ResultCodeEnum.UPLOAD_ERROR);
            }
        }
        if (filePath != null) {
            sysUser.setHeadUrl(filePath);
        }
        int row = this.sysUserMapper.updateById(sysUser);
        List<String> deptIds = sysUser.getDeptIds();
        QueryWrapper<SysUserDept> queryWrapper = new QueryWrapper<SysUserDept>();
        queryWrapper.eq("user_id", sysUser.getId());
        sysUserDeptService.remove(queryWrapper);
        if (deptIds != null && deptIds.size() > 0) {
            for (String deptId : deptIds) {
                SysUserDept sysUserDept = new SysUserDept();
                sysUserDept.setUserId(sysUser.getId());
                sysUserDept.setDeptId(deptId);
                sysUserDeptService.save(sysUserDept);
            }
        }
        return row > 0;
    }

    @Override
    public SysUser getById(String id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        QueryWrapper<SysUserDept> studentInterQueryWrapper = new QueryWrapper<SysUserDept>();
        studentInterQueryWrapper.select("dept_id");
        studentInterQueryWrapper.eq("user_id", sysUser.getId());
        Function<Object, String> f = (o -> o.toString());
        List<String> deptIds = sysUserDeptService.listObjs(studentInterQueryWrapper, f);
        if (deptIds == null) {
            deptIds = new ArrayList<String>();
        }
        sysUser.setDeptIds(deptIds);
        return sysUser;
    }

    @Override
    public Map<String, Object> getUserInfo(String username) {
        Map<String, Object> result = new HashMap<>();
        SysUser sysUser = this.getByUsername(username);
        //根据用户id获取菜单权限值
        List<RouterVo> routerVoList = sysMenuService.findUserMenuList(sysUser.getId());
        //根据用户id获取用户按钮权限
        List<String> permsList = sysMenuService.findUserPermsList(sysUser.getId());

        result.put("name", sysUser.getName());
        result.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        result.put("roles", new HashSet<>());
        result.put("buttons", permsList);
        result.put("routers", routerVoList);
        return result;
    }


    @Override
    public void importData(MultipartFile multipartFile) {
        List<SysUser> saveUserList = new ArrayList<>();
        List<SysUserDept> saveUserDeptList = new ArrayList<>();
        List<String> checkUserName = new ArrayList<>();
        excelHandler.checkFile(multipartFile);
        try {
            List<Object> plist = new ArrayList<>();
            plist.add(new UserExcelVo());
            plist.add(new UserExcelVo());
            plist.add(new UserExcelVo());
            List<List<UserExcelVo>> voList = excelHandler.importExcels(multipartFile, 3, plist);
            if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(voList)) {
                voList.forEach(vo -> {
                    vo.forEach(v -> {
                        SysUser sysUser = new SysUser();
                        BeanUtils.copyProperties(v, sysUser);
                        String pwd = MD5.encrypt("111111");
                        String deptName = v.getDeptName();
                        if (!StringUtils.isEmpty(deptName)) {
                            QueryWrapper deptWrapper = new QueryWrapper();
                            deptWrapper.eq("name", deptName);
                            SysDept sysDept = sysDeptService.getOne(deptWrapper);
                            if (sysDept != null) {
                                SysUserDept sysUserDept = new SysUserDept();
                                sysUserDept.setDeptId(sysDept.getId());
                                sysUserDept.setUserId(sysUser.getId());
                                sysUserDept.setUserName(sysUser.getUsername());
                                saveUserDeptList.add(sysUserDept);
                            }
                        }
                        String postName = v.getPostName();
                        if (!StringUtils.isEmpty(postName)) {
                            QueryWrapper postWrapper = new QueryWrapper();
                            postWrapper.eq("name", postName);
                            SysPost sysPost = sysPostService.getOne(postWrapper);
                            if (sysPost != null) {
                                sysUser.setPostId(sysPost.getId());
                            }
                        }
                        sysUser.setPassword(pwd);
                        sysUser.setStatus(1);
                        if (!checkUserName.contains(sysUser.getUsername())) {
                            checkUserName.add(sysUser.getUsername());
                        } else {
                            throw new LanfException(5240, "用户名不能重复");
                        }
                        saveUserList.add(sysUser);
                    });
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new LanfException(9004, "导入失败");
        }
        if (!CollectionUtils.isEmpty(saveUserList)) {
            System.out.println("saveUserList===" + Math.random() + "MM" + saveUserList.size());
            this.saveBatch(saveUserList);
        }
        if (!CollectionUtils.isEmpty(saveUserDeptList)) {
            for (SysUserDept userDept : saveUserDeptList) {
                QueryWrapper userWrapper = new QueryWrapper();
                userWrapper.eq("username", userDept.getUserName());
                SysUser sysUser = this.getOne(userWrapper);
                userDept.setUserId(sysUser.getId());
            }
            sysUserDeptService.saveBatch(saveUserDeptList);
        }
    }

    @Override
    public void exportData(HttpServletResponse response) {
        List<SysUser> list = this.selectPage(new Page<SysUser>(1, 10000), new SysUserQueryVo()).getRecords();
        List<UserExportVo> expList = new ArrayList<>();
        list.forEach(po -> {
            UserExportVo vo = new UserExportVo();
            BeanUtils.copyProperties(po, vo);
            expList.add(vo);
        });
        try {
            this.excelHandler.exportExcel(response, expList, UserExportVo.class, "用户数据", "用户数据");
        } catch (Exception e) {
            e.printStackTrace();
            throw new LanfException(9005, "导出失败");
        }
    }

    @Override
    public List<SysUser> queryList(SysUserQueryVo userQueryVo) {
        SysUser sysUser = UserUtil.getUserInfo();
        if ("1".equals(sysUser.getId())) {
            userQueryVo.setCurDeptIds(null);
        } else {
            if (CollectionUtils.isEmpty(sysUser.getDeptIds())) {
                return null;
            }
            userQueryVo.setCurDeptIds(sysUser.getDeptIds());
        }
        return sysUserMapper.queryList(userQueryVo);
    }
}
