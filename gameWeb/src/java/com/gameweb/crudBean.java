/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gameweb;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 *
 * @author Ajump
 */
@ManagedBean(name = "crudBean")
@SessionScoped
public class crudBean implements Serializable {

    private int id;
    private String name;
    private String username;
    private String password;
    private String phone;
    private String address;
    private String email;
    private String dbPassword;
    private String dbUser;
    protected DbConnection Db_connect;
      private Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
    /**
     * Creates a new instance of crudBean
     */
    public crudBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public String getDbUser() {
        return dbUser;
    }
    
    public String add() throws Exception {
        int i = 0;
        if (name != null) {
            PreparedStatement ps = null;
            Connection con = null;
            Db_connect = new DbConnection();
            try {
                if (Db_connect != null) {
                    con = Db_connect.get_connection();
                    if (con != null) {
                        String sql = "INSERT INTO cust_details(name, username ,password, phone, address ,email) VALUES(?,?,?,?,?,?)";
                        ps = con.prepareStatement(sql);
                        ps.setString(1, name);
                        ps.setString(2, username);
                        ps.setString(3, password);
                        ps.setString(4, phone);
                        ps.setString(5, address);
                        ps.setString(6, email);
                        i = ps.executeUpdate();
                        System.out.println("Data Added Successfully");
                    }
                }
            } catch (SQLException e) {
                System.out.println(e);
            }   
        }
        
            return "success";
    }
   
    
    
    
}
