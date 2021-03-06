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
import java.util.List;

/**
 *
 * @author grupo06
 * Funciones de acceso a la BBDD para realizar operaciones sobre la tabla de Predecesoras
 */
public class PredecesorasDB {
    
    /**
     * Crea un nuevo par: actividad-predecesora
     * @param actividad
     * @param predecesora 
     */
     public static void crearPredecesoras(int actividad,int predecesora) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        String query = "INSERT INTO Predecesoras (actividad,predecesora) VALUES ("+actividad+","+predecesora+")";
        try {
            ps = connection.prepareStatement(query);
            ps.executeUpdate();
            ps.close();
            pool.freeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
     /**
      * Obtiene lista de predecesoras dada su actividad
      * @param id
      * @return 
      */
    public static List<Integer> obtenerPredecesoras(int id){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT * FROM Predecesoras WHERE actividad=?";
        List<Integer> predecesoras = new ArrayList<Integer>();
         try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                predecesoras.add(rs.getInt("predecesora"));
            }
            rs.close();
            ps.close();
            pool.freeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return predecesoras;
        
    }
    
}
