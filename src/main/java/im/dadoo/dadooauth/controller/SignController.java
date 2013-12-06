package im.dadoo.dadooauth.controller;

import im.dadoo.dadooauth.domain.User;
import im.dadoo.dadooauth.exception.AuthException;
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
			@RequestParam String password) throws AuthException {
		try {
			return this.signService.signin(name, password);
		} catch (AuthException ex) {
			ex.setUrl("/signin");
			ex.setMethod("POST");
			throw ex;
		}
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	@ResponseBody
	public User signup(@RequestParam String name, @RequestParam String email, 
			@RequestParam String password) throws AuthException {
		try {
			return this.signService.signup(name, email, password);
		} catch (AuthException ex) {
			ex.setUrl("/signup");
			ex.setMethod("POST");
			throw ex;
		}
	}
	
	@RequestMapping(value = "/verify", method = RequestMethod.POST)
	@ResponseBody
	public User verify(@RequestParam Integer id) throws AuthException {
		try {
			return this.signService.verify(id);	
		} catch (AuthException ex) {
			ex.setUrl("/verify");
			ex.setMethod("POST");
			throw ex;
		}
	}
	
	@RequestMapping(value = "/lock", method = RequestMethod.POST)
	@ResponseBody
	public User lock(@RequestParam Integer id) throws AuthException {
		try {
			return this.signService.lock(id);	
		} catch (AuthException ex) {
			ex.setUrl("/lock");
			ex.setMethod("POST");
			throw ex;
		}
	}
	
	@RequestMapping(value = "/unlock", method = RequestMethod.POST)
	@ResponseBody
	public User unlock(@RequestParam Integer id) {
		try {
			return this.signService.unlock(id);	
		} catch (AuthException ex) {
			ex.setUrl("/unlock");
			ex.setMethod("POST");
			throw ex;
		}
	}
}
