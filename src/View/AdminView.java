/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Controller.BookingController;
import View.*;
import java.awt.CardLayout;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author gandh
 */
public class AdminView extends javax.swing.JFrame {
            public String name;
            public String worker_id;
            DefaultTableModel requestHandleBookingsModel;
            DefaultTableModel paymentHandleBookingsModel;
            DefaultTableModel BookingsModel;
            public String[] credentials_for_refresh;
            
            private BookingController bookingController;
            private AdminView adminview;
            
    /**
     * Creates new form HomeView
     */
    public AdminView(String[] credentials) {
        name=credentials[0];
        
        worker_id=credentials[1];
        credentials_for_refresh=credentials;
        initComponents();
        bookingController = new BookingController();
        initializeTables();
    }
    
    
    
    public void initializeTables(){
        BookingsModel = new DefaultTableModel();
        paymentHandleBookingsModel = new DefaultTableModel(){
            Class[] types = new Class [] {
               java.lang.String.class, java.lang.Object.class,java.lang.String.class, java.lang.Boolean.class,  java.lang.Object.class
            };
            
           boolean[] canEdit = new boolean [] {
                false,false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
            
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
        requestHandleBookingsModel = new DefaultTableModel(){
            Class[] types = new Class [] {
                java.lang.Object.class,java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class
            };
            
           boolean[] canEdit = new boolean [] {
                false,false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
            
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
            int i=0;
            
        };
        BookingsModel.addColumn("User");
        BookingsModel.addColumn("Bill No");
        BookingsModel.addColumn("Room No");
        BookingsModel.addColumn("Room Type");
        BookingsModel.addColumn("Start Date");
        BookingsModel.addColumn("End Date");
        BookingsModel.addColumn("Amount");
        
        ResultSet BookingsModelResult = bookingController.getBookings();
        ResultSet[] unpaidBookingsModelResult_admin = bookingController.getUnpaidBookings_admin();
        ResultSet cacnelunpaidBookingsModelResult_admin = bookingController.getCancelRequests();
        try{
            while (BookingsModelResult.next()) {
                int user=BookingsModelResult.getInt("user");
                int BillNo = BookingsModelResult.getInt("bill_id");
                int roomNo = BookingsModelResult.getInt("room_no");
                String roomType = BookingsModelResult.getString("room_type");
                Date startDate = BookingsModelResult.getDate("start_date");
                Date endDate = BookingsModelResult.getDate("end_date");
                String totalAmount = BookingsModelResult.getString("Total_amount");

                // Add a row to the table model
                BookingsModel.addRow(new Object[]{user,BillNo, roomNo, roomType, startDate, endDate, totalAmount});
            }

            // Close result set and prepared statement
            BookingsModelResult.close();
       }catch(SQLException ex) {
            //model.addRow(new Object[]{null, null, "No data", null, null, null});
            // Handle exceptions
            ex.printStackTrace();
        }
        
        paymentHandleBookingsModel.addColumn("Type");
        paymentHandleBookingsModel.addColumn("Bill No");
        paymentHandleBookingsModel.addColumn("User");
        paymentHandleBookingsModel.addColumn("Generate Bill");
        paymentHandleBookingsModel.addColumn("Amount");
        
        
        
        requestHandleBookingsModel.addColumn("Bill No");
        requestHandleBookingsModel.addColumn("User");
        requestHandleBookingsModel.addColumn("Accept");
        requestHandleBookingsModel.addColumn("Amount");
        
        
        try{
            while (cacnelunpaidBookingsModelResult_admin.next()) {
                int BillNo = cacnelunpaidBookingsModelResult_admin.getInt("bill_id");
                int user =  cacnelunpaidBookingsModelResult_admin.getInt("user");
                //int roomNo = unpaidBookingsModelResult_admin[0].getInt("room_no");
               
                //String roomType = unpaidBookingsModelResult_admin[0].getString("room_type");
                //Date startDate = unpaidBookingsModelResult_admin[0].getDate("start_date");
                //Date endDate = unpaidBookingsModelResult_admin[0].getDate("end_date");
                String totalAmount = cacnelunpaidBookingsModelResult_admin.getString("Total_amount");

                // Add a row to the table model
                requestHandleBookingsModel.addRow(new Object[]{BillNo ,user,false, totalAmount});
                System.out.println("set");
            }

            // Close result set and prepared statement
            cacnelunpaidBookingsModelResult_admin.close();
       }catch(SQLException ex) {
            //model.addRow(new Object[]{null, null, "No data", null, null, null});
            // Handle exceptions
            ex.printStackTrace();
        }
        
        
        try{
            while (unpaidBookingsModelResult_admin[0].next()) {
                int BillNo = unpaidBookingsModelResult_admin[0].getInt("bill_id");
                int user =  unpaidBookingsModelResult_admin[0].getInt("user");
                //int roomNo = unpaidBookingsModelResult_admin[0].getInt("room_no");
               
                //String roomType = unpaidBookingsModelResult_admin[0].getString("room_type");
                //Date startDate = unpaidBookingsModelResult_admin[0].getDate("start_date");
                //Date endDate = unpaidBookingsModelResult_admin[0].getDate("end_date");
                String totalAmount = unpaidBookingsModelResult_admin[0].getString("Total_amount");

                // Add a row to the table model
                paymentHandleBookingsModel.addRow(new Object[]{"Room",BillNo ,user,false, totalAmount});
            }

            // Close result set and prepared statement
            unpaidBookingsModelResult_admin[0].close();
       }catch(SQLException ex) {
            //model.addRow(new Object[]{null, null, "No data", null, null, null});
            // Handle exceptions
            ex.printStackTrace();
        }
        
        try{
            while (unpaidBookingsModelResult_admin[1].next()) {
                int BillNo = unpaidBookingsModelResult_admin[1].getInt("food_bill_id");
                //int roomNo = unpaidBookingsModelResult_admin[0].getInt("room_no");
                int user=unpaidBookingsModelResult_admin[1].getInt("user_id");
               
                //String roomType = unpaidBookingsModelResult_admin[0].getString("room_type");
                //Date startDate = unpaidBookingsModelResult_admin[0].getDate("start_date");
                //Date endDate = unpaidBookingsModelResult_admin[0].getDate("end_date");
                String totalAmount = unpaidBookingsModelResult_admin[1].getString("amount");

                // Add a row to the table model
                paymentHandleBookingsModel.addRow(new Object[]{"Food",BillNo,user ,false, totalAmount});
            }

            // Close result set and prepared statement
            unpaidBookingsModelResult_admin[1].close();
       }catch(SQLException ex) {
            //model.addRow(new Object[]{null, null, "No data", null, null, null});
            // Handle exceptions
            ex.printStackTrace();
        }
        
        
        try{
            while (unpaidBookingsModelResult_admin[2].next()) {
                int BillNo = unpaidBookingsModelResult_admin[2].getInt("travel_bill_id");
                //int roomNo = unpaidBookingsModelResult_admin[0].getInt("room_no");
               int user=unpaidBookingsModelResult_admin[2].getInt("c_id");
                //String roomType = unpaidBookingsModelResult_admin[0].getString("room_type");
                //Date startDate = unpaidBookingsModelResult_admin[0].getDate("start_date");
                //Date endDate = unpaidBookingsModelResult_admin[0].getDate("end_date");
                String totalAmount = unpaidBookingsModelResult_admin[2].getString("amount");

                // Add a row to the table model
                paymentHandleBookingsModel.addRow(new Object[]{"Travel",BillNo ,user,false, totalAmount});
            }

            // Close result set and prepared statement
            unpaidBookingsModelResult_admin[2].close();
       }catch(SQLException ex) {
            //model.addRow(new Object[]{null, null, "No data", null, null, null});
            // Handle exceptions
            ex.printStackTrace();
        }
        
        
        try{
            while (unpaidBookingsModelResult_admin[3].next()) {
                int BillNo = unpaidBookingsModelResult_admin[3].getInt("event_id");
                //int roomNo = unpaidBookingsModelResult_admin[0].getInt("room_no");
                int user=unpaidBookingsModelResult_admin[3].getInt("cust_id");
                //String roomType = unpaidBookingsModelResult_admin[0].getString("room_type");
                //Date startDate = unpaidBookingsModelResult_admin[0].getDate("start_date");
                //Date endDate = unpaidBookingsModelResult_admin[0].getDate("end_date");
                String totalAmount = unpaidBookingsModelResult_admin[3].getString("amount");

                // Add a row to the table model
                paymentHandleBookingsModel.addRow(new Object[]{"Event",BillNo ,user,false, totalAmount});
            }

            // Close result set and prepared statement
            unpaidBookingsModelResult_admin[3].close();
       }catch(SQLException ex) {
            //model.addRow(new Object[]{null, null, "No data", null, null, null});
            // Handle exceptions
            ex.printStackTrace();
        }
        
        
        jTable2.setModel(BookingsModel);
        jTable1.setModel(paymentHandleBookingsModel);// Set the model for jTable2
        jTable3.setModel(requestHandleBookingsModel);// Set the model for jTable2
            
    }
    
    
    public String[] getFirstColumnValuesForTrueBoolean() {
    int rowCount = paymentHandleBookingsModel.getRowCount();
    String[] values = new String[rowCount];
    int i=0;
    for (int row = 0; row < rowCount; row++) {
        Boolean isTrue = (Boolean) paymentHandleBookingsModel.getValueAt(row, 3); // Check the third column (index 2)
        Object firstColumnValue = paymentHandleBookingsModel.getValueAt(row, 0);
        if (isTrue) {
           values[i] = (String) firstColumnValue;

            i++;
        }
    }
    
    // Trim the values array to remove any unused elements
    return Arrays.copyOf(values, i);
    }
    
    
    public int[] getSecondColumnValuesForTrueBoolean() {
    int rowCount = paymentHandleBookingsModel.getRowCount();
    int[] values = new int[rowCount];
    int i=0;
    for (int row = 0; row < rowCount; row++) {
        Boolean isTrue = (Boolean) paymentHandleBookingsModel.getValueAt(row, 3); // Check the third column (index 2)
        Object firstColumnValue = paymentHandleBookingsModel.getValueAt(row, 1);
        if (isTrue) {
            values[i] = (int) firstColumnValue;

            i++;
        }
    }
    
    // Trim the values array to remove any unused elements
    return Arrays.copyOf(values, i);
    }
    
    public int[] getThirdColumnValuesForTrueBoolean() {
    int rowCount = requestHandleBookingsModel.getRowCount();
    int[] values = new int[rowCount];
    int i=0;
    for (int row = 0; row < rowCount; row++) {
        Boolean isTrue = (Boolean) requestHandleBookingsModel.getValueAt(row, 2); // Check the third column (index 2)
        Object firstColumnValue = requestHandleBookingsModel.getValueAt(row, 0);
        if (isTrue) {
            values[i] = (int) firstColumnValue;

            i++;
        }
    }
    
    // Trim the values array to remove any unused elements
    return Arrays.copyOf(values, i);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jTable3 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        getContentPane().setLayout(new java.awt.CardLayout());

        jPanel1.setLayout(null);

        jButton1.setText("Handle Requests");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(300, 170, 180, 70);
        
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/back.png")));
        jButton7.setBounds(1400, 10, 50, 50);
        jPanel1.add(jButton7);
        jButton7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton7MouseClicked(evt);
            }
        });
        
        jPanel1.add(jButton6);
        jButton6.setText("Accept Payments");
        jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton6MouseClicked(evt);
            }
        });
        jPanel1.add(jButton6);
        jButton6.setBounds(1070, 170, 180, 70);

        
        jTable2.setUpdateSelectionOnSort(false);
        jTable2.setVerifyInputWhenFocusTarget(false);
        jScrollPane2.setViewportView(jTable2);
        jTable2.setRowHeight(60);

        jPanel1.add(jScrollPane2);
        jScrollPane2.setBounds(40, 300, 1470, 402);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("All Bookings");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(630, 250, 300, 30);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel10.setText("Name    :");
        jPanel1.add(jLabel10);
        jLabel10.setBounds(370, 30, 100, 40);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel11.setText(name);
        jPanel1.add(jLabel11);
        jLabel11.setBounds(480, 30, 280, 40);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel8.setText(worker_id);
        jPanel1.add(jLabel8);
        jLabel8.setBounds(230, 30, 140, 40);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel9.setText("Customer ID    :");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(50, 30, 190, 40);

        getContentPane().add(jPanel1, "home");

        
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
        });
        jPanel3.setLayout(null);

        jLabel3.setText("Cancel Room");
        jPanel3.add(jLabel3);
        jLabel3.setBounds(600, 10, 400, 80);
        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        
        jButton3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton3.setText("Accept Payment");
        jButton3.setBounds(700, 700, 200, 30);
        jPanel3.add(jButton3);
        
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/back.png"))); // NOI18N
        jButton8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton8MouseClicked(evt);
            }
        });
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton8);
        jButton8.setBounds(10, 10, 50, 50);
        
        jTable3.setUpdateSelectionOnSort(false);
        jTable3.setVerifyInputWhenFocusTarget(false);
        jScrollPane3.setViewportView(jTable3);
        jTable3.setRowHeight(60);
        
        
        jPanel3.add(jScrollPane3);
        jScrollPane3.setBounds(40, 100, 1470, 550);

        getContentPane().add(jPanel3, "Cancel Room");

        
        jPanel6.setLayout(null);

        jLabel6.setText("Accept Payments");
        jPanel6.add(jLabel6);
        jLabel6.setBounds(600, 10, 400, 80);
        
        jTable1.setUpdateSelectionOnSort(false);
        jTable1.setVerifyInputWhenFocusTarget(false);
        jScrollPane1.setViewportView(jTable1);
        jTable1.setRowHeight(60);

        jPanel6.add(jScrollPane1);
        jScrollPane1.setBounds(40, 100, 1470, 550);
        
        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton2.setText("Accept Payment");
        jButton2.setBounds(700, 700, 200, 30);
        jPanel6.add(jButton2);
        
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        
        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        //jLabel2.setText("Book Room");
        //jPanel2.add(jLabel2);
        //jLabel2.setBounds(640, 10, 330, 80);

        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/back.png"))); // NOI18N
        jButton11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton11MouseClicked(evt);
            }
        });
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton11);
        jButton11.setBounds(10, 10, 50, 50);

        getContentPane().add(jPanel6, "Book Transportation");

        
        pack();
    }// </editor-fold>                        

                                         

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {                                      
        // TODO add your handling code here:
                CardLayout cardLayout = (CardLayout) getContentPane().getLayout();
        cardLayout.show(getContentPane(), "Cancel Room");
    }                                     

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {                                     
        // TODO add your handling code here:
                
    }    
    
    public void jButton2MouseClicked(java.awt.event.MouseEvent evt) {
        int[] bill_no_s = getSecondColumnValuesForTrueBoolean();
        String[] type_s = getFirstColumnValuesForTrueBoolean();
        
        boolean done=bookingController.acceptPayments(bill_no_s,type_s);
        if(done){
            JOptionPane.showMessageDialog(null, "Payment Accepted", "Accepted", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(null, "Payment Not Accepted", "Not Accepted", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void jButton3MouseClicked(java.awt.event.MouseEvent evt) {
        int[] bill_no_s = getThirdColumnValuesForTrueBoolean();
        //String[] type_s = getFirstColumnValuesForTrueBoolean();
        
        boolean done=bookingController.cancelRequestsHandle(bill_no_s);
        if(done){
            JOptionPane.showMessageDialog(null, "Cacnel Accepted", "Accepted", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(null, "Cancel Not Accepted", "Not Accepted", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    

                                         

    private void jButton6MouseClicked(java.awt.event.MouseEvent evt) {                                      
        // TODO add your handling code here:
                CardLayout cardLayout = (CardLayout) getContentPane().getLayout();
        cardLayout.show(getContentPane(), "Book Transportation");
    }                                     


    private void jButton7MouseClicked(java.awt.event.MouseEvent evt) {                                      
        // TODO add your handling code here:
                    adminview = new AdminView(credentials_for_refresh);
                    adminview.setVisible(true);
                    this.dispose();
        
    }                                     

    private void jButton8MouseClicked(java.awt.event.MouseEvent evt) {                                      
        // TODO add your handling code here:
        CardLayout cardLayout = (CardLayout) getContentPane().getLayout();
        cardLayout.show(getContentPane(), "home");
    }                                     

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
    }                                        

    private void jButton9MouseClicked(java.awt.event.MouseEvent evt) {                                      
        // TODO add your handling code here:
        CardLayout cardLayout = (CardLayout) getContentPane().getLayout();
        cardLayout.show(getContentPane(), "home");
    }                                     

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
    }                                        

    private void jButton10MouseClicked(java.awt.event.MouseEvent evt) {                                       
        // TODO add your handling code here:
        CardLayout cardLayout = (CardLayout) getContentPane().getLayout();
        cardLayout.show(getContentPane(), "home");
    }                                      

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
    }                                         

    private void jButton11MouseClicked(java.awt.event.MouseEvent evt) {                                       
        // TODO add your handling code here:
        CardLayout cardLayout = (CardLayout) getContentPane().getLayout();
        cardLayout.show(getContentPane(), "home");
    }                                      

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
    }                                         

    private void jButton12MouseClicked(java.awt.event.MouseEvent evt) {                                       
        // TODO add your handling code here:
        CardLayout cardLayout = (CardLayout) getContentPane().getLayout();
        cardLayout.show(getContentPane(), "home");
    }                                      

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
    }                                         

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
    }                                        

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify                     
    
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable3;
    
    // End of variables declaration                   
}
