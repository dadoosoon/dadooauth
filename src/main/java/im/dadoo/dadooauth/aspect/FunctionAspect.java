package im.dadoo.dadooauth.aspect;

import java.util.HashMap;
import java.util.Map;

import im.dadoo.dadooauth.log.AuthLog;
import im.dadoo.dadooauth.log.LogSender;

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
	private LogSender logSender;
	
	@Around("execution(public * im.dadoo.dadooauth.service..*.*(..)) ")
	public Object logFun(ProceedingJoinPoint pjp) throws Throwable {
		Long t1 = System.currentTimeMillis();
		Object ret = pjp.proceed();
		Long t2 = System.currentTimeMillis();
		String sig = pjp.getSignature().toLongString();
		Object[] args = pjp.getArgs();
		
		//发送到日志服务器
		Map<String, Object> content = new HashMap<String, Object>();
		content.put("functionName", sig);
		content.put("args", args);
		content.put("ret", ret);
		content.put("time", t2 - t1);
		AuthLog log = new AuthLog(content, AuthLog.TYPE_FUN, System.currentTimeMillis());
		this.logSender.send(log);
		
		logger.info("函数信息:{}~~参数值:{}~~返回值:{}~~运行时间:{}", sig, args, ret, t2 - t1);
		return ret;
	}
}
