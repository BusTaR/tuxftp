package de.tuxftp.application;

import static java.lang.System.in;

import de.tuxftp.application.ftpClients.AnonymousSession;
import java.util.Scanner;

public class ApplicationFtpClient {

	public static void main(String[] args) throws Exception {
		System.out.println("Which kind of connection should be established?");
		System.out.println("typ 1 for a anonymous session");
		System.out.println("typ 2 for a user+password session");

		Scanner scanner = new Scanner(in);
		int input = 0;
		try {
			input = scanner.nextInt();

			switch (input) {
			case (1):
				ApplicationFtpClient.testForServerWithAnonyoumsAccount();
				break;
			}
		} finally {
			scanner.close();
		}

	}


	static void testForServerWithAnonyoumsAccount() throws Exception {
		new AnonymousSession("ftp2.de.debian.org", 21);
	}

}
