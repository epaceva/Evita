package blog.services;

import java.util.Objects;

import org.springframework.stereotype.Service;

@Service
public class DuplicateUserStubImpl implements DuplicateUser{
	@Override
	public boolean authenticate(String user, String newUser) {
		
		return Objects.equals(user, newUser);
	}
	
}
