/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.SignUpModel;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;



/**
 *
 * @author gandh
 */
public class UserController {
    
    // Method to handle user registration
    public int registerUser(String userName, String password, String address, String phoneNo) {
        // Validate user data
            // Create a UserModel object
            //String register_user = "INSERT INTO hotel_customers (phone_no, location, password, Name) VALUES (?, ?, ?, ?)";
            
            
            int userId=-1;
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_reservation","root","praveenkrishna2003");
                System.out.println("Success");
                String insertSQL = "INSERT INTO hotel_customers (phone_no, location, password,Name) VALUES (?, ?, ?, ?)";
                PreparedStatement preparedStatement = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
                
    
                //PreparedStatement preparedStatement = con.prepareStatement(insertSQL);
    
                preparedStatement.setString(1, phoneNo); 
                preparedStatement.setString(2, address); 
                preparedStatement.setString(3, password);
                preparedStatement.setString(4, userName);
    
    
                int rowsAffected = preparedStatement.executeUpdate();
    
                if (rowsAffected > 0) {
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

                    if (generatedKeys.next()) {
                        userId = generatedKeys.getInt(1); // Assuming user ID is an int
                        SignUpModel user = new SignUpModel(userName, address, phoneNo,String.valueOf(userId));
                        System.out.println("Insertion successful. Rows affected: " + rowsAffected);
                        System.out.println("Your User Id is " + userId);
                    } else {
                        System.out.println("Auto-generated keys not found.");
                    }

                    generatedKeys.close();
                } else {
                    System.out.println("Insertion failed.");
                }

                preparedStatement.close();
                con.close();
                
            }catch(ClassNotFoundException | SQLException ex){
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                
            }
            return userId;
           
        
        
    }
    
    public String[] checkUserCredentials(String userId,String password) {
        String[] userDetails=null;
        //boolean status=false;
        try {
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_reservation", "root", "praveenkrishna2003");
    System.out.println("Connection success");
    
    // Assuming you have a table named "hotel_customers"
    String selectSQL = "SELECT * FROM hotel_customers WHERE customer_id = ? AND password = ?";
    PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
    
    preparedStatement.setString(1, userId); // Replace "1" with the parameter index as per your table structure
    preparedStatement.setString(2, password); // Replace "2" with the parameter index as per your table structure

    ResultSet result = preparedStatement.executeQuery();

    if (result.next()) {
        // User with provided credentials found
        //int userId = result.getInt("customer_id");
        String phoneNo = result.getString("phone_no");
        String location = result.getString("location");
        String name = result.getString("Name");
        String customer_id=result.getString("customer_id");

        userDetails = new String[]{ customer_id,phoneNo, location, name };
        // Now, userDetails array contains the retrieved user details
        System.out.println("User details retrieved successfully.");
        SignUpModel user = new SignUpModel(name, location, phoneNo,String.valueOf(userId));
        //status=true;
        // Close result set, statement, and connection
        result.close();
        preparedStatement.close();
        con.close();
        
        // You can return the userDetails array here or process it as needed
    } else {
        System.out.println("User not found or incorrect credentials.");
    }
    } catch (ClassNotFoundException | SQLException ex) {
        Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
    }

        return userDetails;
    };
    
    public String[] checkAdminCredentials(String userId,String password) {
        String[] adminDetails=null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_reservation", "root", "praveenkrishna2003");
            System.out.println("Connection success");

            // Assuming you have a table named "hotel_customers"
            String selectSQL = "SELECT * FROM hotel_workers WHERE worker_id = ? AND password = ?";
            PreparedStatement preparedStatement = con.prepareStatement(selectSQL);

            preparedStatement.setString(1, userId); // Replace "1" with the parameter index as per your table structure
            preparedStatement.setString(2, password); // Replace "2" with the parameter index as per your table structure

            ResultSet result = preparedStatement.executeQuery();

            if (result.next()) {
                // User with provided credentials found
                //int userId = result.getInt("customer_id");
                String name = result.getString("worker_name");
                String worker_id=result.getString("worker_id");

                adminDetails = new String[]{  name,worker_id };
                // Now, userDetails array contains the retrieved user details
                System.out.println("User details retrieved successfully.");

                // Close result set, statement, and connection
                result.close();
                preparedStatement.close();
                con.close();

                // You can return the userDetails array here or process it as needed
            } else {
                System.out.println("User not found or incorrect credentials.");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return adminDetails;
    }
    // Method for data validation (you can implement your validation logic here)
 

}
