package sockets.socketMessages;

import java.io.IOException;

import sockets.DataSocket;

public class ServerDatas {
	private DataSocket dSocket;

	public ServerDatas(DataSocket dSocket) {
		this.dSocket = dSocket;
	}

	public void awaitsLISTanswer() {
		while (true) {
			try {
				int buffersize = dSocket.getDataSocketInput().available();
				// If data are available
				if ((buffersize = dSocket.getDataSocketInput().available()) != 0) {
					int counter;
					byte buffer[] = new byte[buffersize];
					// while data are available -> read into buffer
					while ((counter = dSocket.getDataSocketInput().read(buffer, 0,
							buffersize)) != -1) {
						int count = 0;
						
						
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
}
