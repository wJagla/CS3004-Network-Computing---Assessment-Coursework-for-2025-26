import java.io.*;
import java.net.*;


public class CustomerBClient {
	
	public static void main(String[] args)throws IOException{
		Socket WarehouseClientSocket = null;
		PrintWriter out = null;
		BufferedReader in = null;
		int WarehouseSocketNumber = 4545;
		String WarehouseServerName = "localhost";
		String ClientID = "Customer B";
		String WarehouseClientType = "Customer";
		
		try {
			WarehouseClientSocket = new Socket(WarehouseServerName, WarehouseSocketNumber);
			out = new PrintWriter(WarehouseClientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(WarehouseClientSocket.getInputStream()));
		} catch (UnknownHostException e) {
            System.err.println("Don't know about host: localhost ");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: "+ WarehouseSocketNumber);
            System.exit(1);
        }
		
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromUser;
        
        System.out.println("Initialised " + WarehouseClientType + " client and IO connections");
        
        in.readLine();
        out.println(WarehouseClientType);
        in.readLine();
        out.println(ClientID);
        
        while(true) {
        	fromUser = stdIn.readLine();
        	if(fromUser != null) {
        		System.out.println("Client: " + fromUser);
        		out.println(fromUser);
        	}
        	
        	fromServer = in.readLine();
        	System.out.println("Server: " + fromServer);
        }
        
        //  out.close();
        // in.close();
        // stdIn.close();
        // WarehouseClientSocket.close();
        
	}

}
