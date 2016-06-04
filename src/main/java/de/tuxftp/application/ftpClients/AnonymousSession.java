package de.tuxftp.application.ftpClients;

import de.tuxftp.debugging.PassivModeException;
import de.tuxftp.rfc765.AccessControlCommands;
import de.tuxftp.rfc765.ServiceCommands;
import de.tuxftp.rfc765.TransferParameterCommands;
import de.tuxftp.sockets.DataSocket;
import de.tuxftp.sockets.MessageSocket;
import de.tuxftp.sockets.socketMessages.ServerDataAnswer;
import de.tuxftp.sockets.socketMessages.ServerDatas;
import de.tuxftp.sockets.socketMessages.ServerMessagesAnswer;

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
	private static MessageSocket msgSocket = null;
	private ServerMessagesAnswer servAnswer = null;
	private AccessControlCommands access = null;
	private ServiceCommands service = null;
	private TransferParameterCommands transfer = null;
	private DataSocket dataSocket = null;
	
	public AnonymousSession(String address, int port) {
		anonymousSession(address,port);
	}

	/**
	 * @param address
	 * @param port
	 */
	private void anonymousSession(String address, int port) {

		
		msgSocket = new MessageSocket(address,port);
		service = new ServiceCommands(msgSocket);
		transfer = new TransferParameterCommands(msgSocket);
		//OptionalCommands optional = new OptionalCommands(msgSocket);
		access = new AccessControlCommands(msgSocket);
		servAnswer = new ServerMessagesAnswer(msgSocket);
		
		/*
		 * 		initialize ftp-connection
		*/
		msgSocket.startSocket();
		this.sleep_(1000);
		servAnswer.readInputStream();
		
		this.initializeConnection();
		this.initializePasMode();
		

	//	FtpServiceComImpl fserver = new FtpServiceComImpl(dataSocket, dataAnsw, msgSocket);
	//	UserInterface userInterface = new UserInterface(fserver, msgSocket);
	//	userInterface.Interface();
		System.out.println("Client: Closing Connection");
		msgSocket.closeMessageSocket();
		dataSocket.closeDataSocket();
	}
	private void initializeConnection() {
		access.sendUserName(DEFAULT_USER);
		this.sleep_(1000);
		servAnswer.readInputStream();
		
		access.sendPassword(DEFAULT_PASSWORD);
		this.sleep_(1000);
		servAnswer.readInputStream();
		
		service.sendSystem();
		this.sleep_(1000);
		servAnswer.readInputStream();
		
		service.sendFEAT();
		this.sleep_(1000);
		servAnswer.readInputStream();
		
		service.sendPrintWorkingdirectory();
		this.sleep_(1000);
		servAnswer.readInputStream();
		
		transfer.sendPASV();
		this.sleep_(1000);
		servAnswer.readInputStream();

	}
	
	private void initializePasMode() {
		transfer.sendPASV();
		this.sleep_(1000);
		
		ServerDataAnswer dataAnswer = new ServerDataAnswer(msgSocket);

		try {
			dataAnswer.readPasvAnswer();
			System.out.println("IP: " + dataAnswer.getRETURN_IP() 
										+ " und Port: " +dataAnswer.getRETURN_PORT());
		} catch (PassivModeException e) {
			e.printStackTrace();
		}
		service.sendLIST(); 
		this.sleep_(1000);


    	servAnswer.readInputStream();
		dataSocket = new DataSocket(dataAnswer.getRETURN_IP(),
				dataAnswer.getRETURN_PORT());
		dataSocket.startSocket();
	//	servAnsw.readInputStream();

		ServerDatas servDatas = new ServerDatas(dataSocket);
		
		
		this.sleep_(1000);
		servAnswer.readInputStream();
		this.sleep_(1000);
		servDatas.awaitsLISTanswer();
		servAnswer.readInputStream();
	}
	
	private void sleep(int t) { 
		int end = (int) (System.currentTimeMillis() + t); 
		while (System.currentTimeMillis() < end) { 
			try { 
				Thread.sleep(t); 
				} catch (InterruptedException e) {
					e.getStackTrace();
				} 
			} 
		}

	private void sleep_(int t) { 
		try { 
			Thread.sleep(t); 
			} catch (InterruptedException e) {
				e.getStackTrace();
			} 
		}
}
