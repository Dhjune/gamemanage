package com.mdream.gamemanage.dao.client;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mdream.gamemanage.common.api.BaseDaoItl;
import com.mdream.gamemanage.model.client.PushGame;

@Repository
public class PushGameDaoImp implements BaseDaoItl<PushGame>{

	@Autowired
	private SessionFactory sessionFactory;
	
	
	
	public void delete(int id) {
		
		Session session =  sessionFactory.getCurrentSession();
		Query query =  session.createSQLQuery("update push_game set status = 0 where pg_id=?");
		query.setParameter(0, id);
		query.executeUpdate();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Integer getCountByType(int type) {
		int count = 0;			
		Session session =  sessionFactory.getCurrentSession();
		Query query =  session.createSQLQuery("select count(*) from push_game where  pg_type = ?");
		query.setParameter(0, type);
		List list =  query.list();		
		if(list != null&& list.size()>0){								
			count = Integer.parseInt(list.get(0).toString());
		}
		return count;
	}

	

	public void saveOrUpdate(PushGame pg) {
		Session session =  sessionFactory.getCurrentSession();
		if(pg.getId()!=null&&pg.getId()>0){
			pg.setModifyTime(new Date());
		}else{
			pg.setModifyTime(new Date());
		}
		session.saveOrUpdate(pg);				
	}

	
	
	
	
}
