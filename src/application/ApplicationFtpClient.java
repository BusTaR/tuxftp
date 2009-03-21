package application;

import inputAndOutput.FtpServerAnswerMessages;
import ftpClient.AnonymousSession;
import ftpClient.UserPasswordSession;

public class ApplicationFtpClient {


	static UserPasswordSession userpass = new UserPasswordSession();
	static Thread userpassThread = new Thread(userpass);
	
	public static FtpServerAnswerMessages ftpAnswere = new FtpServerAnswerMessages();
	public static Thread ftpAnswereThread = new Thread(ftpAnswere);
	

	

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
