package blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
public class SpringBlogMvcApp {

	//https://github.com/mtiger2k/pageableSpringBootDataJPA
	//https://www.javacodegeeks.com/2013/03/implement-bootstrap-pagination-with-spring-data-and-thymeleaf.html

	public static void main(String[] args) {
		SpringApplication.run(SpringBlogMvcApp.class, args);

	}

}
