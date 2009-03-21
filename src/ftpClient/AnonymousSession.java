package ftpClient;

import inputAndOutput.FtpDataSocket;
import inputAndOutput.FtpMessageSocket;
import inputAndOutput.FtpServerAnswerMessages;
import inputAndOutput.FtpServerDataMessages;

import java.io.*;
import java.net.*;
import java.nio.channels.SocketChannel;



import rfc.AccessControlCommands;
import rfc.FtpServiceCommands;
import rfc.OptionalCommands;
import rfc.TransferParameterCommands;
import trash.FG_FtpServer;
import userInterface.UserInterface;

// FuckingGeniusFtpClient; FGFtpClient

public class AnonymousSession implements Runnable {
	
	
	// Class variable
	public final static int DEFAULT_DATA_PORT = 20;  // for active mode
	public final static int DEFAULT_PORT = 21;
	public final static String DEFAULT_ADDRESS = "ftp2.de.debian.org";
	public final static String DEFAULT_PATH = "/debian";
	public final static String DEFAULT_USER = "anonymous";
	public final static String DEFAULT_PASSWORD = "anonymous@anonymous.de";

	private static FtpServerAnswerMessages messages = new FtpServerAnswerMessages();
	private static Thread ftpServerMSGThread = new Thread(messages);
	private static FtpMessageSocket client = new FtpMessageSocket();
	private static FtpDataSocket data = new FtpDataSocket();
    private ThreadControll controll = new ThreadControll();  // Semaphore
    private static boolean threadRunning = true;

    
    
    

    
    // MasterThread 
    public void run () {
    	
    	anonymousSession();
    	
    }
    private static void anonymousSession() {

    	UserInterface userInterface = new UserInterface();
    	AccessControlCommands access = new AccessControlCommands();
    	TransferParameterCommands transfer = new TransferParameterCommands();
    	OptionalCommands optional = new OptionalCommands();
    	FtpServiceCommands service = new FtpServiceCommands();
    	client.startMessageSocket(DEFAULT_ADDRESS, DEFAULT_PORT);
    	//SocketChannel neu = client.startMessageSocket().getChannel();
    	
    	access.sendUserName();
    	inputAndOutput.FtpServerAnswerMessages.readInputStream();
    	access.sendPassword();
    	try {
			Thread.sleep(1000);  // wait for server answere
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
      	inputAndOutput.FtpServerAnswerMessages.readInputStream();
      	service.sendSystem();
    	inputAndOutput.FtpServerAnswerMessages.readInputStream();
    	FtpServiceCommands.sendFEAT();
    	try {
			Thread.sleep(1000);  // wait for server answere
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	inputAndOutput.FtpServerAnswerMessages.readInputStream();
    	service.sendPrintWorkingdirectory();
    	inputAndOutput.FtpServerAnswerMessages.readInputStream();
    	transfer.sendPASV();
    	inputAndOutput.FtpServerAnswerMessages.readInputStream();
    	FtpDataSocket data = new FtpDataSocket();
    	data.startDataSocket(FtpServerAnswerMessages.getRETURN_IP(), 
    							FtpServerAnswerMessages.getRETURN_PORT());
    	
    	service.sendLIST();  // server sends a port
    	inputAndOutput.FtpServerAnswerMessages.readInputStream();
    	FtpServerDataMessages dataMsg = new FtpServerDataMessages();
    	dataMsg.awaitsLISTanswer();
    	inputAndOutput.FtpServerAnswerMessages.readInputStream();
    	data.closeDataSocket();
    	userInterface.Interface();
    	System.out.println("Client: Closing Connection");
    	AnonymousSession.killClientThread();
    	FtpMessageSocket.closeMessageSocket();
    	
    }
 
	public static void killClientThread() {  // stopping thread
		threadRunning = false;
	} 
  
 
  
}

