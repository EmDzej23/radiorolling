package com.nevreme.rolling.dao;

import com.nevreme.rolling.model.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AnswerDao extends AbstractDao<Answer, Long> {
    @Autowired
    public AnswerDao(Answer clazz) {
        super(clazz);
    }

    @Override
    public Long count() {
        return null;
    }

    @Override
    public boolean exists(Long primaryKey) {
        return false;
    }

}