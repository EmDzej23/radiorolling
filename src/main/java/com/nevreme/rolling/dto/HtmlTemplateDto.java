package com.nevreme.rolling.dto;

import javax.persistence.Column;

import org.springframework.stereotype.Component;

@Component
public class HtmlTemplateDto {
	private Long id;
	private String hTemplate;
	private String name;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String gethTemplate() {
		return hTemplate;
	}
	public void sethTemplate(String hTemplate) {
		this.hTemplate = hTemplate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
