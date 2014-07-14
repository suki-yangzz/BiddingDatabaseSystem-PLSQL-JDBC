import java.sql.*;
import java.io.*;
import java.util.*;

public class Custom {
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
	
	public void CustomExecute(String user_id){
		boolean done = true;
		do{
			System.out.println("Enter your selection: ");
			int func_no = input.nextInt();
			System.out.println("Your choice is: "+func_no);
			
			switch(func_no){
			case 1:
				System.out.println("Brow_Product");
				func_1();
				break;
			case 2:
				System.out.println("Search_Product");
				func_2();
				break;
			case 3:
				System.out.println("Put_Auction (Please input the smallest category as you can.)");
				func_3(user_id);
				break;	
			case 4:
				System.out.println("bidproduct");
				func_4(user_id);
				break;
			case 5:
				System.out.println("update_status");
				func_5(user_id);
				break;
			case 6:
				System.out.println("Suggestoin");
				func_6(user_id);
				break;
			case 7:
				System.out.println("You have quit");
				done = false; break;
			default:
				System.out.println("Function is not found for your entry");break;
			}		
		}while(done);
	}

    private static void func_a1(){
    	ResultSet resultSet1;

        try{
        	connection = getDBConnection();
        	
        	CallableStatement cstmt1 = connection.prepareCall("{call List_RootCategory(?) }");
	    	cstmt1.registerOutParameter(1,oracle.jdbc.OracleTypes.CURSOR);
	    	cstmt1.execute();
	    	resultSet1 = (ResultSet) cstmt1.getObject(1);
	    	while(resultSet1.next()){
	    		System.out.println(resultSet1.getString(1));
	    	}
	    	cstmt1.close();
	    	connection.commit();
        }catch(Exception Ex){
        	System.out.println("Error running list_rootcategory. Machine Error: " + Ex.toString());
        }
    }
    
    private static void func_a3(String category){
    	ResultSet resultSet2;    	
    	try{
    		connection = getDBConnection();
    		CallableStatement cstmt2 = connection.prepareCall("{call List_SubCategory(?,?)}");
        	cstmt2.setString(1, category);    	
        	cstmt2.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
        	cstmt2.execute();
        	resultSet2 = (ResultSet) cstmt2.getObject(2);
        	System.out.println("SubCategory:");
        	while(resultSet2.next()){
        		String sub_cate = resultSet2.getString("NAME");
        		System.out.println(sub_cate);
        	}            	
          cstmt2.close();
          connection.commit();
    	}catch(Exception Ex){
        	System.out.println("Error running list_subcategory. Machine Error: " + Ex.toString());
        }
    }
    
    private static void func_a5(String sel, String category){
    	ResultSet resultSet3;     	
    	try{
    		connection = getDBConnection();
    		CallableStatement cstmt3 = connection.prepareCall("{call Brow_Product(?,?,?)}");
    		cstmt3.setString(1, sel);
    		cstmt3.setString(2, category);    	
        	cstmt3.registerOutParameter(3, oracle.jdbc.OracleTypes.CURSOR);
        	cstmt3.execute();
        	resultSet3 = (ResultSet) cstmt3.getObject(3);
        	System.out.println("Result: ");
    		System.out.printf("%12s","auction_id");
    		System.out.printf("%22s","name");
    		System.out.printf("%32s","description");
    		System.out.printf("%12s","seller");
    		System.out.printf("%25s","start_date");
    		System.out.printf("%11s","min_price");
    		System.out.printf("%16s","number_of_days");
    		System.out.printf("%17s","status");
    		System.out.printf("%25s","sell_date");
    		System.out.printf("%7s\n","amount");
    		System.out.println("------------------------------------------------------------------------------------" +
    				"-----------------------------------------------------------------------------------------------");
        	while(resultSet3.next()){
        		String auction_id = resultSet3.getString("AUCTION_ID");
        		String name = resultSet3.getString("NAME");
        		String description = resultSet3.getString("DESCRIPTION");
        		String seller = resultSet3.getString("SELLER");
        		String start_date = resultSet3.getString("START_DATE");
        		String min_price = resultSet3.getString("MIN_PRICE");
        		String number_of_days = resultSet3.getString("NUMBER_OF_DAYS");
        		String status = resultSet3.getString("STATUS");
        		String sell_date = resultSet3.getString("SELL_DATE");
        		String amount = resultSet3.getString("AMOUNT");
        		
        		System.out.printf("%12s",auction_id);
        		System.out.printf("%22s",name);
        		System.out.printf("%32s",description);
        		System.out.printf("%12s",seller);
        		System.out.printf("%25s",start_date);
        		System.out.printf("%11s",min_price);
        		System.out.printf("%16s",number_of_days);
        		System.out.printf("%17s",status);
        		System.out.printf("%25s",sell_date);
        		System.out.printf("%7s\n",amount);

        	}            	
          cstmt3.close(); 
          connection.commit();
    	}catch(Exception Ex){
        	System.out.println("Error running brow_product. Machine Error: " + Ex.toString());
        }
    }
    
	public void func_1(){
    	int hasleaf_a=1;

    	try{
    		connection = getDBConnection();
        	connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            
            Custom.func_a1();
            String category = readEntry("Enter your category name: ");
            
            while(hasleaf_a==1)
            {
            	Custom.func_a3(category);
            	category = readEntry("Enter your category name: ");
                CallableStatement cstmtleaf = connection.prepareCall("{? = call Has_Leaf(?) }");
                cstmtleaf.registerOutParameter(1, oracle.jdbc.OracleTypes.INTEGER);
                cstmtleaf.setString(2, category);
                cstmtleaf.execute();
                hasleaf_a = cstmtleaf.getInt(1);
                cstmtleaf.close();
            }        	
            
        	
        	System.out.println("For displaying by the highest bid amount, press 1");
        	System.out.println("For displaying alphabetically by product name, press 2");
        	
        	String sel = readEntry("Enter your sorting type: ");
        	
        	Custom.func_a5(sel, category);
        	connection.commit();
        	
    	}catch(Exception Ex){
    	      System.out.println("Error running BROWING PRODUCT.  Machine Error: " +
    	            Ex.toString());
    	}    	
	}
	
	public void func_2(){
    	ResultSet resultSet; 
    	
    	String keyword1 = readEntry("Enter Keyword1: ");
    	String keyword2 = readEntry("Enter Keyword2: ");
    	try{
    	connection = getDBConnection();
    	System.out.println("Search Products by Text.");
    	connection.setAutoCommit(false);
        connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        
        CallableStatement cstmt4 = connection.prepareCall("{call Search_Product(?,?,?)}");        
    	cstmt4.registerOutParameter(3,oracle.jdbc.OracleTypes.CURSOR);
    	cstmt4.setString(1, keyword1);
    	cstmt4.setString(2, keyword2);  	    	
		cstmt4.execute();
		resultSet = (ResultSet) cstmt4.getObject(3);
		System.out.println("Result:");
		System.out.printf("%12s","auction_id");
		System.out.printf("%22s","name");
		System.out.printf("%32s","description");
		System.out.printf("%12s","seller");
		System.out.printf("%25s","start_date");
		System.out.printf("%11s","min_price");
		System.out.printf("%16s","number_of_days");
		System.out.printf("%17s","status");
		System.out.printf("%25s","sell_date");
		System.out.printf("%7s\n","amount");
		System.out.println("------------------------------------------------------------------------------------" +
				"-----------------------------------------------------------------------------------------------");
    	while(resultSet.next()){
    		String auction_id = resultSet.getString("AUCTION_ID");
    		String name = resultSet.getString("NAME");
    		String description = resultSet.getString("DESCRIPTION");
    		String seller = resultSet.getString("SELLER");
    		String start_date = resultSet.getString("START_DATE");
    		String min_price = resultSet.getString("MIN_PRICE");
    		String number_of_days = resultSet.getString("NUMBER_OF_DAYS");
    		String status = resultSet.getString("STATUS");
    		String sell_date = resultSet.getString("SELL_DATE");
    		String amount = resultSet.getString("AMOUNT");

    		System.out.printf("%12s",auction_id);
    		System.out.printf("%22s",name);
    		System.out.printf("%32s",description);
    		System.out.printf("%12s",seller);
    		System.out.printf("%25s",start_date);
    		System.out.printf("%11s",min_price);
    		System.out.printf("%16s",number_of_days);
    		System.out.printf("%17s",status);
    		System.out.printf("%25s",sell_date);
    		System.out.printf("%7s\n",amount);
    	}
    	cstmt4.close();
    	connection.commit();
    	connection.close();
    	}catch(Exception Ex){
    	    System.out.println("Machine Error: " + Ex.toString());
    	}
	}
	
	public void func_3(String user_id){
    	int hasleaf_c = 1;
    	
		String name = readEntry("Enter your product's name: ");//1
		String description = readEntry("Enter your product's description: ");//2
		String min_price = readEntry("Enter your product's min_price: ");//4
		String category = readEntry("Enter your product's category: ");//5
		String number_of_days = readEntry("Enter your product's number_of_days: ");//6		
		
    	try{
	    	connection = getDBConnection();
	        connection.setAutoCommit(false);
	        connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);	        
	
	        CallableStatement cstmt5 = connection.prepareCall("{call Put_Auction(?,?,?,?,?,?)}");
	    	cstmt5.setString(1, name);
	    	cstmt5.setString(2, description);
	    	cstmt5.setString(3, user_id);
	    	cstmt5.setString(4, min_price);
	    	cstmt5.setString(5, category);
	    	cstmt5.setString(6, number_of_days);
	    	
	        CallableStatement cstmt6 = connection.prepareCall("{? = call Has_Leaf(?) }");
	        cstmt6.registerOutParameter(1, oracle.jdbc.OracleTypes.INTEGER);
	        cstmt6.setString(2, category);
	        cstmt6.execute();
	        hasleaf_c = cstmt6.getInt(1);
	        //Thread.sleep(10000);
	        if(hasleaf_c == 0) {
	        	cstmt5.execute();
	        	connection.commit();
	    		System.out.println("Successfully.");
	    		System.out.printf("%22s","name");
	    		System.out.printf("%32s","description");
	    		System.out.printf("%12s","seller");
	    		System.out.printf("%11s","min_price");
	    		System.out.printf("%22s","category");
	    		System.out.printf("%16s\n","number_of_days");
	    		System.out.println("-------------------------------------------------------------------------------------------------------------------");
	    		
	    		System.out.printf("%22s",name);
	    		System.out.printf("%32s",description);
	    		System.out.printf("%12s",user_id);
	    		System.out.printf("%11s",min_price);
	    		System.out.printf("%22s",category);
	    		System.out.printf("%16s\n",number_of_days);
	        }
	        else {
	        	System.out.println("Not a valid leaf category.");
	        }
	        cstmt5.close();
	        cstmt6.close();
	    	connection.close();
    	}catch(Exception Ex){
    		System.out.println("Not a valid leaf category. Please try again.");
    	}
	}
	
	public void func_4(String user_id){
    	System.out.println("Function 4: bidproduct");

    	try{
    		connection = getDBConnection();
    		connection.setAutoCommit(false);
    		connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
    		
    		String query0 = "select auction_id, name, amount from product where status = 'underauction'";
    		java.sql.Statement stmt = connection.createStatement();
    		resultSet = stmt.executeQuery(query0);
    		System.out.printf("%12s","product_id");
			System.out.printf("%22s","name");
			System.out.printf("%12s\n","amount");
			System.out.println("--------------------------------------------------");
    		while(resultSet.next()){
    			int i = resultSet.getInt(1);
    			String name = resultSet.getString(2);
    			int price = resultSet.getInt(3);
    			System.out.printf("%12d",i);
    			System.out.printf("%22s",name);
    			System.out.printf("%12d\n",price);
    		}
    		
        	System.out.println("Enter the product_id: ");
        	int product_id = input.nextInt();
        	System.out.println("Enter your bidding amount: ");
        	int bid_amount = input.nextInt();
        	
			System.out.printf("%12d",product_id);
			System.out.printf("%12d",bid_amount);
			System.out.printf("%12s\n",user_id);
    		
    		String query = "call bidproduct(?,?,?,?)";
    		CallableStatement cstmt1 = connection.prepareCall(query);
			cstmt1.setInt(1,product_id );
			cstmt1.setInt(2,bid_amount);
			cstmt1.setString(3,user_id);
			cstmt1.registerOutParameter(4,java.sql.Types.INTEGER);
			cstmt1.executeQuery();
			connection.commit();
			int decide = cstmt1.getInt(4);
			
			if(decide == 1){
				System.out.println("Procedure bidproduct is finished.");	
			}
			else if(decide == 0){
				System.out.println("Bidding amount should be larger than current amount.");
			}
			else{
				System.out.println("This product is not under auction.");
			}
			connection.close();
    	}catch(Exception Ex){
			//System.out.println("Error running the sample query"+Ex.toString());	
			System.out.println("Bidding amount should be larger than current amount.");
		}
	}
	
	public void func_5(String user_id){
    	System.out.println("Function 5: update_status");
    	try{
	    	connection = getDBConnection();
	    	connection.setAutoCommit(false);
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			String query1 = "call showUnderauction (?,?)";
			CallableStatement cstmt = connection.prepareCall(query1);
			cstmt.setString(1, user_id);
			cstmt.registerOutParameter(2,oracle.jdbc.OracleTypes.CURSOR);
			cstmt.executeQuery();
			resultSet = (ResultSet)cstmt.getObject(2);
			System.out.printf("%12s","auction_id");
			System.out.printf("%22s","name");
			System.out.printf("%11s\n","min_price");
			System.out.println("--------------------------------------------");
			while(resultSet.next()){
				int auction_id = resultSet.getInt("auction_id");
				String name = resultSet.getString("Name");
				int min_price = resultSet.getInt("min_price");

				System.out.printf("%12d",auction_id);
				System.out.printf("%22s",name);
				System.out.printf("%11d\n",min_price);
			}
	    	System.out.println("Enter the product_id: ");
	    	int product_id = input.nextInt(); 
			String query2 = "call display_price(?,?)";
			CallableStatement cstmt2 = connection.prepareCall(query2);
			cstmt2.setInt(1,product_id);
			cstmt2.registerOutParameter(2,java.sql.Types.INTEGER);
			cstmt2.execute();
			int second_price = cstmt2.getInt(2);
			if (second_price == 0){
				System.out.println("No one has ever bided on this product");
			}
			else{
			System.out.println("The current second highest bidding price for this product is: "+second_price);
			System.out.println("Please enter your decision: Sell/Withdraw?");
			String decision = getInfo("Enter your decision: ");
			String query3 = "call update_status(?,?,?)";
			CallableStatement cstmt3 = connection.prepareCall(query3);
			cstmt3.setInt(1, product_id);
			cstmt3.setInt(2, second_price);
			cstmt3.setString(3,decision);
			cstmt3.executeQuery();
			connection.commit();
			connection.close();
			System.out.println("Procedure Update_status is finished.");	
			}
			
    	}catch(Exception Ex){
			System.out.println("Error running the sample query"+Ex.toString());	
		}
	}
	
	public void func_6(String user_id){
    	System.out.println("Function 6: Suggestion");
    	try{
    		connection = getDBConnection();
    		connection.setAutoCommit(false);
    		connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
    		
    		String query4 = "call Suggestion(?,?)";
    		CallableStatement cstmt4 = connection.prepareCall(query4);
    		cstmt4.setString(1,user_id);
    		cstmt4.registerOutParameter(2,oracle.jdbc.OracleTypes.CURSOR);
    		cstmt4.executeQuery();
			resultSet = (ResultSet)cstmt4.getObject(2);
			System.out.printf("%12s","popularity");
			System.out.printf("%12s","auction_id");
			System.out.printf("%20s\n","Product_name");
			System.out.println("--------------------------------------------");
			while(resultSet.next()){
				int popularity = resultSet.getInt("popularity");
				int auction_id = resultSet.getInt("bid_id");
				String Product_name = resultSet.getString("name");
				System.out.printf("%12d",popularity);
				System.out.printf("%12d",auction_id);
				System.out.printf("%20s \n",Product_name);
			}
			connection.commit();
			connection.close();
    	}catch(Exception Ex){
			System.out.println("Error running the sample query"+Ex.toString());	
		}
	}
}
	
