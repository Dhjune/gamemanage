package com.mdream.gamemanage.common.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Component;

import com.mdream.gamemanage.common.api.BaseDaoItl;
import com.mdream.gamemanage.common.api.BaseServiceItl;
import com.mdream.gamemanage.model.game.Game;

@Component
public class JxlResolveImp implements JxlResolve{
	
	@Override
	public void filling(String file,int start,Class clazz,BaseServiceItl service) throws Exception{
		
		InputStream instream = new FileInputStream(file);   			  
		Workbook  readwb = Workbook.getWorkbook(instream);
		Sheet readsheet = readwb.getSheet(0);
		
		int rsColumns = readsheet.getColumns();  
		
        int rsRows = readsheet.getRows();   	  
          
        Cell cell = null;
        Object obj = null;
        String str;
        Method[] methods  =  new Method[rsColumns];
        Field[]  fields =  new Field[rsColumns];
        for(int i=0;i<rsColumns;i++){
        	str =  readsheet.getCell(i, 0).getContents(); 
        	Field field =  clazz.getDeclaredField(str);        	
        	str = "set"+str.replaceFirst(str.substring(0, 1), str.substring(0, 1).toUpperCase());       	
        	methods[i] = clazz.getMethod(str, field.getType());
        	fields[i] = field;
        }
        
        
        for (; start < rsRows; start++){
        	obj = clazz.newInstance();
        	for(int clu=0;clu<rsColumns;clu++){
        		
        		methods[clu].invoke(obj, getField(fields[clu],readsheet.getCell(clu,start).getContents()));
        		  
        	}
        	service.saveOrUpdate(obj);
        	
        }
		
	}
	
	public void reading(String[][] titles,String filepath, List list,Class clazz,HttpServletResponse response) throws Exception{
			
			
			File file = new File(filepath); 
			
			WritableWorkbook wwb = null;   
			
	        OutputStream os;
			try {
				os = new FileOutputStream(file);
				wwb=Workbook.createWorkbook(os);
				WritableSheet sheet = wwb.createSheet("Root Sheet", 0);   
				Label label=null;
				String fieldStr=null;
				int fcolumns = titles[0].length;
				Method[] methods  =  new Method[fcolumns];
			    Field[]  fields =  new Field[fcolumns];
			    String methodStr = "";
	            for(int rows=0;rows<titles.length;rows++){
	            	String [] temp = titles[rows];
	            	for(int columns=0;columns<temp.length;columns++){
	            		if(rows==0){
	            			fieldStr = temp[columns];
	            			label =  new Label(columns, rows, fieldStr);
	            			sheet.addCell(label);
	            			//fieldStr =  sheet.getCell(columns, rows).getContents(); 
	                    	Field field =  clazz.getDeclaredField(fieldStr);        	
	                    	methodStr = "get"+fieldStr.replaceFirst(fieldStr.substring(0, 1), fieldStr.substring(0, 1).toUpperCase());       	
	                    	methods[columns] = clazz.getMethod(methodStr, null);
	                    	fields[columns] = field;
	            		}else{
	            			fieldStr = temp[columns];
	            			label =  new Label(columns, rows, fieldStr);
	            			sheet.addCell(label);
	            		}
	            	}
	            }
	            Object obj = null;
	            for(int i=0;i<list.size();i++){
	            	obj = list.get(i);
	            	for(int j =0;j<fcolumns;j++){
	            		
	            		label =  new Label(j, i+2, String.valueOf(methods[j].invoke(obj, null)));
		            	sheet.addCell(label); 
	            	}
	            	
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
	
	public void dofill(){
		
	}
	
	
	public void doread(){
		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
    public <T> T getField( Field field,String value) throws NoSuchFieldException, SecurityException, ParseException{
		
		Class type = field.getType();
	
		if(type.getName().equals("java.lang.String")) { 			
			return (T) value; 
		}
				
		else if(type.equals("java.lang.Boolean")) { 
			Boolean bool=true; 
			
			if(value.equals("false")) { 
				bool=false; 
			} 
			return (T) bool; 
		} 
				
		else if(type.getName().equals("int")||type.getName().equals("java.lang.Integer")) {
			
			return (T) new Integer(value);
			
		} 
		
		else if(type.getName().equals("float")||type.getName().equals("java.lang.Float")) {
			
			return (T) new Float(value);
			
		} 

		else if(type.getName().equals("long")||type.getName().equals("class java.lang.Long")) { 
			
			return (T) new Long(value);
						
		} 
		
		else if(type.getName().equals("double")||type.getName().equals("class java.lang.Double")) { 
			
			return (T) new Long(value);
			
		} 
		
		return (T) value;
	}
	
	
}
