package blog.services;

import java.util.Objects;

import org.springframework.stereotype.Service;

@Service
public class LoginServiceStubImp implements LoginService {

	@Override
	public boolean authenticate(String username, String password) {
		
		return Objects.equals(username, password);
	}
}
