/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import java.sql.*;
import java.lang.*;
import java.sql.Date;


/**
 *
 * @author gandh
 */
public class BookingController {
           
    public ResultSet getPreviousBookings(int customer_id){
        
        ResultSet result=null;
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_reservation","root","praveenkrishna2003");
            String query = "SELECT * FROM bookings WHERE user = "+customer_id+" AND paid = 1";
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
    
    public ResultSet getUnpaidBookings(int customer_id){
        
        ResultSet result=null;
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_reservation","root","praveenkrishna2003");
            String query = "SELECT * FROM bookings WHERE user = "+customer_id+" AND paid = 0";
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
    
    public ResultSet BookRooms(int customer_id,int room_type,Date start_date,Date end_date ){
        ResultSet result=null;
        boolean roombooked=false;
        int room_no;
        int room_cap;
        boolean room_availability;
        int amount=0;
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_reservation","root","praveenkrishna2003");
            String query = "SELECT * FROM hotel_rooms WHERE room_type="+room_type;
            PreparedStatement preparedStatement = con.prepareStatement(query);
            ResultSet reference = preparedStatement.executeQuery();
            while (reference.next()) {
                room_no=reference.getInt("room_no");
                room_cap = reference.getInt("room_type");
                room_availability=reference.getBoolean("room_availability");
                if(room_cap==room_type&&room_availability){
                    try{
                        String query1 = "INSERT INTO bookings (user, room_no, room_type, start_date, end_date, total_amount, paid) VALUES (?,?,?,?,?,?,?)";
                        PreparedStatement preparedStatement1 = con.prepareStatement(query1);
                        
                        preparedStatement1.setInt(1,customer_id);
                        preparedStatement1.setInt(2,room_no);
                        preparedStatement1.setInt(3,room_type);
                        preparedStatement1.setDate(4,start_date);
                        preparedStatement1.setDate(5,end_date);
                        preparedStatement1.setInt(6,amount);
                        preparedStatement1.setInt(7,0);
                        roombooked=true;
                        
                        
                        int rowsUpdated1 = preparedStatement1.executeUpdate();
                        
                        String query2 = "UPDATE hotel_rooms SET room_availability = 0 WHERE room_no = ?";
                        PreparedStatement preparedStatement2 = con.prepareStatement(query2);
                        preparedStatement2.setInt(1, room_no); // Set the room_no parameter
                        int rowsUpdated = preparedStatement2.executeUpdate(); // Use executeUpdate() for non-query statements
                    
                        String query3 = "SELECT * FROM bookings WHERE room_no = ?";
                        PreparedStatement preparedStatement3 = con.prepareStatement(query3);
                        preparedStatement3.setInt(1, room_no);
                        
                        result=preparedStatement3.executeQuery();
                    
                    }catch(SQLException e){
                        e.printStackTrace();
                        
                    }
                    
                }
                if(roombooked){
                    break;
                }
            }
            reference.close();
            //preparedStatement.close();
        }catch(SQLException ex) {
            //model.addRow(new Object[]{null, null, "No data", null, null, null});
            // Handle exceptions
            ex.printStackTrace();
        }
    
    return result;
    }
    
    public boolean BookFoods(int customer_id,int no_of_days,boolean breakfast,boolean lunch,boolean snacks,boolean dinner){
                    try{
                        
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_reservation","root","praveenkrishna2003");
           
                        
                        String query = "INSERT INTO food (user_id, no_of_days, breakfast, lunch, snacks, dinner) VALUES (?,?,?,?,?,?)";
                        PreparedStatement preparedStatement = con.prepareStatement(query);
                        
                        preparedStatement.setInt(1,customer_id);
                        preparedStatement.setInt(2,no_of_days);
                        preparedStatement.setBoolean(3,breakfast);
                        preparedStatement.setBoolean(4,lunch);
                        preparedStatement.setBoolean(5,snacks);
                        preparedStatement.setBoolean(6,dinner);
                  
                       
                        
                        
                        int rowsUpdated1 = preparedStatement.executeUpdate();
                        
                        //String query2 = "UPDATE hotel_rooms SET room_availability = 0 WHERE room_no = ?";
                        //PreparedStatement preparedStatement2 = con.prepareStatement(query2);
                        //preparedStatement2.setInt(1, room_no); // Set the room_no parameter
                        //int rowsUpdated = preparedStatement2.executeUpdate(); // Use executeUpdate() for non-query statements
                        
                        return true;
                    }catch(SQLException e){
                        e.printStackTrace();
                        return false;
                    }
    
    
    }
    
    
    
    
}
