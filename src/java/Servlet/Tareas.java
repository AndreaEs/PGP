/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Business.TareaPersonal;
import java.io.IOException;
import java.util.ArrayList;
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
        if(usuario!=null){
            String accion = request.getParameter("tarea");
            if(accion!=null){
                if(accion.equals("crearTarea")){
                    TareaPersonal.guardarNuevaTarea(getTareaFromParameter(request, 0, usuario));
                    url=getTareas(usuario, sesion);
                } else if (accion.equals("verTareas")){
                    url = getTareas(usuario, sesion);
                } else if (accion.equals("crearNuevaTarea")) {
                    sesion.setAttribute("actualizar", false);
                    url="/tarea.jsp";
                } else if(accion.equals("actualizarUnaTarea")){
                    int idTarea = Integer.parseInt(request.getParameter("idTarea"));
                    sesion.setAttribute("actualizar", true);
                    sesion.setAttribute("idTarea", idTarea);
                    TareaPersonal t = TareaPersonal.getTarea(idTarea);
                    sesion.setAttribute("tarea", t);
                    url="/tarea.jsp";
                } else if (accion.equals("actualizarTarea")){
                    int idTarea = Integer.parseInt(request.getParameter("idTarea"));
                    TareaPersonal.actualizarTarea(getTareaFromParameter(request,idTarea, usuario));
                    url = getTareas(usuario, sesion);
                }
                RequestDispatcher respuesta = getServletContext().getRequestDispatcher(url);
                respuesta.forward(request, response);
            }
        }
    }
    
    private TareaPersonal getTareaFromParameter(HttpServletRequest request, int idTarea, String usuario){
        String tipo = request.getParameter("tipoTarea");
        String fecha = request.getParameter("fecha");
        if(idTarea==0){
            return new TareaPersonal(tipo, usuario, fecha);
        } else {
            return new TareaPersonal(idTarea,tipo, usuario,fecha);
        }
    }

    private String getTareas(String usuario, HttpSession sesion){
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
