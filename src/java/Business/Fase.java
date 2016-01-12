/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import Data.FaseDB;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author grupo06
 * Representación de una fase de un proyecto
 */
public class Fase implements Serializable{

    private int id;
    private String nombre;
    private String fechaInicio;
    private String fechaFin;
    private char estado;
    private int idProyecto;

   public Fase (String nombre, String fechaInicio, String fechaFin, int idProyecto){
       this.nombre=nombre;
       this.fechaInicio=fechaInicio;
       this.fechaFin=fechaFin;
       this.idProyecto=idProyecto;
       this.estado='S';
       
   }
   
   public Fase (String nombre, String fechaInicio, String fechaFin ,char estado, int idProyecto){
       this.nombre=nombre;
       this.fechaInicio=fechaInicio;
       this.fechaFin=fechaFin;
       this.idProyecto=idProyecto;
       this.estado=estado;
       
   }
   
   public Fase (int id, String nombre, String fechaInicio, String fechaFin ,char estado, int idProyecto){
       this.id=id;
       this.nombre=nombre;
       this.fechaInicio=fechaInicio;
       this.fechaFin=fechaFin;
       this.idProyecto=idProyecto;
       this.estado=estado;
       
   }

    public int getId() {
        return id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
   public String getFechaInicio(){
       return fechaInicio;
   }
   
   public String getFechaFin(){
       return fechaFin;
   }

    public char getEstado() {
        return estado;
    }

    public int getIdProyecto() {
        return idProyecto;
    }
    
    public void setEstado(char estado){
        this.estado=estado;
    }
    
    /**
     * Llama a FaseDB.insert para insertar una nueva fase en la BBDD
     * @param f --> Fase a insertar
     */
    public static void crearNuevaFase(Fase f){
        FaseDB.insert(f);
    }
    
    /**
     * Llama a FaseDB.selectFases para obtener las fases de un proyecto
     * dado su ID
     * @param idProyecto --> ID del proyecto 
     * @return --> ArrayList de fases en el proyecto
     */
    public static ArrayList<Fase> getFase(int idProyecto) {
        return FaseDB.selectFases(idProyecto);
    }
    
    /**
     * Llama a FaseDB.selectFase para obtener una fase dado su ID
     * @param idFase --> ID de la fase a obtener
     * @return Fase a la que corresponde dicho ID
     */
    public static Fase getPhase(int idFase) {		
        return FaseDB.selectFase(idFase);		
    }		
 		
    /**
     * Llama a FaseDB.updateFase para actualizar una fase
     * @param f --> Fase a actualizar
     */
    public static void actualizarFase(Fase f) {		
        FaseDB.updateFase(f);		
    }

}
