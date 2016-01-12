/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Business.Proyecto;
import Business.TareaPersonal;
import Business.User;
import Data.TareaDB;
import Data.UserDB;
import java.io.IOException;
import java.io.PrintWriter;
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
 * @author grupo06
 */
@WebServlet(name = "Acceder", urlPatterns = {"/Acceder"})
public class Acceder extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Acceder</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Acceder at " + request.getContextPath() + "</h1>");
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
        
        String user = request.getParameter("user");
        String password = request.getParameter("pass");
        String rol = request.getParameter("tipo");
        HttpSession sesion = request.getSession();
        
        String url="", msg=" ";
        
        if(UserDB.identificar(user, password, rol)){
            msg=" ";
            sesion.setAttribute("user", user);
            sesion.setAttribute("pass", password);
            sesion.setAttribute("tipo", rol);
            
            if(rol.equals("D")){
                ArrayList<TareaPersonal> tareas = TareaDB.findAll(user);
                sesion.setAttribute("tareas", tareas);
                url="/informesD.jsp";
            } else {
                if(rol.equals("J")){
                    ArrayList<Proyecto> proyectos = Proyecto.getProyectos(user);
                    sesion.setAttribute("proyectos", proyectos);
                    url="/informesJP.jsp";
                } else {
                    if(rol.equals("A")){
                        ArrayList<User> usuarios = UserDB.findAll();
                        sesion.setAttribute("usuarios", usuarios);
                        url="/usuarios.jsp";
                    }
                }
            }
        }else{
            
            msg = "El usuario, el rol y/o password no son correctos";
            url= "/index.jsp";
        }
        
        sesion.setAttribute("msg", msg);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
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
