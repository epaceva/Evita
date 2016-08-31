package blog.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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

	/**
	 * Method register date formatter for the controller.
	 * 
	 * This helps for transporting dates between browser and server. When HTML template (post/edit) contains:
	 * 
	 * <pre>
	 * {@code 
	 * <input type="hidden" th:field="*{publicationDate}" />
	 * }
	 * </pre>
	 * 
	 * Custom date editor read / writes the date, so Post object can keep its date.
	 * 
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@RequestMapping(value = { "/post" }, method = RequestMethod.GET)
	public ModelAndView get(Model model, Pageable pageable) {
		if (!model.containsAttribute("user")) return new ModelAndView("redirect:/user/login");

		User user = (User) model.asMap().get("user");
		
		Page<Post> posts = postService.findByAuthor(user, pageable);
		PageWrapper<Post> page = new PageWrapper<Post>(posts, "/post");

		model.addAttribute("page", page);
		model.addAttribute("posts", posts);

		return new ModelAndView("/posts/index");
	}

	@RequestMapping("post/{id}")
	public String get(@PathVariable Long id, Model model) {
		Post post = postService.findById(id);
		
		model.addAttribute("lastPosts", postService.getPosts(0,  5).getContent());
		
		if (post == null)
			return "/posts/not-found";
		model.addAttribute("post", postService.findById(id));
		return "/posts/post";
	}
	
	@RequestMapping(value = "/post/new", method = RequestMethod.GET)
	public String newPost(Model model) {
		if (!model.containsAttribute("user")) return "redirect:/user/login";
		
		Post post = new Post();
		post.setId(0L);
		User user = (User) model.asMap().get("user");
		post.setAuthor(user);
		post.setPublicationDate(new Date());		
		model.addAttribute("post", post);
		
        return "/posts/edit";		
	}    

    @RequestMapping(value = { "post/edit/{id}", "post/edit/"})
    public String edit(@PathVariable Long id, Model model){
		if (!model.containsAttribute("user")) return "redirect:/user/login";

		Post post = postService.findById(id);
		model.addAttribute("post", post);
		
        return "posts/edit";
    }

	@RequestMapping(value = "/post", method = RequestMethod.POST)
	public String save(Post post, Model model) {
		if (!model.containsAttribute("user")) return "redirect:/user/login";
				
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
		if (post.getBody().length() > 20971520) {
			notificationService.addErrorMessage("Body should be less than 20971520 (20MB) chars.");
			return "posts/edit";
		}
		
		System.out.println(post);
		
		if (post.getId() != null && post.getId() != 0) {
			post = postService.edit(post);
		} else {
			post.setAuthor((User) model.asMap().get("user"));			
			post.setPublicationDate(new Date());
			post = postService.create(post);
		}
		
		notificationService.addInfoMessage("Post created successfully");
		return "redirect:/post/edit/" + post.getId();
	}
	

    @RequestMapping("post/delete/{id}")
    public String delete(@PathVariable Long id, Model model){
		if (!model.containsAttribute("user")) return "redirect:/user/login";

        postService.deleteById(id);
        return "redirect:/post";
    }

}
