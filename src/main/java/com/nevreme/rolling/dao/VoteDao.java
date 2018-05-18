package com.nevreme.rolling.dao;

import com.nevreme.rolling.model.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class VoteDao extends AbstractDao<Vote, Long> {
    @Autowired
    public VoteDao(Vote clazz) {
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
