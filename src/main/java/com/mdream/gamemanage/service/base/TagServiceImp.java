package com.mdream.gamemanage.service.base;

import java.text.ParseException;
import java.util.Date;
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
import com.mdream.gamemanage.common.proxy.hibernate.criteria.HibernateResolvers;
import com.mdream.gamemanage.common.proxy.hibernate.criteria.HibernateResolversService;
import com.mdream.gamemanage.dao.game.TypeTagDaoImp;
import com.mdream.gamemanage.model.base.Application;
import com.mdream.gamemanage.model.game.Tag;

@Transactional(value=TxType.NOT_SUPPORTED)
@Service
public class TagServiceImp {
	
	@Autowired
	private HibernateResolversService hibernateResolversService;
	
	@Autowired
	private HibernateResolvers hibernateResolvers;
	
	@Autowired
	private TypeTagDaoImp typeTagDaoImp ;
	
	public Tag get(int id){
		
		return hibernateResolversService.find(Tag.class, id);
		
	}
	
	
	
	public <T> Map list(List<ExpressionGroup> list, T target, int pageIndex,int pageSize) throws NoSuchFieldException, SecurityException, ParseException{
			
			//分页结果集
		//	setResultTransformer(Transformers.aliasToBean(StatCountbook.class));
			Criteria criteria =  hibernateResolversService.getCriteria(list, target);	
			criteria.setCacheable(true);
			criteria.add( Restrictions.eq(criteria.getAlias()+".status",1));
			criteria.setFirstResult((pageIndex-1)*pageSize);
			criteria.setMaxResults(pageSize);
			criteria.setCacheable(true);
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
		tag.setCreateTime(new Date());
		hibernateResolversService.save(tag);
		
	}

	public void update(Tag tag) throws Exception{
		tag.setModifyTime(new Date());
		hibernateResolversService.update(tag);
		
	}

	public void delete(int id) throws Exception{
		
		Tag tag = hibernateResolversService.find(Tag.class, id);
		tag.setStatus(0);
		hibernateResolversService.update(tag);
		typeTagDaoImp.delteByTagId(id);
	//	hibernateResolversService.delete(hibernateResolversService.find(Tag.class, id));
		
	}
	
	public List<Tag> list(){
		
		return  hibernateResolvers.list(new Tag());
		
	}



	public void merge(Tag tag) throws Exception{
		hibernateResolversService.merge(tag);
		
	}



	
}
