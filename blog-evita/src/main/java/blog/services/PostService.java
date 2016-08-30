package blog.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import blog.models.Post;


public interface PostService {
	List<Post> findAll();
	List<Post> findByPublicationDate();
	Post findById(Long id);
	Post create(Post post);
	Post edit(Post post);
	void deleteById(Long id);

	Page<Post> getPosts(Integer page, Integer size);
	Page<Post> getPosts(Pageable pageable);

}
