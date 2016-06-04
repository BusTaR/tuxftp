package de.tuxftp.sockets.socketMessages;

import java.io.IOException;

import de.tuxftp.sockets.DataSocket;

public class ServerDatas {
	private DataSocket dSocket;

	public ServerDatas(DataSocket dSocket) {
		this.dSocket = dSocket;
	}

	public void awaitsLISTanswer() {
			try {
				int buffersize = 0;
				// If data are available
				if ((buffersize = dSocket.getDataSocketInput().available()) != 0) {
					int counter;
					byte buffer[] = new byte[buffersize];
					// while data are available -> read into buffer
					System.out.println("< Data from Server: ");
					while ((counter = dSocket.getDataSocketInput().read(buffer, 0,
							buffersize)) != -1) {					
						String fromServer = new String(buffer, 0, counter);
						System.out.print(fromServer);
						System.out.println("\nDataSocket Input: " + counter);
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		//}

	}
}
