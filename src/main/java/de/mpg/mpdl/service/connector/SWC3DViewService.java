package de.mpg.mpdl.service.connector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.io.IOUtils;

import de.mpg.mpdl.service.connector.util.PropertyReader;



public class SWC3DViewService extends RestClient{
	
	private static final String mpdlServiceTarget = PropertyReader.getProperty("swc.3Dview.targetURL");
	
	
    /**
     * Generates 3D View from MPDL 3D View Service.
     * 
     * @param serviceTargetURL URL of your Service. "" for using MPDL SWC 3D Viewer service.
     * @param inputStream .swc file input stream.
     * @param portable true for getting 3D View with local javascript.
     * @return 3D View file
     * @throws IOException
     * @throws URISyntaxException 
     */
	public File generateFromStream(String serviceTargetURL, InputStream inputStream, boolean portable) throws IOException, URISyntaxException{
		serviceTargetURL = getServiceTargetURL(serviceTargetURL);
		File respFile = File.createTempFile("swc_3d", ".html");
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("swc", IOUtils.toString(inputStream));
		params.put("portable", String.valueOf(portable));
		doPost(serviceTargetURL, params, respFile);
		return respFile;
	}
	
	
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
		serviceTargetURL = getServiceTargetURL(serviceTargetURL);
		File respFile = File.createTempFile("swc_3d", ".html");
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("swc", IOUtils.toString(new FileInputStream(f)));
		params.put("portable", String.valueOf(portable));
		doPost(serviceTargetURL, params, respFile);
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
		serviceTargetURL = getServiceTargetURL(serviceTargetURL);
		File respFile = File.createTempFile("swc_3d", ".html");	
		Map<String, String> params = new HashMap<String, String>();
		params.put("swc", swcInString);
		params.put("portable", String.valueOf(portable));
		doPost(serviceTargetURL, params, respFile);
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
		serviceTargetURL = getServiceTargetURL(serviceTargetURL);
		File respFile = File.createTempFile("swc_3d", ".html");	
		doGet(String.format(serviceTargetURL + "?url=%s&portable=%s", url, String.valueOf(portable) ), respFile);		
		return respFile;
	}


	String getServiceTargetURL(String url) {
		if("".equalsIgnoreCase(url))
			url = mpdlServiceTarget;
		return url;
	}
	


}
