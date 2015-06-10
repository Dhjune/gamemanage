package com.mdream.gamemanage.control.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mdream.gamemanage.common.inteceptor.Permission;
import com.mdream.gamemanage.common.page.Constans;
import com.mdream.gamemanage.common.page.PageNav;
import com.mdream.gamemanage.common.page.PageNavResolver;
import com.mdream.gamemanage.common.proxy.hibernate.criteria.ExpressionGroup;
import com.mdream.gamemanage.common.tools.ToolsFactory;
import com.mdream.gamemanage.model.base.Application;
import com.mdream.gamemanage.model.game.Game;
import com.mdream.gamemanage.service.game.GameServiceImp;

@Controller
@RequestMapping(value="game")
public class GameController {
	
	@Autowired
	private GameServiceImp gameServiceImp ;
	
	
	
	@Autowired
	private PageNavResolver pageNavResolver;
	
	@RequestMapping(value="create",method=RequestMethod.GET)
	@Permission
	public String createGame(){
		
		return "account/game/create";
		
	}
	
	@RequestMapping(value="create",method=RequestMethod.POST)
	@Permission
	public String createGame(Game game,Model model){
				
		Map<String, String> reply =  new HashMap<String,String>();
		try{	
			boolean exist =  gameServiceImp.exist(game);
			gameServiceImp.save(game);			
			reply.put("rcode", "1");
			if(exist){
				
				reply.put("message","添加成功，但是有重名情况！！");
				
			}else{
				
				reply.put("message","添加成功!!");
				
			}
			
		}catch(Exception e){
			reply.put("rcode", "-1");
			reply.put("message","错误异常");
		}
		model.addAttribute("rdata", reply);
		return "account/game/success";
		
	}
	
	@RequestMapping(value="update",method=RequestMethod.GET)
	@Permission
	public String update(Model model,HttpServletRequest res,@RequestParam("id") int id){
		model.addAttribute("game", gameServiceImp.get(id));
		return "account/game/update";
	}
	
	@RequestMapping(value="update",method=RequestMethod.POST)
	@Permission
	@ResponseBody
	public Map<String, String> update(Game game,Model model){
		Map<String, String> reply =  new HashMap<String,String>();
		Game oldGame  =  gameServiceImp.get(game.getId());
		if(oldGame!=null){
			oldGame.setName(game.getName());
			oldGame.setUrl(game.getUrl());
			oldGame.setDescription(game.getDescription());
			oldGame.setPraise(game.getPraise());
			oldGame.setBelittle(game.getBelittle());
			oldGame.setHotflag(game.getHotflag());
			oldGame.setRecommend(game.getRecommend());
			oldGame.setGodflag(game.getGodflag());
			oldGame.setNowflag(game.getNowflag());
			oldGame.setIcon(game.getIcon());
			oldGame.setImg(game.getImg());
			oldGame.setStar(game.getStar());
//			game.setId(oldGame.getId());
//			game.setCreateTime(oldGame.getCreateTime());
//			game.setStatus(oldGame.getStatus());
			try {
				gameServiceImp.update(oldGame);
				reply.put("rcode", "1");
				reply.put("message","操作成功");
			} catch (Exception e) {
				e.printStackTrace();
				reply.put("rcode", "-1");
				reply.put("message","错误异常");
			}
			
		}else{
			reply.put("rcode", "0");
			reply.put("message"," 数据不存在");
		}
		model.addAttribute("rdata", reply);
		return reply;		
	}
	
	
	@RequestMapping(value="delete")
	@Permission
	@ResponseBody
	public Map<String,String> delete(@RequestParam(value="id") int id){
		Map<String,String> map = new HashMap<String,String>();
		try{
			gameServiceImp.delete(id);
		}catch(Exception e){
			e.printStackTrace();
		}
				
		return map;		
		
	}
	
	@RequestMapping(value="recover")
	@Permission
	@ResponseBody
	public String recover(@RequestParam(value="id") int id){
		gameServiceImp.recover(id);
		return "null";
	}
	
	
	
	@RequestMapping(value="view")
	@Permission
	public String view(Model model,@RequestParam(value="id") int id){
		model.addAttribute("game",gameServiceImp.getGameInfo(id));
		return"account/game/view";
		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="home")
	@Permission
	public String home(Model model)throws Exception{
				
		String url = ToolsFactory.urlTools.tr("/game/list%s");				
		PageNav<Game> context =null;
		Game game =  new Game();
		int pageSize =  Constans.ACCOUNT_DEFAULT_PAGE_SIZE;
		Map<String,Object> map = gameServiceImp.getGameList(null, game, 1, pageSize);
		List<Game> result =  (List<Game>) map.get("result");
		long total =  (Long) map.get("total");
		context =  pageNavResolver.initPagenav(result, game, total, pageSize, 1, url);		
		model.addAttribute("context", context);	
		model.addAttribute("sign", "games");
		return "account/game/home";		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="list",produces = "application/json")
	@Permission
	//@ResponseBody
	public String gameList(@RequestBody List<ExpressionGroup>  list,HttpServletRequest request,Model model) throws Exception{
		String pageIndexString =  request.getParameter("pageIndex");		
		int pageIndex = 1;		
		if(pageIndexString !=null && !pageIndexString.equals("") ){	
			String s = "\\d+";
			Pattern  pattern=Pattern.compile(s); 
			Matcher mt=pattern.matcher(pageIndexString); 
			if(mt.find()){
				
				pageIndex = Integer.parseInt(pageIndexString);
				
			}		
		}else{
			
			pageIndex = 1;	
			
		}
		
		String url = ToolsFactory.urlTools.tr("/game/list%s");				
		PageNav<Game> context =null;
		Game game =  new Game();
		int pageSize =  Constans.ACCOUNT_DEFAULT_PAGE_SIZE;
		Map<String,Object> map = gameServiceImp.getGameList(list, game, pageIndex, pageSize);
		List<Game> result =  (List<Game>) map.get("result");
		long total =  (Long) map.get("total");
		context =  pageNavResolver.initPagenav(result, game, total, pageSize, pageIndex, url);	
		System.out.println(total);
		model.addAttribute("context", context);	
		model.addAttribute("sign", "games");
		return "account/game/list";	
		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="search",produces = "application/json")
	@Permission
	//@ResponseBody
	public String gameSearch(@RequestBody List<ExpressionGroup>  list,HttpServletRequest request,Model model) throws Exception{
		String pageIndexString =  request.getParameter("pageIndex");		
		int pageIndex = 1;		
		if(pageIndexString !=null && !pageIndexString.equals("") ){	
			String s = "\\d+";
			Pattern  pattern=Pattern.compile(s); 
			Matcher mt=pattern.matcher(pageIndexString); 
			if(mt.find()){
				
				pageIndex = Integer.parseInt(pageIndexString);
				
			}		
		}else{
			
			pageIndex = 1;	
			
		}
		
		String url = ToolsFactory.urlTools.tr("/game/list%s");				
		PageNav<Game> context =null;
		Game game =  new Game();
		int pageSize =  Constans.ACCOUNT_DEFAULT_PAGE_SIZE;  
		Map<String,Object> map = gameServiceImp.getGameList(list, game, pageIndex, pageSize);   
		List<Game> result =  (List<Game>) map.get("result");   
		long total =  (Long) map.get("total");   
		context =  pageNavResolver.initPagenav(result, game, total, pageSize, pageIndex, url);	
		model.addAttribute("context", context);	    
		model.addAttribute("sign", "games");   
		
		return "account/game/search";	
		
	}
	
						
	
}
