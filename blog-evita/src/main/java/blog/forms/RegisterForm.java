package blog.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegisterForm {
	
	@NotNull
    @Size(min = 2, max = 30, message = "Please enter your First Name")
    private String firstName;
     
    @NotNull
    @Size(min = 2, max = 30, message = "Please enter your Last Name")
    private String lastName;

	@Size(min=2, max=30, message = "Please enter username")
	private String username;
	
	@NotNull
	@Size(min=2, max=30, message = "Please enter password")
	private String password;
	
	@NotNull
	@Size(min=2, max=30, message = "Please confirm password")
	private String repeatPassword;
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRepeatPassword() {
		return repeatPassword;
	}

	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}
	
	

}
