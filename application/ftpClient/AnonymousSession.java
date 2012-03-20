package application.ftpClient;

import debugging.PassivModeException;
import rfc765.AccessControlCommands;
import rfc765.ServiceCommands;
import rfc765.TransferParameterCommands;
import sockets.DataSocket;
import sockets.MessageSocket;
import sockets.socketMessages.ServerDatas;
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
		ServerMessagesAnswer servAnswer = new ServerMessagesAnswer(msgSocket);
		
		/*
		 * 		initialize ftp-connection
		*/
		msgSocket.startSocket();
    	try {
			Thread.sleep(1000);  // wait for server answere
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		servAnswer.readInputStream();
		
		access.sendUserName(DEFAULT_USER);
    	try {
			Thread.sleep(1000);  // wait for server answere
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		servAnswer.readInputStream();
		
		access.sendPassword(DEFAULT_PASSWORD);
    	try {
			Thread.sleep(1000);  // wait for server answere
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		servAnswer.readInputStream();
		service.sendSystem();
    	try {
			Thread.sleep(1000);  // wait for server answere
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		servAnswer.readInputStream();
		service.sendFEAT();
    	try {
			Thread.sleep(1000);  // wait for server answere
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		servAnswer.readInputStream();
		
		service.sendPrintWorkingdirectory();
    	try {
			Thread.sleep(1000);  // wait for server answere
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		servAnswer.readInputStream();
		
		transfer.sendPASV();
    	try {
			Thread.sleep(1000);  // wait for server answere
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		ServerDataAnswer dataAnswer = new ServerDataAnswer(msgSocket);
		try {
			dataAnswer.readPasvAnswer();
			System.out.println("IP: " + dataAnswer.getRETURN_IP() 
										+ " und Port: " +dataAnswer.getRETURN_PORT());
		} catch (PassivModeException e) {
			e.printStackTrace();
		}
		service.sendLIST(); 
    	try {
			Thread.sleep(1000);  // wait for server answere
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

    	
		DataSocket dataSocket = new DataSocket(dataAnswer.getRETURN_IP(),
				dataAnswer.getRETURN_PORT());
		ServerDatas servDatas = new ServerDatas(dataSocket);
		
		dataSocket.startSocket();
    	try {
			Thread.sleep(1000);  // wait for server answere
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		servAnswer.readInputStream();
    	try {
			Thread.sleep(1000);  // wait for server answere
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		servDatas.awaitsLISTanswer();
		servAnswer.readInputStream();
		

	//	FtpServiceComImpl fserver = new FtpServiceComImpl(dataSocket, dataAnsw, msgSocket);
	//	UserInterface userInterface = new UserInterface(fserver, msgSocket);
	//	userInterface.Interface();
		System.out.println("Client: Closing Connection");
		msgSocket.closeMessageSocket();
		dataSocket.closeDataSocket();
	}



}
