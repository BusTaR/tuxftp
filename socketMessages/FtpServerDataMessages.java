package socketMessages;

import java.io.IOException;
import java.util.StringTokenizer;

import sockets.FtpDataSocket;
import sockets.FtpMessageSocket;


/**
 * @author Tobias Letschka
 * this class handle modes for ...
 *
 */
public class FtpServerDataMessages {

	private static int RETURN_PORT;
	private static String RETURN_IP;
	private FtpDataSocket fdata = null;
	private FtpMessageSocket client;
	
	public FtpServerDataMessages(FtpDataSocket fdata, FtpMessageSocket client) {
		this.fdata = fdata;
		this.client = client;
	}


	public static int getRETURN_PORT() {
		return RETURN_PORT;
	}

	public static String getRETURN_IP() {
		return RETURN_IP;
	}
	/**
	 *  vll dünnschiss ?
	 */
	public void awaitsLISTanswer() {
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
		client.output().println("PASV"); // send command PASV
		FtpServerDataMessages.readPasvAnswer(); // read answer from server
		FtpDataSocket data = new FtpDataSocket(FtpServerDataMessages.getRETURN_IP(),
				FtpServerDataMessages.getRETURN_PORT()); // start data socket
		// through reading get ip and port
		return data;
	}
	/*
	 * read the server answer and find transfer ip and port
	 */
	public static void readPasvAnswer() {
		int buffersize = 2000;
		char buffer[] = new char[buffersize];
		

		// endless loop
		while (true) {
			// if any byte is available (start)
			try {
				int counter;
				if ((counter = FtpMessageSocket.getSocketInput().available()) != 0) {
					// read bytes till 0 byte are available (end)
					while ((counter = FtpMessageSocket.getSocketInput().available()) != 0) {
						int count = 0;
						count = FtpMessageSocket.input().read(buffer, 0, buffersize);
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

	private static void analysePASVstring(String input) {
		String substring = input.substring(0, 3);

		if (substring.equals("227") == true) { // if found message who start
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
					RETURN_IP = ip[0] + "." + ip[1] + "." + ip[2] + "." + ip[3];
					String highBit = ip[4];
					String lowBit = ip[5];

					RETURN_PORT = Integer.parseInt(highBit) * 256
							+ Integer.parseInt(lowBit);

					System.out.println("Die Ip ist " + RETURN_IP);
					System.out.println("Port: höchstwertiger Bit: " + highBit
							+ " Port:niederwertiger bit: " + lowBit);
					System.out.println("Berechneter Port: " + RETURN_PORT);

				}
			}

		}
	}

}
