package com.mdream.gamemanage.service.client;

import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
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
import com.mdream.gamemanage.dao.client.SlideRefDaoImp;
import com.mdream.gamemanage.dao.client.SlideShowDaoImp;
import com.mdream.gamemanage.model.client.Slide;
import com.mdream.gamemanage.model.client.SlideRef;
import com.mdream.gamemanage.model.client.SlideShow;

@Service
@Transactional(value=TxType.NOT_SUPPORTED)
public class SlideShowServiceImp {
	
	@Autowired
	private HibernateResolversService hibernateResolversService;
	
	@Autowired
	private HibernateResolvers hibernateResolvers;
	
	@Autowired
	private SlideShowDaoImp slideShowDaoImp;
	
	@Autowired
	private SlideRefDaoImp  slideRefDaoImp ;
	
	public SlideShow get(int id){
		
		return hibernateResolversService.find(SlideShow.class, id);
		
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

	public void save(SlideShow show) throws Exception{
		hibernateResolversService.save(show);
		
	}

	public void update(SlideShow show) throws Exception{
		hibernateResolversService.update(show);
		
	}

	public void delete(int id) {
		slideShowDaoImp.delete(id);
		slideRefDaoImp.deleteBySlideShowId(id);
		//hibernateResolversService.delete(hibernateResolversService.find(SlideShow.class, id));
		
	}
	
	public List<SlideShow> list(){
		return hibernateResolvers.list(new SlideShow());
	}

	@SuppressWarnings("unchecked")
	public SlideShow getInfo(int id) {
		
		SlideShow show = hibernateResolversService.find(SlideShow.class, id);
		Collections.sort(show.getSlideRefs(),new Comparator(){
		      public int compare(Object op1,Object op2){
		          SlideRef ref1=(SlideRef)op1;
		          SlideRef ref2=(SlideRef)op2;		          		      
		          return ref1.getSort().compareTo(ref2.getSort());
		        }
		      });
		//show.getSlideRefs().size();
		return show;
		
	}
	
}
