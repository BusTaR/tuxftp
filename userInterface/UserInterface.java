package userInterface;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import sockets.MessageSocket;

/**
 * @author Tobias Letschka
 * 
 */

public class UserInterface {
	private FtpServiceComImpl fservice;
	private MessageSocket msgSocket;
	public UserInterface(FtpServiceComImpl fservice, MessageSocket msgSocket) {
		this.fservice = fservice;
		this.msgSocket = msgSocket;
	}
	public void Interface() {
		
		int buffersize = 1024;
		char stdInbuffer[] = new char[buffersize];

		BufferedReader stdIn = new BufferedReader(new InputStreamReader(
				System.in)); // makes reading from keyboard/shell ready

		// 1. read from keyboard
		// 2. send to Server
		// 3. catch server answer
		// 4. start to by 1
		int stdInCounter;

		System.out.println("********* welcome to UserInterface **********");

		try {
			while (true) {
				System.out.println("please insert a command: (e.g. HELP) ");
				// 1.1 read from Keyboard/shell into a buffer
				
					stdInCounter = stdIn.read(stdInbuffer);

				// 1.2 convert char to string
				String stdInString = new String(stdInbuffer, 0, stdInCounter);

				if (inputRFCHandling(stdInString) == true) {
					System.out.println("command transfered");

				} else {
					msgSocket.output().println(stdInString);
				}
				/*				
				closing userInterface
				*/
				if (inputQuit(stdInString) == true) {
					readServerAnswer();
					break;
				}

				readServerAnswer(); // testweise

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void readServerAnswer() {
		try {
			if (msgSocket.getSocketInput().available() != 0) { // if
				int buffersize = msgSocket.getSocketInput().available();
				int count;
				char buffer[] = new char[buffersize];
				// available
				while (msgSocket.getSocketInput().available() != 0) { // if
					// not
					// available
					count = msgSocket.input().read(buffer, 0, buffersize);
					System.out.println("< Server : ");
					String fromServer = new String(buffer, 0, count);
					System.out.println(fromServer);
				}
			}

		} catch (IOException e) {
			System.err.println("get Login Message failed");
		}

	}


	/**
	 * For the case user insert an rfc-command into userInterface
	 * @param stdInString
	 * @return
	 */
	private boolean inputRFCHandling(String stdInString) {

		// split standard Input into tokens, token cut by " "
		String tokenToFind = " ";
		StringTokenizer token = new StringTokenizer(stdInString, tokenToFind);
		int arraySize = token.countTokens();
		String foundToken[] = new String[arraySize];
		// find all tokens and save tokens into array
		for (int i = 0; token.hasMoreTokens(); i++) {
			foundToken[i] = token.nextToken();
			// debugging
			System.out.println("Token: " + i + " inhalt: " + foundToken[i]);
		}

		// foundToken[0] contains ftp command
		// fountToken[i] contains parameters
		/*
		 * now we remove /n (newline) into tokens
		 */
		String cutstring = "\n";
		String replacestring = "";
		String newStdInString;
		for (int i = 0; i < foundToken.length; i++) {
			if (foundToken[i].contains(cutstring) == true) {
				foundToken[i] = foundToken[i].replace(cutstring, replacestring);
			}

		}
		/*
		 * just a command was sent without parameters
		 */
		if (foundToken[0].contains("HELP") == true) {
			msgSocket.output().println("HELP");
			return true;
		}
		if (fservice.userSendLIST(foundToken[0]) == true) {
			return true;
		}
		if (fservice.userSendSYST(foundToken[0]) == true) {  // any failure, check it !!!
			return true;
		}
		/*
		 * just a command with parameter was send
		 */
		int count;
//		if((count = foundToken.length) > 1 ) {
//			if (fservice.downloadFile(foundToken[0], foundToken[1]) == true) {
//				return true;
//			}
//			if(fservice.changeDirectory(foundToken[0], foundToken[1]) == true) {
//				return true;
//			}
//			if(fservice.uploadFile(foundToken[0], foundToken[1]) == true) {
//				return true;
//			}
//		}

		


	
		return false; // jump back into endless crycle

	}

	private boolean inputQuit(String token) {
		String cutstring = "\n";
		String replacestring = "";
		String newStdInString;
		if (token.contains(cutstring) == true) { // if cutstring is
			// include due
			newStdInString = token.replace(cutstring, replacestring);
			if (newStdInString.equals("exit") || newStdInString.equals("EXIT") || newStdInString.equals("quit")) { // Input
				// handling
				msgSocket.output().println("QUIT");
				return true; // stopping UserInterface
			}
			if (newStdInString.equals("QUIT")) {
				return true; // stopping UserInterface
			}

		}
		return false;
	}

}
