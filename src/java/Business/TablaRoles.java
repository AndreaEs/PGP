/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import Data.TablaRolesDB;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Sandra
 */
public class TablaRoles implements Serializable {

    
    
    private int idProyecto;
    private String idParticipante;
    private String categoria;
    
    public TablaRoles(){
        this.idProyecto = 0;
        this.idParticipante="";
        this.categoria="";
    }
    public TablaRoles(int idProyecto , String idParticipante , String categoria){
        
        this.idProyecto = idProyecto;
        this.idParticipante = idParticipante;
        this.categoria= categoria;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(String idParticipante) {
        this.idParticipante = idParticipante;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public static void insert(int idProyecto, String desarrollador, String categoria) {
        TablaRolesDB.insert(idProyecto,desarrollador,categoria);
    }
    public static boolean exist(int idP){
        return TablaRolesDB.exist(idP);
    }
    
    public static ArrayList<String> getParticipantes(int idProyecto){
        return TablaRolesDB.getIdParticipante(idProyecto);
    }
    
}
