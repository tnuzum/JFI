package JOL;
import java.io.IOException;

import org.junit.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pageObjectsJOL.LandingPagePO;
import resources.base;


public class LandingPageTest extends base{
	
	
	
	@BeforeTest
		public void initialize() throws IOException
		{
			 driver = initializeDriver();
			 driver.get(prop.getProperty("JOLLoginPage"));
			 
		}
		
	@Test (priority = 1)
	public void pageHeader() throws IOException
	{
		LandingPagePO l = new LandingPagePO(driver);
		Assert.assertEquals("1. Select Club", l.getPageHeader().getText());
		Assert.assertTrue(l.getFilterByLocationLabel().getText().contains("Filter By Location:"));
	}
	
	@Test (priority = 2)
		public void clubListResult1() throws IOException
		{
		LandingPagePO l = new LandingPagePO(driver);
		Assert.assertEquals("Jonas Sports-Plex", l.getClub1Name().getText());
		Assert.assertEquals("Webster, TX", l.getClub1Location().getText());
		Assert.assertEquals("Jonas Sports-Plex", l.getClub1AddressTitle().getText());
		Assert.assertTrue(l.getClub1Address().getText().contains("16969 S Texas Ave"));

//		System.out.println(l.getClub1Address().getText());

		}
	@Test (priority = 3)
		public void clubListResult2() throws IOException
		{
		LandingPagePO l = new LandingPagePO(driver);
		Assert.assertEquals("Studio Jonas", l.getClub2Name().getText());
		Assert.assertEquals("Webster, TX", l.getClub2Location().getText());
		Assert.assertEquals("Studio Jonas", l.getClub2AddressTitle().getText());
		Assert.assertTrue(l.getClub2Address().getText().contains("1 Nasa Pkwy"));

		}

	@AfterTest
		public void teardown() throws InterruptedException
		{
			driver.close();
			driver=null;
		}
	
	
	
	

}
