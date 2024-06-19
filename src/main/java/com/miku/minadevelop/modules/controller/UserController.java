package com.miku.minadevelop.modules.controller;


import cloud.tianai.captcha.common.constant.CaptchaTypeConstant;
import cloud.tianai.captcha.common.response.ApiResponse;
import cloud.tianai.captcha.spring.application.ImageCaptchaApplication;
import cloud.tianai.captcha.spring.vo.CaptchaResponse;
import cloud.tianai.captcha.spring.vo.ImageCaptchaVO;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miku.minadevelop.common.Result;
import com.miku.minadevelop.modules.entity.User;
import com.miku.minadevelop.modules.pojo.CheckData;
import com.miku.minadevelop.modules.pojo.UserPoJo;
import com.miku.minadevelop.modules.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author miku
 * @since 2024-06-13
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final StringRedisTemplate redisTemplate;

    private final ImageCaptchaApplication application;

    private final UserServiceImpl userService;
    /**
     * 登录
     * @Captcha 开启验证码验证
     * @param user
     * @return
     */

//    @Captcha("SLIDER")
    @PostMapping("/login/{rememberMe}")
    public Result login(@RequestBody User user,@PathVariable(value = "rememberMe",required = false) boolean rememberMe) {
        System.out.println(user);
        User one = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getAccount, user.getAccount()));
        System.out.println(one);
        if (one == null) {
            return Result.fail("账号不存在",406);
        }
        if (!one.getPassword().equals(user.getPassword())){
            return Result.fail("密码错误",406);
        }
        /**
         * 判断用户是否勾选了记住我的选项
         */
        if (rememberMe) {
            StpUtil.login(one.getUid(),true);
        }
        StpUtil.login(one.getUid(),false);
        /**
         * 获取token数据
         */
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        return Result.ok(tokenInfo);
    }

    /**
     * 通过用户id查询用户信息
     */
    @GetMapping("/{id}")
    public Result getUserInfoById(@PathVariable("id") Long id) {
        User user = userService.getById(id);
        UserPoJo pojo = BeanUtil.copyProperties(user,UserPoJo.class);
        if (pojo == null){
            return Result.fail("用户不存在",406);
        }
        return Result.ok(pojo);
    }
    /**
     * 注册
     */
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        return Result.ok();
    }

    /**
     * 退出
     */
    @PostMapping("/logout")
    public Result logout(@RequestBody String uid) {
        StpUtil.logout(uid);
        log.info("用户{}注销成功",uid);
        return Result.ok("退出登录成功");
    }


    /**
     * 生成验证码
     */
    @PostMapping("/captcha")
    public CaptchaResponse<ImageCaptchaVO> genCaptcha(HttpServletRequest request, @RequestParam(value = "type", required = false)String type) {
        if (StringUtils.isBlank(type)) {
            type = CaptchaTypeConstant.SLIDER;
        }
        if ("RANDOM".equals(type)) {
            int i = ThreadLocalRandom.current().nextInt(0, 4);
            if (i == 0) {
                type = CaptchaTypeConstant.SLIDER;
            } else if (i == 1) {
                type = CaptchaTypeConstant.CONCAT;
            } else if (i == 2) {
                type = CaptchaTypeConstant.ROTATE;
            } else{
                type = CaptchaTypeConstant.WORD_IMAGE_CLICK;
            }
        }
        CaptchaResponse<ImageCaptchaVO> response = application.generateCaptcha(type);
        //把当前的response存入redis
        redisTemplate.opsForValue().set(response.getId(),response.getId());
        application.getImageCaptchaResourceManager();
        return response;
    }

    @PostMapping("/check")
    @ResponseBody
    public ApiResponse<?> checkCaptcha(@RequestBody CheckData data,
                                       HttpServletRequest request) {
          String s = redisTemplate.opsForValue().get(data.getId());
          ApiResponse<?> match = application.matching(s,data.getData());
        return match;
    }


}
