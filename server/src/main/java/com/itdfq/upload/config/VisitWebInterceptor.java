package com.itdfq.upload.config;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: GodChin
 * @Date: 2021/12/29 11:14
 * @Blog: itdfq.com
 * @QQ: 909256107
 * @Description: web拦截
 */
@Slf4j
public class VisitWebInterceptor implements HandlerInterceptor {

    /**
     * 线程方式获取时间
     */
    private static final ThreadLocal<Long> LOCAL_THREAD = new ThreadLocal<Long>();


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        Long endTime = System.currentTimeMillis();
        log.info("请求路径为:{},请求所用时间为:{}", request.getRequestURI(), (endTime - LOCAL_THREAD.get()));
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("ip：{}", getIpAddress(request));
        //线程方式
        LOCAL_THREAD.set(System.currentTimeMillis());
        log.info("访问路径：{}，参数：{}", request.getRequestURI(), JSON.toJSONString(request.getParameterMap()));
        return true;
    }

    /**
     * 获取Ip地址
     *
     * @param request
     * @return
     */
    private static String getIpAddress(HttpServletRequest request) {
        String Xip = request.getHeader("X-Real-IP");
        String XFor = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotBlank(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = XFor.indexOf(",");
            if (index != -1) {
                return XFor.substring(0, index);
            } else {
                return XFor;
            }
        }
        XFor = Xip;
        if (StringUtils.isNotBlank(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
            return XFor;
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getRemoteAddr();
        }
        return XFor;
    }

}
