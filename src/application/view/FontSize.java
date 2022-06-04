package application.view;

public enum FontSize
{
	SMALL,
	MEDIUM, 
	LARGE;
	
	
	// return path to the corresponding css file
	public static String getPath(FontSize fs)
	{
		switch(fs)
		{
			case SMALL:
				return "css/fontSmall.css";
				
			case MEDIUM:
					return "css/fontMedium.css";
					
			case LARGE:
					return "css/fontBig.css";
				
			default:
				return null;
		}
	}
}
