/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import Business.User;
import Business.Participante;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Jennifer
 */
public class ParticipantesBD {
    
    public static ArrayList<Participante> findAll(){
        ConnectionPool pool = ConnectionPool.getInstance();
      Connection connection = pool.getConnection();
      PreparedStatement ps = null;
      ResultSet rs = null;
      String query="SELECT * FROM Participaciones";
      ArrayList<Participante> part = new ArrayList<Participante>();
      Participante p=null;
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()){
                p = new Participante(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getString(4), rs.getString(5));
                part.add(p);
            }
            rs.close();
            ps.close();
            pool.freeConnection(connection);
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return part;
    }
    
    public static ArrayList<Participante> findByActividad(int idActividad){
          ConnectionPool pool = ConnectionPool.getInstance();
      Connection connection = pool.getConnection();
      PreparedStatement ps = null;
      ResultSet rs = null;
      String query="SELECT * FROM Participaciones WHERE idActividad=?";
      ArrayList<Participante> part = new ArrayList<Participante>();
      Participante p=null;
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, idActividad);
            rs = ps.executeQuery();
            while (rs.next()){
                p = new Participante(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getString(4), rs.getString(5));
                part.add(p);
            }
            rs.close();
            ps.close();
            pool.freeConnection(connection);
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return part;
    }
    
    public static ArrayList<User> getParticipantesDisponibles(){
      ArrayList<User> res = new ArrayList<User>();
      HashMap<String, Double> participantes = getParticipantes();
      ArrayList<User> todos= UserDB.findAll();
        for (User todo : todos) {
            if (participantes.containsKey(todo.getLogin())) {
                if (participantes.get(todo.getLogin()) < 100) {
                    res.add(todo);
                }
            } else {
                res.add(todo);
            }
        }
      
      return res;
  }

    
  public static double getPorcentaje(String login){
      ConnectionPool pool = ConnectionPool.getInstance();
      Connection connection = pool.getConnection();
      PreparedStatement ps = null;
      ResultSet rs = null;
      String query="SELECT porcentaje FROM Participaciones WHERE login=?";
      double res=0;
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, login);
            rs = ps.executeQuery();
            while(rs.next()){
                res+=rs.getDouble(1);
            }
            rs.close();
            ps.close();
            pool.freeConnection(connection);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return res;
  }
  
  public static int insert(Participante p){
      ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        String query="INSERT INTO Participaciones (idActividad, login, porcentaje, rol, idParticipante) VALUES ("
                + p.getIdActividad() +",'"
                + p.getLogin() + "',"
                + p.getPorcentaje() + ",'"
                + p.getRol()+ "','"
                + p.getIdParticipante() +"')";
                
        try {
            ps = connection.prepareStatement(query);
            int res = ps.executeUpdate();
            ps.close();
            pool.freeConnection(connection);
            return res;
        } catch (SQLException e) { 
            e.printStackTrace(); 
            return 0;
        }
  }
  
  public static boolean exist(String login){
      ConnectionPool pool = ConnectionPool.getInstance();
      Connection connection = pool.getConnection();
      PreparedStatement ps = null;
      ResultSet rs = null;
      String query="SELECT * FROM Participaciones WHERE login=?";
      
      try {
            ps = connection.prepareStatement(query); 
            ps.setString(1, login); 
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
  
  private static HashMap<String,Double> getParticipantes(){
      ConnectionPool pool = ConnectionPool.getInstance();
      Connection connection = pool.getConnection();
      PreparedStatement ps = null;
      ResultSet rs = null;
      String query="SELECT * FROM Participaciones";
      HashMap<String,Double> part = new HashMap<String, Double>();
        
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()){
                if(!part.containsKey(rs.getString(2))){
                    part.put(rs.getString(2), rs.getDouble(3));
                } else {
                    part.put(rs.getString(2),part.get(rs.getString(2))+rs.getDouble(3));
                }
            }
            rs.close();
            ps.close();
            pool.freeConnection(connection);
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return part;
  }
  
}
