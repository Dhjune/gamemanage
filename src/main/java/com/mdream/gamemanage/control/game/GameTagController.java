package com.mdream.gamemanage.control.game;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(value="game/gametag/")
@Controller
public class GameTagController {
	
	@RequestMapping(value="create",method=RequestMethod.GET)
	public String create(Model model){
		return "account/game/gametag/create";
	}
	
	
	@RequestMapping(value="create",method=RequestMethod.POST)
	public String create(){
		return "account/game/gametag/create";
	}
	
	
	@RequestMapping(value="view")
	public String view(@RequestParam(value="id") int id) {
			
		return "account/game/gametag/view";
		
	}
	
	@RequestMapping(value="update",method=RequestMethod.GET)
	public String update(Model model){
		return "account/game/gametag/update";
	}
	
	@RequestMapping(value="update",method=RequestMethod.POST)
	public String update(){
		
		return "account/game/gametag/update";
		
	}
	
	@RequestMapping(value="delete")
	@ResponseBody
	public String delete(){
		
		return "account/game/gametag/update";
		
	}
	
}
