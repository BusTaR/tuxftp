/**
 * 
 */
package trash;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;

/**
 * @author iron
 *
 */
public class FG_FtpServer {
	public static ServerSocket ftpServerSocket = null;
	public static InputStream serverinput = null;
	public static OutputStream serveroupt = null;
	public final static int DEFAULT_WAIT_PORT = 34315;
	
	public FG_FtpServer() {
		try {
			ftpServerSocket = new ServerSocket(DEFAULT_WAIT_PORT);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public FG_FtpServer(int wait_port) {
		try {
			ftpServerSocket = new ServerSocket(wait_port);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public Socket waitForClient() {
		Socket Client;
		try {
			Client = ftpServerSocket.accept();
			return Client;						 // default return value
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return null; 						// if it fails than return null
	}
	public InputStream ClientInputStream() {
		try {
			InputStream inStream = waitForClient().getInputStream();
			return inStream;						 // default return value
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;  								// if it fails than return null
	}
	public OutputStream ClientOutputStream() {
		OutputStream outStream;
		try {
			outStream = waitForClient().getOutputStream();
			return outStream;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public void CloseFtpServerSocket() {
		try {
			ClientInputStream().close();		// close iinputstream
			ClientOutputStream().close();		// close outputstream
			ftpServerSocket.close();			// close ServerSocket
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
