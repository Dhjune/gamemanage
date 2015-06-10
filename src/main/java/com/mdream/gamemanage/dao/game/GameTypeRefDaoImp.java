package com.mdream.gamemanage.dao.game;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mdream.gamemanage.model.resulttransformer.GamePicTrans;
import com.mdream.gamemanage.model.resulttransformer.GameTypeTrans;

@Repository
public class GameTypeRefDaoImp {

	
	@Autowired
	private SessionFactory sessionFactory;
	
	public GameTypeTrans find(Integer gameId,Integer gameTypeId){
		
		Session session  = sessionFactory.getCurrentSession();	
		Query query =  session.createSQLQuery("select gameId,gameTypeId,status from game_type_ref where gameId=? and gameTypeId=?");
		query.setParameter(0, gameId);
		query.setParameter(1, gameTypeId);
		query.setResultTransformer(Transformers.aliasToBean(GameTypeTrans.class));
		List<GameTypeTrans> list =  query.list();
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return  null;
		}
		
	}
	
	public void insert(int gameId,int gameTypeId){
		
		Session session  = sessionFactory.getCurrentSession();		
		Query query =  session.createSQLQuery("insert into game_type_ref (gameId,gameTypeId,createTime) values(?,?,now())");
		query.setParameter(0, gameId);
		query.setParameter(1, gameTypeId);		
		query.executeUpdate();
				
	}
	
	public void update(GameTypeTrans trans){
		
		Session session  = sessionFactory.getCurrentSession();
		Query query =  session.createSQLQuery("update game_type_ref set status = ?,modifyTime=now() where  gameId=? and gameTypeId =?");
		query.setParameter(0, trans.getStatus());
		query.setParameter(1, trans.getGameId());
		query.setParameter(2, trans.getGameTypeId());
		query.executeUpdate();
		
	}
	
	public List<GameTypeTrans> listResult(){
		Session session  = sessionFactory.getCurrentSession();
		Query query =  session.createSQLQuery("select gameId,gameTypeId,status from game_type_ref");
		query.setResultTransformer(Transformers.aliasToBean(GameTypeTrans.class));
		return query.list();
	}

	public void delete(int gameId, int gameTypeId) {
		
		Session session  = sessionFactory.getCurrentSession();
		Query query =  session.createSQLQuery("delete from  game_type_ref  where  gameId=? and gameTypeId =?");
		query.setParameter(0, gameId);
		query.setParameter(1, gameTypeId);
		query.executeUpdate();
		
	}
	
	
	
	
	public void deleteBygameId(int gameId){
		
		Session session  = sessionFactory.getCurrentSession();
		Query query =  session.createSQLQuery("delete from  game_type_ref  where  gameId=? ");
		query.setParameter(0, gameId);
		query.executeUpdate();
				
	}
	
	public void deleteByTypeId(int typeId){
		
		Session session  = sessionFactory.getCurrentSession();
		Query query =  session.createSQLQuery("delete from  game_type_ref  where gameTypeId =?");		
		query.setParameter(0, typeId);
		query.executeUpdate();
		
	}
	
	public void fakedelete(int gameId, int gameTypeId){
		Session session  = sessionFactory.getCurrentSession();
		Query query =  session.createSQLQuery("update game_type_ref set status=0  where  gameId=? and gameTypeId =?");
		query.setParameter(0, gameId);
		query.setParameter(1, gameTypeId);
		query.executeUpdate();
	}
	
	public void fakeDeleteBygameId(int gameId){
		Session session  = sessionFactory.getCurrentSession();
		Query query =  session.createSQLQuery("update game_type_ref set status=0  where  gameId=? and status = 1");
		query.setParameter(0, gameId);
		query.executeUpdate();
	}
	
	public void fakeDeleteByTypeId(int typeId){
		Session session  = sessionFactory.getCurrentSession();
		Query query =  session.createSQLQuery("update game_type_ref set status=0  where  gameTypeId=? and status = 1");
		query.setParameter(0, typeId);
		query.executeUpdate();
	}
	
}
