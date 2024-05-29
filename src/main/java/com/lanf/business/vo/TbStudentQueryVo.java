package com.lanf.business.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 学生 vo类
 * @date 2023-11-24 00:02:09
 */
@Data
public class TbStudentQueryVo {
    private String name;
    private String classId;
    private String className;
    private String areaId;
    private String areaName;
    private String treeId;
    private String treeName;
    private String deptId;
    private List<String> curDeptIds;
    private String treesId;
    private String courseId;
    private String teacherId;
    private String deptName;
    private Date createTimeBegin;
    private Date createTimeEnd;
    private Date updateTimeBegin;
    private Date updateTimeEnd;
}

