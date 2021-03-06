package com.nevreme.rolling.dto;

import java.sql.Timestamp;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class VideoDto {
	private Long id;
	private String description;
	private Timestamp started;
	private String ytId;
	private int duration;
	private int index_num;
	private int state;
	private int offset;
	private String quote;
	private int dailyRecommend;
	private String plName;
	private Set<TagDto> tags;

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

	public String getPlName() {
		return plName;
	}

	public void setPlName(String plName) {
		this.plName = plName;
	}

	public Set<TagDto> getTags() {
		return tags;
	}

	public void setTags(Set<TagDto> tags) {
		this.tags = tags;
	}

}
