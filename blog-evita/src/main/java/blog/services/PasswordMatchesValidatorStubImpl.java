package blog.services;

import java.util.Objects;

import org.springframework.stereotype.Service;

@Service
public class PasswordMatchesValidatorStubImpl implements PasswordMatchesValidator{

	@Override
	public boolean authenticate(String password, String repeatPassword) {
		
		return Objects.equals(password, repeatPassword);
	}

}
