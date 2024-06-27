package com.miku.minadevelop.modules.controller;


import cloud.tianai.captcha.common.constant.CaptchaTypeConstant;
import cloud.tianai.captcha.common.response.ApiResponse;
import cloud.tianai.captcha.spring.application.ImageCaptchaApplication;
import cloud.tianai.captcha.spring.vo.CaptchaResponse;
import cloud.tianai.captcha.spring.vo.ImageCaptchaVO;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miku.minadevelop.common.Result;
import com.miku.minadevelop.common.exception.CustomException;
import com.miku.minadevelop.common.exception.GlobalExceptionHandler;
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
import java.io.Serializable;
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
        if (!one.getPassword().equals(SecureUtil.md5(user.getPassword()))){
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
    @GetMapping("/{uid}")
    public Result getUserInfoById(@PathVariable(value = "uid") Long id) {
        User user = userService.getById(id);
        UserPoJo pojo = BeanUtil.copyProperties(user,UserPoJo.class);
        if (pojo == null){
            return Result.fail("用户不存在",406);
        }
        return Result.ok(pojo);
    }

    @PostMapping("/update/{uid}")
    public Result updateUser(@PathVariable(value = "uid")Long uid){
        if (BeanUtil.isEmpty(uid)){
            log.info("当前更新的用户uid为:{}",uid);
            throw new CustomException("数据库中不存在改用户id");
        }
        //判断数据库中是否存在改u用户
        User user = userService.getById(uid);
        if (BeanUtil.isEmpty(user)){
            log.info("用户:{} 不存在数据库中",uid);
            throw new CustomException("参数异常");
        }
        return Result.ok(user);
    }
    /**
     * 注册
     */
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        user.setPassword(SecureUtil.md5(user.getPassword()));
        boolean save = userService.save(user);
        if (!save){
            log.info("保存用户信息异常：{}",user);
            throw new CustomException("参数异常");

        }
        return Result.ok("注册成功");
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

    /**
     * 通过token获取用户
     * @param token
     * @return
     */
    @GetMapping("/get")
    public Result getUserByUid(@PathVariable(value = "token") Integer token){
        if (BeanUtil.isEmpty(token)){
            log.info("当前查询的用户id为:{}",token);
            throw new CustomException("参数有误");
        }
        String tokenValue = StpUtil.getTokenValue();
        Object loginId = StpUtil.getLoginId();
        if (!tokenValue.equals(tokenValue)){
            log.info("当前用户的token为:{}",token);
            //把当前的账号踢下线
            StpUtil.logout(loginId);
            throw new CustomException("token异常");
        }
        User byId = userService.getById((Long)loginId);
        return Result.ok(byId);
    }

    /**
     * 重置用户密码
     * @param uid
     * @return
     */
    @PostMapping("/reset/{uid}")
    public Result resetUser(@PathVariable Integer uid){
        userService.resetUser(uid);
        return Result.ok("修改成功");
    }

    /**
     * 账号的解禁和禁用
     * @return
     */
    @PostMapping("/ban")
    public Result Ban(){
        Object loginId = StpUtil.getLoginId();
        User user = userService.getById((Serializable) loginId);
        user.setStatus(user.getStatus()^1);
        return Result.ok("修改成功");
    }

}
