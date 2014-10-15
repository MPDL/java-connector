package de.mpg.mpdl.service.connector;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.Part;

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
		Part[] parts = { new FilePart(f.getName(), f) };
		doPost(serviceTargetURL, parts, respFile);
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
	public File getAnalysisFromString(String serviceTargetURL, String swcInString, String numberOfBins) throws IOException, URISyntaxException{
		if("".equals(numberOfBins)) {
			numberOfBins = "10";
		}
		serviceTargetURL = getServiceTargetURL(serviceTargetURL);		
		File respFile = File.createTempFile("swc_3d", ".txt");	
		Map<String, String> params = new HashMap<String, String>();
		params.put("swc", swcInString);
		params.put("numberOfBins", numberOfBins);
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
	public File getAnalysisFromURL(String serviceTargetURL, String url, String numberOfBins) throws IOException, URISyntaxException{
		if("".equals(numberOfBins)) {
			numberOfBins = "10";
		}
		serviceTargetURL = getServiceTargetURL(serviceTargetURL);
		File respFile = File.createTempFile("swc_3d", ".txt");		
		doGet(serviceTargetURL + String.format("?url=%s&numberOfBins=%s", url, numberOfBins), respFile);
		return respFile;
	}
	
	String getServiceTargetURL(String url){
		if("".equalsIgnoreCase(url))
			url = mpdlServiceTarget;
		return url;
	}
	
	

}
