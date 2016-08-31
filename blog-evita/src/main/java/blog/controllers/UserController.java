package blog.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import blog.PageWrapper;
import blog.forms.LoginForm;
import blog.forms.RegisterForm;
import blog.models.User;
import blog.services.NotificationService;
import blog.services.UserService;

@Controller
@SessionAttributes("user")
public class UserController {

	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/user/logout",  method=RequestMethod.POST)
	public String postLogout(SessionStatus sessionStatus) {
		sessionStatus.setComplete();
		return "redirect:/";
	}
	
	@RequestMapping("/user/login")
	public String getLogin(LoginForm loginForm) {
		return "users/login";
	}
	
	@RequestMapping(value="/user/login", method=RequestMethod.POST)
	public String postLogin(@Valid LoginForm loginForm, BindingResult bindingResult, Model model) {
		
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

		model.addAttribute("user", user);
		
		notificationService.addInfoMessage("Successfully login");
		return "redirect:/";
	}
	
	@RequestMapping(value = "/user/register", method = RequestMethod.GET)
	public String getRegister(RegisterForm registerForm) {
		return "users/register";
	}
	
	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	public String showRegisterPage(@Valid RegisterForm registerForm, BindingResult bindingResult)
			{
		if (bindingResult.hasErrors()) {
			notificationService.addErrorMessage("Fill the form correctly");
			return "users/register";
		}
		
		if (!registerForm.getPassword().equals(registerForm.getRepeatPassword())){
			notificationService.addErrorMessage("Password not match");
			return "users/register";
		}
		
		if (null != userService.findByUsername(registerForm.getUsername())) {
			notificationService.addErrorMessage("User already exist");
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
		
		notificationService.addInfoMessage("Registration is successful. You can login with your account.");
		return "redirect:/user/login";
	}	
	
	@RequestMapping(value = { "/user" }, method = RequestMethod.GET)
	public ModelAndView get(Model model, Pageable pageable) {
		if (!model.containsAttribute("user")) return new ModelAndView("redirect:/users/login");

		Page<User> users = userService.getUsers(pageable);
		PageWrapper<User> page = new PageWrapper<>(users, "/user");

		model.addAttribute("page", page);
		model.addAttribute("users", users);

		return new ModelAndView("/users/index");
	}

}
