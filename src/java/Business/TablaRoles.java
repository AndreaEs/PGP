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
 * @author grupo06
 * Representación de la Tabla de roles
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
    
    /**
     * Llama a TablaRolesDB.insert para rellenar una fila de la tabla de roles
     * @param idProyecto --> ID del proyecto
     * @param desarrollador --> Login del desarrollador
     * @param categoria --> Categoria a asignar a ese desarrollados en ese proyecto
     */
    public static void insert(int idProyecto, String desarrollador, String categoria) {
        TablaRolesDB.insert(idProyecto,desarrollador,categoria);
    }
    
    /**
     * Llama a TablaRolesDB.exist para comprobar la existencia de un proyecto
     * @param idP --> ID del proyecto
     * @return true si existe, false en caso contrario
     */
    public static boolean exist(int idP){
        return TablaRolesDB.exist(idP);
    }
    
    /**
     * Llama a TablaRolesDB.getIdParticipante para obtener los participantes de 
     * un proyecto
     * @param idProyecto --> ID del proyecto a obtener los participantes
     * @return ArrayList de participantes
     */
    public static ArrayList<String> getParticipantes(int idProyecto){
        return TablaRolesDB.getIdParticipante(idProyecto);
    }
    
}
