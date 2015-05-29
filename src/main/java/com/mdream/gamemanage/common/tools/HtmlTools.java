package com.mdream.gamemanage.common.tools;

import java.io.BufferedWriter;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

@Component
public class HtmlTools implements ApplicationContextAware{
	
	private  ApplicationContext applicationContext;
	
	private VelocityConfigurer configur;
	/**
	 * 
	 * @Title: initHtmlFile 
	 * @Description: TODO
	 * @param temp  模板路径
	 * @param save  文件保存路径 
	 * @param map  填充到模板中的数据，
	 * @return boolean html静态化是否成功。 
	 */
	public boolean initHtmlFile(String temp ,String save,Map map){
					
		VelocityConfigurer vc =  applicationContext.getBean(VelocityConfigurer.class);		
		VelocityEngine ve =  vc.getVelocityEngine();
		VelocityContext context = new VelocityContext(map); 
		Template template = ve.getTemplate(temp);
		File savefile = new File(save);   
		if(!savefile.getParentFile().exists()) savefile.getParentFile().mkdirs();   
		FileOutputStream outstream =  null;   
		OutputStreamWriter writer = null;
		BufferedWriter bufferWriter = null;
		
		/* 计划使用velocity 包装hibernate hql ，sql语句
		 * 
		 * ve.evaluate(context, bufferWriter, "", "fgae $!{name}");
		ve.evaluate(context, bufferWriter, "",new Reader() {
			
			@Override
			public int read(char[] cbuf, int off, int len) throws IOException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public void close() throws IOException {
				// TODO Auto-generated method stub
				
			}
		});
		*/
		try{
					
			outstream =  new FileOutputStream(savefile);		
			writer  = new OutputStreamWriter(outstream,"UTF-8");   		   	
			bufferWriter = new BufferedWriter(writer);   
			
			template.merge(context, bufferWriter);   
			bufferWriter.flush();   
			outstream.close();
			bufferWriter.close();
			return true;
		}catch(Exception e){
			 try {
				outstream.close();
				bufferWriter.close(); 
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}   			
			return false;
		}
						
	}
	
	public String initHqlOrSql(String sql,Map map){
		
		VelocityEngine ve =  configur.getVelocityEngine();
		VelocityContext context = new VelocityContext(map);
		CharArrayWriter writer = new CharArrayWriter();
		ve.evaluate(context, writer, "", sql);
		
		return writer.toString();
		
	}
	
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
		ToolsFactory.htmlTools = this;
		this.configur =  applicationContext.getBean(VelocityConfigurer.class);
	}

	public VelocityConfigurer getConfigur() {
		return configur;
	}

	public void setConfigur(VelocityConfigurer configur) {
		this.configur = configur;
	}
	
	
	
}
