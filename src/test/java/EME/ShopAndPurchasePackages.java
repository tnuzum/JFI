package EME;

import java.io.IOException;

import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.DashboardPO;
import pageObjects.PurchasePackagesPO;
import pageObjects.ShopPackagesPO;
import resources.base;
import resources.reusableMethods;

public class ShopAndPurchasePackages extends base {
	@BeforeClass
	public void initialize() throws InterruptedException, IOException {
		driver = initializeDriver();
		log.info("Driver Initialized");
		driver.get(prop.getProperty("EMELoginPage"));

	}
	@Test (priority = 1, description = "Confirming Filter works")
	public void KeywordFilterCheck() throws IOException, InterruptedException {

		   reusableMethods.activeMember5Login();
		   Thread.sleep(2000);
		   DashboardPO d = new DashboardPO(driver);
		   d.getMenuShopPackages().click();
		   ShopPackagesPO sp = new ShopPackagesPO(driver);
		   sp.getKeyWord().sendKeys("Basketball");
		  /* System.out.println(sp.getPackageNames().size());
     	   System.out.println(sp.getPackagesections().size());
     	   System.out.println(sp.getPurchaseButtons().size());*/
		   for (int i = 0; i<sp.getPackageNames().size(); i++)
		   {
			   Assert.assertTrue(sp.getPackageNames().get(i).getText().contains("Basketball"));
		   }
		   
	       }
	@Test (priority = 2, description = "Confirming Add To Cart button text changed to Purchase and Page Header name Validation")
	public void PurchaseBtnNameCheck() throws IOException, InterruptedException {

		   ShopPackagesPO sp = new ShopPackagesPO(driver);
		   for (int i = 0; i<sp.getPurchaseButtons().size(); i++)
		   {
			   Assert.assertEquals("Purchase", sp.getPurchaseButtons().get(i).getText());
		   }
		   Assert.assertEquals("Shop Packages", sp.getPageHeader().getText());
		   Assert.assertEquals("Dashboard", sp.getBreadcrumbDashboard().getText());
		   Assert.assertEquals("Shop", sp.getBreadcrumbShop().getText());
		   
	       }
		   
		   @Test (priority = 3, description = "Confirming correct package is selected")
		   public void SelectPackage() {
			   
		   ShopPackagesPO sp = new ShopPackagesPO(driver);
			   
		   for (int i = 0; i<sp.getPackageNames().size(); i++)
			   
		   { if (sp.getPackageNames().get(i).getText().contains("Basketball 60 Min"))
			   
			   {
				  sp.getPurchaseButtons().get(i).click();
				  break;
			   }
			   
		   }
		   PurchasePackagesPO pp = new PurchasePackagesPO(driver);
		   Assert.assertEquals("Basketball 60 Min", pp.getPackageName().getText());
		   }
		   
		   @Test (priority = 4, description = "Page Layout Validation")
			public void PageLayoutValidation() {
			   PurchasePackagesPO pp = new PurchasePackagesPO(driver);
			   Assert.assertEquals("Dashboard", pp.getBreadcrumbDashboard().getText());
			   Assert.assertEquals("Shop", pp.getBreadcrumbShop().getText());
			   Assert.assertEquals("Confirm", pp.getBreadcrumbConfirm().getText());
		   }


	/*@AfterClass
	public void teardown() throws InterruptedException
	{
		driver.close();
		driver=null;
	}*/
}