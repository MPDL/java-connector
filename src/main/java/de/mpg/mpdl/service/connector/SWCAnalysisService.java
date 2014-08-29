package de.mpg.mpdl.service.connector;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;

import de.mpg.mpdl.service.connector.util.PropertyReader;

public class SWCAnalysisService extends RestClient{
	
	private static final String mpdlServiceTarget = PropertyReader.getProperty("swc.analyze.targetURL");
	
    /**
     * Gets ayalysis data from MPDL 3D View Service.
     * 
     * @param serviceTargetURL URL of your Service. "" for using MPDL SWC 3D Viewer service.
     * @param f .swc raw file.
     * @return 3D View file
     * @throws IOException
     * @throws URISyntaxException 
     * @throws MessagingException 
     */
	public File getAnalysisFromFile(String serviceTargetURL, File f) throws IOException, URISyntaxException, MessagingException{
		serviceTargetURL = getServiceTargetURL(serviceTargetURL);
		File respFile = File.createTempFile("swc_3d", ".txt");
		doPost(serviceTargetURL, f, respFile);
		return respFile;
	}
	
    /**
     * Gets ayalysis data from MPDL 3D View Service
     * 
     * @param serviceTargetURL URL of your Service. "" for using MPDL SWC 3D Viewer service.
     * @param swcInString .swc file content.
     * @return 3D View file
     * @throws IOException
     * @throws URISyntaxException 
     */
	public File getAnalysisFromString(String serviceTargetURL, String swcInString) throws IOException, URISyntaxException{
		serviceTargetURL = getServiceTargetURL(serviceTargetURL);
		File respFile = File.createTempFile("swc_3d", ".txt");	
		Map<String, String> params = new HashMap<String, String>();
		params.put("swc", swcInString);		
		doPost(serviceTargetURL, params, respFile);
		return respFile;
	}
	
    /**
     * Gets ayalysis data from MPDL 3D View Service
     * 
     * @param serviceTargetURL URL of your Service. "" for using MPDL SWC 3D Viewer service.
     * @param url .swc file url.
     * @return 3D View file
     * @throws IOException
     * @throws URISyntaxException 
     */
	public File getAnalysisFromURL(String serviceTargetURL, String url) throws IOException, URISyntaxException{
		serviceTargetURL = getServiceTargetURL(serviceTargetURL);
		File respFile = File.createTempFile("swc_3d", ".txt");	
		Map<String, String> params = new HashMap<String, String>();
		params.put("url", url);	
		doPost(serviceTargetURL, params, respFile);
		return respFile;
	}
	
	String getServiceTargetURL(String url){
		if("".equalsIgnoreCase(url))
			url = mpdlServiceTarget;
		return url;
	}
	
	

}
