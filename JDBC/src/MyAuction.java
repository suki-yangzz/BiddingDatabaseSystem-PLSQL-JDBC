import java.sql.*;
//import java.sql.Date;
import java.io.*;


public class MyAuction {
	
    private static String readEntry(String prompt){
    	try{
    		StringBuffer buffer = new StringBuffer();
    		System.out.print(prompt);
    		System.out.flush();
    		int c = System.in.read();
    		while(c!= '\n' && c!= -1) {
    			buffer.append((char)c);
    			c = System.in.read();
    		}
    		return buffer.toString().trim();
    	}catch(IOException e){
    		return "Error in readEntry.";
    	}
    }
    
    public static String sel;
	public static String username;
	public static String password;
	public static String new_customer;
	public static String new_pwd;
	public static String new_name;
	public static String new_address;
	public static String new_email;
	
	
	private static Connection connection;
	
	public static int isadmin = 2;	
	public static int iscustomer = 2;
	public static int check_val = 0;
	
	private static Connection getDBConnection(){
		String username, password;
		username = "hux11";
		password = "3828472";
		Connection connection = null;
		try{
			DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
			String url = "jdbc:oracle:thin:@db10.cs.pitt.edu:1521:dbclass"; 
    	    connection = DriverManager.getConnection(url, username, password);   
    	}catch(Exception Ex)  {
    	    System.out.println("Error connecting to database.  Machine Error: " +Ex.toString());
    	}
    	return connection;
    }	
	
	public static void check(){
        try{
        	connection = getDBConnection();        	
        	CallableStatement cstmt = connection.prepareCall("{? = call Check_loginpwd(?,?)}");
	    	cstmt.registerOutParameter(1,oracle.jdbc.OracleTypes.INTEGER);
	    	cstmt.setString(2, username);
	    	cstmt.setString(3, password);
	    	cstmt.execute();
	    	check_val = cstmt.getInt(1);
	    	cstmt.close();
	    	connection.commit();
	    	connection.close();
	    	
        }catch(Exception Ex){
        	System.out.println("Error Running Check Login and Password. Machine Error: " + Ex.toString());
 
        }
	}
	
	public static void Is_Admin(){
		
        try{
        	connection = getDBConnection();	
        	CallableStatement cstmt = connection.prepareCall("{? = call Is_Admin(?)}");
	    	cstmt.registerOutParameter(1,oracle.jdbc.OracleTypes.INTEGER);
	    	cstmt.setString(2, username);
	    	cstmt.execute();
	    	isadmin = cstmt.getInt(1);
	    	cstmt.close();
	    	connection.commit();
	    	connection.close();
        }catch(Exception Ex){
        	System.out.println("Error Running Is_Admin. Machine Error: " + Ex.toString());
        }
	}
	
	public static void Is_Customer(){

        try{
        	connection = getDBConnection();      	
        	CallableStatement cstmt = connection.prepareCall("{? = call Is_Customer(?)}");
	    	cstmt.registerOutParameter(1,oracle.jdbc.OracleTypes.INTEGER);
	    	cstmt.setString(2, username);
	    	cstmt.execute();
	    	iscustomer = cstmt.getInt(1);
	    	cstmt.close();
	    	connection.commit();
	    	connection.close();
        }catch(Exception Ex){
        	System.out.println("Error Running Is_Customer. Machine Error: " + Ex.toString());
        }
	}

	public static void register(){    	

    	try{
    		connection = getDBConnection();
        	System.out.println("Connect Successfully.");
        	connection.setAutoCommit(true);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);      
            System.out.println("Your Login Name is " + new_customer);
        	
        	if((isadmin==0) && (iscustomer==0)){
        		try{
        			CallableStatement cstmt3 = connection.prepareCall("{call Check_Customer(?,?,?,?,?)}");
                	cstmt3.setString(1, new_customer);
                	cstmt3.setString(2, new_pwd);
                	cstmt3.setString(3, new_name);
                	cstmt3.setString(4, new_address);
                	cstmt3.setString(5, new_email);
                	cstmt3.execute();
                	Thread.sleep(10000);
                	System.out.println("Register successfully.");
                	cstmt3.close();
        		}catch(Exception Ex){
        			//System.out.println("Error running Check_Customer.  Machine Error: " + Ex.toString());
        			System.out.println("Username or password are not right!");
        		}
        	}
        	else 
        		System.out.println("This Login Is Already Exist. Please Try Another One.");
        	connection.commit();
        	connection.close();

    	}catch(Exception Ex){
    	      System.out.println("Error running New A Customer.  Machine Error: " +
    	            Ex.toString());
    	}
    	
	}
	
	public static void main(String[] args){
		Admin adminexecute = new Admin();
		Custom customexecute = new Custom();
		String decide = "No";
		
		System.out.println("********Welcome to MyAuction System********");
		do{
			sel = readEntry("Enter your option: Login or Register? ");
			
			if(sel.equals("Login")){
				username = readEntry("Enter your username: ");
				password = readEntry("Enter your password: ");
				
				Is_Admin();
				Is_Customer();
				check();
				if(check_val==0) System.out.println("Username or password are not right!");
				
				if((isadmin==1) && (check_val==2)){
					System.out.println("/Administrator Interface/");
					System.out.println("Administrator Log in successfully and welcom back!");
					System.out.println("Select your options.");
					System.out.println("For updating system date, press 1.");
					System.out.println("For product statistics for all, press 2");
					System.out.println("For product statistics of specified customer, press 3");
					System.out.println("For top-k volumns in leaf-categories, press 4");
					System.out.println("For top-k volumns in root-categories, press 5");
					System.out.println("For top-k active bidders, press 6");
					System.out.println("For top-k active buyers, press 7");
					System.out.println("Quit, press 8");
	
					adminexecute.AdminExecute();
				}
				else if ((iscustomer==1) && (check_val==1)){
					System.out.println("/Customer Interface/");
					System.out.println("Customer Log in successfully and welcome back!");
					System.out.println("For browsing products, press 1");
					System.out.println("For searching for product by text, press 2");
					System.out.println("For putting products for auction, press 3");
					System.out.println("For bidding on products, press 4");
					System.out.println("For selling products, press 5");
					System.out.println("For suggestion, press 6");
					System.out.println("Quit, press 7");
					
					customexecute.CustomExecute(username);
				}
			}
			
			else if(sel.equals("Register")){
		    	new_customer = readEntry("Enter Your Login: ");
		    	new_pwd = readEntry("Enter Your Password: ");
		    	new_name = readEntry("Enter Your Name: ");
		    	new_address = readEntry("Enter Your Address: ");
		    	new_email = readEntry("Enter Your Email: ");
		    	
		    	Is_Admin();
				Is_Customer();
				check();
				register();
			}
			decide = readEntry("Finish? Yes or No?");
		}while(decide.equals("No"));

		System.out.println("*******Thank you for your use.**********");
	}

}
