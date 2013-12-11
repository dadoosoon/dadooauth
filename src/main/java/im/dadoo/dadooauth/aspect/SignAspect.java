package im.dadoo.dadooauth.aspect;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import im.dadoo.dadooauth.domain.User;
import im.dadoo.dadooauth.log.AuthLog;
import im.dadoo.dadooauth.log.LogSender;
import im.dadoo.dadooauth.service.SignService;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;

@Component
@Aspect
public class SignAspect {

	@Autowired
	private SignService signService;
	
	@Autowired
	private LogSender logSender;
	
	@Autowired
	private Jedis jedis;
	
	@Autowired
	private ObjectMapper mapper;
	
	@AfterReturning(value = "execution(public * im.dadoo.dadooauth.controller.SignController.signin(..))", 
			returning="user")
	public void add(User user) 
			throws JsonGenerationException, JsonMappingException, IOException {
		if (user != null) {
			String json = this.mapper.writeValueAsString(user);
			String key = "dadooauth:user:" + user.getId();
			if (!this.jedis.exists(key)) {
				Map<String, Object> content = new HashMap<String, Object>();
				content.put("user", key);
				content.put("signinDatetime", System.currentTimeMillis());
				
				AuthLog log = new AuthLog(content, AuthLog.TYPE_SIGN);
				this.logSender.send(log);
			}
			this.jedis.set(key, json);
		}
	}
	
	@AfterReturning(value = "execution(public * im.dadoo.dadooauth.controller.SignController.verify(..))" + 
													"execution(public * im.dadoo.dadooauth.controller.SignController.lock(..))" + 
													"execution(public * im.dadoo.dadooauth.controller.SignController.unlock(..))" +
													"execution(public * im.dadoo.dadooauth.controller.UserController.update(..))",
													returning="user")
	public void update(User user) 
			throws JsonGenerationException, JsonMappingException, IOException {
		if (this.jedis.exists(String.format("dadooauth:user:%d", user.getId()))) {
			if (user.getState() == User.NORMAL) {
				String json = this.mapper.writeValueAsString(user);
				System.out.println(json);
				this.jedis.set(String.format("dadooauth:user:%d", user.getId()), json);
			}
			else {
				this.jedis.del(String.format("dadooauth:user:%d", user.getId()));
			}
		}
	}
}
