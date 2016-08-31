package blog.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 30, unique = true)
	private String username;
	
	@Column(length = 60)
	private String passwordHash;
	
	@Column(length = 60)
	private String repeatPasswordHash;
	
	@Column(length = 100)
	private String firstName;
	
	@Column(length = 100)
	private String lastName;
	
	@Column()
	private boolean isAdmin = false;
	
	@OneToMany(mappedBy = "author")
	private Set<Post> posts = new HashSet<>();

	public User() {
		isAdmin = false;
	}
	
	public User(String username, String firstName, String lastName) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		isAdmin = false;
	}

	
	public User(Long id, String username, String firstName, String lastName){
		this.id = id;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		isAdmin = false;		
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public boolean isAdmin() {
		return isAdmin;
	}
	
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPasswordHash() {
		return passwordHash;
	}
	
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	public String getRepeatPasswordHash() {
		return repeatPasswordHash;
	}
	
	public void setRepeatPasswordHash(String repeatPasswordHash) {
		this.repeatPasswordHash = repeatPasswordHash;
	}
	
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
	
	public Set<Post> getPosts() {
		return posts;
	}
	
	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}
	
	@Override
	public String toString(){
		return "User{" + "id" + id +
				", username='" + username + '\'' +
				", passwordHash='" + passwordHash + '\'' +
				", firstName='" + firstName + '\'' + 
				", lastName='" + lastName + '\'' + '}';
	}
}

