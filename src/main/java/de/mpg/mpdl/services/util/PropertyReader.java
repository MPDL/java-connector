package de.mpg.mpdl.services.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Helper class for reading properties from the global property file.
 */
public class PropertyReader
{
    private static Properties properties;

    private static final String DEFAULT_PROPERTY_FILE = "services.properties";
    /**
     * Gets the value of a property for the given key from the system properties or the services property file.
     * It is always tried to get the requested property value from the system properties.
     * This option gives the opportunity to set a specific property temporary using the system properties.
     * If the requested property could not be obtained from the system properties the services property file is accessed.
     * (For details on access to the properties file see class description.)
     *
     * @param key The key of the property.
     * @return The value of the property.
     * @throws IOException
     * @throws URISyntaxException 
     */
    public static String getProperty(String key)
    {

        if (properties == null)
        {
            loadProperties();
        }
        String value = properties.getProperty(key);
        return value;
    }
    

    public static void loadProperties()
    {
        String propertiesFile = DEFAULT_PROPERTY_FILE;
        InputStream instream = getInputStream(propertiesFile);
        properties = new Properties();
        try
        {
            properties.load(instream);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        properties.putAll(properties);
    }

    private static InputStream getInputStream(String filePath)
    {
        InputStream instream = null;
        try
        {
        	instream = new FileInputStream(filePath);
        }
        catch (Exception e)
        {
            URL url = PropertyReader.class.getClassLoader().getResource(filePath);
            if (url != null)
            {
                try
                {
                    instream = url.openStream();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
        }
        if (instream == null)
        {
            try
            {
                throw new FileNotFoundException(filePath);
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }
        return instream;
    }
    

}
