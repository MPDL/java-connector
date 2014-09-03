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
	
	public static void main(String[] args) throws IOException, URISyntaxException {
		SWC3DViewService test = new SWC3DViewService();
		File swc = new File("C:/Users/schudan/Desktop/HB060602_3ptSoma.swc");
//		File datei = test.generateFromFile("http://vm15.mpdl.mpg.de/swc/api/view", swc, false);
//		File datei = test.generateFromString("http://vm15.mpdl.mpg.de/swc/api/view", "1 1 293.400000000000 175.050000000000 154.350000000000 0.450601700000 -1", false);
		File datei = test.generateFromURL("http://localhost:8080/swc/api/view", "http://localhost:8080/Service-api-webpage/Test.swc", false);
		File output = new File("C:/Users/schudan/Desktop/test65.html");
		Files.copy(datei.toPath(), output.toPath());
	}
	
	private static final String mpdlServiceTarget = PropertyReader.getProperty("swc.3Dview.targetURL");
	
	
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
