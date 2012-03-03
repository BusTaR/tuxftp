package application;

import java.util.Scanner;

import application.ftpClient.AnonymousSession;
import application.ftpClient.UserPasswordSession;



public class ApplicationFtpClient {

	public static void main(String[] args) {
		System.out.println("Which kind of connection should be established?");
		System.out.println("typ 1 for a anonymous session");
		System.out.println("typ 2 for a user+password session");
		
		Scanner scanner = new Scanner(System.in);
		int input;
		
		input = scanner.nextInt();
		
		switch(input) {
		case(1): ApplicationFtpClient.testForServerWithAnonyoumsAccount();
				break;
		case(2): ApplicationFtpClient.testforServerWithUserPasswortAccount();
				break;
		}
		
	
	
	}
	private static void testforServerWithUserPasswortAccount() {
		new UserPasswordSession("pr0-talk.de",21,"web16","Password");
		
	}
	static void testForServerWithAnonyoumsAccount() {
		new AnonymousSession();  						
	}

}
