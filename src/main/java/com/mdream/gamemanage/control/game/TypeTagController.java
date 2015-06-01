package com.mdream.gamemanage.control.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mdream.gamemanage.common.inteceptor.Permission;
import com.mdream.gamemanage.model.resulttransformer.TypeTagTrans;
import com.mdream.gamemanage.service.base.TagServiceImp;
import com.mdream.gamemanage.service.game.GameTypeServiceImp;
import com.mdream.gamemanage.service.game.TypeTagServiceImp;


@RequestMapping(value="game/typetag/")
@Controller
public class TypeTagController {
	
	@Autowired
	private TypeTagServiceImp typeTagServiceImp;
	
	@Autowired
	private TagServiceImp tagServiceImp;
	
	@Autowired
	private  GameTypeServiceImp gameTypeServiceImp;
	
	@RequestMapping(value="create",method=RequestMethod.GET)
	@Permission
	public String create(Model model){
		return "account/game/typetag/create";
	}
	
	
	@RequestMapping(value="create",method=RequestMethod.POST)
	@Permission
	public String create(TypeTagTrans trans,Model model){
		typeTagServiceImp.insert(trans);
		
		model.addAttribute("type",gameTypeServiceImp.find(trans.getGameTypeId()));
		return "account/tag/listoftype";
	}
	
	
	@RequestMapping(value="view")
	@Permission
	public String view(@RequestParam(value="id") int id) {
			
		return "account/tag/listoftype";
		
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
	public String delete(TypeTagTrans trans,Model model){
		typeTagServiceImp.delete(trans);
		
		model.addAttribute("type",gameTypeServiceImp.find(trans.getGameTypeId()));
		return "account/tag/listoftype";	
		
	}
	

}
