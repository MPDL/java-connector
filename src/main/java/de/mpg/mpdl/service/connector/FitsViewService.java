package de.mpg.mpdl.service.connector;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import de.mpg.mpdl.service.connector.util.PropertyReader;

public class FitsViewService extends RestClient{

	private static final String mpdlServiceTarget = PropertyReader.getProperty("fits.view.targetURL");

	String getServiceTargetURL(String url) {
		if("".equalsIgnoreCase(url))
			url = mpdlServiceTarget;
		return url;
	}
	
	public File generateFromFile(String serviceTargetURL, File f) throws IOException, URISyntaxException{
		serviceTargetURL = getServiceTargetURL(serviceTargetURL);
		File respFile = File.createTempFile("fits_view", ".html");		
		doPost(serviceTargetURL, f, respFile);
		return respFile;
	}
		
	public File generateFromURL(String serviceTargetURL, String url) throws IOException, URISyntaxException{
		serviceTargetURL = getServiceTargetURL(serviceTargetURL);
		File respFile = File.createTempFile("fits_view", ".html");	
		doGet(String.format(serviceTargetURL + "?url=%s", url), respFile);		
		return respFile;
	}
	
	
	public static void main(String[] args) throws IOException, URISyntaxException {
		FitsViewService test = new FitsViewService();
		File fits = new File("C:/Users/yu/Desktop/ngc6503.fits");
		File op1 = test.generateFromFile(mpdlServiceTarget, fits);
		System.err.println(op1.getAbsolutePath());
		String url = "http://edmond.mpdl.mpg.de/imeji/file/hmc00514.fit?id=http%3A//edmond.mpdl.mpg.de/imeji/file/lnliBOpQ_skRFX9H/b3/43/8d/78-7189-42e5-a8df-5684799d2b6c/0/original/9bf836b12e1e4ccc2467fecc02b77868.fit";
		File op2 = test.generateFromURL(mpdlServiceTarget, url);
		System.err.println(op2.getAbsolutePath());
	}

}
