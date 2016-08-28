package blog.services;

public interface PasswordMatchesValidator {
	boolean authenticate (String password, String repeatPassword);
}
