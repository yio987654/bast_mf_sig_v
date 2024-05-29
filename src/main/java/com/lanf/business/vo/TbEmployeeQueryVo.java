package com.lanf.business.vo;

import lombok.Data;
import java.util.Date;
import java.util.List;

/**
* @author generator
* @version 1.0
* @description 员工信息 vo类
* @date 2023-11-30 10:44:29
*/
@Data
public class TbEmployeeQueryVo {
       private String realname;
       private Integer age;
       private java.util.Date bornDate;
       private String deptName;
       private String deptId;
       private List<String> curDeptIds;
       private Date createTimeBegin;
       private Date createTimeEnd;
       private Date updateTimeBegin;
       private Date updateTimeEnd;
}

