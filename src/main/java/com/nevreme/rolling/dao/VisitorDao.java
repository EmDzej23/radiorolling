package com.nevreme.rolling.dao;

import com.nevreme.rolling.dao.sql.SqlBuilder;
import com.nevreme.rolling.model.Video;
import com.nevreme.rolling.model.Visitor;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class VisitorDao extends AbstractDao<Visitor, Long> {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public VisitorDao(Visitor clazz) {
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

    @Override
    public Visitor findOneEagerly(Long primaryKey) {
        String sql = new SqlBuilder().select(Visitor.class, true).fetch("answers").build();
        return entityManager.createQuery(sql, Visitor.class).getSingleResult();
    }
    
    public Visitor findByVisitorId(String id) {
    	TypedQuery<Visitor> tq = entityManager
				.createQuery(new SqlBuilder().select(Visitor.class, true).where("visitorId").build(), Visitor.class);
		tq.setParameter("visitorId", id);
		return tq.getResultList().isEmpty()?null:tq.getSingleResult();
    }

    @Override
    public List<Visitor> findAllEagerly() {
        String sql = new SqlBuilder().select(Visitor.class, true).fetch("answers").build();
        return entityManager.createQuery(sql, Visitor.class).getResultList();
    }

}