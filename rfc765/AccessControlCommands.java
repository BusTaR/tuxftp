package rfc765;
import sockets.MessageSocket;

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


	private final static String USERNAME = "USER";
	private final static String PASSWORD = "PASS";
	private final static String ACCOUNT = "ACC";
	private final static String CHANGE_WORK_DIR = "CWD";
	private final static String CHANGE_TO_PARENT_DIR = "CDUP";
	private final static String STRUCTURE_MOUNT = "SMNT";
	private final static String REINITIALIZE = "REIN";
	private final static String LOGOUT = "QUIT";
	private MessageSocket msgSocket;
	
	public AccessControlCommands(MessageSocket msgSocket) {
		this.msgSocket = msgSocket;
	}
	/*
	 * following methods implements FtpMessagesSocket function to send
	 * rfc-command to server
	 */


	/**
	 * send Username to Server about MessagePort / Socket
	 * 
	 * @param username
	 */
	public void sendUserName(String username) {
		// Send Username to Server
		System.out.println("> " + USERNAME + ": " + username);
		msgSocket.output().println(USERNAME + " " + username);
	}

	/**
	 * send Password to Server about MessagePort / Socket
	 * 
	 * @param password
	 */
	public void sendPassword(String password) {
		// Send Password to Server
		System.out.println(">" + PASSWORD + ": " + password);
		msgSocket.output().println(PASSWORD + " " + password);
	}

	/**
	 * 
	 */
	public void sendAccount() {
		System.out.println("> " + ACCOUNT);
		msgSocket.output().println(ACCOUNT);
	}

	/**
	 * 
	 */
	public void sendChangeWorkingDirectory() {
		System.out.println("> " + CHANGE_WORK_DIR);
		msgSocket.output().println(CHANGE_WORK_DIR);
	}

	/**
	 * 
	 */
	public void sendChangeToParentDirectory() {
		System.out.println("> " + CHANGE_TO_PARENT_DIR);
		msgSocket.output().println(CHANGE_TO_PARENT_DIR);
	}

	/**
	 * 
	 */
	public void sendSTructureMount() {
		System.out.println("> " + STRUCTURE_MOUNT);
		msgSocket.output().println(STRUCTURE_MOUNT);
	}

	/**
	 * This command terminates a USER, flushing all I/O and account information,
	 * except to allow any transfer in progress to be completed. All parameters
	 * are reset to the default settings and the control connection is left
	 * open. This is identical to the state in which a user finds himself
	 * immediately after the control connection is opened. A USER command may be
	 * expected to follow.
	 */
	public void sendRenitialize() {
		System.out.println("> " + REINITIALIZE);
		msgSocket.output().println(REINITIALIZE);
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
	public void sendLogout() {
		System.out.println("> " + LOGOUT);
		msgSocket.output().println(LOGOUT);
	}
}
