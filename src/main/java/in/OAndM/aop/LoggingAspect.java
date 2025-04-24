 package in.OAndM.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import in.OAndM.core.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Configuration
public class LoggingAspect {
	private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

	
	@SuppressWarnings("unchecked")
	@AfterReturning(pointcut = "execution(* in.OAndM.resources.application.*Controller.*(..)) "
			+ "|| execution(* in.OAndM.resources.application.*.*Controller.*(..))", returning="retVal")
	public void logAdminActionsBeforeAdvice(JoinPoint joinPoint, Object retVal){
		
		String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
		String methodName = joinPoint.getSignature().getName();
		
		HttpServletRequest servletRequest = null;
		for (Object signatureArg: joinPoint.getArgs()) {
	      if(signatureArg instanceof HttpServletRequest){
	    	  servletRequest = (HttpServletRequest)signatureArg;
	    	  break;
	      }
		}
    	
    	String status;
		ResponseEntity<BaseResponse<HttpStatus, ?>> response = (ResponseEntity<BaseResponse<HttpStatus, ?>>)retVal;
    	if(response != null && response.getBody().isSuccess())
    		status = "SUCCESS";
    	else
    		status = "FAILED";	
    	
    	logger.info("Class Name:{}, Method Name:{}, Status:{}, Servlet Request:{}", className, methodName, status, servletRequest);
    	
	}
}
