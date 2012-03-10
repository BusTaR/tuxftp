package application.ftpClient;

import debugging.PassivModeException;
import rfc765.AccessControlCommands;
import rfc765.ServiceCommands;
import rfc765.TransferParameterCommands;
import sockets.DataSocket;
import sockets.MessageSocket;
import sockets.socketMessages.ServerMessagesAnswer;
import sockets.socketMessages.ServerDataAnswer;
import userInterface.FtpServiceComImpl;
import userInterface.UserInterface;

/**
 * @author Tobias Letschka class implements a basic anonymous-session
 * 
 */
public class AnonymousSession {

	// Class variable
	public final static int DEFAULT_DATA_PORT = 20; // for active mode

	public final static String DEFAULT_PATH = "/debian";
	public final static String DEFAULT_USER = "anonymous";
	public final static String DEFAULT_PASSWORD = "anonymous@anonymous.de";
	private static MessageSocket msgSocket;

	public AnonymousSession(String address, int port) {
		anonymousSession(address,port);
	}

	/**
	 * @param address
	 * @param port
	 */
	private void anonymousSession(String address, int port) {

		
		msgSocket = new MessageSocket(address,port);
		ServiceCommands service = new ServiceCommands(msgSocket);
		TransferParameterCommands transfer = new TransferParameterCommands(msgSocket);
		//OptionalCommands optional = new OptionalCommands(msgSocket);
		AccessControlCommands access = new AccessControlCommands(msgSocket);
		ServerMessagesAnswer servAnsw = new ServerMessagesAnswer(msgSocket);
		
		/*
		 * 		initialize ftp-connection
		*/
		msgSocket.startSocket();
		access.sendUserName(DEFAULT_USER);
		servAnsw.readInputStream();
		
		access.sendPassword(DEFAULT_PASSWORD);
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
		
		System.out.println("IP: " + dataMsg.getRETURN_IP() 
									+ " und Port: " +dataMsg.getRETURN_PORT());
		DataSocket dataSocket = new DataSocket(dataMsg.getRETURN_IP(),
				dataMsg.getRETURN_PORT());
		dataSocket.startSocket();
	
	

		service.sendLIST(); // server sends a port
		servAnsw.readInputStream();
		//dataMsg.awaitsLISTanswer();
		servAnsw.readInputStream();
		

		FtpServiceComImpl fserver = new FtpServiceComImpl(dataSocket, dataMsg, msgSocket);
		UserInterface userInterface = new UserInterface(fserver, msgSocket);
		userInterface.Interface();
		System.out.println("Client: Closing Connection");
		msgSocket.closeMessageSocket();
		dataSocket.closeDataSocket();
	}



}
