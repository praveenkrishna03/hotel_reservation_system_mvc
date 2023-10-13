/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import java.sql.*;

/**
 *
 * @author gandh
 */
public class BookingController {
           
    public ResultSet getPreviousBookings(int customer_id){
        
        ResultSet result=null;
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_reservation","root","praveenkrishna2003");
            String query = "SELECT * FROM bookings WHERE user = "+customer_id;
            PreparedStatement preparedStatement = con.prepareStatement(query);
            result = preparedStatement.executeQuery();
            
            //preparedStatement.close();
        }catch(SQLException ex) {
            //model.addRow(new Object[]{null, null, "No data", null, null, null});
            // Handle exceptions
            ex.printStackTrace();
        }
        
        return result;
    }
    
    
}
