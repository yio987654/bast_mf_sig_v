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
* @author tanlingfei
* @version 1.0
* @description 测试表2 po类
* @date 2024-05-09 23:57:31
*/
@Data
@ApiModel(description = "测试表2")
@TableName("test02")
public class Test02 extends BaseEntity {
        private static final long serialVersionUID = 1L;
        @ApiModelProperty(value = "姓名")
        @TableField("name")
        private String name;
        @ApiModelProperty(value = "年纪")
        @TableField("age")
        private Integer age;
}
