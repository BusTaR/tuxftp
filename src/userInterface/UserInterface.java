package userInterface;

import inputAndOutput.FtpDataSocket;
import inputAndOutput.FtpMessageSocket;
import inputAndOutput.FtpServerAnswerMessages;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * @author Tobias Letschka
 * 
 */

public class UserInterface {

	public void Interface() {

		int buffersize = 1024;
		char stdInbuffer[] = new char[buffersize];
		char serverbuffer[] = new char[buffersize];

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
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}

	private static void readServerAnswer() {
		int buffersize = 10000000;
		int count;
		char buffer[] = new char[buffersize];

		try {

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (FtpMessageSocket.getSocketInput().available() != 0) { // if
				// available
				while (FtpMessageSocket.getSocketInput().available() != 0) { // if
					// not
					// available
					count = FtpMessageSocket.in.read(buffer, 0, buffersize);
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
	private static boolean inputRFCHandling(String stdInString) {

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
			FtpMessageSocket.out.println("HELP");
			return true;
		}
		if (FtpServiceComImpl.userSendLIST(foundToken[0]) == true) {
			return true;
		}
		if (FtpServiceComImpl.userSendSYST(foundToken[0]) == true) {  // any failure, check it !!!
			return true;
		}
		/*
		 * just a command with parameter was send
		 */
		int count;
		if((count = foundToken.length) > 1 ) {
			if (FtpServiceComImpl.userDownload(foundToken[0], foundToken[1]) == true) {
				return true;
			}
			if(FtpServiceComImpl.userChangeDir(foundToken[0], foundToken[1]) == true) {
				return true;
			}
			if(FtpServiceComImpl.userUpload(foundToken[0], foundToken[1]) == true) {
				return true;
			}
		}

		


	
		return false; // jump back into endless crycle

	}

	private static boolean inputQuit(String token) {
		String cutstring = "\n";
		String replacestring = "";
		String newStdInString;
		if (token.contains(cutstring) == true) { // if cutstring is
			// include due
			newStdInString = token.replace(cutstring, replacestring);
			if (newStdInString.equals("exit") || newStdInString.equals("EXIT") || newStdInString.equals("quit")) { // Input
				// handling
				FtpMessageSocket.out.println("QUIT");
				return true; // stopping UserInterface
			}
			if (newStdInString.equals("QUIT")) {
				return true; // stopping UserInterface
			}

		}
		return false;
	}

}
