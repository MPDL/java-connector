package de.mpg.mpdl.services.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.IOUtils;

import de.mpg.mpdl.services.util.OutputFormat;
import de.mpg.mpdl.services.util.PropertyReader;



public class ScreenshotService {
	
	public File captureFromURL(String serviceTargetURL, String url, OutputFormat outputFormat, String outputSize, String crop)throws IOException, URISyntaxException{
		File ssFile = File.createTempFile("screenshot_", "." + outputFormat);

		if("".equalsIgnoreCase(serviceTargetURL))
			serviceTargetURL = PropertyReader.getProperty("screenshot.targetURL");

		GetMethod get = new GetMethod(serviceTargetURL + addParameterstoURL(url, outputFormat.toString(), outputSize, crop));
		HttpClient client = new HttpClient();
		client.executeMethod(get);
		IOUtils.copy(get.getResponseBodyAsStream(), new FileOutputStream(ssFile));
		get.releaseConnection();
		return ssFile;
	}
	
	
	public File captureFromHTML(String serviceTargetURL, String html, OutputFormat outputFormat, String outputSize, String crop)throws IOException, URISyntaxException{
		File ssFile = File.createTempFile("screenshot_", "." + outputFormat);

		if("".equalsIgnoreCase(serviceTargetURL))
			serviceTargetURL = PropertyReader.getProperty("screenshot.targetURL");

		PostMethod post = new PostMethod(serviceTargetURL + addParameterstoURL("", outputFormat.toString(), outputSize, crop));
		post.setParameter("html", html);

		HttpClient client = new HttpClient();
		client.executeMethod(post);
		IOUtils.copy(post.getResponseBodyAsStream(), new FileOutputStream(ssFile));
		post.releaseConnection();
		return ssFile;
	}
	
	
	public static String addParameterstoURL(String url, String outputFormat, String outputSize, String crop){
		if(url == null)
			return "?format=" + outputFormat + "&size=" + outputSize + "&crop=" + crop;
		else
			return "?url=" + url + "&format=" + outputFormat + "&size=" + outputSize + "&crop=" + crop;
	}
	

	


}
