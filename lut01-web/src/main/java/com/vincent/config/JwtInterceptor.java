package com.vincent.config;

import cn.hutool.core.util.ObjectUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.vincent.common.Constants;
import com.vincent.enums.ResultCodeEnum;
import com.vincent.exception.CustomException;
import com.vincent.user.pojo.User;
import com.vincent.user.service.UserService;
import com.vincent.utils.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * JWT 拦截器：用于接口调用前验证 token 合法性
 */
@Component
public class JwtInterceptor implements HandlerInterceptor  {

    private static final Logger log = LoggerFactory.getLogger(JwtInterceptor.class);

    @Resource
    private UserService userService;





    /**
     * 请求前处理逻辑
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // 1. 获取请求路径，放行登录/注册接口
        String uri = request.getRequestURI();
//        if (uri.contains("/api/user/login") || uri.contains("/api/user/register")) {
//            return true;
//        }

        // ✅ 白名单放行：登录、注册、环境数据（历史）
        if (
                uri.contains("/api/user/login")
                        || uri.contains("/api/user/register")
                        || uri.contains("/api/environment/history")
                        || uri.contains("/api/harmony/greenhouse")
                        || uri.startsWith("/video/")
                        || uri.startsWith("/uploads/")
                        || uri.startsWith("/error")        // ← 加上这一行

        ) {
            return true;
        }

        // ✅ 放行 OPTIONS 请求（CORS预检）
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            System.out.println("【JwtInterceptor拦截器】OPTIONS 请求，直接放行");
            return true;
        }

        System.out.println("【JwtInterceptor拦截器】获取到的uri："+uri);

        // 2. 获取 token
        String token = request.getHeader(Constants.TOKEN);


        System.out.println("【JwtInterceptor拦截器】获取到的 token = " + token);



        if (token == null || token.trim().isEmpty()) {
            System.out.println("【JwtInterceptor拦截器】token 为空，拒绝访问");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.getWriter().write("未登录或token为空");
            return false;
        }


        if (token.startsWith("Bearer ")) {
            System.out.println("【JwtInterceptor拦截器】监测到token开头包含Bearer ，执行去除Bearer 操作");
            token = token.substring(7); // 去掉 Bearer 和空格
            System.out.println("【JwtInterceptor拦截器】去掉Bearer 之后的token = "+token);
        }


        if (ObjectUtil.isEmpty(token)) {
            token = request.getParameter(Constants.TOKEN);
        }
        if (ObjectUtil.isEmpty(token)) {
            throw new CustomException(ResultCodeEnum.TOKEN_INVALID_ERROR); // token 缺失
        }

        try {
            // 3. 校验 token 签名合法性
            TokenUtils.verifyToken(token);

            // 4. 提取 userId，从数据库中查找用户
            String userId = JWT.decode(token).getAudience().get(0).split("-")[0];
            User user = userService.findUserByUserId(Integer.parseInt(userId));
            if (ObjectUtil.isNull(user)) {
                throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
            }

            // ✅ 你也可以在这里设置用户信息到 request 属性，供 controller 使用
            // request.setAttribute("currentUser", user);

        } catch (JWTVerificationException e) {
            log.error("token 校验失败", e);
            throw new CustomException(ResultCodeEnum.TOKEN_CHECK_ERROR);
        } catch (Exception e) {
            log.error("token 处理异常", e);
            throw new CustomException(ResultCodeEnum.TOKEN_CHECK_ERROR);
        }

        return true; // ✅ 校验通过，放行
    }
}
