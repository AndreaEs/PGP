package Business;


import Data.ActividadBD;
import Data.FaseDB;
import Data.ParticipantesBD;
import Data.TablaRolesDB;
import java.io.Serializable;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jennifer
 */
public class Participante implements Serializable{
    
    private int idActividad=0;
    private String login=null;
    private double porcentaje=0;
    private String rol=null;
    private String idParticipante=null;
    
    public Participante(int idActividad, String login, double porcentaje, String rol, String idParticipante){
        this.idActividad=idActividad;
        this.login=login;
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
        this.login=login;
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
    
    public static void insertar(Participante p){
        ParticipantesBD.insert(p);
    }
    
    public static ArrayList<Participante> getParticipantes(){
        return ParticipantesBD.findAll();
    }
    
    public static ArrayList<Participante> getParticipantes(int idActividad){
        return ParticipantesBD.findByActividad(idActividad);
    }
    
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
    
    private boolean comprobarPorcentaje(String login, double porcentaje){
        if ((ParticipantesBD.getPorcentaje(login)+porcentaje)<100){
            return true;
        }
        return false;
    }
    
}
