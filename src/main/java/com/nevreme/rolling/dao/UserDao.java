package com.nevreme.rolling.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.nevreme.rolling.dao.sql.SqlBuilder;
import com.nevreme.rolling.model.User;


@Repository
public class UserDao extends AbstractDao<User, Long> {

	@Autowired
	public UserDao(User clazz) {
		super(clazz);
	}

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

	public void flush() {
		entityManager.flush();
	}

	public void clear() {
		entityManager.clear();
	}

	public User save(User admin) {
		admin.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));
		admin.setActive(1);
		return entityManager.merge(admin);
	}
	
	public User findUserByEmail(String email) {
		String sql = new SqlBuilder().select(User.class, true).fetch("roles").where("username").build();
		List<User> users = entityManager.createQuery(sql, User.class).setParameter("username", email).getResultList();
		if (users.isEmpty()) {
			return null;
		}
		return users.get(0);
	}

	public Long count() {
		// TODO Auto-generated method stub
		return null;
	}

	public void delete(User admin) {
		if (this.entityManager.contains(admin)) {
			this.entityManager.remove(admin);
		} else {
			admin = findOne(admin.getId());
			this.entityManager.remove(admin);
		}
	}

	public boolean exists(Long primaryKey) {
		return findOne(primaryKey) != null;
	}

	@Override
	public User findOneEagerly(Long primaryKey) {
		if (primaryKey == null)
			return null;
		String sql = new SqlBuilder().select(User.class, true).fetch("roles").fetch("posts").fetch("comments").where("id").build();
		User admin = entityManager.createQuery(sql, User.class).setParameter("id", primaryKey).getSingleResult();
		return admin;
	}

	@Override
	public List<User> findAll() {
		String sql = new SqlBuilder().select(User.class, true).build();
		return entityManager.createQuery(sql, User.class).getResultList();
	}

	@Override
	public List<User> findAllEagerly() {
		String sql = new SqlBuilder().select(User.class, true).fetch("roles").fetch("comments").fetch("posts").build();
		return entityManager.createQuery(sql, User.class).getResultList();
	}

	@Override
	public User update(User admin) {
		User entity = findOne(admin.getId());
		if (entity != null) {
			if (!admin.getPassword().equals(entity.getPassword())) {
				admin.setPassword(bCryptPasswordEncoder.encode(entity.getPassword()));
			}
		}
		return entityManager.merge(admin);
	}

}

