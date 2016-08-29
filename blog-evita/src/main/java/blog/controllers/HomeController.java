package blog.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import blog.models.Post;
import blog.services.NotificationService;
import blog.services.PostService;

@Controller
@SessionAttributes("user")
public class HomeController extends BaseController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private NotificationService notificationService;

	
	
	@RequestMapping("/")
	public String home(Model model){
	
		
		
		List<Post> latest5Posts = postService.findByPublicationDate();
		model.addAttribute("latest5posts", latest5Posts);
		
		List<Post> latest3Posts = latest5Posts.stream().limit(3).collect(Collectors.toList());
		model.addAttribute("latest3posts", latest3Posts);
		return "index";
	}
	
	@RequestMapping("/posts/view/{id}")
	public String viewPost(@PathVariable("id") Long id, Model model){
		Post post = postService.findById(id);
		
		if (post == null) {
			notificationService.addErrorMessage("Cannot find post" + id);
			return "redirect:/";
		}
		
		return "/posts/index";
		
	}

}
