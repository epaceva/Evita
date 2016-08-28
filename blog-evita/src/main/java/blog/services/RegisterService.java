package blog.services;

public interface RegisterService {
	boolean authenticate(String firstName, String lastName, String username, String password, String repeatPassword);

}
