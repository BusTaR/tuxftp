package inputAndOutput;

import java.io.IOException;

/**
 * @author Tobias Letschka
 * this class handle modes for ...
 *
 */
public class FtpServerDataMessages {

	
	
	/**
	 *  vll dünnschiss ?
	 */
	public void awaitsLISTanswer() {
		FtpDataSocket fdata = new FtpDataSocket();
		while (true) {
			try {
				// If data are available
				if (fdata.getDataSocketInput().available() != 0) {
					int counter, buffersize = 10000;
					byte buffer[] = new byte[buffersize];
					// while data are available -> read into buffer
					while ((counter = fdata.getDataSocketInput().available()) != 0) {
						int count = 0;
						count = fdata.getDataSocketInput().read(buffer, 0,
								buffersize);
						System.out.println("< Server: ");
						String fromServer = new String(buffer, 0, count);
						System.out.println(fromServer);
					}
					break;
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * non static because every object needs an extra datasocket
	 * 
	 * @return
	 */
	public FtpDataSocket setPasvMode() {
		FtpMessageSocket.out.println("PASV"); // send command PASV
		FtpServerAnswerMessages.readInputStream(); // read answer from
		// server
		FtpDataSocket data = new FtpDataSocket(); // start data socket
		// through reading get ip and port
		data.startDataSocket(FtpServerAnswerMessages.getRETURN_IP(),
				FtpServerAnswerMessages.getRETURN_PORT());
		return data;
	}
}
