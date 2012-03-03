package rfc765;
/**
 * @author Tobias Letschka
 * @see http://www.ietf.org/rfc/rfc959.txt
 * @see RFC 959                                                     October 1985
 * @see File Transfer Protocol
 * 
 *
 */
public class OptionalCommands {
	
	private static sockets.FtpMessageSocket socketMSG = new sockets.FtpMessageSocket ();
/*	

 * SMNT - Structure Mount
 * STOU - Store Unique
 * RMD - Remove Directory
*/
	public static void sendStructureMount() {
		   System.out.println("> SMNT");
		   socketMSG.output().println("SMNT");
	   }
	public static void sendStoreUnique() {
		   System.out.println("> STOU");
		   socketMSG.output().println("STOU");
	   }
	
	public static void sendRemoveDirectory() {
		   System.out.println("> RMD");
		   socketMSG.output().println("RMD");
	   }
	
}
