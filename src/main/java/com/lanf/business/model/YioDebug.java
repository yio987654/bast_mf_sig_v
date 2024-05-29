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
* @description yio测试 po类
* @date 2024-05-09 23:38:22
*/
@Data
@ApiModel(description = "yio测试")
@TableName("yio_debug")
public class YioDebug extends BaseEntity {
        private static final long serialVersionUID = 1L;
        @ApiModelProperty(value = "名称")
        @TableField("name")
        private String name;
        @ApiModelProperty(value = "邮箱")
        @TableField("mail")
        private String mail;
}
