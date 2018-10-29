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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 *
 * @author User
 */
@ManagedBean(name = "list")
@SessionScoped
public class list implements Serializable {
    
    private int id;
    private String name;
    private String description;
    private double price;
    private String username;
    private String fullname;
    private String address;
    private java.sql.Date date;
    protected DbConnection Db_connect;
    /**
     * Creates a new instance of list
     */
    public list() {
    }
    
     public list(int id, String name, String description, double price, String username) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }
    
    public String getUsername() {
        return username;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    public void setUsername(String username) {
        this.username =  username;
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

    public java.sql.Date getDate() {
        

        java.util.Date today = new java.util.Date();
        date =  new java.sql.Date(today.getTime());
    
        return date;
    }

    public void setGDate(java.sql.Date GetCurrentDate) {
        this.date = GetCurrentDate;
    }
    
    
    private Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap(); 
    
     public ArrayList getGet_all_game() throws Exception{
        ArrayList list_of_game=new ArrayList();
             Connection connection=null;
        try {
            DbConnection obj_DB_connection=new DbConnection();
            connection=obj_DB_connection.get_connection();
            Statement st=connection.createStatement();
            ResultSet rs=st.executeQuery("select * from list");
            while(rs.next()){
                list game_list=new list();
                game_list.setId(rs.getInt("id"));
                game_list.setName(rs.getString("name"));
                game_list.setDescription(rs.getString("description"));
                game_list.setPrice(rs.getDouble("price"));
                list_of_game.add(game_list);
            }
        } catch (Exception e) {
            System.out.println(e);
        }finally{
            if(connection!=null){
                connection.close();
            }
        }
        return list_of_game;
}
     
     
     public String getGame_info(){
     FacesContext fc = FacesContext.getCurrentInstance();
     Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
     
     String field_id = params.get("action");
     try{
         DbConnection obj_DB_connection=new DbConnection();
          Connection connection=obj_DB_connection.get_connection();
          Statement st=connection.createStatement();
          ResultSet rs=st.executeQuery("select * from list where id="+field_id);
          list ls_game=new list();
          rs.next();
          ls_game.setId(rs.getInt("id"));
          ls_game.setName(rs.getString("name"));
          ls_game.setDescription(rs.getString("description"));
          ls_game.setPrice(rs.getDouble("price"));
          sessionMap.put("gameInfo", ls_game);  
      } catch (Exception e) {
            System.out.println(e);
      }
     return "/gameInfo.xhtml";
     }
     /*
       public String buy_game(String user,String gameName,double price,java.sql.Date date,String fullname,String address){
     try{
         DbConnection obj_DB_connection=new DbConnection();
          Connection connection=obj_DB_connection.get_connection();
          
          String sql = "INSERT INTO purchase_history(username, name, price, date, fullname, address) VALUES(?,?,?,?,?,?)";
          PreparedStatement ps = connection.prepareStatement(sql);
          ps.setString(1, user);
          ps.setString(2, gameName);
          ps.setDouble(3, price);
          ps.setDate(4, date);
          ps.setString(5, fullname);
          ps.setString(6, address);
          ps.executeUpdate();
          System.out.println("Data Added Successfully");
        
          
      } catch (Exception e) {
            System.out.println(e);
      }
     return "/gameList.xhtml";
     }*/
       
       public String buy_game(String user) throws Exception {
        int i = 0;
        
        if (user != null) {
            PreparedStatement ps = null;
            Connection con = null;
            Db_connect = new DbConnection();
            try {
                if (Db_connect != null) {
                    con = Db_connect.get_connection();
                    if (con != null) {
                        String sql = "INSERT INTO purchase_history(username, name, price, date, fullname, address) VALUES (?,?,?,?,?,?)";
                        ps = con.prepareStatement(sql);
                        ps.setString(1, user);
                        ps.setString(2, name);
                        ps.setDouble(3, price);
                        ps.setDate(4, date);
                        ps.setString(5, fullname);
                        ps.setString(6, address);
                        i = ps.executeUpdate();
                        System.out.println("Data Added Successfully");
                    }
                }
            } catch (SQLException e) {
                System.out.println(e);
            }   
        }
        return "/gameList.xhtml?faces-redirect=true";  
   }
       

         public String wishlist_game(String user){
         
         PreparedStatement ps = null;
         Connection con = null;
         Db_connect = new DbConnection();
        try{
          if (Db_connect != null) {
                    con = Db_connect.get_connection();
                    if (con != null) {
                    String sql = "INSERT INTO wishlist(username, name, price) VALUES(?,?,?)";
                    ps = con.prepareStatement(sql);
                    ps.setString(1, user);
                    ps.setString(2, name);
                    ps.setDouble(3, price);
                    ps.executeUpdate();
                    System.out.println("Data Added Successfully");
        
                    }
          
            }
        } catch (Exception e) {
            System.out.println(e);
      }
            return "gameInfo.xhtml";
     }
         
     public ArrayList getUserGame(String user) throws Exception{
        ArrayList user_game=new ArrayList();
             Connection connection=null;
        try {
            DbConnection obj_DB_connection=new DbConnection();
            connection=obj_DB_connection.get_connection();
            Statement st=connection.createStatement();
            ResultSet rs=st.executeQuery("select * from purchase_history where username =" +user);
            while(rs.next()){
                list game_list=new list();
                game_list.setId(rs.getInt("id"));
                game_list.setName(rs.getString("name"));
                game_list.setPrice(rs.getDouble("price"));
                user_game.add(game_list);
            }
        } catch (Exception e) {
            System.out.println(e);
        }finally{
            if(connection!=null){
                connection.close();
            }
        }
        return user_game;
     }
    
     public String getToBuy(){
         return "/buy.xhtml";
     }
}
