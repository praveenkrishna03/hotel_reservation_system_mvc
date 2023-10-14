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
    
    public ResultSet getBookings(){
        
        ResultSet result=null;
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_reservation","root","praveenkrishna2003");
            String query = "SELECT * FROM bookings";
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
    
    public ResultSet[] getUnpaidBookings(int customer_id){
        
        ResultSet[] result=new ResultSet[4];
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_reservation","root","praveenkrishna2003");
            String query = "SELECT * FROM bookings WHERE user = "+customer_id+" AND paid = 0";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            result[0] = preparedStatement.executeQuery();
            
            String query1 = "SELECT * FROM food WHERE user_id = "+customer_id+" AND paid = 0";
            PreparedStatement preparedStatement1 = con.prepareStatement(query1);
            result[1] = preparedStatement1.executeQuery();
            
            
            String query2 = "SELECT * FROM travel WHERE c_id = "+customer_id+" AND paid = 0";
            PreparedStatement preparedStatement2 = con.prepareStatement(query2);
            result[2] = preparedStatement2.executeQuery();
            
            String query3 = "SELECT * FROM events WHERE cust_id = "+customer_id+" AND paid = 0";
            PreparedStatement preparedStatement3 = con.prepareStatement(query3);
            result[3] = preparedStatement3.executeQuery();
            //preparedStatement.close();
        }catch(SQLException ex) {
            //model.addRow(new Object[]{null, null, "No data", null, null, null});
            // Handle exceptions
            ex.printStackTrace();
        }
        
        return result;
    }
    
    public ResultSet[] getUnpaidBookings_admin(){
        
        ResultSet[] result=new ResultSet[4];
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_reservation","root","praveenkrishna2003");
            String query = "SELECT * FROM bookings WHERE paid = 0";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            result[0] = preparedStatement.executeQuery();
            
            String query1 = "SELECT * FROM food WHERE paid = 0";
            PreparedStatement preparedStatement1 = con.prepareStatement(query1);
            result[1] = preparedStatement1.executeQuery();
            
            
            String query2 = "SELECT * FROM travel WHERE paid = 0";
            PreparedStatement preparedStatement2 = con.prepareStatement(query2);
            result[2] = preparedStatement2.executeQuery();
            
            String query3 = "SELECT * FROM events WHERE AND paid = 0";
            PreparedStatement preparedStatement3 = con.prepareStatement(query3);
            result[3] = preparedStatement3.executeQuery();
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
                    
                }else if (room_cap==room_type&&!room_availability){
                    
                    String query1 = "SELECT * FROM bookings WHERE room_no = "+room_no;
                    PreparedStatement preparedStatement1 = con.prepareStatement(query1);
                    ResultSet reference2 = preparedStatement1.executeQuery();
                    while (reference2.next()) {
                        java.sql.Date start = reference2.getDate("start_date");
                        java.sql.Date end = reference2.getDate("end_date");
                        if ((start.before(start_date) && end.before(start_date)) || (start.after(end_date) && end.before(end_date))){
                            try{
                                String query2 = "INSERT INTO bookings (user, room_no, room_type, start_date, end_date, total_amount, paid) VALUES (?,?,?,?,?,?,?)";
                                PreparedStatement preparedStatement2 = con.prepareStatement(query2);

                                preparedStatement2.setInt(1,customer_id);
                                preparedStatement2.setInt(2,room_no);
                                preparedStatement2.setInt(3,room_type);
                                preparedStatement2.setDate(4,start_date);
                                preparedStatement2.setDate(5,end_date);
                                preparedStatement2.setInt(6,amount);
                                preparedStatement2.setInt(7,0);
                                roombooked=true;


                                int rowsUpdated1 = preparedStatement2.executeUpdate();

                                String query3 = "UPDATE hotel_rooms SET room_availability = 0 WHERE room_no = ?";
                                PreparedStatement preparedStatement3 = con.prepareStatement(query3);
                                preparedStatement3.setInt(1, room_no); // Set the room_no parameter
                                int rowsUpdated = preparedStatement3.executeUpdate(); // Use executeUpdate() for non-query statements

                                String query4 = "SELECT * FROM bookings WHERE room_no = ? AND start_date= ?";
                                PreparedStatement preparedStatement4 = con.prepareStatement(query4);
                                preparedStatement4.setInt(1, room_no);
                                preparedStatement4.setDate(2, start_date);

                                result=preparedStatement4.executeQuery();

                            }catch(SQLException e){
                                e.printStackTrace();

                            }
                        
                        }
                        if(roombooked){
                            break;
                        }
                        
                        
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
    
    public ResultSet BookEvents(int customer_id,int room_type,Date start_date,Date end_date ){
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
                        
                        
                        String query1 = "INSERT INTO events (cust_id, event_start, event_end, no_of_attendees, status, hall, amount, paid) VALUES (?, ?, ?, ?, 0, ?, ?, 0)";
                        PreparedStatement preparedStatement1 = con.prepareStatement(query1);
                        
                        preparedStatement1.setInt(1,customer_id);
                        preparedStatement1.setDate(2,start_date);
                        preparedStatement1.setDate(3,end_date);
                        preparedStatement1.setInt(4,(room_type-5)*25);
                        preparedStatement1.setInt(5,room_no);
                        preparedStatement1.setInt(6,amount);
                        roombooked=true;
                        
                        
                        int rowsUpdated1 = preparedStatement1.executeUpdate();
                        
                        String query2 = "UPDATE hotel_rooms SET room_availability = 0 WHERE room_no = ?";
                        PreparedStatement preparedStatement2 = con.prepareStatement(query2);
                        preparedStatement2.setInt(1, room_no); // Set the room_no parameter
                        int rowsUpdated = preparedStatement2.executeUpdate(); // Use executeUpdate() for non-query statements
                    
                        String query3 = "SELECT * FROM events WHERE hall = ?";
                        PreparedStatement preparedStatement3 = con.prepareStatement(query3);
                        preparedStatement3.setInt(1, room_no);
                        
                        result=preparedStatement3.executeQuery();
                    
                    }catch(SQLException e){
                        e.printStackTrace();
                        
                    }
                    
                }else if (room_cap==room_type&&!room_availability){
                    
                    String query1 = "SELECT * FROM events WHERE hall = "+room_no;
                    PreparedStatement preparedStatement1 = con.prepareStatement(query1);
                    ResultSet reference2 = preparedStatement1.executeQuery();
                    while (reference2.next()) {
                        java.sql.Date start = reference2.getDate("event_start");
                        java.sql.Date end = reference2.getDate("event_end");
                        if ((start.before(start_date) && end.before(start_date)) || (start.after(end_date) && end.before(end_date))){
                            try{
                                String query2 = "INSERT INTO events (cust_id, event_start, event_end, no_of_attendees, status, hall, amount, paid) VALUES (?, ?, ?, ?, 0, ?, ?, 0)";
                                PreparedStatement preparedStatement2 = con.prepareStatement(query2);

                                preparedStatement2.setInt(1,customer_id);
                                preparedStatement2.setDate(2,start_date);
                                preparedStatement2.setDate(3,end_date);
                                preparedStatement2.setInt(4,(room_type-5)*25);
                                preparedStatement2.setInt(5,room_no);
                                preparedStatement2.setInt(6,amount);
                                roombooked=true;


                                int rowsUpdated1 = preparedStatement2.executeUpdate();

                                String query3 = "UPDATE hotel_rooms SET room_availability = 0 WHERE room_no = ?";
                                PreparedStatement preparedStatement3 = con.prepareStatement(query3);
                                preparedStatement3.setInt(1, room_no); // Set the room_no parameter
                                int rowsUpdated = preparedStatement3.executeUpdate(); // Use executeUpdate() for non-query statements

                                String query4 = "SELECT * FROM events WHERE hall = ?";
                                PreparedStatement preparedStatement4 = con.prepareStatement(query4);
                                preparedStatement4.setInt(1, room_no);
                                preparedStatement4.setDate(2, start_date);

                                result=preparedStatement4.executeQuery();

                            }catch(SQLException e){
                                e.printStackTrace();

                            }
                        
                        }
                        if(roombooked){
                            break;
                        }
                        
                        
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
    
    
    public boolean BookTransport(int customer_id,int no_of_days,int bus,int car,int van,int jeep){
                    try{
                        
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_reservation","root","praveenkrishna2003");
           
                        
                        String query = "INSERT INTO travel (c_id, no_of_days, bus, van, car, jeep) VALUES (?,?,?,?,?,?)";
                        PreparedStatement preparedStatement = con.prepareStatement(query);
                        
                        preparedStatement.setInt(1,customer_id);
                        preparedStatement.setInt(2,no_of_days);
                        preparedStatement.setInt(3,bus);
                        preparedStatement.setInt(4,van);
                        preparedStatement.setInt(5,car);
                        preparedStatement.setInt(6,jeep);
                  
                        
                       
                        
                        
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
    
    public boolean cancelRequestsSend( int[] billIds) {
    // Define the SQL query for inserting into the cancel_request table
    
    
        String insertQuery = "INSERT INTO cancel_request (bill_id,status) VALUES (?,0)";

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_reservation","root","praveenkrishna2003");
            // Prepare the SQL statement
            PreparedStatement preparedStatement = con.prepareStatement(insertQuery);

            for (int billId : billIds) {
                // Set the bill_id value
                preparedStatement.setInt(1, billId);
                preparedStatement.executeUpdate();
            }

            // Close the prepared statement
            preparedStatement.close();
            return true;

        } catch (SQLException ex) {
            // Handle any exceptions
            ex.printStackTrace();
            return false;
        }
    }
    
    public void deleteBookingsByBillNumbers( int[] billNumbers) {
    // Define the SQL query with a placeholder for the bill number
    String deleteQuery = "DELETE FROM bookings WHERE bill_id = ?";

    try {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_reservation","root","praveenkrishna2003");
        // Prepare the SQL statement
        PreparedStatement preparedStatement = con.prepareStatement(deleteQuery);

        // Loop through the bill numbers and delete rows
        for (int billNumber : billNumbers) {
            preparedStatement.setInt(1, billNumber);
            preparedStatement.executeUpdate();
        }

        // Close the prepared statement
        preparedStatement.close();

    } catch (SQLException ex) {
        // Handle any exceptions
        ex.printStackTrace();
    }
}
    
    
}
