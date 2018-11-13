package com.nevreme.rolling.model;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//import org.hibernate.annotations.Cache;
//import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Component
@Entity
@Table(name = "video", indexes = { @Index(columnList = "playlist_id", name = "idx_playlist_id"),
		@Index(columnList = "state", name = "idx_state") })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
//@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class Video {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String description;

	private Timestamp started;
	
	private Timestamp recommendDate;

	@Column(name="yt_id")
	private String ytId;

	private int duration;

	private int index_num;

	private int state;
	
	private String quote;
	
	@Column(name="daily_recommend")
	private int dailyRecommend;
	
	@Column(name="video_offset")
	private int offset;
	
	@ManyToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinTable(name = "post_tag",  joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
	private Set<Tag> tags;

	@ManyToOne
	@JoinColumn(name = "playlist_id")
	private Playlist playlist;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getStarted() {
		return started;
	}

	public void setStarted(Timestamp started) {
		this.started = started;
	}

	public String getYtId() {
		return ytId;
	}

	public void setYtId(String ytId) {
		this.ytId = ytId;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getIndex_num() {
		return index_num;
	}

	public void setIndex_num(int index_num) {
		this.index_num = index_num;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Playlist getPlaylist() {
		return playlist;
	}

	public void setPlaylist(Playlist playlist) {
		this.playlist = playlist;
	}

	public String getQuote() {
		return quote;
	}

	public void setQuote(String quote) {
		this.quote = quote;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getDailyRecommend() {
		return dailyRecommend;
	}

	public void setDailyRecommend(int dailyRecommend) {
		this.dailyRecommend = dailyRecommend;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public Timestamp getRecommendDate() {
		return recommendDate;
	}

	public void setRecommendDate(Timestamp recommendDate) {
		this.recommendDate = recommendDate;
	}
	
}
