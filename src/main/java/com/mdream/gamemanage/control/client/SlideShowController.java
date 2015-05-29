package com.mdream.gamemanage.control.client;

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
import com.mdream.gamemanage.model.client.Slide;
import com.mdream.gamemanage.model.client.SlideShow;
import com.mdream.gamemanage.model.game.GameType;
import com.mdream.gamemanage.service.client.SlideServiceImp;
import com.mdream.gamemanage.service.client.SlideShowServiceImp;

@Controller
@RequestMapping(value="client/slideshow")
public class SlideShowController {
	
	@Autowired
	private SlideShowServiceImp slideShowServiceImp;
	
	@Autowired
	private PageNavResolver pageNavResolver;
	
	@RequestMapping(value="create",method=RequestMethod.GET)
	public String create(){
		return "account/client/slideshow/create";
	}
	
	@RequestMapping(value="create",method=RequestMethod.POST)
	public String create(SlideShow show,Model model){
		
		Map<String, String> reply =  new HashMap<String,String>();
		
		try{			
			show.setCreateTime(new Date());
			slideShowServiceImp.save(show);
			reply.put("rcode", "1");
			reply.put("message","操作成功");
		}catch(Exception e){
			reply.put("rcode", "-1");
			reply.put("message","错误异常");
		}
		
		model.addAttribute("rdata", reply);
		return "account/client/slideshow/success";
	}
	
	
	@RequestMapping(value="view")
	public String view(@RequestParam(value="id") int id,Model model) {
		model.addAttribute("show", slideShowServiceImp.getInfo(id));
		return "account/client/slideshow/view";
		
	}
	
	@RequestMapping(value="update",method=RequestMethod.GET)
	public String update(Model model,HttpServletRequest res,@RequestParam("id") int id){
		model.addAttribute("show", slideShowServiceImp.get(id));
		return "account/client/slideshow/update";
	}
	
	@RequestMapping(value="update",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> update(Model model,SlideShow show){
		Map<String, String> reply =  new HashMap<String,String>();
		SlideShow old = slideShowServiceImp.get(show.getId());
		if(old!=null){
			old.setName(show.getName());
			old.setScene(show.getScene());
			old.setIcon(show.getIcon());
			old.setModifyTime(new Date());
			try {
				slideShowServiceImp.update(old);
				reply.put("rcode", "1");
				reply.put("message","操作成功");
			} catch (Exception e) {
				reply.put("rcode", "-1");
				reply.put("message","操作失败");
			}
		}else{
			reply.put("rcode", "0");
			reply.put("message","数据不存在");
		}
		
		return reply;		
	}
	
	
	@RequestMapping(value="delete")
	@ResponseBody
	public String delete(@RequestParam("id") int id){
		slideShowServiceImp.delete(id);
		return "account/client/slideshow/update";
		
	}
	
	@RequestMapping(value="home")
	public String home(Model model)throws Exception{
				
		String url = ToolsFactory.urlTools.tr("/client/slideshow/list%s");				
		PageNav<SlideShow> context =null;
		SlideShow show =  new SlideShow();
		int pageSize =  Constans.ACCOUNT_DEFAULT_PAGE_SIZE;
		Map map = slideShowServiceImp.list(null, show, 1, pageSize);
		List<SlideShow> result =  (List<SlideShow>) map.get("result");
		long total =  (Long) map.get("total");
		context =  pageNavResolver.initPagenav(result, show, total, pageSize, 1, url);		
		model.addAttribute("context", context);	
		model.addAttribute("sign", "shows");
		return "account/client/slideshow/home";
		
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
		String url = ToolsFactory.urlTools.tr("/client/slideshow/list%s");				
		PageNav<SlideShow> context =null;
		SlideShow show =  new SlideShow();
		int pageSize =  Constans.ACCOUNT_DEFAULT_PAGE_SIZE;
		Map map = slideShowServiceImp.list(null, show, pageIndex, pageSize);
		List<SlideShow> result =  (List<SlideShow>) map.get("result");
		long total =  (Long) map.get("total");
		context =  pageNavResolver.initPagenav(result, show, total, pageSize, 1, url);		
		model.addAttribute("context", context);	
		model.addAttribute("sign", "shows");		
		return "account/client/slideshow/list";
		
	}
	
}
