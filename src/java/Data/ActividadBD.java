/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import Business.Actividad;
import Business.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author andreaescribano
 */
public class ActividadBD {

    public static void insertActividad(Actividad actividad) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        int diaInicio, mesInicio, anoInicio, diaFin, mesFin, anoFin;
        int[] fechaInicio = getFechaInt(actividad.getFechaInicio());
        diaInicio = fechaInicio[0];
        mesInicio = fechaInicio[1];
        anoInicio = fechaInicio[2];
        int[] fechaFin = getFechaInt(actividad.getFechaFin());
        diaFin = fechaFin[0];
        mesFin = fechaFin[1];
        anoFin = fechaFin[2];
        String query = "INSERT INTO Actividades (login, descripcion, rol, duracionEstimada, diaInicio, mesInicio, anoInicio, diaFin, mesFin, anoFin, duracionReal, estado, idFase) VALUES ('"
                + actividad.getLogin() + "','"
                + actividad.getDescripcion() + "','"
                + actividad.getRolNecesario() + "',"
                + actividad.getDuracionEstimada() + ","
                + diaInicio + ","
                + mesInicio + ","
                + anoInicio + ","
                + diaFin + ","
                + mesFin + ","
                + anoFin + ","
                + actividad.getDuracionReal() + ",'"
                + actividad.getEstado() + "',"
                + actividad.getIdFase() + ")";
        try {
            ps = connection.prepareStatement(query);
            ps.executeUpdate();
            ps.close();
            pool.freeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Actividad> selectActividades(int idFase) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT * FROM Actividades a WHERE idFase=? ORDER BY a.anoInicio, a.mesInicio, a.diaInicio ASC";
        ArrayList<Actividad> actividades = new ArrayList<Actividad>();
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, idFase);
            rs = ps.executeQuery();
            while (rs.next()) {
                String fechaInicio = String.format("%02d/%02d/%04d", rs.getInt(6), rs.getInt(7), rs.getInt(8));
                String fechaFin = String.format("%02d/%02d/%04d", rs.getInt(9), rs.getInt(10), rs.getInt(11));
                Actividad a = new Actividad(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), fechaInicio, fechaFin, rs.getInt(12), rs.getString(13).charAt(0), idFase);
                actividades.add(a);
            }
            rs.close();
            ps.close();
            pool.freeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return actividades;
    }

    public static Actividad selectActividad(int idActividad) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT * FROM Actividades WHERE id=?";
        Actividad a = null;
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, idActividad);
            rs = ps.executeQuery();
            if (rs.next()) {
                String fechaInicio = String.format("%02d/%02d/%04d", rs.getInt(7), rs.getInt(6), rs.getInt(8));
                String fechaFin = String.format("%02d/%02d/%04d", rs.getInt(10), rs.getInt(9), rs.getInt(11));
                a = new Actividad(idActividad, rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), fechaInicio, fechaFin, rs.getInt(12), rs.getString(13).charAt(0), rs.getInt(14));
            }
            rs.close();
            ps.close();
            pool.freeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

    public static ArrayList<Actividad> selectActividades(String login) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT * FROM Actividades WHERE login=?";
        ArrayList<Actividad> actividades = new ArrayList<Actividad>();
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, login);
            rs = ps.executeQuery();
            while (rs.next()) {
                String fechaInicio = String.format("%04d-%02d-%02d", rs.getInt(8), rs.getInt(7), rs.getInt(6));
                String fechaFin = String.format("%04d-%02d-%02d", rs.getInt(11), rs.getInt(10), rs.getInt(9));
                Actividad a = new Actividad(rs.getInt(1), login, rs.getString(3), rs.getString(4), rs.getInt(5), fechaInicio, fechaFin, rs.getInt(12), rs.getString(13).charAt(0), rs.getInt(14));
                actividades.add(a);
            }
            rs.close();
            ps.close();
            pool.freeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return actividades;
    }
    
    public static ArrayList<Actividad> selectActividadesLoginOrdenadas(String login) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT * FROM Actividades a WHERE login=? ORDER BY a.anoInicio, a.mesInicio, a.diaInicio ASC";
        ArrayList<Actividad> actividades = new ArrayList<Actividad>();
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, login);
            rs = ps.executeQuery();
            while (rs.next()) {
                String fechaInicio = String.format("%02d/%02d/%04d", rs.getInt(6), rs.getInt(7), rs.getInt(8));
                String fechaFin = String.format("%02d/%02d/%04d", rs.getInt(9), rs.getInt(10), rs.getInt(11));
                Actividad a = new Actividad(rs.getInt(1), login, rs.getString(3), rs.getString(4), rs.getInt(5), fechaInicio, fechaFin, rs.getInt(12), rs.getString(13).charAt(0), rs.getInt(14));
                actividades.add(a);
            }
            rs.close();
            ps.close();
            pool.freeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return actividades;
    }

    public static void updateActividad(Actividad a) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        int diaInicio, mesInicio, anoInicio, diaFin, mesFin, anoFin;
        int[] fechaInicio = getFechaInt(a.getFechaInicio());
        diaInicio = fechaInicio[0];
        mesInicio = fechaInicio[1];
        anoInicio = fechaInicio[2];
        int[] fechaFin = getFechaInt(a.getFechaFin());
        diaFin = fechaFin[0];
        mesFin = fechaFin[1];
        anoFin = fechaFin[2];
        String query = "UPDATE Actividades SET login=?, descripcion=?, rol=?, duracionEstimada=?, diaInicio=?, mesInicio=?, anoInicio=?, diaFin=?, mesFin=?, anoFin=?, duracionReal=?, estado=?, idFase=? WHERE id=?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, a.getLogin());
            ps.setString(2, a.getDescripcion());
            ps.setString(3, a.getRolNecesario());
            ps.setInt(4, a.getDuracionEstimada());
            ps.setInt(5, diaInicio);
            ps.setInt(6, mesInicio);
            ps.setInt(7, anoInicio);
            ps.setInt(8, diaFin);
            ps.setInt(9, mesFin);
            ps.setInt(10, anoFin);
            ps.setInt(11, a.getDuracionReal());
            ps.setString(12, String.valueOf(a.getEstado()));
            ps.setInt(13, a.getIdFase());
            ps.setInt(14, a.getIdentificador());
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

    /*Comprueba el número de actividades que tiene asignadas un usuario en la 
     semana en que se encuentra la fecha dada*/
    public static boolean actividadesSemana(String login, String f) throws ParseException {
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
        String query = "SELECT * FROM Actividades WHERE login=?";
        int actividades = 0;
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, login);
            rs = ps.executeQuery();
            while (rs.next()) {
                String fechaInicio = String.format("%04d-%02d-%02d", rs.getInt(8), rs.getInt(7), rs.getInt(6));
                String fechaFin = String.format("%04d-%02d-%02d", rs.getInt(11), rs.getInt(10), rs.getInt(9));
                //Pasar String a Calendar
                Calendar fechaI = Calendar.getInstance();
                fechaI.setTime(formatter.parse(fechaInicio));
                Calendar fechaF = Calendar.getInstance();
                fechaF.setTime(formatter.parse(fechaFin));
                //Comprobar semanas de esas fechas
                int semanaI = fechaI.get(Calendar.WEEK_OF_YEAR);
                int semanaF = fechaF.get(Calendar.WEEK_OF_YEAR);
                for (int i = semanaI; i <= semanaF; i++) {
                    if (i == semana) {
                        //La actividad está en la semana de la fecha
                        actividades = actividades + 1;
                    }
                }
            }
            rs.close();
            ps.close();
            pool.freeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (actividades > 3) {
            return false;
        } else {
            return true;
        }
    }

    /*Comprueba que el usuario "login" no lleva más de 40 horas asignadas  a 
     actividades de todos los proyectos en la semana en la que se encuetra "fecha"
     Es decir, comprueba esta parte del enunciado:
     La suma del tiempo dedicado a todas las actividades de todos los proyectos en los
     que puede estar implicadas no debiera superar las cuarenta horas semanales
     PARA QUE FUNCIONE (JEFE DE PROYECTOS): Hay que pasarle un dia de la semana en la que se va a 
     encontrar la Actividad*/
    public static boolean horasSemana(String login, String fecha) throws ParseException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        //Pasar String a Calendar
        Calendar f = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        f.setTime(formatter.parse(fecha));
        //Obtener semana de dicha fecha
        int semana = f.get(Calendar.WEEK_OF_YEAR);
        //Obtener Las actividades del cliente        
        String query = "SELECT * FROM Actividades WHERE login=?";
        int horas = 0;
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, login);
            rs = ps.executeQuery();
            while (rs.next()) {
                int duracion = rs.getInt(5);
                String fechaInicio = String.format("%04d-%02d-%02d", rs.getInt(8), rs.getInt(7), rs.getInt(6));
                String fechaFin = String.format("%04d-%02d-%02d", rs.getInt(11), rs.getInt(10), rs.getInt(9));
                //Pasar String a Calendar
                Calendar fechaI = Calendar.getInstance();
                fechaI.setTime(formatter.parse(fechaInicio));
                Calendar fechaF = Calendar.getInstance();
                fechaF.setTime(formatter.parse(fechaFin));
                //Comprobar semanas de esas fechas
                int semanaI = fechaI.get(Calendar.WEEK_OF_YEAR);
                int semanaF = fechaF.get(Calendar.WEEK_OF_YEAR);
                if (semanaI == semana || semanaF == semana) {
                    horas = horas + duracion;
                }
            }
            rs.close();
            ps.close();
            pool.freeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Horas semanales: " + horas);
        if (horas > 39) {
            return false;
        } else {
            return true;
        }
    }

    public static ArrayList<Actividad> selectActividadesInforme(String login, String fechaI, String fechaF) throws ParseException {
        //Comprobar que la fecha de Inicio no es posterior a hoy
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date fechaIU = formatter.parse(fechaI);
        Date hoy = Calendar.getInstance().getTime();
        if (fechaIU.compareTo(hoy) > 0) {
            return null;
        } else {
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            //Selecciona actividades de fase de proyectos "En Curso" de determinado usuario;
            String query = "SELECT * FROM Actividades a,Fases f,Proyectos p WHERE a.idFase=f.id AND f.idProyecto=p.id AND p.estado='E' AND a.login=?";
            ArrayList<Actividad> actividades = new ArrayList<Actividad>();
            try {
                ps = connection.prepareStatement(query);
                ps.setString(1, login);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String fechaInicio = String.format("%04d-%02d-%02d", rs.getInt(8), rs.getInt(7), rs.getInt(6));
                    String fechaFin = String.format("%04d-%02d-%02d", rs.getInt(11), rs.getInt(10), rs.getInt(9));

                    Actividad a = new Actividad(rs.getInt(1), login, rs.getString(3), rs.getString(4), rs.getInt(5), fechaInicio, fechaFin, rs.getInt(12), rs.getString(13).charAt(0), rs.getInt(14));

                    //Comprobar si la actividad obtenida está entre el rango de fechas introducido por el usuario
                    if (comprobarFechaEntreFechas(fechaI, fechaF, a)) {
                        actividades.add(a);
                    }
                }
                rs.close();
                ps.close();
                pool.freeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return actividades;
        }
    }

    //TYA = Trabajadores y sus actividades
    //Solución muy completa pero no funciona
    /*public static HashMap<User,ArrayList<Actividad>> selectInformeTYA(String fechaI, String fechaF) throws ParseException {
     ConnectionPool pool = ConnectionPool.getInstance();
     Connection connection = pool.getConnection();
     PreparedStatement ps = null;
     ResultSet rs = null;
     HashMap<User,ArrayList<Actividad>> inf = new HashMap<User,ArrayList<Actividad>>();
     SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
     //Selecciona actividades de fase de proyectos "En Curso" de determinado usuario;
     String query = "SELECT * FROM Usuarios u, Actividades a WHERE a.login=u.login";
     ArrayList<Actividad> actividades = new ArrayList<Actividad>();
     try {
     ps = connection.prepareStatement(query);
     rs = ps.executeQuery();
     while (rs.next()) {
     String fechaInicio = String.format("%04d-%02d-%02d", rs.getInt("anoinicio"), rs.getInt("mesinicio"),rs.getInt("diainicio"));
     String fechaFin = String.format("%04d-%02d-%02d", rs.getInt("anofin"), rs.getInt("mesfin"),rs.getInt("diafin"));
     //Pasar String a Calendar
     Calendar fechaIA = Calendar.getInstance();
     fechaIA.setTime(formatter.parse(fechaInicio));
     Calendar fechaFA = Calendar.getInstance();
     fechaFA.setTime(formatter.parse(fechaFin));
                
     User u = new User(rs.getString("login"),rs.getString("pass"),rs.getString("tipo").charAt(0),rs.getString("nif"),rs.getString("infogeneral"),rs.getInt("maxproy"));
     Actividad a = new Actividad(rs.getInt("id"),rs.getString("login"), rs.getString("descripcion"), rs.getString("rol"), rs.getInt("duracionestimada"), fechaInicio, fechaFin, rs.getInt("duracionreal"), rs.getString("estado").charAt(0), rs.getInt("idfase"));
                
     //Comprobar si la actividad obtenida está entre el rango de fechas introducido por el usuario
     if(comprobarFechaEntreFechas(fechaI,fechaF,a)){
     if(inf.get(u.getLogin())==null){
     //Esto no lo hace bien. Cree que es la 1º vez que ve ese usuario...
     System.out.println(u.getLogin()+" "+u.getNif());
     inf.put(u, new ArrayList<Actividad>());
     }   
     inf.get(u).add(a);
     }
     }
     rs.close();
     ps.close();
     pool.freeConnection(connection);
     } catch (SQLException e) {
     e.printStackTrace();
     }
        
     return inf;
     }*/
    //TYA = Trabajadores y sus actividades
    //Solución alternativa
    public static HashMap<String, ArrayList<Actividad>> selectInformeTYA(String fechaI, String fechaF, String login) throws ParseException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        HashMap<String, ArrayList<Actividad>> inf = new HashMap<String, ArrayList<Actividad>>();
        //Selecciona actividades de fase de proyectos "En Curso" de determinado usuario;
        String query = "SELECT * FROM Usuarios u, Actividades a,Fases f, Proyectos p WHERE a.login=u.login AND a.idfase=f.id AND f.idproyecto=p.id AND p.login=?";

        ArrayList<Actividad> actividades = new ArrayList<Actividad>();
        try {

            ps = connection.prepareStatement(query);
            ps.setString(1, login);
            rs = ps.executeQuery();

            while (rs.next()) {
                String fechaInicio = String.format("%04d-%02d-%02d", rs.getInt("anoinicio"), rs.getInt("mesinicio"), rs.getInt("diainicio"));
                String fechaFin = String.format("%04d-%02d-%02d", rs.getInt("anofin"), rs.getInt("mesfin"), rs.getInt("diafin"));

                User u = new User(rs.getString("login"), rs.getString("pass"), rs.getString("tipo").charAt(0), rs.getString("nif"), rs.getString("infogeneral"), rs.getInt("maxproy"));
                Actividad a = new Actividad(rs.getInt("id"), rs.getString("login"), rs.getString("descripcion"), rs.getString("rol"), rs.getInt("duracionestimada"), fechaInicio, fechaFin, rs.getInt("duracionreal"), rs.getString("estado").charAt(0), rs.getInt("idfase"));

                //Comprobar si la actividad obtenida está entre el rango de fechas introducido por el usuario
                if (comprobarFechaEntreFechas(fechaI, fechaF, a)) {
                    if (inf.get(u.getLogin()) == null) {
                        inf.put(u.getLogin(), new ArrayList<Actividad>());
                    }
                    inf.get(u.getLogin()).add(a);
                }
            }
            rs.close();
            ps.close();
            pool.freeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return inf;
    }

    /*date1.comparetp(date2) > 0 --> date1 esta después de date2
     date1.comparetp(date2) < 0 --> date1 esta antes de date2*/
    public static boolean comprobarFechaEntreFechas(String fechaI, String fechaF, Actividad a) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date fechaIU = formatter.parse(fechaI);
            java.util.Date fechaFU = formatter.parse(fechaF);
            java.util.Date fechaIA = formatter.parse(a.getFechaInicio());
            java.util.Date fechaFA = formatter.parse(a.getFechaFin());

            if (fechaIU.compareTo(fechaIA) == 0 || fechaIU.compareTo(fechaFA) == 0) {
                return true;
            }
            if (fechaFU.compareTo(fechaIA) == 0 || fechaFU.compareTo(fechaFA) == 0) {
                return true;
            }
            if (fechaIU.compareTo(fechaIA) > 0 && fechaFU.compareTo(fechaFA) < 0) {
                return true;
            }
            if (fechaIU.compareTo(fechaIA) > 0 && fechaIU.compareTo(fechaFA) < 0) {
                return true;
            }
            if (fechaFU.compareTo(fechaIA) > 0 && fechaFU.compareTo(fechaFA) < 0) {
                return true;
            }
            if (fechaIU.compareTo(fechaIA) < 0 && fechaFU.compareTo(fechaFA) > 0) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static ArrayList<Actividad> selectInformeAFF(String fechaI, String fechaF, String login) throws ParseException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT * FROM Actividades a,Fases f, Proyectos p WHERE a.idfase=f.id AND f.idproyecto=p.id AND a.estado='F' or a.estado='E' AND p.login=?";
        ArrayList<Actividad> actividades = new ArrayList<Actividad>();
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, login);
            rs = ps.executeQuery();
            while (rs.next()) {
                String fechaInicio = String.format("%04d-%02d-%02d", rs.getInt("anoinicio"), rs.getInt("mesinicio"), rs.getInt("diainicio"));
                String fechaFin = String.format("%04d-%02d-%02d", rs.getInt("anofin"), rs.getInt("mesfin"), rs.getInt("diafin"));

                Actividad a = new Actividad(rs.getInt("id"), rs.getString("login"), rs.getString("descripcion"), rs.getString("rol"), rs.getInt("duracionestimada"), fechaInicio, fechaFin, rs.getInt("duracionreal"), rs.getString("estado").charAt(0), rs.getInt("idfase"));

                //Comprobar si la actividad obtenida está entre el rango de fechas introducido por el usuario
                if (comprobarFechaEntreFechas(fechaI, fechaF, a)) {
                    actividades.add(a);
                }
            }
            rs.close();
            ps.close();
            pool.freeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return actividades;
    }

    public static ArrayList<Actividad> selectInformeAAR(String fechaI, String fechaF, String login) throws ParseException {
        //Comprobar que la fecha de Inicio no es anterior a hoy
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date fechaIU = formatter.parse(fechaI);
        Date hoy = Calendar.getInstance().getTime();
        if (fechaIU.compareTo(hoy) < 0) {
            return null;
        } else {
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            String query = "SELECT * FROM Actividades a,Fases f, Proyectos p WHERE a.idfase=f.id AND f.idproyecto=p.id AND p.login=?";
            ArrayList<Actividad> actividades = new ArrayList<Actividad>();
            try {
                ps = connection.prepareStatement(query);
                ps.setString(1, login);
                rs = ps.executeQuery();
                while (rs.next()) {
                     
                    String fechaInicio = String.format("%04d-%02d-%02d", rs.getInt("anoinicio"), rs.getInt("mesinicio"), rs.getInt("diainicio"));
                    String fechaFin = String.format("%04d-%02d-%02d", rs.getInt("anofin"), rs.getInt("mesfin"), rs.getInt("diafin"));
                    Actividad a = new Actividad(rs.getInt("id"), rs.getString("login"), rs.getString("descripcion"), rs.getString("rol"), rs.getInt("duracionestimada"), fechaInicio, fechaFin, rs.getInt("duracionreal"), rs.getString("estado").charAt(0), rs.getInt("idfase"));
                    //Comprobar si la actividad obtenida está entre el rango de fechas introducido por el usuario
                    if (comprobarFechaEntreFechas(fechaI, fechaF, a)) {
                        actividades.add(a);
                    }
                }
                rs.close();
                ps.close();
                pool.freeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return actividades;
        }
    }
}
