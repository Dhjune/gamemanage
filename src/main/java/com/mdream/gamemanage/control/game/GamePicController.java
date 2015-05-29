package com.mdream.gamemanage.control.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mdream.gamemanage.model.game.GamePic;
import com.mdream.gamemanage.model.resulttransformer.GamePicTrans;
import com.mdream.gamemanage.service.game.GamePicServiceImp;
import com.mdream.gamemanage.service.game.GameServiceImp;

@Controller
@RequestMapping(value="game/pic")
public class GamePicController {
	
	@Autowired
	private GamePicServiceImp gamePicServiceImp ;
	
	@Autowired
	private GameServiceImp gameServiceImp ;
	
	@RequestMapping(value="create",method=RequestMethod.GET)
	public String create(){
		
		return "account/game/pic/create";
		
	}
	
	@RequestMapping(value="create",method=RequestMethod.POST)
	public String create(GamePic pic,@RequestParam(value="gameId") int gameId,Model model){
		gamePicServiceImp.insert(pic, gameId);
		
		model.addAttribute("game", gameServiceImp.getGameInfo(gameId));
		return  "account/game/pic/listofgame";
	//	return "redirect:/game/view?id="+gameId;
	}
	
	@RequestMapping(value="get",method=RequestMethod.POST)
	@ResponseBody
	public GamePicTrans get(GamePicTrans trans){
										
		return gamePicServiceImp.find(trans);
	}
	
	@RequestMapping(value="view")
	public String view(@RequestParam(value="id") int id) {
			
		return "account/game/pic/view";
		
	}
	
	@RequestMapping(value="update",method=RequestMethod.GET)
	public String update(){
		return "account/game/pic/update";
	}
	
	@RequestMapping(value="update",method=RequestMethod.POST)
	public String update(GamePicTrans trans,Model model) throws Exception{
		
		gamePicServiceImp.update(trans);
		model.addAttribute("game", gameServiceImp.getGameInfo(trans.getGameId()));
		return  "account/game/pic/listofgame";
		
	}
	
	@RequestMapping(value="delete")
	public String delete(GamePicTrans trans,Model model) throws Exception{
		
		gamePicServiceImp.delete(trans.getId());
		model.addAttribute("game", gameServiceImp.getGameInfo(trans.getGameId()));
		return  "account/game/pic/listofgame";
		
	}

}
