package com.nevreme.rolling.service;

import com.nevreme.rolling.dao.QuestionDao;
import com.nevreme.rolling.dao.sql.SqlBuilder;
import com.nevreme.rolling.model.Card;
import com.nevreme.rolling.model.Question;

import java.util.List;

import javax.persistence.TypedQuery;

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

    public List<Question> findAllInRange(int start, int max) {
		return questionDao.findAllInRange(start, max);
	}
    
    public Question findOneEagerlyActive() {
    	return questionDao.findOneEagerlyActive();
    }

}