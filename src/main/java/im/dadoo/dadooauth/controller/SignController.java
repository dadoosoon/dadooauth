package im.dadoo.dadooauth.controller;

import im.dadoo.dadooauth.domain.User;
import im.dadoo.dadooauth.service.SignService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SignController {

	@Autowired
	private SignService signService;
	
	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	@ResponseBody
	public User signin(@RequestParam String name, 
			@RequestParam String password) {
		return this.signService.signin(name, password);
	}
}
