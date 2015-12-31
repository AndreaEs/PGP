/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import Business.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Jennifer
 */
public class UserDB {

    //Es una clase prueba que tiene que tener estos metodos, luego
    // puede que pongamos una entity o lo que sea
    
    /*Esto es como en SSW*/
    
    public static boolean identificar(String user , String pass, String tipo ){
         ConnectionPool pool = ConnectionPool.getInstance(); 
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null; 
        String query = "SELECT login FROM Usuarios " 
                + "WHERE login = ? AND pass = ? AND tipo = ?"; 
        try {
            ps = connection.prepareStatement(query); 
            ps.setString(1, user); 
            ps.setString(2, pass);
            ps.setString(3, tipo);
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
    public static boolean exist(String user) {
        //
        ConnectionPool pool = ConnectionPool.getInstance(); 
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null; 
        String query = "SELECT login FROM Usuarios " 
                + "WHERE login = ?"; 
        try {
            ps = connection.prepareStatement(query); 
            ps.setString(1, user); 
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

    public static boolean comprobarPassword(String password) {
        ConnectionPool pool = ConnectionPool.getInstance(); 
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null; 
        String query = "SELECT pass FROM usuario " 
                + "WHERE pass = ?"; 
        try {
            ps = connection.prepareStatement(query); 
            ps.setString(1, password); 
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
    
    public static int insert(User user) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        String query="INSERT INTO Usuarios (login, pass, tipo, nif, maxProy,"
                + "infoGeneral) VALUES ('"
                + user.getLogin()+"','"
                + user.getPass()+"','"
                + user.getTipo()+ "','"
                + user.getNif()+"','"
                + user.getMaxProy()+"','"
                + user.getInfoGeneral()+ "')";
                
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

    public static ArrayList<User> findAll(){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT * FROM Usuarios";
        ArrayList<User> usuarios = new ArrayList<User>();
        
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()){
                User u = new User(rs.getString(1), rs.getString(2), rs.getString(3).charAt(0), rs.getString(4), rs.getString(6));
                usuarios.add(u);
            }
            rs.close();
            ps.close();
            pool.freeConnection(connection);
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return usuarios;
    }

    public static ArrayList<String> getLoginUsuarios() throws SQLException{
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        String query = "SELECT login FROM usuarios ";
        ResultSet rs = null; 
        Statement st = connection.createStatement();
         //ResultSet rs = st.executeQuery("select * from contacto" );
        ArrayList<String> usuarios = new ArrayList();
        try {
            //ps = connection.prepareStatement(query); 
            rs = st.executeQuery(query);
            while(rs.next()){
                usuarios.add(rs.getString("login"));
            }
                
           
            rs.close(); 
            st.close(); 
            pool.freeConnection(connection);
            return usuarios;
        } catch (SQLException e) { 
            e.printStackTrace(); 
            return usuarios;
        }
        
       
    }
    
    public static User getUsuario(String login){
        User usuario = new User();
        ConnectionPool pool = ConnectionPool.getInstance(); 
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null; 
        String query = "SELECT * FROM Usuarios " 
                + "WHERE login = ?"; 
        try {
            ps = connection.prepareStatement(query); 
            ps.setString(1, login); 
            
            rs = ps.executeQuery();
           while(rs.next()){
            usuario.setLogin(login);
            usuario.setPass(rs.getString("pass"));
            usuario.setNif(rs.getString("nif"));
            usuario.setInfoGeneral(rs.getString("infogeneral"));
            usuario.setMaxProy(rs.getInt("maxproy"));
            usuario.setTipo(rs.getString("tipo").charAt(0));
           }
            rs.close(); 
            ps.close(); 
            pool.freeConnection(connection);
            return usuario;
        } catch (SQLException e) { 
            e.printStackTrace(); 
            return usuario;
        }
    }
    
    public static void delete(User user){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        String query="DELETE FROM Usuarios WHERE login = ?";
                
                
        try {
            
            ps = connection.prepareStatement(query);
            ps.setString(1, user.getLogin());
            int res = ps.executeUpdate();
            ps.close();
            pool.freeConnection(connection);
            
        } catch (SQLException e) { 
            e.printStackTrace(); 
            
        }
    }

   
  public static void update(User user){
       ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        String query="UPDATE Usuarios SET login = ? , pass = ? ,"
                + "tipo = ? , infoGeneral = ? WHERE nif = ?";
                
                
        try {
            
            ps = connection.prepareStatement(query);
            ps.setString(1, user.getLogin());
            ps.setString(2,user.getPass() );
            ps.setString(3,String.valueOf(user.getTipo() ));
            ps.setString(4, user.getInfoGeneral());
            ps.setString(5, user.getNif() );
            
            
            int res = ps.executeUpdate();
            ps.close();
            pool.freeConnection(connection);
            
        } catch (SQLException e) { 
            e.printStackTrace(); 
            
        }
  }


}
