package com.mdream.gamemanage.model.qrcode;


import java.awt.Color;

public class LogoImageConfig {
		//logo默认边框颜色
		public static final Color DEFAULT_BORDERCOLOR = new Color(1.0F, 0.75F, 0.0F, 0.45F);
		//logo默认边框宽度
		public static final int DEFAULT_BORDER = 0;
		//logo大小默认为照片的1/5
		public static final int DEFAULT_LOGOPART = 5;

		
		private final int border = DEFAULT_BORDER;
		private final Color borderColor;
		private final int logoPart;
		
		private String logoPath;
		private String logoUrl ;
		
		
		/**
		 * Creates a default config with on color {@link #BLACK} and off color
		 * {@link #WHITE}, generating normal black-on-white barcodes.
		 */
		public LogoImageConfig() {
			this(DEFAULT_BORDERCOLOR, DEFAULT_LOGOPART);
		}

		
		public LogoImageConfig(Color borderColor, int logoPart) {
			this.borderColor = borderColor;
			this.logoPart = logoPart;
		}


		public Color getBorderColor() {
			return borderColor;
		}


		public int getBorder() {
			return border;
		}


		public int getLogoPart() {
			return logoPart;
		}


		public String getLogoUrl() {
			return logoUrl;
		}


		public void setLogoUrl(String logoUrl) {
			this.logoUrl = logoUrl;
		}


		public String getLogoPath() {
			return logoPath;
		}


		public void setLogoPath(String logoPath) {
			this.logoPath = logoPath;
		}
}
