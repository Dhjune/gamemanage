package com.mdream.gamemanage.model.qrcode;

public enum QrcodeColor{
	WHITE(0xFFFFFFFF),BLACK(0xff000000),BLUE(0xff3299CC),YELLOW_GREEN(0xffB3D52B),GREEN(0xFF66FF66),
	CYAN(0xFF66FFE0),VIOLET(0xFFA366FF),YELLOW(0xFFFFFF66),ORANGE(0xFFFFE066),RED(0xFFFA886B);
	private int value = 0;
	
	private QrcodeColor(int value) {    //    必须是private的，否则编译错误
        this.value = value;
    }
	
	 public static QrcodeColor valueOf(int value) {    //    手写的从int到enum的转换函数
	        switch (value) {
	        case 0xFFFFFFFF:
	            return WHITE;
	        case 0xff000000:
	            return BLACK;
	        case 0xff3299CC:
	            return BLUE;
	        case 0xffB3D52B:
	            return YELLOW_GREEN;
	        case 0xFF66FF66:
	            return GREEN;
	        case 0xFF66FFE0:
	            return CYAN;
	        case 0xFFA366FF:
	            return VIOLET;
	        case 0xFFFFFF66:
	            return YELLOW;
	        case 0xFFFFE066:
	            return ORANGE;
	        case 0xFFFA886B:
	            return RED;
	        default:
	            return null;
	        }
	    }

	    public int value() {
	        return this.value;
	    }
	
}
