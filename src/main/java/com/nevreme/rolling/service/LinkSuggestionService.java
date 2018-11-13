package com.nevreme.rolling.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nevreme.rolling.dao.LinkSuggestionDao;
import com.nevreme.rolling.model.LinkSuggestion;

@Service
public class LinkSuggestionService extends AbstractService<LinkSuggestion, Long>{

	@Autowired
	public LinkSuggestionService(LinkSuggestionDao iDao) {
		super(iDao);
	}

}
