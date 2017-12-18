package com.nevreme.rolling.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.nevreme.rolling.dao.AbstractDao;


@Transactional
public abstract class AbstractService<T, ID extends Serializable> {

	private AbstractDao<T, ID> iDao;
	
	public AbstractService(AbstractDao<T, ID> iDao) {
		this.iDao = iDao;
	}

	public T save(T entity) {
		return iDao.save(entity);
	}

	public T findOne(ID primaryKey) {
		return iDao.findOne(primaryKey);
	}
	
	public T findOneEagerly(ID primaryKey) {
		return iDao.findOneEagerly(primaryKey);
	}

	public List<T> findAll() {
		return iDao.findAll();
	}

	public List<T> findAllEagerly() {
		return iDao.findAllEagerly();
	}
	
	public Long count() {
		return iDao.count();
	}

	public void delete(ID id) {
		iDao.delete(id);
	}

	public AbstractDao<T, ID> getiDao() {
		return iDao;
	}

	public void setiDao(AbstractDao<T, ID> iDao) {
		this.iDao = iDao;
	}
	
//	public T findByUsername(String user) {
//		return iDao.findByUsername(user);
//	}
	
//	public boolean isUsernameUnique(ID username) {
//		return iDao.isUsernameUnique(username);
//	}
	public void saveAsSql (String sql) {
		iDao.saveWithSql(sql);
	}
	public void updateAsSql (String sql, String id) {
		iDao.updateWithSql(sql, id);
	}
	
}

