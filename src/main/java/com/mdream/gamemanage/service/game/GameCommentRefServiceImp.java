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
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.mdream.gamemanage.common.proxy.hibernate.criteria.ExpressionGroup;
import com.mdream.gamemanage.common.proxy.hibernate.criteria.HibernateResolversService;
import com.mdream.gamemanage.dao.game.GameCommentRefDaoImp;
import com.mdream.gamemanage.model.game.GameCommentRef;
import com.mdream.gamemanage.model.game.Tag;
import com.mdream.gamemanage.model.resulttransformer.GameCommentTrans;
import com.mdream.gamemanage.model.resulttransformer.GameTypeTrans;

@Service
@Transactional(value=TxType.NOT_SUPPORTED)
public class GameCommentRefServiceImp {
	
	@Autowired
	private HibernateResolversService hibernateResolversService;
	
	@Autowired
	private GameCommentRefDaoImp gameCommentRefDaoImp;
	
	public GameCommentRef get(int gameId,int commentId){
		
		return gameCommentRefDaoImp.get(gameId, commentId);
		
	}
	
	public GameCommentTrans find(int gameId ,int commentId){
		return  gameCommentRefDaoImp.find(gameId, commentId);
	}
	
	public <T> Map list(List<ExpressionGroup> list, T target, int pageIndex,int pageSize) throws NoSuchFieldException, SecurityException, ParseException{
			
			//分页结果集
		    //setResultTransformer(Transformers.aliasToBean(StatCountbook.class));
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

	public void delete(GameCommentTrans trans) throws Exception{
		
		gameCommentRefDaoImp.delete(trans.getGameId(), trans.getCommentId());
				
	}
	
	public  void insert(GameCommentTrans trans){
				
		GameCommentTrans gt =  find(trans.getGameId(), trans.getCommentId());
		if(gt!=null &&gt.getStatus()<1){
			gt.setStatus(1);
			update(gt);
		}else if(gt==null){		
			gameCommentRefDaoImp.insert(trans);
		}
	}
	
	public void update (GameCommentTrans trans){
		gameCommentRefDaoImp.update(trans);
	}
	
	public List<GameCommentTrans> listResult(){
		return gameCommentRefDaoImp.listResult();
	}
	
}
