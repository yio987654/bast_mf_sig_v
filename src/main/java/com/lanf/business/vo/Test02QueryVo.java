package com.lanf.business.vo;

import lombok.Data;
import java.util.Date;
import java.util.List;

/**
* @author tanlingfei
* @version 1.0
* @description 测试表2 vo类
* @date 2024-05-09 23:57:31
*/
@Data
public class Test02QueryVo {
       private String name;
       private Integer age;
       private Date createTimeBegin;
       private Date createTimeEnd;
       private Date updateTimeBegin;
       private Date updateTimeEnd;
}

