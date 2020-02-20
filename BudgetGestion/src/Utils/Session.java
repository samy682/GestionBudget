package Utils;

import Database.Database;
import Entities.User;

public class Session {
	public static User us;
	
	 private static Session INSTANCE;
	    
	    private Session(){};
	    
	    public static Session getInstance(){
	        if (INSTANCE == null) {
	            INSTANCE = new Session();
	            return INSTANCE;
	        } else {
	            return INSTANCE;
	        }
	    }
	  
	    
	    
}
