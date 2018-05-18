package com.nevreme.rolling.service;

import com.nevreme.rolling.dao.AnswerDao;
import com.nevreme.rolling.model.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerService extends AbstractService<Answer, Long> {

    @Autowired
    private AnswerDao answerDao;

    @Autowired
    public AnswerService(AnswerDao answerDao) {
        super(answerDao);
    }


}