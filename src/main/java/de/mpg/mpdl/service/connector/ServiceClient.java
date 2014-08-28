package de.mpg.mpdl.service.connector;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;

public abstract class ServiceClient {
	protected File doPost(File f){
		return f;
	}
	
	
	protected File doGet(String connURL, File f) throws HttpException, IOException{
		GetMethod get = new GetMethod(connURL);
		HttpClient client = new HttpClient();
		client.executeMethod(get);
		IOUtils.copy(get.getResponseBodyAsStream(), new FileOutputStream(f));
		get.releaseConnection();
		return f; 
	}
	
	protected String addParameterstoURL(String mediaURL,
			String outputFormat, String outputSize, String crop) {
		if (mediaURL == null)
			return "?format=" + outputFormat + "&size=" + outputSize + "&crop=" + crop;
		else
			return "?url=" + mediaURL + "&format=" + outputFormat + "&size=" + outputSize + "&crop=" + crop;
	}
}
