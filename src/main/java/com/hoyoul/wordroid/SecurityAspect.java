package com.hoyoul.wordroid;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SecurityAspect {
	private static final Logger logger = LoggerFactory.getLogger(SecurityAspect.class);
	
	public Object checkAuth(ProceedingJoinPoint call) throws Throwable {
		
		logger.info("check security.");
		
		Object [] objs = call.getArgs();
		
		for(Object obj : objs){
			if(obj instanceof HttpServletRequest){
				HttpServletRequest request = (HttpServletRequest)obj;
				HttpSession session = request.getSession();
				
				if(session.getAttribute("loginUser") == null){
					logger.info("Invalid user.");
					return "redirect:/";
				}
				
				break;
			}
		}

        return call.proceed();
    }
}
