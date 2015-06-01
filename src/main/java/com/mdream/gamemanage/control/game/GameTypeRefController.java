package com.mdream.gamemanage.control.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mdream.gamemanage.common.inteceptor.Permission;
import com.mdream.gamemanage.service.game.GameServiceImp;
import com.mdream.gamemanage.service.game.GameTypeRefServiceImp;

@Controller
@RequestMapping(value="game/type/ref")
public class GameTypeRefController {
	
	@Autowired
	private GameTypeRefServiceImp gameTypeRefServiceImp;
	
	
	@Autowired
	private GameServiceImp gameServiceImp ;
	
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
	public String update(Model model){
		return "account/game/typetag/update";
	}
	
	@RequestMapping(value="update",method=RequestMethod.POST)
	@Permission
	public String update(){
		
		return "account/game/typetag/update";
		
	}
	
	@RequestMapping(value="delete",method=RequestMethod.POST)
	@Permission
	public String delete(@RequestParam(value="gameId") int gameId,@RequestParam(value="typeId") int typeId,Model model){
		gameTypeRefServiceImp.delete(gameId, typeId);
		model.addAttribute("game", gameServiceImp.getGameInfo(gameId));
		return "account/game/type/listofgame";
		
	}
	
}
