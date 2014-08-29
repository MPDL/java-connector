package de.mpg.mpdl.service.connector;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.IOUtils;

import de.mpg.mpdl.service.connector.util.OutputFormat;
import de.mpg.mpdl.service.connector.util.PropertyReader;



public class ScreenshotService extends RestClient{
	
	private static final String mpdlServiceTarget = PropertyReader.getProperty("screenshot.targetURL");
    /**
     * Captures screenshot.
     * 
     * @param serviceTargetURL URL of your Service. "" for using MPDL screenshot service.
     * @param url webpage url.
     * @param outputFormat eg. png/jpeg..., "" for default format png.
     * @param outputSize "" for original size. 
     * @param crop eg. "40x30-10-10"
     * @return screenshot file
     * @throws IOException
     * @throws URISyntaxException 
     */	
	public File captureFromURL(String serviceTargetURL, String url, OutputFormat outputFormat, String outputSize, String crop)throws IOException, URISyntaxException{
		File ssFile = File.createTempFile("screenshot_", "." + outputFormat);

		serviceTargetURL = getServiceTargetURL(serviceTargetURL);
		doGet(serviceTargetURL + addParameterstoURL(url, outputFormat.toString(), outputSize, crop), ssFile);
		return ssFile;
	}
	
    /**
     * Captures screenshot.
     * 
     * @param serviceTargetURL URL of your Service. "" for using MPDL screenshot service.
     * @param html html content of the webpage.
     * @param outputFormat eg. png/jpeg..., "" for default format png.
     * @param outputSize "" for original size. 
     * @param crop eg. "40x30-10-10"
     * @return screenshot file
     * @throws IOException
     * @throws URISyntaxException 
     */		
	public File captureFromHTML(String serviceTargetURL, String html, OutputFormat outputFormat, String outputSize, String crop)throws IOException, URISyntaxException{
		File ssFile = File.createTempFile("screenshot_", "." + outputFormat);
		serviceTargetURL = getServiceTargetURL(serviceTargetURL) + addParameterstoURL("", outputFormat.toString(), outputSize, crop);
		Map<String, String> params = new HashMap<String, String>();
		params.put("html", html);	
		doPost(serviceTargetURL, params, ssFile);
		return ssFile;
	}

	String getServiceTargetURL(String url) {
		if("".equalsIgnoreCase(url))
			url = mpdlServiceTarget;
		return url;
	}
	
}
