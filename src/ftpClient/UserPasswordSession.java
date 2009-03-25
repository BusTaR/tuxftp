package ftpClient;

import rfc.AccessControlCommands;
import rfc.FtpServiceCommands;
import rfc.OptionalCommands;
import rfc.TransferParameterCommands;
import socketMessages.FtpServerAnswerMessages;
import socketMessages.FtpServerDataMessages;
import sockets.FtpDataSocket;
import sockets.FtpMessageSocket;
import userInterface.UserInterface;

public class UserPasswordSession implements Runnable {
	private static boolean threadRunning = true;
	private static FtpMessageSocket message = new FtpMessageSocket();

	@Override
	public void run() {
		loginSession("pr0-talk.de",21,"web16","XGT3GEmY");
		
		
	}
	   private static void loginSession(String address, int port, String username, String password) {

	    	UserInterface userInterface = new UserInterface();
	    	AccessControlCommands access = new AccessControlCommands();
	    	TransferParameterCommands transfer = new TransferParameterCommands();
	    	OptionalCommands optional = new OptionalCommands();
	    	FtpServiceCommands service = new FtpServiceCommands();
	    	message.startMessageSocket(address, port);
	    	
	    	
	    	access.sendUserName(username);
	    	socketMessages.FtpServerAnswerMessages.readInputStream();
	    	access.sendPassword(password);
	    	try {
				Thread.sleep(1000);  // wait for server answere
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	      	socketMessages.FtpServerAnswerMessages.readInputStream();
	      	service.sendSystem();
	    	socketMessages.FtpServerAnswerMessages.readInputStream();
	    	FtpServiceCommands.sendFEAT();
	    	try {
				Thread.sleep(1000);  // wait for server answere
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	socketMessages.FtpServerAnswerMessages.readInputStream();
	    	service.sendPrintWorkingdirectory();
	    	socketMessages.FtpServerAnswerMessages.readInputStream();
	    	transfer.sendPASV();
	    	socketMessages.FtpServerDataMessages.readPasvAnswer();
	    	FtpDataSocket data = new FtpDataSocket();
	    	data.startDataSocket(FtpServerDataMessages.getRETURN_IP(), 
	    							FtpServerDataMessages.getRETURN_PORT());
	    	
	    	service.sendLIST();  // server sends a port
	    	socketMessages.FtpServerAnswerMessages.readInputStream();
	    	FtpServerDataMessages dataMsg = new FtpServerDataMessages();
	    	dataMsg.awaitsLISTanswer();
	    	socketMessages.FtpServerAnswerMessages.readInputStream();
	    	data.closeDataSocket();
	    	userInterface.Interface();
	    	System.out.println("Client: Closing Connection");
	    	AnonymousSession.killClientThread();
	    	FtpMessageSocket.closeMessageSocket();
	    	killClientThread();
	    	
	    }
		public static void killClientThread() {  // stopping thread
			threadRunning = false;
		} 
	

}
