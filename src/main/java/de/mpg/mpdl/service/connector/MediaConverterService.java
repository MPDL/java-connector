package de.mpg.mpdl.service.connector;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.io.IOUtils;

import de.mpg.mpdl.service.connector.util.OutputFormat;
import de.mpg.mpdl.service.connector.util.PropertyReader;


public class MediaConverterService extends ServiceClient{

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

		if ("".equalsIgnoreCase(serviceTargetURL))
			serviceTargetURL = PropertyReader.getProperty("mediaConverter.targetURL");

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
	public File convertFromFile(String serviceTargetURL, File mediaFile, OutputFormat outputFormat, String outputSize, String crop)throws IOException, URISyntaxException {
		File cFile = File.createTempFile("mediaConverter_", "." + outputFormat);

		if ("".equalsIgnoreCase(serviceTargetURL))
			serviceTargetURL = PropertyReader.getProperty("mediaConverter.targetURL");

		PostMethod post = new PostMethod(serviceTargetURL + addParameterstoURL(null, outputFormat.toString(), outputSize, crop));
		Part[] parts = { new FilePart(mediaFile.getName(), mediaFile) };
		post.setRequestEntity(new MultipartRequestEntity(parts, post.getParams()));
		HttpClient client = new HttpClient();
		client.executeMethod(post);
		IOUtils.copy(post.getResponseBodyAsStream(), new FileOutputStream(cFile));
		post.releaseConnection();
		
		return cFile;
	}




}
