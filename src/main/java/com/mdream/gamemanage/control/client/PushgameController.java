package com.mdream.gamemanage.control.client;


import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mdream.gamemanage.common.http.SyncHttpWork;
import com.mdream.gamemanage.common.inteceptor.Permission;
import com.mdream.gamemanage.common.page.Constans;
import com.mdream.gamemanage.common.page.PageNav;
import com.mdream.gamemanage.common.page.PageNavResolver;
import com.mdream.gamemanage.common.proxy.hibernate.criteria.ExpressionGroup;
import com.mdream.gamemanage.common.tools.ToolsFactory;
import com.mdream.gamemanage.common.xml.JxlResolveImp;
import com.mdream.gamemanage.model.client.PushGame;
import com.mdream.gamemanage.service.client.PushGameServiceImp;


@Controller
@RequestMapping("client/pushgame")
public class PushgameController {
	
	@Autowired
	private PushGameServiceImp pushGameServiceImp;
	
	@Autowired
	private PageNavResolver pageNavResolver;
	
	@Autowired
	private SyncHttpWork syncHttpWork;
	
	@Autowired
	private JxlResolveImp jxlResolveImp;
	
	@RequestMapping(value="create",method=RequestMethod.GET)
	@Permission
	public String create(){
		return "account/client/pushgame/create";
	}
	
	@RequestMapping(value="create",method=RequestMethod.POST)
	@Permission
	public String create(PushGame pg,Model model){
				
		Map<String, String> reply =  new HashMap<String,String>();
		try{			
			pushGameServiceImp.create(pg);
			reply.put("rcode", "1");
			reply.put("message","操作成功");
		}catch(Exception e){
			e.printStackTrace();
			reply.put("rcode", "-1");
			reply.put("message","错误异常");
		}
		model.addAttribute("rdata", reply);
		
		return "account/client/pushgame/success";
	}
	@RequestMapping(value="update",method=RequestMethod.GET)
	@Permission
	public String update(@RequestParam("id") int id,Model model){
		
		model.addAttribute("pushgame", pushGameServiceImp.get(id)) ;
		
		return "account/client/pushgame/update";  
	}
	
	@RequestMapping(value="home")
	@Permission
	public String home(Model model)throws Exception{
						
		String url = ToolsFactory.urlTools.tr("/client/pushgame/list%s");				
		PageNav<PushGame> context =null;
		
		PushGame pg = new PushGame();
		
		int pageSize =  Constans.ACCOUNT_DEFAULT_PAGE_SIZE;
		
		Map map = pushGameServiceImp.getList(null, pg, 1, pageSize);
		
		List<PushGame> result =  (List<PushGame>) map.get("result");
		
		long total =  (Long) map.get("total");
		
		context =  pageNavResolver.initPagenav(result, pg, total, pageSize, 1, url);	
		
		model.addAttribute("context", context);	
		
		model.addAttribute("sign", "p_games");
		
		return "account/client/pushgame/home";		
		
	}
	
	@RequestMapping(value="list",produces = "application/json")
	@Permission
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
		
		String url = ToolsFactory.urlTools.tr("/client/pushgame/list%s");				
		PageNav<PushGame> context =null;
		PushGame pg = new PushGame();
		int pageSize =  Constans.ACCOUNT_DEFAULT_PAGE_SIZE;
		Map map = pushGameServiceImp.getList(list, pg, pageIndex, pageSize);
		List<PushGame> result =  (List<PushGame>) map.get("result");
		long total =  (Long) map.get("total");
		context =  pageNavResolver.initPagenav(result, pg, total, pageSize, pageIndex, url);		
		model.addAttribute("context", context);	
		model.addAttribute("sign", "p_games");		
		return "account/client/pushgame/list";
		
	}
	
	
	@RequestMapping(value="batchSaveOrUpdate")
	@Permission
	@ResponseBody
	public Map<String, String> batchSaveOrUpdate(HttpServletRequest request){
		
		Map<String, String> reply =  new HashMap<String,String>();
		try{			
			String file = syncHttpWork.savefile(request, com.mdream.gamemanage.common.tools.Constans.FILE_BASE_SAVE_PATH);
			jxlResolveImp.filling(file, 2, PushGame.class, pushGameServiceImp);		
			reply.put("rcode", "1");
			reply.put("message","操作成功");
		}catch(Exception e){
			e.printStackTrace();
			reply.put("rcode", "-1");
			reply.put("message","错误异常");
		}
		
		return reply;
	}
	
	
	@RequestMapping(value="downpage")
	@Permission
	public void downpage(HttpServletRequest request,HttpServletResponse response,@RequestParam("pageIndex") int pageIndex){
		try{
			String values =  request.getParameter("values");
			Type listType = new TypeToken<Collection<ExpressionGroup>>() {}.getType();			
	//	   GsonHttpMessageConverter gmc = (GsonHttpMessageConverter) ToolsFactory.applicationContext.getBean(GsonHttpMessageConverter.class);
		    Gson gson =new Gson();
			List<ExpressionGroup> list =gson.fromJson(values, listType);
		    PushGame pg = new PushGame();
		    Map map = pushGameServiceImp.getList(list, pg, pageIndex,  Constans.ACCOUNT_DEFAULT_PAGE_SIZE);
			
			List<PushGame> result =  (List<PushGame>) map.get("result");
			String[][] titles = new String[][]{
				{"id","type","gameId","gameName","sort","status"},{"选送id","选送类型","游戏id","游戏名称","排序","状态"}
			};
			
			jxlResolveImp.reading(titles, syncHttpWork.createFile(".xls", com.mdream.gamemanage.common.tools.Constans.FILE_BASE_SAVE_PATH), result, PushGame.class, response);
			}catch(Exception e){
				e.printStackTrace();
			}
		
		
	}
	
	@RequestMapping(value="downloadAll")
	@Permission
	public void downloadAll(HttpServletRequest request,HttpServletResponse response){
		
		try{
			String values =  request.getParameter("values");
			Type listType = new TypeToken<Collection<ExpressionGroup>>() {}.getType();
			
	//	   GsonHttpMessageConverter gmc = (GsonHttpMessageConverter) ToolsFactory.applicationContext.getBean(GsonHttpMessageConverter.class);
		   Gson gson =new Gson();
			List<ExpressionGroup> list =gson.fromJson(values, listType);
		   // System.out.println(list.get(0));
		    PushGame pg = new PushGame();
		    List<PushGame> result= pushGameServiceImp.getAll(list, pg);
			
			String[][] titles = new String[][]{
					{"id","type","gameId","gameName","sort","status"},{"选送id","选送类型","游戏id","游戏名称","排序","状态"}
			};
			
			jxlResolveImp.reading(titles, syncHttpWork.createFile(".xls", com.mdream.gamemanage.common.tools.Constans.FILE_BASE_SAVE_PATH), result, PushGame.class, response);
			}catch(Exception e){
				e.printStackTrace();
			}
		
	}
	
}
