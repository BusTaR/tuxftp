package de.tuxftp.rfc765;

import de.tuxftp.sockets.MessageSocket;

public class OptionalCommands {
	
	private MessageSocket socketMSG;
/*	

 * SMNT - Structure Mount
 * STOU - Store Unique
 * RMD - Remove Directory
*/
	public OptionalCommands(MessageSocket socketMSG) {
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
