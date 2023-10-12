/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author gandh
 */
public class SignUpModel {
    private String userId;
    private String userName;
    //private String password;
    private String address;
    private String phoneNo;

    // Constructor
    public SignUpModel(String userName, String address, String phoneNo,String userId) {
        this.userId=userId;
        this.userName = userName;
        this.address = address;
        this.phoneNo = phoneNo;
    }
    
    // Getters and setters for the fields
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    // Additional methods for validation or data processing can be added here
}
