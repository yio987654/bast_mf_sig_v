package com.lanf.system.filter;

import com.alibaba.fastjson.JSON;
import com.lanf.common.exception.CacheExpiredException;
import com.lanf.common.helper.JwtHelper;
import com.lanf.common.result.Result;
import com.lanf.common.result.ResultCodeEnum;
import com.lanf.common.utils.HeaderConstant;
import com.lanf.common.utils.ResponseUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 认证解析过滤器
 * @date 2023/2/27 10:23
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private RedisTemplate redisTemplate;

    public TokenAuthenticationFilter(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        logger.info("uri:" + request.getRequestURI());
        //如果是登录接口，直接放行
        if ("/api/admin/system/index/login".equals(request.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = null;
        try {
            authentication = getAuthentication(request);
        } catch (ExpiredJwtException e) {
            ResponseUtil.out(response, Result.build(null, ResultCodeEnum.TOKENEXPIRED));
            return;
        } catch (CacheExpiredException e) {
            ResponseUtil.out(response, Result.build(null, ResultCodeEnum.TOKENEXPIRED));
            return;
        }
        if (null != authentication) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        } else {
            ResponseUtil.out(response, Result.build(null, ResultCodeEnum.PERMISSION));
        }
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        // token置于header里
        String token = request.getHeader(HeaderConstant.token);
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter(HeaderConstant.token);
        }
        logger.info("token:" + token);
        if (!StringUtils.isEmpty(token)) {
            String useruame = JwtHelper.getUsername(token);
            logger.info("useruame:" + useruame);
            if (!StringUtils.isEmpty(useruame)) {
                String authoritiesString = (String) redisTemplate.opsForValue().get(useruame);
                if (StringUtils.isEmpty(authoritiesString)) {
                    throw new CacheExpiredException(ResultCodeEnum.TOKENEXPIRED);
                }
                List<Map> mapList = JSON.parseArray(authoritiesString, Map.class);
                List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                for (Map map : mapList) {
                    authorities.add(new SimpleGrantedAuthority((String) map.get("authority")));
                }
                return new UsernamePasswordAuthenticationToken(useruame, null, authorities);
            }
        }
        return null;
    }
}
