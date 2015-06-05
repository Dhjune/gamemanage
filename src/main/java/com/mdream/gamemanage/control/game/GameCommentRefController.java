package com.mdream.gamemanage.control.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mdream.gamemanage.common.inteceptor.Permission;
import com.mdream.gamemanage.common.tools.ToolsFactory;
import com.mdream.gamemanage.model.game.GameCommentRef;
import com.mdream.gamemanage.model.resulttransformer.GameCommentTrans;
import com.mdream.gamemanage.service.game.GameCommentRefServiceImp;
import com.mdream.gamemanage.service.game.GameServiceImp;

@Controller
@RequestMapping(value="game/comment/ref/")
public class GameCommentRefController {
	
	@Autowired
	private GameCommentRefServiceImp gameCommentRefServiceImp;
	
	@Autowired
	private GameServiceImp gameServiceImp ;

	public String create(){
		
		return "";
	}
	
	@RequestMapping(value="create",method=RequestMethod.POST)
	@Permission
	public String  create(GameCommentTrans trans,Model model)  throws Exception{
		gameCommentRefServiceImp.insert(trans);
		model.addAttribute("game", gameServiceImp.getGameInfo(trans.getGameId()));
		return "account/game/comment/listofgame";
		
	}
	
	@RequestMapping(value="update",method=RequestMethod.GET)
	@Permission
	public String update(Model model){
		return "";
	}
	

	@RequestMapping(value="get",method=RequestMethod.POST)
	@Permission
	@ResponseBody
	public String get(@RequestParam(value="gameId") int gameId,@RequestParam(value="commentId") int commentId){
		
		return ToolsFactory.gsonTools.getGson().toJson(gameCommentRefServiceImp.get(gameId, commentId));		
		
	}
	
	@RequestMapping(value="update",method=RequestMethod.POST)
	@Permission
	public String update(GameCommentTrans trans,Model model){
		gameCommentRefServiceImp.update(trans);
		model.addAttribute("game", gameServiceImp.getGameInfo(trans.getGameId()));
		return "account/game/comment/listofgame";
	}
	
	@RequestMapping(value="delete")	
	@Permission
	public String delete(GameCommentTrans trans,Model model)  throws Exception{
		
		gameCommentRefServiceImp.delete(trans);
		model.addAttribute("game", gameServiceImp.getGameInfo(trans.getGameId()));
		return "account/game/comment/listofgame";
		
	}
	
	@RequestMapping(value="list",method=RequestMethod.POST)
	@Permission
	public String  list(HttpServletRequest res)  throws Exception {
		
		return "";
		
	}
	
}
