/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import Data.ProyectoDB;
import Data.UserDB;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author grupo06
 * Representación de un proyecto
 */
public class Proyecto implements Serializable {

    private int identificador;
    private String nombre;
    private String fechaInicio;
    private String fechaFin;
    private char estado;
    private String login;
    private int numP;

    public Proyecto(String nombre, String fechaInicio, String fechaFin, String login) {
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.login = login;
        this.estado = 'S';

    }

    public Proyecto(String nombre, String fechaInicio, String fechaFin, char estado, String login) {
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.login = login;
        this.estado = estado;
    }

    public Proyecto(String nombre, String fechaInicio, String fechaFin, char estado, String login, int numP) {
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.login = login;
        this.estado = estado;
        this.numP = numP;
    }

    public Proyecto(int identificador, String nombre, String fechaInicio, String fechaFin, char estado, String login) {
        this.identificador = identificador;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.login = login;
        this.estado = estado;
    }

    public Proyecto(int identificador, String nombre, String fechaInicio, String fechaFin, char estado, String login, int numP) {
        this.identificador = identificador;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.login = login;
        this.estado = estado;
        this.numP = numP;
    }

    public int getIdentificador() {
        return identificador;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public char getEstado() {
        return estado;
    }

    public String getLogin() {
        return login;
    }

    public int getNumP() {
        return numP;
    }

    public void setNumP(int numP) {
        this.numP = numP;
    }
   
    /**
     * Llama a ProyectoDB.insert para insertar un proyecto en la BBDD
     * @param p --> Proyecto a insertar
     */
    public static void guardarNuevoProyecto(Proyecto p) {
        ProyectoDB.insert(p);
    }

    /**
     * Llama a ProyectoDB.selectProyectos para obtener los proyectos
     * de los que un usuario es jefe
     * @param usuario --> Usuario a obtener los proyectos de los que es jefe
     * @return Proyectos de los que es jefe
     */
    public static ArrayList<Proyecto> getProyectos(String usuario) {
        User u = UserDB.getUsuario(usuario);
        return ProyectoDB.selectProyectos(u.getNif());
    }

    /**
     * Llama a ProyectoDB.selectTodosProyectos para obtener todos los proyectos
     * de la BBDD
     * @return ArrayList con todos los proyectos
     */
    public static ArrayList<Proyecto> getTodosProyectos() {
        return ProyectoDB.selectTodosProyectos();
    }

    /**
     * Llama a ProyectoDB.selectProyecto para obtener un proyecto dado su ID
     * @param idProyecto --> ID del proyecto a obtener
     * @return Proyecto con dicho ID
     */
    public static Proyecto getProject(int idProyecto) {
        return ProyectoDB.selectProyecto(idProyecto);
    }

    /**
     * Llama a ProyectoDB.updateProyecto para actualizar un proyecto
     * @param p --> Proyecto a actualizar
     */
    public static void actualizarProyecto(Proyecto p) {
        ProyectoDB.updateProyecto(p);
    }

    /**
     * Llama a ProyectoDB.selectProyectosSinOrdenar para obtener los proyectos
     * de un usuario sin ordenar
     * @param usuario --> Usuario a obtener los proyectos
     * @return ArrayList de Proyectos
     */
    public static ArrayList<Proyecto> getProyectosSinOrdenar(String usuario) {
        User u = UserDB.getUsuario(usuario);
        return ProyectoDB.selectProyectosSinOrdenar(u.getNif());
    }

}
