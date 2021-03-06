/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import Data.TareaDB;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author grupo06
 * Representación de una tarea personal
 */
public class TareaPersonal implements Serializable{

    private int id;
    private String tipo;
    private String login;
    private String fecha;

    public TareaPersonal() {
        this.id = 0;
        this.tipo = "";
        this.login = "";
        this.fecha = "";
    }
    
    public TareaPersonal(String tipo, String login, String fecha){
        this.tipo = tipo;
        this.login = login;
        this.fecha = fecha;
    }

    public TareaPersonal(int id, String tipo, String login, String fecha) {
        this.id = id;
        this.tipo = tipo;
        this.login = login;
        this.fecha = fecha;
    }

    public int getId() {
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

    public static void guardarNuevaTarea(TareaPersonal tp) {
        TareaDB.insert(tp);
    }

    public static ArrayList<TareaPersonal> getTareas(String usuario) {
        return TareaDB.findAll(usuario);
    }

    /**
     * Llama a TareaDB.find para buscar una tarea
     * @param idTarea --> ID de la tarea a buscar
     * @return Tarea
     */
    public static TareaPersonal getTarea(int idTarea) {
        return TareaDB.find(idTarea);
    }

    /**
     * Llama a TareaDB.updateTarea para actualizar una tarea
     * @param tp --> Tarea a actualizar
     */
    public static void actualizarTarea(TareaPersonal tp) {
        TareaDB.updateTarea(tp);
    }
    
    /**
     * Comprueba si se quiere asignar una tarea en fin de semana
     * @param fecha --> Fecha a comprobar
     * @return false si cae en fin de semana, true en caso contrario
     * @throws ParseException 
     */
    public boolean tareaFinSemana(String fecha) throws ParseException{
        //Pasar String a Calendar
        Calendar f = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        f.setTime(formatter.parse(fecha));
        //Obtener semana de dicha fecha
        int dia = f.get(Calendar.DAY_OF_WEEK);
        if(dia==Calendar.SATURDAY || dia==Calendar.SUNDAY)
            return false;
        else
            return true;
    }

}
