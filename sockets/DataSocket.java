package sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;


/**
 * @author Tobias Letschka
 * 
 * 
 */

public class DataSocket extends FtpSocket {
	
	private static InputStreamReader inputstreamreader = null;;
	private static PrintWriter out = null;
	private static BufferedReader in;
	
	public DataSocket(String address, int port) {
		this.address = address;
		this.port = port;
	}
	@Override
	public
	void startSocket() {
		try {
			socket = new Socket(address, port);
			socket.setSoTimeout(300);					// Set Timeout 300ms
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host: "
					+ address);
			System.err.println("DataSocket Server answer: " + e.getMessage());
		} catch (IOException e) {

			System.err.println("Couldn't get I/O for " + "the connection to: "
					+ address);
			System.err.println("DataSocket Server answer: " + e.getMessage());
			System.exit(1);
		}
	}

	public int getReceiveBufferSize() throws SocketException {
		return socket.getReceiveBufferSize();
	}
	public InputStream getDataSocketInput() {
		try {
			InputStream instream = socket.getInputStream();
			return instream;
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public OutputStream getDataSocketOutput() {
		OutputStream outstream;
		try {
			outstream = socket.getOutputStream();
			return outstream;
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public PrintWriter output() {
		// get output Stream
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			return out;
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		} // write into stream, to server
		return null;
	}

	public BufferedReader input() {
		// get input Stream
		try {
			inputstreamreader = new InputStreamReader(socket
					.getInputStream());

			return in = new BufferedReader(inputstreamreader);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}

		return null;
	}

	public void closeDataSocket() {
		//out.close();
		try {
			socket.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}



}
