package blog.services;

import java.util.List;

import blog.models.Post;


public interface PostService {
	List<Post> findAll();
	List<Post> findByPublicationDate();
	Post findById(Long id);
	Post create(Post post);
	Post edit(Post post);
	void deleteById(Long id);

}
