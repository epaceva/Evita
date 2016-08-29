package blog.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import blog.forms.LoginForm;
import blog.forms.RegisterForm;
import blog.models.User;
import blog.services.DuplicateUser;
import blog.services.LoginService;
import blog.services.NotificationService;
import blog.services.PasswordMatchesValidator;
import blog.services.RegisterService;
import blog.services.UserService;

@Controller
public class RegisterController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private NotificationService notifyService;
	
	@RequestMapping("/users/register")
	public String register(RegisterForm registerForm) {
		return "users/register";
	}
	
	@RequestMapping(value = "/users/register", method = RequestMethod.POST)
	public String showRegisterPage(@Valid RegisterForm registerForm, BindingResult bindingResult,
			@Valid DuplicateUser duplicateUser)
			{
		if (bindingResult.hasErrors()) {
			notifyService.addErrorMessage("Fill the form correctly");
			return "users/register";
		}
		
		if (!registerForm.getPassword().equals(registerForm.getRepeatPassword())){
			notifyService.addErrorMessage("Password not match");
			return "users/register";
		}
		
		if (null != userService.findByUsername(registerForm.getUsername())) {
			notifyService.addErrorMessage("User already exist");
			return "users/register";
		}
		
		User user = new User();
		user.setId(0L);
		user.setUsername(registerForm.getUsername());;
		user.setFirstName(registerForm.getFirstName());
		user.setLastName(registerForm.getLastName());
		user.setPasswordHash(registerForm.getPassword());
		user.setRepeatPasswordHash(registerForm.getRepeatPassword());

		userService.create(user);
		
		notifyService.addInfoMessage("Registration is successful. You can login with your account.");
		return "redirect:/users/login";
	}
	

}
