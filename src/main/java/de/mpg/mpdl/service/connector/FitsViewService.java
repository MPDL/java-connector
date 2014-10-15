package de.mpg.mpdl.service.connector;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.io.IOUtils;

import de.mpg.mpdl.service.connector.util.PropertyReader;

public class FitsViewService extends RestClient{

	private static final String mpdlServiceTarget = PropertyReader.getProperty("fits.view.targetURL");


    /**
     * Generates fits Viewer from MPDL fits View Service.
     * 
     * @param serviceTargetURL URL of your Service. "" for using MPDL fits Viewer service.
     * @param inputStream .swc file input stream.
     * @return fits View file
     * @throws IOException
     * @throws URISyntaxException 
     */
	public File generateFromStream(String serviceTargetURL, InputStream inputStream) throws IOException, URISyntaxException{
		serviceTargetURL = getServiceTargetURL(serviceTargetURL);
		File respFile = File.createTempFile("fits_view", ".html");		
		Map<String, String> params = new HashMap<String, String>();
		params.put("fit", IOUtils.toString(inputStream));
		doPost(serviceTargetURL, params, respFile);
		return respFile;
	}
	
    /**
     * Generates fits Viewer from MPDL fits View Service.
     * 
     * @param serviceTargetURL URL of your Service. "" for using MPDL fits Viewer service.
     * @param f .fit(s) raw file.
     * @return fits View file
     * @throws IOException
     * @throws URISyntaxException 
     */
	public File generateFromFile(String serviceTargetURL, File f) throws IOException, URISyntaxException{
		serviceTargetURL = getServiceTargetURL(serviceTargetURL);
		File respFile = File.createTempFile("fits_view", ".html");	
		Part[] parts = { new FilePart(f.getName(), f) };
		doPost(serviceTargetURL, parts, respFile);
		return respFile;
	}
		
    /**
     * Generates fits Viewer from MPDL fits View Service.
     * 
     * @param serviceTargetURL URL of your Service. "" for using fits Viewer service.
     * @param url .fit(s) file url.
     * @return fits View file
     * @throws IOException
     * @throws URISyntaxException 
     */
	public File generateFromURL(String serviceTargetURL, String url) throws IOException, URISyntaxException{
		serviceTargetURL = getServiceTargetURL(serviceTargetURL);
		File respFile = File.createTempFile("fits_view", ".html");	
		doGet(String.format(serviceTargetURL + "?url=%s", url), respFile);		
		return respFile;
	}
	
	String getServiceTargetURL(String url) {
		if("".equalsIgnoreCase(url))
			url = mpdlServiceTarget;
		return url;
	}
	


}
