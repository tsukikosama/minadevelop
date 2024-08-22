package com.miku.minadevelop.common.Interceptor;


import org.asynchttpclient.AsyncHttpClientConfig;
import org.asynchttpclient.netty.channel.ChannelManager;
import org.asynchttpclient.netty.handler.intercept.Interceptors;
import org.asynchttpclient.netty.request.NettyRequestSender;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IpBasedInterceptor implements HandlerInterceptor {

    //在请求处理之前调用
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    //// 在请求处理之后进行调用（在视图渲染之前），可用于资源清理
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
