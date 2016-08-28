package blog.services;

public interface RegisterService {
	boolean authenticate(String username, String password, String repeatPassword);

}
