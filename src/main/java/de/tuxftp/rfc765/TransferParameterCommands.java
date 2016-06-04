package de.tuxftp.rfc765;

import java.io.File;

import de.tuxftp.sockets.MessageSocket;

/**
 * @author Tobias Letschka
 * @see http://www.ietf.org/rfc/rfc959.txt
 * @see RFC 959                                                     October 1985
 * @see File Transfer Protocol
 * 
 *
 */
public class TransferParameterCommands {
	private MessageSocket socketMSG;
	
	

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
	
	public TransferParameterCommands(MessageSocket socketMSG) {
		this.socketMSG = socketMSG;
	}
	/*	
	following methods implements FtpMessagesSocket function to send rfc-command to server
	*/
	public void sendPORT(String port) {
		   System.out.println("> " + DATA_PORT + ":");
		   socketMSG.output().println(DATA_PORT + " " + port);
	}
	public void sendPASV() {
	   	   // set Passiv Mode
	   	System.out.println("> " + PASSIVE_MODE + " mode:");
	   	socketMSG.output().println(PASSIVE_MODE);	
	   	//Server return: 227 Entering Passive Mode (195,71,9,196,57,223)
	   	// IP: 195,71,9,196,
	   	// PORTS: 57,223

	}
	public void sendRepesentationType() {
	       // set TransferMode (Binary = I)
		System.out.println("> " + REPRESENTATION_TYPE +  " I");
		socketMSG.output().println(REPRESENTATION_TYPE + " I");
	} 
	public void sendFileStructure() {
		   System.out.println("> " + FILE_STRUCTURE +":");
		   socketMSG.output().println(FILE_STRUCTURE);
	}
	public void sendTransferMode() {
		   System.out.println("> " + TRANSFER_MODE + ":");
		   socketMSG.output().println(TRANSFER_MODE);
	}
}
