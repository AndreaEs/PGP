/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import Data.ActividadBD;
import Data.ParticipantesBD;
import Data.TareaDB;
import Data.UserDB;
import Data.VacacionesDB;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author grupo06
 * Representación de un usuario
 */
public class User implements Serializable{
    
    private String login;
    private String pass;
    private char tipo;
    private String nif;
    private int maxProy;
    private String infoGeneral;
    
    public User(){
        login="";
        pass="";
        tipo=' ';
        nif="";
        infoGeneral="";
    }
    
    public User(String login, String pass, char tipo, String nif){
        this.login=login;
        this.pass=pass;
        this.tipo=tipo;
        this.nif=nif;
        this.infoGeneral="";
    }
    
    public User(String login, String pass, char tipo, String nif, String infoGeneral){
        this.login=login;
        this.pass=pass;
        this.tipo=tipo;
        this.nif=nif;
        this.infoGeneral=infoGeneral;
    }
    public User(String login, String pass, char tipo, String nif, String infoGeneral, int maxProy){
        this.login=login;
        this.pass=pass;
        this.tipo=tipo;
        this.nif=nif;
        this.infoGeneral=infoGeneral;
        this.maxProy = maxProy;
    }
    
    public String getLogin(){
        return login;
    }
    
    public String getPass(){
        return pass;
    }
    
    public char getTipo(){
        return tipo;
    }
    
    public String getNif(){
        return nif;
    }
     public void setNif(String nif){
        this.nif = nif;
    }
    
    public int getMaxProy(){
        return maxProy;
    }
    public void setMaxProy(int maxProy){
        this.maxProy= maxProy;
    }
    
    public String getInfoGeneral(){
        return infoGeneral;
    }
    
    public void setLogin(String login){
        this.login = login;
    }
    
    public void setPass(String pass){
        this.pass=pass;
    }
    
    public void setTipo(char tipo){
        this.tipo = tipo;
    }
    
    public void setInfoGeneral(String infoGeneral){
        this.infoGeneral=infoGeneral;
    }
    
    /**
     * Llama a UserDB.exist para comprobar la existencia de un usuario
     * @param login --> Login a comprobar la existencia
     * @return true si existe, false en caso contrario
     */
    public static boolean exist(String login){
        return UserDB.exist(login);
    }
    
    /**
     * Llama a UserDB.insert para insertar un usuario en la BBDD
     * @param user --> Usuario a insertar
     */
    public static void insert(User user){
        UserDB.insert(user);
    }
    
    /**
     * Borra un usuario
     * @param user --> Usuario a borrar
     */
    public static void delete(User user){
        ArrayList<TareaPersonal> tareas = new ArrayList();
        tareas = TareaDB.getTareas(user.getLogin()); 
        for(int i =0; i<tareas.size(); i++){
            TareaDB.delete(tareas.get(i));
        }
        ArrayList<Participante> participaciones = new ArrayList();
        participaciones = ParticipantesBD.getParticipaciones(user.getLogin());
        System.out.println("vamos a borrar a los participantes");
        for(int i = 0; i<participaciones.size(); i++){
           ParticipantesBD.delete(user.getLogin());
        }
        ArrayList<Actividad> actividades = new ArrayList();
        actividades = ActividadBD.selectActividades(user.getLogin());
        for(int i =0; i<actividades.size(); i++){
            Actividad a = new Actividad( actividades.get(i).getIdentificador(),null,actividades.get(i).getDescripcion(),
            actividades.get(i).getRolNecesario(), actividades.get(i).getDuracionEstimada(),actividades.get(i).getFechaInicio(),
                    actividades.get(i).getFechaFin(), actividades.get(i).getDuracionReal(),actividades.get(i).getEstado(),actividades.get(i).getIdFase());
            ActividadBD.updateActividadUsuario(a);
        }
        System.out.println("justo aqui");
        
        ArrayList<Vacaciones> vacaciones = new ArrayList();
        vacaciones = (ArrayList) VacacionesDB.obtenerVacaciones(user.getLogin());
        for(int i =0; i<vacaciones.size(); i++){
            VacacionesDB.delete(vacaciones.get(i));
        }
        
        UserDB.delete(user);
    }
    
    /**
     * Llama a UserDB.update para actualizar la información de un usuario
     * @param user --> Usuario a actualizar
     */
    public static void update(User user){
        UserDB.update(user);
    }
    
    /**
     * Llama a UserDB.getPosiblesJefes para obtener usuarios candidatos a 
     * Jefe de proyecto
     * @return Array de usuarios posibles
     */
    public static ArrayList<String> getPosiblesJefes(){
        return UserDB.getPosiblesJefes();
    }
    
    /**
     * Llama a UserDB.getJefe para obtener un jefe dado su DNI
     * @param dni --> DNI del jefe a buscar
     * @return Login del jefe de proyecto
     */
    public static String getJefe(String dni){
        return UserDB.getJefe(dni);
    }
}
