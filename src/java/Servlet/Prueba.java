/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Business.Actividad;
import Business.CalendarDTO;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author andreaescribano
 */
@WebServlet(name = "Prueba", urlPatterns = {"/Prueba"})
public class Prueba extends HttpServlet {

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
        String accion = request.getParameter("accion");
        if (accion.equals("mostrarEventos")) {
            ArrayList<Actividad> listaActividades = Actividad.getActividades();
            List l = new ArrayList();

            for (Actividad a : listaActividades) {
                CalendarDTO c = new CalendarDTO();
                c.setId(a.getIdentificador());
                c.setStart(a.getFechaInicio());
                c.setEnd(a.getFechaFin());
                c.setTitle(a.getDescripcion());
                if (a.getEstado() == 'A') {
                    c.setColor("#00FF00");
                } else if (a.getEstado() == 'R') {
                    c.setColor("#FE2E2E");
                } else if (a.getEstado() == 'P') {
                    c.setColor("#FFFF00");
                }
                c.setTextColor("#000000");
                l.add(c);
            }

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.write(new Gson().toJson(l));
        }
        /* if (accion.equals("crearEvento")) {
         String titulo = request.getParameter("title");
         String tipoEvento = request.getParameter("tipoEvento");
         String tipoTarea = request.getParameter("tipoTarea");
         int duracion = Integer.parseInt(request.getParameter("duracion"));
         String eventoEmpieza = request.getParameter("empieza");
         String eventoAcaba = request.getParameter("acaba");
         System.out.println("titulo: "+ titulo + ", tipoEvento: " + tipoEvento + ", tipoTarea:" + tipoTarea + ", duracion: "+duracion + ", empieza: "+eventoEmpieza +", acaba: "+eventoAcaba);
         }else if (accion.equals("actualizarEvento")){
         String titulo = request.getParameter("title");
         String tipoEvento = request.getParameter("tipoEvento");
         String tipoTarea = request.getParameter("tipoTarea");
         int duracion = Integer.parseInt(request.getParameter("duracion"));
         String eventoEmpieza = request.getParameter("empieza");
         String eventoAcaba = request.getParameter("acaba");
         System.out.println("titulo: "+ titulo + ", tipoEvento: " + tipoEvento + ", tipoTarea:" + tipoTarea + ", duracion: "+duracion + ", empieza: "+eventoEmpieza +", acaba: "+eventoAcaba);
         }*/

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
