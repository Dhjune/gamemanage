package com.mdream.gamemanage.dao.game;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GameTypeDaoImp {

	@Autowired
	private SessionFactory sessionFactory;
														
	public void delete(int id) throws Exception{
		
		Session  session =  sessionFactory.getCurrentSession();
		Query query =  session.createSQLQuery("update game_type set status=0 where id=? ");
		query.setParameter(0, id);
		query.executeUpdate();
		
	}

}
