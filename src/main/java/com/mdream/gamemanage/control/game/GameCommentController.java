package com.mdream.gamemanage.control.game;

import java.util.Date;
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

import com.mdream.gamemanage.common.page.Constans;
import com.mdream.gamemanage.common.page.PageNav;
import com.mdream.gamemanage.common.page.PageNavResolver;
import com.mdream.gamemanage.common.proxy.hibernate.criteria.ExpressionGroup;
import com.mdream.gamemanage.common.tools.ToolsFactory;
import com.mdream.gamemanage.model.game.Game;
import com.mdream.gamemanage.model.game.GameComment;
import com.mdream.gamemanage.model.game.GameType;
import com.mdream.gamemanage.service.game.GameCommentServiceImp;

@Controller
@RequestMapping(value="game/comment")
public class GameCommentController {
	
	@Autowired
	private PageNavResolver pageNavResolver;
	
	@Autowired
	private GameCommentServiceImp gameCommentServiceImp;
	
	@RequestMapping(value="create",method=RequestMethod.GET)
	public String create(){
		return "account/game/comment/create";
	}
	
	@RequestMapping(value="create",method=RequestMethod.POST)
	public String create(GameComment comment,Model model){
		Map<String,String> reply =  new HashMap<String,String>();
		try{
			comment.setCreateTime(new Date());
			gameCommentServiceImp.save(comment);
			
			reply.put("rcode", "1");
			reply.put("message","操作成功");
		}catch(Exception e){
			e.printStackTrace();
			reply.put("rcode", "-1");
			reply.put("message","错误异常");
		}
		
		model.addAttribute("rdata", reply);
		
		return "account/game/comment/success";
	}
	
	
	@RequestMapping(value="view")
	public String view(@RequestParam(value="id") int id) {
			
		return "account/game/comment/view";
		
	}
	
	@RequestMapping(value="update",method=RequestMethod.GET)
	public String update(@RequestParam(value="id") int id,Model model)throws Exception{
		
		model.addAttribute("comment", gameCommentServiceImp.get(id));
		
		return "account/game/comment/update";
	}
	
	@RequestMapping(value="update",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> update(GameComment comment){
		Map<String,String> replay =  new HashMap<String,String>();
		
		GameComment old = null;
		try {
			old = gameCommentServiceImp.get(comment.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			replay.put("message", "异常错误");
			replay.put("rcode", "-1");
			return replay;
		}
		if(old!=null){
			old.setContent(comment.getContent());
			old.setModifyTime(new Date());
			try {
				gameCommentServiceImp.update(old);
			} catch (Exception e) {
				replay.put("message", "异常错误");
				replay.put("rcode", "-1");
			}
			replay.put("message", "修改成功");
			replay.put("rcode", "1");
			
		}else{
			replay.put("message", "不存在该条信息");
			replay.put("rcode", "0");
		}
		return replay;		
	}
	
	@RequestMapping(value="delete")
	@ResponseBody
	public String delete(@RequestParam(value="id" )int id) throws Exception{
		gameCommentServiceImp.delete(id);
		return "account/game/comment/update";
		
	}
	
	@RequestMapping(value="home")
	public String home(Model model)throws Exception{
				
		String url = ToolsFactory.urlTools.tr("/game/comment/list%s");				
		PageNav<GameComment> context =null;
		GameComment comment =  new GameComment();
		int pageSize =  Constans.ACCOUNT_DEFAULT_PAGE_SIZE;
		Map map = gameCommentServiceImp.list(null, comment, 1, pageSize);
		List<GameComment> result =  (List<GameComment>) map.get("result");
		long total =  (Long) map.get("total");
		context =  pageNavResolver.initPagenav(result, comment, total, pageSize, 1, url);		
		model.addAttribute("context", context);	
		model.addAttribute("sign", "comments");
		return "account/game/comment/home";
		
	}
	
	@RequestMapping(value="list",produces = "application/json")
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
		
		String url = ToolsFactory.urlTools.tr("/game/comment/list%s");				
		PageNav<GameComment> context =null;
		GameComment game =  new GameComment();
		int pageSize =  Constans.ACCOUNT_DEFAULT_PAGE_SIZE;
		Map map = gameCommentServiceImp.list(list, game, pageIndex, pageSize);
		List<GameComment> result =  (List<GameComment>) map.get("result");
		long total =  (Long) map.get("total");
		context =  pageNavResolver.initPagenav(result, game, total, pageSize, pageIndex, url);	
		System.out.println(total);
		model.addAttribute("context", context);	
		model.addAttribute("sign", "comments");
		return "account/game/comment/list";	
		
	}
	
	@RequestMapping(value="jlist",produces = "application/json")
	@ResponseBody
	public String getJsonList() throws Exception{
		
		return ToolsFactory.gsonTools.getGson().toJson(gameCommentServiceImp.list());
	}
	
	
	
}
