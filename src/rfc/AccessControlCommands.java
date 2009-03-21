package rfc;

/**
 * @author Tobias Letschka
 * @see http://www.ietf.org/rfc/rfc959.txt
 * @see RFC 959                                                     October 1985
 * @see File Transfer Protocol
 * 
 *
 */
import inputAndOutput.FtpMessageSocket;
import ftpClient.AnonymousSession;



public class AccessControlCommands {
	private static inputAndOutput.FtpMessageSocket socketMSG = new inputAndOutput.FtpMessageSocket();
	/*
	 * 4.1.1. ACCESS CONTROL COMMANDS 
	 * USER NAME (USER) 
	 * PASSWORD (PASS) 
	 * ACCOUNT (ACCT) 
	 * CHANGE WORKING DIRECTORY (CWD) 
	 * CHANGE TO PARENT DIRECTORY (CDUP)
	 * STRUCTURE MOUNT (SMNT) 
	 * REINITIALIZE (REIN) 
	 * LOGOUT (QUIT)
	 */

	public final static String USERNAME = "USER";
	public final static String PASSWORD = "PASS";
	public final static String ACCOUNT = "ACC";
	public final static String CHANGE_WORK_DIR = "CWD";
	public final static String CHANGE_TO_PARENT_DIR = "CDUP";
	public final static String STRUCTURE_MOUNT = "SMNT";
	public final static String REINITIALIZE = "REIN";
	public final static String LOGOUT = "QUIT";

	/*
	 * following methods implements FtpMessagesSocket function to send
	 * rfc-command to server
	 */

	
	
	/**
	 * send Username to Server about MessagePort / Socket
	 * 
	 */
	public static void sendUserName() {
		
		System.out.println("> USER: " + AnonymousSession.DEFAULT_USER);
		FtpMessageSocket.output().println("USER " + AnonymousSession.DEFAULT_USER);
	}
	
	/**
	 * send Username to Server about MessagePort / Socket
	 * @param username
	 */
	public static void sendUserName(String username) {
		// Send Username to Server
		System.out.println("> USER: " + username);
		FtpMessageSocket.output().println("USER " + username);
	}

	/**
	 * send Password to Server about MessagePort / Socket
	 */
	public static void sendPassword() {
		// Send Password to Server
		System.out.println("> PASS: " + AnonymousSession.DEFAULT_PASSWORD);
		socketMSG.output().println("PASS " + AnonymousSession.DEFAULT_PASSWORD);
	}

	/**
	 * send Password to Server about MessagePort / Socket
	 * @param password
	 */
	public static void sendPassword(String password) {
		// Send Password to Server
		System.out.println("> PASS: " + password);
		socketMSG.output().println("PASS " + password);
	}

	public static void sendAccount() {
		System.out.println("> ACCT");
		socketMSG.output().println("ACCT");
	}

	public static void sendChangeWorkingDirectory() {
		System.out.println("> CWD");
		socketMSG.output().println("CWD");
	}

	public static void sendChangeToParentDirectory() {
		System.out.println("> CDUP");
		socketMSG.output().println("CDUP");
	}

	public static void sendSTructureMount() {
		System.out.println("> SMNT");
		socketMSG.output().println("SMNT");
	}

	public static void sendRenitialize() {
		System.out.println("> REIN");
		socketMSG.output().println("REIN");
	}

	public static void sendLogout() {
		System.out.println("> QUIT");
		socketMSG.output().println("QUIT");
	}
}
