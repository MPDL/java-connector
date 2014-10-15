package de.mpg.mpdl.service.connector;

public class Connector extends ClassLoader{

	
	
	public static MediaConverterService getMediaConverterService() throws InstantiationException, IllegalAccessException
	{
		return MediaConverterService.class.newInstance();
	}
	
	public static ScreenshotService getScreenshotService() throws InstantiationException, IllegalAccessException
	{
		return ScreenshotService.class.newInstance();
	}
	
	public static SWC3DViewService getSWC3DViewService() throws InstantiationException, IllegalAccessException
	{
		return SWC3DViewService.class.newInstance();
	}
	
	public static SWCAnalysisService getSWCAnalysisService() throws InstantiationException, IllegalAccessException
	{
		return SWCAnalysisService.class.newInstance();
	}
	
	public static FitsViewService getFitsViewService() throws InstantiationException, IllegalAccessException
	{
		return FitsViewService.class.newInstance();
	}
	
	public static DataViewerService getDataViewerService() throws InstantiationException, IllegalAccessException
	{
		return DataViewerService.class.newInstance();
	}
	
	
	
	
}
