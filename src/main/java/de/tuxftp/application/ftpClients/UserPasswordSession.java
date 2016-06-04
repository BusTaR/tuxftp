package de.tuxftp.application.ftpClients;

import de.tuxftp.debugging.PassivModeException;
import de.tuxftp.rfc765.AccessControlCommands;
import de.tuxftp.rfc765.OptionalCommands;
import de.tuxftp.rfc765.ServiceCommands;
import de.tuxftp.rfc765.TransferParameterCommands;
import de.tuxftp.sockets.DataSocket;
import de.tuxftp.sockets.MessageSocket;
import de.tuxftp.sockets.socketMessages.ServerDataAnswer;
import de.tuxftp.sockets.socketMessages.ServerMessagesAnswer;
import de.tuxftp.userInterface.FtpServiceComImpl;
import de.tuxftp.userInterface.UserInterface;

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
