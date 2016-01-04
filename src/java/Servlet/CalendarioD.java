/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Business.Actividad;
import Business.Calendario;
import Business.TareaPersonal;
import Data.CalendarioDB;
import Data.ActividadBD;
import Data.TareaDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author gil
 */
@WebServlet(name = "CalendarioD", urlPatterns = {"/CalendarioD"})
public class CalendarioD extends HttpServlet {

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
            throws ServletException, IOException, ParseException {
        response.setContentType("text/html;charset=UTF-8");

        String mensaje = "";
        String login = request.getParameter("login");
        String tipo = request.getParameter("tipo");
        String tipoT = request.getParameter("tipoT");
        String duracion = request.getParameter("duracion");
        String fechaI = request.getParameter("fechaI");
        String fechaF = request.getParameter("fechaF");

        if (tipo.equals("V")) {

            Calendario c = new Calendario();
            c.setUsuario(login);
            c.setTipo(tipo);
            c.setTipoT(tipoT);
            c.setFechaInicio(fechaI);
            c.setFechaFin(fechaF);
            
            //Comprobaciones de un evento tipo Vacaciones
            //Primero obtener de la base de datos las fechas de vacaciones del usuario
            List<Calendario> vacaciones = new ArrayList<Calendario>();
            vacaciones = CalendarioDB.obtenerVacaciones(login);

            //Comprobar cuantos días lleva de vacaciones
            long dias = c.comprobarDiasVacaciones(vacaciones);

            //Días de vacaciones que ha solicitado
            List<Calendario> nuevo = new ArrayList<Calendario>();
            nuevo.add(c);
            long nuevos = c.comprobarDiasVacaciones(nuevo);
            if (nuevos > 14) {
                mensaje = "No puedes asignar más de dos semanas seguidas de vacaciones";
            } else if ((dias + nuevos) > 28) {
                mensaje = "No puedes asignar " + nuevos + " días de vacaciones. Te quedan: " + (28 - dias);
            }

            //Comprobar que en esas fechas no tenga ya una actividad asignada
            List<Actividad> actividades = new ArrayList<Actividad>();
            actividades = ActividadBD.selectActividades(login);
            for (int i = 0; i < actividades.size(); i++) {
                if (c.comprobarRangosEntreFechas(actividades.get(i).getFechaInicio(), actividades.get(i).getFechaFin(), c)) {
                    mensaje = "No puedes asignar vacaciones en ese día porque ya tienes actividades asignadas en esas fechas";
                }
            }

            //Comprobar que en esas fechas no tenga ya vacaciones asignadas
            for (int i = 0; i < vacaciones.size(); i++) {
                if (c.comprobarRangosEntreFechas(vacaciones.get(i).getFechaInicio(), vacaciones.get(i).getFechaFin(), c)) {
                    mensaje = "No puedes asignar vacaciones en ese día porque ya tienes vacaciones asignadas en esas fechas";
                }
            }
            
            //Si ha superado todos los pasos --> puede asignar vacaciones en las fechas que introdujo
            if (mensaje.equals("")) {
                mensaje = "¡Todo correcto! Asignados el rango de fechas " + fechaI + " --> " + fechaF + " como días de vacaciones";
                CalendarioDB.insertVacaciones(c);
            }

            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet Calendario</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Servlet Calendario at " + request.getContextPath() + "</h1>");
                out.println("<h4>Login: " + login + " </h4>");
                out.println("<h4>Tipo de evento: " + tipo + " </h4>");
                out.println("<h4>Fecha inicio: " + fechaI + " </h4>");
                out.println("<h4>Fecha fin: " + fechaF + " </h4>");
                out.println("<h3>Días que llevas asignados de vacaciones " + dias + "</h3>");
                out.println("<h3>Días nuevos que quieres asignar de vacaciones " + nuevos + "</h3>");
                out.println("<h2>" + mensaje + "</h2>");
                out.println("</body>");
                out.println("</html>");
            }

        } else {
            
            TareaPersonal tp = new TareaPersonal();
            tp.setLogin(login);
            tp.setTipo(tipoT);
            tp.setFecha(fechaI);
            tp.setDuracion(Integer.parseInt(duracion));
            
            //Comprobaciones de un evento tipo Tarea Personal
  
            //Comprobar que no quiere asignar una tarea en fin de semana
            if(!tp.tareaFinSemana(fechaI))
                mensaje="No puedes asignar una tarea en fin de semana";
            
            //Obtener actividades de ese usuario
            List<Actividad> actividades = new ArrayList<Actividad>();
            actividades = ActividadBD.selectActividades(login);
            
            //Comprobar que ese usuario tiene menos de 25 tareas asignadas para esa semana
            if(!TareaDB.tareasPersonalesSemana(login, fechaI))
                mensaje="El usuario "+login+" ya tiene asignadas 24 tareas personales para la semana del "+fechaI;
            
            //Comprobar que en esa fecha hay una actividad
            for(int i=0;i<actividades.size();i++){
                Actividad a = actividades.get(i);
                if(a.comprobarFechaEntreFechas(fechaI, a)){
                    mensaje="Hay una actividad en esa fecha";
                    //Asignar actividad
                    tp.setActividad(a.getIdentificador());
                }
            }
            
            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet Calendario</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Servlet Calendario at " + request.getContextPath() + "</h1>");
                out.println("<h4>Login: " + login + " </h4>");
                out.println("<h4>Tipo de evento: " + tipoT + " </h4>");
                out.println("<h4>Fecha: " + fechaI + " </h4>");
                out.println("<h4>Duración: " + duracion + " </h4>");
                out.println("<h2>" + mensaje + "</h2>");
                out.println("</body>");
                out.println("</html>");
            }
        }

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
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(CalendarioD.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(CalendarioD.class.getName()).log(Level.SEVERE, null, ex);
        }
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
