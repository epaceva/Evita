package blog.services;

import java.util.Objects;

public class PasswordMatchesValidatorStubImp implements PasswordMatchesValidator{
	@Override
	public boolean authenticate(String password, String repeatPassword) {
		
		return Objects.equals(password, repeatPassword);
	}

}
