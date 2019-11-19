package JOL;
import java.io.IOException;

import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pageObjectsJOL.SelectClubPagePO;
import pageObjectsJOL.SelectPlanPagePO;
import resources.base;


public class SelectClubPageTest extends base{
	
	
	
	@BeforeTest (description = "Initialize")
		public void initialize() throws IOException
		{
			 driver = initializeDriver();
			 driver.get(prop.getProperty("JOLLoginPage"));
			 
		}
		
	@Test (priority = 1, description = "pageHeader")
	public void pageHeader()
	{
		SelectClubPagePO sc = new SelectClubPagePO(driver);
		Assert.assertEquals("1. Select Club", sc.getPageHeader().getText());
		Assert.assertTrue(sc.getFilterByLocationLabel().getText().contains("Filter By Location:"));
	}
	
	@Test (priority = 2, description = "clubListResult1")
		public void clubListResult1() throws InterruptedException
		{
		SelectClubPagePO sc = new SelectClubPagePO(driver);
		SelectPlanPagePO sp = new SelectPlanPagePO(driver);
		Assert.assertEquals("Jonas Sports-Plex", sc.getClub1Name().getText());
		Assert.assertEquals("Webster, TX", sc.getClub1Location().getText());
		Assert.assertEquals("Jonas Sports-Plex", sc.getClub1AddressTitle().getText());
		Assert.assertTrue(sc.getClub1Address().getText().contains("16969 S Texas Ave"));
		Assert.assertTrue(sc.getClub1Address().getText().contains("Webster, TX, 77598"));
		Assert.assertTrue(sc.getClub1Address().getText().contains("Phone : (425) 998-3435"));
		Assert.assertTrue(sc.getClub1Address().getText().contains("Email : stacie.coates@jonasfitness.com"));
		Assert.assertTrue(sc.getClub1ViewPlansButton().isEnabled());
				
		sc.getClub1ViewPlansButton().click(); // ensure button is working
			
		WebElement scl = sp.getSelectClubLink();
		while (!scl.isEnabled())//while button is NOT(!) enabled
		{
			Thread.sleep(200);
			System.out.println("Waiting for page to load...");
		}
		sp.getSelectClubLink().click(); // return to SelectClub page
		
		//System.out.println(sc.getClub2Address().getText());

		}
	@Test (priority = 3, description = "clubListResult2")
		public void clubListResult2()
		{
		SelectClubPagePO sc = new SelectClubPagePO(driver);
		Assert.assertEquals("Studio Jonas", sc.getClub2Name().getText());
		Assert.assertEquals("Webster, TX", sc.getClub2Location().getText());
		Assert.assertEquals("Studio Jonas", sc.getClub2AddressTitle().getText());
		Assert.assertTrue(sc.getClub2Address().getText().contains("1 Nasa Pkwy"));
		Assert.assertTrue(sc.getClub2Address().getText().contains("Webster, TX, 77598"));
		Assert.assertTrue(sc.getClub2Address().getText().contains("Phone : (425) 998-3435"));
		Assert.assertTrue(sc.getClub2Address().getText().contains("Email : Stacie.Coates@Jonasfitness.com"));
		Assert.assertTrue(sc.getClub2ViewPlansButton().isEnabled());

		}

	@AfterTest
		public void teardown()
		{
			driver.close();
			driver=null;
		}
	
	
	
	

}
