/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gameweb;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;


/**
 *
 * @author Ajump
 */
@ManagedBean(name = "purchaseHistory")
@SessionScoped
public class purchaseHistory implements Serializable {

    private int id;
    private String username;
    private String name;
    private double price;
    private Date date;
    private String fullname;
    private String address;
    private DbConnection Db_connect;
    private String searchAdmin;
    private String searchUser;
    private loginManagedBean lmb;

    public loginManagedBean getLmb() {
        return lmb;
    }

    public void setLmb(loginManagedBean lmb) {
        this.lmb = lmb;
    }
    
    /**
     * Creates a new instance of purchaseHistory
     */
    public purchaseHistory() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSearchAdmin() {
        return searchAdmin;
    }

    public void setSearchAdmin(String searchAdmin) {
        this.searchAdmin = searchAdmin;
    }

    public String getSearchUser() {
        return searchUser;
    }

    public void setSearchUser(String searchUser) {
        this.searchUser = searchUser;
    }
    
    
    
        //get all game List
    public ArrayList<purchaseHistory> getGetpurchaseHistory() throws Exception{
           
           ArrayList<purchaseHistory> adminView_purchaseHistory = new ArrayList<>();
           Connection connection=null;
           String sql = "";
           try {
            Db_connect = new DbConnection();
            connection= Db_connect.get_connection();
            Statement st=connection.createStatement();
            
            if(searchAdmin != null)
                sql = "SELECT * FROM purchase_history WHERE username LIKE '%"+ searchAdmin + "%'"
                        + "OR name LIKE '%"+ searchAdmin + "%'"
                        + "OR fullname LIKE '%"+ searchAdmin + "%'"
                        + "OR address LIKE '%"+ searchAdmin + "%'"
                        + "OR date LIKE '%"+ searchAdmin + "%'";
            else
                sql = "SELECT * FROM purchase_history";
            
            ResultSet rs=st.executeQuery(sql);
            while(rs.next()){
                purchaseHistory obj_purchaseHistory = new purchaseHistory();
                obj_purchaseHistory.setId(rs.getInt("id"));
                obj_purchaseHistory.setUsername(rs.getString("username"));
                obj_purchaseHistory.setName(rs.getString("name"));
                obj_purchaseHistory.setPrice(rs.getDouble("price"));
                obj_purchaseHistory.setDate(rs.getDate("date"));
                obj_purchaseHistory.setFullname(rs.getString("fullname"));
                obj_purchaseHistory.setAddress(rs.getString("address"));
                adminView_purchaseHistory.add(obj_purchaseHistory);
            }
        } catch (Exception e) {
            System.out.println(e);
        }/*finally{
            if(connection!=null){
                connection.close();
            }
        }*/
        return adminView_purchaseHistory;
    }
    
    public ArrayList<purchaseHistory> getUserPurchaseHistory() throws Exception{
        
        ArrayList<purchaseHistory> userView_purchaseHistory = new ArrayList<>();
           Connection connection=null;
           String sql = "";
           try {
            Db_connect = new DbConnection();
            connection= Db_connect.get_connection();
            Statement st=connection.createStatement();
            
            if(searchUser != null)
                sql = "SELECT * FROM purchase_history WHERE name LIKE '%"+ searchUser + "%'"
                        + "OR date LIKE '%"+ searchUser + "%'"
                        + "OR fullname LIKE '%"+ searchUser + "%'"
                        + "OR address LIKE '%"+ searchUser + "%'";
            else
                sql = "SELECT * FROM purchase_history WHERE username = 'azam'";
            
            ResultSet rs=st.executeQuery(sql);
            while(rs.next()){
                purchaseHistory obj_purchaseHistory = new purchaseHistory();
                obj_purchaseHistory.setId(rs.getInt("id"));
                obj_purchaseHistory.setUsername(rs.getString("username"));
                obj_purchaseHistory.setName(rs.getString("name"));
                obj_purchaseHistory.setPrice(rs.getDouble("price"));
                obj_purchaseHistory.setDate(rs.getDate("date"));
                obj_purchaseHistory.setFullname(rs.getString("fullname"));
                obj_purchaseHistory.setAddress(rs.getString("address"));
                userView_purchaseHistory.add(obj_purchaseHistory);
            }
        } catch (Exception e) {
            System.out.println(e);
        }/*finally{
            if(connection!=null){
                connection.close();
            }
        }*/
        return userView_purchaseHistory;
        
    }
}
