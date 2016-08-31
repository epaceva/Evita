package blog; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import blog.models.User;
import blog.services.UserService;

@Service
public class AppListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private UserService userService;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		User user = userService.findByUsername("admin");
		if (user == null) {
			user = new User();
			user.setAdmin(true);
			user.setFirstName("Evelina");
			user.setLastName("Paceva");
			user.setPasswordHash("www");
			user.setRepeatPasswordHash("www");
			user.setUsername("admin");
			userService.create(user);
		}
	}
	
}
