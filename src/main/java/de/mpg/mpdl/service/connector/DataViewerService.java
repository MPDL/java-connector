package de.mpg.mpdl.service.connector;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.io.IOUtils;

import de.mpg.mpdl.service.connector.util.PropertyReader;

public class DataViewerService extends RestClient{
	
	
	private static final String mpdlServiceTarget = PropertyReader.getProperty("dataViewer.service.targetURL");
	
    /**
     * Transfers Stream to data-viewer Service.
     * 
     * @param serviceTargetURL URL of your Service. "" for using MPDL data-viewer Viewer service.
     * @param inputStream input stream.
     * @return mimeType file mimetype
     * @throws IOException
     * @throws URISyntaxException 
     */
	public File transferStream(String serviceTargetURL, InputStream istream, String mimeType) throws IOException{	
		serviceTargetURL = getServiceTargetURL(serviceTargetURL);
		File respFile = File.createTempFile("result_", ".html");
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("file", IOUtils.toString(istream));
		params.put("mimetype", mimeType);
		doPost(serviceTargetURL, params, respFile);
		return respFile;
	}
	
    /**
     * Transfers File to data-vierwer Service.
     * 
     * @param serviceTargetURL URL of your Service. "" for using MPDL data-viewer Viewer service.
     * @param f file.
     * @return mimeType file mimetype
     * @throws IOException
     * @throws URISyntaxException 
     */
	public File transferFile(String serviceTargetURL, File f, String mimeType)  throws IOException{
		serviceTargetURL = getServiceTargetURL(serviceTargetURL);
		File respFile = File.createTempFile("result_", ".html");		
		Part[] parts = { new FilePart(f.getName(), f) , new StringPart("mimetype", mimeType)};
		doPost(serviceTargetURL, parts, respFile);
		return respFile;
	}
	
    /**
     * Transfers File to data-vierwer Service.
     * 
     * @param serviceTargetURL URL of your Service. "" for using MPDL data-viewer Viewer service.
     * @param url file url.
     * @return mimeType file mimetype
     * @throws IOException
     * @throws URISyntaxException 
     */
	public File transferURL(String serviceTargetURL, String url, String mimeType)  throws IOException{
		serviceTargetURL = getServiceTargetURL(serviceTargetURL);
		File respFile = File.createTempFile("fits_view", ".html");	
		doGet(String.format(serviceTargetURL + "?url=%s&mimetype=%s", url, mimeType), respFile);		
		return respFile;
	}
	
	
	String getServiceTargetURL(String url) {
		if("".equalsIgnoreCase(url))
			url = mpdlServiceTarget;
		return url;
	}
}
