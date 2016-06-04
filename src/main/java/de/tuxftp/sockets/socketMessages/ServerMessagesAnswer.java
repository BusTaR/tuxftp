package de.tuxftp.sockets.socketMessages;

import java.io.IOException;

import de.tuxftp.sockets.MessageSocket;



/**
 * @author Tobias Letschka
 * 
 * catching Messages (ASCII) from FTP-Server and interpreted and printed this messages
 * @see MessageSocket for Network-Connection
 */

public class ServerMessagesAnswer {
	private MessageSocket msgSocket;


	public ServerMessagesAnswer(MessageSocket msgSocket) {
		this.msgSocket = msgSocket;
	}
	/**
	 * this methode reads Input-Site of FtpMessageSocket == Server Answer
	 * and printing it to shell
	 */
//	public void readInputStream() {
//		// endless loop
//		while (true) {
//			
//			try {
//				int counter;
//				// if any byte is available (start)
//				if ((counter = client.getSocketInput().available()) != 0) {
//					int buffersize = client.getSocketInput().available();
//					char buffer[] = new char[buffersize];
//					// read bytes till 0 byte are available (end)
//					while ((counter = client.getSocketInput().available()) != 0) {
//						int count = 0;
//						count = client.input().read(buffer, 0, buffersize);
//						System.out.println("< Server: ");
//						String fromServer = new String(buffer, 0, count);
//						System.out.println(fromServer);
//						System.out.println(" verfügbare Zeichen " + counter);
//					}
//					if(client.input().read() == -1) {
//						break;
//					}
//					try {
//						Thread.sleep(100);
//						break;
//					} catch (InterruptedException e) {
//						System.err.println("InterruptedException in FtpServerAnswerMessages: " + e.getMessage());
//						e.printStackTrace();
//					}
//				}
//			} catch (IOException e) {
//				System.err.println("IOException in FtpServerAnswerMessages: " + e.getMessage());
//				e.printStackTrace();
//			}
//		}
//
//	}
	public void readInputStream() {
		try {
			
			int buffersize;
			if ((buffersize = msgSocket.getSocketInput().available()) != 0) { // is any msg available?
				char buffer[] = new char[buffersize];
				int count = 0;
				while(msgSocket.getSocketInput().available() != 0)  {
					count = msgSocket.input().read(buffer, 0, buffersize);
					System.out.println("< Server: ");
					String fromServer = new String(buffer, 0, count);
					System.out.println(fromServer);
					System.out.println(" verfügbare Zeichen " + buffersize);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
