package com.mdream.gamemanage.model.qrcode;



public class ObtainColor {

	public int getRgbColor(String color){
			
		for(QrcodeColor s : QrcodeColor.values()) {   	         
	        if(s.toString().equals(color)){
	        	return s.value();
	        }
	    }
		return 0;
	}
}
