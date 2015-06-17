package com.mdream.gamemanage.dao.game;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mdream.gamemanage.model.game.Game;

@Repository
public class GameDaoImp {

	@Autowired
	private SessionFactory sessionFactory;

	public void delete(int id) throws Exception{
		
		Session session  =  sessionFactory.getCurrentSession();
		Query query =  session.createSQLQuery("update game set status=0 where id=?");
		query.setParameter(0, id);
		query.executeUpdate();
		
	}

	public void update(Game game) {
		
		Session session  =  sessionFactory.getCurrentSession();
		Query query =  session.createSQLQuery("update game set status=0 where id=?");
		query.setParameter(0, game.getId());
		query.executeUpdate();
		
	}

	public void recover(int id) {
		Session session  =  sessionFactory.getCurrentSession();
		Query query =  session.createSQLQuery("update game set status=1 where id=?");
		query.setParameter(0, id);
		query.executeUpdate();
	}

	public boolean exist(Game game) {
		
		Session session  =  sessionFactory.getCurrentSession();
		Query query =  session.createSQLQuery("SELECT count(*) count FROM game WHERE name = ?");
		query.setParameter(0, game.getName());
		List list = query.list();
		if(list!=null && list.size()>0){
			return true;
		}
		return false;
		
		
	}

	public void fakeDelete(int id) {
		Session session  =  sessionFactory.getCurrentSession();
		Query query =  session.createSQLQuery("update game set status=0 where id=?");
		query.setParameter(0, id);
		query.executeUpdate();
	}
	
	
	
	
}
