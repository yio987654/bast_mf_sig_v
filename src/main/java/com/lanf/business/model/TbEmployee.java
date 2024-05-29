package com.lanf.business.model;

import com.lanf.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
/**
* @author generator
* @version 1.0
* @description 员工信息 po类
* @date 2023-11-30 10:44:29
*/
@Data
@ApiModel(description = "员工信息")
@TableName("tb_employee")
public class TbEmployee extends BaseEntity {
        private static final long serialVersionUID = 1L;
        @ApiModelProperty(value = "姓名")
        @TableField("realname")
        private String realname;
        @ApiModelProperty(value = "年龄")
        @TableField("age")
        private Integer age;
        @ApiModelProperty(value = "出生日期")
        @TableField("born_date")
        private java.util.Date bornDate;
        @ApiModelProperty(value = "所属部门")
        @TableField("dept_id")
        private String deptId;
        @TableField(exist = false)
        private String deptName;
}
