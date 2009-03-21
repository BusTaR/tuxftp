package inputAndOutput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class FtpMessageSocket {
	
	
	// Class Object see Construtor 
	public static Socket ftpSocket = null;  // create an Socket
	
	
	// 
    public static PrintWriter out = null;   // writing messages to server
    public static BufferedReader in = null; // reads server messages, buffered
    public static InputStreamReader inputstreamreader = null;
    
    
    
	public Socket startMessageSocket(String address, int port) {
	   	   try {	   		   
	   		   		// get Socket
	   		   		ftpSocket = new Socket(address, port);
	   		   		
	   		   		// first Server answer       
			        System.out.println("Server :" + input().readLine());
			        return ftpSocket;
			   
	       } catch (UnknownHostException e) {
	           System.err.println("Don't know about host: " + address);
	       } catch (IOException e) {
	           System.err.println("Couldn't get I/O for "
	                              + "the connection to: " + address);
	           System.exit(1);
	       } 		
		 return null;  				       			
}

	public static synchronized InputStream getSocketInput() {
	 try {
		InputStream instream = ftpSocket.getInputStream();
		return instream;
	} catch (IOException e) {
		System.out.println(e.getMessage());
		e.printStackTrace();
	}
	return null;
	}

	public static synchronized OutputStream getSocketOutput() {
	 	OutputStream outstream;
		try {
			outstream = ftpSocket.getOutputStream();
			return outstream;
		} catch (IOException e) {
			System.err.println("FtpMessagesSocket" + e.getMessage());
			e.printStackTrace();
		}
	 	return null;
	}

	public static synchronized PrintWriter output() {
	// get output Stream        
		try {
			out = new PrintWriter(ftpSocket.getOutputStream(), true);
			return out;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // write into stream, to server
  return null;
	}
	public static synchronized BufferedReader input() {
	   // get input Stream
		try {
			inputstreamreader = new InputStreamReader(ftpSocket.getInputStream());
			return in = new BufferedReader(inputstreamreader);
		} catch (IOException e) {
			// TODO Auto-generated catch block
		e.printStackTrace();
		}
           
	return null;
	}
	public static void closeMessageSocket() {
	  	out.close();
    	try {
			in.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
    	try {
			ftpSocket.close();
		} catch (IOException e) {
			System.err.println("Socket cannot closed");
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
