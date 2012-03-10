package userInterface;

import rfc765.ServiceCommands;
import sockets.DataSocket;
import sockets.MessageSocket;
import sockets.socketMessages.ServerDataAnswer;


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
	private DataSocket data;
	private ServerDataAnswer dataMsg;
	private MessageSocket msgSocket;
	
	public FtpServiceComImpl(DataSocket data, ServerDataAnswer dataMsg, MessageSocket msg ) {
		this.data = data;
		this.dataMsg = dataMsg;
		this.msgSocket = msg;
	}
	/**
	 * displayed server operating System
	 * @param command
	 * @return
	 */
	public boolean userSendSYST(String command) {
		if(command.contains(rfc765.ServiceCommands.SYSTEM)) {
			ServiceCommands fscom = new ServiceCommands(msgSocket);
			fscom.sendSystem();
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
	public boolean userSendLIST(String command) {
		if (command.contains("LIST")) {
			msgSocket.output().println("LIST"); // send list command
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
//	public void userSendLIST() {
//		msgSocket.output().println("LIST"); // send list command
//	}
//
//	public boolean changeDirectory(String command, String directory) {
//		AccessControlCommands access = new AccessControlCommands(msgSocket);
//		if (command.contains(access.)
//				&& directory != null) {
//			msgSocket.output()
//					.println(rfc765.AccessControlCommands.CHANGE_WORK_DIR + " "
//							+ directory);
//			//FtpServerAnswerMessages.readInputStream();
//			FtpServiceComImpl fservice = new FtpServiceComImpl(data, dataMsg, msgSocket);
//			fservice.userSendLIST();
//		}
//		return false;
//	}

//	public boolean downloadFile(String command, String fileName) {
//		// If command == RETR
//		if (command.contains(rfc765.FtpServiceCommands.RETRIEVE)
//				&& fileName != null) {
//			dataMsg.setPasvMode();
//			socketMsg.output().println(rfc765.FtpServiceCommands.RETRIEVE + " "
//					+ fileName);
//			//FtpServerAnswerMessages.readInputStream();
//
//			try {
//				// generate file: called fileName
//				File file = new File(fileName); 
//				// set stream on file with name: fileName
//				FileOutputStream stream = new FileOutputStream(file); 
//				// get a fucking buffersize
//				int buffersize = data.getReceiveBufferSize(); 
//				byte buffer[] = new byte[buffersize]; // generate buffer
//				while (true) {
//					if (data.getDataSocketInput().available() != 0) {
//						data.getDataSocketInput().read(buffer);
//					}
//					break;
//				}
//
//				stream.write(buffer); // write into file
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (SocketException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			// data.readDataInputStream();
//			// FtpServerAnswerMessages.readInputStream();
//			data.closeDataSocket();
//			return true;
//		}
//		return false;
//	}

//	public boolean uploadFile(String command, String fileName) {
//		// find command
//		if(command.contains(rfc765.FtpServiceCommands.STORE)
//													&& fileName != null) {
//			//1. list local filesystem
//			//2. set pasv or port mode ~ pasiv transfermode btw activ transfermode
//			dataMsg.setPasvMode();
//			// send rfc command to server
//			socketMsg.output().println(rfc765.FtpServiceCommands.STORE);
//			// read answer
//		//	FtpServerAnswerMessages.readInputStream();
//			return true;
//		}
//		return false;
//	}
}
