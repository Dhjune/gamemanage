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
import com.mdream.gamemanage.dao.game.GameTypeDaoImp;
import com.mdream.gamemanage.dao.game.GameTypeRefDaoImp;
import com.mdream.gamemanage.dao.game.TypeTagDaoImp;
import com.mdream.gamemanage.model.game.Game;
import com.mdream.gamemanage.model.game.GameType;
import com.mdream.gamemanage.model.game.Tag;

@Service
@Transactional(value=TxType.NOT_SUPPORTED)
public class GameTypeServiceImp {
	
	@Autowired
	private HibernateResolversService hibernateResolversService;
	
	@Autowired
	private HibernateResolvers hibernateResolvers;
	
	@Autowired
	private GameTypeDaoImp gameTypeDaoImp ;
	
	@Autowired
	private GameTypeRefDaoImp gameTypeRefDaoImp;
	
	@Autowired
	private TypeTagDaoImp typeTagDaoImp;
	
	public GameType get(int id){
		
		return hibernateResolversService.find(GameType.class, id);
		
	}
	
	public GameType find(int id){
		GameType type =  hibernateResolversService.find(GameType.class, id);
		if(type!=null){
			System.out.println(type.getTags().size()+"sdfgsdffffffffff");
		}return  type;
	}
	
	
	public  Map list(List<ExpressionGroup> list, GameType target, int pageIndex,int pageSize) throws NoSuchFieldException, SecurityException, ParseException{
			
			//分页结果集
		//	setResultTransformer(Transformers.aliasToBean(StatCountbook.class));
			Criteria criteria =  hibernateResolversService.getCriteria(list, target);	
			criteria.add( Restrictions.eq(criteria.getAlias()+".status",1));
			criteria.setFirstResult((pageIndex-1)*pageSize);
			criteria.setMaxResults(pageSize);
			List<GameType> result = criteria.list();	
			if(result!=null &&result.size()>0){
				GameType item = null;
				for(int i = 0;i<result.size();i++){
					item = result.get(i);
					item.getTags().size();
				}
			}
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

	public void save(GameType tag) throws Exception{
		hibernateResolversService.save(tag);
		
	}

	public void update(GameType tag) throws Exception{
		hibernateResolversService.update(tag);
		
	}

	public void delete(int id) throws Exception {
		gameTypeDaoImp.delete(id);
		gameTypeRefDaoImp.deleteByTypeId(id);
		typeTagDaoImp.deleteByTypeId(id);		
		//hibernateResolversService.delete(hibernateResolversService.find(Tag.class, id));
		
	}
	
	public List<GameType> list(){
		return  hibernateResolvers.list(new GameType());
	}

}
