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
		msgSocket.startMessageSocket();
		FtpServiceCommands service = new FtpServiceCommands(msgSocket);
		TransferParameterCommands transfer = new TransferParameterCommands(msgSocket);
		OptionalCommands optional = new OptionalCommands(msgSocket);
		AccessControlCommands access = new AccessControlCommands();
		FtpServerAnswerMessages servAnsw = new FtpServerAnswerMessages(msgSocket);
		
		access.sendUserName();
		servAnsw.readInputStream();
		
		access.sendPassword();
		try {
			Thread.sleep(1000); // wait for server answer
		} catch (InterruptedException e1) {

			e1.printStackTrace();
		}
		servAnsw.readInputStream();
		service.sendSystem();
		servAnsw.readInputStream();
		service.sendFEAT();
		try {
			Thread.sleep(1000); // wait for server answere
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		servAnsw.readInputStream();
		service.sendPrintWorkingdirectory();
		servAnsw.readInputStream();
		transfer.sendPASV();
		socketMessages.FtpServerDataMessages.readPasvAnswer();
		FtpDataSocket data = new FtpDataSocket(FtpServerDataMessages.getRETURN_IP(),
				FtpServerDataMessages.getRETURN_PORT());
	

		service.sendLIST(); // server sends a port
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
