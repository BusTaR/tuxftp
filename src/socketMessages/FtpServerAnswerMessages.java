package socketMessages;

import java.io.IOException;

import sockets.FtpMessageSocket;

import ftpClient.ThreadControll;

/**
 * @author Tobias Letschka
 * 
 * catching Messages (ASCII) from FTP-Server and interpreted and printed this messages
 * @see sockets/FtpMessageSocket for Network-Connection
 */

public class FtpServerAnswerMessages implements Runnable {

	// in future it will use threads
	private static boolean threadRunning = true;
	ThreadControll controll = new ThreadControll(); // Semaphore

	
	// Working Thread
	public void run() {

		// while(threadRunning) {

		// if(controll.running() == true) {
		// System.out.println("Working Thread starts work");
		//						 
		//						
		// }

		readInputStream();
		// if(controll.waiting() == false) {
		//						
		// try {
		//					        	
		// System.out.println("Working Thread goes sleeping");
		// Thread.sleep(1000);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		// }

		// System.out.println("Working Thread died");

	}
	// stopping thread
	public static void killMSGThread() { 
		threadRunning = false;
	}

	/**
	 * this methode reads Input-Site of FtpMessageSocket == Server Answer
	 * and printing it to shell
	 */
	public static void readInputStream() {
		int buffersize = 2000;
		char buffer[] = new char[buffersize];
		FtpMessageSocket client = new FtpMessageSocket();

		// endless loop
		while (true) {
			
			try {
				int counter;
				// if any byte is available (start)
				if ((counter = client.getSocketInput().available()) != 0) {
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
