package com.nevreme.rolling.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nevreme.rolling.dao.CardDao;
import com.nevreme.rolling.model.Card;

@Service
public class CardService extends AbstractService<Card, Long>{

	@Autowired
	CardDao dao;
	
	@Autowired
	public CardService(CardDao iDao) {
		super(iDao);
	}

	public List<Card> findAllInRange(int start, int max) {
		return dao.findAllInRange(start, max);
	}
}
