package application.view;

public enum Themes 
{
	LIGHT,
	DARK,
	ONEDARK,
	GITHUBLIGHT;
	
	
	// return path to the corresponding css file
	public static String getPath(Themes theme)
	{
		switch(theme)
		{
			case LIGHT:
				return "css/themeLight.css";
				
			case DARK:
					return "css/themeDark.css";
					
			case ONEDARK:
					return "css/themeOneDark.css";
					
			case GITHUBLIGHT:
				return "css/themeGithubLight.css";
				
			default:
				return null;
		}
	}
}
