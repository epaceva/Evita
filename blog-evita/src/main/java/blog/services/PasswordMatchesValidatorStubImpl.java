package blog.services;

import org.springframework.stereotype.Service;

@Service
public class PasswordMatchesValidatorStubImpl implements PasswordMatchesValidator{

	@Override
	public boolean authenticate(String password, String repeatPassword) {
		return password == repeatPassword;
	}

}
