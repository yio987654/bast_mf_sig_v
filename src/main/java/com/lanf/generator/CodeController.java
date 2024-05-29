package com.lanf.generator;

import com.lanf.common.result.Result;
import com.lanf.log.annotation.Log;
import com.lanf.log.type.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tanlingfei
 * @version 1.0
 * @description TODO
 * @date 2023/5/29 21:54
 */
@RestController
@RequestMapping("/code/generator")
public class CodeController {
    @Autowired
    private DataSourceProperties dataSourceProperties;

    @PostMapping("/save")
    @Log(title = "代码生成", businessType = BusinessType.INSERT)
    @PreAuthorize("hasAuthority('bnt.code.generator.add')")
    public Result save(@RequestBody CodeVo codeVo) {
        codeVo.setConnectionURL(dataSourceProperties.getUrl());
        codeVo.setDriverName(dataSourceProperties.getDriverClassName());
        codeVo.setUsername(dataSourceProperties.getUsername());
        codeVo.setPassword(dataSourceProperties.getPassword());
        Generator.generatOne(codeVo);
        return Result.ok();
    }
}
