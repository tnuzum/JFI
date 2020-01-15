package EME;

import javax.mail.Message;
import javax.mail.MessagingException;


import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class EmailTests  {
	private static EmailUtils emailUtils ;

	  @BeforeClass
	  public static void connectToEmail() {
	    try {
	      emailUtils = new EmailUtils("jonasautotesting@gmail.com", "Testing1!", "smtp.gmail.com", EmailUtils.EmailFolder.INBOX) ;
	    } catch (Exception e) {
		      e.printStackTrace();
		      Assert.fail(e.getMessage());
		      //You have been successfully enrolled in the following class.
		    }
	  }
/*	  @Test
	  public void testVerificationCode() {
	    try {
	      //TODO: Execute actions to send verification code to email
	 
	      String verificationCode = emailUtils.getAuthorizationCode();
	 
	      //TODO: Enter verification code on screen and submit
	 
	      //TODO: add assertions
	 
	    } catch (Exception e) {
	      e.printStackTrace();
	      Assert.fail(e.getMessage());
	    }
	  }*/
	  
	  @Test(priority=1, description = "confirms the number of unread emails")
	  public void numberOfUnreadMails() throws MessagingException {
    	int count = emailUtils.getNumberOfUnreadMessages();
    	System.out.println(count);
    	Assert.assertTrue(count == 2);
    }
	  @Test(priority=2, description = "Verifies the Enrollment confirmation email")
	  public void EnrollmentConfirmation() {
	    try{
	    Message email1 = emailUtils.getMessagesBySubject("Enrollment Notification", true, 2)[0];
	    
	    System.out.println(emailUtils.getMessageContent(email1));
	    String emailMessage1 = emailUtils.getMessageContent(email1);
	    Assert.assertTrue(emailUtils.isTextInMessage(email1, "You have been successfully e=nrolled in the following class."));
	    Assert.assertTrue(emailMessage1.contains("You have been successfully e=nrolled in the following class."));
	    } catch (Exception e) {
	      e.printStackTrace();
	      Assert.fail(e.getMessage());
	    }
	    
  }
	  @Test(priority=3, description = "Verifies the Unenrollment confirmation email")
	  public void UnenrollmentConfirmation() {
	    try{
	    Message email2 = emailUtils.getMessagesBySubject("Unenrollment Notification for Member Self-Service", true, 2)[0];
	    
	    System.out.println(emailUtils.getMessageContent(email2));
	    String emailMessage2 = emailUtils.getMessageContent(email2);
	    Assert.assertTrue(emailUtils.isTextInMessage(email2, "You have unenrolled from the following  class."));
	    Assert.assertTrue(emailMessage2.contains("You have unenrolled from the following  class."));
	    } catch (Exception e) {
	      e.printStackTrace();
	      Assert.fail(e.getMessage());
	    }
	    
  }
	  
	 
	/*  @Test
	  public void testLink() {
	    
	    //TODO: apply for a loan using criteria that will result in the application being rejected
	    
	    try{
	      Message email = emailUtils.getMessagesBySubject("Decision on Your Loan Application", true, 5)[0];
	      String link = emailUtils.getUrlsFromMessage(email, "Click here to view the reason").get(0);
	      
	      driver.get(link);
	      
	      //TODO: continue testing
	    } catch (Exception e) {
	      e.printStackTrace();
	      Assert.fail(e.getMessage());
	    }
  }	*/
}
