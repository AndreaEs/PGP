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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author gil
 */
public class TareaPersonalDB {
    
    /*Comprueba el número de tateas personales que tiene asignadas un usuario en la 
    semana en que se encuentra la fecha dada
    Es decir, comprueba esta parte del enunciado:
    El número de “tareas personales” que una persona puede realizar por semana no
    debe superar las 24 entre todos los proyectos en los que esté trabajando durante la
    misma.*/
    public static boolean tareasPersonalesSemana(String login, String f) throws ParseException{
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
        int actividades=0;
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, login);
            rs = ps.executeQuery();
            while (rs.next()) {
                String fechaInicio = String.format("%04d-%02d-%02d", rs.getInt(8), rs.getInt(7),rs.getInt(6));
                String fechaFin = String.format("%04d-%02d-%02d", rs.getInt(11), rs.getInt(10),rs.getInt(9));
                //Pasar String a Calendar
                Calendar fechaI = Calendar.getInstance();
                fechaI.setTime(formatter.parse(fechaInicio));
                Calendar fechaF = Calendar.getInstance();
                fechaF.setTime(formatter.parse(fechaFin));
                //Comprobar semanas de esas fechas
                int semanaI = fechaI.get(Calendar.WEEK_OF_YEAR);
                int semanaF = fechaF.get(Calendar.WEEK_OF_YEAR);
                for(int i=semanaI;i<=semanaF;i++){
                    if(i==semana){
                        //La tarea está en la semana de la fecha
                        actividades=actividades+1;
                    }
                }
            }
            rs.close();
            ps.close();
            pool.freeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(actividades>24)
            return false;
        else
            return true;
    }
    
}
