package ftpClient;


import rfc765.AccessControlCommands;
import rfc765.FtpServiceCommands;
import rfc765.OptionalCommands;
import rfc765.TransferParameterCommands;
import socketMessages.FtpServerAnswerMessages;
import socketMessages.FtpServerDataMessages;
import sockets.FtpDataSocket;
import sockets.FtpMessageSocket;
import userInterface.UserInterface;

/**
 * @author Tobias Letschka class implements a basic anonymous-session
 * 
 */
public class AnonymousSession implements Runnable {

	// Class variable
	public final static int DEFAULT_DATA_PORT = 20; // for active mode
	public final static int DEFAULT_PORT = 21;
	public final static String DEFAULT_ADDRESS = "ftp2.de.debian.org";
	public final static String DEFAULT_PATH = "/debian";
	public final static String DEFAULT_USER = "anonymous";
	public final static String DEFAULT_PASSWORD = "anonymous@anonymous.de";

	private static FtpServerAnswerMessages messages = new FtpServerAnswerMessages();
	private static FtpMessageSocket client = new FtpMessageSocket();
	private static boolean threadRunning = true; // terminate thread

	// MasterThread
	public void run() {

		anonymousSession();

	}

	/**
	 * polymorphy: see also anonymousSession(String address, int port)
	 * a easy to handling methode to build a anonymous connection to an default Ftp-Server
	 */
	private static void anonymousSession() {

		anonymousSession(DEFAULT_ADDRESS, DEFAULT_PORT);

	}

	/**
	 * @param address
	 * @param port
	 */
	private static void anonymousSession(String address, int port) {
		TransferParameterCommands transfer = new TransferParameterCommands();
		OptionalCommands optional = new OptionalCommands();
		FtpServiceCommands service = new FtpServiceCommands();
		client.startMessageSocket(address, port);

		AccessControlCommands access = new AccessControlCommands();
		access.sendUserName();
		socketMessages.FtpServerAnswerMessages.readInputStream();
		access.sendPassword();
		try {
			Thread.sleep(1000); // wait for server answer
		} catch (InterruptedException e1) {

			e1.printStackTrace();
		}
		socketMessages.FtpServerAnswerMessages.readInputStream();
		service.sendSystem();
		socketMessages.FtpServerAnswerMessages.readInputStream();
		FtpServiceCommands.sendFEAT();
		try {
			Thread.sleep(1000); // wait for server answere
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		socketMessages.FtpServerAnswerMessages.readInputStream();
		service.sendPrintWorkingdirectory();
		socketMessages.FtpServerAnswerMessages.readInputStream();
		transfer.sendPASV();
		socketMessages.FtpServerDataMessages.readPasvAnswer();
		FtpDataSocket data = new FtpDataSocket();
		data.startDataSocket(FtpServerDataMessages.getRETURN_IP(),
				FtpServerDataMessages.getRETURN_PORT());

		service.sendLIST(); // server sends a port
		socketMessages.FtpServerAnswerMessages.readInputStream();
		FtpServerDataMessages dataMsg = new FtpServerDataMessages();
		dataMsg.awaitsLISTanswer();
		socketMessages.FtpServerAnswerMessages.readInputStream();
		data.closeDataSocket();

		UserInterface userInterface = new UserInterface();
		userInterface.Interface();
		System.out.println("Client: Closing Connection");
		AnonymousSession.killClientThread();
		FtpMessageSocket.closeMessageSocket();
	}

	public static void killClientThread() { // stopping thread
		threadRunning = false;
	}

}
