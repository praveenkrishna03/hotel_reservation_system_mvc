/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;


import View.*;
import java.lang.*;
import Controller.*;
import java.awt.CardLayout;
import Model.SignUpModel;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.List;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


/**
 *
 * @author gandh
 */
public class HomeView extends javax.swing.JFrame {
    
    private SignUpModel signUpModel;

    private BookingController bookingController;
            public String[] credentials_for_refresh;
            public String name;
            public String phone_no;
            public String location;
            public String customer_id;
            public String password;
            DefaultTableModel cancelUnpaidBookingsModel;
            //DefaultTableModel unpaidBookingsModel;
    /**
     * Creates new form HomeView
     */
    public HomeView(String[] credentials) {
        
        //name = signUpModel.getUserName();
        //customer_id = signUpModel.getUserID();
        //password = signUpModel.getPassword();
        //location = signUpModel.getAddress();
        //phone_no = signUpModel.getPhoneNo();
        
        name=credentials[3];
        phone_no=credentials[1];
        location=credentials[2];
        customer_id=credentials[0];
        signUpModel = new SignUpModel(name, location, phone_no, customer_id);
        //initializeTable();
        credentials_for_refresh=credentials;
        initComponents();
        bookingController = new BookingController();
        initializeTables();
    }

   
    
    
//         public DefaultTableModel previousBookingsModel;
  //       public DefaultTableModel unpaidBookingsModel;
    public void initializeTables() {
        DefaultTableModel previousBookingsModel = new DefaultTableModel();
        DefaultTableModel unpaidBookingsModel = new DefaultTableModel(){
            Class[] types = new Class [] {
               java.lang.String.class, java.lang.Object.class, java.lang.Boolean.class,  java.lang.Object.class
            };
            
           boolean[] canEdit = new boolean [] {
                false,false, false, true, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
            
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
        cancelUnpaidBookingsModel = new DefaultTableModel(){
            Class[] types = new Class [] {
                java.lang.String.class,java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class
            };
            
           boolean[] canEdit = new boolean [] {
                false,false, true, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
            
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
            int i=0;
            
        };
        
        

// Define the table structure
        previousBookingsModel.addColumn("Bill No");
        previousBookingsModel.addColumn("Room No");
        previousBookingsModel.addColumn("Room Type");
        previousBookingsModel.addColumn("Start Date");
        previousBookingsModel.addColumn("End Date");
        previousBookingsModel.addColumn("Amount");

       ResultSet previousBookingsModelResult = bookingController.getPreviousBookings(Integer.parseInt(customer_id));
       ResultSet[] unpaidBookingsModelResult = bookingController.getUnpaidBookings(Integer.parseInt(customer_id));
       ResultSet[] cancelUnpaidBookingsModelResult = bookingController.getUnpaidBookings(Integer.parseInt(customer_id));
           
       try{
            while (previousBookingsModelResult.next()) {
                int BillNo = previousBookingsModelResult.getInt("bill_id");
                int roomNo = previousBookingsModelResult.getInt("room_no");
                String roomType = previousBookingsModelResult.getString("room_type");
                Date startDate = previousBookingsModelResult.getDate("start_date");
                Date endDate = previousBookingsModelResult.getDate("end_date");
                String totalAmount = previousBookingsModelResult.getString("Total_amount");

                // Add a row to the table model
                previousBookingsModel.addRow(new Object[]{BillNo, roomNo, roomType, startDate, endDate, totalAmount});
            }

            // Close result set and prepared statement
            previousBookingsModelResult.close();
       }catch(SQLException ex) {
            //model.addRow(new Object[]{null, null, "No data", null, null, null});
            // Handle exceptions
            ex.printStackTrace();
        }
        
       
        unpaidBookingsModel.addColumn("Type");
        unpaidBookingsModel.addColumn("Bill No");
        //unpaidBookingsModel.addColumn("Room No");
        unpaidBookingsModel.addColumn("Generate Bill");
        //unpaidBookingsModel.addColumn("Room Type");
        //unpaidBookingsModel.addColumn("Start Date");
        //unpaidBookingsModel.addColumn("End Date");
        unpaidBookingsModel.addColumn("Amount");
       

        try{
            while (unpaidBookingsModelResult[0].next()) {
                int BillNo = unpaidBookingsModelResult[0].getInt("bill_id");
                //int roomNo = unpaidBookingsModelResult[0].getInt("room_no");
               
                //String roomType = unpaidBookingsModelResult[0].getString("room_type");
                //Date startDate = unpaidBookingsModelResult[0].getDate("start_date");
                //Date endDate = unpaidBookingsModelResult[0].getDate("end_date");
                String totalAmount = unpaidBookingsModelResult[0].getString("Total_amount");

                // Add a row to the table model
                unpaidBookingsModel.addRow(new Object[]{"Room",BillNo ,false, totalAmount});
            }

            // Close result set and prepared statement
            unpaidBookingsModelResult[0].close();
       }catch(SQLException ex) {
            //model.addRow(new Object[]{null, null, "No data", null, null, null});
            // Handle exceptions
            ex.printStackTrace();
        }
        
        try{
            while (unpaidBookingsModelResult[1].next()) {
                int BillNo = unpaidBookingsModelResult[1].getInt("food_bill_id");
                //int roomNo = unpaidBookingsModelResult[0].getInt("room_no");
               
                //String roomType = unpaidBookingsModelResult[0].getString("room_type");
                //Date startDate = unpaidBookingsModelResult[0].getDate("start_date");
                //Date endDate = unpaidBookingsModelResult[0].getDate("end_date");
                String totalAmount = unpaidBookingsModelResult[1].getString("amount");

                // Add a row to the table model
                unpaidBookingsModel.addRow(new Object[]{"Food",BillNo ,false, totalAmount});
            }

            // Close result set and prepared statement
            unpaidBookingsModelResult[1].close();
       }catch(SQLException ex) {
            //model.addRow(new Object[]{null, null, "No data", null, null, null});
            // Handle exceptions
            ex.printStackTrace();
        }
        
        
        try{
            while (unpaidBookingsModelResult[2].next()) {
                int BillNo = unpaidBookingsModelResult[2].getInt("travel_bill_id");
                //int roomNo = unpaidBookingsModelResult[0].getInt("room_no");
               
                //String roomType = unpaidBookingsModelResult[0].getString("room_type");
                //Date startDate = unpaidBookingsModelResult[0].getDate("start_date");
                //Date endDate = unpaidBookingsModelResult[0].getDate("end_date");
                String totalAmount = unpaidBookingsModelResult[2].getString("amount");

                // Add a row to the table model
                unpaidBookingsModel.addRow(new Object[]{"Travel",BillNo ,false, totalAmount});
            }

            // Close result set and prepared statement
            unpaidBookingsModelResult[2].close();
       }catch(SQLException ex) {
            //model.addRow(new Object[]{null, null, "No data", null, null, null});
            // Handle exceptions
            ex.printStackTrace();
        }
        
        
        try{
            while (unpaidBookingsModelResult[3].next()) {
                int BillNo = unpaidBookingsModelResult[3].getInt("event_id");
                //int roomNo = unpaidBookingsModelResult[0].getInt("room_no");
               
                //String roomType = unpaidBookingsModelResult[0].getString("room_type");
                //Date startDate = unpaidBookingsModelResult[0].getDate("start_date");
                //Date endDate = unpaidBookingsModelResult[0].getDate("end_date");
                String totalAmount = unpaidBookingsModelResult[3].getString("amount");

                // Add a row to the table model
                unpaidBookingsModel.addRow(new Object[]{"Event",BillNo ,false, totalAmount});
            }

            // Close result set and prepared statement
            unpaidBookingsModelResult[3].close();
       }catch(SQLException ex) {
            //model.addRow(new Object[]{null, null, "No data", null, null, null});
            // Handle exceptions
            ex.printStackTrace();
        }
        
        
        
        
        
        cancelUnpaidBookingsModel.addColumn("Type");
        cancelUnpaidBookingsModel.addColumn("Bill No");
        cancelUnpaidBookingsModel.addColumn("Cancel");
        cancelUnpaidBookingsModel.addColumn("Room No");
        cancelUnpaidBookingsModel.addColumn("Room Type");
        cancelUnpaidBookingsModel.addColumn("Start Date");
        cancelUnpaidBookingsModel.addColumn("End Date");
        cancelUnpaidBookingsModel.addColumn("Amount");
       

        try{
            while (cancelUnpaidBookingsModelResult[0].next()) {
                int BillNo = cancelUnpaidBookingsModelResult[0].getInt("bill_id");
                int roomNo = cancelUnpaidBookingsModelResult[0].getInt("room_no");
               
                String roomType = cancelUnpaidBookingsModelResult[0].getString("room_type");
                Date startDate = cancelUnpaidBookingsModelResult[0].getDate("start_date");
                Date endDate = cancelUnpaidBookingsModelResult[0].getDate("end_date");
                String totalAmount = cancelUnpaidBookingsModelResult[0].getString("Total_amount");

                // Add a row to the table model
                cancelUnpaidBookingsModel.addRow(new Object[]{"Room",BillNo ,false,roomNo,roomType,startDate,endDate, totalAmount});
            }

            // Close result set and prepared statement
            cancelUnpaidBookingsModelResult[0].close();
       }catch(SQLException ex) {
            //model.addRow(new Object[]{null, null, "No data", null, null, null});
            // Handle exceptions
            ex.printStackTrace();
        }
        
        
        jTable2.setModel(previousBookingsModel); // Set the model for jTable2
        jTable3.setModel(unpaidBookingsModel); // Set the model for jTable2
        jTable1.setModel(cancelUnpaidBookingsModel); // Set the model for jTable2
    }
    
    
    public int[] getFirstColumnValuesForTrueBoolean() {
    int rowCount = cancelUnpaidBookingsModel.getRowCount();
    int[] values = new int[rowCount];
    int i=0;
    for (int row = 0; row < rowCount; row++) {
        Boolean isTrue = (Boolean) cancelUnpaidBookingsModel.getValueAt(row, 2); // Check the third column (index 2)
        Object firstColumnValue = cancelUnpaidBookingsModel.getValueAt(row, 1);
        if (isTrue) {
            values[i] = (int) firstColumnValue;

            i++;
        }
    }
    
    // Trim the values array to remove any unused elements
    return Arrays.copyOf(values, i);
    }
    
      

//jTable2.setModel(model); // Set the model for jTable2


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
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jButton20 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jButton13 = new javax.swing.JButton();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        jComboBox7 = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jButton8 = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton18 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jButton9 = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        jButton16 = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jButton17 = new javax.swing.JButton();
        jLabel35 = new javax.swing.JLabel();
        jComboBox15 = new javax.swing.JComboBox<>();
        jLabel36 = new javax.swing.JLabel();
        jComboBox16 = new javax.swing.JComboBox<>();
        jComboBox17 = new javax.swing.JComboBox<>();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jComboBox18 = new javax.swing.JComboBox<>();
        jLabel39 = new javax.swing.JLabel();
        jComboBox19 = new javax.swing.JComboBox<>();
        jLabel40 = new javax.swing.JLabel();
        jComboBox20 = new javax.swing.JComboBox<>();
        //jTextField1 = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jButton10 = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        jComboBox8 = new javax.swing.JComboBox<>();
        jLabel26 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jButton14 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jButton11 = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jComboBox9 = new javax.swing.JComboBox<>();
        jComboBox10 = new javax.swing.JComboBox<>();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();
        jComboBox11 = new javax.swing.JComboBox<>();
        jComboBox12 = new javax.swing.JComboBox<>();
        jCheckBox7 = new javax.swing.JCheckBox();
        jComboBox13 = new javax.swing.JComboBox<>();
        jComboBox21 = new javax.swing.JComboBox<>();
        jCheckBox8 = new javax.swing.JCheckBox();
        jButton15 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jButton12 = new javax.swing.JButton();
        jButton21 = new javax.swing.JButton();
        
        jLabel30 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jButton19 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        getContentPane().setLayout(new java.awt.CardLayout());

        jPanel1.setLayout(null);

        jButton1.setText("Cancel Room");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(300, 170, 180, 70);

        jButton2.setText("Book Room");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jPanel1.add(jButton2);
        jButton2.setBounds(40, 170, 180, 70);

        jButton3.setText("Book Food");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        jPanel1.add(jButton3);
        jButton3.setBounds(810, 170, 180, 70);

        jButton4.setText("Book Event");
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });
        jPanel1.add(jButton4);
        jButton4.setBounds(560, 170, 180, 70);

        jButton5.setText("Bill Generation");
        jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton5MouseClicked(evt);
            }
        });
        jPanel1.add(jButton5);
        jButton5.setBounds(1330, 170, 180, 70);

        jButton6.setText("Book Transportation");
        jButton6.setToolTipText("");
        jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton6MouseClicked(evt);
            }
        });
        jPanel1.add(jButton6);
        jButton6.setBounds(1070, 170, 180, 70);
        
        
          

        /*jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "S.No", "Room No", "No of Guests", "Start Date", "End Date", "Amount"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });*/
        
        //jTable2.setModel(model);
        jTable2.setRowHeight(40);
        jTable2.setUpdateSelectionOnSort(false);
        jTable2.setVerifyInputWhenFocusTarget(false);
        jScrollPane2.setViewportView(jTable2);

        jPanel1.add(jScrollPane2);
        jScrollPane2.setBounds(40, 300, 1470, 402);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Previous Bookings by You");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(630, 250, 300, 40);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel8.setText(customer_id);
        jPanel1.add(jLabel8);
        jLabel8.setBounds(230, 80, 100, 40);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel9.setText("Customer ID    :");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(50, 80, 190, 40);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel10.setText("Name    :");
        jPanel1.add(jLabel10);
        jLabel10.setBounds(370, 80, 100, 40);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel11.setText(name);
        jPanel1.add(jLabel11);
        jLabel11.setBounds(480, 80, 100, 40);

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel12.setText(phone_no);
        jPanel1.add(jLabel12);
        jLabel12.setBounds(880, 80, 220, 40);

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel13.setText("Phone Number    :");
        jPanel1.add(jLabel13);
        jLabel13.setBounds(670, 80, 200, 40);

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel14.setText(location);
        jPanel1.add(jLabel14);
        jLabel14.setBounds(1260, 80, 260, 40);

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel15.setText("Location    :");
        jPanel1.add(jLabel15);
        
        jLabel15.setBounds(1120, 80, 130, 40);

        jButton20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logout.png"))); // NOI18N
        jButton20.setText("Logout");
        jButton21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/back.png")));
        jButton21.setBounds(10, 10, 50, 50);
        jButton20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton20.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton20.setIconTextGap(0);
        jPanel1.add(jButton21);
        jButton20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton20MouseClicked(evt);
            }
        });
        jButton21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton21MouseClicked(evt);
            }
        });
        jPanel1.add(jButton20);
        jButton20.setBounds(1350, 20, 150, 50);

        getContentPane().add(jPanel1, "home");

        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel2MouseClicked(evt);
            }
        });
        jPanel2.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Book Room");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(640, 10, 330, 80);

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/back.png"))); // NOI18N
        jButton7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton7MouseClicked(evt);
            }
        });
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton7);
        jButton7.setBounds(10, 10, 50, 50);

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel16.setText("End Date");
        jPanel2.add(jLabel16);
        jLabel16.setBounds(540, 365, 230, 40);

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel17.setText("Room type");
        jPanel2.add(jLabel17);
        jLabel17.setBounds(540, 200, 220, 40);

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel18.setText("Start Date");
        jPanel2.add(jLabel18);
        jLabel18.setBounds(540, 290, 210, 40);

        jButton13.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton13.setText("Book");
        jPanel2.add(jButton13);
        jButton13.setBounds(740, 550, 100, 30);
        jButton13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton13MouseClicked(evt);
            }

            
        });
        
        
        

        jComboBox2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1 seater", "2 seater", "3 seater", "4 seater", "5 seater" }));
        jPanel2.add(jComboBox2);
        jComboBox2.setBounds(800, 207, 190, 31);

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel19.setText("Day");
        jPanel2.add(jLabel19);
        jLabel19.setBounds(800, 303, 43, 16);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30" }));
        jPanel2.add(jComboBox1);
        jComboBox1.setBounds(840, 300, 50, 22);

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel20.setText("Month");
        jPanel2.add(jLabel20);
        jLabel20.setBounds(900, 303, 43, 16);

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        jPanel2.add(jComboBox3);
        jComboBox3.setBounds(950, 300, 50, 22);

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2023", "2024", "2025" }));
        jPanel2.add(jComboBox4);
        jComboBox4.setBounds(1070, 300, 90, 22);

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel21.setText("Year");
        jPanel2.add(jLabel21);
        jLabel21.setBounds(1030, 303, 43, 16);

        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel22.setText("Day");
        jPanel2.add(jLabel22);
        jLabel22.setBounds(800, 380, 43, 16);

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30" }));
        jPanel2.add(jComboBox5);
        jComboBox5.setBounds(840, 380, 50, 22);

        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel23.setText("Month");
        jPanel2.add(jLabel23);
        jLabel23.setBounds(900, 380, 43, 16);

        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        jPanel2.add(jComboBox6);
        jComboBox6.setBounds(950, 380, 50, 22);

        jLabel24.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel24.setText("Year");
        jPanel2.add(jLabel24);
        jLabel24.setBounds(1030, 380, 43, 16);

        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2023", "2024", "2025" }));
        jPanel2.add(jComboBox7);
        jComboBox7.setBounds(1070, 380, 90, 22);

        getContentPane().add(jPanel2, "Book Room");

        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
        });
        jPanel3.setLayout(null);

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

        jLabel28.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("Cancel Room");
        jPanel3.add(jLabel28);
        jLabel28.setBounds(640, 10, 330, 80);

        /*jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                " S.No", "Cancel", "Room No ", "Room Type ", "Start Date", "End Date", "Amount"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });*/
        jTable1.setRowHeight(60);
        jScrollPane1.setViewportView(jTable1);

        jPanel3.add(jScrollPane1);
        jScrollPane1.setBounds(52, 100, 1430, 580);

        jButton18.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton18.setText("Send Request");
        jPanel3.add(jButton18);
        jButton18.setBounds(740, 700, 160, 30);
        jButton18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton18MouseClicked(evt);
            }
        });
        
        getContentPane().add(jPanel3, "Cancel Room");

        jPanel4.setLayout(null);

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/back.png"))); // NOI18N
        jButton9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton9MouseClicked(evt);
            }
        });
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton9);
        jButton9.setBounds(10, 10, 50, 50);

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("Book Event");
        jPanel4.add(jLabel27);
        jLabel27.setBounds(640, 10, 330, 80);

        jButton16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/back.png"))); // NOI18N
        jButton16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton16MouseClicked(evt);
            }
        });
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton16);
        jButton16.setBounds(10, 10, 50, 50);

        jLabel32.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel32.setText("End Date");
        jPanel4.add(jLabel32);
        jLabel32.setBounds(540, 365, 230, 40);

        jLabel33.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel33.setText("No of Attendees");
        jPanel4.add(jLabel33);
        jLabel33.setBounds(540, 200, 220, 40);

        jLabel34.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel34.setText("Start Date");
        jPanel4.add(jLabel34);
        jLabel34.setBounds(540, 290, 210, 40);

        jButton17.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton17.setText("Book");
        jPanel4.add(jButton17);
        jButton17.setBounds(740, 550, 100, 30);
        jButton17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton17MouseClicked(evt);
            }

            
        });

        jLabel35.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel35.setText("Day");
        jPanel4.add(jLabel35);
        jLabel35.setBounds(800, 303, 43, 16);

        jComboBox15.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30" }));
        jPanel4.add(jComboBox15);
        jComboBox15.setBounds(840, 300, 50, 22);

        jLabel36.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel36.setText("Month");
        jPanel4.add(jLabel36);
        jLabel36.setBounds(900, 303, 43, 16);

        jComboBox16.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        jPanel4.add(jComboBox16);
        jComboBox16.setBounds(950, 300, 50, 22);

        jComboBox17.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2023", "2024", "2025" }));
        jPanel4.add(jComboBox17);
        jComboBox17.setBounds(1070, 300, 90, 22);

        jLabel37.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel37.setText("Year");
        jPanel4.add(jLabel37);
        jLabel37.setBounds(1030, 303, 43, 16);

        jLabel38.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel38.setText("Day");
        jPanel4.add(jLabel38);
        jLabel38.setBounds(800, 380, 43, 16);

        jComboBox18.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30" }));
        jPanel4.add(jComboBox18);
        jComboBox18.setBounds(840, 380, 50, 22);

        jLabel39.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel39.setText("Month");
        jPanel4.add(jLabel39);
        jLabel39.setBounds(900, 380, 43, 16);

        jComboBox19.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        jPanel4.add(jComboBox19);
        jComboBox19.setBounds(950, 380, 50, 22);

        jLabel40.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel40.setText("Year");
        jPanel4.add(jLabel40);
        jLabel40.setBounds(1030, 380, 43, 16);

        jComboBox20.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2023", "2024", "2025" }));
        jPanel4.add(jComboBox20);
        jComboBox20.setBounds(1070, 380, 90, 22);

        //jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        //jPanel4.add(jTextField1);
        //jTextField1.setBounds(800, 207, 230, 40);

        jComboBox21.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jComboBox21.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "25", "50", "75", "100", "150" }));
        jPanel4.add(jComboBox21);
        jComboBox21.setBounds(800, 207, 230, 40);
        getContentPane().add(jPanel4, "Book Event");

        jPanel5.setLayout(null);

        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/back.png"))); // NOI18N
        jButton10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton10MouseClicked(evt);
            }
        });
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton10);
        jButton10.setBounds(10, 10, 50, 50);

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel25.setText("No of Days");
        jPanel5.add(jLabel25);
        jLabel25.setBounds(540, 200, 220, 40);

        jComboBox8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30" }));
        jPanel5.add(jComboBox8);
        jComboBox8.setBounds(800, 207, 190, 31);

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("Book Food");
        jPanel5.add(jLabel26);
        jLabel26.setBounds(640, 10, 330, 80);

        jCheckBox1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jCheckBox1.setText("Breakfast");
        jCheckBox1.setBorder(null);
        jCheckBox1.setIconTextGap(30);
        jCheckBox1.setPreferredSize(new java.awt.Dimension(143, 143));
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        jPanel5.add(jCheckBox1);
        jCheckBox1.setBounds(690, 300, 240, 60);

        jCheckBox2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jCheckBox2.setText("Lunch");
        jCheckBox2.setBorder(null);
        jCheckBox2.setIconTextGap(30);
        jCheckBox2.setPreferredSize(new java.awt.Dimension(143, 143));
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });
        jPanel5.add(jCheckBox2);
        jCheckBox2.setBounds(690, 390, 240, 60);

        jCheckBox3.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jCheckBox3.setText("Snacks");
        jCheckBox3.setBorder(null);
        jCheckBox3.setIconTextGap(30);
        jCheckBox3.setPreferredSize(new java.awt.Dimension(143, 143));
        jCheckBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox3ActionPerformed(evt);
            }
        });
        jPanel5.add(jCheckBox3);
        jCheckBox3.setBounds(690, 480, 240, 60);

        jCheckBox4.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jCheckBox4.setText("Dinner");
        jCheckBox4.setBorder(null);
        jCheckBox4.setIconTextGap(30);
        jCheckBox4.setPreferredSize(new java.awt.Dimension(143, 143));
        jCheckBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox4ActionPerformed(evt);
            }
        });
        jPanel5.add(jCheckBox4);
        jCheckBox4.setBounds(690, 570, 240, 60);

        jButton14.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton14.setText("Book");
        jPanel5.add(jButton14);
        jButton14.setBounds(740, 680, 100, 30);
        jButton14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton14MouseClicked(evt);
            }

            
        });

        getContentPane().add(jPanel5, "Book Food");

        jPanel6.setLayout(null);

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

        jLabel29.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("Book Transportation");
        jPanel6.add(jLabel29);
        jLabel29.setBounds(570, 10, 450, 80);

        jLabel31.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel31.setText("No of Days");
        jPanel6.add(jLabel31);
        jLabel31.setBounds(540, 200, 220, 40);

        jComboBox9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jComboBox9.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30" }));
        jPanel6.add(jComboBox9);
        jComboBox9.setBounds(800, 207, 190, 31);

        jComboBox10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jComboBox10.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5" }));
        jPanel6.add(jComboBox10);
        jComboBox10.setBounds(800, 607, 190, 31);

        jLabel41.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel41.setText("Jeep");
        //jLabel41.setIconTextGap(20);
        jPanel6.add(jLabel41);
        jLabel41.setBounds(550, 600, 160, 36);

        jLabel42.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel42.setText("Bus");
        //jLabel42.setIconTextGap(20);
        jPanel6.add(jLabel42);
        jLabel42.setBounds(550, 300, 160, 36);

        jComboBox11.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jComboBox11.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5" }));
        jPanel6.add(jComboBox11);
        jComboBox11.setBounds(800, 307, 190, 31);

        jComboBox12.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jComboBox12.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5" }));
        jPanel6.add(jComboBox12);
        jComboBox12.setBounds(800, 407, 190, 31);

        jLabel43.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel43.setText("Car");
        //jLabel43.setIconTextGap(20);
        jPanel6.add(jLabel43);
        jLabel43.setBounds(550, 400, 160, 36);

        jComboBox13.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jComboBox13.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5" }));
        jPanel6.add(jComboBox13);
        jComboBox13.setBounds(800, 507, 190, 31);

        jLabel44.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel44.setText("Van");
        //jLabel44.setIconTextGap(20);
        jPanel6.add(jLabel44);
        jLabel44.setBounds(550, 500, 160, 36);

        jButton15.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton15.setText("Book");
        jPanel6.add(jButton15);
        jButton15.setBounds(730, 680, 100, 30);
        jButton15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton15MouseClicked(evt);
            }

            
        });

        getContentPane().add(jPanel6, "Book Transportation");

        jPanel7.setLayout(null);
        
        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/back.png"))); // NOI18N
        jButton12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton12MouseClicked(evt);
            }
        });
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton12);
        
        jButton12.setBounds(10, 10, 50, 50);

        jLabel30.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("Bill Generation");
        jPanel7.add(jLabel30);
        jLabel30.setBounds(600, 10, 410, 80);

        /*jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                " S.No", "Bill Generate", "Room No ", "Bill ID", "Start Date", "End Date", "Amount"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });*/
        jTable3.setRowHeight(60);
        jScrollPane3.setViewportView(jTable3);

        jPanel7.add(jScrollPane3);
        jScrollPane3.setBounds(52, 100, 1430, 580);

        jButton19.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton19.setText("Generate Bill");
        jPanel7.add(jButton19);
        jButton19.setBounds(740, 700, 160, 30);

        getContentPane().add(jPanel7, "Bill Generation");

        pack();
    }// </editor-fold>                        

    private void jPanel2MouseClicked(java.awt.event.MouseEvent evt) {                                     

    }                                    

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {                                      
        // TODO add your handling code here:
                CardLayout cardLayout = (CardLayout) getContentPane().getLayout();
                cardLayout.show(getContentPane(), "Book Room");
    }                                     

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {                                      
        // TODO add your handling code here:
                CardLayout cardLayout = (CardLayout) getContentPane().getLayout();
        cardLayout.show(getContentPane(), "Cancel Room");
    }                                     

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {                                     
        // TODO add your handling code here:
                
    }                                    

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {                                      
        // TODO add your handling code here:
                CardLayout cardLayout = (CardLayout) getContentPane().getLayout();
        cardLayout.show(getContentPane(), "Book Event");
    }                                     

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {                                      
        // TODO add your handling code here:
                CardLayout cardLayout = (CardLayout) getContentPane().getLayout();
        cardLayout.show(getContentPane(), "Book Food");
    }                                     

    private void jButton6MouseClicked(java.awt.event.MouseEvent evt) {                                      
        // TODO add your handling code here:
                CardLayout cardLayout = (CardLayout) getContentPane().getLayout();
        cardLayout.show(getContentPane(), "Book Transportation");
    }                                     

    private void jButton5MouseClicked(java.awt.event.MouseEvent evt) {                                      
        // TODO add your handling code here:
                CardLayout cardLayout = (CardLayout) getContentPane().getLayout();
        cardLayout.show(getContentPane(), "Bill Generation");
    }                                     

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
    }                                        

    private void jButton7MouseClicked(java.awt.event.MouseEvent evt) {                                      
        // TODO add your handling code here:
        CardLayout cardLayout = (CardLayout) getContentPane().getLayout();
        cardLayout.show(getContentPane(), "home");
        
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
    
    private void jButton13MouseClicked(java.awt.event.MouseEvent evt) {                                      
        // TODO add your handling code here:
        String start_s=(String) jComboBox4.getSelectedItem();
        String end_s=(String) jComboBox7.getSelectedItem();
        
        
        int room_type = jComboBox2.getSelectedIndex()+1;
        int start_day = jComboBox1.getSelectedIndex()+1;
        int start_month = jComboBox3.getSelectedIndex()+1;
        int start_year = Integer.parseInt(start_s);
        int end_day = jComboBox5.getSelectedIndex()+1;
        int end_month = jComboBox6.getSelectedIndex()+1;
        int end_year = Integer.parseInt(end_s);
        int cust_id=Integer.parseInt(customer_id);

        Calendar calendar_start = Calendar.getInstance();
        calendar_start.set(Calendar.YEAR, start_year);
        calendar_start.set(Calendar.MONTH, start_month - 1); // Adjust for the 0-based month
        calendar_start.set(Calendar.DAY_OF_MONTH, start_day);
        Date start = calendar_start.getTime();

        Calendar calendar_end = Calendar.getInstance();
        calendar_end.set(Calendar.YEAR, end_year);
        calendar_end.set(Calendar.MONTH, end_month - 1); // Adjust for the 0-based month
        calendar_end.set(Calendar.DAY_OF_MONTH, end_day);
        Date end = calendar_end.getTime();
        
        java.sql.Date start_date = new java.sql.Date(start.getTime());
        java.sql.Date end_date = new java.sql.Date(end.getTime());
        java.sql.Date current_date = new java.sql.Date(System.currentTimeMillis());

        
        if (start_date.compareTo(current_date) >= 0) {
            if (start_date.compareTo(end_date) <= 0) {
                ResultSet result = bookingController.BookRooms(cust_id, room_type, start_date, end_date);
                if(result!=null){
                    try {
                        while (result.next()) {

                            int BillNo = result.getInt("bill_id");
                            int roomNo = result.getInt("room_no");
                            String roomType = result.getString("room_type");
                            Date startDate = result.getDate("start_date");
                            Date endDate = result.getDate("end_date");
                            String totalAmount = result.getString("Total_amount");
                            JOptionPane.showMessageDialog(null, "Room No : "+roomNo+"\nRoom Type : "+roomType+"\nStart Date : "+startDate+"\nEnd Date : "+endDate+"\nAmount : "+totalAmount, "Room Booked", JOptionPane.INFORMATION_MESSAGE);


                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(HomeView.class.getName()).log(Level.SEVERE, null, ex);

                    }

                }else{
                    JOptionPane.showMessageDialog(null, "Room not available", "Room Not Booked", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
        // Show an error message if start_date is greater than end_date
                JOptionPane.showMessageDialog(null, "Start date must be less than or equal to end date", "Invalid Date Range", JOptionPane.ERROR_MESSAGE);
            }
        } else {
    // Show an error message if start_date is earlier than the current date
            JOptionPane.showMessageDialog(null, "Start date must be greater than or equal to the current date", "Invalid Start Date", JOptionPane.ERROR_MESSAGE);
        }
        
        
        
    }
    
    private void jButton17MouseClicked(java.awt.event.MouseEvent evt) {                                      
        // TODO add your handling code here:
        String start_s=(String) jComboBox17.getSelectedItem();
        String end_s=(String) jComboBox20.getSelectedItem();
        
        
        int room_type = (jComboBox21.getSelectedIndex()+1)+5;
        int start_day = jComboBox15.getSelectedIndex()+1;
        int start_month = jComboBox16.getSelectedIndex()+1;
        int start_year = Integer.parseInt(start_s);
        int end_day = jComboBox18.getSelectedIndex()+1;
        int end_month = jComboBox19.getSelectedIndex()+1;
        int end_year = Integer.parseInt(end_s);
        int cust_id=Integer.parseInt(customer_id);

        Calendar calendar_start = Calendar.getInstance();
        calendar_start.set(Calendar.YEAR, start_year);
        calendar_start.set(Calendar.MONTH, start_month - 1); // Adjust for the 0-based month
        calendar_start.set(Calendar.DAY_OF_MONTH, start_day);
        Date start = calendar_start.getTime();

        Calendar calendar_end = Calendar.getInstance();
        calendar_end.set(Calendar.YEAR, end_year);
        calendar_end.set(Calendar.MONTH, end_month - 1); // Adjust for the 0-based month
        calendar_end.set(Calendar.DAY_OF_MONTH, end_day);
        Date end = calendar_end.getTime();
        
        java.sql.Date start_date = new java.sql.Date(start.getTime());
        java.sql.Date end_date = new java.sql.Date(end.getTime());
        java.sql.Date current_date = new java.sql.Date(System.currentTimeMillis());

        
        if (start_date.compareTo(current_date) >= 0) {
            if (start_date.compareTo(end_date) <= 0) {
                ResultSet result = bookingController.BookEvents(cust_id, room_type, start_date, end_date);
                if(result!=null){
                    try {
                        while (result.next()) {

                            int BillNo = result.getInt("event_id");
                            int roomNo = result.getInt("hall");
                            int roomType = result.getInt("no_of_attendees");
                            Date startDate = result.getDate("event_start");
                            Date endDate = result.getDate("event_end");
                            String totalAmount = result.getString("amount");
                            JOptionPane.showMessageDialog(null, "Hall no : "+roomNo+"\nHall Type : "+roomType+"\nStart Date : "+startDate+"\nEnd Date : "+endDate+"\nAmount : "+totalAmount, "Hall Booked", JOptionPane.INFORMATION_MESSAGE);


                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(HomeView.class.getName()).log(Level.SEVERE, null, ex);

                    }

                }else{
                    JOptionPane.showMessageDialog(null, "Hall not available", "Hall Not Booked", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
        // Show an error message if start_date is greater than end_date
                JOptionPane.showMessageDialog(null, "Start date must be less than or equal to end date", "Invalid Date Range", JOptionPane.ERROR_MESSAGE);
            }
        } else {
    // Show an error message if start_date is earlier than the current date
            JOptionPane.showMessageDialog(null, "Start date must be greater than or equal to the current date", "Invalid Start Date", JOptionPane.ERROR_MESSAGE);
        }
        
        
        
    }
    
    
    
    
    
    public void jButton18MouseClicked(java.awt.event.MouseEvent evt) {
        int[] bill_no_s = getFirstColumnValuesForTrueBoolean();
        boolean done=bookingController.cancelRequestsSend(bill_no_s);
        if(done){
            JOptionPane.showMessageDialog(null, "Cancel Request Sent", "Request Sent", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(null, "Cancel Request Not Sent", "Request not Sent", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    private void jButton14MouseClicked(java.awt.event.MouseEvent evt) {
        String days=(String) jComboBox8.getSelectedItem();
        boolean breakfast=jCheckBox1.isSelected();
        boolean lunch=jCheckBox2.isSelected();
        boolean snacks=jCheckBox3.isSelected();
        boolean dinner=jCheckBox4.isSelected();
        int cust_id=Integer.parseInt(customer_id);
        int no_of_days = Integer.parseInt(days);
        
        
        boolean done=bookingController.BookFoods(cust_id, no_of_days, breakfast, lunch, snacks, dinner);
        
        if(done){
            JOptionPane.showMessageDialog(null, "Food Booking successful", "Booked", JOptionPane.INFORMATION_MESSAGE);

        }
        else{
            JOptionPane.showMessageDialog(null, "Something Error", "Not Booked", JOptionPane.INFORMATION_MESSAGE);
        }
        
    
    }
    
    private void jButton15MouseClicked(java.awt.event.MouseEvent evt) {
        String days=(String) jComboBox8.getSelectedItem();
        int bus=jComboBox11.getSelectedIndex();
        int car=jComboBox12.getSelectedIndex();
        int van=jComboBox13.getSelectedIndex();
        int jeep=jComboBox10.getSelectedIndex();
        int cust_id=Integer.parseInt(customer_id);
        int no_of_days = Integer.parseInt(days);
        
        
        boolean done=bookingController.BookTransport(cust_id, no_of_days, bus, car, van, jeep);
        
        if(done){
            JOptionPane.showMessageDialog(null, "Transportation Booking successful", "Booked", JOptionPane.INFORMATION_MESSAGE);

        }
        else{
            JOptionPane.showMessageDialog(null, "Something Error", "Not Booked", JOptionPane.INFORMATION_MESSAGE);
        }
        
    
    }

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
    }                                          

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
    }                                          

    private void jCheckBox3ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
    }                                          

    private void jCheckBox4ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
    }                                          

    private void jButton16MouseClicked(java.awt.event.MouseEvent evt) {                                       
        // TODO add your handling code here:
    }                                      

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
    }                                         

    private void jButton20MouseClicked(java.awt.event.MouseEvent evt) {                                       
        // TODO add your handling code here:
                        
                        SignUpView SignUpView = new SignUpView();
                        SignUpView.setVisible(true);
                        this.dispose();
    }
    
    private void jButton21MouseClicked(java.awt.event.MouseEvent evt) {                                       
        // TODO add your handling code here:
                        
                   HomeView homeView = new HomeView(credentials_for_refresh);
                    homeView.setVisible(true);
                    this.dispose();
    }

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JButton jButton21;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JCheckBox jCheckBox7;
    private javax.swing.JCheckBox jCheckBox8;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox10;
    private javax.swing.JComboBox<String> jComboBox11;
    private javax.swing.JComboBox<String> jComboBox12;
    private javax.swing.JComboBox<String> jComboBox13;
    private javax.swing.JComboBox<String> jComboBox15;
    private javax.swing.JComboBox<String> jComboBox16;
    private javax.swing.JComboBox<String> jComboBox17;
    private javax.swing.JComboBox<String> jComboBox18;
    private javax.swing.JComboBox<String> jComboBox19;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox20;
    private javax.swing.JComboBox<String> jComboBox21;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JComboBox<String> jComboBox8;
    private javax.swing.JComboBox<String> jComboBox9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    //private javax.swing.JTextField jTextField1;
    // End of variables declaration                   
}
