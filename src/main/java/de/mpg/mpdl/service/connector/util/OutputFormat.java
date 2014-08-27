package de.mpg.mpdl.service.connector.util;

public enum OutputFormat {
	PNG("png"), 
	JPEG("jpg"), 
	GIT("gif"), 
	TIFF("tiff"), 
	SVG("svg"), 
	BMP("bmp");
	
	private String format;

	private OutputFormat(String format) {
		this.format = format;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}


}
