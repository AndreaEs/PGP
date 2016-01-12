/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import Data.ActividadBD;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author grupo06
 * Representación de las actividades de una fase de un proyecto
 */
public class Actividad implements Serializable{

    private int identificador;
    private String login;
    private String descripcion;
    private String rolNecesario;
    private int duracionEstimada;
    private String fechaInicio;
    private String fechaFin;
    private int duracionReal;
    private char estado;
    private int idFase;
    private List<Integer> predecesoras;


    public Actividad(String login, String descripcion , String rolNecesario, int duracionEstimada, String fechaInicio, String fechaFin, int duracionReal, char estado, int idFase) {

        this.descripcion = descripcion;
        this.rolNecesario = rolNecesario;
        this.duracionEstimada = duracionEstimada;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.duracionReal = duracionReal;
        this.estado = estado;
        this.idFase = idFase;
        this.login=login;
    }


    public Actividad(int identificador, String login, String descripcion, String rolNecesario, int duracionEstimada, String fechaInicio, String fechaFin, int duracionReal, char estado, int idFase) {

        this.identificador = identificador;
        this.descripcion = descripcion;
        this.rolNecesario = rolNecesario;
        this.duracionEstimada = duracionEstimada;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.duracionReal = duracionReal;
        this.estado = estado;
        this.idFase = idFase;
        this.login=login;
    }

    public int getIdentificador() {
        return identificador;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getRolNecesario() {
        return rolNecesario;
    }

    public int getDuracionEstimada() {
        return duracionEstimada;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public char isEstado() {
        return estado;
    }


    public int getDuracionReal() {
        return duracionReal;
    }

    public char getEstado() {
        return estado;
    }

    public int getIdFase() {
        return idFase;
    }
    
     public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }

    public List<Integer> getPredecesoras() {
        return predecesoras;
    }

    public void setPredecesoras(List<Integer> predecesoras) {
        this.predecesoras = predecesoras;
    }

    /**
     * Pasa a la función ActividadBD.insertActividad una actividad
     * @param a 
     */
    public static void guardarNuevaActividad(Actividad a) {
        ActividadBD.insertActividad(a);
    }

    /**
     * Llama a la función ActividadBD.selectActividades para obtener
     * las actividades de una fase
     * @param idFase --> ID de la fase en la que está la actividad
     * @return ArrayList de actividades
     */
    public static ArrayList<Actividad> getFase(int idFase) {
        return ActividadBD.selectActividades(idFase);   
    }

    /**
     * Llama a ActividadBD.updateActividad para actualizar una actividad
     * @param a --> Actividad a actualizar
     */
    public static void actualizarActividad(Actividad a) {
        ActividadBD.updateActividad(a);
    }

    /**
     * Llama a ActividadBD.selectActividad para obtener una actividad
     * @param idActividad --> ID de la actividad a devolver
     * @return Actividad encontrada
     */
    public static Actividad getActivity(int idActividad) {
        return ActividadBD.selectActividad(idActividad);
    }
    
    /**
     * Llama a ActividadBD.selectActividades para obtener las actividades
     * de un usuario
     * @param user --> Usuario del que se quieren obtener las actividades
     * @return ArrayList con las actividades del usuario
     */
    public static ArrayList<Actividad> getActividades(String user) {
        return ActividadBD.selectActividades(user);
    }
    
    /**
     * Llama a ActividadBD.selectActividadesLoginOrdenadas para obtener 
     * actividades de usuario ordenadas cronológicamente
     * @param user --> Usuario del que se quieren obtener las actividades
     * @return Actividades ordenadas cronológicamente
     */
    public static ArrayList<Actividad> getActividadesLoginOrdenadas(String user) {
        return ActividadBD.selectActividadesLoginOrdenadas(user);
    }
    
    /*date1.comparetp(date2) > 0 --> date1 esta después de date2
     date1.comparetp(date2) < 0 --> date1 esta antes de date2*/
    /**
     * Comprueba si en la fecha en la que se quiere añadir una tarea  
     * personal existe una actividad programada 
     * @param fecha --> De la tarea personal
     * @param a --> Actividad a comprobar sus fechas
     * @return true si la fecha de la tarea personal entra en el rango
     * de las fechas de la actividad, false en caso contrario
     */
    public boolean comprobarFechaEntreFechas(String fecha, Actividad a) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date fechaI = formatter.parse(a.getFechaInicio());
            java.util.Date fechaF = formatter.parse(a.getFechaFin());
            java.util.Date fechaTP = formatter.parse(fecha);
            if (fechaI.compareTo(fechaTP)==0)
                return true;
            if (fechaF.compareTo(fechaTP)==0)
                return true;
            if (fechaI.compareTo(fechaTP) < 0 && fechaF.compareTo(fechaTP) > 0)
                return true;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    
}
