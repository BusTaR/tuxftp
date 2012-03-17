package sockets;

import java.net.InetAddress;
import java.net.Socket;

public abstract class FtpSocket {
	protected static Socket socket = null;
	protected String address;
	protected int port;
	protected InetAddress iAdress;
	
	abstract public void startSocket();
}
