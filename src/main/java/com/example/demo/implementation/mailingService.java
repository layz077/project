package com.example.demo.implementation;

import java.net.PasswordAuthentication;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class mailingService {


	private static final String companyEmail = "****";
	private static final String password = "****";
	private static final String host="smtp.gmail.com";
	private static final String port="465";
	private String subject;
	private String message;
	private static final Logger logger = Logger.getLogger(mailingService.class);

		/*TYPE
		 * registration
		 * login
		 * or any other*/
	
	public void sendMail(String to,String name ,String userName, String type,HttpServletRequest request){
		
	          Properties properties = System.getProperties();
	          
	          properties.setProperty("mail.smtp.host", host);
	          properties.setProperty("mail.smtp.port", port);
	          properties.setProperty("mail.smtp.ssl.enable", "true");
	          properties.setProperty("mail.smtp.auth", "true");
	          
	          if(type.equalsIgnoreCase("register")) {
	        	  subject = "Welcome to our platform, " + name;
	        	  message = "Thank you for considering our platform.Your username is "+ userName +".\n If you think this mail is a mistake please click on the below link to deregister your mail.";
	          }
	          
	          else if(type.equalsIgnoreCase("login")) {
	        	  subject = "***New Sign In***";
	        	  message = "There is a new sign in from ip " + request.getRemoteAddr()+".\nIf not you please change the password";
	          }
	          
	          else if(type.equalsIgnoreCase("update")) {
	        	  subject = "Profile Updated Successfully";
	        	  message = "Your profile was updated successfully. If not done by you, to revert these changes click the below link";
	          }
	          
	          else if(type.equalsIgnoreCase("password")) {
	        	  subject = "Password Changed successfully";
	        	  message = "Your profile password is changed successfully. If not done by you, to revert these changes click the below link";
	          }
	          else if(type.equalsIgnoreCase("deletion warning")) {
	        	  subject = "DAILYSHOTS: END IS NEAR";
	        	  message = "You have only one day before your account is permanently deleted.Click on the below link to reactivate your acount.\n"
	        	  		  + "http://localhost:8080/recoverAccount/" + userName;
	        	  
	          }
	          
	          else if(type.equalsIgnoreCase("delete")) {
	        	  subject = "ACCOUNT DELETED PERMANENTLY";
	        	  message = "Sorry to see you go :( . Your details are now deleted from our server."
	        			  +"You can use same details to sign up again";
	          }
			  else if(type.equalsIgnoreCase("login recover")) {
				  subject = "Account recovered successfully";
				  message = "Welcome back to our platform all your services are restored.";
			  }
	         
	          Session session = Session.getInstance(properties, new Authenticator() {
			
	        	  @Override
	        	    public javax.mail.PasswordAuthentication getPasswordAuthentication() {
	        	    	return new javax.mail.PasswordAuthentication(companyEmail, password);
	        	    }
	          });
	          
	          MimeMessage mimeMessage = new MimeMessage(session);
	          
	          try {
				mimeMessage.setFrom(companyEmail);
				mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
				mimeMessage.setSubject(subject);
				mimeMessage.setText(message);
				
				Transport.send(mimeMessage);
				logger.info("Email sent");
								
				
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	         
	}
	
}
