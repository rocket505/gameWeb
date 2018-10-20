/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gameweb;

import java.sql.Connection;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;


/**
 *
 * @author Ajump
 */
@ManagedBean(name = "loginManagedBean")
@SessionScoped
public class loginManagedBean implements Serializable {
    
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
     * Creates a new instance of loginManagedBean
     */
    public loginManagedBean() {
        //DbConnection Db_connect = new DbConnection();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getDbPassword() {
        return dbPassword;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
 
    
    public void dbData(String user) {
        loginManagedBean obj_login = null;
        if (user != null) {           
            Connection con = null;
            Db_connect = new DbConnection();
            if (Db_connect != null) {
                try {
                    con = Db_connect.get_connection();
                    if (con != null) {
                        obj_login = new loginManagedBean();
                        String sql = "SELECT * FROM cust_details WHERE username = '"+ user + "'";
                        PreparedStatement ps = con.prepareStatement(sql);
                        ResultSet rs = ps.executeQuery();
                        rs.next();
                        
                        obj_login.setId(rs.getInt("id"));
                        obj_login.setName(rs.getString("name"));
                        obj_login.setUsername(rs.getString("username"));
                        obj_login.setPassword(rs.getString("password"));
                        obj_login.setPhone(rs.getString("phone"));
                        obj_login.setAddress(rs.getString("address"));
                        obj_login.setEmail(rs.getString("email"));
                        
                        dbUser = rs.getString("username");
                        dbPassword = rs.getString("password");
                        
                        sessionMap.put("userDetail", obj_login);
                        
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
    }

    public String login(){
        dbData(username);
        
        if (username.equals(dbUser) && password.equals(dbPassword)){
        
              if(username.equals("admin") && password.equals("admin"))
                  return "Admin";
              else
                return "user";
        }   
                  
        else
            return "invalid";
        
    }

    
    public void logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        FacesContext.getCurrentInstance()
                .getApplication().getNavigationHandler()
                .handleNavigation(FacesContext.getCurrentInstance(), null, "/login.xhtml");
    }
    
    public String edit_User(int userid){
        
        loginManagedBean obj_login = null;
        System.out.println(userid);
		
        try{
            Db_connect = new DbConnection();
            Connection connection = Db_connect.get_connection();
            Statement st = connection.createStatement();  
            ResultSet rs= st.executeQuery("SELECT * FROM cust_details WHERE id = "+userid);
            rs.next();
            obj_login = new loginManagedBean();
            obj_login.setId(rs.getInt("id"));
            obj_login.setName(rs.getString("name"));
            obj_login.setUsername(rs.getString("username"));
            obj_login.setPassword(rs.getString("password"));
            obj_login.setPhone(rs.getString("phone"));
            obj_login.setAddress(rs.getString("address"));
            obj_login.setEmail(rs.getString("email"));
            sessionMap.put("userDetail", obj_login);
            
        }catch(Exception e){
            System.out.println(e);
        }       
        return "/editprofile.xhtml?faces-redirect=true";
    }
    public String updateUser(int userid){
        try {
            Db_connect = new DbConnection();
            Connection connection = Db_connect.get_connection();
            String sql = "UPDATE cust_details SET name=?, username=?, password=?,phone=?,address=?,email=? WHERE id =" + userid;         
            PreparedStatement ps = connection.prepareStatement(sql);
            
            ps.setString(1, name);
            ps.setString(2, username);
            ps.setString(3, password);
            ps.setString(4, phone);
            ps.setString(5, address);
            ps.setString(6, email);
            System.out.println(ps);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
       return "/userMain.xhtml?faces-redirect=true";   
    }
}
