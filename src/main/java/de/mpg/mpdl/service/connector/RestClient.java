package de.mpg.mpdl.service.connector;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.io.IOUtils;






public abstract class RestClient {
	
	protected File doPost(String connURL, File requestFile, File respFile) throws IOException{
		PostMethod post = new PostMethod(connURL);
		Part[] parts = { new FilePart(requestFile.getName(), requestFile) };
		post.setRequestEntity(new MultipartRequestEntity(parts, post.getParams()));
		HttpClient client = new HttpClient();
		client.executeMethod(post);
		IOUtils.copy(post.getResponseBodyAsStream(), new FileOutputStream(respFile));
		post.releaseConnection();
		return respFile;
	}
	
	protected File doPost(String connURL, Map<String, String> params, File respFile) throws IOException{
		PostMethod post = new PostMethod(connURL);
		Iterator iter = params.entrySet().iterator();
		while(iter.hasNext()){
			Entry e = (Entry) iter.next();
			post.setParameter((String)e.getKey(), (String)e.getValue());	
		}		
		HttpClient client = new HttpClient();
		client.executeMethod(post);
		IOUtils.copy(post.getResponseBodyAsStream(), new FileOutputStream(respFile));
		post.releaseConnection();
		return respFile;
	}
	
	
	
	
	protected File doGet(String connURL, File f) throws HttpException, IOException{
		GetMethod get = new GetMethod(connURL);
		HttpClient client = new HttpClient();
		client.executeMethod(get);
		IOUtils.copy(get.getResponseBodyAsStream(), new FileOutputStream(f));
		get.releaseConnection();
		return f; 
	}
	
	protected String addParameterstoURL(String mediaURL, String outputFormat, String outputSize, String crop) {
		if (mediaURL == null)
			return "?format=" + outputFormat + "&size=" + outputSize + "&crop=" + crop;
		else
			return "?url=" + mediaURL + "&format=" + outputFormat + "&size=" + outputSize + "&crop=" + crop;
	}
	
	abstract String getServiceTargetURL(String url);
	
	
}
