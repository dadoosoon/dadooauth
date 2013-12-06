package im.dadoo.dadooauth.controller;

import im.dadoo.dadooauth.domain.User;
import im.dadoo.dadooauth.exception.AuthException;
import im.dadoo.dadooauth.exception.ExceptionCode;
import im.dadoo.dadooauth.service.SignService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SignController extends BaseController {

	@Autowired
	private SignService signService;
	
	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	@ResponseBody
	public User signin(@RequestParam String name, 
			@RequestParam String password) {
		User user = this.signService.signin(name, password);
		if (user == null) {
			throw new AuthException(404, ExceptionCode.SIGNIN_FAILED, "/signin", "POST", "用户名或密码错误");
		}
		
		return user;
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	@ResponseBody
	public User signup(@RequestParam String name, 
			@RequestParam String email, @RequestParam String password) throws AuthException {
		User user = null;
		try {
			user = this.signService.signup(name, email, password);
		} catch(AuthException ex) {
			ex.setUrl("/signup");
			ex.setMethod("POST");
			throw ex;
		}
		return user;
	}
	
//	@RequestMapping(value = "/verify", method = RequestMethod.POST)
//	@ResponseBody
//	public User verify(@RequestParam Integer id) {
//		User user = this.verify(id);
//		
//	}
}
