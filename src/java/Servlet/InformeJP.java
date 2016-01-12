/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Business.Actividad;
import Business.User;
import Data.ActividadBD;
import Data.ProyectoDB;
import Data.UserDB;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
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
@WebServlet(name = "InformeJP", urlPatterns = {"/InformeJP"})
public class InformeJP extends HttpServlet {

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
        String login = (String) sesion.getAttribute("user");
        String tipoI = request.getParameter("tipoI");
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
        String fechaI = fechaInicio.substring(6) + "-" + fechaInicio.substring(0, 2) + "-" + fechaInicio.substring(3, 5);
        String fechaF = fechaFin.substring(6) + "-" + fechaFin.substring(0, 2) + "-" + fechaFin.substring(3, 5);
        //String fechaI = request.getParameter("fechaI");
        //String fechaF = request.getParameter("fechaF");
        String url = null;

        //if(tipoI.equals("EPA") || tipoI.equals("APA"))
        //throw new UnsupportedOperationException("Not supported yet.");
        /*Relación de trabajadores y sus actividades asignadas 
         durante un periodo (semanal)determinado.*/
        if (tipoI.equals("TA")) {
            sesion.setAttribute("fechaI", fechaI);
            sesion.setAttribute("fechaF", fechaF);
            sesion.setAttribute("login", login);
            sesion.setAttribute("tipoI", tipoI);
            url = getInformeTYA(sesion, fechaI, fechaF, login);
            /*Relación de actividades activas o finalizadas en una fecha concreta o en un periodo
             de tiempo, viendo el tiempo planificado y el tiempo realizado de cada una de ellas.*/
        } else if (tipoI.equals("AAF")) {
            if (fechaF.equals("")) {
                fechaF = fechaI;
            }
            sesion.setAttribute("fechaI", fechaI);
            sesion.setAttribute("fechaF", fechaF);
            sesion.setAttribute("login", login);
            sesion.setAttribute("tipoI", tipoI);
            url = getInformeAAF(sesion, fechaI, fechaF, login);
            /*Actividades a realizar, así como los recursos asignados, durante un periodo de
             tiempo determinado posterior a la fecha actual.*/
        } else if (tipoI.equals("AAR")) {
            sesion.setAttribute("fechaI", fechaI);
            sesion.setAttribute("fechaF", fechaF);
            sesion.setAttribute("login", login);
            sesion.setAttribute("tipoI", tipoI);
            url = getInformeAAR(sesion, fechaI, fechaF, login);
        } else if (tipoI.equals("CA")) {
            sesion.setAttribute("fechaI", fechaI);
            sesion.setAttribute("fechaF", fechaF);
            sesion.setAttribute("login", login);
            sesion.setAttribute("tipoI", tipoI);
            url = getInformeCA(sesion, fechaI, fechaF, login);
            System.out.println("vamos a salir");
        } else if (tipoI.equals("AF")) {
            sesion.setAttribute("fechaI", fechaI);
            sesion.setAttribute("fechaF", fechaF);
            sesion.setAttribute("login", login);
            sesion.setAttribute("tipoI", tipoI);
            url = getInformeAF(sesion, fechaI, fechaF, login);
        } else if (tipoI.equals("PC")) {
            sesion.setAttribute("fechaI", fechaI);
            sesion.setAttribute("fechaF", fechaF);
            sesion.setAttribute("login", login);
            sesion.setAttribute("tipoI", tipoI);
            url = getInformePC(sesion, fechaI, fechaF, login);
        }

        RequestDispatcher respuesta = getServletContext().getRequestDispatcher(url);
        respuesta.forward(request, response);

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
            Logger.getLogger(InformeJP.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(InformeJP.class.getName()).log(Level.SEVERE, null, ex);
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

    private String getInformeTYA(HttpSession sesion, String fechaI, String fechaF, String login) throws ParseException {
        //Obtener relación de actividades y usuarios entre esas fechas
        //HashMap<User,ArrayList<Actividad>> inf = new HashMap<User,ArrayList<Actividad>>();
        HashMap<String, ArrayList<Actividad>> inf = new HashMap<String, ArrayList<Actividad>>();
        User u = UserDB.getUsuario(login);
        inf = ActividadBD.selectInformeTYA(fechaI, fechaF, u.getNif());
        sesion.setAttribute("informe", inf);
        return "/vistaInformeJP.jsp";
    }

    private String getInformeAAF(HttpSession sesion, String fechaI, String fechaF, String login) throws ParseException {
        ArrayList<Actividad> inf = new ArrayList<Actividad>();
        User u = UserDB.getUsuario(login);
        inf = ActividadBD.selectInformeAFF(fechaI, fechaF, u.getNif());
        sesion.setAttribute("informe", inf);
        return "/vistaInformeJP.jsp";
    }

    private String getInformeAAR(HttpSession sesion, String fechaI, String fechaF, String login) throws ParseException {
        ArrayList<Actividad> inf = new ArrayList<Actividad>();
        User u = UserDB.getUsuario(login);
        inf = ActividadBD.selectInformeAAR(fechaI, fechaF, u.getNif());
        if (inf != null) {
            sesion.setAttribute("informe", inf);
        }
        return "/vistaInformeJP.jsp";
    }

    private String getInformeCA(HttpSession sesion, String fechaI, String fechaF, String login) throws ParseException {
        ArrayList<Actividad> inf = new ArrayList<Actividad>();
        User u = UserDB.getUsuario(login);
        inf = ActividadBD.selectInformeCA(fechaI, fechaF, u.getNif());
        if (inf != null) {
            System.out.println("Entramos en el if");
            sesion.setAttribute("informe", inf);
        }
        return "/vistaInformeJP.jsp";
    }

    private String getInformeAF(HttpSession sesion, String fechaI, String fechaF, String login) throws ParseException {
        HashMap<String, ArrayList<Actividad>> inf = new HashMap<String, ArrayList<Actividad>>();
        User u = UserDB.getUsuario(login);
        inf = ActividadBD.selectInformeAF(fechaI, fechaF, u.getNif());
        if (inf != null) {
            sesion.setAttribute("informe", inf);
        }
        return "/vistaInformeJP.jsp";
    }

    private String getInformePC(HttpSession sesion, String fechaI, String fechaF, String login) {
        HashMap<String, HashMap<String, ArrayList<Actividad>>> inf
                = new HashMap<String, HashMap<String, ArrayList<Actividad>>>();

        inf = ProyectoDB.selectInformePC(fechaI, fechaF);
        if (inf != null) {
            sesion.setAttribute("informe", inf);
        }
        System.out.println("hola llegamos hasta aqui");

        return "/vistaInformeJP.jsp";
    }
}
