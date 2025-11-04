import java.net.*;
import java.util.Arrays;
import java.io.*;



public class SharedWarehouseState {
	
	private int myApples;
	private int myOranges;
	private boolean accessing = false;
	private int threadsWaiting = 0;
	
	public SharedWarehouseState(int apples, int oranges) {
		myApples = apples;
		myOranges = oranges;
	}
	
	  public synchronized void acquireLock() throws InterruptedException{
	        Thread me = Thread.currentThread(); // get a ref to the current thread
	        System.out.println(me.getName()+" is attempting to acquire a lock!");	
	        ++threadsWaiting;
		    while (accessing) {  // while someone else is accessing or threadsWaiting > 0
		      System.out.println(me.getName()+" waiting to get a lock as someone else is accessing...");
		      //wait for the lock to be released - see releaseLock() below
		      wait();
		    }
		    // nobody has got a lock so get one
		    --threadsWaiting;
		    accessing = true;
		    System.out.println(me.getName()+" got a lock!"); 
		  }
	  
	  public synchronized void releaseLock() {
		  //release the lock and tell everyone
	      accessing = false;
	      notifyAll();
	      Thread me = Thread.currentThread(); // get a ref to the current thread
	      System.out.println(me.getName()+" released a lock!");
	  }
	  
	  public synchronized String processInput(String clientType, String theInput) {
		  String theOutput = null;
		  String[] command = theInput.trim().split("\\s+");
		  
		  if(validateInput(command)){
			  if(clientType.equals("Customer")) {
				  if(command[0].equalsIgnoreCase("Check_Stock")) {
					  theOutput = "There are " + myApples + " apples and " + myOranges + " oranges.";
				  } else if (command[0].equalsIgnoreCase("Buy_apples")) {
					  int amount = Integer.parseInt(command[1]);
					  if(myApples >= amount) {
						  myApples -= amount;
						  theOutput = amount + " apples purchased - " + myApples+ " remain.";
					  } else {
						  theOutput = "Stock is too low";
					  }
					 
					  System.out.println(theOutput);
				  } else if (command[0].equalsIgnoreCase("Buy_oranges")) {
					  int amount = Integer.parseInt(command[1]);
					  if(myOranges >= amount) {
						  myOranges -= amount;
						  theOutput = amount + " oranges purchased - " + myOranges+ " remain.";
					  } else {
						  theOutput = "Stock is too low";
					  }
					  
					  System.out.println(theOutput);
				  } else {
					  theOutput = "Unauthorised request"; 
					  System.out.println(theOutput);
				  }
			  } else if (clientType.equals("Supplier")) {
				  if(command[0].equalsIgnoreCase("Check_Stock")) {
					  theOutput = "There are " + myApples + " apples and " + myOranges + " oranges.";
				  } else if (command[0].equalsIgnoreCase("Add_apples")) {
					  int amount = Integer.parseInt(command[1]);
					  myApples += amount;
					  theOutput = amount + " apples added - " + "New amount: " + myApples;
					  System.out.println(theOutput);
				  } else if (command[0].equalsIgnoreCase("Add_oranges")) {
					  int amount = Integer.parseInt(command[1]);
					  myOranges += amount;
					  theOutput = amount + " oranges added - " + "New amount: " + myOranges;
					  System.out.println(theOutput);
				  } else {
					  theOutput = "Unauthorised request"; 
					  System.out.println(theOutput);
				  }
			  }
			  
		  } else {
			  if(clientType.equals("Customer")) {
				  theOutput = "Received incorrect request - only understand: "
				  		+ "\"Buy_apples\", "
				  		+ "\"Buy_Oranges\", "
				  		+ "\"Check_Stock\"";
			  } else {
				  theOutput = "Received incorrect request - only understand: "
					  		+ "\"Add_apples\", "
					  		+ "\"Add_Oranges\", "
					  		+ "\"Check_Stock\"";
			  }
		  }
		  
		  return theOutput;
	  }
	  
	  public boolean validateInput(String[] input) {
		  String[] commands = {"buy_apples", "buy_oranges",
				  "add_apples", "add_oranges"};
		  
		    if (input.length == 1) {
			    return input[0].equalsIgnoreCase("Check_Stock");
			}


		    if (input.length == 2 && Arrays.asList(commands).contains(input[0].toLowerCase())) {
		        try {
		            int amount = Integer.parseInt(input[1]);
		            return amount > 0;
		        } catch (NumberFormatException e) {
		            return false;
		        }
		        
		    }
		    
		  return false;
	  
	  }

}
