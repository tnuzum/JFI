package resources;

import org.openqa.selenium.WebElement;
import pageObjects.DashboardPO;
import resources.base;


public class reusableWaits extends base{

		public static String dashboardMemberName()
	{
		DashboardPO d = new DashboardPO(driver);
		WebElement n = d.getMyInfoMemberName();
		while (n.getText().isBlank())
		{
			n.getText();
		}
		return null;
	}

	
	
	}
	
