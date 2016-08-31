package blog.models;

import java.util.Date;

import javax.persistence.*;


@Entity
@Table(name = "posts")
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 500)
	private String title;
	
	@Lob
	@Column(nullable = false, length = 20971520)
	private String body; 
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private User author;
	
	@Column(nullable = false)
	private Date publicationDate = new Date();
	
	public Post() {
		
	}
	
	public Post(Long id, String title, String body, User author){
		this.id = id;
		this.title = title;
		this.body = body;
		this.author = author;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	
	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationData) {
		this.publicationDate = publicationData;
	}
		
	@Override
	public String toString() {
		return "Post{" +
				"id=" + id + 
				", title='" + title + '\'' + 
				", body='" + body + '\'' + 
				", author=" + author + 
				", date=" + publicationDate + 
				'}';
	}
	

}
