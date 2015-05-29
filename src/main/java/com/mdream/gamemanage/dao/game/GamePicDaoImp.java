package com.mdream.gamemanage.dao.game;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mdream.gamemanage.model.game.GamePic;
import com.mdream.gamemanage.model.resulttransformer.GamePicTrans;

@Repository
public class GamePicDaoImp {

	@Autowired
	private SessionFactory sessionFactory;
	
	
	public void  insert(GamePic pic,Integer gameId){
		
		Session session  =  sessionFactory.getCurrentSession();
		Query query =  session.createSQLQuery("insert into game_pic(gameId,picUrl,picPath,sort,createTime) values(?,?,?,?,now())");
		query.setParameter(0, gameId);
		query.setParameter(1, pic.getPicUrl());
		query.setParameter(2, pic.getPicPath());
		query.setParameter(3, pic.getSort());
		query.executeUpdate();
		
		
	}
	
	public void update(GamePic pic,Integer gameId){
		
		Session session  =  sessionFactory.getCurrentSession();
		
		Query query =  session.createSQLQuery("update game_pic set gameId=?,sort=?,modifyTime=now(),picUrl=?,picPath=? where id=?");
		query.setParameter(0, gameId);
		query.setParameter(1, pic.getSort());
		query.setParameter(2, pic.getPicUrl());
		query.setParameter(3, pic.getPicPath());
		query.setParameter(4, pic.getId());
		query.executeUpdate();
		
	}

	@SuppressWarnings("unchecked")
	public List<GamePicTrans> listResult() {
		
		Session session  =  sessionFactory.getCurrentSession();
		Query query =  session.createSQLQuery("select id,gameId,picUrl,picPath,sort from  game_pic");
		query.setResultTransformer(Transformers.aliasToBean(GamePicTrans.class));
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public GamePicTrans find(GamePicTrans trans) {
		Session session  =  sessionFactory.getCurrentSession();
		Query query =  session.createSQLQuery("select id,gameId,picUrl,picPath,sort from  game_pic where id=?");
		query.setParameter(0, trans.getId());
		query.setResultTransformer(Transformers.aliasToBean(GamePicTrans.class));
		List<GamePicTrans> list =  query.list();
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return  null;
		}
	}

	public void update(GamePicTrans trans) {
		
		Session session  =  sessionFactory.getCurrentSession();		
		Query query =  session.createSQLQuery("update game_pic set sort=?,modifyTime=now(),picUrl=?,picPath=? where id=?");
		
		query.setParameter(0, trans.getSort());
		query.setParameter(1, trans.getPicUrl());
		query.setParameter(2, trans.getPicPath());
		query.setParameter(3, trans.getId());
		query.executeUpdate();
	}
	
	public void delete(int id){
		Session session =  sessionFactory.getCurrentSession();
		Query query =  session.createSQLQuery("delete  from  game_pic where id=?");
		query.setParameter(0, id);
		query.executeUpdate();
		
	}
	
	public void deleteBygameId(int gameId){
		Session session =  sessionFactory.getCurrentSession();
		Query query =  session.createSQLQuery("delete  from  game_pic where gameId=?");
		query.setParameter(0, gameId);
		query.executeUpdate();
		
		
		
	}
	
	
}
