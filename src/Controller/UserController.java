/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.SignUpModel;
import com.sun.jdi.connect.spi.Connection;
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
            
            SignUpModel user = new SignUpModel(userName, password, address, phoneNo);
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

    // Method for data validation (you can implement your validation logic here)
 

}
