package application.view;

public enum Themes 
{
	LIGHT,
	DARK,
	ONEDARK,
	GITHUBLIGHT;
	
	
	// return path to the corresponding css file
	public static String getCssPath(Themes theme)
	{
		switch(theme)
		{
			case LIGHT:
//				return "C:\\Users\\yoyoo\\Desktop\\Ojas\\Programs\\Java\\email_system\\src\\application\\view\\css\\themeLight.css";
				return "css/themeLight.css";
				
			case DARK:
				return "css/themeDark.css";
					
			case ONEDARK:
				return "css/themeOneDark.css";
					
			case GITHUBLIGHT:
//				return "C:\\Users\\yoyoo\\Desktop\\Ojas\\Programs\\Java\\email_system\\src\\application\\view\\css\\themeGithubLight.css";
				return "css/themeGithubLight.css";
				
			default:
				return null;
		}
	}
}
