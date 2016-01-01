/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import Business.TareaPersonal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Jennifer
 */
public class TareaDB {
    
    public static void insert(TareaPersonal tarea){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        String query = "INSERT INTO TareasPersonales (tipo, login, actividad, fecha) VALUES ('"
                + tarea.getTipo() + "','"
                + tarea.getLogin() + "','"
                + tarea.getActividad() + "','"
                + tarea.getFecha() + "')";
        try {
            ps = connection.prepareStatement(query);
            ps.executeUpdate();
            ps.close();
            pool.freeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static ArrayList<TareaPersonal> findAll(String login){
         ConnectionPool pool = ConnectionPool.getInstance(); 
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null; 
        String query = "SELECT * FROM TareasPersonales WHERE login = ?";
        ArrayList<TareaPersonal> tmp = new ArrayList<TareaPersonal>();
        try{
            ps = connection.prepareStatement(query);
            ps.setString(1, login);
            rs = ps.executeQuery();
            
            while(rs.next()){
                //TareaPersonal t = new TareaPersonal(rs.getInt(1), rs.getString(2), rs.getString(3),
                //rs.getString(5), rs.getInt(4));
                //tmp.add(t);
            }
            
            rs.close();
            ps.close();
            pool.freeConnection(connection);
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return tmp;
        
    }
    
    public static TareaPersonal find(int idTarea){
        ConnectionPool pool = ConnectionPool.getInstance();		
         Connection connection = pool.getConnection();		
         PreparedStatement ps = null;		
         ResultSet rs = null;		
         String query = "SELECT * FROM TareasPersonales WHERE id=?";
         TareaPersonal tp = null;
         try {		
             ps = connection.prepareStatement(query);		
             ps.setInt(1, idTarea);		
             rs = ps.executeQuery();		
             if (rs.next()) {		
                  //tp = new TareaPersonal(idTarea,rs.getString(2), rs.getString(3), rs.getString(5), rs.getInt(4));		
             }		
             rs.close();		
             ps.close();		
             pool.freeConnection(connection);		
         } catch (SQLException e) {		
             e.printStackTrace();		
         }		
         return tp;
    }
    
    public static void updateTarea(TareaPersonal tp){
        ConnectionPool pool = ConnectionPool.getInstance();		
         Connection connection = pool.getConnection();		
         PreparedStatement ps = null;
         String query = "UPDATE TareasPersonales SET tipo=?, login=?, actividad=?, fecha=? WHERE id=?";		
         try {
              ps = connection.prepareStatement(query);
              ps.setString(1, tp.getTipo());
              ps.setString(2, tp.getLogin());
              //ps.setInt(3, tp.getActividad());
              ps.setString(4, tp.getFecha());
              //ps.setInt(5, tp.getId());
            ps.executeUpdate();		
             ps.close();		
             pool.freeConnection(connection);		
         } catch (SQLException e) {		
             e.printStackTrace();		
         }	
    }
}

