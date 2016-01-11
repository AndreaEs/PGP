/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Business.Actividad;
import Data.ActividadBD;
import Data.PredecesorasDB;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
        
        HttpSession sesion = request.getSession();
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
        String login = (String)sesion.getAttribute("user");
        String fechaI = fechaInicio.substring(6)+"-"+fechaInicio.substring(0,2)+"-"+fechaInicio.substring(3,5);
        String fechaF = fechaFin.substring(6)+"-"+fechaFin.substring(0,2)+"-"+fechaFin.substring(3,5);
        String url = null;
        
        
        //Comprobar que la fecha de Inicio no es posterior a hoy
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date fechaIU = formatter.parse(fechaI);
        Date hoy = Calendar.getInstance().getTime();
        if(fechaIU.compareTo(hoy)>0){
            System.out.println("No se pueden pedir informes posteriores a la fecha actual");
        }         
        
        url = getInforme(sesion,login,fechaI,fechaF);
        
        RequestDispatcher respuesta = getServletContext().getRequestDispatcher(url);
        respuesta.forward(request, response);
        
    }
    
    private String getInforme(HttpSession sesion, String login, String fechaI, String fechaF) throws ParseException{
        
        //Obtener actividades del usuario login entre las fechas indicadas
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
                    List<Integer> predecesoras = new ArrayList<Integer>();
                    predecesoras = PredecesorasDB.obtenerPredecesoras(actividades.get(i).getIdentificador());
                    actividades.get(i).setPredecesoras(predecesoras);
                    inf.get(j).add(actividades.get(i));
                }
        }
        
        //sesion.setAttribute("login", login);
        sesion.setAttribute("user", login);
        sesion.setAttribute("fechaI", fechaI);
        sesion.setAttribute("fechaF", fechaF);
        sesion.setAttribute("informe", inf);
        
        return "/vistaInformeD.jsp";
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
