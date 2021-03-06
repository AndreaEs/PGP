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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author grupo06
 * Funciones de acceso a la BBDD para realizar operaciones sobre la tabla de Tareas Personales
 */
public class TareaDB {

    /**
     * Insertar una nueva tarea personal
     * @param tarea 
     */
    public static void insert(TareaPersonal tarea) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        String query = "INSERT INTO TareasPersonales (tipo, login, fecha) VALUES ('"
                + tarea.getTipo() + "','"
                + tarea.getLogin() + "','"
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

    /**
     * Buscar todas las tareas personales de un usuario
     * @param login
     * @return 
     */
    public static ArrayList<TareaPersonal> findAll(String login) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT * FROM TareasPersonales WHERE login = ?";
        ArrayList<TareaPersonal> tmp = new ArrayList<TareaPersonal>();
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, login);
            rs = ps.executeQuery();

            while (rs.next()) {
                TareaPersonal t = new TareaPersonal(rs.getInt(1),rs.getString(2), rs.getString(3), rs.getString(4));
                tmp.add(t);
            }

            rs.close();
            ps.close();
            pool.freeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tmp;

    }

    /**
     * Buscar una tarea personal dado su ID
     * @param idTarea
     * @return 
     */
    public static TareaPersonal find(int idTarea) {
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
                tp = new TareaPersonal(idTarea, rs.getString(2), rs.getString(3), rs.getString(4));
            }
            rs.close();
            ps.close();
            pool.freeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tp;
    }

    /**
     * Actualizar una tarea personal
     * @param tp 
     */
    public static void updateTarea(TareaPersonal tp) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        String query = "UPDATE TareasPersonales SET tipo=?, login=?, fecha=? WHERE id=?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, tp.getTipo());
            ps.setString(2, tp.getLogin());
            ps.setString(3, tp.getFecha());
            ps.setInt(4, tp.getId());
            ps.executeUpdate();
            ps.close();
            pool.freeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Comprueba el número de tateas personales que tiene asignadas un usuario en la 
     semana en que se encuentra la fecha dada
     Es decir, comprueba esta parte del enunciado:
     El número de “tareas personales” que una persona puede realizar por semana no
     debe superar las 24 entre todos los proyectos en los que esté trabajando durante la
     misma.
     * @param login
     * @param f
     * @return
     * @throws ParseException 
     */
    public static boolean tareasPersonalesSemana(String login, String f) throws ParseException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        //Pasar String a Calendar
        Calendar fecha = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        fecha.setTime(formatter.parse(f));
        //Obtener semana de dicha fecha
        int semana = fecha.get(Calendar.WEEK_OF_YEAR);
        //Obtener Las actividades del cliente        
        String query = "SELECT * FROM TareasPersonales WHERE login=?";
        int tareas = 0;
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, login);
            rs = ps.executeQuery();
            while (rs.next()) {
                String fechaTP = rs.getString(4);
                //Pasar String a Calendar
                Calendar fechaT = Calendar.getInstance();
                fechaT.setTime(formatter.parse(fechaTP));
                //Comprobar semanas de esas fechas
                int semanaTP = fechaT.get(Calendar.WEEK_OF_YEAR);
                if (semana == semanaTP) {
                    //La tarea está en la semana de la fecha
                    tareas = tareas + 1;
                }
            }
            rs.close();
            ps.close();
            pool.freeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (tareas > 24) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Eliminar una tarea personal
     * @param tarea 
     */
    public static void delete(TareaPersonal tarea) {
         ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        String query="DELETE FROM TareasPersonales WHERE id = ?";
                
                
        try {
            
            ps = connection.prepareStatement(query);
            ps.setInt(1, tarea.getId());
            int res = ps.executeUpdate();
            ps.close();
            pool.freeConnection(connection);
            
        } catch (SQLException e) { 
            e.printStackTrace(); 
            
        }
    }

    /**
     * Obtener las tareas personales de un usuario
     * @param login
     * @return 
     */
    public static ArrayList<TareaPersonal> getTareas(String login) {
       ArrayList<TareaPersonal> tareas = new ArrayList();
       ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT * FROM TareasPersonales WHERE login=?";
        
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, login);
            rs = ps.executeQuery();
            while (rs.next()) {
                TareaPersonal t = new TareaPersonal(rs.getInt(1),rs.getString(2), rs.getString(3), rs.getString(4));
                tareas.add(t);
            }
            rs.close();
            ps.close();
            pool.freeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
       
       return tareas;
    }
}
