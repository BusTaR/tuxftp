package socketMessages;

import java.io.IOException;

import sockets.FtpMessageSocket;



/**
 * @author Tobias Letschka
 * 
 * catching Messages (ASCII) from FTP-Server and interpreted and printed this messages
 * @see sockets/FtpMessageSocket for Network-Connection
 */

public class FtpServerAnswerMessages {
	private FtpMessageSocket client;


	public FtpServerAnswerMessages(FtpMessageSocket client) {
		this.client = client;
	}
	/**
	 * this methode reads Input-Site of FtpMessageSocket == Server Answer
	 * and printing it to shell
	 */
	public void readInputStream() {


		// endless loop
		while (true) {
			
			try {
				int counter;
				// if any byte is available (start)
				if ((counter = client.getSocketInput().available()) != 0) {
					int buffersize = client.getSocketInput().available();
					char buffer[] = new char[buffersize];
					// read bytes till 0 byte are available (end)
					while ((counter = client.getSocketInput().available()) != 0) {
						int count = 0;
						count = client.input().read(buffer, 0, buffersize);
						System.out.println("< Server: ");
						String fromServer = new String(buffer, 0, count);
						System.out.println(fromServer);
					}
					try {
						Thread.sleep(100);
						break;
					} catch (InterruptedException e) {
						System.err.println("InterruptedException in FtpServerAnswerMessages: " + e.getMessage());
						e.printStackTrace();
					}
				}
			} catch (IOException e) {
				System.err.println("IOException in FtpServerAnswerMessages: " + e.getMessage());
				e.printStackTrace();
			}
		}

	}


}
