package im.dadoo.dadooauth.controller;

import java.util.List;

import im.dadoo.dadooauth.domain.User;
import im.dadoo.dadooauth.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

	private static final Integer DEFAULT_PAGESIZE = 100;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	@ResponseBody
	public User item(@PathVariable Integer id) {
		return this.userService.fetchById(id);
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	@ResponseBody
	public List<User> list(@RequestParam(required = false) Integer pagecount, 
			@RequestParam(required = false) Integer pagesize) {
		if (pagecount == null) {
			return this.userService.list();
		}
		else {
			if (pagesize == null) {
				pagesize = DEFAULT_PAGESIZE;
			}
			return this.userService.list(pagecount, pagesize);
		}
	}
}
