package blog.controllers;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import blog.forms.PostForm;
import blog.models.Post;
import blog.models.User;
import blog.services.NotificationService;
import blog.services.PostService;

@Controller
@SessionAttributes("user")
public class CreatePostController {
	
	@Autowired
	private NotificationService notificationService;

	@Autowired
	private PostService postService;
	
	@RequestMapping(value = "/posts/edit", method=RequestMethod.GET)
	public ModelAndView showLoginForm(PostForm postForm, BindingResult bindingResult, Model model) {
		if (!model.containsAttribute("user")) return new ModelAndView("redirect:/users/login");
		
		Post post = new Post();
		post.setTitle("");
		post.setBody("");
		
		return new ModelAndView("posts/edit", "post", post);
	}
	
	@RequestMapping(value = "/posts/edit", method = RequestMethod.POST)
	public ModelAndView savePage(@Valid PostForm postForm, BindingResult bindingResult, Model model) {
		if (!model.containsAttribute("user")) return new ModelAndView("redirect:/users/login");
		
		Post post = new Post();
		post.setTitle(postForm.getTitle());
		post.setBody(postForm.getBody());
		
		if (bindingResult.hasErrors()) {
			notificationService.addErrorMessage("Fill the form correctly");
			return new ModelAndView("posts/edit", "post", post);
		}
		
		User user = (User) model.asMap().get("user");
		post.setAuthor(user);
		post.setPublicationDate(new Date());

		postService.edit(post);

		notificationService.addInfoMessage("Post created successfully");
		return new ModelAndView("posts/edit", "post", post);
	}

}
