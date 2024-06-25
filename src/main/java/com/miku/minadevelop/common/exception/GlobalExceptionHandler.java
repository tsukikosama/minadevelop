package com.miku.minadevelop.common.exception;

import cn.dev33.satoken.exception.NotLoginException;
import com.miku.minadevelop.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

        @ExceptionHandler(CustomException.class)
        public Result bizExceptionHandler(CustomException e) {
            log.error("【业务异常】: {}", e.getMessage());
            return Result.fail(e.getMessage(),1);
        }

        @ExceptionHandler(NotLoginException.class)
        public Result notLoginExceptionExceptionHandler(NotLoginException e) {
            log.error("【登录异常】: {}", ExceptionUtils.getStackTrace(e));
            return Result.fail( e.getMessage(),401);
        }

        @ExceptionHandler(BindException.class)
        public Result bindExceptionHandler(BindException e) {
            log.error("【参数异常】: {}", ExceptionUtils.getStackTrace(e));
            BindingResult result = e.getBindingResult();
            if (result instanceof BeanPropertyBindingResult) {
                String msg = result.getFieldErrors().get(0).getDefaultMessage();
                return Result.fail( msg,1);
            } else {
                return Result.fail( "系统异常，请联系管理员",500);
            }
        }
        /**
         * 全局异常处理
         *
         * @param e
         * @return
         */
        @ExceptionHandler(Exception.class)
        public Result exceptionHandler(Exception e) {
            log.error("【系统异常】: {}", ExceptionUtils.getStackTrace(e));
            // 处理异常
            if (e instanceof MethodArgumentNotValidException) {
                // 参数检验异常
                MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) e;
                BindingResult result = methodArgumentNotValidException.getBindingResult();
                return Result.fail(result.getFieldErrors().get(0).getDefaultMessage(),500);
            } else if (e instanceof HttpRequestMethodNotSupportedException) {
                return Result.fail("请求方法不正确",500);
            } else if (e instanceof MissingServletRequestParameterException) {
                MissingServletRequestParameterException ex = (MissingServletRequestParameterException) e;
                return Result.fail("请求参数缺少: " + ex.getParameterName(),500);
            } else if (e instanceof MethodArgumentTypeMismatchException) {
                MethodArgumentTypeMismatchException ex = (MethodArgumentTypeMismatchException) e;
                return Result.fail("请求参数类型不正确：" + ex.getName(),500);
            } else if (e instanceof NoHandlerFoundException) {
                NoHandlerFoundException ex = (NoHandlerFoundException) e;
                return Result.fail( ex.getRequestURL(),500);
            } else {
                log.error("【系统异常】: {}", ExceptionUtils.getStackTrace(e));
                return Result.fail( "系统异常，请联系管理员",500);
            }
        }

}
