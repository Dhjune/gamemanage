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

import com.mdream.gamemanage.common.inteceptor.Permission;
import com.mdream.gamemanage.common.page.Constans;
import com.mdream.gamemanage.common.page.PageNav;
import com.mdream.gamemanage.common.page.PageNavResolver;
import com.mdream.gamemanage.common.proxy.hibernate.criteria.ExpressionGroup;
import com.mdream.gamemanage.common.tools.ToolsFactory;
import com.mdream.gamemanage.model.base.Application;
import com.mdream.gamemanage.model.game.Game;
import com.mdream.gamemanage.service.base.ApplicationServiceImp;

@Controller
@RequestMapping(value="application")
public class ApplicationController {
	@Autowired
	private ApplicationServiceImp applicationServiceImp;
	
	@Autowired
	private PageNavResolver pageNavResolver;

	@RequestMapping(value="create",method=RequestMethod.GET)
	@Permission
	public String create(){
		return "account/app/create";
	}
	
	@RequestMapping(value="create",method=RequestMethod.POST)
	@Permission
	public String create(Application app,Model model){
		Map<String, String> reply =  new HashMap<String,String>();
		try{			
			applicationServiceImp.save(app);
			reply.put("rcode", "1");
			reply.put("message","操作成功");
		}catch(Exception e){
			reply.put("rcode", "-1");
			reply.put("message","错误异常");
		}
		model.addAttribute("rdata", reply);
		return "account/app/success";
	}
	
	
	@RequestMapping(value="view")
	@Permission
	public String view(@RequestParam(value="id") int id) {
			
		return "account/app/view";
		
	}
	
	@RequestMapping(value="update",method=RequestMethod.GET)
	@Permission
	public String update(Model model,HttpServletRequest res,@RequestParam("id") int id){
		model.addAttribute("app", applicationServiceImp.get(id));
		return "account/app/update";
	}
	
	@RequestMapping(value="update",method=RequestMethod.POST)
	@Permission
	@ResponseBody
	public Map<String, String> update(Application app,Model model){
		Map<String, String> reply =  new HashMap<String,String>();
		Application oldApp =  applicationServiceImp.get(app.getId());
		if(oldApp!=null){
			oldApp.setAppName(app.getAppName());
			oldApp.setAppLogo(app.getAppLogo());
			try {
				applicationServiceImp.update(oldApp);
				reply.put("rcode", "1");
				reply.put("message","操作成功");
			} catch (Exception e) {
				reply.put("rcode", "-1");
				reply.put("message","错误异常");
			}
			
		}else{
			reply.put("rcode", "0");
			reply.put("message"," 数据不存在");
		}
		model.addAttribute("rdata", reply);
		return reply;		
	}
	
	@RequestMapping(value="delete")
	@ResponseBody
	@Permission
	public String delete(HttpServletRequest request) throws NumberFormatException, Exception{
		String id =  request.getParameter("id");
		if(id!=null && !id.equals("")){
			applicationServiceImp.delete(Integer.parseInt(id));
		}
		return "account/app/update";
		
	}
	
	@RequestMapping(value="home")
	public String home(Model model)throws Exception{
				
		String url = ToolsFactory.urlTools.tr("/application/list%s");				
		PageNav<Application> context =null;
		Application app =  new Application();
		int pageSize =  Constans.ACCOUNT_DEFAULT_PAGE_SIZE;
		Map map = applicationServiceImp.list(null, app, 1, pageSize);
		List<Application> result =  (List<Application>) map.get("result");
		long total =  (Long) map.get("total");
		context =  pageNavResolver.initPagenav(result, app, total, pageSize, 1, url);		
		model.addAttribute("context", context);	
		model.addAttribute("sign", "games");
		return "account/app/home";
		
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
		
		String url = ToolsFactory.urlTools.tr("/application/list%s");				
		PageNav<Application> context =null;
		Application app =  new Application();
		int pageSize =  Constans.ACCOUNT_DEFAULT_PAGE_SIZE;
		Map map = applicationServiceImp.list(list, app, pageIndex, pageSize);
		List<Application> result =  (List<Application>) map.get("result");
		long total =  (Long) map.get("total");
		context =  pageNavResolver.initPagenav(result, app, total, pageSize, pageIndex, url);		
		model.addAttribute("context", context);	
		model.addAttribute("sign", "games");
		return "account/app/list";	
		
	}
	
}
