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
<<<<<<< HEAD
		this.sleep_(1000);
		servAnsw.readInputStream();
		
		access.sendUserName(DEFAULT_USER);
		this.sleep_(1000);
		servAnsw.readInputStream();
		
		access.sendPassword(DEFAULT_PASSWORD);
		this.sleep_(1000);
		servAnsw.readInputStream();
		service.sendSystem();
		this.sleep_(1000);
		servAnsw.readInputStream();
		service.sendFEAT();
		this.sleep_(1000);
		servAnsw.readInputStream();
		
		service.sendPrintWorkingdirectory();
		this.sleep_(1000);
		servAnsw.readInputStream();
		
		transfer.sendPASV();
		this.sleep_(1000);
		ServerDataAnswer dataAnsw = new ServerDataAnswer(msgSocket);
=======
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
>>>>>>> e2dbcbd8e345e70b81a3ec868d9f0dd6873be81f
		try {
			dataAnswer.readPasvAnswer();
			System.out.println("IP: " + dataAnswer.getRETURN_IP() 
										+ " und Port: " +dataAnswer.getRETURN_PORT());
		} catch (PassivModeException e) {
			e.printStackTrace();
		}
		service.sendLIST(); 
		this.sleep_(1000);

<<<<<<< HEAD
    	servAnsw.readInputStream();
		DataSocket dataSocket = new DataSocket(dataAnsw.getRETURN_IP(),
				dataAnsw.getRETURN_PORT());
		dataSocket.startSocket();
	//	servAnsw.readInputStream();
=======
    	
		DataSocket dataSocket = new DataSocket(dataAnswer.getRETURN_IP(),
				dataAnswer.getRETURN_PORT());
>>>>>>> e2dbcbd8e345e70b81a3ec868d9f0dd6873be81f
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
