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
		ServerMessagesAnswer servAnsw = new ServerMessagesAnswer(msgSocket);
		
		/*
		 * 		initialize ftp-connection
		*/
		msgSocket.startSocket();
    	try {
			Thread.sleep(1000);  // wait for server answere
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		servAnsw.readInputStream();
		
		access.sendUserName(DEFAULT_USER);
    	try {
			Thread.sleep(1000);  // wait for server answere
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		servAnsw.readInputStream();
		
		access.sendPassword(DEFAULT_PASSWORD);
    	try {
			Thread.sleep(1000);  // wait for server answere
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		servAnsw.readInputStream();
		service.sendSystem();
    	try {
			Thread.sleep(1000);  // wait for server answere
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		servAnsw.readInputStream();
		service.sendFEAT();
    	try {
			Thread.sleep(1000);  // wait for server answere
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		servAnsw.readInputStream();
		
		service.sendPrintWorkingdirectory();
    	try {
			Thread.sleep(1000);  // wait for server answere
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		servAnsw.readInputStream();
		
		transfer.sendPASV();
    	try {
			Thread.sleep(1000);  // wait for server answere
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		ServerDataAnswer dataAnsw = new ServerDataAnswer(msgSocket);
		try {
			dataAnsw.readPasvAnswer();
		} catch (PassivModeException e) {
			e.printStackTrace();
		}
		service.sendLIST(); 
    	try {
			Thread.sleep(1000);  // wait for server answere
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		servAnsw.readInputStream();
		System.out.println("IP: " + dataAnsw.getRETURN_IP() 
									+ " und Port: " +dataAnsw.getRETURN_PORT());
		DataSocket dataSocket = new DataSocket(dataAnsw.getRETURN_IP(),
				dataAnsw.getRETURN_PORT());
		dataSocket.startSocket();
		ServerDatas servDatas = new ServerDatas(dataSocket);
	
		servDatas.awaitsLISTanswer();
		servAnsw.readInputStream();
		

	//	FtpServiceComImpl fserver = new FtpServiceComImpl(dataSocket, dataAnsw, msgSocket);
	//	UserInterface userInterface = new UserInterface(fserver, msgSocket);
	//	userInterface.Interface();
		System.out.println("Client: Closing Connection");
		msgSocket.closeMessageSocket();
		dataSocket.closeDataSocket();
	}



}
