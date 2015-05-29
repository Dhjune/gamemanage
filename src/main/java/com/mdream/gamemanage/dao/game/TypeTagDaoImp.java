package com.mdream.gamemanage.dao.game;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mdream.gamemanage.model.resulttransformer.GameTypeTrans;
import com.mdream.gamemanage.model.resulttransformer.TypeTagTrans;

@Repository
public class TypeTagDaoImp {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public TypeTagTrans find(Integer tagId,Integer gameTypeId){
		
		Session session  = sessionFactory.getCurrentSession();	
		Query query =  session.createSQLQuery("select tagId,gameTypeId,status from type_tag_ref where tagId=? and gameTypeId=?");
		query.setParameter(0, tagId);
		query.setParameter(1, gameTypeId);
		query.setResultTransformer(Transformers.aliasToBean(TypeTagTrans.class));
		List<TypeTagTrans> list =  query.list();
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return  null;
		}
		
	}
	
	public void insert(TypeTagTrans trans){
		
		Session session  = sessionFactory.getCurrentSession();		
		Query query =  session.createSQLQuery("insert into type_tag_ref (gameTypeId,tagId,createTime) values(?,?,now())");
		query.setParameter(0, trans.getGameTypeId());
		query.setParameter(1, trans.getTagId());		
		query.executeUpdate();
				
	}
	
	public void update(TypeTagTrans trans){
		
		Session session  = sessionFactory.getCurrentSession();
		Query query =  session.createSQLQuery("update type_tag_ref set status = ?,modifyTime=now() where  gameTypeId=? and tagId =?");
		query.setParameter(0, trans.getStatus());		
		query.setParameter(1, trans.getGameTypeId());
		query.setParameter(2, trans.getTagId());
		query.executeUpdate();
		
	}
	
	public List<TypeTagTrans> listResult(){
		Session session  = sessionFactory.getCurrentSession();
		Query query =  session.createSQLQuery("select tagId,gameTypeId,status from type_tag_ref");
		query.setResultTransformer(Transformers.aliasToBean(TypeTagTrans.class));
		return query.list();
	}

	public void delete(int typeId,int tagId){
		
		Session session  =  sessionFactory.getCurrentSession();
		Query query =  session.createSQLQuery("delete from type_tag_ref where gameTypeId =? and tagId =?");
		query.setParameter(0, typeId);
		query.setParameter(1, tagId);
		query.executeUpdate();
			
	}
	
	public void deleteByTypeId(int typeId){
		
		Session session  =  sessionFactory.getCurrentSession();
		Query query =  session.createSQLQuery("delete from type_tag_ref where gameTypeId =?");
		query.setParameter(0, typeId);
		query.executeUpdate();
		
	}
	
	public void delteByTagId(int tagId){

		Session session  =  sessionFactory.getCurrentSession();
		Query query =  session.createSQLQuery("delete from type_tag_ref where tagId =?");
		query.setParameter(0, tagId);
		query.executeUpdate();
		
	}
	
}
