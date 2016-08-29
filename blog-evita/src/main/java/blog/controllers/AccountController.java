package blog.controllers;

import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import blog.forms.LoginForm;
import blog.forms.RegisterForm;
import blog.models.User;
import blog.services.LoginService;
import blog.services.NotificationService;
import blog.services.RegisterService;
import blog.services.UserService;

@Controller
public class AccountController {
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/users/login")
	public String showLoginForm(LoginForm loginForm) {
		return "users/login";
	}
	
	@RequestMapping(value="/users/login", method=RequestMethod.POST)
	public String showLoginForm(@Valid LoginForm loginForm, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			notificationService.addErrorMessage("Please correct username/password");
			return "users/login";
		}
		
		User user = userService.findByUsername(loginForm.getUsername());
		if (user == null){

		notificationService.addErrorMessage("Wrong username/password");
		return "/users/login";
		}
		
		if(!user.getPasswordHash().equals(loginForm.getPassword())) {
			notificationService.addErrorMessage("Please correct username/password");
			return "users/login";
		}
		
		notificationService.addInfoMessage("Successfully login");
		return "redirect:/";
	}

}
