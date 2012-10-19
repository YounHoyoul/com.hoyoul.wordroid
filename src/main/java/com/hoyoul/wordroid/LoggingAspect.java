package com.hoyoul.wordroid;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingAspect {
	private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
	
	public Object log(ProceedingJoinPoint call) throws Throwable {
        
        logger.info("###Entering method :" + call.toShortString());
        
		Object [] objs = call.getArgs();
		boolean isReqestFound = false;
		for(Object obj : objs){
			if(obj instanceof HttpServletRequest){
				HttpServletRequest request = (HttpServletRequest)obj;
				logger.info(request.getRequestURL().toString());
				
				@SuppressWarnings("rawtypes")
				Enumeration en = request.getParameterNames();
				while(en.hasMoreElements()){
					String name = (String) en.nextElement();
					String value = request.getParameter(name);
					logger.info(name+'='+value);
				}
				
				isReqestFound = true;
				
				break;
			}
		}
		
        Object point = call.proceed();
        
        if(isReqestFound){
        	logger.info("return => " + point);
        }
        
        logger.info("###Exiting method : " + call.toShortString());

        return point;
    }
}
