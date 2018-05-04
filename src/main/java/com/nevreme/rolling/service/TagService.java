package com.nevreme.rolling.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nevreme.rolling.dao.TagDao;
import com.nevreme.rolling.model.Tag;


@Service
public class TagService extends AbstractService<Tag, Long>{

	@Autowired
	public TagService(TagDao iDao) {
		super(iDao);
	}

}
