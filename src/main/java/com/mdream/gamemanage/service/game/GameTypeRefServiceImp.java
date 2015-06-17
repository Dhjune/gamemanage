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

import com.mdream.gamemanage.common.api.BaseServiceItl;
import com.mdream.gamemanage.common.proxy.hibernate.criteria.DefaultHibernateDecorator;
import com.mdream.gamemanage.common.proxy.hibernate.criteria.ExpressionGroup;
import com.mdream.gamemanage.common.proxy.hibernate.criteria.HibernateResolversService;
import com.mdream.gamemanage.dao.game.GameTypeRefDaoImp;
import com.mdream.gamemanage.model.client.PushGame;
import com.mdream.gamemanage.model.game.GameTypeRef;
import com.mdream.gamemanage.model.game.Tag;
import com.mdream.gamemanage.model.resulttransformer.GameTypeTrans;

@Service
@Transactional(value=TxType.NOT_SUPPORTED)
public class GameTypeRefServiceImp implements BaseServiceItl<GameTypeTrans>{
	
	@Autowired
	private HibernateResolversService hibernateResolversService;
	
	@Autowired
	private GameTypeRefDaoImp gameTypeRefDaoImp;
	
	@Autowired
	private DefaultHibernateDecorator defaultHibernateDecorator;
	
	public GameTypeRef  get(int gameId,int typeId){
		
		return gameTypeRefDaoImp.get(gameId, typeId);
		
	}
	
	public GameTypeTrans find(int gameId,int gameTypeId){
		return gameTypeRefDaoImp.find(gameId, gameTypeId);
	}
	
	public <T> Map list(List<ExpressionGroup> list, T target, int pageIndex,int pageSize) throws NoSuchFieldException, SecurityException, ParseException{
			
			//分页结果集
		//	setResultTransformer(Transformers.aliasToBean(StatCountbook.class));
			Criteria criteria =  hibernateResolversService.getCriteria(list, target);	
//			criteria.setCacheable(true);
			
			criteria.add( Restrictions.eq(criteria.getAlias()+".status",1));
			criteria =  defaultHibernateDecorator.addAsc(criteria, criteria.getAlias()+".sort");
		
			criteria.setFirstResult((pageIndex-1)*pageSize);
			criteria.setMaxResults(pageSize);
			List<T> result = criteria.list();
		
			//记录数 
//			criteria.setProjection( Projections.projectionList()
//					.add( Projections.countDistinct(criteria.getAlias()+".id"))		
//				);
			criteria.setFirstResult(0);
			criteria.setMaxResults(1);
//			List counts = criteria.list();
			
			long total = 0; 
			total = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
//			if(counts!=null && counts.size()>0){
//				
//				total = (Long) counts.get(0);			 
//				
//			}
			
			Map map =  new HashMap();
			map.put("result", result);
			map.put("total", total);
			
			return map;
			
	}
	
	public List<GameTypeRef> getAll(List<ExpressionGroup> list, GameTypeRef game) throws NoSuchFieldException, SecurityException, ParseException{
		Criteria criteria =  hibernateResolversService.getCriteria(list, game);	
		if(list==null || list.size()<1){
			criteria.add( Restrictions.eq(criteria.getAlias()+".status",1));
			
			
		}
		defaultHibernateDecorator.addAsc(criteria, criteria.getAlias()+".sort");
		criteria.setCacheable(true);
		List<GameTypeRef> result = criteria.list();
		
		return result;
	}

	public void save(GameTypeRef ref) throws Exception{
		hibernateResolversService.save(ref);
		
	}

	public void update(GameTypeRef ref) throws Exception{
		hibernateResolversService.update(ref);
		
	}

	public void delete(int gameId,int gameTypeId) {
		GameTypeTrans trans = find(gameId,gameTypeId);
		gameTypeRefDaoImp.delete(gameId,gameTypeId);
		delSort(trans);
				
	}
	
	public void delSort(GameTypeTrans trans){
		
		gameTypeRefDaoImp.delSort(trans);
	}
	
	public void fakeDelete(int gameId,int gameTypeId){
		gameTypeRefDaoImp.fakedelete(gameId, gameTypeId);
	}
	
	
	public  void insert(int gameId ,int gameTypeId){
		
		GameTypeTrans gt =  find(gameId, gameTypeId);
		if(gt!=null &&gt.getStatus()<1){
			gt.setStatus(1);
			update(gt);
		}else if(gt==null){		
			gameTypeRefDaoImp.insert(gameId, gameTypeId);
		}
		
	}
	
	public void update (GameTypeTrans trans){
		gameTypeRefDaoImp.update(trans);
	}
	
	public List<GameTypeTrans> listResult(){
		return gameTypeRefDaoImp.listResult();
	}

	public void updateSort(GameTypeTrans trans) {
		gameTypeRefDaoImp.updateSort(trans);
		
	}


	public void saveOrUpdate(GameTypeTrans t) {
		gameTypeRefDaoImp.saveOrUpdate(t);
		
	}
	
}
