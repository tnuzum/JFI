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
		Assert.assertTrue(l.getClub1Address().getText().contains("Webster, TX, 77598"));
		Assert.assertTrue(l.getClub1Address().getText().contains("Phone : (425) 998-3435"));
		Assert.assertTrue(l.getClub1Address().getText().contains("Email : stacie.coates@jonasfitness.com"));
		Assert.assertTrue(l.getClub1ViewPlansButton().isEnabled());
		l.getClub1ViewPlansButton().click();
		
		//System.out.println(l.getClub2Address().getText());

		}
	@Test (priority = 3)
		public void clubListResult2() throws IOException
		{
		LandingPagePO l = new LandingPagePO(driver);
		Assert.assertEquals("Studio Jonas", l.getClub2Name().getText());
		Assert.assertEquals("Webster, TX", l.getClub2Location().getText());
		Assert.assertEquals("Studio Jonas", l.getClub2AddressTitle().getText());
		Assert.assertTrue(l.getClub2Address().getText().contains("1 Nasa Pkwy"));
		Assert.assertTrue(l.getClub2Address().getText().contains("Webster, TX, 77598"));
		Assert.assertTrue(l.getClub2Address().getText().contains("Phone : (425) 998-3435"));
		Assert.assertTrue(l.getClub2Address().getText().contains("Email : Stacie.Coates@Jonasfitness.com"));
		Assert.assertTrue(l.getClub2ViewPlansButton().isEnabled());

		}

	@AfterTest
		public void teardown() throws InterruptedException
		{
			driver.close();
			driver=null;
		}
	
	
	
	

}
