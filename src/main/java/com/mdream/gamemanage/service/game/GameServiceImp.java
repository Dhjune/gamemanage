package com.mdream.gamemanage.service.game;

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
import com.mdream.gamemanage.dao.game.GameCommentRefDaoImp;
import com.mdream.gamemanage.dao.game.GameDaoImp;
import com.mdream.gamemanage.dao.game.GamePicDaoImp;
import com.mdream.gamemanage.dao.game.GameTypeDaoImp;
import com.mdream.gamemanage.dao.game.GameTypeRefDaoImp;
import com.mdream.gamemanage.model.game.Game;
import com.mdream.gamemanage.model.game.Tag;
@Service
@Transactional(value=TxType.NOT_SUPPORTED)
public class GameServiceImp {
	
	@Autowired
	private HibernateResolversService hibernateResolversService;
	
	
	@Autowired
	private GameDaoImp gameDaoImp;
	
	@Autowired
	private GameTypeRefDaoImp gameTypeRefDaoImp;
	
	@Autowired
	private GamePicDaoImp  gamePicDaoImp;
	
	@Autowired
	private GameCommentRefDaoImp gameCommentRefDaoImp ;
	
	
	@Autowired
	private  HibernateResolvers hibernateResolvers;
	
	@Transactional(value=TxType.NOT_SUPPORTED)
	public  Map getGameList(List<ExpressionGroup> list, Game game, int pageIndex,int pageSize) throws NoSuchFieldException, SecurityException, ParseException{
		
		//分页结果集
		Criteria criteria =  hibernateResolversService.getCriteria(list, game);	
		if(list==null || list.size()<1){
			criteria.add( Restrictions.eq(criteria.getAlias()+".status",1));
		}
	
		criteria.setCacheable(true);
		criteria.setFirstResult((pageIndex-1)*pageSize);
		criteria.setMaxResults(pageSize);
		List<Game> result = criteria.list();
		if(result!=null &&result.size()>0){
			Game item = null;
			for(int i = 0;i<result.size();i++){
				item = result.get(i);
				item.getGametypes().size();
				item.getCommentRefs().size();
				item.getPics().size();
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
	
	
	public  List<Game> list(){
		return  (List<Game>) hibernateResolvers.list(new Game());
	}

	@Transactional(value=TxType.REQUIRED)
	public void save(Game game) throws Exception{
		game.setCreateTime(new Date());
		hibernateResolvers.save(game);
		
	}

	@Transactional(value=TxType.REQUIRED)
	public void update(Game game) throws Exception{
		game.setModifyTime(new Date());
		hibernateResolvers.update(game);
		
	}
	
	public void nativeUpdate(Game game){
		gameDaoImp.update(game);
		
	}

	public Game get(int id) {
		// TODO Auto-generated method stub
		return hibernateResolversService.find(Game.class, id);
	}
	
	public void merge(Game game)throws Exception{
		hibernateResolversService.merge(game);
	}


	public void delete(int id) throws Exception {
		//别用这样的方式,因为game和type没有使用one-many one-many 形成many-many
		//所以关系控制方建立在game，这样删除game就波及到无辜的type.
//		Game game = hibernateResolvers.find(Game.class, id);
//		hibernateResolvers.delete(game);
		gameDaoImp.delete(id);
		gameTypeRefDaoImp.deleteBygameId(id);
		gameCommentRefDaoImp.deleteBygameId(id);
		gamePicDaoImp.deleteBygameId(id);
	}

	
	public void fakedelete(int id){
		
		gameDaoImp.fakeDelete(id);
		
	}

	public Game getGameInfo(int id) {
		Game game = null;
		game = hibernateResolversService.find(Game.class, id);
		game.getGametypes().size();
		game.getTypeRefs().size();
		game.getPics().size();
		game.getCommentRefs().size();
		return game;
	}


	public void recover(int id) {
		gameDaoImp.recover(id);
		
	}


	public boolean exist(Game game) {
		
		return gameDaoImp.exist(game);
		
	}
	
	

}
