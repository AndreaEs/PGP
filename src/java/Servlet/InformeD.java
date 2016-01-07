/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Business.Actividad;
import Data.ActividadBD;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
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
@WebServlet(name = "InformeD", urlPatterns = {"/InformeD"})
public class InformeD extends HttpServlet {
    
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

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
        
        String login = request.getParameter("login");
        String fechaI = request.getParameter("fechaI");
        String fechaF = request.getParameter("fechaF");
        
        //Obtener actividades del usuario login entre las fechas indicada
        ArrayList<Actividad> actividades = new ArrayList<Actividad>();
        actividades = ActividadBD.selectActividadesInforme(login, fechaI, fechaF);
        //Ordenar por semanas
        HashMap<Integer,ArrayList<Actividad>> inf = new HashMap<Integer,ArrayList<Actividad>>();
        //1º. Obtener cuantas semanas abarca la actividad
        for(int i=0;i<actividades.size();i++){
                //Pasar String a Calendar
                Calendar fechaIA = Calendar.getInstance();
                fechaIA.setTime(formatter.parse(actividades.get(i).getFechaInicio()));
                Calendar fechaFA = Calendar.getInstance();
                fechaFA.setTime(formatter.parse(actividades.get(i).getFechaFin()));
                //Comprobar semanas de esas fechas
                int semanaI = fechaIA.get(Calendar.WEEK_OF_YEAR);
                int semanaF = fechaFA.get(Calendar.WEEK_OF_YEAR);
                for(int j=semanaI;j<=semanaF;j++){
                    if (inf.get(j) == null) 
                        inf.put(j, new ArrayList<Actividad>());
                    inf.get(j).add(actividades.get(i));
                }
        }
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet InformeD</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet InformeD at " + request.getContextPath() + "</h1>");
            Iterator it = inf.entrySet().iterator();
            while(it.hasNext()){
                Entry par = (Entry)it.next();
                int valor = (int) par.getKey();
                out.println("<h3> Semana: "+valor+"</h3>");
                out.println("<h3> Actividades </h3>");
                if(!inf.get(valor).isEmpty())
                    for(int i=0;i<inf.get(valor).size();i++){
                        out.println("<h4> Descripción: "+inf.get(valor).get(i).getDescripcion()+"</h4>");
                        out.println("<h4> Fecha Inicio: "+inf.get(valor).get(i).getFechaInicio()+"</h4>");
                        out.println("<h4> Fecha Fin: "+inf.get(valor).get(i).getFechaFin()+"</h4>");
                        out.println("<h4> Estado: "+inf.get(valor).get(i).getEstado()+"</h4>");
                        out.println("<h4> Fase: "+inf.get(valor).get(i).getIdFase()+"</h4>");
                    }
            }
            out.println("</body>");
            out.println("</html>");
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
            Logger.getLogger(InformeD.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(InformeD.class.getName()).log(Level.SEVERE, null, ex);
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
