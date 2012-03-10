package userInterface;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import sockets.DataSocket;
import sockets.MessageSocket;
import sockets.socketMessages.ServerDataAnswer;

/**
 * @author Tobias Letschka 
 * to work with this mode you have to understand basically ftp-commands by rfc959
 * 
 */
public class RfcMode {
	
	private ServerDataAnswer dataMsg;
	private DataSocket data;
	private MessageSocket msg;

	public RfcMode(DataSocket data, ServerDataAnswer dataMsg, MessageSocket msg) {
		this.data = data;
		this.dataMsg = dataMsg;
		this.msg = msg;
	}
	public void Interface() {

		int buffersize = 1024;
		char stdInbuffer[] = new char[buffersize];
		// makes reading from keyboard/shell ready
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(
											System.in)); 

		// 1. read from keyboard
		// 2. send to Server
		// 3. catch server answer
		// 4. start by 1
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
					msg.output().println(stdInString);
				}
				/*
				 * closing userInterface
				 */
				if (inputQuit(stdInString) == true) {
					//FtpServerAnswerMessages.readInputStream();
					break;
				}

				//FtpServerAnswerMessages.readInputStream();

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private boolean inputRFCHandling(String stdInString) {
		FtpServiceComImpl fservice = new FtpServiceComImpl(data, dataMsg, msg);
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
		for (int i = 0; i < foundToken.length; i++) {
			if (foundToken[i].contains(cutstring) == true) {
				foundToken[i] = foundToken[i].replace(cutstring, replacestring);
			}

		}
		/*
		 * just a command was sent without parameters
		 */

		if (foundToken[0].contains("HELP") == true) {
			msg.output().println("HELP");
			return true;
		}
		if (fservice.userSendLIST(foundToken[0]) == true) {
			return true;
		}
		if (fservice.userSendSYST(foundToken[0]) == true) { // any failure, check it !!!
			return true;
		}
		/*
		 * just a command with parameter was send
		 */
		int count;
		if ((count = foundToken.length) > 1) {
//			if (fservice.downloadFile(foundToken[0], foundToken[1]) == true) {
//				return true;
//			}
//			if (fservice.changeDirectory(foundToken[0], foundToken[1]) == true) {
//				return true;
//			}
//			if (fservice.uploadFile(foundToken[0], foundToken[1]) == true) {
//				return true;
//			}
		}

		return false; // jump back into endless crycle

	}

	private boolean inputQuit(String token) {
		String cutstring = "\n";
		String replacestring = "";
		String newStdInString;
		if (token.contains(cutstring) == true) { // if cutstring is
			// include due
			newStdInString = token.replace(cutstring, replacestring);
			if (newStdInString.equals("exit") || newStdInString.equals("EXIT")
					|| newStdInString.equals("quit")) { // Input
				// handling
				msg.output().println("QUIT");
				return true; // stopping UserInterface
			}
			if (newStdInString.equals("QUIT")) {
				return true; // stopping UserInterface
			}

		}
		return false;
	}
}
