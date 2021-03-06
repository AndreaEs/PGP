/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Business.Actividad;
import Business.TareaPersonal;
import Data.ActividadBD;
import Data.TareaDB;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author grupo06
 */
@WebServlet(name = "Tareas", urlPatterns = {"/Tareas"})
public class Tareas extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sesion = request.getSession();
        String usuario = (String) sesion.getAttribute("user");
        String url = null;
        if (usuario != null) {
            String accion = request.getParameter("tarea");
            if (accion != null) {
                if (accion.equals("crearTarea")) {
                    String tipo = request.getParameter("tipoTarea");
                    String fecha = request.getParameter("fecha");
                    String mensaje = "";
                    boolean correcto = false;
                    TareaPersonal tp = new TareaPersonal();
                    tp.setLogin(usuario);
                    tp.setTipo(tipo);
                    tp.setFecha(fecha);
                    
                    try {
                        //Comprobaciones de un evento tipo Tarea Personal
                        //Comprobar que no quiere asignar una tarea en fin de semana
                        if (!tp.tareaFinSemana(fecha)) {
                            mensaje = "No puedes asignar una tarea en fin de semana";
                        }
                    } catch (ParseException ex) {
                        Logger.getLogger(Tareas.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    try {
                        //Comprobar que ese usuario tiene menos de 25 tareas asignadas para esa semana
                        if (!TareaDB.tareasPersonalesSemana(usuario, fecha)) {
                            mensaje = "El usuario " + usuario + " ya tiene asignadas 24 tareas personales para la semana del " + fecha;
                        }
                    } catch (ParseException ex) {
                        Logger.getLogger(Tareas.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    //Obtener actividades de ese usuario
                    List<Actividad> actividades = new ArrayList<Actividad>();
                    actividades = ActividadBD.selectActividades(usuario);

                    //Comprobar que en esa fecha hay al menos una actividad
                    List<Actividad> actFecha = new ArrayList<Actividad>();
                    for (int i = 0; i < actividades.size(); i++) {
                        Actividad a = actividades.get(i);
                        if (a.comprobarFechaEntreFechas(fecha, a) && a.getEstado()!='C') {
                            actFecha.add(a);
                        }
                    }

                    //Si no había ninguna actividad asignada --> avisar usuario
                    //no hacer insert
                    if (actFecha.isEmpty()) {
                        mensaje = "No hay ninguna actividad en esa fecha";
                    }
                    //Si solo había una actividad --> asignar tarea a esa actividad
                    if (actFecha.size() >= 1) {
                        if(comprobarFechaActual(fecha)){
                        //Anadir tp a la bbdd
                        TareaDB.insert(tp);
                        correcto = true;
                        mensaje="¡Todo correcto! Asignada Tarea";
                        } else {
                            mensaje="La fecha es posterior a la actual";
                        }
                    } 

                    if (!correcto) {
                        url = "/Tareas?tarea=crearNuevaTarea";
                    } else {
                        ArrayList<TareaPersonal> tareas = TareaPersonal.getTareas(usuario);
                        sesion.setAttribute("tareas", tareas);
                        url = "/vistaTareas.jsp";
                    }
                    request.setAttribute("mensaje", mensaje);
                    sesion.setAttribute("user", (String) sesion.getAttribute("user"));
                } else if (accion.equals("verTareas")) {
                    url = getTareas(usuario, sesion);
                } else if (accion.equals("crearNuevaTarea")) {
                    sesion.setAttribute("actualizar", false);
                    url = "/tarea.jsp";
                } else if (accion.equals("actualizarUnaTarea")) {
                    int idTarea = Integer.parseInt(request.getParameter("idTarea"));
                    sesion.setAttribute("actualizar", true);
                    sesion.setAttribute("idTarea", idTarea);
                    TareaPersonal t = TareaPersonal.getTarea(idTarea);
                    sesion.setAttribute("tarea", t);
                    url = "/tarea.jsp";
                } else if (accion.equals("actualizarTarea")) {
                    int idTarea = Integer.parseInt(request.getParameter("idTarea"));
                    TareaPersonal tp = TareaPersonal.getTarea(idTarea);
                    boolean correcto=false;
                    String fecha = request.getParameter("fecha");
                    System.err.println("FECHA: "+fecha);
                    String mensaje="";
                    try {
                        System.err.println("dentro del primer try");
                        //Comprobaciones de un evento tipo Tarea Personal
                        //Comprobar que no quiere asignar una tarea en fin de semana
                        if (!tp.tareaFinSemana(fecha)) {
                            mensaje = "No puedes asignar una tarea en fin de semana, inténtelo de nuevo";
                        }
                    } catch (ParseException ex) {
                        Logger.getLogger(Tareas.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    //Obtener actividades de ese usuario
                    List<Actividad> actividades = new ArrayList<Actividad>();
                    actividades = ActividadBD.selectActividades(usuario);

                    //Comprobar que en esa fecha hay al menos una actividad
                    List<Actividad> actFecha = new ArrayList<Actividad>();
                    for (int i = 0; i < actividades.size(); i++) {
                        Actividad a = actividades.get(i);
                        if (a.comprobarFechaEntreFechas(fecha, a) && a.getEstado()!='C') {
                            actFecha.add(a);
                        }
                    }

                    //Si no había ninguna actividad asignada --> avisar usuario
                    //no hacer insert
                    if (actFecha.isEmpty()) {
                        mensaje = "No hay ninguna actividad en esa fecha, inténtelo de nuevo";
                    }
                    //Si solo había una actividad --> asignar tarea a esa actividad
                    if (actFecha.size() >= 1) {
                        if(comprobarFechaActual(fecha)){
                        //Anadir tp a la bbdd
                        TareaPersonal.actualizarTarea(getTareaFromParameter(request, idTarea, usuario));
                        correcto = true;
                        mensaje="¡Todo correcto! Tarea actualizada";
                        } else {
                            mensaje="La fecha es posterior a la actual, inténtelo de nuevo";
                        }
                    } 

                    
                        ArrayList<TareaPersonal> tareas = TareaPersonal.getTareas(usuario);
                        sesion.setAttribute("tareas", tareas);
                        url = "/vistaTareas.jsp";
                    
                    request.setAttribute("mensaje", mensaje);
                    sesion.setAttribute("user", (String) sesion.getAttribute("user"));
                    
                }
                RequestDispatcher respuesta = getServletContext().getRequestDispatcher(url);
                respuesta.forward(request, response);
            }
        }
    }

    /**
     * Crear una tarea
     * @param request
     * @param idTarea
     * @param usuario
     * @return 
     */
    private TareaPersonal getTareaFromParameter(HttpServletRequest request, int idTarea, String usuario) {
        String tipo = request.getParameter("tipoTarea");
        String fecha = request.getParameter("fecha");
        if (idTarea == 0) {
            return new TareaPersonal(tipo, usuario, fecha);
        } else {
            return new TareaPersonal(idTarea, tipo, usuario, fecha);
        }
    }

    /**
     * Obtener tareas de un usuario
     * @param usuario
     * @param sesion
     * @return 
     */
    private String getTareas(String usuario, HttpSession sesion) {
        ArrayList<TareaPersonal> tareas = TareaPersonal.getTareas(usuario);
        sesion.setAttribute("tareas", tareas);
        return "/vistaTareas.jsp";
    }
    
    /**
     * Comprobar si una fecha es la de hoy
     * @param fecha
     * @return 
     */
    private boolean comprobarFechaActual(String fecha){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try{
          Date actual = new Date();
          java.util.Date f = formatter.parse(fecha);
          if(f.before(actual)){
              return false;
          } else {
              return true;
          }
        } catch (ParseException e){
            e.printStackTrace();
        }
        
        return false;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
