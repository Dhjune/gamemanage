package com.mdream.gamemanage.control.game;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mdream.gamemanage.common.http.SyncHttpWork;
import com.mdream.gamemanage.common.inteceptor.Permission;
import com.mdream.gamemanage.common.page.Constans;
import com.mdream.gamemanage.common.page.PageNav;
import com.mdream.gamemanage.common.page.PageNavResolver;
import com.mdream.gamemanage.common.proxy.hibernate.criteria.ExpressionGroup;
import com.mdream.gamemanage.common.tools.ToolsFactory;
import com.mdream.gamemanage.common.xml.JxlResolveImp;
import com.mdream.gamemanage.model.client.PushGame;
import com.mdream.gamemanage.model.game.GameType;
import com.mdream.gamemanage.model.game.GameTypeRef;
import com.mdream.gamemanage.model.resulttransformer.GameTypeTrans;
import com.mdream.gamemanage.service.game.GameServiceImp;
import com.mdream.gamemanage.service.game.GameTypeRefServiceImp;

@Controller
@RequestMapping(value="game/type/ref")
public class GameTypeRefController {
	
	@Autowired
	private GameTypeRefServiceImp gameTypeRefServiceImp;
	
	@Autowired
	private PageNavResolver pageNavResolver;
	
	@Autowired
	private GameServiceImp gameServiceImp ;
	
	@Autowired
	private SyncHttpWork syncHttpWork;
	
	@Autowired
	private JxlResolveImp jxlResolveImp;
	
	@RequestMapping(value="create",method=RequestMethod.GET)
	@Permission
	public String create(Model model){
		return "account/game/type/ref/create";
	}
	
	
	@RequestMapping(value="create",method=RequestMethod.POST)
	@Permission
	public String create(@RequestParam(value="gameId") int gameId,@RequestParam(value="typeId") int typeId,Model model){
		
		gameTypeRefServiceImp.insert(gameId, typeId);	
		model.addAttribute("game", gameServiceImp.getGameInfo(gameId));
		return "account/game/type/listofgame";
		
		
	}
	
	
	
	
	@RequestMapping(value="view")
	@Permission
	public String view(@RequestParam(value="id") int id) {
			
		return "account/game/typetag/view";
		
	}
	
	@RequestMapping(value="update",method=RequestMethod.GET)
	@Permission
	public String update(@RequestParam(value="gameId") int gameId,@RequestParam(value="typeId") int typeId,Model model){
					
		model.addAttribute("ref", gameTypeRefServiceImp.get(gameId, typeId));
		
		return "account/game/typelist/update";
	}
	
	@RequestMapping(value="update",method=RequestMethod.POST)
	@Permission
	@ResponseBody
	public Map<String, String> update(GameTypeTrans trans){
		Map<String, String> reply =  new HashMap<String,String>();
				
		try {
			gameTypeRefServiceImp.updateSort(trans);
			reply.put("rcode", "1");
			reply.put("message","操作成功");
		} catch (Exception e) {
			reply.put("rcode", "-1");
			reply.put("message","错误异常");
		}
				
		return reply;	
		
	}
	
	@RequestMapping(value="delete",method=RequestMethod.POST)
	@Permission
	public String delete(@RequestParam(value="gameId") int gameId,@RequestParam(value="typeId") int typeId,Model model){
		
		gameTypeRefServiceImp.delete(gameId, typeId);
		//gameTypeRefServiceImp.fakeDelete(gameId, typeId);
		model.addAttribute("game", gameServiceImp.getGameInfo(gameId));
		
		return "account/game/type/listofgame";
		
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="home")
	@Permission
	public String home(Model model)throws Exception{
				
		String url = ToolsFactory.urlTools.tr("/game/type/ref/list%s");				
		PageNav<GameTypeRef> context =null;
		GameTypeRef type =  new GameTypeRef();
		int pageSize =  Constans.ACCOUNT_DEFAULT_PAGE_SIZE;
		Map<String,Object> map = gameTypeRefServiceImp.list(null, type, 1, pageSize);
		List<GameTypeRef> result =  (List<GameTypeRef>) map.get("result");
		long total =  (Long) map.get("total");
		context =  pageNavResolver.initPagenav(result, type, total, pageSize, 1, url);		
		model.addAttribute("context", context);	
		model.addAttribute("sign", "typegames");
		return "account/game/typelist/home";
		
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
		String url = ToolsFactory.urlTools.tr("/game/type/ref/list%s");				
		PageNav<GameTypeRef> context =null;
		GameTypeRef type =  new GameTypeRef();
		int pageSize =  Constans.ACCOUNT_DEFAULT_PAGE_SIZE;
		Map<String,Object> map = gameTypeRefServiceImp.list(list, type, pageIndex, pageSize);
		List<GameTypeRef> result =  (List<GameTypeRef>) map.get("result");
		long total =  (Long) map.get("total");
		context =  pageNavResolver.initPagenav(result, type, total, pageSize, pageIndex, url);				
		model.addAttribute("context", context);	
		model.addAttribute("sign", "typegames");
		return "account/game/typelist/list";	
		
	}
	
	
	@RequestMapping(value="batchSaveOrUpdate")
	@Permission
	@ResponseBody
	public String batchSaveOrUpdate(HttpServletRequest request) throws Exception{
		String file = syncHttpWork.savefile(request, com.mdream.gamemanage.common.tools.Constans.FILE_BASE_SAVE_PATH);
		jxlResolveImp.filling(file, 2, GameTypeTrans.class, gameTypeRefServiceImp);		
		return "1234567";
	}
	
	@RequestMapping(value="downpage")
	@Permission
	public void downpage(HttpServletRequest request,HttpServletResponse response,@RequestParam("pageIndex") int pageIndex){
		try{
			String values =  request.getParameter("values");
			Type listType = new TypeToken<Collection<ExpressionGroup>>() {}.getType();			
	//	   GsonHttpMessageConverter gmc = (GsonHttpMessageConverter) ToolsFactory.applicationContext.getBean(GsonHttpMessageConverter.class);
		    Gson gson =new Gson();
			List<ExpressionGroup> list =gson.fromJson(values, listType);
			GameTypeRef ref = new GameTypeRef();
		    Map map = gameTypeRefServiceImp.list(list, ref, pageIndex,  Constans.ACCOUNT_DEFAULT_PAGE_SIZE);
			
			List<GameTypeRef> result =  (List<GameTypeRef>) map.get("result");
			
			List<GameTypeTrans> r_list = new ArrayList<GameTypeTrans>();
			GameTypeRef gtref =  null;
			for(int i=0;i<result.size();i++){
				gtref =  result.get(i);
				r_list.add(new GameTypeTrans(gtref.getPk().getGame().getId()
						,gtref.getPk().getGameType().getId(),gtref.getPk().getGame().getName(),
						gtref.getPk().getGameType().getName(),gtref.getSort()));
				
			}
			String[][] titles = new String[][]{
				{"gameTypeId","gameId","gameName","typeName","sort"},{"分类id","游戏id","分类名称","游戏名称","排序"}
			};
			
			jxlResolveImp.reading(titles, syncHttpWork.createFile(".xls", com.mdream.gamemanage.common.tools.Constans.FILE_BASE_SAVE_PATH), r_list, GameTypeTrans.class, response);
			}catch(Exception e){
				e.printStackTrace();
			}
		
		
	}
	
	@RequestMapping(value="downloadAll")
	@Permission
	public void downloadAll(HttpServletRequest request,HttpServletResponse response){
		
		try{
			String values =  request.getParameter("values");
			Type listType = new TypeToken<Collection<ExpressionGroup>>() {}.getType();
			
	//	   GsonHttpMessageConverter gmc = (GsonHttpMessageConverter) ToolsFactory.applicationContext.getBean(GsonHttpMessageConverter.class);
		   Gson gson =new Gson();
			List<ExpressionGroup> list =gson.fromJson(values, listType);
			GameTypeRef pg = new GameTypeRef();
			
		    List<GameTypeRef> result= gameTypeRefServiceImp.getAll(list, pg);
	
			List<GameTypeTrans> r_list = new ArrayList<GameTypeTrans>();
			
			GameTypeRef gtref =  null;
			for(int i=0;i<result.size();i++){
				gtref =  result.get(i);
				r_list.add(new GameTypeTrans(gtref.getPk().getGame().getId()
						,gtref.getPk().getGameType().getId(),gtref.getPk().getGame().getName(),
						gtref.getPk().getGameType().getName(),gtref.getSort()));
				
			}
			String[][] titles = new String[][]{
					{"gameTypeId","gameId","gameName","typeName","sort"},{"分类id","游戏id","分类名称","游戏名称","排序"}
			};
			
			jxlResolveImp.reading(titles, syncHttpWork.createFile(".xls", com.mdream.gamemanage.common.tools.Constans.FILE_BASE_SAVE_PATH), r_list, GameTypeTrans.class, response);
			}catch(Exception e){
				e.printStackTrace();
			}
		
	}
	
}
