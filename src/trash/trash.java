package trash;

public class trash {
	/**
	 * filter searching e.g \r
	 *
	 */
	public static boolean filter(char buffer[], char one, char two) {   
						
		boolean status = false;  // default, filter doesnt found searched 2 chars
        for(int i = 0; i < buffer.length; i++) {  // searching into buffer 
        	System.out.println("index : " + i);
        	System.out.println(buffer[i]);
        	char c = buffer[i];
        	if(buffer[i] == one) {  // searching e.g '\'		// if first char was found into char buffer
        		// search 		// no					
        		if((filter(buffer, two, i)) == true) {			// ask next function for second char e.g. r
        			status = true;
        			break;										// only both chars in a line was found stop function an return true
        		}
        		
        	} else {
        		status = false;
        	}
        }
     return status;
	}
	public static boolean filter(char buffer[], char two , int index) {   // e.g. searching '\' and 'c'
		index++;  // next char and only this next char
		boolean status = false;  // default filter doesnt found searched 2 chars
        	if(buffer[index] == two) {  // search '/'
        								
        		
        		status = true;
        	} else {
        		status = false;
        	}
        
     return status;
	}
	public void waiting() {
		try {
			this.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
