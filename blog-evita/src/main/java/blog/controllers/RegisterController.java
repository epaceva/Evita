package blog.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import blog.forms.LoginForm;
import blog.forms.RegisterForm;
import blog.services.DuplicateUser;
import blog.services.NotificationService;
import blog.services.PasswordMatchesValidator;
import blog.services.RegisterService;

@Controller
public class RegisterController {
	
	@Autowired
	private RegisterService registerService;
	
	@Autowired
	private NotificationService notifyService;
	
	@RequestMapping("/users/register")
	public String register(RegisterForm registerForm) {
		return "users/register";
	}
	
	@RequestMapping(value = "/users/register", method = RequestMethod.POST)
	public String showRegisterPage(@Valid RegisterForm registerForm, BindingResult bindingResult, 
			@Valid PasswordMatchesValidator passwordMatchesValidator,
			@Valid DuplicateUser duplicateUser,
			@Valid LoginForm loginForm) {
		if (bindingResult.hasErrors()) {
			notifyService.addErrorMessage("Fill the form correctly");
			return "users/register";
		}
		
		if (registerService.authenticate(registerForm.getUsername(),registerForm.getPassword(), registerForm.getRepeatPassword())){
			notifyService.addErrorMessage("Invalid registration");
			return "users/register";
		}
		
		if (passwordMatchesValidator.authenticate(registerForm.getPassword(), registerForm.getRepeatPassword())){
			notifyService.addErrorMessage("Password not match");
			return "users/register";
		}
		
		if(duplicateUser.authenticate(loginForm.getUsername(),registerForm.getUsername())) {
			notifyService.addErrorMessage("User already exist");
			return "users/register";
		}
		
		notifyService.addInfoMessage("Registration is successful");
		return "users/login";
	}
	

}
