package com.nevreme.rolling.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.nevreme.rolling.dao.sql.SqlBuilder;

public abstract class AbstractDao<T, ID extends Serializable> {
	@PersistenceContext
	private EntityManager entityManager;

	private T clazz;

	public AbstractDao(T clazz) {
		this.clazz = clazz;
	}

        // @todo - ovo bi trebalo da bude persist, inace se ne razlikuje od update s...
	public T save(T entity) {
		return entityManager.merge(entity);
	}

	public T update(T entity) {
		return entityManager.merge(entity);
	}

	public T findOne(ID primaryKey) {
		String sql = new SqlBuilder().select(clazz.getClass(), true).where("id").build();
		@SuppressWarnings("unchecked")
		T t = (T) entityManager.createQuery(sql, clazz.getClass()).setParameter("id", primaryKey).getSingleResult();
		return t;
	}

	public T findOneEagerly(ID primaryKey) {
		String sql = new SqlBuilder().select(clazz.getClass(), true).where("id").build();
		@SuppressWarnings("unchecked")
		T t = (T) entityManager.createQuery(sql, clazz.getClass()).setParameter("id", primaryKey).getSingleResult();
		return t;
	};
	public List<T> findAllEagerly() {
		String sql = new SqlBuilder().select(clazz.getClass(), true).build();
		@SuppressWarnings("unchecked")
		List<T> t = (List<T>) entityManager.createQuery(sql, clazz.getClass()).getResultList();
		return t;
	};
	public List<T> findAll() {
		String sql = new SqlBuilder().select(clazz.getClass(), true).build();
		@SuppressWarnings("unchecked")
		List<T> t = (List<T>) entityManager.createQuery(sql, clazz.getClass()).getResultList();
		return t;
	};
	
	public void delete(ID id) {
		T entity = findOne(id);
		this.entityManager.remove(entity);
	}
	public void saveWithSql(String sql){
		SqlBuilder sqlBuilder = new SqlBuilder();
		Object[] values = sqlBuilder.getValues(sql);
		Query query = entityManager.createNativeQuery(sqlBuilder.insertQuery(clazz.getClass(), sql));
		for (int i = 0;i<values.length;i++) {
			query.setParameter((i+1), ""+values[i]+"");	
		}
		query.executeUpdate();
	};
	
	public void updateWithSql(String sql, String id){
		String query = new SqlBuilder().update(clazz.getClass()).updateValues(sql, id).updateBuild();
		System.out.println(query);
		entityManager.createNativeQuery(query).executeUpdate();
	};
	
	public abstract Long count();
	public abstract boolean exists(ID primaryKey);
//	public abstract boolean isUsernameUnique(ID primaryKey);
//	public abstract T findByUsername(String user);
}
