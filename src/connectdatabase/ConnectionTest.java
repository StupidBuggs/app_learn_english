/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Administrator
 */
public class ConnectionTest {
    static Connection conn = null;
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        if(conn == null){
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;instance=SQLEXPRESS;databaseName=demo", "sa", "thiem12345");
        }
        return conn;
    }
    
    public static ResultSet select(String what, String table) throws SQLException, ClassNotFoundException{
        String sql = "SELECT "+what+" FROM "+table;
        Statement stm = getConnection().createStatement();
        return stm.executeQuery(sql);
    }
    
    public static ResultSet select(String what, String table, String cond) throws SQLException, ClassNotFoundException{
        String sql = "SELECT "+what+" FROM "+table+" WHERE "+cond;
        Statement stm = getConnection().createStatement();
        return stm.executeQuery(sql);
    }
    
    public static int insert(String table, String row, String value) throws SQLException, ClassNotFoundException{
        String sql = "INSERT INTO "+table+" ("+row+") VALUES ("+value+")";
        Statement stm = getConnection().createStatement();
        return stm.executeUpdate(sql);
    }
    
    public static int update(String table, String SET, String cond) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE "+table+" SET "+SET+" WHERE "+cond;
        Statement stm = getConnection().createStatement();
        return stm.executeUpdate(sql);
    }
    
    public static int delete(String table, String cond) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM "+table+" WHERE "+cond;
        Statement stm = getConnection().createStatement();
        return stm.executeUpdate(sql);
    }
}
    