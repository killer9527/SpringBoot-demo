package com.wfw.interceptor;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by killer9527 on 2018/3/16.
 * 日志拦截器
 */
public class LogInterceptor implements HandlerInterceptor{
    /**
     * controller 执行之前调用
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();  //记录起始时间
        request.setAttribute("startTime", startTime);

        //todo:记录接口访问和接口参数
        return true;
    }

    /**
     * controller 执行之后，且页面渲染之前调用
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        // You can add attributes in the modelAndView
        System.out.println();
    }

    /**
     * 页面渲染之后调用，一般用于资源清理操作
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        long startTime = (long)request.getAttribute("startTime");  //获取起始时间
        long endTime = System.currentTimeMillis();
        long timeTaken = endTime - startTime;
        String requestUrl = request.getRequestURL().toString();
        System.out.println(requestUrl);
        System.out.println("耗时：" + timeTaken);
    }
}
