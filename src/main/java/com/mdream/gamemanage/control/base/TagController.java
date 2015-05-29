package com.mdream.gamemanage.control.base;

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
import com.mdream.gamemanage.model.base.Application;
import com.mdream.gamemanage.model.game.GameType;
import com.mdream.gamemanage.model.game.Tag;
import com.mdream.gamemanage.service.base.TagServiceImp;


@Controller
@RequestMapping(value="tag")
public class TagController {
	
	@Autowired
	private TagServiceImp tagServiceImp;
	
	@Autowired
	private PageNavResolver pageNavResolver;
	
	@RequestMapping(value="create",method=RequestMethod.GET)
	public String create(){
		return "account/tag/create";
	}
	
	@RequestMapping(value="create",method=RequestMethod.POST)
	public String create(Tag tag,Model model){
		
		Map<String, String> reply =  new HashMap<String,String>();
		try{
			
			tagServiceImp.save(tag);
			reply.put("rcode", "1");
			reply.put("message","操作成功");
		}catch(Exception e){
			reply.put("rcode", "-1");
			reply.put("message","错误异常");
		}
		model.addAttribute("rdata", reply);
		return "account/tag/success";
	}
	
	
	@RequestMapping(value="view")
	public String view(@RequestParam(value="id") int id) {
			
		return "account/tag/view";
		
	}
	
	@RequestMapping(value="update",method=RequestMethod.GET)
	public String update(Model model,HttpServletRequest res,@RequestParam("id") int id){
		model.addAttribute("tag", tagServiceImp.get(id));
		return "account/tag/update";
	}
	
	@RequestMapping(value="update",method=RequestMethod.POST)
	public String update(Model model,Tag tag){
		Map<String, String> reply =  new HashMap<String,String>();
		Tag oldTag =  tagServiceImp.get(tag.getId());
		if(oldTag!=null){
			oldTag.setName(tag.getName());
			oldTag.setStatus(tag.getStatus());
			try {
				tagServiceImp.update(oldTag);
				reply.put("rcode", "1");
				reply.put("message","操作成功");
			} catch (Exception e) {
				reply.put("rcode", "-1");
				reply.put("message","发生异常");
			}
		}else{
			reply.put("rcode", "0");
			reply.put("message"," 数据不存在");
		}
		model.addAttribute("tag", tag);
		
		return "account/tag/update";
		
	}
	
	@RequestMapping(value="delete")
	@ResponseBody
	public String delete(@RequestParam("id") int id)throws Exception{
		
		tagServiceImp.delete(id);
		
		return "account/tag/update";
		
	}
	

	@RequestMapping(value="home")
	public String home(Model model)throws Exception{
				
		String url = ToolsFactory.urlTools.tr("/tag/list%s");				
		PageNav<Tag> context =null;
		Tag tag =  new Tag();
		int pageSize =  Constans.ACCOUNT_DEFAULT_PAGE_SIZE;
		Map map = tagServiceImp.list(null, tag, 1, pageSize);
		List<Tag> result =  (List<Tag>) map.get("result");
		long total =  (Long) map.get("total");
		context =  pageNavResolver.initPagenav(result, tag, total, pageSize, 1, url);		
		model.addAttribute("context", context);	
		model.addAttribute("sign", "tags");
		return "account/tag/home";
		
	}
	
	@RequestMapping(value="list",produces = "application/json")
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
		
		String url = ToolsFactory.urlTools.tr("/tag/list%s");				
		PageNav<Tag> context =null;
		Tag tag =  new Tag();
		int pageSize =  Constans.ACCOUNT_DEFAULT_PAGE_SIZE;
		Map map = tagServiceImp.list(list, tag, pageIndex, pageSize);
		List<Tag> result =  (List<Tag>) map.get("result");
		long total =  (Long) map.get("total");
		context =  pageNavResolver.initPagenav(result, tag, total, pageSize, pageIndex, url);		
		model.addAttribute("context", context);	
		model.addAttribute("sign", "tags");		
		return "account/tag/list";			
	}
	
	@RequestMapping(value="jlist",produces = "application/json")
	@ResponseBody
	public String getJsonList(){
		
		return ToolsFactory.gsonTools.getGson().toJson(tagServiceImp.list());
	}
}
