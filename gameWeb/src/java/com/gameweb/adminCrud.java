/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gameweb;



import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Ajump
 */
@ManagedBean(name = "adminCrud")
@SessionScoped
public class adminCrud implements Serializable {

    private int id;
    private String name;
    private String description;
    private double price;
    private String type;
    private String developer;
    private String publisher;
    private int quantity;
    private String search;
    private DbConnection Db_connect;
    private Map<String,Object> sessionMapp = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

    
    public adminCrud() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }


    
   //add game into table list
    public String add_Game() throws Exception {
        int i = 0;
        if (name != null) {
            PreparedStatement ps = null;
            Connection con = null;
            Db_connect = new DbConnection();
            try {
                if (Db_connect != null) {
                    con = Db_connect.get_connection();
                    if (con != null) {
                        String sql = "INSERT INTO list(name, description ,price, type, developer ,publisher, quantity) VALUES(?,?,?,?,?,?,?)";
                        ps = con.prepareStatement(sql);
                        ps.setString(1, name);
                        ps.setString(2, description);
                        ps.setDouble(3, price);
                        ps.setString(4, type);
                        ps.setString(5, developer);
                        ps.setString(6, publisher);
                        ps.setInt(7, quantity);
                        i = ps.executeUpdate();
                        System.out.println("Data Added Successfully");
                    }
                }
            } catch (SQLException e) {
                System.out.println(e);
            }   
        }
        return "/adminHome.xhtml?faces-redirect=true";  
   }
    
    //get all game List
    public ArrayList<adminCrud> getGet_all_game() throws Exception{
           
           ArrayList<adminCrud> list_of_game = new ArrayList<>();
           Connection connection=null;
           String sql = "";
           try {
            Db_connect = new DbConnection();
            connection= Db_connect.get_connection();
            Statement st=connection.createStatement();
            
            if(search != null)
                sql = "SELECT * FROM list WHERE name = '&"+ search + "&' "
                        + "OR WHERE description = '&"+ search + "&'"
                        + "OR WHERE type = '&"+ search + "&'"
                        + "WHERE developer = '&"+ search + "&'"
                        + "WHERE publisher = '&"+ search + "&'";
            else
                sql = "SELECT * FROM list";
            
            ResultSet rs=st.executeQuery(sql);
            while(rs.next()){
                adminCrud obj_AdminCrud = new adminCrud();
                obj_AdminCrud.setId(rs.getInt("id"));
                obj_AdminCrud.setName(rs.getString("name"));
                obj_AdminCrud.setDescription(rs.getString("description"));
                obj_AdminCrud.setPrice(rs.getDouble("price"));
                obj_AdminCrud.setType(rs.getString("type"));
                obj_AdminCrud.setDeveloper(rs.getString("developer"));
                obj_AdminCrud.setPublisher(rs.getString("publisher"));
                obj_AdminCrud.setQuantity(rs.getInt("quantity"));
                list_of_game.add(obj_AdminCrud);
            }
        } catch (Exception e) {
            System.out.println(e);
        }/*finally{
            if(connection!=null){
                connection.close();
            }
        }*/
        return list_of_game;
    }
    // Used to fetch record to update
    
   
    public String editGameDetails(int gameid) {
        
        adminCrud obj_AdminCrud = null;
        System.out.println(gameid);
		
        try{
            Db_connect = new DbConnection();
            Connection connection = Db_connect.get_connection();
            Statement st = connection.createStatement();  
            ResultSet rs= st.executeQuery("SELECT * FROM list WHERE id = "+gameid);
            rs.next();
            obj_AdminCrud = new adminCrud();
            obj_AdminCrud.setId(rs.getInt("id"));
            obj_AdminCrud.setName(rs.getString("name"));
            obj_AdminCrud.setDescription(rs.getString("description"));
            obj_AdminCrud.setPrice(rs.getDouble("price"));
            obj_AdminCrud.setType(rs.getString("type"));
            obj_AdminCrud.setDeveloper(rs.getString("developer"));
            obj_AdminCrud.setPublisher(rs.getString("publisher"));
            sessionMapp.put("gameDetail", obj_AdminCrud);
           
            
        }catch(Exception e){
            System.out.println(e);
        }       
        return "/editGame.xhtml";
    }
    //update game
    public String update_Game(int gameid){
        System.out.println(gameid);
        try {
            Db_connect = new DbConnection();
            Connection connection = Db_connect.get_connection();
            String sql = "UPDATE list SET name=?, description=?, price=?,type=?,developer=?,publisher=?,quantity=? WHERE id =" + gameid;         
            PreparedStatement ps = connection.prepareStatement(sql);
            
            ps.setString(1, name);
            ps.setString(2, description);
            ps.setDouble(3, price);
            ps.setString(4, type);
            ps.setString(5, developer);
            ps.setString(6, publisher);
            ps.setInt(7, quantity);
            System.out.println(ps);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
       return "/adminHome.xhtml?faces-redirect=true";   
    }
      
    //delete game
    public String delete_Game(int gameid){    
        System.out.println(gameid);
        try {
            Db_connect = new DbConnection();
            Connection connection = Db_connect.get_connection();
            PreparedStatement ps=connection.prepareStatement("DELETE FROM list WHERE id=" + gameid);
            System.out.println(ps);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
       return "/adminHome.xhtml?faces-redirect=true";   
    }
    
}
