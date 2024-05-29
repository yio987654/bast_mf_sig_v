//
//
package com.lanf.model.vo;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 用户查询实体
 * </p>
 */
@Data
public class SysUserQueryVo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String keyword;
	private String username;
	private String name;
	private String phone;
	private String createTimeBegin;
	private String createTimeEnd;
	private String roleId;
	private String postId;
	private String deptId;
	private List<String> curDeptIds;


}

