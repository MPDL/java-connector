package de.mpg.mpdl.services.api;

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

import de.mpg.mpdl.services.util.OutputFormat;
import de.mpg.mpdl.services.util.PropertyReader;


public class MediaConverterService {

	public File convertFromURL(String serviceTargetURL, String mediaURL,OutputFormat outputFormat, String outputSize, String crop) throws IOException, URISyntaxException {
		File cFile = File.createTempFile("mediaConverter_", "." + outputFormat);

		if ("".equalsIgnoreCase(serviceTargetURL))
			serviceTargetURL = PropertyReader.getProperty("mediaConverter.targetURL");

		GetMethod get = new GetMethod(serviceTargetURL + addParameterstoURL(mediaURL, outputFormat.toString(), outputSize, crop));

		HttpClient client = new HttpClient();
		int status = client.executeMethod(get);
		IOUtils.copy(get.getResponseBodyAsStream(), new FileOutputStream(cFile));
		get.releaseConnection();
		return cFile;
	}

	public File convertFromFile(String serviceTargetURL, File f, OutputFormat outputFormat, String outputSize, String crop)throws IOException, URISyntaxException {
		File cFile = File.createTempFile("mediaConverter_", "." + outputFormat);

		if ("".equalsIgnoreCase(serviceTargetURL))
			serviceTargetURL = PropertyReader.getProperty("mediaConverter.targetURL");

		PostMethod post = new PostMethod(serviceTargetURL + addParameterstoURL(null, outputFormat.toString(), outputSize,
						crop));
		Part[] parts = { new FilePart(f.getName(), f) };
		post.setRequestEntity(new MultipartRequestEntity(parts, post.getParams()));
		HttpClient client = new HttpClient();
		client.executeMethod(post);
		IOUtils.copy(post.getResponseBodyAsStream(), new FileOutputStream(cFile));
		post.releaseConnection();
		return cFile;
	}

	public String addParameterstoURL(String mediaURL,
			String outputFormat, String outputSize, String crop) {
		if (mediaURL == null)
			return "?format=" + outputFormat + "&size=" + outputSize + "&crop=" + crop;
		else
			return "?url=" + mediaURL + "&format=" + outputFormat + "&size=" + outputSize + "&crop=" + crop;
	}


}
