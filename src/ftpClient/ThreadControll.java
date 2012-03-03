package ftpClient;

import java.util.concurrent.Semaphore;

public class ThreadControll {

	
	private static final int MAX_AVAILABLE_PERMITS = 1;
	private Semaphore sem1 = new Semaphore(MAX_AVAILABLE_PERMITS);  // default: semaphore = 1 
	
	public boolean running()  {
		
		// reducing the number of available permits by one
		try {
			sem1.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		int counter;
		if((counter = sem1.availablePermits()) == 0) {
			return true;
		}
		else {
			return false;
		}
	}
	public boolean waiting() {
		
		// increasing Semaphore -> sem1+1
		sem1.release(1);  
		 
		int counter;
		if((counter = sem1.availablePermits()) != 1) {
			return true;
		}
		else {
			return false;
		}
	}
	

	
}
