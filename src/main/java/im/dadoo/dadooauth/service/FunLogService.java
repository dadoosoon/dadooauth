package im.dadoo.dadooauth.service;

import im.dadoo.dadooauth.log.AuthLog;
import im.dadoo.dadooauth.log.LogSender;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FunLogService {

	@Autowired
	private LogSender logSender;
	
	public void save(String functionName, Object[] args, Object ret, Long time) {
		Map<String, Object> content = new HashMap<String, Object>();
		content.put("functionName", functionName);
		content.put("args", args);
		content.put("ret", ret);
		content.put("time", time);
		
		AuthLog log = new AuthLog(content, AuthLog.TYPE_FUN, System.currentTimeMillis());
		logSender.send(log);
		
	}
}
