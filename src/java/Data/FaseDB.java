/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import Business.Fase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author andreaescribano
 */
public class FaseDB {

    public static void insert(Fase fase) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        String query = "INSERT INTO Fases (nombre, fechaInicio, fechaFin, estado, idProyecto) VALUES ('"
                + fase.getNombre() + "','"
                + fase.getFechaInicio()+ "','"
                + fase.getFechaFin()+ "','"
                + fase.getEstado() + "',"
                + fase.getIdProyecto() + ")";

        try {
            ps = connection.prepareStatement(query);
            ps.executeUpdate();
            ps.close();
            pool.freeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Fase> selectFases(int idProyecto) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT * FROM Fases WHERE idProyecto=?";
        ArrayList<Fase> fases = new ArrayList<Fase>();
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, idProyecto);
            rs = ps.executeQuery();
            while(rs.next()){
                Fase f = new Fase(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5).charAt(0),idProyecto);
                fases.add(f);
            }
            rs.close();
            ps.close();
            pool.freeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fases;
    }
public static Fase selectFase(int idFase) {		
         ConnectionPool pool = ConnectionPool.getInstance();		
         Connection connection = pool.getConnection();		
         PreparedStatement ps = null;		
         ResultSet rs = null;		
         String query = "SELECT * FROM Fases WHERE id=?";		
         Fase f = null;		
         try {		
             ps = connection.prepareStatement(query);		
             ps.setInt(1, idFase);		
             rs = ps.executeQuery();		
             while(rs.next()){		
                 String fechaInicio = String.format("%02d/%02d/%04d", rs.getInt(4), rs.getInt(3),rs.getInt(5));		
                 String fechaFin = String.format("%02d/%02d/%04d", rs.getInt(7), rs.getInt(6),rs.getInt(8));		
                 f = new Fase(idFase, rs.getString(2), fechaInicio, fechaFin, rs.getString(9).charAt(0), rs.getInt(10));		
             }		
             rs.close();		
             ps.close();		
             pool.freeConnection(connection);		
         } catch (SQLException e) {		
             e.printStackTrace();		
         }		
         return f;		
     }		
 		
     public static void updateFase(Fase f) {		
         ConnectionPool pool = ConnectionPool.getInstance();		
         Connection connection = pool.getConnection();		
         PreparedStatement ps = null;		
         int diaInicio, mesInicio, anoInicio, diaFin, mesFin, anoFin;		
         int[] fechaInicio = getFechaInt(f.getFechaInicio());		
         diaInicio = fechaInicio[0];		
         mesInicio = fechaInicio[1];		
         anoInicio = fechaInicio[2];		
         int[] fechaFin = getFechaInt(f.getFechaFin());		
         diaFin = fechaFin[0];		
         mesFin = fechaFin[1];		
         anoFin = fechaFin[2];		
         String query = "UPDATE Fases SET nombre=?, diaInicio=?, mesInicio=?, anoInicio=?, diaFin=?, mesFin=?, anoFin=?, estado=?, idProyecto=? WHERE id=?";		
         try {		
             ps = connection.prepareStatement(query);		
             ps.setString(1, f.getNombre());		
             ps.setInt(2, diaInicio);		
             ps.setInt(3, mesInicio);		
             ps.setInt(4, anoInicio);		
             ps.setInt(5, diaFin);		
             ps.setInt(6, mesFin);		
             ps.setInt(7, anoFin);		
             ps.setString(8, ""+f.getEstado());		
             ps.setInt(9, f.getIdProyecto());		
             ps.setInt(10, f.getId());		
             ps.executeUpdate();		
             ps.close();		
             pool.freeConnection(connection);		
         } catch (SQLException e) {		
             e.printStackTrace();		
         }		
     }		
     		
     private static int[] getFechaInt(String fecha) {		
         int[] fechas = new int[3];		
         int cont = 0;		
         String num = "";		
         for (int i = 0; i < fecha.length(); i++) {		
             if (fecha.charAt(i) != '/') {		
                 num += fecha.charAt(i);		
                 if (cont == 1) {		
                     fechas[1] = Integer.parseInt(num);		
                     num = "";		
                 } else if (cont == 3) {		
                     fechas[0] = Integer.parseInt(num);		
                     num = "";		
                 } else if (cont == 7) {		
                     fechas[2] = Integer.parseInt(num);		
                     num = "";		
                 }		
                 cont++;		
             }		
         }		
         return fechas;		
     }
}