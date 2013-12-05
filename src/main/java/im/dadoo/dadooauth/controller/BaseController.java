package im.dadoo.dadooauth.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

public class BaseController {
	
	@ExceptionHandler(NoSuchRequestHandlingMethodException.class)
	@ResponseBody
	public String handle404(NoSuchRequestHandlingMethodException ex, 
			HttpServletRequest req, HttpServletResponse res) {
		res.setStatus(404);
		return "404";
	}
}
