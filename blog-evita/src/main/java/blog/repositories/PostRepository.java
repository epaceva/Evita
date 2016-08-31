package blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import blog.models.Post;
import blog.models.User;

import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
	
	@Query("SELECT p FROM Post p LEFT JOIN FETCH p.author ORDER BY p.publicationDate DESC")
	List<Post> findByPublicationDate(Pageable pageabl);

    Page<Post> findAll(Pageable pageable);

    Page<Post> findByAuthor(User user, Pageable pageable);

}
