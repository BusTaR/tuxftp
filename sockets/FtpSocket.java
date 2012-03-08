package sockets;

import java.net.Socket;

public abstract class FtpSocket {
	protected static Socket socket = null;
	protected String address;
	protected int port;
	
	abstract public void startSocket();
}
