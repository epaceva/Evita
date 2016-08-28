package blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import blog.models.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	

}
