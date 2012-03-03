package rfc765;

import sockets.FtpMessageSocket;

/**
 * @author Tobias Letschka
 * @see http://www.ietf.org/rfc/rfc959.txt
 * @see RFC 959                                                     October 1985
 * @see File Transfer Protocol
 * 
 *
 */
public class TransferParameterCommands {
	private static sockets.FtpMessageSocket socketMSG;
	
	

	/*
	 * 4.1.2.  TRANSFER PARAMETER COMMANDS
	 * 	DATA PORT (PORT)
	 * 	PASSIVE (PASV)
	 * 	REPRESENTATION TYPE (TYPE)
	 * 	FILE STRUCTURE (STRU)
	 * 	TRANSFER MODE (MODE)
	 *  
	*/
	private final static String DATA_PORT = "PORT";
	private final static String PASSIVE_MODE = "PASV";
	private final static String REPRESENTATION_TYPE = "TYPE";
	private final static String FILE_STRUCTURE = "STRU";
	private final static String TRANSFER_MODE = "MODE";
	
	public TransferParameterCommands(FtpMessageSocket socketMSG) {
		this.socketMSG = socketMSG;
	}
	/*	
	following methods implements FtpMessagesSocket function to send rfc-command to server
	*/
	public void sendPORT() {
		   System.out.println("> PORT:");
		   socketMSG.output().println("PORT 3000");
	}
	public void sendPASV() {
	   	   // set Passiv Mode
	   	System.out.println("> PASV mode:");
	   	socketMSG.output().println("PASV");	
	   	//Server return: 227 Entering Passive Mode (195,71,9,196,57,223)
	   	// IP: 195,71,9,196,
	   	// PORTS: 57,223

	}
	public void sendRepesentationType() {
	       // set TransferMode (Binary = I)
		System.out.println("> TYPE I");
		socketMSG.output().println("TYPE I");
	} 
	public void sendFileStructure() {
		   System.out.println("> STRU:");
		   socketMSG.output().println("STRU");
	}
	public void sendTransferMode() {
		   System.out.println("> MODE:");
		   socketMSG.output().println("MODE");
	}
}
