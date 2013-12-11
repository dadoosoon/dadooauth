package im.dadoo.dadooauth.aspect;

import im.dadoo.dadooauth.service.FunLogService;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class FunctionAspect {
	
	private static final Logger logger = LoggerFactory.getLogger(FunctionAspect.class);
	
	@Autowired
	private FunLogService flService;
	
	@Around("execution(public * im.dadoo.dadooauth.service..*.*(..)) "
			+ "and not execution(public * im.dadoo.dadooauth.service.FunLogService.*(..)) ")
	public Object logFun(ProceedingJoinPoint pjp) throws Throwable {
		Long t1 = System.currentTimeMillis();
		Object ret = pjp.proceed();
		Long t2 = System.currentTimeMillis();
		String sig = pjp.getSignature().toLongString();
		Object[] args = pjp.getArgs();
		
		this.flService.save(sig, args, ret, t2 - t1);
		logger.info("函数信息:{}~~参数值:{}~~返回值:{}~~运行时间:{}", sig, args, ret, t2 - t1);
		return ret;
	}
}
