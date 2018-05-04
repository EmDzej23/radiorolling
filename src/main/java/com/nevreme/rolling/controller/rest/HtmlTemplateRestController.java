package com.nevreme.rolling.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nevreme.rolling.dto.HtmlTemplateDto;
import com.nevreme.rolling.model.HtmlTemplate;
import com.nevreme.rolling.service.AbstractService;

@Controller
@RequestMapping("/public/api/template/")
public class HtmlTemplateRestController extends AbstractRestController<HtmlTemplate, HtmlTemplateDto, Long> {

	@Autowired
	public HtmlTemplateRestController(AbstractService<HtmlTemplate, Long> repo, HtmlTemplateDto dto) {
		super(repo, dto);
	}

}
