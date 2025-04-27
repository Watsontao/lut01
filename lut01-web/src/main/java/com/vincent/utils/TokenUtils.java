package com.vincent.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.vincent.common.Constants;
import com.vincent.enums.RoleEnum;
import com.vincent.user.pojo.User;
import com.vincent.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * JWT 工具类（使用统一密钥）
 */
@Component
public class TokenUtils {

    private static final Logger log = LoggerFactory.getLogger(TokenUtils.class);

    // ✅ 服务端统一密钥（建议放到配置文件中）
    private static final String SECRET = "vincent-agri-app";

    private static UserService staticUserService;

    @Resource
    private UserService userService;

    @PostConstruct
    public void init() {
        staticUserService = this.userService;
    }

    /**
     * ✅ 生成 token
     * @param userId 用户ID
     * @param role 角色
     */
    public static String createToken(String userId, String role) {
        return JWT.create()
                .withAudience(userId + "-" + role)
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2)) // 2 小时过期
                .sign(Algorithm.HMAC256(SECRET)); // ✅ 用统一密钥签名
    }

    /**
     * ✅ 验证 token 合法性
     */
    public static void verifyToken(String token) throws JWTVerificationException {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        jwtVerifier.verify(token); // 校验合法性
    }

    /**
     * ✅ 获取当前登录用户
     */
    public static User getCurrentUser() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader(Constants.TOKEN);
            if (ObjectUtil.isNotEmpty(token)) {

                // 新增：去掉 "Bearer " 前缀
                if (token.startsWith("Bearer ")) {
                    token = token.substring(7);
                    System.out.println("当前用户登录的token："+token);
                }
                String userRole = JWT.decode(token).getAudience().get(0);
                String userId = userRole.split("-")[0];
                return staticUserService.findUserByUserId(Integer.valueOf(userId));
            }
        } catch (JWTDecodeException e) {
            log.error("解析 token 失败", e);
        } catch (Exception e) {
            log.error("获取当前用户失败", e);
        }
        return null;
    }

    /**
     * ✅ 获取当前登录用户角色
     */
    public static String getCurrentRole() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader(Constants.TOKEN);
            if (ObjectUtil.isNotEmpty(token)) {

                // 新增：去掉 "Bearer " 前缀
                if (token.startsWith("Bearer ")) {
                    token = token.substring(7);
                    System.out.println("当前用户登录的token："+token);
                }


                String userRole = JWT.decode(token).getAudience().get(0);
                return userRole.split("-")[1]; // 取 role
            }
        } catch (Exception e) {
            log.error("获取当前用户角色失败", e);
        }
        return null;
    }
}
