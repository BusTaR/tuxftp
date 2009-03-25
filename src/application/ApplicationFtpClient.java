package application;

import socketMessages.FtpServerAnswerMessages;
import ftpClient.AnonymousSession;
import ftpClient.UserPasswordSession;

public class ApplicationFtpClient {


	private static UserPasswordSession userpass = new UserPasswordSession();
	private static Thread userpassThread = new Thread(userpass);
	
	private static FtpServerAnswerMessages ftpAnswere = new FtpServerAnswerMessages();
	private static Thread ftpAnswereThread = new Thread(ftpAnswere);
	

	

	public static void main(String[] args) {

		
		userpassThread.start();
	
	//	client.killClientThread(false);

		//	ftpClientThread.join();    // auf ende warten

    	
	}
	void testForServerWithAnonyoumsAccount() {
		 AnonymousSession anonymous = new AnonymousSession();  // get Socket 						
		 Thread anonymousThread = new Thread(anonymous);  // establish a server connection
		//anonymousThread.start(); 	// start Thread
	}

}
