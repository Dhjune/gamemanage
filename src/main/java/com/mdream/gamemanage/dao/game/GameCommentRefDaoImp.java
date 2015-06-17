package com.mdream.gamemanage.dao.game;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mdream.gamemanage.model.game.GameCommentRef;
import com.mdream.gamemanage.model.resulttransformer.GameCommentTrans;

@Repository
public class GameCommentRefDaoImp {

	@Autowired
	private SessionFactory sessionFactory;
	
	public GameCommentTrans find(Integer gameId,Integer commentId){
		
		Session session  = sessionFactory.getCurrentSession();	
		Query query =  session.createSQLQuery("select gameId,commentId,count,status from game_comment_ref where gameId=?  and commentId=?");
		query.setParameter(0, gameId);
		query.setParameter(1, commentId);
		query.setResultTransformer(Transformers.aliasToBean(GameCommentTrans.class));
		List<GameCommentTrans> list =  query.list();
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return  null;
		}
		
	}
	
	public GameCommentRef get(Integer gameId,Integer commentId){
		GameCommentRef ref =null;
		Session session  = sessionFactory.getCurrentSession();
		Query  query  =  session.createQuery("from GameCommentRef ref where ref.pk.game="+gameId+" and ref.pk.comment="+ commentId);
        List<GameCommentRef> list = query.list();
        if(list.size()>0){
        	ref= list.get(0);    
        	ref.getPk();
        }
		return ref;
		
	}
	
	public void insert(GameCommentTrans trans){
		Session session  = sessionFactory.getCurrentSession();		
		Query query =  session.createSQLQuery("insert into game_comment_ref (gameId,commentId,count,createTime) values(?,?,?,now())");
		query.setParameter(0, trans.getGameId());
		query.setParameter(1, trans.getCommentId());	
		query.setParameter(2, trans.getCount());	
		query.executeUpdate();
	}
	
	
	
	public void update(GameCommentTrans trans){
		
		Session session  = sessionFactory.getCurrentSession();
		Query query =  session.createSQLQuery("update game_comment_ref set count =?,status = ?,modifyTime=now() where  gameId=? and commentId =?");
		query.setParameter(0, trans.getCount());
		query.setParameter(1, trans.getStatus());
		query.setParameter(2, trans.getGameId());
		query.setParameter(3, trans.getCommentId());
		query.executeUpdate();
		
		
	}
	
	public List<GameCommentTrans> listResult(){
		
		Session session  = sessionFactory.getCurrentSession();
		Query query =  session.createSQLQuery("select gameId,commentId,count,status from game_comment_ref");
		query.setResultTransformer(Transformers.aliasToBean(GameCommentTrans.class));
		return query.list();
		
	}
	
	
	
	public void delete(Integer gameId,Integer commentId){
		Session session  = sessionFactory.getCurrentSession();		
		Query query =  session.createSQLQuery("delete from game_comment_ref where gameId=? and commentId=?");
		query.setParameter(0, gameId);
		query.setParameter(1, commentId);	
		query.executeUpdate();
	}
	
	public void fakeDelete(Integer gameId,Integer commentId){
		Session session  = sessionFactory.getCurrentSession();		
		Query query =  session.createSQLQuery("update  game_comment_ref set status = 0 where gameId=? and commentId=?");
		query.setParameter(0, gameId);
		query.setParameter(1, commentId);	
		query.executeUpdate();
	}
	
	
	public void deleteBygameId(int gameId){
		Session session  = sessionFactory.getCurrentSession();		
		Query query =  session.createSQLQuery("delete from game_comment_ref where gameId=?");
		query.setParameter(0, gameId);
		query.executeUpdate();
	}
	
	public void fakeDeleteBygameId(int gameId){
		
		Session session  = sessionFactory.getCurrentSession();		
		Query query =  session.createSQLQuery("delete from game_comment_ref where gameId=?");
		query.setParameter(0, gameId);
		query.executeUpdate();
	}
	
	public void deleteBycommentId(int commentId){
		Session session  = sessionFactory.getCurrentSession();		
		Query query =  session.createSQLQuery("update game_comment_ref set status = 0 where commentId=?");
		query.setParameter(0, commentId);
		query.executeUpdate();
	}
	
	public void fakeDeleteBycommentId(int commentId){
		
		Session session  = sessionFactory.getCurrentSession();		
		Query query =  session.createSQLQuery("update game_comment_ref set status=0 where commentId=?");
		query.setParameter(0, commentId);
		query.executeUpdate();
	}
	
}
