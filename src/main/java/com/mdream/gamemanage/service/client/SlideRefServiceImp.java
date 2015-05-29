package com.mdream.gamemanage.service.client;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mdream.gamemanage.common.proxy.hibernate.criteria.ExpressionGroup;
import com.mdream.gamemanage.common.proxy.hibernate.criteria.HibernateResolversService;
import com.mdream.gamemanage.dao.client.SlideRefDaoImp;
import com.mdream.gamemanage.model.client.SlideRef;
import com.mdream.gamemanage.model.game.Tag;
import com.mdream.gamemanage.model.resulttransformer.SlideShowTrans;

@Service
@Transactional(value=TxType.NOT_SUPPORTED)
public class SlideRefServiceImp {
	
	@Autowired
	private HibernateResolversService hibernateResolversService;
	
	
	
	@Autowired
	private SlideRefDaoImp  slideRefDaoImp ;
	
	public SlideRef get(int slideId,int showId){
		
		return  slideRefDaoImp.get(slideId, showId);
		
	}
	
	
	
	public <T> Map list(List<ExpressionGroup> list, T target, int pageIndex,int pageSize) throws NoSuchFieldException, SecurityException, ParseException{
			
			//分页结果集
		//	setResultTransformer(Transformers.aliasToBean(StatCountbook.class));
			Criteria criteria =  hibernateResolversService.getCriteria(list, target);	
			criteria.add( Restrictions.eq(criteria.getAlias()+".status",1));
			criteria.setFirstResult((pageIndex-1)*pageSize);
			criteria.setMaxResults(pageSize);
			List<T> result = criteria.list();			
			//记录数 
			criteria.setProjection( Projections.projectionList()
				.add( Projections.countDistinct(criteria.getAlias()+".id"))		
			);
			criteria.setFirstResult(0);
			criteria.setMaxResults(1);
			List counts = criteria.list();
			long total = 0; 
			if(counts!=null && counts.size()>0){
				
				total = (Long) counts.get(0);			 
				
			}
			
			Map map =  new HashMap();
			map.put("result", result);
			map.put("total", total);
			
			return map;
			
	}

	public void save(SlideRef ref) throws Exception{
		hibernateResolversService.save(ref);
		
	}

	public void update(SlideRef ref) throws Exception{
		hibernateResolversService.update(ref);
		
	}

	public void delete(int slideId,int showId) throws Exception{
		
		SlideRef ref = slideRefDaoImp.get(slideId, showId);
		ref.setStatus(0);
		hibernateResolversService.update(ref);
	}
	
	public void delete(SlideShowTrans trans){
		slideRefDaoImp.delete(trans.getSlideId(), trans.getSlideShowId());;
	}
	
	public void insert(SlideShowTrans trans){
		slideRefDaoImp.insert(trans);
	}
	
	public void update(SlideShowTrans trans){
		slideRefDaoImp.update(trans);
	}
	
	public void merge(SlideShowTrans trans){
		slideRefDaoImp.merge(trans);
	}
	
	public List<SlideShowTrans> listResult(){
		return slideRefDaoImp.listResult();
	}
	
}
