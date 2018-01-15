package it351web;



import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Powell
 */
public class webapp implements Serializable{
    
    private Connection connection;
    private Integer rowIndicatorMain = 0;
    private String customerRecord = "TEST";

    public String getCustomerRecord() {
        return customerRecord;
    }

    public void setCustomerRecord(String customerRecord) {
        this.customerRecord = customerRecord;
    }

    public Integer getRowIndicatorMain() {
        return rowIndicatorMain;
    }

    public void setRowIndicatorMain(Integer rowIndicatorMain) {
        this.rowIndicatorMain = rowIndicatorMain;
    }
    
    public void connect(){
        
    try{
    String url = "jdbc:mysql://localhost/it351";
    Class.forName("com.mysql.jdbc.Driver");
    connection = DriverManager.getConnection(url, "root", "IT351"); //final argument must be edited to match mysql root password
    System.out.println("Connecting to database");
    }   catch(Exception e){
    System.err.println("Connection Error");
    
        }
    }
    
    public String readRecord(){
        PreparedStatement statement = null;
        int rowIndicator = this.rowIndicatorMain;
        try {
            
            if(connection == null){
                System.out.println("connecting");
                this.connect();
            }
            statement = connection.prepareStatement("select cust_data from customer where ID = ?");
            // creates prepstatement to select cust data with int being rowindicator
            statement.setInt(1, rowIndicator);
            
            ResultSet rs = statement.executeQuery();
            if(rs.next()){ //if data is found then return the string
                String data = rs.getString(1);
                return data;
            }   else{ //if data is not found return with string "empty"
                    return "empty";
                }
        }   catch (Exception e) {
                System.out.println(e);
                e.printStackTrace();
                return null;
            }   finally{ //finally, close statement
                    try{
                        statement.close();
                    }catch(Exception e2){ }
        } 
    }
    
    public void updateRecord(String rowUpdate){ //uses static input to show working record updates
        PreparedStatement statement = null;
        int rowIndicator = this.rowIndicatorMain;
        try {
            if(connection == null){
                System.out.println("connecting");
                this.connect();
                
            }
            statement = connection.prepareStatement("update customer set cust_data = ? where ID = ?");
            
            statement.setString(1, rowUpdate);
            statement.setInt(2, rowIndicator);
            statement.execute();
             
        } catch (Exception e) {
            System.out.println(e);
             
        }finally{
            try{
                statement.close();
            }catch(Exception e2){ }
        }  
            
            
        }
    
    public void deleteRecord(){ //deletes customer record from customer table with matching id of row indicator
        PreparedStatement statement = null;
        int rowIndicator = this.rowIndicatorMain;
        try {
            if(connection == null){
                System.out.println("connecting");
                this.connect();
            }
            statement = connection.prepareStatement("delete from customer where ID = ?");
            
            statement.setInt(1, rowIndicator);
            statement.execute();
             
        } catch (Exception e) {
            System.out.println(e);
             
        }finally{
            try{
                statement.close();
            }catch(Exception e2){ }
        }
    }
        
        public void createRecord(String custName){ //creates new record by inserting static values to show working method
        PreparedStatement statement = null;
        try {
            if(connection == null){
                System.out.println("connecting");
                this.connect();
            }
            statement = connection.prepareStatement("insert into customer (first_name, last_name, cust_data)" + " values (?,?,?)");

            statement.setString(1, custName);
            statement.setString(2, "Bob");
            statement.setString(3, "This is a new record!");
            statement.execute();
            //System.out.println("ran");
        } catch (Exception e) {
            System.out.println(e);
             
        }finally{
            try{
                statement.close();
            }catch(Exception e2){ }
        }  
            
            
        }
        
        public int nextHighestRow(){
        Statement statement = null;
        int rowIndicator = this.rowIndicatorMain;
        try {
            
            if(connection == null){
                System.out.println("connecting");
                this.connect();
            }
            statement = connection.createStatement();
            // creates statement to find next highest row based on current row
            ResultSet rs = statement.executeQuery("select ID from customer where ID > " + rowIndicator + " order by ID");
            if(rs.next()){ //if data is found then return the row id
                int data = rs.getInt(1);
                return data;
            }   else{ 
                    rs = statement.executeQuery("select ID from customer order by ID"); //if reaching the end of rows then restart from highest lowest
                    
                    if(rs.next()){
                        int data = rs.getInt(1);
                        return data;
                    }
                    
                    else{
                        return 0;
                    }
                }
        }   catch (Exception e) {
                System.out.println(e);
                e.printStackTrace();
                return 0;
            }   finally{ //finally, close statement
                    try{
                        statement.close();
                    }catch(Exception e2){ }
        } 
    }
        
        public int nextLowestRow(){
        Statement statement = null;
        int rowIndicator = this.rowIndicatorMain;
        try {
            
            if(connection == null){
                System.out.println("connecting");
                this.connect();
            }
            statement = connection.createStatement();
            // creates statement to find next lowest row based on current row
            ResultSet rs = statement.executeQuery("select ID from customer where ID < " + rowIndicator + " order by ID DESC");
            if(rs.next()){ //if data is found then return the row ID
                int data = rs.getInt(1);
                return data;
            }   else{ 
                    rs = statement.executeQuery("select ID from customer order by ID DESC"); //if reaching the end of rows then restart from highest row
                    
                    if(rs.next()){
                        int data = rs.getInt(1);
                        return data;
                    }
                    
                    else{
                        return 0;
                    }
                }
        }   catch (Exception e) {
                System.out.println(e);
                e.printStackTrace();
                return 0;
            }   finally{ //finally, close statement
                    try{
                        statement.close();
                    }catch(Exception e2){ }
        } 
    }
}
