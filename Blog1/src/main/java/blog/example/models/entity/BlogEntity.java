package blog.example.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name="blog")
public class BlogEntity {
	public BlogEntity(String blogTitle, String fileName, int account_id,  Integer userId,Integer fav) {
		this.blogTitle = blogTitle;
		this.blogImage = fileName;
		this.account_id = account_id;		
		this.userId = userId;
		this.fav =fav;
	}

	@Id
	@Column(name="blog_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int blogId;

	@NonNull
	@Column(name="title")
	private String blogTitle;

	@NonNull
	@Column(name="blog_image")
	private String blogImage;

	@Column(name="account_id")
	private int account_id;

	@NonNull
	@Column(name="message")
	private String message;

	@Column(name="user_id")
	private Integer userId;
	
	@Column(name="fav")
	private Integer fav;
	
}
