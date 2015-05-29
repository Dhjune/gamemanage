package com.mdream.gamemanage.dao.client;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mdream.gamemanage.model.client.SlideRef;
import com.mdream.gamemanage.model.resulttransformer.GameTypeTrans;
import com.mdream.gamemanage.model.resulttransformer.SlideShowTrans;
import com.mdream.gamemanage.model.resulttransformer.TypeTagTrans;


@Repository
public class SlideRefDaoImp {

	@Autowired
	private SessionFactory sessionFactory;
	
	public SlideRef get(int slideId ,int showId){
		
		Session session =  sessionFactory.getCurrentSession();
		Query query =session.createQuery("from SlideRef ref where ref.pk.slide="+slideId+" and ref.pk.slideShow="+showId);
		SlideRef ref =  null;
		List<SlideRef> list =  query.list();
		if(list!=null && list.size()>0){
			ref  =  list.get(0);
			ref.getPk();
		}
 		return ref;
		
	}
	
	
	public SlideShowTrans find(int slideId ,int showId){
		
		Session session  = sessionFactory.getCurrentSession();	
		Query query =  session.createSQLQuery("select slideId,slideShowId,status from slide_show_ref where slideId=?,and slideShowId=?");
		query.setParameter(0, slideId);
		query.setParameter(1, showId);
		query.setResultTransformer(Transformers.aliasToBean(SlideShowTrans.class));
		List<SlideShowTrans> list =  query.list();
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return  null;
		}
		
	}
	
	public void insert(SlideShowTrans trans){
		
		Session session  = sessionFactory.getCurrentSession();		
		Query query =  session.createSQLQuery("insert into slide_show_ref (slideShowId,slideId,createTime) values(?,?,now())");
		query.setParameter(0, trans.getSlideShowId());
		query.setParameter(1, trans.getSlideId());		
		query.executeUpdate();
				
	}
	
	public void update(SlideShowTrans trans){
		
		Session session  = sessionFactory.getCurrentSession();
		Query query =  session.createSQLQuery("update slide_show_ref set status = ?,modifyTime=now() where  slideShowId =?  and slideId=?");
		query.setParameter(0, trans.getStatus());		
		query.setParameter(1, trans.getSlideShowId());
		query.setParameter(2, trans.getSlideId());	
		query.executeUpdate();
		
	}
	
	public void merge(SlideShowTrans trans){
		Session session  = sessionFactory.getCurrentSession();
		Query query =  session.createSQLQuery("update slide_show_ref set sort = ?,modifyTime=now() where  slideShowId =?  and slideId=?");
		query.setParameter(0, trans.getSort());		
		query.setParameter(1, trans.getSlideShowId());
		query.setParameter(2, trans.getSlideId());	
		query.executeUpdate();
	}
	
	public List<SlideShowTrans> listResult(){
		Session session  = sessionFactory.getCurrentSession();
		Query query =  session.createSQLQuery("select  slideId,slideShowId,status from slide_show_ref");
		query.setResultTransformer(Transformers.aliasToBean(SlideShowTrans.class));
		return query.list();
	}
	
	public void delete(int slideId,int showId){
		Session session  =  sessionFactory.getCurrentSession();
		Query query =session.createSQLQuery("delete from slide_show_ref where slideShowId =? and slideId =?");
		query.setParameter(0, showId);
		query.setParameter(1, slideId);
		query.executeUpdate();
	}
	
	
	public void deleteBySlideId(int slideId){
		Session session  =  sessionFactory.getCurrentSession();
		Query query =session.createSQLQuery("delete from slide_show_ref where slideId =?");
		query.setParameter(0, slideId);
		query.executeUpdate();
	}
	
	public void deleteBySlideShowId(int showId){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("delete from slide_show_ref where slideShowId = ? ");
		query.setParameter(0, showId);
		query.executeUpdate();
	}


	
}
