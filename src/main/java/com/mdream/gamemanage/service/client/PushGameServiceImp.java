package com.mdream.gamemanage.service.client;

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

import com.mdream.gamemanage.common.api.BaseServiceItl;
import com.mdream.gamemanage.common.proxy.hibernate.criteria.DefaultHibernateDecorator;
import com.mdream.gamemanage.common.proxy.hibernate.criteria.ExpressionGroup;
import com.mdream.gamemanage.common.proxy.hibernate.criteria.HibernateResolversService;
import com.mdream.gamemanage.dao.client.PushGameDaoImp;
import com.mdream.gamemanage.model.client.PushGame;
import com.mdream.gamemanage.model.game.Game;


@Service
@Transactional(value=TxType.NOT_SUPPORTED)
public class PushGameServiceImp implements BaseServiceItl<PushGame>{

	@Autowired
	private HibernateResolversService hibernateResolversService;
	
	@Autowired
	private DefaultHibernateDecorator defaultHibernateDecorator;
	
	@Autowired
	private PushGameDaoImp pushGameDaoImp;
	
	public PushGame get(int id){
		
		return hibernateResolversService.find(PushGame.class, id);
		
	}

	public void create(PushGame pg) throws Exception{
		int count = getCountByType(pg.getType());
		pg.setSort(count+1);
		hibernateResolversService.save(pg);
	}
	
	
	public  Map getList(List<ExpressionGroup> list, PushGame game, int pageIndex,int pageSize) throws NoSuchFieldException, SecurityException, ParseException{
		
		//分页结果集
		Criteria criteria =  hibernateResolversService.getCriteria(list, game);	
		if(list==null || list.size()<1){
			criteria.add( Restrictions.eq(criteria.getAlias()+".status",1));
			defaultHibernateDecorator.addDesc(criteria, criteria.getAlias()+".sort");
		}
		criteria.setFirstResult((pageIndex-1)*pageSize);
		criteria.setMaxResults(pageSize);
		List<PushGame> result = criteria.list();		
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
	
	public List<PushGame> getAll(List<ExpressionGroup> list, PushGame game) throws NoSuchFieldException, SecurityException, ParseException{
		Criteria criteria =  hibernateResolversService.getCriteria(list, game);	
		if(list==null || list.size()<1){
			criteria.add( Restrictions.eq(criteria.getAlias()+".status",1));
			defaultHibernateDecorator.addDesc(criteria, criteria.getAlias()+".sort");
		}
		
		List<PushGame> result = criteria.list();	
		return result;
	}
	
	public void update(PushGame pg) throws Exception{
		pg.setModifyTime(new Date());
		hibernateResolversService.update(pg);
	}
	
	public void delete(Integer id){
		pushGameDaoImp.delete(id);
	}
	
	public int getCountByType(int type){
		return pushGameDaoImp.getCountByType(type);
	}

	@Override
	public void saveOrUpdate(PushGame t) {
		pushGameDaoImp.saveOrUpdate(t);
		
	}
	
}
