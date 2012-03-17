package sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MessageSocket extends FtpSocket {
	// 
    private PrintWriter out = null;   // writing messages to server
    private BufferedReader in = null; // reads server messages, buffered
    private InputStreamReader inputstreamreader = null;
    
    
    public MessageSocket(String address, int port) {
		this.address = address;
		this.port = port;
	}
	@Override
	public
	void startSocket() {
		   try {	   		   
  		   		// get Socket
  		   		socket = new Socket(address, port);     
  		   		socket.setSoTimeout(300);
      } catch (UnknownHostException e) {
          System.err.println("Don't know about host: " + address);
      } catch (IOException e) {
          System.err.println("Couldn't get I/O for "
                             + "the connection to: " + address);
      } 		  
	}
	   				       			
	public InputStream getSocketInput() {
	 try {
		InputStream instream = socket.getInputStream();
		return instream;
	} catch (IOException e) {
		System.out.println(e.getMessage());
		e.printStackTrace();
	}
	return null;
	}

	public OutputStream getSocketOutput() {
	 	OutputStream outstream;
		try {
			outstream = socket.getOutputStream();
			return outstream;
		} catch (IOException e) {
			System.err.println("FtpMessagesSocket" + e.getMessage());
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // write into stream, to server
  return null;
	}
	public BufferedReader input() {
	   // get input Stream
		try {
			inputstreamreader = new InputStreamReader(socket.getInputStream());
			return in = new BufferedReader(inputstreamreader);
		} catch (IOException e) {
			// TODO Auto-generated catch block
		e.printStackTrace();
		}
           
	return null;
	}
	public void closeMessageSocket() {
	  	out.close();
    	try {
			in.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
    	try {
			socket.close();
		} catch (IOException e) {
			System.err.println("Socket cannot closed");
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
