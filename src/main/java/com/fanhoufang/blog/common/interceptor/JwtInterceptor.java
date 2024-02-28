package com.fanhoufang.blog.common.interceptor;

import com.fanhoufang.blog.common.constant.CommonStatusCode;
import com.fanhoufang.blog.common.exception.ServiceException;
import com.fanhoufang.blog.common.lang.UserThreadLocal;
import com.fanhoufang.blog.entity.SysUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;


@Slf4j
public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response,@NonNull Object handler) {
        //从http请求头获取token
        String username = request.getHeader("username");
        String token = request.getHeader("token");
        String authorization = request.getHeader("authorization");
        String userId = request.getHeader("user-id");
        String certNo = request.getHeader("certNo");
        String code = request.getHeader("code");
        if (StringUtils.isEmpty(authorization) || StringUtils.isEmpty(userId)) {
            throw new ServiceException(CommonStatusCode._2002);
        }
        //如果验证成功放行请求
        SysUser user = SysUser.builder()
                .username(username)
                .token(token)
                .authorization(authorization)
                .userId(userId)
                .certNo(certNo)
                .code(code).build();
        UserThreadLocal.put(user);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //如果不删 ThreadLocal中用完的信息会有内存泄漏的风险
        UserThreadLocal.remove();
    }
}