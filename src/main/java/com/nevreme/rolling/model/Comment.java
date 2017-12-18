package com.nevreme.rolling.model;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Component
@Entity
@Table(name="comment")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class , property = "id")
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(length=250, name = "text")
 	private String text;
	
	@Column(name = "comm_date")
	private Timestamp date;
	
	@Column(name = "comment_type")
	private CommentType comment_type;
	
	@ManyToOne
	@JoinColumn(name="comment_author")
//	@JsonManagedReference(value="com_aut")
	private User comment_author;
	
	@ManyToOne
    @JoinColumn(name="comment_id")
//	@JsonManagedReference(value="c_comments")
	private Comment comment;
	
	@OneToMany(mappedBy="comment")
//	@JsonBackReference(value="c_comments")
	private Set<Comment> comments;
	
	@ManyToOne
    @JoinColumn(name="post_id")
//	@JsonManagedReference(value="p_comments")
	private Post post;
	@Column(name = "upvote_count")
	private int upvoteCount;
	@Column(name = "user_has_upvoted")
	private int userHasUpvoted;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public CommentType getComment_type() {
		return comment_type;
	}

	public void setComment_type(CommentType comment_type) {
		this.comment_type = comment_type;
	}

	public User getComment_author() {
		return comment_author;
	}

	public void setComment_author(User comment_author) {
		this.comment_author = comment_author;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public int getUpvoteCount() {
		return upvoteCount;
	}

	public void setUpvoteCount(int upvoteCount) {
		this.upvoteCount = upvoteCount;
	}

	public int isUserHasUpvoted() {
		return userHasUpvoted;
	}

	public void setUserHasUpvoted(int userHasUpvoted) {
		this.userHasUpvoted = userHasUpvoted;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}
	
}
