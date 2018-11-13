package com.nevreme.rolling.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nevreme.rolling.dao.HtmlTemplateDao;
import com.nevreme.rolling.model.HtmlTemplate;

@Service
public class HtmlTemplateService extends AbstractService<HtmlTemplate, Long>{

	@Autowired
	public HtmlTemplateService(HtmlTemplateDao iDao) {
		super(iDao);
	}
}
