package de.mpg.mpdl.service.connector;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;




import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.Part;

import de.mpg.mpdl.service.connector.util.OutputFormat;
import de.mpg.mpdl.service.connector.util.PropertyReader;


public class MediaConverterService extends RestClient{
	
	public static void main(String[] args) throws IOException, URISyntaxException {
		
		MediaConverterService test = new MediaConverterService();
		File inputFile = new File("C:/Users/schudan/Desktop/test2.png");
//		File datei = test.convertFromFile("", inputFile, OutputFormat.PNG, "", "");
		File datei = test.convertFromURL("", "https://www.wikimedia.de/w/images.homepage/thumb/1/19/Digikompzlogo.jpg/170px-Digikompzlogo.jpg", OutputFormat.PNG, "", "");
		File output = new File("C:/Users/schudan/Desktop/test20.png");
		Files.copy(datei.toPath(), output.toPath());
		
	}

	private static final String mpdlServiceTarget = PropertyReader.getProperty("mediaConverter.targetURL");
    /**
     * Converts media.
     * 
     * @param serviceTargetURL URL of your Service. "" for using MPDL media conversion service.
     * @param mediaURL url of a media(png/tiff/jpqg... file).
     * @param outputFormat eg. png/jpeg..., "" for default format png.
     * @param outputSize "" for original size. 
     * @param crop eg. "40x30-10-10"
     * @return screenshot file
     * @throws IOException
     * @throws URISyntaxException 
     */	
	public File convertFromURL(String serviceTargetURL, String mediaURL, OutputFormat outputFormat, String outputSize, String crop) throws IOException, URISyntaxException {
		File cFile = File.createTempFile("mediaConverter_", "." + outputFormat);

		serviceTargetURL = getServiceTargetURL(serviceTargetURL);

		doGet(serviceTargetURL + addParameterstoURL(mediaURL, outputFormat.toString(), outputSize, crop), cFile);
		return cFile;
	}

    /**
     * Converts media.
     * 
     * @param serviceTargetURL URL of your Service. "" for using MPDL media conversion service.
     * @param mediaFile media(png/tiff/jpqg...) file.
     * @param outputFormat eg. png/jpeg..., "" for default format png.
     * @param outputSize "" for original size. 
     * @param crop eg. "40x30-10-10"
     * @return screenshot file
     * @throws IOException
     * @throws URISyntaxException 
     */	
	public File convertFromFile(String serviceTargetURL, File f, OutputFormat outputFormat, String outputSize, String crop)throws IOException, URISyntaxException {
		File cFile = File.createTempFile("mediaConverter_", "." + outputFormat);
		serviceTargetURL = getServiceTargetURL(serviceTargetURL) + addParameterstoURL(null, outputFormat.toString(), outputSize, crop);
		Part[] parts = { new FilePart(f.getName(), f) };
		doPost(serviceTargetURL, parts, cFile);
		return cFile;
	}

	String getServiceTargetURL(String url) {
		if("".equalsIgnoreCase(url))
			url = mpdlServiceTarget;
		return url;
	}


}
