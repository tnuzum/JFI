package EME;

import javax.mail.Message;
import javax.mail.MessagingException;


import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class EmailConfirmations  {
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
	    Assert.assertTrue(emailMessage1.contains("You have been successfully e=nrolled in the following class."));
	    
	    Assert.assertTrue(emailUtils.isTextInMessage(email1, "You have been successfully e=nrolled in the following class."));
	    Assert.assertTrue(emailUtils.isTextInMessage(email1, "Locati=on: Jonas Sports-Plex"));
	    Assert.assertTrue(emailUtils.isTextInMessage(email1, "Class Name: Free Class Auto"));
	    Assert.assertTrue(emailUtils.isTextInMessage(email1, "Class T=ime: 1/14/2020 10:00 AM"));
	   
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
	    Assert.assertTrue(emailMessage2.contains("You have unenrolled from the following  class."));
	    
	    Assert.assertTrue(emailUtils.isTextInMessage(email2, "You have unenrolled from the following  class."));
	    Assert.assertTrue(emailUtils.isTextInMessage(email2, "Location: Jonas Sports-Plex"));
	    Assert.assertTrue(emailUtils.isTextInMessage(email2, "Class Name: Free Class Auto"));
	    Assert.assertTrue(emailUtils.isTextInMessage(email2, "Class Time: 1/14/2020 10:00:00 AM"));
	   
	    } catch (Exception e) {
	      e.printStackTrace();
	      Assert.fail(e.getMessage());
	    }
	    
  }
	  
	 
	
}
