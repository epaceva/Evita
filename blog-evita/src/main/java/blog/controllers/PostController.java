package blog.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import blog.PageWrapper;
import blog.models.Post;
import blog.models.User;
import blog.services.NotificationService;
import blog.services.PostService;

@Controller
@SessionAttributes("user")
public class PostController {

	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private PostService postService;

	@RequestMapping(value = { "/post" }, method = RequestMethod.GET)
	public ModelAndView get(Model model, Pageable pageable) {
		if (!model.containsAttribute("user"))
			return new ModelAndView("redirect:/users/login");

		Page<Post> posts = postService.getPosts(pageable);
		PageWrapper<Post> page = new PageWrapper<Post>(posts, "/post");

		model.addAttribute("page", page);
		model.addAttribute("posts", posts);

		return new ModelAndView("/posts/index");
	}

	@RequestMapping("post/{id}")
	public String get(@PathVariable Long id, Model model) {
		Post post = postService.findById(id);
		
		if (post == null)
			return "/posts/not-found";
		model.addAttribute("post", postService.findById(id));
		return "/posts/post";
	}
	
	@RequestMapping(value = "/post/new", method = RequestMethod.GET)
	public String newPost(Model model) {
		if (!model.containsAttribute("user")) return "redirect:/users/login";
		
		Post post = new Post();
		post.setId(0L);

		User user = (User) model.asMap().get("user");
		post.setAuthor(user);
		post.setPublicationDate(new Date());
		
		model.addAttribute("post", post);
		
        return "redirect:/posts/edit";		
	}    

    @RequestMapping("post/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
		if (!model.containsAttribute("user")) return "redirect:/users/login";
		
		Post post = postService.findById(id);
		model.addAttribute("post", post);
		
        return "posts/edit";
    }

	@RequestMapping(value = "/post", method = RequestMethod.POST)
	public String save(Post post, BindingResult bindingResult, Model model) {
		if (!model.containsAttribute("user")) return "redirect:/users/login";
		
		if (bindingResult.hasErrors()) {
			notificationService.addErrorMessage("Fill the form correctly");
			return "posts/edit";
		}
		
		if (post.getTitle() == null || post.getTitle().trim().length() < 3) {
			notificationService.addErrorMessage("Title should be more than 3 chars.");
			return "posts/edit";			
		}
		if (post.getTitle().length() > 500) {
			notificationService.addErrorMessage("Title should be less than 500 chars.");
			return "posts/edit";			
		}
		if (post.getBody() == null || post.getBody().trim().length() < 3) {
			notificationService.addErrorMessage("Body should be more than 3 chars.");
			return "posts/edit";			
		}
		if (post.getBody().length() > 1024) {
			notificationService.addErrorMessage("Body should be less than 1024 chars.");
			return "posts/edit";
		}

		User user = (User) model.asMap().get("user");
		post.setAuthor(user);
		if (post.getId() != null && post.getId() != 0) {
			postService.edit(post);
		} else {
			post.setPublicationDate(new Date());
			postService.create(post);
		}
		
		notificationService.addInfoMessage("Post created successfully");
		return "redirect:/post/edit/" + post.getId();
	}
	

    @RequestMapping("post/delete/{id}")
    public String delete(@PathVariable Long id, Model model){
		if (!model.containsAttribute("user")) return "redirect:/users/login";

        postService.deleteById(id);
        return "redirect:/post";
    }

}
