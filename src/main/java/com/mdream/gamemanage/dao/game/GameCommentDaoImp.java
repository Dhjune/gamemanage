package com.mdream.gamemanage.dao.game;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GameCommentDaoImp {

	@Autowired
	private SessionFactory sessionFactory;
	
	public void delete(int id) {
		Session session  =  sessionFactory.getCurrentSession();
		Query query =  session.createSQLQuery("update game_comment set status=0 where id=?");
		query.setParameter(0, id);
		query.executeUpdate();
		
		
	}

}
