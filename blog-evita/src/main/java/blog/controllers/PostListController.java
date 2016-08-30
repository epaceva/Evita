package blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import blog.forms.PostForm;
import blog.models.Post;
import blog.services.NotificationService;
import blog.services.PostService;

@Controller
@SessionAttributes("user")
public class PostListController {
	
	@Autowired
	private NotificationService notificationService;

	@Autowired
	private PostService postService;
	
	//https://www.javacodegeeks.com/2013/03/implement-bootstrap-pagination-with-spring-data-and-thymeleaf.html
	@RequestMapping(value = { "/posts" }, method=RequestMethod.GET)
	public ModelAndView get(
			Model model,
			Pageable pageable) {
		if (!model.containsAttribute("user")) return new ModelAndView("redirect:/users/login");
		
		Page<Post> posts = postService.getPosts(pageable);
		PageWrapper<Post> page = new PageWrapper<Post>(posts, "/posts");
		
		model.addAttribute("page", page);		
		model.addAttribute("posts", posts);
		
    	return new ModelAndView("/posts/index");
	}
	
	//http://nixmash.com/java/pagination-with-spring-mvc-solr-thymeleaf-and-bootstrap/
//	@RequestMapping(value = { "/posts/{page}", "/posts" }, method=RequestMethod.GET)
//	public ModelAndView get(
//			@RequestParam(name="page", defaultValue="1", required=false) Integer pageNumber, 
//			Model model) {
//		if (!model.containsAttribute("user")) return new ModelAndView("redirect:/users/login");
//		
//		Page<Post> page = postService.getPosts(pageNumber, 10);
//		
//	    int current = page.getNumber() + 1;
//	    int begin = Math.max(1, current - 5);
//	    int end = Math.min(begin + 10, page.getTotalPages());
//
//	    System.out.println("---->>> " + page.getContent().size());
//	    model.addAttribute("page", page);
//	    model.addAttribute("posts", page.getContent());	    
//	    model.addAttribute("beginIndex", begin);
//	    model.addAttribute("endIndex", end);
//	    model.addAttribute("currentIndex", current);
//	    
//		return new ModelAndView("/posts/index");
//	}

}
