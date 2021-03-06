/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import Business.Vacaciones;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author grupo06
 * Funciones de acceso a la BBDD para realizar operaciones sobre la tabla de Vacaciones
 */
public class VacacionesDB {
    
    /**
     * Obtiene todas las vacaciones
     * @param login
     * @return 
     */
    public static List<Vacaciones> obtenerVacaciones(String login){
        
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query ="SELECT * FROM Vacaciones WHERE login=?";
        List<Vacaciones> calendario = new ArrayList<Vacaciones>();
        
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, login);
            rs = ps.executeQuery();
            while (rs.next()) {
                    Vacaciones cal = new Vacaciones(rs.getString(1),rs.getString(2),login);
                    calendario.add(cal);
            }
            rs.close();
            ps.close();
            pool.freeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return calendario;
        
    }
    
    /**
     * Inserta Vacaciones en la BBDD
     * @param v 
     */
    public static void insertVacaciones(Vacaciones v) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        String fi = v.getFechaInicio();
        String ff = v.getFechaFin();
        String login = v.getUsuario();
        String query = "INSERT INTO Vacaciones(fechaInicio,fechaFin,login) VALUES('"+fi+"','"+ff+"','"+login+"')";
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
     * Elimina vacaciones de la BBDD
     * @param v 
     */
    public static void delete(Vacaciones v) {
        System.out.println("borramos");
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        String query="DELETE FROM Vacaciones WHERE login = ?";
                
                
        try {
            
            ps = connection.prepareStatement(query);
            ps.setString(1, v.getUsuario());
            int res = ps.executeUpdate();
            ps.close();
            pool.freeConnection(connection);
            
        } catch (SQLException e) { 
            e.printStackTrace(); 
            
        }
    }

}
