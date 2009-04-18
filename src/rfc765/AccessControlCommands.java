package rfc765;
import sockets.FtpMessageSocket;
import ftpClient.AnonymousSession;

/**
 * @author Tobias Letschka
 * @see http://www.ietf.org/rfc/rfc959.txt
 * @see RFC 959                                                     October 1985
 * @see File Transfer Protocol
 * 
 *
 */

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

public class AccessControlCommands {


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
		FtpMessageSocket.output().println(
				"USER " + AnonymousSession.DEFAULT_USER);
	}

	/**
	 * send Username to Server about MessagePort / Socket
	 * 
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
		FtpMessageSocket.output().println(
				"PASS " + AnonymousSession.DEFAULT_PASSWORD);
	}

	/**
	 * send Password to Server about MessagePort / Socket
	 * 
	 * @param password
	 */
	public static void sendPassword(String password) {
		// Send Password to Server
		System.out.println("> PASS: " + password);
		FtpMessageSocket.output().println("PASS " + password);
	}

	/**
	 * 
	 */
	public static void sendAccount() {
		System.out.println("> ACCT");
		FtpMessageSocket.output().println("ACCT");
	}

	/**
	 * 
	 */
	public static void sendChangeWorkingDirectory() {
		System.out.println("> CWD");
		FtpMessageSocket.output().println("CWD");
	}

	/**
	 * 
	 */
	public static void sendChangeToParentDirectory() {
		System.out.println("> CDUP");
		FtpMessageSocket.output().println("CDUP");
	}

	/**
	 * 
	 */
	public static void sendSTructureMount() {
		System.out.println("> SMNT");
		FtpMessageSocket.output().println("SMNT");
	}

	/**
	 * This command terminates a USER, flushing all I/O and account information,
	 * except to allow any transfer in progress to be completed. All parameters
	 * are reset to the default settings and the control connection is left
	 * open. This is identical to the state in which a user finds himself
	 * immediately after the control connection is opened. A USER command may be
	 * expected to follow.
	 */
	public static void sendRenitialize() {
		System.out.println("> REIN");
		FtpMessageSocket.output().println("REIN");
	}

	/**
	 *      This command terminates a USER and if file transfer is not
	 *      in progress, the server closes the control connection.  If
     *      file transfer is in progress, the connection will remain
     *      open for result response and the server will then close it.
     *      If the user-process is transferring files for several USERs
     *      but does not wish to close and then reopen connections for
     *      each, then the REIN command should be used instead of QUIT.

     *      An unexpected close on the control connection will cause the
     *      server to take the effective action of an abort (ABOR) and a
     *       logout (QUIT).
	 */
	public static void sendLogout() {
		System.out.println("> QUIT");
		FtpMessageSocket.output().println("QUIT");
	}
}
