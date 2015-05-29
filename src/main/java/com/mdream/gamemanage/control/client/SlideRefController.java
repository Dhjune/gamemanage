package com.mdream.gamemanage.control.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mdream.gamemanage.common.page.PageNavResolver;
import com.mdream.gamemanage.common.tools.ToolsFactory;
import com.mdream.gamemanage.model.client.Slide;
import com.mdream.gamemanage.model.client.SlideRef;
import com.mdream.gamemanage.model.client.SlideRefPK;
import com.mdream.gamemanage.model.client.SlideShow;
import com.mdream.gamemanage.model.game.GameType;
import com.mdream.gamemanage.model.resulttransformer.SlideShowTrans;
import com.mdream.gamemanage.service.client.SlideRefServiceImp;
import com.mdream.gamemanage.service.client.SlideServiceImp;
import com.mdream.gamemanage.service.client.SlideShowServiceImp;

@Controller
@RequestMapping(value="client/slide/ref")
public class SlideRefController {
	
	@Autowired
	private SlideServiceImp slideServiceImp;
	
	@Autowired
	private PageNavResolver pageNavResolver;
	
	@Autowired
	private SlideShowServiceImp slideShowServiceImp;
	
	@Autowired
	private SlideRefServiceImp slideRefServiceImp ;
	
	@RequestMapping(value="create",method=RequestMethod.GET)
	public String create(){
		return "account/client/slide/ref/create";
	}
	
	@RequestMapping(value="create",method=RequestMethod.POST)
	public String create(SlideShowTrans trans,Model model) throws Exception{
		
		slideRefServiceImp.insert(trans);
		model.addAttribute("show", slideShowServiceImp.getInfo(trans.getSlideShowId()));
		return "account/client/slide/listofshow";
		
	}
	
	
	@RequestMapping(value="view")
	public String view(@RequestParam(value="id") int id) {
			
		return "account/client/slide/ref/view";
		
	}
	
	@RequestMapping(value="update",method=RequestMethod.GET)
	public String update(){
		
		return "account/client/slide/update";
		
	}
	
	@RequestMapping(value="get",method=RequestMethod.POST)
	@ResponseBody
	public String get(@RequestParam(value="slideId") int slideId,@RequestParam(value="showId") int showId){
		
		return ToolsFactory.gsonTools.getGson().toJson(slideRefServiceImp.get(slideId, showId));		
		
	}
	
	@RequestMapping(value="update",method=RequestMethod.POST)
	public String update(SlideShowTrans trans,Model model) throws Exception{
		
		slideRefServiceImp.merge(trans);
		model.addAttribute("show", slideShowServiceImp.getInfo(trans.getSlideShowId()));
		return "account/client/slide/listofshow";
		
	}
	
	@RequestMapping(value="delete")

	public String delete(SlideShowTrans trans,Model model) throws Exception{
		
		slideRefServiceImp.delete(trans);
		model.addAttribute("show", slideShowServiceImp.getInfo(trans.getSlideShowId()));
		return "account/client/slide/listofshow";
		
	}
	
}
