/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gameweb;


import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Ajump
 */
@ManagedBean(name = "userWishlist")
@SessionScoped
public class userWishlist implements Serializable {

    /**
     * Creates a new instance of userWishlist
     */
    private int id;
    private String username;
    private String name;
    private double price;
    private DbConnection Db_connect;
    private String search; 
    
    public userWishlist() {
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

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
    
    
    public ArrayList<userWishlist> getUserWishlist(String user) throws Exception{
        
        ArrayList<userWishlist> userView_Wishlist = new ArrayList<>();
           Connection connection=null;
           String sql = "";
           try {
            Db_connect = new DbConnection();
            connection= Db_connect.get_connection();
            Statement st=connection.createStatement();
            
            if(search != null)
                sql = "SELECT * FROM wishlist WHERE name = '%"+search+"%'";
            else
                sql = "SELECT * FROM wishlist WHERE username ="+ user;
            
            ResultSet rs=st.executeQuery(sql);
            while(rs.next()){
                userWishlist obj_wishlist = new userWishlist();
                obj_wishlist.setId(rs.getInt("id"));
                obj_wishlist.setUsername(rs.getString("username"));
                obj_wishlist.setName(rs.getString("name"));
                obj_wishlist.setPrice(rs.getDouble("price"));
                userView_Wishlist.add(obj_wishlist);
            }
        } catch (Exception e) {
            System.out.println(e);
        }/*finally{
            if(connection!=null){
                connection.close();
            }
        }*/
        return userView_Wishlist;
        
    }
    
}
