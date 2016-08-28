package blog.services;

import java.util.Objects;

import org.springframework.stereotype.Service;

@Service
public class RegisterServiceStubImpl implements RegisterService {
	
	@Override
	public boolean authenticate(String firstName, String lastName, String username,  String password, String repeatPassword) {
		
		return Objects.equals( password, repeatPassword);
	}
}
