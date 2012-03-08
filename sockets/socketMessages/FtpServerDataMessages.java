package sockets.socketMessages;

import java.io.IOException;
import java.util.StringTokenizer;

import sockets.FtpMessageSocket;


/**
 * @author Tobias Letschka
 * this class handle modes for ...
 *
 */
public class FtpServerDataMessages {

	private int port;
	private String serverIP;
	private FtpMessageSocket msgSocket;
	
	public FtpServerDataMessages(FtpMessageSocket msgSocket) {
		
		this.msgSocket = msgSocket;
	}


	public int getRETURN_PORT() {
		return port;
	}

	public String getRETURN_IP() {
		return serverIP;
	}
//	/**
//	 *  vll dünnschiss ?
//	 */
//	public void awaitsLISTanswer() {
//		while (true) {
//			try {
//				// If data are available
//				if (fdata.getDataSocketInput().available() != 0) {
//					int counter, buffersize = 10000;
//					byte buffer[] = new byte[buffersize];
//					// while data are available -> read into buffer
//					while ((counter = fdata.getDataSocketInput().available()) != 0) {
//						int count = 0;
//						count = fdata.getDataSocketInput().read(buffer, 0,
//								buffersize);
//						System.out.println("< Server: ");
//						String fromServer = new String(buffer, 0, count);
//						System.out.println(fromServer);
//					}
//					break;
//				}
//
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//
//	}

//	/**
//	 * non static because every object needs an extra datasocket
//	 * 
//	 * @return
//	 */
//	public FtpDataSocket setPasvMode() {
//		msgSocket.output().println("PASV"); // send command PASV
//		ftpServMsg.readPasvAnswer(); // read answer from server
//		FtpDataSocket data = new FtpDataSocket(ftpServMsg.getRETURN_IP(),
//				ftpServMsg.getRETURN_PORT()); // start data socket
//		// through reading get ip and port
//		return data;
//	}
	/*
	 * read the server answer and find transfer ip and port
	 */
	public void readPasvAnswer() {
		int buffersize = 2000;
		char buffer[] = new char[buffersize];
		while (true) {									// endless loop
			try {										// if any byte is available (start)
				int counter;
				if ((counter = msgSocket.getSocketInput().available()) != 0) {
					// read bytes till 0 byte are available (end)
					while ((counter = msgSocket.getSocketInput().available()) != 0) {
						int count = 0;
						count = msgSocket.input().read(buffer, 0, buffersize);
						System.out.println("< Server: ");
						String fromServer = new String(buffer, 0, count);
						System.out.println(fromServer);
						analysePASVstring(fromServer);
					}
					try {
						Thread.sleep(100);
						break;
					} catch (InterruptedException e) {
						System.err.println("FtpServerAnswerMessage:");
						System.err.println(e.getMessage());
						e.printStackTrace();
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void analysePASVstring(String input) {
		String substring = input.substring(0, 3);

		if (substring.equals("227") == true) { // if found message who starts
												// with 227...

			System.out.println("Pasmod gefunden");
			System.out.println("zu untersuchtender String: ");
			System.out.println(input);
			System.out.println("Ende");

			// analyse string server answer for pasv-mode
			// 227 Entering Passive Mode (195,71,9,196,177,13)

			StringTokenizer leftside = new StringTokenizer(input, "(");
			if (leftside.hasMoreTokens()) {
				String leftsubstring = leftside.nextToken();
				// System.out.println(leftsubstring);
				StringTokenizer rightside = new StringTokenizer(input, ")");
				if (rightside.hasMoreTokens()) {
					String rightsubstring = rightside.nextToken();
					// System.out.println(rightsubstring);
					String tmp = rightsubstring.replaceAll(leftsubstring, "");
					String ipPort;
					ipPort = tmp.substring(1); // cut first character
					System.out.println(ipPort);
					String ip[] = ipPort.split(",", 6);
					serverIP = ip[0] + "." + ip[1] + "." + ip[2] + "." + ip[3];
					String highBit = ip[4];
					String lowBit = ip[5];

					port = Integer.parseInt(highBit) * 256
							+ Integer.parseInt(lowBit);

					System.out.println("Die Ip ist " + ip);
					System.out.println("Port: höchstwertiger Bit: " + highBit
							+ " Port:niederwertiger bit: " + lowBit);
					System.out.println("Berechneter Port: " + port);

				}
			}

		}
	}

}
