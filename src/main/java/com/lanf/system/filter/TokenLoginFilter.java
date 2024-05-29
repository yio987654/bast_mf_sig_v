package com.lanf.system.filter;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lanf.common.helper.JwtHelper;
import com.lanf.common.result.Result;
import com.lanf.common.result.ResultCodeEnum;
import com.lanf.common.utils.HeaderConstant;
import com.lanf.common.utils.IpUtil;
import com.lanf.common.utils.ResponseUtil;
import com.lanf.log.service.SysLoginLogService;
import com.lanf.model.system.SysLoginLog;
import com.lanf.model.vo.LoginVo;
import com.lanf.system.custom.CustomUser;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 登录过滤器，继承UsernamePasswordAuthenticationFilter，对用户名密码进行登录校验
 * </p>
 */
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {
    private RedisTemplate redisTemplate;

    private SysLoginLogService sysLoginLogService;

    public TokenLoginFilter(AuthenticationManager authenticationManager, RedisTemplate redisTemplate, SysLoginLogService sysLoginLogService) {
        this.sysLoginLogService = sysLoginLogService;
        this.redisTemplate = redisTemplate;
        this.redisTemplate.opsForValue().set("normal", "test");
        JwtHelper.createToken("normal", "test");
        this.setAuthenticationManager(authenticationManager);
        this.setPostOnly(false);
        //指定登录接口及提交方式，可以指定任意路径
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/admin/system/index/login", "POST"));
    }

    /**
     * 登录认证
     *
     * @param req
     * @param res
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {
        try {
            LoginVo loginVo = new ObjectMapper().readValue(req.getInputStream(), LoginVo.class);
            Authentication authenticationToken = new UsernamePasswordAuthenticationToken(loginVo.getUsername(), loginVo.getPassword());
            return this.getAuthenticationManager().authenticate(authenticationToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 登录成功
     *
     * @param request
     * @param response
     * @param chain
     * @param auth
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        CustomUser customUser = (CustomUser) auth.getPrincipal();
        String token = JwtHelper.createToken(customUser.getSysUser().getId(), customUser.getSysUser().getUsername());
        //保存权限数据
        redisTemplate.opsForValue().set(customUser.getUsername(), JSON.toJSONString(customUser.getAuthorities()),
                Duration.ofHours(24));
        //保存用户数据redis
        redisTemplate.opsForValue().set(customUser.getSysUser().getId(), JSON.toJSONString(customUser.getSysUser()),
                Duration.ofHours(24));
        Map<String, Object> map = new HashMap<>();
        map.put(HeaderConstant.token, token);
        //保存登录日志
        if (sysLoginLogService != null) {
            SysLoginLog sysLoginLog = new SysLoginLog();
            sysLoginLog.setUsername(customUser.getUsername());
            sysLoginLog.setStatus(1);
            sysLoginLog.setIpaddr(IpUtil.getIpAddress(request));
            sysLoginLog.setMsg("登录成功");
            sysLoginLog.setAccessTime(new Date());
            request.setAttribute(HeaderConstant.token, token);
            sysLoginLogService.save(sysLoginLog);
        }
        ResponseUtil.out(response, Result.ok(map));
    }

    /**
     * 登录失败
     *
     * @param request
     * @param response
     * @param e
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException e) throws IOException, ServletException {

        if (e.getCause() instanceof RuntimeException) {
            ResponseUtil.out(response, Result.build(null, 204, e.getMessage()));
        } else {
            ResponseUtil.out(response, Result.build(null, ResultCodeEnum.LOGIN_MOBLE_ERROR));
        }
    }
}

