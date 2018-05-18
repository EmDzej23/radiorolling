package com.nevreme.rolling.configuration;

import com.nevreme.rolling.model.Visitor;
import com.nevreme.rolling.service.VisitorService;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class CookieInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private VisitorService visitorService;
    private String cookieName = "visitorId";

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (visitorService == null) {
            ServletContext servletContext = request.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            visitorService = webApplicationContext.getBean(VisitorService.class);
        }

        Cookie[] cookies = request.getCookies();
        boolean found = false;
        if (cookies == null) {
            addVisitor(response);
            return;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().contentEquals(cookieName)) {
                found = true;
            }
        }

        if (!found) {
            addVisitor(response);
        }

    }

    private void addVisitor(HttpServletResponse response) {
        Visitor visitor = new Visitor();
        visitor.setVisitorId(UUID.randomUUID().toString());
        visitorService.save(visitor);
        response.addCookie(new Cookie(cookieName, visitor.getVisitorId()));
    }

}