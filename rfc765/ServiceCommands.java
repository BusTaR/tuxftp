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


public class ServiceCommands {
	
	private FtpMessageSocket socketMSG ;
	
	/*
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
	public final static String ABORT = "ABOR";
	public final static String ALLOCATE = "ALLO";
	public final static String APPEND= "APPE";
	public final static String DELETE = "DELE";
	public final static String LIST = "LIST";
	public final static String HELP = "HELP";
	public final static String MAKE_DIRECTORY = "MKD";
	public final static String NAME_LIST = "NLST";
	
	public final static String NOOP = "NOOP";
	public final static String RETRIEVE= "RETR";
	public final static String STORE = "STOR";
	public final static String STORE_UNIQUE = "STOU";
	public final static String REMOVE_DIRECTORY = "RMD";
	public final static String RENAME_FROM = "RNFR";
	public final static String RENAME_TO = "RNTO";
	public final static String RESTART = "REST";
	public final static String PRINT_WORKING_DIRECTORY = "PWD";
	public final static String SITE_PARAMETERS = "SITE";
	public final static String SYSTEM = "SYST";
	public final static String STATUS = "STAT";
	/*	
	following methods implements FtpMessagesSocket function to send rfc-command to server
	*/
	public ServiceCommands(FtpMessageSocket socketMsg) {
		this.socketMSG = socketMsg;
	}
	public void sendABORT() {
		   System.out.println("> ABORT");
		   socketMSG.output().println("ABORT");
	   }
	public void sendALLOCATE() {
		   System.out.println("> ALLO");
		   socketMSG.output().println("ALLO");
	   }
	public void sendAPPEND() {
		   System.out.println("> APPE");
		   socketMSG.output().println("APPE");
	   }

	public void sendDELETE() {
		   System.out.println("> DELE");
		   socketMSG.output().println("DELE");
	   }

/*	
	 * LIST (LIST)
	 * HELP (HELP)
	 * MAKE DIRECTORY (MKD)
	 * NAME LIST (NLST)
	 * NOOP (NOOP)
	 * RETRIEVE (RETR) 
	 * STORE (STOR) 
	*/
	public void sendLIST() {
		   System.out.println("> LIST:");
		   socketMSG.output().println("LIST");
	}
	public void sendHELP() {
		   
		   System.out.println("> HELP");
		   socketMSG.output().println("HELP");
	   }
	public void sendMakeDirectory(String dirName) {
		   
		   System.out.println("> MKD " + dirName );
		   socketMSG.output().println("MKD " + dirName);
	   }	
	public void sendNameList() {
		   // Get Server Operation System Typ
		   System.out.println("> NLST");
		   socketMSG.output().println("NLST");
	   }
	public void sendNOOP() {
		   System.out.println("> NOOP:");
		   socketMSG.output().println("NOOP");
	}
	//RETRIEVE
	public void sendRetrieve() {
		   System.out.println("> RETR");
		   socketMSG.output().println("RETR");
	   }
	public void sendSTORE() {
		   System.out.println("> STOR");
		   socketMSG.output().println("STOR");
	}
	/*
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
	public void sendStoreUnique() {
		   System.out.println("> STOU:");
		   socketMSG.output().println("STOU");
	}
	public void sendRemoveDirectory() {
		   System.out.println("> RMD");
		   socketMSG.output().println("RMD");
	   }
	public void sendRenameFrom() {
		   System.out.println("> RNFR");
		   socketMSG.output().println("RNFR");
	}
	public void sendRenameTo() {
		   System.out.println("> RNTO:");
		   socketMSG.output().println("RNTO");
	}
	public void sendRestart() {
		   System.out.println("> REST");
		   socketMSG.output().println("REST");
	   }
	public void sendPrintWorkingdirectory() {
		   System.out.println("> PWD");
		   socketMSG.output().println("PWD");
	}	
	public void sendSiteParameters() {
		   System.out.println("> SITE");
		   socketMSG.output().println("SITE");
	}		
	public void sendSystem() {
		   // Get Server Operation System Typ
		   System.out.println("> SYST");
		   socketMSG.output().println("SYST");
	   }
	public void sendStatus() {
		   // Get Server Operation System Typ
		   System.out.println("> STAT");
		   socketMSG.output().println("STAT");
	   }
/*	
	FEAT

	*/
	public void sendFEAT() {
		   
		   System.out.println("> FEAT");
		   socketMSG.output().println("FEAT");   
	}


	   




	 
	
}
