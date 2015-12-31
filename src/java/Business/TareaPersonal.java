/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import Data.TareaDB;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author gil
 */
public class TareaPersonal implements Serializable{
    
    private int id;
    private String tipo;
    private String login;
    private String fecha;
    private int actividad;

    
    public TareaPersonal(){
        this.id=0;
        this.tipo="";
        this.login="";
        this.fecha="";
        this.actividad=0;
    }
    
    public TareaPersonal(String tipo, String login, String fecha, int actividad){
        this.tipo=tipo;
        this.login=login;
        this.fecha=fecha;
        this.actividad=actividad;
    }
    
    public TareaPersonal(int id, String tipo, String login, String fecha, int actividad){
        this.id=id;
        this.tipo=tipo;
        this.login=login;
        this.fecha=fecha;
        this.actividad=actividad;
    }

    public int getId(){
        return id;
    }
    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    public int getActividad() {
        return actividad;
    }

    public void setActividad(int actividad) {
        this.actividad = actividad;
    }
    
    public static void guardarNuevaTarea(TareaPersonal tp){
        TareaDB.insert(tp);
    }
    
    public static ArrayList<TareaPersonal> getTareas(String usuario){
        return TareaDB.findAll(usuario);
    }
    
    public static TareaPersonal getTarea(int idTarea){
        return TareaDB.find(idTarea);
    }
    
    public static void actualizarTarea(TareaPersonal tp){
        TareaDB.updateTarea(tp);
    }
    
}
