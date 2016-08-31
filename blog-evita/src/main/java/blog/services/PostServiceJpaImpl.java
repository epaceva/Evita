package blog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import blog.models.Post;
import blog.models.User;
import blog.repositories.PostRepository;

@Service
@Primary
public class PostServiceJpaImpl implements PostService {

	@Autowired
	private PostRepository postRepo;

	@Override
	public List<Post> findAll() {
		return this.postRepo.findAll();
	}

	@Override
	public List<Post> findByPublicationDate() {
		return this.postRepo.findByPublicationDate(new PageRequest(0, 5));
	}

	@Override
	public Page<Post> findByAuthor(User user, Pageable pageable) {
		return postRepo.findByAuthor(user, pageable);
	}
	
	public Page<Post> getPosts(Integer page, Integer size) {
		PageRequest request = new PageRequest(page, size, Sort.Direction.DESC, "publicationDate");
		return postRepo.findAll(request);
	}

	@Override
	public Page<Post> getPosts(Pageable pageable) {
		return postRepo.findAll(pageable);
	}

	@Override
	public Post findById(Long id) {
		return this.postRepo.findOne(id);
	}

	@Override
	public Post create(Post post) {
		return this.postRepo.save(post);
	}

	@Override
	public Post edit(Post post) {
		return this.postRepo.save(post);
	}

	@Override
	public void deleteById(Long id) {
		this.postRepo.delete(id);

	}

}
