package com.mdream.gamemanage.control.base;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mdream.gamemanage.model.client.Slide;
import com.mdream.gamemanage.model.client.SlideShow;
import com.mdream.gamemanage.model.game.Game;
import com.mdream.gamemanage.model.game.GameComment;
import com.mdream.gamemanage.model.game.GamePic;
import com.mdream.gamemanage.model.game.GameType;
import com.mdream.gamemanage.model.game.Tag;
import com.mdream.gamemanage.model.resulttransformer.GameCommentTrans;
import com.mdream.gamemanage.model.resulttransformer.GamePicTrans;
import com.mdream.gamemanage.model.resulttransformer.GameTypeTrans;
import com.mdream.gamemanage.model.resulttransformer.SlideShowTrans;
import com.mdream.gamemanage.model.resulttransformer.TypeTagTrans;
import com.mdream.gamemanage.service.base.TagServiceImp;
import com.mdream.gamemanage.service.client.SlideRefServiceImp;
import com.mdream.gamemanage.service.client.SlideServiceImp;
import com.mdream.gamemanage.service.client.SlideShowServiceImp;
import com.mdream.gamemanage.service.game.GameCommentRefServiceImp;
import com.mdream.gamemanage.service.game.GameCommentServiceImp;
import com.mdream.gamemanage.service.game.GamePicServiceImp;
import com.mdream.gamemanage.service.game.GameServiceImp;
import com.mdream.gamemanage.service.game.GameTypeRefServiceImp;
import com.mdream.gamemanage.service.game.GameTypeServiceImp;
import com.mdream.gamemanage.service.game.TypeTagServiceImp;


@Controller
@RequestMapping("import")
public class ImportDataController {
		
	@Autowired
	private GameServiceImp gameServiceImp;
	
	@Autowired
	private GameTypeServiceImp gameTypeServiceImp;
	
	@Autowired
	private TagServiceImp tagServiceImp;
	
	@Autowired
	private GameCommentServiceImp gameCommentServiceImp;
	
	@Autowired
	private SlideShowServiceImp slideShowServiceImp;
	
	@Autowired
	private SlideServiceImp slideServiceImp;
	
	@Autowired
	private  GamePicServiceImp gamePicServiceImp;
	@Autowired
	private GameTypeRefServiceImp gameTypeRefServiceImp;
	
	@Autowired
	private GameCommentRefServiceImp gameCommentRefServiceImp;
	
	@Autowired
	private TypeTagServiceImp typeTagServiceImp;
	
	@Autowired
	private SlideRefServiceImp slideRefServiceImp;
	
	@RequestMapping("/gameFile")
	@ResponseBody
	public String   importgame( 
			HttpServletRequest request) throws Exception, BiffException{
		ServletContext application = request.getSession().getServletContext();
		String savePath = application.getRealPath("/") + "upload/";
					
			InputStream instream = new FileInputStream(savefile(request,savePath));   			  
			Workbook  readwb = Workbook.getWorkbook(instream);
			Sheet readsheet = readwb.getSheet(0); 
			    int rsColumns = readsheet.getColumns();   			  
	            //获取Sheet表中所包含的总行数   	  
	            int rsRows = readsheet.getRows();   
	  
	            //获取指定单元格的对象引用   
	            Cell cell = null;
	            if(rsColumns>12){
	            	
	            	for (int i =2; i < rsRows; i++)   	  
		            {   
		            	
		            	cell = readsheet.getCell(0, i);
		            	int id =  Integer.parseInt(cell.getContents());
		            	Game game = gameServiceImp.get(id);
		            	if(game!=null){
		            		cell = readsheet.getCell(1, i);  	            	
		                    game.setName(cell.getContents());
		                    
		                    cell =  readsheet.getCell(2, i);
		                    game.setDescription(cell.getContents());
		                    
		                    cell = readsheet.getCell(3, i);
		                    game.setUrl(cell.getContents());
		                    
		                    cell = readsheet.getCell(4, i);
		                    game.setIcon(cell.getContents());
		                    
		                    cell = readsheet.getCell(5, i);
		                    game.setImg(cell.getContents());
		                    
		                    cell = readsheet.getCell(6, i);
		                    game.setHotflag(Integer.parseInt(cell.getContents()));
		                    
		                    cell = readsheet.getCell(7, i);
		                    game.setNowflag(Integer.parseInt(cell.getContents()));
		                    
		                    cell = readsheet.getCell(8, i);
		                    game.setGodflag(Integer.parseInt(cell.getContents()));
		                    
		                    cell = readsheet.getCell(9, i);
		                    game.setRecommend(Integer.parseInt(cell.getContents()));
		                    
		                    cell = readsheet.getCell(10, i);
		                    game.setStar(Integer.parseInt(cell.getContents()));
		                    
		                    cell = readsheet.getCell(11, i);
		                    game.setPraise(Integer.parseInt(cell.getContents()));
		                    
		                    cell = readsheet.getCell(12, i);
		                    game.setBelittle(Integer.parseInt(cell.getContents()));
		                                      	  
		                    
		                    cell = readsheet.getCell(13, i);
		                    game.setStatus(Integer.parseInt(cell.getContents()));
		                    
		                    game.setModifyTime(new Date());
		                    
		                    gameServiceImp.update(game);
		            	}
		            	
		            }  
	            	
	            }else{
	            for (int i = 2; i < rsRows; i++)   	  
	            {   
	            	Game game = new Game();
	            	cell = readsheet.getCell(0, i);  	            	
                    game.setName(cell.getContents());
                    
                    cell =  readsheet.getCell(1, i);
                    game.setDescription(cell.getContents());
                    
                    cell = readsheet.getCell(2, i);
                    game.setUrl(cell.getContents());
                    
                    cell = readsheet.getCell(3, i);
                    game.setIcon(cell.getContents());
                    
                    cell = readsheet.getCell(4, i);
                    game.setImg(cell.getContents());
                    
                    cell = readsheet.getCell(5, i);
                    game.setHotflag(Integer.parseInt(cell.getContents()));
                    
                    cell = readsheet.getCell(6, i);
                    game.setNowflag(Integer.parseInt(cell.getContents()));
                    
                    cell = readsheet.getCell(7, i);
                    game.setGodflag(Integer.parseInt(cell.getContents()));
                    
                    cell = readsheet.getCell(8, i);
                    game.setRecommend(Integer.parseInt(cell.getContents()));
                    
                    cell = readsheet.getCell(9, i);
                    game.setStar(Integer.parseInt(cell.getContents()));
                    
                    cell = readsheet.getCell(10, i);
                    game.setPraise(Integer.parseInt(cell.getContents()));
                    
                    cell = readsheet.getCell(11, i);
                    game.setBelittle(Integer.parseInt(cell.getContents()));
                                  
                    game.setCreateTime(new Date());
                    
                    gameServiceImp.save(game);
	            }   
	            }
		
		return "";
		
	}
	@RequestMapping(value="downgameFile")
	public void downgameFile(HttpServletResponse response,HttpServletRequest request){

		String[] title = {"id","name","description","url","icon","img","hotflag","nowflag","godflag","recommend",
				"star","praise","belittle","status"};
		String[] beizhu = {"游戏id","游戏名称","简介","游戏链接","小图","大图","热门标签1-是，0-否","最新标签1-是，0-否","神级标签1-是，0-否","推荐标签1-是，0-否",
				"星级1-10","点赞数","差评数","使用状态"};
		ServletContext application = request.getSession().getServletContext();
		String savePath = application.getRealPath("/") + "upload/";
		savePath +=  "file/";
    	File saveDirFile = new File(savePath);
    	if (!saveDirFile.exists()) {
    		saveDirFile.mkdirs();
    	}
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	String ymd = sdf.format(new Date());
    	savePath += ymd + "/";
    	File dirFile = new File(savePath);
    	if (!dirFile.exists()) {
    		dirFile.mkdirs();
    	}
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String newFileName = df.format(new Date()) + "_"
				+ new Random().nextInt(1000) + ".xls" ;
		List<Game> list =  gameServiceImp.list();
		File file = new File(savePath+newFileName); //封装成File对象
		
		  WritableWorkbook wwb = null;   
          // 新建立一个jxl文件,即在d盘下生成testJXL.xls   
         OutputStream os;
		try {
			os = new FileOutputStream(file);
			wwb=Workbook.createWorkbook(os);
			WritableSheet sheet = wwb.createSheet("Root Sheet", 0);   
			Label label=null;   
            for(int i=0;i<title.length;i++){                 
                label = new Label(i,0,title[i]);                           
                sheet.addCell(label);   
            }
            for(int i=0;i<beizhu.length;i++){                 
                label = new Label(i,1,beizhu[i]);                           
                sheet.addCell(label);   
            }
            Game game = null;
            for(int i=0;i<list.size();i++){
            	 game = list.get(i);
            	label =  new Label(0, i+2, String.valueOf(game.getId()));
            	sheet.addCell(label); 
            	label =  new Label(1, i+2, game.getName());
            	sheet.addCell(label);
            	
            	label =  new Label(2, i+2, game.getDescription());
            	sheet.addCell(label);
            	
            	label =  new Label(3, i+2, game.getUrl());
            	sheet.addCell(label);
            	
            	label =  new Label(4, i+2, game.getIcon());
            	sheet.addCell(label);
            	
            	label =  new Label(5, i+2, game.getImg());
            	sheet.addCell(label);
            	
            	label =  new Label(6, i+2, String.valueOf(game.getHotflag()));
            	sheet.addCell(label);
            	
            	label =  new Label(7, i+2, String.valueOf(game.getNowflag()));
            	sheet.addCell(label);
            	
            	label =  new Label(8, i+2, String.valueOf(game.getGodflag()));
            	sheet.addCell(label);
            	
            	label =  new Label(9, i+2, String.valueOf(game.getRecommend()));
            	sheet.addCell(label);
            	
            	label =  new Label(10, i+2, String.valueOf(game.getStar()));
            	sheet.addCell(label);
            	
            	label =  new Label(11, i+2, String.valueOf(game.getPraise()));
            	sheet.addCell(label);
            	
            	label =  new Label(12, i+2, String.valueOf(game.getBelittle()));
            	sheet.addCell(label);
            	label =  new Label(13, i+2, String.valueOf(game.getStatus()));
            	sheet.addCell(label);
            }
            wwb.write();
            wwb.close();
            os.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
            
		
	  	String fileName = file.getName();  //得到文件名
  		int fileLen = (int)file.length();
  		
  		//设置响应头信息
		response.setContentType("application/x-msdownload");   
		String str = "attachment;filename=" + fileName;
		response.setHeader("Content-Disposition", str);  
		response.setContentLength(fileLen);
   
		//分别得到输入 输出流
		FileInputStream input = null;
				
		ServletOutputStream out = null;
		
		try {
			input = new FileInputStream(file);
		
			out = response.getOutputStream();
		   
			byte[] cache = new byte[1024*512];  //设置读写缓存512K
			int len = 0;
			while((len = input.read(cache)) > 0)
			{
				out.write(cache,0,len);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//关闭流
			try {
				input.close();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}	
	
	@RequestMapping("/typeFile")
	@ResponseBody
	private String typeFime( 
			HttpServletRequest request) throws Exception, BiffException{
		ServletContext application = request.getSession().getServletContext();
		String savePath = application.getRealPath("/") + "upload/";
				
			InputStream instream = new FileInputStream(savefile(request,savePath));   			  
			Workbook  readwb = Workbook.getWorkbook(instream);
			Sheet readsheet = readwb.getSheet(0); 
			 int rsColumns = readsheet.getColumns();   			  
	            //获取Sheet表中所包含的总行数   	  
	            int rsRows = readsheet.getRows();   	  
	            //获取指定单元格的对象引用   
	            Cell cell = null;
	            if(rsColumns>2){
	            	for (int i = 2; i < rsRows; i++)   	  
		            {   
	            		
	            		cell = readsheet.getCell(0, i);
	            		int id =  Integer.parseInt(cell.getContents());
		            	GameType type = gameTypeServiceImp.get(id);
		            	if(type!=null){
		            		cell = readsheet.getCell(1, i);  	            	
			            	type.setName(cell.getContents());
			            	
			            	cell = readsheet.getCell(2, i);
			            	type.setSort(Integer.parseInt(cell.getContents()));
			            	
			            	cell = readsheet.getCell(3, i);
			            	type.setStatus(Integer.parseInt(cell.getContents()));
			            	  
			            	type.setModifyTime(new Date());
			            	
		                    gameTypeServiceImp.update(type);
		            	}
		            	
		            }
	            }else{
		            for (int i = 2; i < rsRows; i++)   	  
		            {   
		            	GameType type = new GameType();
		            	
		            	cell = readsheet.getCell(0, i);  	            	
		            	type.setName(cell.getContents());
		            	
		            	cell = readsheet.getCell(1, i);
		            	type.setSort(Integer.parseInt(cell.getContents()));
		            	
		            	type.setCreateTime(new Date());                                     	        
	                    gameTypeServiceImp.save(type);
		            }
	            }
		
		
		return "";
		
	}
	
	@RequestMapping(value="downtypeFile")
	public void downtypeFile(HttpServletResponse response,HttpServletRequest request){

		String[] title = {"id","name","sort","status"};   
		String[] beizhu = {"分类id"," 分类名称","序号","状态"};  
		ServletContext application = request.getSession().getServletContext();
		String savePath = application.getRealPath("/") + "upload/";
		savePath +=  "file/";
    	File saveDirFile = new File(savePath);
    	if (!saveDirFile.exists()) {
    		saveDirFile.mkdirs();
    	}
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	String ymd = sdf.format(new Date());
    	savePath += ymd + "/";
    	File dirFile = new File(savePath);
    	if (!dirFile.exists()) {
    		dirFile.mkdirs();
    	}
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String newFileName = df.format(new Date()) + "_"
				+ new Random().nextInt(1000) + ".xls" ;
		List<GameType> list =  gameTypeServiceImp.list();
		File file = new File(savePath+newFileName); //封装成File对象
		
		  WritableWorkbook wwb = null;   
          // 新建立一个jxl文件,即在d盘下生成testJXL.xls   
         OutputStream os;
		try {
			os = new FileOutputStream(file);
			wwb=Workbook.createWorkbook(os);
			WritableSheet sheet = wwb.createSheet("Root Sheet", 0);   
			Label label=null;   
            for(int i=0;i<title.length;i++){                 
                label = new Label(i,0,title[i]);                           
                sheet.addCell(label);   
            }
            for(int i=0;i<beizhu.length;i++){                 
                label = new Label(i,1,beizhu[i]);                           
                sheet.addCell(label);   
            }
            GameType type = null;
            for(int i=0;i<list.size();i++){
            	type = list.get(i);
            	
            	label =  new Label(0, i+2, String.valueOf(type.getId()));            	
            	sheet.addCell(label);  
            	
            	label =  new Label(1, i+2, type.getName());
            	sheet.addCell(label); 
            	
            	label =  new Label(2, i+2, String.valueOf(type.getSort()));
            	sheet.addCell(label);
            	
            	label =  new Label(3, i+2, String.valueOf(type.getStatus()));
            	sheet.addCell(label);
            	
            }
            wwb.write();
            wwb.close();
            os.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
            
		
	  	String fileName = file.getName();  //得到文件名
  		int fileLen = (int)file.length();
  		
  		//设置响应头信息
		response.setContentType("application/x-msdownload");   
		String str = "attachment;filename=" + fileName;
		response.setHeader("Content-Disposition", str);  
		response.setContentLength(fileLen);
   
		//分别得到输入 输出流
		FileInputStream input = null;
				
		ServletOutputStream out = null;
		
		try {
			input = new FileInputStream(file);
		
			out = response.getOutputStream();
		   
			byte[] cache = new byte[1024*512];  //设置读写缓存512K
			int len = 0;
			while((len = input.read(cache)) > 0)
			{
				out.write(cache,0,len);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//关闭流
			try {
				input.close();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}	
	
	
	@RequestMapping("/tagFile")
	@ResponseBody
	private String tagFile( 
			HttpServletRequest request) throws Exception, BiffException{
		ServletContext application = request.getSession().getServletContext();
		String savePath = application.getRealPath("/") + "upload/";
				
			InputStream instream = new FileInputStream(savefile(request,savePath));   			  
			Workbook  readwb = Workbook.getWorkbook(instream);
			Sheet readsheet = readwb.getSheet(0); 
			 int rsColumns = readsheet.getColumns();   			  
	            //获取Sheet表中所包含的总行数   	  
	            int rsRows = readsheet.getRows();   	  
	            //获取指定单元格的对象引用   
	            Cell cell = null;
	        if(rsColumns>1){
	        	 for (int i = 2; i < rsRows; i++)   	  
		            {   
		            	
		            	cell = readsheet.getCell(0, i);
		            	int id =  Integer.parseInt(cell.getContents());
		            	Tag tag  = tagServiceImp.get(id);
		            	if(tag!=null){
		            		cell = readsheet.getCell(1, i);
		            		tag.setName(cell.getContents());
		            		
		            		cell = readsheet.getCell(2, i);
		            		tag.setStatus(Integer.parseInt(cell.getContents()));
		            		tag.setModifyTime(new Date());
		            		//
		            		tagServiceImp.merge(tag);
		                   // tagServiceImp.update(tag);
		            	}
		            	
		            } 
	        }else{
	            for (int i = 2; i < rsRows; i++)   	  
	            {   
	            	Tag tag = new Tag();
	            	cell = readsheet.getCell(0, i);  	            	
	            	tag.setName(cell.getContents());	
	            	tag.setCreateTime(new Date());
                    tagServiceImp.save(tag);
	            }  
	        }
		
		
		return "";
		
	}
	
	@RequestMapping(value="downtagFile")
	public void downtagFile(HttpServletResponse response,HttpServletRequest request){

		String[] title = {"id","name","status"}; 
		String[] beizhu = {"词条id","词条名称","状态"}; 
		ServletContext application = request.getSession().getServletContext();
		String savePath = application.getRealPath("/") + "upload/";
		savePath +=  "file/";
    	File saveDirFile = new File(savePath);
    	if (!saveDirFile.exists()) {
    		saveDirFile.mkdirs();
    	}
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	String ymd = sdf.format(new Date());
    	savePath += ymd + "/";
    	File dirFile = new File(savePath);
    	if (!dirFile.exists()) {
    		dirFile.mkdirs();
    	}
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String newFileName = df.format(new Date()) + "_"
				+ new Random().nextInt(1000) + ".xls" ;
		List<Tag> list =  tagServiceImp.list();
		File file = new File(savePath+newFileName); //封装成File对象
		
		  WritableWorkbook wwb = null;   
          // 新建立一个jxl文件,即在d盘下生成testJXL.xls   
         OutputStream os;
		try {
			os = new FileOutputStream(file);
			wwb=Workbook.createWorkbook(os);
			WritableSheet sheet = wwb.createSheet("Root Sheet", 0);   
			Label label=null;   
            for(int i=0;i<title.length;i++){                 
                label = new Label(i,0,title[i]);                           
                sheet.addCell(label);   
            }
            for(int i=0;i<beizhu.length;i++){                 
                label = new Label(i,1,beizhu[i]);                           
                sheet.addCell(label);   
            }
            Tag tag = null;
            for(int i=0;i<list.size();i++){
            	tag = list.get(i);
            	
            	label =  new Label(0, i+2, String.valueOf(tag.getId()));            	
            	sheet.addCell(label);  
            	
            	label =  new Label(1, i+2, tag.getName());
            	sheet.addCell(label); 
            	
            	label =  new Label(2, i+2,String.valueOf(tag.getStatus()));
            	sheet.addCell(label); 
            	            	
            }
            wwb.write();
            wwb.close();
            os.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
            
		
	  	String fileName = file.getName();  //得到文件名
  		int fileLen = (int)file.length();
  		
  		//设置响应头信息
		response.setContentType("application/x-msdownload");   
		String str = "attachment;filename=" + fileName;
		response.setHeader("Content-Disposition", str);  
		response.setContentLength(fileLen);
   
		//分别得到输入 输出流
		FileInputStream input = null;
				
		ServletOutputStream out = null;
		
		try {
			input = new FileInputStream(file);
		
			out = response.getOutputStream();
		   
			byte[] cache = new byte[1024*512];  //设置读写缓存512K
			int len = 0;
			while((len = input.read(cache)) > 0)
			{
				out.write(cache,0,len);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//关闭流
			try {
				input.close();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}	
	
	
	
	@RequestMapping("/commentFile")
	@ResponseBody
	private String commentFile(
			HttpServletRequest request) throws Exception, BiffException{
		ServletContext application = request.getSession().getServletContext();
		String savePath = application.getRealPath("/") + "upload/";
				
			InputStream instream = new FileInputStream(savefile(request,savePath));   			  
			Workbook  readwb = Workbook.getWorkbook(instream);
			Sheet readsheet = readwb.getSheet(0); 
			 int rsColumns = readsheet.getColumns();   			  
	            //获取Sheet表中所包含的总行数   	  
	            int rsRows = readsheet.getRows();   	  
	            //获取指定单元格的对象引用   
	            Cell cell = null;
	            if(rsColumns>1){
	            	for (int i = 2; i < rsRows; i++)   	  
		            {   
	            		cell = readsheet.getCell(0, i);  	
		            	int  id =  Integer.parseInt(cell.getContents());
		            	GameComment comment = gameCommentServiceImp.get(id);
		            	if(comment!=null){
		            		cell = readsheet.getCell(1, i);  	            			            	
			            	comment.setContent(cell.getContents());  
			            	cell = readsheet.getCell(2, i);
			            	comment.setStatus(Integer.parseInt(cell.getContents()));
			            	comment.setModifyTime(new Date());
			            	gameCommentServiceImp.update(comment);
		            	}
		            	       	            	                                   	        
	                    
	                    
		            }  
	            }else{
		            for (int i = 2; i < rsRows; i++)   	  
		            {   
		            	
		            	GameComment comment = new GameComment();
		            	cell = readsheet.getCell(0, i);  	            	
		            	comment.setContent(cell.getContents());
		            	comment.setCreateTime(new Date());
	                    gameCommentServiceImp.save(comment);
	                    
		            }
	            }   	  
		
		
		return "";
		
	}
	
	@RequestMapping(value="downcommentFile")
	public void downcommentFile(HttpServletResponse response,HttpServletRequest request)throws Exception{

		String[] title = {"id","content","status"};   
		String[] beizhu = {"评价id","评价内容","状态"}; 
		ServletContext application = request.getSession().getServletContext();
		String savePath = application.getRealPath("/") + "upload/";
		savePath +=  "file/";
    	File saveDirFile = new File(savePath);
    	if (!saveDirFile.exists()) {
    		saveDirFile.mkdirs();
    	}
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	String ymd = sdf.format(new Date());
    	savePath += ymd + "/";
    	File dirFile = new File(savePath);
    	if (!dirFile.exists()) {
    		dirFile.mkdirs();
    	}
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String newFileName = df.format(new Date()) + "_"
				+ new Random().nextInt(1000) + ".xls" ;
		List<GameComment> list =  gameCommentServiceImp.list();
		File file = new File(savePath+newFileName); //封装成File对象
		
		  WritableWorkbook wwb = null;   
          // 新建立一个jxl文件,即在d盘下生成testJXL.xls   
         OutputStream os;
		try {
			os = new FileOutputStream(file);
			wwb=Workbook.createWorkbook(os);
			WritableSheet sheet = wwb.createSheet("Root Sheet", 0);   
			Label label=null;   
            for(int i=0;i<title.length;i++){                 
                label = new Label(i,0,title[i]);                           
                sheet.addCell(label);   
            }
            for(int i=0;i<beizhu.length;i++){                 
                label = new Label(i,1,beizhu[i]);                           
                sheet.addCell(label);   
            }
            GameComment comment = null;
            for(int i=0;i<list.size();i++){
            	comment = list.get(i);
            	
            	label =  new Label(0, i+2, String.valueOf(comment.getId()));            	
            	sheet.addCell(label); 
            	
            	label =  new Label(1, i+2, comment.getContent());
            	sheet.addCell(label); 
            	label =  new Label(2, i+2,String.valueOf(comment.getStatus()));
            	sheet.addCell(label); 
            	            	
            }
            wwb.write();
            wwb.close();
            os.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
            
		
	  	String fileName = file.getName();  //得到文件名
  		int fileLen = (int)file.length();
  		
  		//设置响应头信息
		response.setContentType("application/x-msdownload");   
		String str = "attachment;filename=" + fileName;
		response.setHeader("Content-Disposition", str);  
		response.setContentLength(fileLen);
   
		//分别得到输入 输出流
		FileInputStream input = null;
				
		ServletOutputStream out = null;
		
		try {
			input = new FileInputStream(file);
		
			out = response.getOutputStream();
		   
			byte[] cache = new byte[1024*512];  //设置读写缓存512K
			int len = 0;
			while((len = input.read(cache)) > 0)
			{
				out.write(cache,0,len);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//关闭流
			try {
				input.close();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}	
	
	
	@RequestMapping("/slideFile")
	@ResponseBody
	private String slideFile(
			HttpServletRequest request) throws Exception, BiffException{
		ServletContext application = request.getSession().getServletContext();
		String savePath = application.getRealPath("/") + "upload/";
				
			InputStream instream = new FileInputStream(savefile(request,savePath));   			  
			Workbook  readwb = Workbook.getWorkbook(instream);
			Sheet readsheet = readwb.getSheet(0); 
			 int rsColumns = readsheet.getColumns();   			  
	            //获取Sheet表中所包含的总行数   	  
	            int rsRows = readsheet.getRows();   	  
	            //获取指定单元格的对象引用   
	            Cell cell = null;
	            
	            if(rsColumns>5){
	            	for (int i = 2; i < rsRows; i++)   	  
		            {   		            	
	            		
		            	cell = readsheet.getCell(0, i);
		            	int id =  Integer.parseInt(cell.getContents());
		            	Slide slide = slideServiceImp.get(id);
		            	if(slide!=null){
		            		cell = readsheet.getCell(1, i);  	            	
			            	slide.setType(Integer.parseInt(cell.getContents()));    
			            	cell = readsheet.getCell(2,i);
			            	slide.setImgUrl(cell.getContents());
			            	
			            	cell = readsheet.getCell(3,i);
			            	slide.setContent(cell.getContents());
			            	
			            	cell = readsheet.getCell(4,i);
			            	slide.setRefUrl(cell.getContents());
			            	
			            	cell = readsheet.getCell(5,i);
			            	if(cell.getContents()!=null && !cell.getContents().equals("")){	            		
			            		slide.setRefId(Integer.parseInt(cell.getContents()));	            		
			            	}	
			            	cell = readsheet.getCell(6, i);
			            	slide.setStatus(Integer.parseInt(cell.getContents()));
			            	slide.setModifyTime(new Date());
		                    slideServiceImp.update(slide);   
		            	}
		            			            	                 
		            }   
	            }else{
	            
		            for (int i = 2; i < rsRows; i++)   	  
		            {   
		            	
		            	Slide slide = new Slide();
		            	cell = readsheet.getCell(0, i);  	            	
		            	slide.setType(Integer.parseInt(cell.getContents()));    
		            	cell = readsheet.getCell(1,i);
		            	slide.setImgUrl(cell.getContents());
		            	
		            	cell = readsheet.getCell(2,i);
		            	slide.setContent(cell.getContents());
		            	
		            	cell = readsheet.getCell(3,i);
		            	slide.setRefUrl(cell.getContents());
		            	
		            	cell = readsheet.getCell(4,i);
		            	if(cell.getContents()!=null && !cell.getContents().equals("")){	            		
		            		slide.setRefId(Integer.parseInt(cell.getContents()));	            		
		            	}	   
		            	slide.setCreateTime(new Date());
	                    slideServiceImp.save(slide);                    
		            } 
	            }
		
		return "";
		
	}
	
	@RequestMapping(value="downslideFile")
	public void downslideFile(HttpServletResponse response,HttpServletRequest request){

		String[] title = {"id","type","imgUrl","content","refUrl","refId","status"};
		String[] beizhu = {"轮播子项id","轮播类型1-图片，2-字幕","imgUrl-图片类型添加","content-字幕类型添加","refUrl-图片类型添加","refId-引用游戏id，默认为0","状态"};
		ServletContext application = request.getSession().getServletContext();
		String savePath = application.getRealPath("/") + "upload/";
		savePath +=  "file/";
    	File saveDirFile = new File(savePath);
    	if (!saveDirFile.exists()) {
    		saveDirFile.mkdirs();
    	}
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	String ymd = sdf.format(new Date());
    	savePath += ymd + "/";
    	File dirFile = new File(savePath);
    	if (!dirFile.exists()) {
    		dirFile.mkdirs();
    	}
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String newFileName = df.format(new Date()) + "_"
				+ new Random().nextInt(1000) + ".xls" ;
		List<Slide> list =  slideServiceImp.list();
		File file = new File(savePath+newFileName); //封装成File对象
		
		  WritableWorkbook wwb = null;   
          // 新建立一个jxl文件,即在d盘下生成testJXL.xls   
         OutputStream os;
		try {
			os = new FileOutputStream(file);
			wwb=Workbook.createWorkbook(os);
			WritableSheet sheet = wwb.createSheet("Root Sheet", 0);   
			Label label=null;   
            for(int i=0;i<title.length;i++){                 
                label = new Label(i,0,title[i]);                           
                sheet.addCell(label);   
            }
            for(int i=0;i<beizhu.length;i++){                 
                label = new Label(i,1,beizhu[i]);                           
                sheet.addCell(label);   
            }
            Slide slide = null;
            for(int i=0;i<list.size();i++){
            	slide = list.get(i);            	
            	label =  new Label(0, i+2, String.valueOf(slide.getId()));            	
            	sheet.addCell(label); 
            	
            	label =  new Label(1, i+2, String.valueOf(slide.getType()));          	
            	sheet.addCell(label); 
            	
            	label =  new Label(2, i+2, slide.getImgUrl());          	
            	sheet.addCell(label);
            	
            	label =  new Label(3, i+2, slide.getContent());          	
            	sheet.addCell(label);
            	
            	label =  new Label(4, i+2, slide.getRefUrl());          	
            	sheet.addCell(label);
            	if(slide.getRefId()!=null){
            		label =  new Label(5, i+2, String.valueOf(slide.getRefId()));          	
                	sheet.addCell(label);
            	}
            	label =  new Label(6, i+2, String.valueOf(slide.getStatus()));          	
            	sheet.addCell(label); 
            		
            }
            wwb.write();
            wwb.close();
            os.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
            
		
	  	String fileName = file.getName();  //得到文件名
  		int fileLen = (int)file.length();
  		
  		//设置响应头信息
		response.setContentType("application/x-msdownload");   
		String str = "attachment;filename=" + fileName;
		response.setHeader("Content-Disposition", str);  
		response.setContentLength(fileLen);
   
		//分别得到输入 输出流
		FileInputStream input = null;
				
		ServletOutputStream out = null;
		
		try {
			input = new FileInputStream(file);
		
			out = response.getOutputStream();
		   
			byte[] cache = new byte[1024*512];  //设置读写缓存512K
			int len = 0;
			while((len = input.read(cache)) > 0)
			{
				out.write(cache,0,len);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//关闭流
			try {
				input.close();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}	
	
	

	@RequestMapping("/showFile")
	@ResponseBody
	private String showFile( 
			HttpServletRequest request) throws Exception, BiffException{
		ServletContext application = request.getSession().getServletContext();
		String savePath = application.getRealPath("/") + "upload/";
				
			InputStream instream = new FileInputStream(savefile(request,savePath));   			  
			Workbook  readwb = Workbook.getWorkbook(instream);
			Sheet readsheet = readwb.getSheet(0); 
			int rsColumns = readsheet.getColumns();   			  
	            //获取Sheet表中所包含的总行数   	  
	        int rsRows = readsheet.getRows();   	  
	            //获取指定单元格的对象引用   
	        Cell cell = null;
	        if(rsColumns>4){
	        	
	        	for (int i = 2; i < rsRows; i++){   	            	
	            	SlideShow show = new SlideShow();
	            	
	            	cell =  readsheet.getCell(0, i);
	            	show.setId(Integer.parseInt(cell.getContents()));
	            	
	            	cell = readsheet.getCell(1, i);  	            	
	            	show.setType(Integer.parseInt(cell.getContents())); 
	            	
	            	cell = readsheet.getCell(2,i);
	            	show.setScene(Integer.parseInt(cell.getContents()));
	            	
	            	cell = readsheet.getCell(3,i);
	            	show.setIcon(cell.getContents());
	            	
	            	cell = readsheet.getCell(4,i);
	            	show.setName(cell.getContents());
	            	
	            	cell = readsheet.getCell(5, i);  	            	
	            	show.setStatus(Integer.parseInt(cell.getContents())); 
	            	show.setModifyTime(new Date());	            	
	                slideShowServiceImp.update(show);
	                
	            }
	        	
	        }else{
		        for (int i = 2; i < rsRows; i++){   	            	
	            	SlideShow show = new SlideShow();
	            	cell = readsheet.getCell(0, i);  	            	
	            	show.setType(Integer.parseInt(cell.getContents()));    
	            	cell = readsheet.getCell(1,i);
	            	show.setScene(Integer.parseInt(cell.getContents()));
	            	
	            	cell = readsheet.getCell(2,i);
	            	show.setIcon(cell.getContents());
	            	
	            	cell = readsheet.getCell(3,i);
	            	show.setName(cell.getContents());
	            	
	            	show.setCreateTime(new Date());
	                slideShowServiceImp.save(show);
	                
	            }
	        }
		
		return "";
		
	}
	
	@RequestMapping(value="downshowFile")
	public void downshowFile(HttpServletResponse response,HttpServletRequest request){

		String[] title = {"id","type","scene","icon","name","status"}; 
		String[] beizhu = {"轮播组id","轮播组类型1-图片，2-字幕","场景 1首页，2最热3最新","icon图片，字幕类型添加","名称可以不填","状态"};  
		ServletContext application = request.getSession().getServletContext();
		String savePath = application.getRealPath("/") + "upload/";
		savePath +=  "file/";
    	File saveDirFile = new File(savePath);
    	if (!saveDirFile.exists()) {
    		saveDirFile.mkdirs();
    	}
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	String ymd = sdf.format(new Date());
    	savePath += ymd + "/";
    	File dirFile = new File(savePath);
    	if (!dirFile.exists()) {
    		dirFile.mkdirs();
    	}
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String newFileName = df.format(new Date()) + "_"
				+ new Random().nextInt(1000) + ".xls" ;
		List<SlideShow> list =  slideShowServiceImp.list();
		File file = new File(savePath+newFileName); //封装成File对象
		
		  WritableWorkbook wwb = null;   
          // 新建立一个jxl文件,即在d盘下生成testJXL.xls   
         OutputStream os;
		try {
			os = new FileOutputStream(file);
			wwb=Workbook.createWorkbook(os);
			WritableSheet sheet = wwb.createSheet("Root Sheet", 0);   
			Label label=null;   
            for(int i=0;i<title.length;i++){                 
                label = new Label(i,0,title[i]);                           
                sheet.addCell(label);   
            }
            for(int i=0;i<beizhu.length;i++){                 
                label = new Label(i,1,beizhu[i]);                           
                sheet.addCell(label);   
            }
            SlideShow show = null;
            for(int i=0;i<list.size();i++){
            	show = list.get(i);
            	
            	label =  new Label(0, i+2, String.valueOf(show.getId()));            	
            	sheet.addCell(label); 
            	
            	label =  new Label(1, i+2, String.valueOf(show.getType()));          	
            	sheet.addCell(label); 
            	
            	label =  new Label(2, i+2, String.valueOf(show.getScene()));          	
            	sheet.addCell(label);
            	
            	label =  new Label(3, i+2, show.getIcon());          	
            	sheet.addCell(label);
            	
            	label =  new Label(4, i+2, show.getName());          	
            	sheet.addCell(label);
            	label =  new Label(5, i+2, String.valueOf(show.getStatus()));          	
            	sheet.addCell(label);
            		
            }
            wwb.write();
            wwb.close();
            os.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
            
		
	  	String fileName = file.getName();  //得到文件名
  		int fileLen = (int)file.length();
  		
  		//设置响应头信息
		response.setContentType("application/x-msdownload");   
		String str = "attachment;filename=" + fileName;
		response.setHeader("Content-Disposition", str);  
		response.setContentLength(fileLen);
   
		//分别得到输入 输出流
		FileInputStream input = null;
				
		ServletOutputStream out = null;
		
		try {
			input = new FileInputStream(file);
		
			out = response.getOutputStream();
		   
			byte[] cache = new byte[1024*512];  //设置读写缓存512K
			int len = 0;
			while((len = input.read(cache)) > 0)
			{
				out.write(cache,0,len);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//关闭流
			try {
				input.close();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}	
	
	
	
	
	@RequestMapping("/picFile")
	@ResponseBody
	private String picFile(
			HttpServletRequest request) throws Exception, BiffException{
		ServletContext application = request.getSession().getServletContext();
		String savePath = application.getRealPath("/") + "upload/";
			
			InputStream instream = new FileInputStream(savefile(request,savePath));   			  
			Workbook  readwb = Workbook.getWorkbook(instream);
			Sheet readsheet = readwb.getSheet(0); 
			int rsColumns = readsheet.getColumns();   			  
	            //获取Sheet表中所包含的总行数   	  
	        int rsRows = readsheet.getRows();   	  
	            //获取指定单元格的对象引用   
	        Cell cell = null;
	        if(rsColumns>4){
	        	
	        	for (int i = 2; i < rsRows; i++){   	            	
	            	SlideShow show = new SlideShow();
	            	cell = readsheet.getCell(0, i); 
	            	int id = Integer.parseInt(cell.getContents());
	            	GamePic pic = gamePicServiceImp.get(id);
	            	if(pic!=null){
	            		
	            		cell = readsheet.getCell(1,i);
	            		int gameId = Integer.parseInt(cell.getContents());	
	            		cell = readsheet.getCell(2,i);
		            	pic.setPicUrl(cell.getContents());
		            	
		            	cell = readsheet.getCell(3,i);
		            	pic.setPicPath(cell.getContents());
		            	
		            	cell = readsheet.getCell(4,i);
		            	pic.setSort(Integer.parseInt(cell.getContents()));
		            	
		            	cell = readsheet.getCell(5,i);
		            	pic.setStatus(Integer.parseInt(cell.getContents()));
		         	   
		                gamePicServiceImp.update(pic, gameId);
		                
	            	}
	            	           	   
	                
	            }
	        	
	        }else{
		        for (int i = 2; i < rsRows; i++){   	            	
	            	GamePic pic = new GamePic();
	            	cell = readsheet.getCell(0, i); 
	            	int gameId = Integer.parseInt(cell.getContents());
	            	   
	            	cell = readsheet.getCell(1,i);
	            	pic.setPicUrl(cell.getContents());
	            	
	            	cell = readsheet.getCell(2,i);
	            	pic.setPicPath(cell.getContents());
	            	
	            	cell = readsheet.getCell(3,i);
	            	pic.setSort(Integer.parseInt(cell.getContents()));
	            
	                gamePicServiceImp.insert(pic, gameId);
	                
	            }
	        }
		
		
		return "";
		
	}
	
	@RequestMapping(value="downpicFile")
	public void downpicFile(HttpServletResponse response,HttpServletRequest request){

		String[] title = {"id","gameId","picUrl","picPath","sort","status"};
		String[] beizhu = {"图片id","游戏id","图片访问地址","物理地址","序号","状态"};   
		ServletContext application = request.getSession().getServletContext();
		String savePath = application.getRealPath("/") + "upload/";
		savePath +=  "file/";
    	File saveDirFile = new File(savePath);
    	if (!saveDirFile.exists()) {
    		saveDirFile.mkdirs();
    	}
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	String ymd = sdf.format(new Date());
    	savePath += ymd + "/";
    	File dirFile = new File(savePath);
    	if (!dirFile.exists()) {
    		dirFile.mkdirs();
    	}
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String newFileName = df.format(new Date()) + "_"
				+ new Random().nextInt(1000) + ".xls" ;
		List<GamePicTrans> list =  gamePicServiceImp.listResult();
		File file = new File(savePath+newFileName); //封装成File对象
		
		  WritableWorkbook wwb = null;   
          // 新建立一个jxl文件,即在d盘下生成testJXL.xls   
         OutputStream os;
		try {
			os = new FileOutputStream(file);
			wwb=Workbook.createWorkbook(os);
			WritableSheet sheet = wwb.createSheet("Root Sheet", 0);   
			Label label=null;   
            for(int i=0;i<title.length;i++){                 
                label = new Label(i,0,title[i]);                           
                sheet.addCell(label);   
            }
            
            for(int i=0;i<beizhu.length;i++){                 
                label = new Label(i,1,beizhu[i]);                           
                sheet.addCell(label);   
            }
            GamePicTrans pic = null;
            for(int i=0;i<list.size();i++){
            	pic = list.get(i);
            	
            	label =  new Label(0, i+2, String.valueOf(pic.getId()));            	
            	sheet.addCell(label); 
            	
            	label =  new Label(1, i+2, String.valueOf(pic.getGameId()));          	
            	sheet.addCell(label); 
            	
            	label =  new Label(2, i+2, pic.getPicUrl());          	
            	sheet.addCell(label);
            	
            	label =  new Label(3, i+2, pic.getPicPath());          	
            	sheet.addCell(label);
            	
            	label =  new Label(4, i+2, String.valueOf(pic.getSort()));          	
            	sheet.addCell(label);
            	
            	label =  new Label(5, i+2, String.valueOf(pic.getStatus()));          	
            	sheet.addCell(label);
            		
            }
            wwb.write();
            wwb.close();
            os.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
            
		
	  	String fileName = file.getName();  //得到文件名
  		int fileLen = (int)file.length();
  		
  		//设置响应头信息
		response.setContentType("application/x-msdownload");   
		String str = "attachment;filename=" + fileName;
		response.setHeader("Content-Disposition", str);  
		response.setContentLength(fileLen);
   
		//分别得到输入 输出流
		FileInputStream input = null;
				
		ServletOutputStream out = null;
		
		try {
			input = new FileInputStream(file);
		
			out = response.getOutputStream();
		   
			byte[] cache = new byte[1024*512];  //设置读写缓存512K
			int len = 0;
			while((len = input.read(cache)) > 0)
			{
				out.write(cache,0,len);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//关闭流
			try {
				input.close();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}	
	
	

	@RequestMapping("/gameTypeFile")
	@ResponseBody
	private String gameTypeFile( 
			HttpServletRequest request) throws Exception, BiffException{
		ServletContext application = request.getSession().getServletContext();
		String savePath = application.getRealPath("/") + "upload/";
					
			InputStream instream = new FileInputStream(savefile(request,savePath));   			  
			Workbook  readwb = Workbook.getWorkbook(instream);
			Sheet readsheet = readwb.getSheet(0); 
			int rsColumns = readsheet.getColumns();   			  
	            //获取Sheet表中所包含的总行数   	  
	        int rsRows = readsheet.getRows();   	  
	            //获取指定单元格的对象引用   
	        Cell cell = null;
	        if(rsColumns>2){
	        	
	        	for (int i = 2; i < rsRows; i++){   	            	
	            		            	           	
	            		cell = readsheet.getCell(0, i); 
		            	int gameId = Integer.parseInt(cell.getContents());
		            	   
		            	cell = readsheet.getCell(1, i); 
		            	
		            	int gameTypeId = Integer.parseInt(cell.getContents());
		            	
		            	cell = readsheet.getCell(2,i);
		            	int status = Integer.parseInt(cell.getContents());
		            	GameTypeTrans trans =  new GameTypeTrans(gameId,gameTypeId,status);	 
		         	   
		               gameTypeRefServiceImp.update(trans);
		                	            	
	            	           	   
	                
	            }
	        	
	        }else{
		        for (int i = 2; i < rsRows; i++){   	            	
	            	cell = readsheet.getCell(0, i); 
	            	int gameId = Integer.parseInt(cell.getContents());
	            	   
	            	cell = readsheet.getCell(1, i); 
	            	
	            	int gameTypeId = Integer.parseInt(cell.getContents());
	            
	                gameTypeRefServiceImp.insert(gameId, gameTypeId);
	                
	            }
	        }
		
		return "";
		
	}
	
	@RequestMapping(value="downgameTypeFile")
	public void downgameTypeFile(HttpServletResponse response,HttpServletRequest request){

		String[] title = {"gameId","gameTypeId","status"};   
		String[] beizhu = {"游戏id","分类id","状态"}; 
		ServletContext application = request.getSession().getServletContext();
		String savePath = application.getRealPath("/") + "upload/";
		savePath +=  "file/";
    	File saveDirFile = new File(savePath);
    	if (!saveDirFile.exists()) {
    		saveDirFile.mkdirs();
    	}
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	String ymd = sdf.format(new Date());
    	savePath += ymd + "/";
    	File dirFile = new File(savePath);
    	if (!dirFile.exists()) {
    		dirFile.mkdirs();
    	}
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String newFileName = df.format(new Date()) + "_"
				+ new Random().nextInt(1000) + ".xls" ;
		List<GameTypeTrans> list =  gameTypeRefServiceImp.listResult();
		File file = new File(savePath+newFileName); //封装成File对象
		
		  WritableWorkbook wwb = null;   
          // 新建立一个jxl文件,即在d盘下生成testJXL.xls   
         OutputStream os;
		try {
			os = new FileOutputStream(file);
			wwb=Workbook.createWorkbook(os);
			WritableSheet sheet = wwb.createSheet("Root Sheet", 0);   
			Label label=null;   
            for(int i=0;i<title.length;i++){                 
                label = new Label(i,0,title[i]);                           
                sheet.addCell(label);   
            }
            for(int i=0;i<beizhu.length;i++){                 
                label = new Label(i,1,beizhu[i]);                           
                sheet.addCell(label);   
            }
            GameTypeTrans gtype = null;
            for(int i=0;i<list.size();i++){
            	gtype = list.get(i);
            	
            	label =  new Label(0, i+2, String.valueOf(gtype.getGameId()));            	
            	sheet.addCell(label); 
            	
            	label =  new Label(1, i+2, String.valueOf(gtype.getGameTypeId()));          	
            	sheet.addCell(label); 
            	
          
            	
            	label =  new Label(2, i+2, String.valueOf(gtype.getStatus()));          	
            	sheet.addCell(label);
            		
            }
            wwb.write();
            wwb.close();
            os.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
            
		
	  	String fileName = file.getName();  //得到文件名
  		int fileLen = (int)file.length();
  		
  		//设置响应头信息
		response.setContentType("application/x-msdownload");   
		String str = "attachment;filename=" + fileName;
		response.setHeader("Content-Disposition", str);  
		response.setContentLength(fileLen);
   
		//分别得到输入 输出流
		FileInputStream input = null;
				
		ServletOutputStream out = null;
		
		try {
			input = new FileInputStream(file);
		
			out = response.getOutputStream();
		   
			byte[] cache = new byte[1024*512];  //设置读写缓存512K
			int len = 0;
			while((len = input.read(cache)) > 0)
			{
				out.write(cache,0,len);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//关闭流
			try {
				input.close();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}	
	
	

	@RequestMapping("/gameCommentFile")
	@ResponseBody
	private String gameCommentFile(
			HttpServletRequest request) throws Exception, BiffException{
		ServletContext application = request.getSession().getServletContext();
		String savePath = application.getRealPath("/") + "upload/";
				
			InputStream instream = new FileInputStream(savefile(request,savePath));   			  
			Workbook  readwb = Workbook.getWorkbook(instream);
			Sheet readsheet = readwb.getSheet(0); 
			int rsColumns = readsheet.getColumns();   			  
	            //获取Sheet表中所包含的总行数   	  
	        int rsRows = readsheet.getRows();   	  
	            //获取指定单元格的对象引用   
	        Cell cell = null;
	        if(rsColumns>3){
	        	
	        	for (int i = 2; i < rsRows; i++){   	            	
	            		            	           	
	            		cell = readsheet.getCell(0, i); 
		            	int gameId = Integer.parseInt(cell.getContents());
		            	   
		            	cell = readsheet.getCell(1, i); 
		            	
		            	int commentId = Integer.parseInt(cell.getContents());
		            	
		            	cell = readsheet.getCell(2, i); 
		            	int count = Integer.parseInt(cell.getContents());
		            	
		            	cell = readsheet.getCell(3,i);
		            	int status = Integer.parseInt(cell.getContents());
		            	GameCommentTrans trans =  new GameCommentTrans(gameId, commentId, count, status);
		            		           	
		            	gameCommentRefServiceImp.update(trans);
		                	            	            	           	   
	                
	            }
	        	
	        }else{
		        for (int i = 2; i < rsRows; i++){   	            	
		        	cell = readsheet.getCell(0, i); 
	            	int gameId = Integer.parseInt(cell.getContents());
	            	   
	            	cell = readsheet.getCell(1, i); 
	            	
	            	int commentId = Integer.parseInt(cell.getContents());
	            	
	            	cell = readsheet.getCell(2, i); 
	            	int count = Integer.parseInt(cell.getContents());
	            	
	            	GameCommentTrans trans =  new GameCommentTrans(gameId, commentId, count);
	            	gameCommentRefServiceImp.insert(trans);
	                
	            }
	        }
				
		return "";
		
	}
	
	@RequestMapping(value="downgameCommentFile")
	public void downgameCommentFile(HttpServletResponse response,HttpServletRequest request){

		String[] title = {"gameId","commentId","count","status"};   
		String[] beizhu = {"游戏id","评价id","数量","状态"};   
		ServletContext application = request.getSession().getServletContext();
		String savePath = application.getRealPath("/") + "upload/";
		savePath +=  "file/";
    	File saveDirFile = new File(savePath);
    	if (!saveDirFile.exists()) {
    		saveDirFile.mkdirs();
    	}
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	String ymd = sdf.format(new Date());
    	savePath += ymd + "/";
    	File dirFile = new File(savePath);
    	if (!dirFile.exists()) {
    		dirFile.mkdirs();
    	}
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String newFileName = df.format(new Date()) + "_"
				+ new Random().nextInt(1000) + ".xls" ;
		List<GameCommentTrans> list =  gameCommentRefServiceImp.listResult();
		File file = new File(savePath+newFileName); //封装成File对象
		
		  WritableWorkbook wwb = null;   
          // 新建立一个jxl文件,即在d盘下生成testJXL.xls   
         OutputStream os;
		try {
			os = new FileOutputStream(file);
			wwb=Workbook.createWorkbook(os);
			WritableSheet sheet = wwb.createSheet("Root Sheet", 0);   
			Label label=null;   
            for(int i=0;i<title.length;i++){                 
                label = new Label(i,0,title[i]);                           
                sheet.addCell(label);   
            }
            for(int i=0;i<title.length;i++){                 
                label = new Label(i,1,beizhu[i]);                           
                sheet.addCell(label);   
            }
            GameCommentTrans gcment = null;
            for(int i=0;i<list.size();i++){
            	gcment = list.get(i);
            	
            	label =  new Label(0, i+2, String.valueOf(gcment.getGameId()));            	
            	sheet.addCell(label); 
            	
            	label =  new Label(1, i+2, String.valueOf(gcment.getCommentId()));          	
            	sheet.addCell(label); 
            	
            	label =  new Label(2, i+2, String.valueOf(gcment.getCount()));          	
            	sheet.addCell(label);
            	
            	label =  new Label(3, i+2, String.valueOf(gcment.getStatus()));          	
            	sheet.addCell(label);
            		
            }
            wwb.write();
            wwb.close();
            os.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
            
		
	  	String fileName = file.getName();  //得到文件名
  		int fileLen = (int)file.length();
  		
  		//设置响应头信息
		response.setContentType("application/x-msdownload");   
		String str = "attachment;filename=" + fileName;
		response.setHeader("Content-Disposition", str);  
		response.setContentLength(fileLen);
   
		//分别得到输入 输出流
		FileInputStream input = null;
				
		ServletOutputStream out = null;
		
		try {
			input = new FileInputStream(file);
		
			out = response.getOutputStream();
		   
			byte[] cache = new byte[1024*512];  //设置读写缓存512K
			int len = 0;
			while((len = input.read(cache)) > 0)
			{
				out.write(cache,0,len);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//关闭流
			try {
				input.close();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}	
	
	
	@RequestMapping("/typeTagFile")
	@ResponseBody
	private String typeTagFile(
			HttpServletRequest request) throws Exception, BiffException{
		ServletContext application = request.getSession().getServletContext();
		String savePath = application.getRealPath("/") + "upload/";
				
			InputStream instream = new FileInputStream(savefile(request,savePath));   			  
			Workbook  readwb = Workbook.getWorkbook(instream);
			Sheet readsheet = readwb.getSheet(0); 
			int rsColumns = readsheet.getColumns();   			  
	            //获取Sheet表中所包含的总行数   	  
	        int rsRows = readsheet.getRows();   	  
	            //获取指定单元格的对象引用   
	        Cell cell = null;
	        if(rsColumns>2){
	        	
	        	for (int i = 2; i < rsRows; i++){   	            	
	            		            	           	
	            		cell = readsheet.getCell(0, i); 
		            	int gameTypeId = Integer.parseInt(cell.getContents());
		            	   
		            	cell = readsheet.getCell(1, i); 
		            	
		            	int tagId = Integer.parseInt(cell.getContents());
		            	
cell = 					readsheet.getCell(2, i); 
		            	
		            	int status = Integer.parseInt(cell.getContents());
		            			            	
		            	TypeTagTrans trans =  new TypeTagTrans(gameTypeId, tagId,status);
		            		           	
		            	typeTagServiceImp.update(trans);
		                	            	            	           	   
	                
	            }
	        	
	        }else{
		        for (int i = 2; i < rsRows; i++){   	            	
		        	cell = readsheet.getCell(0, i); 
	            	int gameTypeId = Integer.parseInt(cell.getContents());
	            	   
	            	cell = readsheet.getCell(1, i); 
	            	
	            	int tagId = Integer.parseInt(cell.getContents());
	                  	
	            	TypeTagTrans trans =  new TypeTagTrans(gameTypeId, tagId);
	            	typeTagServiceImp.insert(trans);
	            	
	                
	            }
	        }
		
		
		return "";
		
	}
	
	@RequestMapping(value="downtypeTagFile")
	public void downtypeTagFile(HttpServletResponse response,HttpServletRequest request){

		String[] title = {"gameTypeId","tagId","status"};   
		String[] beizhu = {"分类id","词条id","状态"}; 
		ServletContext application = request.getSession().getServletContext();
		String savePath = application.getRealPath("/") + "upload/";
		savePath +=  "file/";
    	File saveDirFile = new File(savePath);
    	if (!saveDirFile.exists()) {
    		saveDirFile.mkdirs();
    	}
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	String ymd = sdf.format(new Date());
    	savePath += ymd + "/";
    	File dirFile = new File(savePath);
    	if (!dirFile.exists()) {
    		dirFile.mkdirs();
    	}
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String newFileName = df.format(new Date()) + "_"
				+ new Random().nextInt(1000) + ".xls" ;
		List<TypeTagTrans> list =  typeTagServiceImp.listResult();
		File file = new File(savePath+newFileName); //封装成File对象
		
		  WritableWorkbook wwb = null;   
          // 新建立一个jxl文件,即在d盘下生成testJXL.xls   
         OutputStream os;
		try {
			os = new FileOutputStream(file);
			wwb=Workbook.createWorkbook(os);
			WritableSheet sheet = wwb.createSheet("Root Sheet", 0);   
			Label label=null;   
            for(int i=0;i<title.length;i++){                 
                label = new Label(i,0,title[i]);                           
                sheet.addCell(label);   
            }
            for(int i=0;i<beizhu.length;i++){                 
                label = new Label(i,1,beizhu[i]);                           
                sheet.addCell(label);   
            }
            TypeTagTrans ttag = null;
            for(int i=0;i<list.size();i++){
            	
            	ttag = list.get(i);
            	
            	label =  new Label(0, i+2, String.valueOf(ttag.getGameTypeId()));            	
            	sheet.addCell(label); 
            	
            	label =  new Label(1, i+2, String.valueOf(ttag.getTagId()));          	
            	sheet.addCell(label); 
            	            	            	
            	label =  new Label(2, i+2, String.valueOf(ttag.getStatus()));          	
            	sheet.addCell(label);
            		
            }
            wwb.write();
            wwb.close();
            os.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
            
		
	  	String fileName = file.getName();  //得到文件名
  		int fileLen = (int)file.length();
  		
  		//设置响应头信息
		response.setContentType("application/x-msdownload");   
		String str = "attachment;filename=" + fileName;
		response.setHeader("Content-Disposition", str);  
		response.setContentLength(fileLen);
   
		//分别得到输入 输出流
		FileInputStream input = null;
				
		ServletOutputStream out = null;
		
		try {
			input = new FileInputStream(file);
		
			out = response.getOutputStream();
		   
			byte[] cache = new byte[1024*512];  //设置读写缓存512K
			int len = 0;
			while((len = input.read(cache)) > 0)
			{
				out.write(cache,0,len);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//关闭流
			try {
				input.close();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}	


	@RequestMapping("/slideShowFile")
	@ResponseBody
	private String slideShowFile(
			HttpServletRequest request) throws Exception, BiffException{
		ServletContext application = request.getSession().getServletContext();
		String savePath = application.getRealPath("/") + "upload/";
				
			InputStream instream = new FileInputStream(savefile(request,savePath));   			  
			Workbook  readwb = Workbook.getWorkbook(instream);
			Sheet readsheet = readwb.getSheet(0); 
			int rsColumns = readsheet.getColumns();   			  
	            //获取Sheet表中所包含的总行数   	  
	        int rsRows = readsheet.getRows();   	  
	            //获取指定单元格的对象引用   
	        Cell cell = null;
	        if(rsColumns>2){
	        	
	        	for (int i = 2; i < rsRows; i++){   	            	
	            		            	           	
	            		cell = readsheet.getCell(0, i); 
		            	int slideId = Integer.parseInt(cell.getContents());
		            	   
		            	cell = readsheet.getCell(1, i); 
		            	
		            	int slideShowId = Integer.parseInt(cell.getContents());
		            	
cell = 					readsheet.getCell(2, i); 
		            	
		            	int status = Integer.parseInt(cell.getContents());
		            			            	
		            	SlideShowTrans trans =  new SlideShowTrans(slideId, slideShowId,status);
		            		           	
		            	slideRefServiceImp.update(trans);
		                	            	            	           	   
	                
	            }
	        	
	        }else{
		        for (int i = 2; i < rsRows; i++){   	            	
		        	cell = readsheet.getCell(0, i); 
	            	int slideId = Integer.parseInt(cell.getContents());
	            	   
	            	cell = readsheet.getCell(1, i); 
	            	
	            	int slideShowId = Integer.parseInt(cell.getContents());
	                  	
	            	SlideShowTrans trans =  new SlideShowTrans(slideId, slideShowId);
	            	slideRefServiceImp.insert(trans);
	            	
	                
	            }
	        }
		
		return "";
		
	}
	
	@RequestMapping(value="downslideShowFile")
	public void downslideShowFile(HttpServletResponse response,HttpServletRequest request){

		String[] title = {"slideId","slideShowId","status"};   
		String[] beizhu = {"轮播子项id","轮播组id","状态 ,备注：轮播子项和轮播组的类型必须一致"};   
		ServletContext application = request.getSession().getServletContext();
		String savePath = application.getRealPath("/") + "upload/";
		savePath +=  "file/";
    	File saveDirFile = new File(savePath);
    	if (!saveDirFile.exists()) {
    		saveDirFile.mkdirs();
    	}
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	String ymd = sdf.format(new Date());
    	savePath += ymd + "/";
    	File dirFile = new File(savePath);
    	if (!dirFile.exists()) {
    		dirFile.mkdirs();
    	}
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String newFileName = df.format(new Date()) + "_"
				+ new Random().nextInt(1000) + ".xls" ;
		List<SlideShowTrans> list =  slideRefServiceImp.listResult();
		File file = new File(savePath+newFileName); //封装成File对象
		
		  WritableWorkbook wwb = null;   
          // 新建立一个jxl文件,即在d盘下生成testJXL.xls   
         OutputStream os;
		try {
			os = new FileOutputStream(file);
			wwb=Workbook.createWorkbook(os);
			WritableSheet sheet = wwb.createSheet("Root Sheet", 0);   
			Label label=null;   
            for(int i=0;i<title.length;i++){                 
                label = new Label(i,0,title[i]);                           
                sheet.addCell(label);   
            }
            for(int i=0;i<beizhu.length;i++){                 
                label = new Label(i,1,beizhu[i]);                           
                sheet.addCell(label);   
            }
            SlideShowTrans sshow = null;
            for(int i=0;i<list.size();i++){
            	
            	sshow = list.get(i);
            	
            	label =  new Label(0, i+2, String.valueOf(sshow.getSlideId()));            	
            	sheet.addCell(label); 
            	
            	label =  new Label(1, i+2, String.valueOf(sshow.getSlideShowId()));          	
            	sheet.addCell(label); 
            	            	            	
            	label =  new Label(2, i+2, String.valueOf(sshow.getStatus()));          	
            	sheet.addCell(label);
            		
            }
            wwb.write();
            wwb.close();
            os.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
            
		
	  	String fileName = file.getName();  //得到文件名
  		int fileLen = (int)file.length();
  		
  		//设置响应头信息
		response.setContentType("application/x-msdownload");   
		String str = "attachment;filename=" + fileName;
		response.setHeader("Content-Disposition", str);  
		response.setContentLength(fileLen);
   
		//分别得到输入 输出流
		FileInputStream input = null;
				
		ServletOutputStream out = null;
		
		try {
			input = new FileInputStream(file);
		
			out = response.getOutputStream();
		   
			byte[] cache = new byte[1024*512];  //设置读写缓存512K
			int len = 0;
			while((len = input.read(cache)) > 0)
			{
				out.write(cache,0,len);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//关闭流
			try {
				input.close();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}	
	
	private String savefile(HttpServletRequest request,String savePath) throws Exception{
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		List items = upload.parseRequest(request);
		Iterator itr = items.iterator();
		while (itr.hasNext()) {
			FileItem item = (FileItem) itr.next();
			String fileName = item.getName();
			if (!item.isFormField()) {
				return copyFile(item.getInputStream(), fileName, savePath);
			}
		}
		return null;
	}
	
	private String copyFile(InputStream inputStream, String fileName,String tempdir) throws IOException {  
        OutputStream outputStream = null;  
        String tempFileName = null; 
        tempdir +=  "file/";
    	File saveDirFile = new File(tempdir);
    	if (!saveDirFile.exists()) {
    		saveDirFile.mkdirs();
    	}
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	String ymd = sdf.format(new Date());
    	tempdir += ymd + "/";
    	File dirFile = new File(tempdir);
    	if (!dirFile.exists()) {
    		dirFile.mkdirs();
    	}
        int pointPosition = fileName.lastIndexOf(".");  
        if (pointPosition < 0) {// myvedio  
            tempFileName = UUID.randomUUID().toString();// 94d1d2e0-9aad-4dd8-a0f6-494b0099ff26  
        } else {// myvedio.flv  
            tempFileName = UUID.randomUUID() + fileName.substring(pointPosition);// 94d1d2e0-9aad-4dd8-a0f6-494b0099ff26.flv  
        }  
        try {  
            outputStream = new FileOutputStream(tempdir + tempFileName);  
            int readBytes = 0;  
            byte[] buffer = new byte[10000];  
            while ((readBytes = inputStream.read(buffer, 0, 10000)) != -1) {  
                outputStream.write(buffer, 0, readBytes);  
            }  
            return tempdir + tempFileName;  
        } catch (IOException ioe) {  
            // log.error(ioe.getMessage(), ioe);  
            throw new IOException("上传文件不存在");// 上传文件不存在  
        } finally {  
            if (outputStream != null) {  
                try {  
                    outputStream.close();  
                } catch (IOException e) {  
                }  
            }  
            if (inputStream != null) {  
                try {  
                    inputStream.close();  
                } catch (IOException e) {  
                }  
            }  
  
        }  
  
    }  

}
