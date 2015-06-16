package com.mdream.gamemanage.service.base;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;






import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.mdream.gamemanage.common.tools.Constans;
import com.mdream.gamemanage.model.qrcode.LogoImageConfig;
import com.mdream.gamemanage.model.qrcode.QrCodeImageConfig;


@Service
public class QrCodeServiceImp {
				
	public String initQrCode(String imagePath,String imageUrl,QrCodeImageConfig qrCodeConfig) {
		try {
        // BitMatrix bitMatrix = new MultiFormatWriter().encode(qrCodeConfig.getContent(), BarcodeFormat.QR_CODE, qrCodeConfig.getWidth(), qrCodeConfig.getHeight(),qrCodeConfig.getHints());
		BitMatrix bitMatrix = new MultiFormatWriter().encode(new String(qrCodeConfig.getContent().getBytes("UTF-8"), "ISO-8859-1")
		, BarcodeFormat.QR_CODE, qrCodeConfig.getWidth(), qrCodeConfig.getHeight(),qrCodeConfig.getHints());
            
		File file = new File(imagePath);
            writeToFile(bitMatrix, qrCodeConfig.getFormat(), file,qrCodeConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
		return imageUrl;	
	}
	
	//生成带logo
	public void overlapImage(String imagePath, String logoPath,LogoImageConfig logoConfig,QrCodeImageConfig qrCodeConfig){
		try{
			BufferedImage image = ImageIO.read(new File(imagePath));	
			BufferedImage logo = ImageIO.read(new File(logoPath));
			Graphics2D g = image.createGraphics();
			//考虑到logo照片贴到二维码中，建议大小不要超过二维码的1/5;
			int width = image.getWidth() / logoConfig.getLogoPart();
			int height = image.getHeight() / logoConfig.getLogoPart();
			//logo起始位置，此目的是为logo居中显示
			int x = (image.getWidth() - width) / 2;
			int y = (image.getHeight() - height) / 2;
			//绘制图
			g.drawImage(logo, x, y, width, height, null);
			
			//给logo画边框
			//构造一个具有指定线条宽度以及 cap 和 join 风格的默认值的实心 BasicStroke
			g.setStroke(new BasicStroke(logoConfig.getBorder()));
			g.setColor(logoConfig.getBorderColor());
			g.drawRect(x, y, width, height);				
			g.dispose();
			//写入logo照片到二维码
			ImageIO.write(image, qrCodeConfig.getFormat(), new File(imagePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
			//BufferedImage image, String formate, 
	}
	
	 public void writeToFile(BitMatrix matrix, String format, File file,QrCodeImageConfig qrCodeConfig) throws IOException {
         BufferedImage image = toBufferedImage(matrix,qrCodeConfig);
         ImageIO.write(image,format, file);
     }
 
     /**
      * 生成二维码内容<br>
      * 
      * @param matrix
      * @return
      */
     public  BufferedImage toBufferedImage(BitMatrix matrix,QrCodeImageConfig qrCodeConfig) {
         int width = matrix.getWidth();
         int height = matrix.getHeight();
         BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
         for (int x = 0; x < width; x++) {
             for (int y = 0; y < height; y++) {
                 image.setRGB(x, y, matrix.get(x, y) == true ? qrCodeConfig.getTo_color() : qrCodeConfig.getBackground_color());
             }
         }
         return image;
     }
     
	public void analysisQrCode() {
				
	}
	
	public HashMap initImagePath(HttpServletRequest res){
		
		
		String savePath =Constans.IMG_BASE_SAVE_PATH;
		String saveUrl = Constans.IMG_BASE_SAVE_URL;
		File saveDirFile = new File(savePath);
		if (!saveDirFile.exists()) {
			saveDirFile.mkdirs();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String ymd = sdf.format(new Date());
		savePath += ymd + "/";
		saveUrl += ymd + "/";
		File dirFile = new File(savePath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String newFileName = df.format(new Date()) + "_"
				+ new Random().nextInt(1000) + "." + "png";		
		savePath +=newFileName; 
		saveUrl +=newFileName;				
		HashMap map = new HashMap();	
		map.put("imageUrl", saveUrl);
		map.put("imagePath", savePath);
		
		return map;
	}

	public String initQrCode(String imagePath, String imageUrl) {
		
		return null;
	}
	

}
