package com.mdream.gamemanage.control.base;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.mdream.gamemanage.model.qrcode.LogoImageConfig;
import com.mdream.gamemanage.model.qrcode.ObtainColor;
import com.mdream.gamemanage.model.qrcode.QrCodeImageConfig;
import com.mdream.gamemanage.service.base.QrCodeServiceImp;

@Controller
public class QrCodeController {
	
	@Autowired
	private QrCodeServiceImp qrCodeServiceImp;
	
	@RequestMapping(value="qrcode/index",method=RequestMethod.GET)
	public String qrCodeIndex(HttpServletRequest res){	
		
		String type = res.getParameter("type");
		if(type ==null||type.equals("")){
			 type = "text";
		}
		res.setAttribute("logoConfig", null);
		res.setAttribute("qrCodeConfig", null);
		res.setAttribute("type", type);
		return "account/qrcode/index";		
	}
	
	@RequestMapping(value="qrcode/{type}",method=RequestMethod.POST)
	public String qrCodeCreate(HttpServletRequest res){	
		
		HashMap map = (HashMap) qrCodeServiceImp.initImagePath(res);
		String type = res.getParameter("type");
		String imagePath = (String) map.get("imagePath");
		String imageUrl = (String) map.get("imageUrl");		
		String logoPath = res.getParameter("logoPath");	
	
		String width = res.getParameter("width").replace(" ", "");
		String height =  res.getParameter("height").replace(" ", "");
		ObtainColor obtain = new ObtainColor();
		String background_color_str = res.getParameter("background_color_str");
		String to_color_str =  res.getParameter("to_color_str");
		int to_color = obtain.getRgbColor(to_color_str);
		int background_color = obtain.getRgbColor(background_color_str);
		String logoUrl = res.getParameter("logoUrl");
		String content = res.getParameter("content");	
		QrCodeImageConfig qrCodeConfig = new QrCodeImageConfig(to_color,imagePath);
		qrCodeConfig.setBackground_color(background_color);
		qrCodeConfig.setTo_color(to_color);
		qrCodeConfig.setBg_color_str(background_color_str);
		qrCodeConfig.setTo_color_str(to_color_str);
		qrCodeConfig.setImagePath(imagePath);
		qrCodeConfig.setImageUrl(imageUrl);
		qrCodeConfig.setHeight(Integer.parseInt(height));
		qrCodeConfig.setWidth(Integer.parseInt(width));
		qrCodeConfig.setContents(content);
		qrCodeServiceImp.initQrCode(imagePath,imageUrl,qrCodeConfig);
		if(logoPath != null && !logoPath.equals("")){
			LogoImageConfig logoConfig = new LogoImageConfig();
			logoConfig.setLogoPath(logoPath);
			logoConfig.setLogoUrl(logoUrl);
			qrCodeServiceImp.overlapImage(imagePath, logoPath, logoConfig, qrCodeConfig);
			res.setAttribute("logoConfig", logoConfig);
			//impl.overlapImage((String)map.get("imagePath"), logoPath);
		}
		res.setAttribute("qrCodeConfig", qrCodeConfig);
		res.setAttribute("type", type);
		
		return "account/qrcode/index";		
	}
	
	@ResponseBody
	@RequestMapping(value="qrcode/refresh/{type}",method=RequestMethod.POST)	
	public Map qrCodeRefresh(HttpServletRequest res,@PathVariable("type") String type){		
		HashMap<String, String> map = new HashMap<String, String>();
		String imagePath = res.getParameter("imagePath");
		String imageUrl = res.getParameter("imageUrl");
		String logoPath = res.getParameter("logoPath");
		
		String width = res.getParameter("width").replace(" ", "");
		String height =  res.getParameter("height").replace(" ", "");
		ObtainColor obtain = new ObtainColor();
		String background_color_str = res.getParameter("background_color_str");
		String to_color_str =  res.getParameter("to_color_str");
		int to_color = obtain.getRgbColor(to_color_str);
		int background_color = obtain.getRgbColor(background_color_str);
		String logoUrl = res.getParameter("logoUrl");
		String content = res.getParameter("content");		
		QrCodeImageConfig qrCodeConfig = new QrCodeImageConfig(to_color,imagePath);
		qrCodeConfig.setBackground_color(background_color);
		qrCodeConfig.setTo_color(to_color);
		qrCodeConfig.setBg_color_str(background_color_str);
		qrCodeConfig.setTo_color_str(to_color_str);
		qrCodeConfig.setImagePath(imagePath);
		qrCodeConfig.setImageUrl(imageUrl);
		qrCodeConfig.setHeight(Integer.parseInt(height));
		qrCodeConfig.setWidth(Integer.parseInt(width));
		qrCodeConfig.setContents(content);
		qrCodeServiceImp.initQrCode(imagePath,imageUrl,qrCodeConfig);
		
		if(logoPath != null && !logoPath.equals("")){
			LogoImageConfig logoConfig = new LogoImageConfig();
			logoConfig.setLogoPath(logoPath);
			logoConfig.setLogoUrl(logoUrl);		
			qrCodeServiceImp.overlapImage(imagePath, logoPath, logoConfig, qrCodeConfig);
			map.put("logoUrl", logoUrl);
			map.put("logoPath", logoPath);		
		}
		
		map.put("imagePath", imagePath);
		map.put("imageUrl", imageUrl);
		map.put("width", imagePath);
		map.put("height", imagePath);
		map.put("background_color", imagePath);
		map.put("to_color", imagePath);
		map.put("content", imagePath);
		
		return map;
		
	}
	
	
	
}
