package com.mdream.gamemanage.control.admin;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mdream.gamemanage.common.inteceptor.Permission;
import com.mdream.gamemanage.model.admin.Admin;
import com.mdream.gamemanage.service.admin.AdminServiceImp;

@Controller
public class AdminController {

	@Autowired
	private AdminServiceImp adminServiceImp;
	
	
	
	@RequestMapping(value="admin/index")
	@Permission
	private String index(){
		
		return  "account/admin/index";
	}
	
	@RequestMapping(value="admin/login",method=RequestMethod.GET)
	public String login(){
		
		return "account/admin/login";
		
	}
	
	@RequestMapping(value="admin/中文测试",method=RequestMethod.GET)
	@ResponseBody
	public String String (){
		StringHttpMessageConverter s = null;
		return "中文测试sgsfedhrfjy";
	}
	
	
	@RequestMapping(value="admin/login",method=RequestMethod.POST)
	public String login(Admin admin ,Model model,HttpServletResponse response,HttpSession session){
		Admin newAdmin =  adminServiceImp.login(admin);
		if(newAdmin!=null){
			
			session.setAttribute("admin", newAdmin);
			return "redirect:index";	
			
		}else{	
			
			model.addAttribute("error", "用户名或者密码错误");
			return "account/admin/login";			
		}
		
	}
		
}
