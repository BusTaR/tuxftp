package application.ftpClient;


import rfc765.AccessControlCommands;
import rfc765.ServiceCommands;
import rfc765.OptionalCommands;
import rfc765.TransferParameterCommands;
import sockets.FtpDataSocket;
import sockets.FtpMessageSocket;
import sockets.socketMessages.FtpServerAnswerMessages;
import sockets.socketMessages.FtpServerDataMessages;
import userInterface.FtpServiceComImpl;
import userInterface.UserInterface;

/**
 * @author Tobias Letschka class implements a basic anonymous-session
 * 
 */
public class AnonymousSession {

	// Class variable
	public final static int DEFAULT_DATA_PORT = 20; // for active mode
	public final static int DEFAULT_PORT = 21;
	public final static String DEFAULT_ADDRESS = "ftp2.de.debian.org";
	public final static String DEFAULT_PATH = "/debian";
	public final static String DEFAULT_USER = "anonymous";
	public final static String DEFAULT_PASSWORD = "anonymous@anonymous.de";
	private static FtpMessageSocket msgSocket;

	public AnonymousSession() {
		anonymousSession();
	}

	/**
	 * polymorphy: see also anonymousSession(String address, int port)
	 * a easy to handling methode to build a anonymous connection to an default Ftp-Server
	 */
	private void anonymousSession() {

		anonymousSession(DEFAULT_ADDRESS, DEFAULT_PORT);

	}

	/**
	 * @param address
	 * @param port
	 */
	private void anonymousSession(String address, int port) {

		
		msgSocket = new FtpMessageSocket(address,port);
		msgSocket.startSocket();
		ServiceCommands service = new ServiceCommands(msgSocket);
		TransferParameterCommands transfer = new TransferParameterCommands(msgSocket);
		OptionalCommands optional = new OptionalCommands(msgSocket);
		AccessControlCommands access = new AccessControlCommands(msgSocket);
		FtpServerAnswerMessages servAnsw = new FtpServerAnswerMessages(msgSocket);

		access.sendUserName(DEFAULT_USER);
		servAnsw.readInputStream();
		
		access.sendPassword();
//		try {
//			Thread.sleep(1000); // wait for server answer
//		} catch (InterruptedException e1) {
//
//			e1.printStackTrace();
//		}
		servAnsw.readInputStream();
		service.sendSystem();
		servAnsw.readInputStream();
		service.sendFEAT();
		servAnsw.readInputStream();
		service.sendPrintWorkingdirectory();
		servAnsw.readInputStream();
		transfer.sendPASV();
		FtpServerDataMessages dataMsg = new FtpServerDataMessages(msgSocket);
		dataMsg.readPasvAnswer();
		
		System.out.println("IP: " + dataMsg.getRETURN_IP() 
									+ " und Port: " +dataMsg.getRETURN_PORT());
		FtpDataSocket dataSocket = new FtpDataSocket(dataMsg.getRETURN_IP(),
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
