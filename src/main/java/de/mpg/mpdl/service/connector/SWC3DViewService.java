package de.mpg.mpdl.service.connector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.io.IOUtils;

import de.mpg.mpdl.service.connector.util.PropertyReader;



public class SWC3DViewService extends ServiceClient{
	
	
    /**
     * Generates 3D View from MPDL 3D View Service.
     * 
     * @param serviceTargetURL URL of your Service. "" for using MPDL SWC 3D Viewer service.
     * @param f .swc raw file.
     * @param portable true for getting 3D View with local javascript.
     * @return 3D View file
     * @throws IOException
     * @throws URISyntaxException 
     */
	public File generateFromFile(String serviceTargetURL, File f, boolean portable) throws IOException, URISyntaxException{
		if("".equalsIgnoreCase(serviceTargetURL))
			serviceTargetURL = PropertyReader.getProperty("swc.3Dview.targetURL");
		File respFile = File.createTempFile("swc_3d", ".html");
		
		PostMethod post = new PostMethod(serviceTargetURL);
		post.setParameter("swc", IOUtils.toString(new FileInputStream(f)));
		post.setParameter("portable", String.valueOf(portable));

		HttpClient client = new HttpClient();
		client.executeMethod(post);
		IOUtils.copy(post.getResponseBodyAsStream(), new FileOutputStream(respFile));
		post.releaseConnection();
		return respFile;
	}
	
    /**
     * Generates 3D View from MPDL 3D View Service
     * 
     * @param serviceTargetURL URL of your Service. "" for using MPDL SWC 3D Viewer service.
     * @param swcInString .swc file content.
     * @param portable true for getting 3D View with local javascript.
     * @return 3D View file
     * @throws IOException
     * @throws URISyntaxException 
     */
	public File generateFromString(String serviceTargetURL, String swcInString, boolean portable) throws IOException, URISyntaxException{
		if("".equalsIgnoreCase(serviceTargetURL))
			serviceTargetURL = PropertyReader.getProperty("swc.3Dview.targetURL");
		File respFile = File.createTempFile("swc_3d", ".html");	
		PostMethod post = new PostMethod(serviceTargetURL);
		post.setParameter("swc", swcInString);		
		post.setParameter("portable", String.valueOf(portable));
		HttpClient client = new HttpClient();
		client.executeMethod(post);
		IOUtils.copy(post.getResponseBodyAsStream(), new FileOutputStream(respFile));
		post.releaseConnection();
		return respFile;
	}
	
    /**
     * Generates 3D View from MPDL 3D View Service
     * 
     * @param serviceTargetURL URL of your Service. "" for using MPDL SWC 3D Viewer service.
     * @param url .swc file url.
     * @param portable true for getting 3D View with local javascript.
     * @return 3D View file
     * @throws IOException
     * @throws URISyntaxException 
     */
	public File generateFromURL(String serviceTargetURL, String url, boolean portable) throws IOException, URISyntaxException{
		if("".equalsIgnoreCase(serviceTargetURL))
			serviceTargetURL = PropertyReader.getProperty("swc.3Dview.targetURL");
		File respFile = File.createTempFile("swc_3d", ".html");	
		PostMethod post = new PostMethod(serviceTargetURL);
		post.setParameter("url", url);		
		post.setParameter("portable", String.valueOf(portable));
		HttpClient client = new HttpClient();
		client.executeMethod(post);
		IOUtils.copy(post.getResponseBodyAsStream(), new FileOutputStream(respFile));
		post.releaseConnection();
		return respFile;
	}
	


}
