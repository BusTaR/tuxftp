package userInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;

import socketMessages.FtpServerAnswerMessages;
import socketMessages.FtpServerDataMessages;
import sockets.FtpDataSocket;
import sockets.FtpMessageSocket;


/*
 * Following Commands are implemented in this class
 * 
 * 
 * 4.1.3.  FTP SERVICE COMMANDS
 * 
 * ABORT (ABOR)
 * ALLOCATE (ALLO)
 * APPEND (with create) (APPE) 
 * DELETE (DELE)
 * LIST (LIST)
 * HELP (HELP)
 * MAKE DIRECTORY (MKD)
 * NAME LIST (NLST)
 * NOOP (NOOP)
 * RETRIEVE (RETR) // file download
 * STORE (STOR) // file upload
 * STORE UNIQUE (STOU) 
 * REMOVE DIRECTORY (RMD)
 * RENAME FROM (RNFR)
 * RENAME TO (RNTO)
 * RESTART (REST)
 * PRINT WORKING DIRECTORY (PWD)
 * SITE PARAMETERS (SITE)
 * SYSTEM (SYST)
 * STATUS (STAT)
 */

public class FtpServiceComImpl {
	
	
	/**
	 * displayed server operating System
	 * @param command
	 * @return
	 */
	public static boolean userSendSYST(String command) {
		if(command.contains(rfc.FtpServiceCommands.SYSTEM)) {
			rfc.FtpServiceCommands.sendSystem();
		}
		return false;
	}

	/**
	 * used to analyse userInput btw. stdInput and send Command LIST to
	 * ftpServer
	 * this is a basic implemenation
	 * @param command
	 * @return
	 */
	public static boolean userSendLIST(String command) {
		if (command.contains("LIST")) {
			FtpDataSocket data = new FtpDataSocket();
			FtpServerDataMessages dataMsg = new FtpServerDataMessages();
			dataMsg.setPasvMode();

			FtpMessageSocket.out.println("LIST"); // send list command
			FtpServerAnswerMessages.readInputStream(); // read msg
			dataMsg.awaitsLISTanswer();
			FtpServerAnswerMessages.readInputStream(); // read msg
			data.closeDataSocket();
			return true;
		}
		return false;
	}

	/**
	 * send also Command LIST to Ftp-Server; but is useful to use this function
	 * as helper into other funktions
	 * @see userChangeDir(String command, String directory)
	 * 
	 */
	public static void userSendLIST() {
		FtpDataSocket data = new FtpDataSocket();
		FtpServerDataMessages dataMsg = new FtpServerDataMessages();
		dataMsg.setPasvMode();

		FtpMessageSocket.out.println("LIST"); // send list command
		FtpServerAnswerMessages.readInputStream(); // read msg
		dataMsg.awaitsLISTanswer();
		FtpServerAnswerMessages.readInputStream(); // read msg
		data.closeDataSocket();
	}

	public static boolean changeDirectory(String command, String directory) {
		if (command.contains(rfc.AccessControlCommands.CHANGE_WORK_DIR)
				&& directory != null) {
			FtpMessageSocket.out
					.println(rfc.AccessControlCommands.CHANGE_WORK_DIR + " "
							+ directory);
			FtpServerAnswerMessages.readInputStream();
			FtpServiceComImpl.userSendLIST();
		}
		return false;
	}

	public static boolean downloadFile(String command, String fileName) {
		// If command == RETR
		if (command.contains(rfc.FtpServiceCommands.RETRIEVE)
				&& fileName != null) {
			FtpDataSocket data = new FtpDataSocket();
			FtpServerDataMessages dataMsg = new FtpServerDataMessages();
			dataMsg.setPasvMode();
			FtpMessageSocket.out.println(rfc.FtpServiceCommands.RETRIEVE + " "
					+ fileName);
			FtpServerAnswerMessages.readInputStream();

			try {
				// generate file: called fileName
				File file = new File(fileName); 
				// set stream on file with name: fileName
				FileOutputStream stream = new FileOutputStream(file); 
				// get a fucking buffersize
				int buffersize = data.ftpclientdata.getReceiveBufferSize(); 
				byte buffer[] = new byte[buffersize]; // generate buffer
				while (true) {
					if (data.getDataSocketInput().available() != 0) {
						data.getDataSocketInput().read(buffer);
					}
					break;
				}

				stream.write(buffer); // write into file
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// data.readDataInputStream();
			// FtpServerAnswerMessages.readInputStream();
			data.closeDataSocket();
			return true;
		}
		return false;
	}

	public static boolean uploadFile(String command, String fileName) {
		// find command
		if(command.contains(rfc.FtpServiceCommands.STORE)
													&& fileName != null) {
			//1. list local filesystem
			//2. set pasv or port mode ~ pasiv transfermode btw activ transfermode
			FtpDataSocket data = new FtpDataSocket();
			FtpServerDataMessages dataMsg = new FtpServerDataMessages();
			dataMsg.setPasvMode();
			// send rfc command to server
			FtpMessageSocket.out.println(rfc.FtpServiceCommands.STORE);
			// read answer
			FtpServerAnswerMessages.readInputStream();
			return true;
		}
		return false;
	}
}
