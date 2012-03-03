package application.ftpClient;

import rfc765.AccessControlCommands;
import rfc765.FtpServiceCommands;
import rfc765.OptionalCommands;
import rfc765.TransferParameterCommands;
import socketMessages.FtpServerAnswerMessages;
import socketMessages.FtpServerDataMessages;
import sockets.FtpDataSocket;
import sockets.FtpMessageSocket;
import userInterface.FtpServiceComImpl;
import userInterface.UserInterface;

public class UserPasswordSession {
	private static FtpMessageSocket msgSocket;

	public UserPasswordSession(String address, int port, String user, String pass) {
		
		loginSession(address,port,user,pass);
	}

	   private static void loginSession(String address, int port, String username, String password) {
		


	    	msgSocket = new FtpMessageSocket(address, port);
	    	msgSocket.startMessageSocket();
	    	FtpServiceCommands service = new FtpServiceCommands(msgSocket);
	    	AccessControlCommands access = new AccessControlCommands();
	    	TransferParameterCommands transfer = new TransferParameterCommands(msgSocket);
	    	OptionalCommands optional = new OptionalCommands(msgSocket);
	    	FtpServerAnswerMessages servAnsw = new FtpServerAnswerMessages(msgSocket);
	    	
	    	
	    	access.sendUserName(username);
	    	servAnsw.readInputStream();
	    	access.sendPassword(password);
	    	try {
				Thread.sleep(1000);  // wait for server answere
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
	    	servAnsw.readInputStream();
	      	service.sendSystem();
	      	servAnsw.readInputStream();
	    	service.sendFEAT();
	    	try {
				Thread.sleep(1000);  // wait for server answere
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
	    	servAnsw.readInputStream();
	    	service.sendPrintWorkingdirectory();
	    	servAnsw.readInputStream();
	    	transfer.sendPASV();
	    	socketMessages.FtpServerDataMessages.readPasvAnswer();
	    	FtpDataSocket data = new FtpDataSocket(FtpServerDataMessages.getRETURN_IP(), 
					FtpServerDataMessages.getRETURN_PORT());
	   
	    	
	    	service.sendLIST();  // server sends a port
	    	servAnsw.readInputStream();
	    	FtpServerDataMessages dataMsg = new FtpServerDataMessages(data, msgSocket);
	    	dataMsg.awaitsLISTanswer();
	    	servAnsw.readInputStream();
	    	data.closeDataSocket();
	    	FtpServiceComImpl fserver = new FtpServiceComImpl(data, dataMsg, msgSocket);
	    	UserInterface userInterface = new UserInterface(fserver);
	    	userInterface.Interface();
	    	System.out.println("Client: Closing Connection");
	    	FtpMessageSocket.closeMessageSocket();
	    	
	    }

	

}
