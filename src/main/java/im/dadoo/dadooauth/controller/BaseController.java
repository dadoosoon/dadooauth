package im.dadoo.dadooauth.controller;

import im.dadoo.dadooauth.dto.DTException;
import im.dadoo.dadooauth.exception.AuthException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

public class BaseController {
	
	@ExceptionHandler(AuthException.class)
	@ResponseBody
	public DTException process(AuthException ex, 
			HttpServletRequest req, HttpServletResponse res) {
		res.setStatus(ex.getStatus());
		return ex.toDTO();
	}
}
