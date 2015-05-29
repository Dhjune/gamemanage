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
import com.mdream.gamemanage.common.proxy.hibernate.criteria.HibernateResolvers;
import com.mdream.gamemanage.common.proxy.hibernate.criteria.HibernateResolversService;
import com.mdream.gamemanage.dao.game.GameCommentDaoImp;
import com.mdream.gamemanage.dao.game.GameCommentRefDaoImp;
import com.mdream.gamemanage.dao.game.GameTypeRefDaoImp;
import com.mdream.gamemanage.model.game.GameComment;
import com.mdream.gamemanage.model.game.Tag;

@Service
@Transactional(value=TxType.NOT_SUPPORTED)
public class GameCommentServiceImp {
	
	@Autowired
	private HibernateResolversService hibernateResolversService;
	
	@Autowired
	private HibernateResolvers hibernateResolvers;
	
	@Autowired
	private GameCommentDaoImp gameCommentDaoImp ;
	
	@Autowired
	private GameCommentRefDaoImp gameCommentRefDaoImp;
	
	
	public GameComment get(int id) throws  Exception{
		
		return hibernateResolversService.find(GameComment.class, id);
		
	}
	
	
	
	public <T> Map list(List<ExpressionGroup> list, T target, int pageIndex,int pageSize) throws  Exception ,NoSuchFieldException, SecurityException, ParseException{
			
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

	public void save(GameComment tag) throws Exception{
		hibernateResolversService.save(tag);
		
	}

	public void update(GameComment tag) throws Exception{
		hibernateResolversService.update(tag);
		
	}

	public void delete(int id) throws Exception{
		gameCommentDaoImp.delete(id);
		gameCommentRefDaoImp.deleteBycommentId(id);
		//hibernateResolversService.delete(hibernateResolversService.find(Tag.class, id));
		
	}
	
	public List<GameComment> list() throws Exception{
		
		return  hibernateResolvers.list(new GameComment());
		
	}
	
}
