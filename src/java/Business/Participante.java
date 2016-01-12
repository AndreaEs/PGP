package Business;


import Data.ParticipantesBD;
import Data.TablaRolesDB;
import Data.VacacionesDB;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author grupo06
 * Representación de un participante en un proyecto
 */
public class Participante implements Serializable{
    
    private int idActividad=0;
    private String login=null;
    private double porcentaje=0;
    private String rol=null;
    private String idParticipante=null;
    
    public Participante(int idActividad, String login, double porcentaje, String rol, String idParticipante){
        this.idActividad=idActividad;
        if(comprobarLogin(login, idActividad)) this.login=login;
        if(comprobarPorcentaje(login, porcentaje)) this.porcentaje=porcentaje;
        if(comprobarRol(rol)) this.rol=rol;
        this.idParticipante=idParticipante;
    }
    
    public int getIdActividad(){
        return idActividad;
    }
    
    public String getLogin(){
        return login;
    }
    
    public double getPorcentaje(){
        return porcentaje;
    }
    
    public String getRol(){
        return rol;
    }
    
    public String getIdParticipante(){
        return idParticipante;
    }
    
    public void setIdActividad(int idActividad){
        this.idActividad=idActividad;
    }
    
    public void setLogin(String login){
        if(comprobarLogin(login, idActividad)) this.login=login;
    }
    
    public void setPorcentaje(double porcentaje){
        if(comprobarPorcentaje(login, porcentaje)){
            this.porcentaje=porcentaje;
        } 
    }
    
    public void setRol(String rol){
        if(comprobarRol(rol)){
            this.rol=rol;
        }
    }
    
    public void setIdParticipante(String idParticipante){
        this.idParticipante=idParticipante;
    }
    
    /**
     * Llama a ParticipantesBD.insert para insertar un nuevo participante
     * @param p --> Participante a insertar
     */
    public static void insertar(Participante p){
        ParticipantesBD.insert(p);
    }
    
    /**
     * Llama a ParticipantesBD.findAll para obtener una lista de participantes
     * @return --> ArrayList de Participantes
     */
    public static ArrayList<Participante> getParticipantes(){
        return ParticipantesBD.findAll();
    }
    
    /**
     * Llama a ParticipantesBD.findByActividad para obtener los participantes
     * de una actividad
     * @param idActividad --> Actividad de la que se quiere obtener los participantes
     * @return --> Participantes de dicha actividad
     */
    public static ArrayList<Participante> getParticipantes(int idActividad){
        return ParticipantesBD.findByActividad(idActividad);
    }
    
    /**
     * Llama a otra función para obtener los roles disponibles en un 
     * proyecto
     * @param idProyecto --> ID del proyecto a obtener los roles
     * @return ArrayList de roles
     */
    public static ArrayList<String> getParticipantesDisponibles(int idProyecto){
        ArrayList<String> roles = TablaRolesDB.getIdParticipante(idProyecto);
        ArrayList<Participante> part = getParticipantes();
        for (Participante part1 : part) {
            if (Fase.getPhase(Actividad.getActivity(part1.getIdActividad()).getIdFase()).getIdProyecto() == idProyecto) {
                if(roles.contains(part1.getIdParticipante())) roles.remove(part1.getIdParticipante());
            }
        }
        return roles;
    }
    
    /**
     * Comprueba la existencia de un rol
     * @param rol --> Rol a comprobar su existencia
     * @return true si existe el rol introducido, false en caso contrario
     */
    private boolean comprobarRol(String rol){
        switch(rol){
            case "JP":
                return true;
            case "AN":
                return true;
            case "DI":
                return true;
            case "AP":
                return true;
            case "RP":
                return true;
            case "PG":
                return true;
            case "PR":
                return true;
            default:
                return false;
        }
    }
    
    /**
     * Llama a ParticipantesBD.getPorcentaje para comprobar si el porcentaje
     * asignado a un usuario es correcto
     * @param login --> Usuario a comprobar su porcentaje de participación
     * @param porcentaje --> Porcentaje a comprobar
     * @return true si se puede asignar ese porcentaje a ese usuario, false en 
     * caso contrario
     */
    private boolean comprobarPorcentaje(String login, double porcentaje){
        if ((ParticipantesBD.getPorcentaje(login)+porcentaje)<100){
            return true;
        }
        return false;
    }
    
    /**
     * Llama a una función que obtiene de la BBDD los proyectos de 
     * los que es jefe un usuario
     * @param login --> Del usuario a comprobar su estado
     * @param idActividad --> 
     * @return va a comprobarVacaciones 
     */
    private boolean comprobarLogin(String login, int idActividad){
        ArrayList<Proyecto> proy = Proyecto.getProyectos(login);
        if(ParticipantesBD.exist(login) && proy.size()>0){
            return false;
        }
        return comprobarVacaciones(login, idActividad);
    }
    
    /**
     * Comprueba si a un participante se le quiere añadir actividades
     * cuando ya tiene asignadas vacaciones
     * @param login --> Usuario del que comprobar las vacaciones
     * @param idActividad --> Actividad a asignar al usuario
     * @return true si se le puede asignar las vacaciones, false en caso contrario
     */
    private boolean comprobarVacaciones(String login, int idActividad){
        Actividad a = Actividad.getActivity(idActividad);
        List<Vacaciones> tmp = VacacionesDB.obtenerVacaciones(login);
        for (Vacaciones tmp1 : tmp) {
            if (tmp1.comprobarRangosEntreFechas(a.getFechaInicio(), a.getFechaFin(), tmp1)) {
                return false;
            }
        }
        return true;
    }
}
