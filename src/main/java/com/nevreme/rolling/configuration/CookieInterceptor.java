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
//    @Autowired
//    private VisitorService visitorService;
//    private String cookieName = "visitorId";

//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if (visitorService == null) {
//            ServletContext servletContext = request.getServletContext();
//            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
//            visitorService = webApplicationContext.getBean(VisitorService.class);
//        }
//        System.out.println("---------------------------------");
//        System.out.println("---------------------------------");
//        System.out.println("REQ:"+request.getHeader("referer"));
//        System.out.println("RES:"+response.getHeader("referer"));
//        System.out.println("---------------------------------");
//        System.out.println("---------------------------------");
////        System.out.prinTLN("ADDD");
////        SYSTEM.OUT.PRINTLN(REQUEST.GETSERVERNAME());
////        SYSTEM.OUT.PRINTln("addd");
//        Cookie[] cookies = request.getCookies();
//        boolean found = false;
//        if (cookies == null) {
//        	System.out.println("adding 1");
//            addVisitor(response);
//            return true;
//        }
//
//        for (Cookie cookie : cookies) {
////        	System.out.println("--------COOKIE REQ-------");
////        	System.out.println(cookie.getName());
////        	System.out.println(cookie.getValue());
////        	System.out.println(cookie.getPath());
////        	System.out.println(cookie.getDomain());
////        	System.out.println(cookie.getMaxAge());
////        	System.out.println("--------COOKIE REQ-------");
//            if (cookie.getName().equals(cookieName)) {
//            	System.out.println("found");
//                found = true;
//                break;
//            }
//        }

//        if (!found) {
////        	System.out.println("adding 2");
////            addVisitor(response);
//        }

//        return true;
//    }

//    private void addVisitor(HttpServletResponse response) {
//        Visitor visitor = new Visitor();
//        visitor.setVisitorId(UUID.randomUUID().toString());
//        visitorService.save(visitor);
//        Cookie cookie = new Cookie(cookieName, visitor.getVisitorId());
//        cookie.setPath("/");
//        cookie.setDomain(".radiorolling.com");
//        cookie.setMaxAge(Integer.MAX_VALUE);
//        
//        System.out.println("--------COOKIE RESPONSE-------");
//        System.out.println("*** "+cookie.getName());
//        System.out.println("*** "+cookie.getValue());
//        System.out.println("*** "+cookie.getPath());
//        System.out.println("*** "+cookie.getDomain());
//        System.out.println("--------COOKIE RESPONSE-------");
//        
//        response.addCookie(cookie);
//    }

}