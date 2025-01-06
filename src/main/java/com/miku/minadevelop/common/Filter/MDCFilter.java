package com.miku.minadevelop.common.Filter;

import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * log4j的MDC配置 把他
 */
@Slf4j
public class MDCFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            // 从请求中获取用户ID（假设它作为请求头传递）
            String userId = request.getHeader("X-UserId");
            if (userId != null) {
                MDC.put("userId", StpUtil.getLoginIdAsString());
            }
            filterChain.doFilter(request, response);
        }catch (Exception e){
            log.error("Error processing request", e);
            throw e; // Rethrow the exception to ensure proper error handling
        }
        finally {
            MDC.clear();
        }
    }
}
