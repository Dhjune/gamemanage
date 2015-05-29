package com.mdream.gamemanage.service.base;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mdream.gamemanage.common.proxy.hibernate.criteria.ExpressionGroup;
import com.mdream.gamemanage.common.proxy.hibernate.criteria.HibernateResolversService;
import com.mdream.gamemanage.model.base.Application;

@Service
@Transactional(value=TxType.NOT_SUPPORTED)
public class ApplicationServiceImp {
	
	@Autowired
	private HibernateResolversService hibernateResolversService;
	
	public Application get(int id){
		
		return hibernateResolversService.find(Application.class, id);
		
	}
	
	public void delete(){
		
	}
	
	public void merge(){
		
	}
	
	public Application update(){
		return  null;
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

	public void save(Application app) throws Exception{
		hibernateResolversService.save(app);
		
	}

	public void update(Application app) throws Exception{
		hibernateResolversService.update(app);
		
	}

	public void delete(int id) throws Exception{
		Application app = hibernateResolversService.find(Application.class, id);
		app.setStatus(0);
		hibernateResolversService.update(app);
		//hibernateResolversService.delete(hibernateResolversService.find(Application.class, id));
		
	}
}
