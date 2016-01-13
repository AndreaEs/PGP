/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Business.Fase;
import Business.Proyecto;
import Business.TablaRoles;
import Business.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
@WebServlet(name = "Proyectos", urlPatterns = {"/Proyectos"})
public class Proyectos extends HttpServlet {

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
        
            String accion = request.getParameter("proyecto");
            if (accion != null) {
                if (accion.equals("crearProyecto")) {
                   sesion.setAttribute("proyecto-nif",(String)request.getParameter("jefe-proyecto") );
                    String user =  (String)request.getParameter("jefe-proyecto");
                    String numero = (String) request.getParameter("numero-participantes");
                    if(!comprobarFechas(request)){
                        sesion.setAttribute("mensaje","La fecha es posterior a la fecha actual");
                    } else {
                        Proyecto.guardarNuevoProyecto(getProyectoFromParameters(request, 0, user,Integer.valueOf(numero)));
                    }
                    url = getTodosProyectos(sesion);
                } else if (accion.equals("verTodosProyectos")) {
                    url = getTodosProyectos(sesion);
                } else if (accion.equals("verProyectos")) {
                    url = getProyectos(usuario, sesion);
                } else if (accion.equals("crearNuevoProyecto")) {
                    sesion.setAttribute("actualizar", false);
                    url = "/proyecto.jsp";
                } else if(accion.equals("actualizarUnProyecto")){
                    int idProyecto = Integer.parseInt(request.getParameter("idProyecto"));
                    //sesion.setAttribute("user", user);
                    sesion.setAttribute("actualizar", true);
                    sesion.setAttribute("idProyecto", idProyecto);
                    Proyecto p = Proyecto.getProject(idProyecto);
                    sesion.setAttribute("proyecto", p);
                    url = "/proyecto.jsp";
                }else if(accion.equals("actualizarProyecto")){
                    String user = User.getJefe( (String)request.getParameter("jefe-proyecto"));
                    int idProyecto = Integer.parseInt(request.getParameter("idProyecto"));
                    Proyecto p = Proyecto.getProject(idProyecto);
                    if(!comprobarFechas(request)){
                        sesion.setAttribute("mensaje","La fecha es posterior a la fecha actual");
                    } else {
                        Proyecto.actualizarProyecto(getProyectoFromParameters(request, idProyecto, p.getLogin(),p.getNumP()));
                    }
                    if(!TablaRoles.exist(idProyecto)){
                    
                    for(int i =0; i<p.getNumP();i++){
                        String desarrollador="desarrollador"+String.valueOf(i);
                         String categoria= request.getParameter("categoria"+i);
                        TablaRoles.insert(idProyecto,desarrollador,categoria);
                    }
                    }
                    url = getTodosProyectos(sesion);
                } else if(accion.equals("finalizar")){
                    int idProyecto = Integer.parseInt(request.getParameter("idProyecto"));
                    Proyecto p = Proyecto.getProject(idProyecto);
                    Proyecto tmp = new Proyecto(p.getNombre(),p.getFechaInicio(),p.getFechaFin(),'C',p.getLogin(),p.getNumP());
                   if(comprobarFinalizar(idProyecto)){
                    Proyecto.actualizarProyecto(tmp);
                   } else {
                       sesion.setAttribute("mensaje", "No puede finalizarlo porque no están cerradas todas las fases.");
                   }
                    url="/vistaProyectos.jsp";
                }
                RequestDispatcher respuesta = getServletContext().getRequestDispatcher(url);
                respuesta.forward(request, response);
            }
        
    }

    /**
     * Obtener nuevo proyecto
     * @param request
     * @param idProyecto
     * @param user
     * @param numP
     * @return 
     */
     private Proyecto getProyectoFromParameters(HttpServletRequest request, int idProyecto, String user, int numP) {
        String nombre = request.getParameter("nombreProyecto");
        System.err.println(nombre);
        String fechaInicioyFin = request.getParameter("fechaInicioyFin");
        String fechaInicio = "";
        boolean encontrado = false;
        int i = 0;
        while (i < fechaInicioyFin.length() && !encontrado) {
            if (fechaInicioyFin.charAt(i) != ' ') {
                fechaInicio += fechaInicioyFin.charAt(i);
            } else {
                encontrado = true;
            }
            i++;
        }
        String fechaFin = fechaInicioyFin.substring(i + 2);

        char estado = request.getParameter("estado").charAt(0);
        if (idProyecto == 0) {
            return new Proyecto(nombre, fechaInicio, fechaFin, estado, user,numP);
        } else {
            return new Proyecto(idProyecto, nombre, fechaInicio, fechaFin, estado, user,numP);
        }
    }
     
     private boolean comprobarFechas(HttpServletRequest request){
         String fechaInicioyFin = request.getParameter("fechaInicioyFin");
        String fechaInicio = "";
        boolean encontrado = false;
        int i = 0;
        while (i < fechaInicioyFin.length() && !encontrado) {
            if (fechaInicioyFin.charAt(i) != ' ') {
                fechaInicio += fechaInicioyFin.charAt(i);
            } else {
                encontrado = true;
            }
            i++;
        }
        
        Date f1 = new Date(fechaInicio);
        Date actual = new Date();
        return !f1.before(actual);
     }

     private boolean comprobarFinalizar(int idProyecto){
        ArrayList<Fase> act = Fase.getFase(idProyecto);
        for(Fase act1 : act){
            if(act1.getEstado()!='C'){
                return false; 
            }
        }
        
        return true;
    }
     
     /**
      * Obtener proyectos de los que un usuario es jefe
      * @param user
      * @param sesion
      * @return 
      */
    private String getProyectos(String user, HttpSession sesion) {
        ArrayList<Proyecto> proyectos = Proyecto.getProyectos(user);
        sesion.setAttribute("user", user);
        sesion.setAttribute("proyectos", proyectos);
        return "/vistaProyectos.jsp";
    }
    
    /**
     * Obtener todos los proyectos
     * @param sesion
     * @return 
     */
    private String getTodosProyectos(HttpSession sesion){
         ArrayList<Proyecto> proyectos = Proyecto.getTodosProyectos();
        sesion.setAttribute("proyectos", proyectos);
        return "/vistaProyectos.jsp";
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
