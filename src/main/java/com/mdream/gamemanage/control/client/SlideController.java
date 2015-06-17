package com.mdream.gamemanage.control.client;

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
import com.mdream.gamemanage.model.client.Slide;
import com.mdream.gamemanage.model.game.GameType;
import com.mdream.gamemanage.model.game.Tag;
import com.mdream.gamemanage.service.client.SlideServiceImp;
@Controller
@RequestMapping("client/slide")
public class SlideController {
	
	@Autowired
	private SlideServiceImp slideServiceImp;
	
	@Autowired
	private PageNavResolver pageNavResolver;
	
	@RequestMapping(value="create",method=RequestMethod.GET)
	@Permission
	public String create(){
		
		return "account/client/slide/create";
		
	}
	
	@RequestMapping(value="create",method=RequestMethod.POST)
	@Permission
	public String create(Slide slide,Model model){
				
		Map<String, String> reply =  new HashMap<String,String>();
		try{		
			
			slideServiceImp.save(slide);
			reply.put("rcode", "1");
			reply.put("message","操作成功");
			
		}catch(Exception e){
			
			reply.put("rcode", "-1");
			reply.put("message","错误异常");
			
		}
		model.addAttribute("rdata", reply);
		
		return "account/client/slide/success";
	}
	
	
	@RequestMapping(value="view")
	@Permission
	public String view(@RequestParam(value="id") int id) {
			
		return "account/client/slide/view";
		
	}
	
	@RequestMapping(value="update",method=RequestMethod.GET)
	@Permission
	public String update(Model model,HttpServletRequest res,@RequestParam("id") int id){
		model.addAttribute("slide", slideServiceImp.get(id));
		return "account/client/slide/update";
	}
	
	@RequestMapping(value="update",method=RequestMethod.POST)
	@Permission
	@ResponseBody
	public Map<String, String>  update(Model model,Slide slide){
		Slide oldSlide =  slideServiceImp.get(slide.getId());
		Map<String, String> reply =  new HashMap<String,String>();
		if(oldSlide!=null){
			if(oldSlide.getType()==1){
				oldSlide.setImgUrl(slide.getImgUrl());
				oldSlide.setRefUrl(slide.getRefUrl());
				oldSlide.setRefId(slide.getRefId());
			}else{
				oldSlide.setContent(slide.getContent());
			}
			
			
			try {
				slideServiceImp.update(oldSlide);
				reply.put("rcode", "1");
				reply.put("message","操作成功");
			} catch (Exception e) {
				reply.put("rcode", "-1");
				reply.put("message","发生异常");
			}
		}else{
			reply.put("rcode", "0");
			reply.put("message","数据不存在");
		}
		
		return  reply;
		
	}
	
	@RequestMapping(value="delete")
	@Permission
	@ResponseBody
	public String delete(@RequestParam("id") int id) throws Exception {
		slideServiceImp.delete(id);
		return "account/client/slide/update";
		
	}
	
	@RequestMapping(value="home")
	@Permission
	public String home(Model model)throws Exception{
				
		String url = ToolsFactory.urlTools.tr("/client/slide/list%s");				
		PageNav<Slide> context =null;
		Slide slide =  new Slide();
		int pageSize =  Constans.ACCOUNT_DEFAULT_PAGE_SIZE;
		Map map = slideServiceImp.list(null, slide, 1, pageSize);
		List<Slide> result =  (List<Slide>) map.get("result");
		long total =  (Long) map.get("total");
		context =  pageNavResolver.initPagenav(result, slide, total, pageSize, 1, url);		
		model.addAttribute("context", context);	
		model.addAttribute("sign", "slides");
		return "account/client/slide/home";
		
	}
	
	@RequestMapping(value="list",produces = "application/json")
	@Permission
	//@ResponseBody
	public String list(@RequestBody List<ExpressionGroup>  list,HttpServletRequest request,Model model) throws Exception{
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
		
		String url = ToolsFactory.urlTools.tr("/client/slide/list%s");				
		PageNav<Slide> context =null;
		Slide slide =  new Slide();
		int pageSize =  Constans.ACCOUNT_DEFAULT_PAGE_SIZE;
		Map map = slideServiceImp.list(list, slide, pageIndex, pageSize);
		List<Slide> result =  (List<Slide>) map.get("result");
		long total =  (Long) map.get("total");
		context =  pageNavResolver.initPagenav(result, slide, total, pageSize, pageIndex, url);		
		model.addAttribute("context", context);	
		model.addAttribute("sign", "slides");		
		return "account/client/slide/list";
		
	}
	
	@RequestMapping(value="jlist",produces = "application/json")
	@ResponseBody
	@Permission
	public String getJsonList(@RequestParam(value="type") int type) throws Exception{
		
		return ToolsFactory.gsonTools.getGson().toJson(slideServiceImp.list(type));
	}
}
