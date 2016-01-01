/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

/**
 *
 * @author gil
 */
public class TareaPersonal {
    
    private String tipo;
    private String login;
    private String fecha;
    private Actividad actividad;
    private int duracion;
    
    public TareaPersonal(){
        this.tipo="";
        this.login="";
        this.fecha="";
        this.actividad=null;
        this.duracion=0;
    }
    
    public TareaPersonal(String tipo, String login, String fecha, Actividad actividad, int duracion){
        this.tipo=tipo;
        this.login=login;
        this.fecha=fecha;
        this.actividad=actividad;
        this.duracion=duracion;
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
    
    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }
    
}
