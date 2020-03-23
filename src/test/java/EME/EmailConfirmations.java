package EME;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
    	Assert.assertTrue(count > 0);
        }
	  
	  
	  @Test(priority=2, description = "Verifies the Class Enrollment confirmation email")
	  public void ClassEnrollmentConfirmation() {
	    try{
	    Message email1 = emailUtils.getMessagesBySubject("Enrollment Notification", true, 6)[0];
	    
	    System.out.println(emailUtils.getMessageContent(email1));
	    String emailMessage1 = emailUtils.getMessageContent(email1);
	    Assert.assertTrue(emailMessage1.contains("You have been successfully enrol=led in the following class."));
	    
	    Assert.assertTrue(emailUtils.isTextInMessage(email1, "You have been successfully enrol=led in the following class."));
	    Assert.assertTrue(emailUtils.isTextInMessage(email1, "Location: =Jonas Sports-Plex"));
	    Assert.assertTrue(emailUtils.isTextInMessage(email1, "Class Name: Free Class Auto"));
	    SimpleDateFormat dateFormat11 = new SimpleDateFormat("M/d/yyyy");
		Calendar today11 = Calendar.getInstance();
		 today11.add(Calendar.DAY_OF_YEAR, 1);
		 String tomorrowsDate = dateFormat11.format(today11.getTime());
		 String classdateAndTime = "Class Time:= " +tomorrowsDate+ " 10:00 AM";
		 System.out.println(classdateAndTime);
	    Assert.assertTrue(emailUtils.isTextInMessage(email1, classdateAndTime));
	   
	    } catch (Exception e) {
	      e.printStackTrace();
	      Assert.fail(e.getMessage());
	    }
	    
  }
	 
	  @Test(priority=3, description = "Verifies the Class Unenrollment confirmation email")
	  public void ClassUnenrollmentConfirmation() {
	    try{
	    Message email2 = emailUtils.getMessagesBySubject("Unenrollment Notification for Member Self-Service", true, 6)[0];
	    
	    System.out.println(emailUtils.getMessageContent(email2));
	    String emailMessage2 = emailUtils.getMessageContent(email2);
	    Assert.assertTrue(emailMessage2.contains("You have unenrolled from the following  class."));
	                                              	    
	    Assert.assertTrue(emailUtils.isTextInMessage(email2, "You have unenrolled from the following  class."));
	    Assert.assertTrue(emailUtils.isTextInMessage(email2, "Location: Jonas Sports-Plex"));
	    Assert.assertTrue(emailUtils.isTextInMessage(email2, "Class Name: Free Class Auto"));
	    SimpleDateFormat dateFormat11 = new SimpleDateFormat("M/d/yyyy");
		Calendar today11 = Calendar.getInstance();
		 today11.add(Calendar.DAY_OF_YEAR, 1);
		 String tomorrowsDate = dateFormat11.format(today11.getTime());
		 String classdateAndTime = "Class Time: " +tomorrowsDate+ " 10:00:00 AM";
		 System.out.println(classdateAndTime);
	    Assert.assertTrue(emailUtils.isTextInMessage(email2, classdateAndTime));
	   
	    } catch (Exception e) {
	      e.printStackTrace();
	      Assert.fail(e.getMessage());
	    }
	    
  }
	  
	  @Test(priority=4, description = "Verifies the Course Enrollment confirmation email")
	  public void CourseEnrollmentConfirmation() {
	    try{
	    Message email3 = emailUtils.getMessagesBySubject("Enrollment Notification", true, 6)[0];
	    
	    System.out.println(emailUtils.getMessageContent(email3));
	    String emailMessage1 = emailUtils.getMessageContent(email3);
	    Assert.assertTrue(emailMessage1.contains("You have been successfully enrol=led in the following class."));
	    
	    Assert.assertTrue(emailUtils.isTextInMessage(email3, "You have been successfully enrol=led in the following class."));
	    Assert.assertTrue(emailUtils.isTextInMessage(email3, "L=ocation: Jonas Sports-Plex"));
	    Assert.assertTrue(emailUtils.isTextInMessage(email3, "Class Name: Free Course Auto"));
	   
	    } catch (Exception e) {
	      e.printStackTrace();
	      Assert.fail(e.getMessage());
	    }
	    
  }
	  
	  @Test(priority=5, description = "Verifies the Course Unenrollment confirmation email")
	  public void CourseUnenrollmentConfirmation() {
	    try{
	    Message email4 = emailUtils.getMessagesBySubject("Unenrollment Notification for Member Self-Service", true, 6)[0];
	    
	    System.out.println(emailUtils.getMessageContent(email4));
	    String emailMessage2 = emailUtils.getMessageContent(email4);
	    Assert.assertTrue(emailMessage2.contains("You have unenrolled from the following  course."));
	    
	    Assert.assertTrue(emailUtils.isTextInMessage(email4, "You have unenrolled from the following  course."));
	    Assert.assertTrue(emailUtils.isTextInMessage(email4, "Location: Jonas Sports-Plex"));
	    Assert.assertTrue(emailUtils.isTextInMessage(email4, "Course Name: Free Course Auto"));
	    Assert.assertTrue(emailUtils.isTextInMessage(email4, "Course Time: 4:30 PM"));
	   
	    } catch (Exception e) {
	      e.printStackTrace();
	      Assert.fail(e.getMessage());
	    }
	    
  }
	  
	  @Test(priority=6, description = "Verifies the Appointment Booking confirmation email")
	  public void BookAppointmentConfirmation() {
	    try{
	    Message email5 = emailUtils.getMessagesBySubject("Appointment Confirmation ", true, 6)[0];
	    
	    System.out.println(emailUtils.getMessageContent(email5));
	    String emailMessage2 = emailUtils.getMessageContent(email5);
	    
	    SimpleDateFormat dateFormat11 = new SimpleDateFormat("EEEE, MMMMM dd, yyyy");
		Calendar today11 = Calendar.getInstance();
		 today11.add(Calendar.DAY_OF_YEAR, 1);
		 String tomorrowsDayAndDate = dateFormat11.format(today11.getTime());
		 System.out.println(tomorrowsDayAndDate);
		 
	    Assert.assertTrue(emailMessage2.contains("Your appointment has been booked= as follows:"));
	    
	    Assert.assertTrue(emailUtils.isTextInMessage(email5, "Your appointment has been booked= as follows:"));
	    Assert.assertTrue(emailUtils.isTextInMessage(email5, "Club: Jonas Fitness"));
	    Assert.assertTrue(emailUtils.isTextInMessage(email5, "Participant(s): Auto, Emailmember"));
	    Assert.assertTrue(emailUtils.isTextInMessage(email5, "Books: PT.Shepard, Elli=ana; FitExpert2; |Gym"));
	    Assert.assertTrue(emailUtils.isTextInMessage(email5, "Description: PTServiceWith3Resourc=es"));
	    Assert.assertTrue(emailUtils.isTextInMessage(email5, "Date: " + tomorrowsDayAndDate ));
	    Assert.assertTrue(emailUtils.isTextInMessage(email5, "Duratio=n: 0 Hour  15 Minute"));
	    Assert.assertTrue(emailUtils.isTextInMessage(email5, "Time: 5:00 AM"));
	    
	   
	    } catch (Exception e) {
	      e.printStackTrace();
	      Assert.fail(e.getMessage());
	    }
	    
  }
	  @Test(priority=7, description = "Verifies the Appointment Cancellation email")
	  public void CancelApointmentConfirmation() {
	    try{
	    Message email6 = emailUtils.getMessagesBySubject("Cancellation of Appointment Confirmation", true, 6)[0];
	    
	    System.out.println(emailUtils.getMessageContent(email6));
	    String emailMessage2 = emailUtils.getMessageContent(email6);
	    
	    SimpleDateFormat dateFormat11 = new SimpleDateFormat("EEEE, MMMMM dd, yyyy");
		Calendar today11 = Calendar.getInstance();
		 today11.add(Calendar.DAY_OF_YEAR, 1);
		 String tomorrowsDayAndDate = dateFormat11.format(today11.getTime());
		 System.out.println(tomorrowsDayAndDate);
	    Assert.assertTrue(emailMessage2.contains("Your appointment for the followi=ng has been canceled:"));
	    
	    Assert.assertTrue(emailUtils.isTextInMessage(email6, "Your appointment for the followi=ng has been canceled:"));
	    Assert.assertTrue(emailUtils.isTextInMessage(email6, "Description: PTServiceWith3Resourc=es"));
	    Assert.assertTrue(emailUtils.isTextInMessage(email6, "Club: Jonas Fitness"));
	    Assert.assertTrue(emailUtils.isTextInMessage(email6, "Date: " + tomorrowsDayAndDate ));
	    Assert.assertTrue(emailUtils.isTextInMessage(email6, "Ti=me: 5:00 AM"));
	    Assert.assertTrue(emailUtils.isTextInMessage(email6, "Duration: 15 Minute"));
	   
	    } catch (Exception e) {
	      e.printStackTrace();
	      Assert.fail(e.getMessage());
	    }
	    
  }
	  
	 
	
}
