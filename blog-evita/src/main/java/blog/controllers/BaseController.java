package blog.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

public class BaseController {

	protected ModelAndView getModel(String path, HttpServletRequest request) {
		ModelAndView model = new ModelAndView(path);
		model.addObject("user", request.getSession().getAttribute("user"));
		return model;
	}
}
