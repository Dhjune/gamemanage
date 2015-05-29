package com.mdream.gamemanage.service.game;

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
import com.mdream.gamemanage.dao.game.TypeTagDaoImp;
import com.mdream.gamemanage.model.game.Tag;
import com.mdream.gamemanage.model.resulttransformer.SlideShowTrans;
import com.mdream.gamemanage.model.resulttransformer.TypeTagTrans;

@Service
@Transactional(value=TxType.NOT_SUPPORTED)
public class TypeTagServiceImp {
	
	@Autowired
	private HibernateResolversService hibernateResolversService;
	
	@Autowired
	private TypeTagDaoImp typeTagDaoImp;
	
	public Tag get(int id){
		
		return hibernateResolversService.find(Tag.class, id);
		
	}
	
	public TypeTagTrans find(int tagId,int gameTypeId){
		return typeTagDaoImp.find(tagId, gameTypeId);
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

	public void save(Tag tag) throws Exception{
		hibernateResolversService.save(tag);
		
	}

	public void update(Tag tag) throws Exception{
		hibernateResolversService.update(tag);
		
	}

	public void delete(int id) throws Exception{
		
		hibernateResolversService.delete(hibernateResolversService.find(Tag.class, id));
		
	}
	
	public void delete(TypeTagTrans trans){
		typeTagDaoImp.delete(trans.getGameTypeId(), trans.getTagId());
	}
	
	public void insert(TypeTagTrans trans){
		
		TypeTagTrans tt =  find(trans.getTagId(),trans.getGameTypeId());
		
		if(tt!=null &&tt.getStatus()<1){
			tt.setStatus(1);
			update(tt);
		}else if(tt==null){	
			System.out.println();
			typeTagDaoImp.insert(trans);
		}
		
	}
	
	public void update(TypeTagTrans trans){
		
		typeTagDaoImp.update(trans);
		
	}
	
	public List<TypeTagTrans> listResult(){
		
		return typeTagDaoImp.listResult();
		
	}

	

}
