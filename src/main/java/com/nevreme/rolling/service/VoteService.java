package com.nevreme.rolling.service;

import com.nevreme.rolling.dao.VoteDao;
import com.nevreme.rolling.model.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteService extends AbstractService<Vote, Long> {

    @Autowired
    private VoteDao voteDao;

    @Autowired
    public VoteService(VoteDao voteDao) {
        super(voteDao);
    }


}
