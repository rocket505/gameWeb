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
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Ajump
 */
@ManagedBean(name = "buyGame")
@SessionScoped
public class buyGame implements Serializable {

    /**
     * Creates a new instance of buyGame
     */
    private String username;
    private String name;
    private double price;
    private Date date;
    private String fullname;
    private String address;
    private int quantity;
    protected DbConnection Db_connect;
    public buyGame() {
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

    public java.sql.Date getDate() {
        java.util.Date today = new java.util.Date();
        date =  new java.sql.Date(today.getTime());
    
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    
    public String buy_game(String user,String name,double price,Date date,String fullname,String address) throws Exception {
        int i = 0;
       
        if (user != null) {
            PreparedStatement ps = null;
            Connection con = null;
            Db_connect = new DbConnection();
            try {
                if (Db_connect != null) {
                    con = Db_connect.get_connection();
                    if (con != null) {
                         buyGame bGame = new buyGame();
                        String sql = "INSERT INTO purchase_history(username, name, price, date, fullname, address) VALUES (?,?,?,?,?,?)";
                        ps = con.prepareStatement(sql);
                        ps.setString(1, user);
                        ps.setString(2, name);
                        ps.setDouble(3, price);
                        ps.setDate(4, date);
                        ps.setString(5, fullname);
                        ps.setString(6, address);
                        i = ps.executeUpdate();
                        bGame.update_Quantity(name);
                        System.out.println("Data Added Successfully");
                    }
                }
            } catch (SQLException e) {
                System.out.println(e);
            }   
        }
        return "/gameList.xhtml?faces-redirect=true";  
   }
    
    public String update_Quantity(String gameName){
        System.out.println(gameName);
        try {
            Db_connect = new DbConnection();
            Connection connection = Db_connect.get_connection();
            String sql = "UPDATE list SET quantity=? WHERE name =" + gameName;         
            PreparedStatement ps = connection.prepareStatement(sql);
            
            quantity = quantity - 1;
            
            ps.setInt(1, quantity);
            System.out.println(ps);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
       return "/adminHome.xhtml?faces-redirect=true";   
    }
    
}
