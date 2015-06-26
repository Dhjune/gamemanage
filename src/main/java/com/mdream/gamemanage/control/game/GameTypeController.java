package com.mdream.gamemanage.control.game;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Access;
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
import com.mdream.gamemanage.model.game.Game;
import com.mdream.gamemanage.model.game.GameType;
import com.mdream.gamemanage.service.game.GameTypeServiceImp;

@Controller
@RequestMapping(value="game/type")
public class GameTypeController {
	
	@Autowired
	private  GameTypeServiceImp gameTypeServiceImp;
	
	@Autowired
	private PageNavResolver pageNavResolver;
	
	@Permission
	@RequestMapping(value="create",method=RequestMethod.GET)
	public String create(){
		return "account/game/type/create";
	}
	
	@Permission
	@RequestMapping(value="create",method=RequestMethod.POST)
	public String create(GameType type,Model model){
		Map<String, String> reply =  new HashMap<String,String>();
		try{
			type.setCreateTime(new Date());
			gameTypeServiceImp.save(type);
			
			reply.put("rcode", "1");
			reply.put("message","操作成功");
		}catch(Exception e){
			e.printStackTrace();
			reply.put("rcode", "-1");
			reply.put("message","错误异常");
		}
		model.addAttribute("rdata", reply);
		
		return "account/game/type/success";
	}
	
	
	@RequestMapping(value="view")
	public String view(@RequestParam(value="id") int id,Model model) {
		model.addAttribute("type",gameTypeServiceImp.find(id));
		return "account/game/type/view";		
	}
	
	@RequestMapping(value="update",method=RequestMethod.GET)
	public String update(Model model,@RequestParam("id") int id){
		model.addAttribute("type", gameTypeServiceImp.get(id));
		return "account/game/type/update";
	}
	
	@RequestMapping(value="update",method=RequestMethod.POST)
	@ResponseBody
	@Permission
	public Map<String, String> update(GameType type,Model model){
		Map<String, String> reply =  new HashMap<String,String>();
		GameType oldType  =  gameTypeServiceImp.get(type.getId());
		if(oldType!=null){
			oldType.setName(type.getName());
			oldType.setModifyTime(new Date());
			try {
				gameTypeServiceImp.update(oldType);
				reply.put("rcode", "1");
				reply.put("message","操作成功");
			} catch (Exception e) {
				reply.put("rcode", "-1");
				reply.put("message","错误异常");
			}
			
		}else{
			reply.put("rcode", "0");
			reply.put("message"," 数据不存在!!");
		}
		model.addAttribute("rdata", reply);
		return reply;	
		
	}
	
	@RequestMapping(value="delete")
	@ResponseBody
	@Permission
	public String delete(@RequestParam("id") int id) throws Exception{
		gameTypeServiceImp.delete(id);
		return "account/game/type/update";
		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="home")
	@Permission
	public String home(Model model)throws Exception{
				
		String url = ToolsFactory.urlTools.tr("/game/type/list%s");				
		PageNav<GameType> context =null;
		GameType type =  new GameType();
		int pageSize =  Constans.ACCOUNT_DEFAULT_PAGE_SIZE;
		Map<String,Object> map = gameTypeServiceImp.list(null, type, 1, pageSize);
		List<GameType> result =  (List<GameType>) map.get("result");
		long total =  (Long) map.get("total");
		context =  pageNavResolver.initPagenav(result, type, total, pageSize, 1, url);		
		model.addAttribute("context", context);	
		model.addAttribute("sign", "types");
		return "account/game/type/home";
		
	}
	
	@RequestMapping(value="jlist",produces = "application/json")
	@ResponseBody
	@Permission
	public String getJsonList(){
		
		return ToolsFactory.gsonTools.getGson().toJson(gameTypeServiceImp.list());
		
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
		
		String url = ToolsFactory.urlTools.tr("/game/type/list%s");				
		PageNav<GameType> context =null;
		GameType type =  new GameType();
		int pageSize =  Constans.ACCOUNT_DEFAULT_PAGE_SIZE;
		Map<String,Object> map = gameTypeServiceImp.list(null, type, pageIndex, pageSize);
		List<GameType> result =  (List<GameType>) map.get("result");
		long total =  (Long) map.get("total");
		context =  pageNavResolver.initPagenav(result, type, total, pageSize, pageIndex, url);				
		model.addAttribute("context", context);	
		model.addAttribute("sign", "types");
		return "account/game/type/list";	
		
	}
	
}
