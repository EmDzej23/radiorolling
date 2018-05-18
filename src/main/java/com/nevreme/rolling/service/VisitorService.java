package com.nevreme.rolling.service;

import com.nevreme.rolling.dao.VisitorDao;
import com.nevreme.rolling.model.Visitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisitorService extends AbstractService<Visitor, Long> {

    @Autowired
    private VisitorDao visitorDao;

    @Autowired
    public VisitorService(VisitorDao visitorDao) {
        super(visitorDao);
    }


}