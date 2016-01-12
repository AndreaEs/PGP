/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import Business.Actividad;
import Business.Fase;
import Business.Proyecto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import static Data.ActividadBD.comprobarFechaEntreFechas;

/**
 *
 * @author grupo06
 * Funciones de acceso a la BBDD para realizar operaciones sobre la tabla de Proyectos
 */
public class ProyectoDB {

    /**
     * Crea un nuevo proyecto
     * @param proyecto 
     */
    public static void insert(Proyecto proyecto) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        int diaInicio, mesInicio, anoInicio, diaFin, mesFin, anoFin;
        int[] fechaInicio = getFechaInt(proyecto.getFechaInicio());
        diaInicio = fechaInicio[0];
        mesInicio = fechaInicio[1];
        anoInicio = fechaInicio[2];
        int[] fechaFin = getFechaInt(proyecto.getFechaFin());
        diaFin = fechaFin[0];
        mesFin = fechaFin[1];
        anoFin = fechaFin[2];
        String query = "INSERT INTO Proyectos (nombre, diaInicio, mesInicio, anoInicio, diaFin, mesFin, anoFin, estado, login) VALUES ('"
                + proyecto.getNombre() + "',"
                + diaInicio + ","
                + mesInicio + ","
                + anoInicio + ","
                + diaFin + ","
                + mesFin + ","
                + anoFin + ",'"
                + proyecto.getEstado() + "','"
                + proyecto.getLogin() + "')";
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
     * Obtiene los proyectos de los que un usuario es jefe ordenados
     * @param usuario
     * @return 
     */
    public static ArrayList<Proyecto> selectProyectos(String usuario) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT * FROM Proyectos WHERE login=? ORDER BY anoInicio, mesInicio, diaInicio ASC";
        ArrayList<Proyecto> proyectos = new ArrayList<Proyecto>();
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, usuario);
            rs = ps.executeQuery();
            while (rs.next()) {
                String fechaInicio = String.format("%02d/%02d/%04d", rs.getInt(3), rs.getInt(4), rs.getInt(5));
                String fechaFin = String.format("%02d/%02d/%04d", rs.getInt(6), rs.getInt(7), rs.getInt(8));
                Proyecto p = new Proyecto(rs.getInt(1), rs.getString(2), fechaInicio, fechaFin, rs.getString(9).charAt(0), usuario);
                proyectos.add(p);
            }
            rs.close();
            ps.close();
            pool.freeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return proyectos;
    }

    /**
     * Obtiene los proyectos de los que un usuario es jefe sin ordenados
     * @param usuario
     * @return 
     */
    public static ArrayList<Proyecto> selectProyectosSinOrdenar(String usuario) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT * FROM Proyectos WHERE login=?";
        ArrayList<Proyecto> proyectos = new ArrayList<Proyecto>();
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, usuario);
            rs = ps.executeQuery();
            while (rs.next()) {
                String fechaInicio = String.format("%04d-%02d-%02d", rs.getInt(5), rs.getInt(4), rs.getInt(3));
                String fechaFin = String.format("%04d-%02d-%02d", rs.getInt(8), rs.getInt(7), rs.getInt(6));
                Proyecto p = new Proyecto(rs.getInt(1), rs.getString(2), fechaInicio, fechaFin, rs.getString(9).charAt(0), usuario);
                proyectos.add(p);
            }
            rs.close();
            ps.close();
            pool.freeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return proyectos;
    }

    /**
     * Obtiene un proyecto dado su ID
     * @param idProyecto
     * @return 
     */
    public static Proyecto selectProyecto(int idProyecto) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT * FROM Proyectos WHERE id=?";
        Proyecto p = null;
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, idProyecto);
            rs = ps.executeQuery();
            if (rs.next()) {
                String fechaInicio = String.format("%02d/%02d/%04d", rs.getInt(4), rs.getInt(3), rs.getInt(5));
                String fechaFin = String.format("%02d/%02d/%04d", rs.getInt(7), rs.getInt(6), rs.getInt(8));
                p = new Proyecto(idProyecto, rs.getString(2), fechaInicio, fechaFin, rs.getString(9).charAt(0), rs.getString(10));
            }
            rs.close();
            ps.close();
            pool.freeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;

    }

    /**
     * Actualiza datos de un proyecto
     * @param p 
     */
    public static void updateProyecto(Proyecto p) {
        System.out.println(p.getEstado());
        System.out.println(p.getIdentificador());
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        int diaInicio, mesInicio, anoInicio, diaFin, mesFin, anoFin;
        int[] fechaInicio = getFechaInt(p.getFechaInicio());
        diaInicio = fechaInicio[0];
        mesInicio = fechaInicio[1];
        anoInicio = fechaInicio[2];
        int[] fechaFin = getFechaInt(p.getFechaFin());
        diaFin = fechaFin[0];
        mesFin = fechaFin[1];
        anoFin = fechaFin[2];
        String query = "UPDATE Proyectos SET nombre=?, diaInicio=?, mesInicio=?, anoInicio=?, diaFin=?, mesFin=?, anoFin=?, estado=?, login=? WHERE id=?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, p.getNombre());
            ps.setInt(2, diaInicio);
            ps.setInt(3, mesInicio);
            ps.setInt(4, anoInicio);
            ps.setInt(5, diaFin);
            ps.setInt(6, mesFin);
            ps.setInt(7, anoFin);
            ps.setString(8, "" + p.getEstado());
            ps.setString(9, p.getLogin());
            ps.setInt(10, p.getIdentificador());
            ps.executeUpdate();
            ps.close();
            pool.freeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Formatea una fecha. De String a int[]
     * @param fecha
     * @return 
     */
    private static int[] getFechaInt(String fecha) {
        int[] fechas = new int[3];
        int cont = 0;
        String num = "";
        for (int i = 0; i < fecha.length(); i++) {
            if (fecha.charAt(i) != '/') {
                if (fecha.charAt(i) != '-') {
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
        }
        return fechas;
    }

    /**
     * Obtiene todos los proyectos ordenados cronológicamente
     * @return 
     */
    public static ArrayList<Proyecto> selectTodosProyectos() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT * FROM Proyectos  ORDER BY anoInicio, mesInicio, diaInicio ASC";
        ArrayList<Proyecto> proyectos = new ArrayList<Proyecto>();
        try {
            ps = connection.prepareStatement(query);

            rs = ps.executeQuery();
            while (rs.next()) {
                String fechaInicio = String.format("%02d/%02d/%04d", rs.getInt(3), rs.getInt(4), rs.getInt(5));
                String fechaFin = String.format("%02d/%02d/%04d", rs.getInt(6), rs.getInt(7), rs.getInt(8));
                Proyecto p = new Proyecto(rs.getInt(1), rs.getString(2), fechaInicio, fechaFin, rs.getString(9).charAt(0), rs.getString("login"));
                proyectos.add(p);
            }
            rs.close();
            ps.close();
            pool.freeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return proyectos;
    }

<<<<<<< HEAD
    /**
     * 
     * @param fechaI
     * @param fechaF
     * @param login
     * @return 
     */
    public static HashMap<String, HashMap<String, ArrayList<Actividad>>> selectInformePC(String fechaI, String fechaF, String login) {
=======
    public static HashMap<String, HashMap<String, ArrayList<Actividad>>> selectInformePC(String fechaI, String fechaF) {
>>>>>>> origin/master
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        HashMap<String, HashMap<String, ArrayList<Actividad>>> inf
                = new HashMap<String, HashMap<String, ArrayList<Actividad>>>();
        //Selecciona actividades de fase de proyectos "En Curso" de determinado usuario;
        String query = "SELECT p.id as idProyecto , p.nombre as nombreP , p.diainicio as diP , p.mesinicio as miP ,"
                + "p.anoinicio as aiP, p.diafin as dfP , p.mesfin as mfP , p.anofin as afP, "
                + "p.estado as estadoP , p.login as loginP, p.numP, "
                + "f.id as idFase,f.nombre as nombreF, f.diainicio as diF , f.mesinicio as miF , f.anoinicio as aiF,"
                + " f.diafin as dfF , f.mesfin as mfF , f.anofin as afF, f.estado as estadoF, f.idProyecto as idPF ,"
                + " a.id as idActividad , a.login as loginA , a.descripcion as descripcionA , a.rol , a.duracionEstimada , "
                + "a.diainicio as diA , a.mesinicio as miA , a.anoinicio as aiA , a.diafin as dfA , a.mesfin as mfA , a.anofin as afA , "
                + "a.duracionreal , a.estado as estadoA , a.idFase as idFa "
                + "FROM  Actividades a,Fases f, Proyectos p WHERE p.estado='C' AND a.idFase=f.id AND f.idProyecto=p.id ";
        HashMap<String, ArrayList<Actividad>> fases = new HashMap<String, ArrayList<Actividad>>();
        ArrayList<Actividad> actividades = new ArrayList<Actividad>();
        try {

            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                String fechaInicioP = String.format("%04d-%02d-%02d", rs.getInt("aiP"), rs.getInt("miP"), rs.getInt("diP"));
                String fechaFinP = String.format("%04d-%02d-%02d", rs.getInt("afP"), rs.getInt("mfP"), rs.getInt("dfP"));
                String fechaInicioF = String.format("%04d-%02d-%02d", rs.getInt("aiF"), rs.getInt("miF"), rs.getInt("diF"));
                String fechaFinF = String.format("%04d-%02d-%02d", rs.getInt("afF"), rs.getInt("mfF"), rs.getInt("dfF"));
                String fechaInicioA = String.format("%04d-%02d-%02d", rs.getInt("aiA"), rs.getInt("miA"), rs.getInt("diA"));
                String fechaFinA = String.format("%04d-%02d-%02d", rs.getInt("afA"), rs.getInt("mfA"), rs.getInt("dfA"));

                Proyecto p = new Proyecto(rs.getInt("idProyecto"), rs.getString("nombreP"), fechaInicioP, fechaFinP, rs.getString("estadoP").charAt(0), rs.getString("loginP"), rs.getInt("numP"));
                Fase f = new Fase(rs.getInt("idFase"), rs.getString("nombreF"), fechaInicioA, fechaFinA, rs.getString("estadoF").charAt(0), rs.getInt("idPf"));
                Actividad a = new Actividad(rs.getInt("idActividad"), rs.getString("loginA"), rs.getString("descripcionA"), rs.getString("rol"), rs.getInt("duracionestimada"), fechaInicioA, fechaFinA, rs.getInt("duracionreal"), rs.getString("estadoA").charAt(0), rs.getInt("idFa"));

                //Comprobar si la actividad obtenida está entre el rango de fechas introducido por el usuario
                if (comprobarFechaEntreFechas(fechaI, fechaF, p)) {
                    if (inf.get(String.valueOf(p.getIdentificador())) == null) {
                        inf.put(String.valueOf(p.getIdentificador()), new HashMap<String, ArrayList<Actividad>>());
                        inf.get(String.valueOf(p.getIdentificador())).put(String.valueOf(f.getId()), new ArrayList<Actividad>());
                        inf.get(String.valueOf(p.getIdentificador())).get(String.valueOf(f.getId())).add(a);

                    } else {
                        if (inf.get(String.valueOf(p.getIdentificador())).get(String.valueOf(f.getId())) == null) {
                            inf.get(String.valueOf(p.getIdentificador())).put(String.valueOf(f.getId()), new ArrayList<Actividad>());
                            inf.get(String.valueOf(p.getIdentificador())).get(String.valueOf(f.getId())).add(a);
                        }
                        inf.get(String.valueOf(p.getIdentificador())).get(String.valueOf(f.getId())).add(a);
                    }
                }
            }
            rs.close();
            ps.close();
            pool.freeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Antes de devolver el inf es :" + inf);
        return inf;

    }

    /**
     * Comprueba si una fecha está entre un rango de fechas dado
     * @param fechaI
     * @param fechaF
     * @param p
     * @return 
     */
    private static boolean comprobarFechaEntreFechas(String fechaI, String fechaF, Proyecto p) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date fechaIU = formatter.parse(fechaI);
            java.util.Date fechaFU = formatter.parse(fechaF);
            java.util.Date fechaFP = formatter.parse(p.getFechaFin());

            if (fechaIU.compareTo(fechaFP) == 0) {
                System.out.println("holaaaa");
                return true;
            }
            if (fechaFU.compareTo(fechaFP) == 0) {
                System.out.println("holaaaa");
                return true;
            }
            if (fechaIU.compareTo(fechaFP) < 0 && fechaFU.compareTo(fechaFP) > 0) {
                System.out.println("holaaaa");
                return true;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

}
