package com.mdream.gamemanage.common.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Component;

@Component
public class SyncHttpWork {
	public String savefile(HttpServletRequest request,String savePath) throws Exception{
		
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
	
	public String copyFile(InputStream inputStream, String fileName,String tempdir) throws IOException {  
        OutputStream outputStream = null;  
        String tempFileName = null; 
      //  tempdir +=  "file/";
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
	
	public String createFile(String fileExt,String tempdir){
	
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
    
    	SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String newFileName = df.format(new Date()) + "_"
				+ new Random().nextInt(1000) + fileExt;
        return  tempdir + newFileName;  
	}
}
