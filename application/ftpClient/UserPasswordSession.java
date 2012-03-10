package application.ftpClient;

import debugging.PassivModeException;
import rfc765.AccessControlCommands;
import rfc765.ServiceCommands;
import rfc765.OptionalCommands;
import rfc765.TransferParameterCommands;
import sockets.DataSocket;
import sockets.MessageSocket;
import sockets.socketMessages.ServerMessagesAnswer;
import sockets.socketMessages.ServerDataAnswer;
import userInterface.FtpServiceComImpl;
import userInterface.UserInterface;

public class UserPasswordSession {
	private MessageSocket msgSocket;

	public UserPasswordSession(String address, int port, String user, String pass) {
		
		loginSession(address,port,user,pass);
	}

	   private void loginSession(String address, int port, String username, String password) {
		


	    	msgSocket = new MessageSocket(address, port);

	    	ServiceCommands service = new ServiceCommands(msgSocket);
	    	AccessControlCommands access = new AccessControlCommands(msgSocket);
	    	TransferParameterCommands transfer = new TransferParameterCommands(msgSocket);
	    	OptionalCommands optional = new OptionalCommands(msgSocket);
	    	ServerMessagesAnswer servAnsw = new ServerMessagesAnswer(msgSocket);

			/*
			 * 		initialize ftp-connection
			*/
	    	msgSocket.startSocket();
	    	access.sendUserName(username);
	    	servAnsw.readInputStream();
	    	access.sendPassword(password);
	    	
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
			ServerDataAnswer dataMsg = new ServerDataAnswer(msgSocket);
			try {
				dataMsg.readPasvAnswer();
			} catch (PassivModeException e) {
				e.printStackTrace();
			}
		
			DataSocket data = new DataSocket(dataMsg.getRETURN_IP(),
					dataMsg.getRETURN_PORT());
			data.startSocket();
	  
	    	
	    	service.sendLIST();  // server sends a port
	    	servAnsw.readInputStream();
	    	//dataMsg.awaitsLISTanswer();
	    	servAnsw.readInputStream();
	    	
	    	FtpServiceComImpl fserver = new FtpServiceComImpl(data, dataMsg, msgSocket);
	    	UserInterface userInterface = new UserInterface(fserver,msgSocket);
	    	userInterface.Interface();
	    	System.out.println("Client: Closing Connection");
	    	msgSocket.closeMessageSocket();
	    	data.closeDataSocket();
	    }

	

}
