package com.nevreme.rolling.service;

import com.nevreme.rolling.dao.QuestionDao;
import com.nevreme.rolling.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("questionService")
public class QuestionService extends AbstractService<Question, Long> {

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    public QuestionService(QuestionDao questionDao) {
        super(questionDao);
    }


}