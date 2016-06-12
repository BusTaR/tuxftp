package de.tuxftp.application;

import static java.lang.System.in;

import java.util.Scanner;

import org.apache.commons.lang.StringUtils;

import de.tuxftp.application.ftpClients.AnonymousSession;
import de.tuxftp.application.ftpClients.UserPasswordSession;

public class ApplicationFtpClient {

	public static void main(String[] args) {
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
			case (2):
				ApplicationFtpClient.testforServerWithUserPasswortAccount();
				break;
			}
		} finally {
			scanner.close();
		}

	}

	private static void testforServerWithUserPasswortAccount() {
		new UserPasswordSession("pr0-talk.de", 21, "web16", "Password");
		

	}

	static void testForServerWithAnonyoumsAccount() {
		new AnonymousSession("ftp2.de.debian.org", 21);
	}

}
