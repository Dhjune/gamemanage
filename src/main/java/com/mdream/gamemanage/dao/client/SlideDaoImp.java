package com.mdream.gamemanage.dao.client;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mdream.gamemanage.model.client.Slide;
import com.mdream.gamemanage.model.resulttransformer.GamePicTrans;

@Repository
public class SlideDaoImp {
	@Autowired
	private SessionFactory sessionFactory;
	
	public void delete(int id) {
		
		Session session =  sessionFactory.getCurrentSession();
		Query query =  session.createSQLQuery("update slide set status = 0 where id=?");
		query.setParameter(0, id);
		query.executeUpdate();
	}

	public List<Slide> list(int type) {
		Session session =  sessionFactory.getCurrentSession();
		Query query =  session.createSQLQuery("select * from slide  where type=? and status = 1");
		query.setParameter(0, type);
		query.setResultTransformer(Transformers.aliasToBean(Slide.class));
		return query.list();
	}
}
