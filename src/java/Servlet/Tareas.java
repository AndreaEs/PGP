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
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
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
 * @author Jennifer
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
                        if (a.comprobarFechaEntreFechas(fecha, a)) {
                            actFecha.add(a);
                        }
                    }

                    //Si no había ninguna actividad asignada --> avisar usuario
                    if (actFecha.isEmpty()) {
                        mensaje = "No hay ninguna actividad en esa fecha";
                    }
                    //Si solo había una actividad --> asignar tarea a esa actividad
                    if (actFecha.size() == 1) {
                        //Anadir tp a la bbdd
                        TareaDB.insert(tp);
                        correcto = true;
                        mensaje="¡Todo correcto! Asignada Tarea";
                    } else {
                        //Anadir tp a la bbdd
                        TareaDB.insert(tp);
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
                    TareaPersonal.actualizarTarea(getTareaFromParameter(request, idTarea, usuario));
                    url = getTareas(usuario, sesion);
                }
                RequestDispatcher respuesta = getServletContext().getRequestDispatcher(url);
                respuesta.forward(request, response);
            }
        }
    }

    private TareaPersonal getTareaFromParameter(HttpServletRequest request, int idTarea, String usuario) {
        String tipo = request.getParameter("tipoTarea");
        String fecha = request.getParameter("fecha");
        if (idTarea == 0) {
            return new TareaPersonal(tipo, usuario, fecha);
        } else {
            return new TareaPersonal(idTarea, tipo, usuario, fecha);
        }
    }

    private String getTareas(String usuario, HttpSession sesion) {
        ArrayList<TareaPersonal> tareas = TareaPersonal.getTareas(usuario);
        sesion.setAttribute("tareas", tareas);
        return "/vistaTareas.jsp";
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
