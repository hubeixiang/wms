package com.sven.wms.web.Interceptor;

import com.sven.wms.web.util.ServletUtils;
import com.sven.wms.web.util.UUIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LoggingInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);
    private static final Map<String, Double> sessionInfo = new ConcurrentHashMap<>();
    private static String LoggingInterceptorID = "LoggingInterceptorID";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        Method method = null;
        if (handler instanceof HandlerMethod) {
            method = ((HandlerMethod) handler).getMethod();
            if (method.isAnnotationPresent(RequestMapping.class)) {
                String uuid = UUIDGenerator.get();
                request.setAttribute(LoggingInterceptorID, uuid);
                sessionInfo.put(uuid, new Double(System.currentTimeMillis()));
                logger.info("[Action] {} requests from {} with {}.",
                        request.getRequestURI(),
                        ServletUtils.getRealIp(request),
                        ServletUtils.parameterMapToString(request));
            }
        }
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        if (handler instanceof HandlerMethod) {
            Method method = ((HandlerMethod) handler).getMethod();
            if (method.isAnnotationPresent(RequestMapping.class)) {
                Object object = request.getAttribute(LoggingInterceptorID);
                if (object == null) {
                    logger.info("[Action] {} responds to {}.", request.getRequestURI(), ServletUtils.getRealIp(request));
                } else {
                    String uuid = String.valueOf(object);
                    Double startTime = sessionInfo.get(uuid);
                    if (startTime == null) {
                        logger.info("[Action] {} responds to {}.", request.getRequestURI(), ServletUtils.getRealIp(request));
                    } else {
                        logger.info("[Action] {} responds to {} cost {}ms.", request.getRequestURI(), ServletUtils.getRealIp(request), System.currentTimeMillis() - startTime);
                    }
                    sessionInfo.remove(uuid);
                }
            }
        }
        super.afterCompletion(request, response, handler, ex);
    }
}
