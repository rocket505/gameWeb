/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gameweb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Ajump
 */
public class DbConnection {
    public static void main(String[] args) throws Exception{
        DbConnection obj_DB_connection=new DbConnection();
        
        System.out.println(obj_DB_connection.get_connection());
    }
    public Connection get_connection() throws Exception{
        
        Connection connection=null;  
        
        try {
            String DATABASE = "jdbc:mysql://localhost:3306/game?zeroDateTimeBehavior=convertToNull";
            String USERNAME = "root";
            String PASSWORD = "1300882525";
            String DRIVER = "com.mysql.jdbc.Driver";
            Class.forName(DRIVER);
            connection=DriverManager.getConnection(DATABASE,USERNAME,PASSWORD);
            
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
        
        return connection;
    }
}
