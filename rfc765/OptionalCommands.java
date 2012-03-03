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
public class OptionalCommands {
	
	private FtpMessageSocket socketMSG;
/*	

 * SMNT - Structure Mount
 * STOU - Store Unique
 * RMD - Remove Directory
*/
	public OptionalCommands(FtpMessageSocket socketMSG) {
		this.socketMSG = socketMSG;
	}
	public void sendStructureMount() {
		   System.out.println("> SMNT");
		   socketMSG.output().println("SMNT");
	   }
	public void sendStoreUnique() {
		   System.out.println("> STOU");
		   socketMSG.output().println("STOU");
	   }
	
	public void sendRemoveDirectory() {
		   System.out.println("> RMD");
		   socketMSG.output().println("RMD");
	   }
	
}
