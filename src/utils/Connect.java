package utils;

import static classes.config.getID;
import java.sql.*;

import javax.swing.JOptionPane;

public class Connect {
	Connection con=null;
	public static Connection connectDb()
	{
		try {
		Class.forName("org.sqlite.JDBC");
		 Connection con=DriverManager.getConnection("jdbc:sqlite:"+getID()+"_gym_mgmt_system.db");
                  Statement stmt = con.createStatement();
         String sql = "CREATE TABLE IF NOT EXISTS login " +
                        "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " username CHAR(255) NOT NULL, " + 
                        " password CHAR(255) NOT NULL, " + 
                        " full_name CHAR(255) NOT NULL, " + 
                        " security_question CHAR(255) NOT NULL, " + 
                        " answer CHAR(255) NOT NULL, " + 
                        " type CHAR(155) NOT NULL)"; 
         stmt.executeUpdate(sql);
         stmt.close();
         
         stmt = con.createStatement();
         sql = "CREATE TABLE IF NOT EXISTS history " +
                        "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " date DATETIME NOT NULL, " +                                             
                        " task CHAR(155) NOT NULL)"; 
         stmt.executeUpdate(sql);
         stmt.close();
         
          stmt = con.createStatement();
         sql = "CREATE TABLE IF NOT EXISTS user " +
                        "(id INTEGER PRIMARY KEY AUTOINCREMENT," +   
                        " user_type CHAR(255) NOT NULL, " + 
                        " username CHAR(155) NOT NULL)"; 
         stmt.executeUpdate(sql);
         stmt.close();
         
         stmt = con.createStatement();
         sql = "CREATE TABLE IF NOT EXISTS lisense " +
                        "(id INTEGER PRIMARY KEY AUTOINCREMENT," +   
                        " date INTEGER NOT NULL, " + 
                        " count CHAR(255) NOT NULL, " + 
                        " allow CHAR(155))"; 
         stmt.executeUpdate(sql);
         stmt.close();
         
         stmt = con.createStatement();
         sql = "CREATE TABLE IF NOT EXISTS attendance " +
                        "(id INTEGER PRIMARY KEY AUTOINCREMENT," +  
                        " user_id INTEGER, " + 
                        " vaarifyMode CHAR(50) NOT NULL, " + 
                        " date CHAR(50) NOT NULL)"; 
         stmt.executeUpdate(sql);
         stmt.close();
         
          stmt = con.createStatement();
         sql = "CREATE TABLE IF NOT EXISTS members " +
                        "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " membership_no INTEGER NOT NULL, " + 
                        " full_name CHAR(255) NOT NULL, " +
                        " gender CHAR(100) NOT NULL, " + 
                        " address CHAR(255), " + 
                        " country CHAR(255) NOT NULL, " + 
                        " weight INTEGER NOT NULL, " + 
                        " status CHAR(255) NOT NULL, " + 
                        " email CHAR(255) NOT NULL, " + 
                        " reg_date DATE NOT NULL, " + 
                        " start_date DATE NOT NULL, " + 
                        " end_date DATE NOT NULL, " +                        
                        " fees_mode CHAR(100) NOT NULL, " + 
                        " discription CHAR(255), " + 
                        " plan CHAR(255) NOT NULL, " + 
                        " paid_fee DOUBLE NOT NULL, " + 
                        " contact_no INT(11) NOT NULL, " + 
                        " dob DATE NOT NULL, " +  
                        " occupation CHAR(255) NOT NULL, " + 
                        " machine CHAR(50), " + 
                        " img longblob )"; 
         stmt.executeUpdate(sql);
         stmt.close();
         
          sql = "CREATE TABLE IF NOT EXISTS fees " +
                        "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " plan CHAR(255) NOT NULL, " +
                        " duration INTEGER NOT NULL, " +                                              
                        " amount INTEGER NOT NULL)"; 
         stmt.executeUpdate(sql);
         stmt.close();
         
         sql = "CREATE TABLE IF NOT EXISTS product " +
                        "(product_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " product_name CHAR(255) NOT NULL, " +
                        " category CHAR(255) NOT NULL, " +
                        " cost_price DOUBLE NOT NULL, " +
                        " sell_price DOUBLE NOT NULL, " +
                        " opening_stock INTEGER NOT NULL, " +                                              
                        " description CHAR(255) )"; 
         stmt.executeUpdate(sql);
         stmt.close();
         
            sql = "CREATE TABLE IF NOT EXISTS sold_products " +
                        "(productId INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " productName CHAR(255) NOT NULL, " +
                        " Quantity INTEGER NOT NULL, " +
                        " Amount CHAR(255) NOT NULL, " +
                        " paidAmount DOUBLE NOT NULL, " +
                        " CustomerName CHAR(255) )"; 
         stmt.executeUpdate(sql);
         stmt.close();
         
          sql = "CREATE TABLE IF NOT EXISTS messages " +
                        "(productId INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " type CHAR(255) NOT NULL, " +
                        " sub CHAR(255) NOT NULL, " +
                        " body CHAR(255) NOT NULL )"; 
         stmt.executeUpdate(sql);
         stmt.close();
         
          sql = "CREATE TABLE IF NOT EXISTS messages_sent " +
                        "(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " tdate DATE NOT NULL )"; 
         stmt.executeUpdate(sql);
         stmt.close();
		 return con;
		 }
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
	}
	
	public static void main(String[] args) {
		Connect c= new Connect();
		c.connectDb();
               
}

   


}
