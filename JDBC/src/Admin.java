import java.sql.*;
//import java.sql.Date;
import java.io.*;
import java.util.*;

public class Admin {
	private static ResultSet resultSet;
	private static Scanner input = new Scanner(System.in);
	private static Connection connection;

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
    
	private static String getInfo(String prompt){
		String inputline = null;
		System.out.print(prompt + " ");
		try{
			BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
			inputline = is.readLine();
			if (inputline.length()==0) return null;
		}catch(IOException e){
			System.out.println("IOException: "+ e);
		}
		return inputline;
	}

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
	
	public void AdminExecute(){
		boolean done = true;	
		do{
			System.out.println("Enter your selection: ");
			int func_no = input.nextInt();
			System.out.println("Your choice is: "+func_no);
			
			switch(func_no){
			case 1:
				System.out.println("DateUpdate");
				func_2();
				break;
			case 4:
				System.out.println("topk_leafcate");
				func_3();
				break;	
			case 5:
				System.out.println("topk_rootcate");
				func_4();
				break;
			case 2:
				System.out.println("Product_Statics for all");
				func_5();
				break;
			case 3:
				System.out.println("Product_Statics for specified customer");
				func_6();
				break;
			case 6:
				System.out.println("active_bidders");
				func_7();
				break;
			case 7:
				System.out.println("activer_buyers");
				func_8();
				break;
			case 8:
				System.out.println("You have quit");
				done = false; break;
			default:
				System.out.println("Fuction is not found for your entry");break;
			}		
		}while(done);
	}
	
	public void func_2(){
		try{
			connection = getDBConnection();
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			System.out.println("Please enter the time:");
			String query = "call DateUpdate(?)";
			String time = getInfo("dd-MM-yyyy HH:mm:ss ");
			PreparedStatement cstmt = connection.prepareCall(query);
			cstmt.setString(1,time);
			cstmt.executeUpdate();
			connection.commit();
			connection.close();
		}catch(Exception Ex){
			System.out.println("Error running the sample query"+Ex.toString());
		}finally{
			System.out.println("Update is executed Successfully.");
		}
	}
	
	public void func_3(){
    	
		String topk = readEntry("Enter your top-k number: ");
		String xmonth = readEntry("Enter your month number: ");
		
    	try{
	    	connection = getDBConnection();
	        connection.setAutoCommit(false);
	        connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
	        
	        CallableStatement cstmt = connection.prepareCall("{call topk_leafcate (?,?,?)}");
	        cstmt.setString(1,topk);
	        cstmt.setString(2,xmonth);
	        cstmt.registerOutParameter(3, oracle.jdbc.OracleTypes.CURSOR);
	        cstmt.execute();
	        resultSet = (ResultSet) cstmt.getObject(3);
	        System.out.printf("%22s","category_name");
			System.out.printf("%10s\n","sold_num");
			System.out.println("--------------------------------------------");
			while(resultSet.next()){
				String categoryname = resultSet.getString("NAME");
				String sold_num = resultSet.getString("SOLD_NUM");
				System.out.printf("%22s",categoryname);
				System.out.printf("%10s\n",sold_num);
			}
			cstmt.close();
	    	connection.commit();
	    	connection.close();
    	}catch(Exception Ex){
    		System.out.println("No result found in the top K highest volume leaf-categories.");
    	    System.out.println("Machine Error: " + Ex.toString());
    	}
	}
	
	public void func_4(){
		String topk = readEntry("Enter your top-k number: ");
		String xmonth = readEntry("Enter your month number: ");
		
    	try{
	    	connection = getDBConnection();
	        connection.setAutoCommit(false);
	        connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
	        
	        CallableStatement cstmt = connection.prepareCall("{call topk_rootcate (?,?,?)}");
	        cstmt.setString(1,topk);
	        cstmt.setString(2,xmonth);
	        cstmt.registerOutParameter(3, oracle.jdbc.OracleTypes.CURSOR);
	        cstmt.execute();
	        resultSet = (ResultSet) cstmt.getObject(3);
	        System.out.printf("%22s","category_name");
			System.out.printf("%10s\n","sold_num");
			System.out.println("--------------------------------------------");
			while(resultSet.next()){
				String categoryname = resultSet.getString("root_name");
				String sold_num = resultSet.getString("total_sum");
				System.out.printf("%22s",categoryname);
				System.out.printf("%10s\n",sold_num);
			}
			cstmt.close();
	    	connection.commit();
	    	connection.close();
    	}catch(Exception Ex){
    		System.out.println("No result found in the top K highest volume root-categories.");
    	    System.out.println("Machine Error: " + Ex.toString());
    	}
	}
	
	public void func_5(){
		try{
			connection = getDBConnection();
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			String query = "call count_all(?)";
			System.out.println("This is to show the statisitc information for all users.");
			CallableStatement cstmt = connection.prepareCall(query);
			cstmt.registerOutParameter(1,oracle.jdbc.OracleTypes.CURSOR);
			cstmt.executeQuery();
			resultSet = (ResultSet) cstmt.getObject(1);
			System.out.printf("%12s","auction_id");
			System.out.printf("%22s","name");
			System.out.printf("%17s","status");
			System.out.printf("%12s","seller");
			System.out.printf("%12s","buyer");
			System.out.printf("%14s","final_amount");
			System.out.printf("%12s","bid_amount");
			System.out.printf("%12s\n","bidder");
			System.out.println("-----------------------------------------------------------------------------------------------------------------");
			while(resultSet.next()){
				int auction_id = resultSet.getInt("auction_id");
				String name = resultSet.getString("name");
				String state = resultSet.getString("status");	
				String seller = resultSet.getString("seller");
				String buyer = resultSet.getString("buyer");
				int final_amount = resultSet.getInt("final_amount");
				int bid_amount = resultSet.getInt("bid_amount");
				String bidder = resultSet.getString("bidder");				
				System.out.printf("%12d",auction_id);
				System.out.printf("%22s",name);
				System.out.printf("%17s",state);
				System.out.printf("%12s",seller);
				System.out.printf("%12s",buyer);
				System.out.printf("%14d",final_amount);
				System.out.printf("%12d",bid_amount);
				System.out.printf("%12s\n",bidder);

			}
			resultSet.close();
			connection.commit();
			connection.close();
		}catch(Exception Ex){
			System.out.println("Error running the sample query"+Ex.toString());
		}finally{
			System.out.println("Search is executed Successfully.");
		}	
	}
	
	public void func_6(){
		try{
			connection = getDBConnection();
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			String query = "call count_specified(?,?)";
			System.out.println("This is to show the statisitc information for specified user.");
			CallableStatement cstmt = connection.prepareCall(query);
			String customer = getInfo("Enter the name of the customer: ");
			cstmt.setString(1,customer);
			cstmt.registerOutParameter(2,oracle.jdbc.OracleTypes.CURSOR);
			cstmt.executeQuery();
			resultSet = (ResultSet) cstmt.getObject(2);
			System.out.printf("%12s","auction_id");
			System.out.printf("%22s","name");
			System.out.printf("%17s","status");
			System.out.printf("%12s","seller");
			System.out.printf("%12s","buyer");
			System.out.printf("%14s","final_amount");
			System.out.printf("%12s","bid_amount");
			System.out.printf("%12s\n","bidder");
			System.out.println("-----------------------------------------------------------------------------------------------------------------");
			while(resultSet.next()){
				int auction_id = resultSet.getInt("auction_id");
				String name = resultSet.getString("name");
				String state = resultSet.getString("status");	
				String seller = resultSet.getString("seller");
				String buyer = resultSet.getString("buyer");
				int final_amount = resultSet.getInt("final_amount");
				int bid_amount = resultSet.getInt("bid_amount");
				String bidder = resultSet.getString("bidder");				
				System.out.printf("%12d",auction_id);
				System.out.printf("%22s",name);
				System.out.printf("%17s",state);
				System.out.printf("%12s",seller);
				System.out.printf("%12s",buyer);
				System.out.printf("%14d",final_amount);
				System.out.printf("%12d",bid_amount);
				System.out.printf("%12s\n",bidder);
			}	
			resultSet.close();
			connection.commit();
			connection.close();
		}catch(Exception Ex){
			System.out.println("Error running the sample query"+Ex.toString());
		}finally{
			System.out.println("Search is executed Successfully.");
		}	
	}
	
	public void func_7(){
		try{
			connection = getDBConnection();
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			String query = "call active_bidders(?,?,?)";
			System.out.println("Please enter the month range: ");
			int x = input.nextInt();
			System.out.println("Please enter the rank: ");
			int k = input.nextInt();
			CallableStatement cstmt2 = connection.prepareCall(query);
			cstmt2.setInt(1,x);
			cstmt2.setInt(2,k);
			cstmt2.registerOutParameter(3,oracle.jdbc.OracleTypes.CURSOR);
			cstmt2.executeQuery();
			resultSet = (ResultSet)cstmt2.getObject(3);
			System.out.printf("%12s","bidder");
			System.out.printf("%7s\n","num");
			System.out.println("-------------------");
			while(resultSet.next()){
				String bidder = resultSet.getString("BIDDER");
				int num = resultSet.getInt("NUM_BID");
				System.out.printf("%12s",bidder);
				System.out.printf("%7d\n",num);
			}
			resultSet.close();
			connection.commit();
			connection.close();
		}catch(Exception Ex){
			System.out.println("Error running the sample query"+Ex.toString());	
		}finally{
			System.out.println("Search is executed.");
		}
	}
	
	public void func_8(){
		try{
			connection = getDBConnection();
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			String query = "call active_buyers(?,?,?)";
			System.out.println("Please enter the month range: ");
			int x = input.nextInt();
			System.out.println("Please enter the rank: ");
			int k = input.nextInt();
			CallableStatement cstmt2 = connection.prepareCall(query);
			cstmt2.setInt(1,x);
			cstmt2.setInt(2,k);
			cstmt2.registerOutParameter(3,oracle.jdbc.OracleTypes.CURSOR);
			cstmt2.executeQuery();
			resultSet = (ResultSet)cstmt2.getObject(3);	
			System.out.printf("%12s","buyer");
			System.out.printf("%7s\n","sum");
			System.out.println("-------------------");
			while(resultSet.next()){
				String buyer = resultSet.getString("Buyer");
				int sum = resultSet.getInt("Sum_Amount");
				System.out.printf("%12s",buyer);
				System.out.printf("%7d\n",sum);
			}
			resultSet.close();
			connection.commit();
			connection.close();
		}catch(Exception Ex){
			System.out.println("Error running the sample query"+Ex.toString());	
		}finally{
			System.out.println("Search is executed.");
		}
	}
}
	
