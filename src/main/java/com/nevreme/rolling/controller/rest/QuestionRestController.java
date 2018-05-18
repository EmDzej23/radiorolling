package com.nevreme.rolling.controller.rest;

import com.nevreme.rolling.dto.QuestionDto;
import com.nevreme.rolling.model.Question;
import com.nevreme.rolling.service.AbstractService;
import com.nevreme.rolling.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/public/api/question/")
public class QuestionRestController extends AbstractRestController<Question, QuestionDto, Long> {
    @Autowired
    private QuestionService questionService;

    @Autowired
    public QuestionRestController(AbstractService<Question, Long> repo, QuestionDto dto) {
        super(repo, dto);
    }
}
