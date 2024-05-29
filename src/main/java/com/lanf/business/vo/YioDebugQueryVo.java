package com.lanf.business.vo;

import lombok.Data;
import java.util.Date;
import java.util.List;

/**
* @author tanlingfei
* @version 1.0
* @description yio测试 vo类
* @date 2024-05-09 23:38:22
*/
@Data
public class YioDebugQueryVo {
       private String name;
       private String mail;
       private Date createTimeBegin;
       private Date createTimeEnd;
       private Date updateTimeBegin;
       private Date updateTimeEnd;
}

