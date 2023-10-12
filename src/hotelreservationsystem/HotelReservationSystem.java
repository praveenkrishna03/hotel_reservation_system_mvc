/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package hotelreservationsystem;

import View.LoginView;
import View.SignUpView;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

/**
 *
 * @author gandh
 */
public class HotelReservationSystem {
    
    
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_reservation","root","praveenkrishna2003");
                System.out.println("Success");
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        SignUpView SignUpView = new SignUpView();
                        SignUpView.setVisible(true);
            
                    }
                });
            }catch(ClassNotFoundException | SQLException ex){
            Logger.getLogger(HotelReservationSystem.class.getName()).log(Level.SEVERE, null, ex);
             }
            
            
       
        }
}
    

