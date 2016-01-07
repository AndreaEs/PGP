/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Sandra
 */
public class TablaRolesDB {

    public static void insert(int idProyecto, String desarrollador, String categoria) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        String query="INSERT INTO TablaRoles (idProyecto , idParticipante , categoria) VALUES ("
                + idProyecto+",'"
                + desarrollador+"','"
                + categoria+  "')";
                
        try {
            ps = connection.prepareStatement(query);
            int res = ps.executeUpdate();
            ps.close();
            pool.freeConnection(connection);
            
        } catch (SQLException e) { 
            e.printStackTrace(); 
            
        }
    }

    public static boolean exist(int idP) {
        ConnectionPool pool = ConnectionPool.getInstance(); 
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null; 
        String query = "SELECT idProyecto FROM TablaRoles " 
                + "WHERE idProyecto = ?"; 
        try {
            ps = connection.prepareStatement(query); 
            ps.setInt(1, idP); 
            rs = ps.executeQuery();
            boolean res = rs.next();
            rs.close(); 
            ps.close(); 
            pool.freeConnection(connection);
            return res;
        } catch (SQLException e) { 
            e.printStackTrace(); 
            return false;
        }
        
    }
    
    public static ArrayList<String> getIdParticipante(int idProyecto){
        ConnectionPool pool = ConnectionPool.getInstance(); 
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null; 
        ArrayList<String> res = new ArrayList<String>();
        String query = "SELECT idParticipante FROM TablaRoles " 
                + "WHERE idProyecto = ?"; 
        try {
            ps = connection.prepareStatement(query); 
            ps.setInt(1, idProyecto); 
            rs = ps.executeQuery();
            while(rs.next()){
                res.add(rs.getString(1));
            }
            rs.close(); 
            ps.close(); 
            pool.freeConnection(connection);
            return res;
        } catch (SQLException e) { 
            e.printStackTrace(); 
            return null;
        }
    }
}
