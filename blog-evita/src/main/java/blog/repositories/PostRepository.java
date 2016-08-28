package blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import blog.models.Post;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
	List<Post>findAllOrderByDateDesc();
	
	@Query("SELECT p FROM Post p LEFT JOIN FETCH p.author ORDER BY p.Date DESC")
	List<Post> findLatest5Posts(Pageable pageabl);

}
