package com.nevreme.rolling.dto;

import org.springframework.stereotype.Component;

@Component
public class LinkSuggestionDto {
	private Long id;
	private String link;
	private int linkType;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public int getLinkType() {
		return linkType;
	}
	public void setLinkType(int linkType) {
		this.linkType = linkType;
	}
	
}
