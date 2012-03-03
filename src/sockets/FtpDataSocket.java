package sockets;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import ftpClient.AnonymousSession;

/**
 * @author Tobias Letschka
 * 
 * 
 */

public class FtpDataSocket {
	public static Socket ftpclientdata = null;

	public static InputStreamReader inputstreamreader = null;;
	public static PrintWriter out = null;
	public static BufferedReader in = null;
	public static FtpDataSocket data = null;

	public Socket startDataSocket(String Adress, int Port) {
		try {

			ftpclientdata = new Socket(Adress, Port);
			return ftpclientdata;
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host: "
					+ AnonymousSession.DEFAULT_ADDRESS);
			System.err.println("DataSocket Server answer: " + e.getMessage());
		} catch (IOException e) {

			System.err.println("Couldn't get I/O for " + "the connection to: "
					+ AnonymousSession.DEFAULT_ADDRESS);
			System.err.println("DataSocket Server answer: " + e.getMessage());
			System.exit(1);
		}
		return null;
	}

	public synchronized InputStream getDataSocketInput() {
		try {
			InputStream instream = ftpclientdata.getInputStream();
			return instream;
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public synchronized OutputStream getDataSocketOutput() {
		OutputStream outstream;
		try {
			outstream = ftpclientdata.getOutputStream();
			return outstream;
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public static synchronized PrintWriter output() {
		// get output Stream
		try {
			out = new PrintWriter(ftpclientdata.getOutputStream(), true);
			return out;
		} catch (IOException e) {
			e.getMessage();
			e.printStackTrace();
		} // write into stream, to server
		return null;
	}

	public static synchronized BufferedReader input() {
		// get input Stream
		try {
			inputstreamreader = new InputStreamReader(ftpclientdata
					.getInputStream());
			return in = new BufferedReader(inputstreamreader);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}

		return null;
	}

	public void closeDataSocket() {
		// out.close();
		// try {
		// in.close();
		// } catch (IOException e) {
		// System.err.println(e.getMessage());
		// e.printStackTrace();
		// }
		try {
			ftpclientdata.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
