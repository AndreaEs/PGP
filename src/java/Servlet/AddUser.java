/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Business.User;
import Data.UserDB;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "AddUser", urlPatterns = {"/AddUser"})
public class AddUser extends HttpServlet {

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
        String url = "";
        String login , nif,pass,rol,informacion;
        HttpSession sesion = request.getSession();

        if (request.getParameter("Accion").equals("anadir")) {
            System.out.println("Llegamosal post de adduser");
             login = request.getParameter("login");
            System.out.println(login);
            nif = request.getParameter("nif");
            System.out.println(nif);
            pass = request.getParameter("pass");
            System.out.println(pass);
            rol = request.getParameter("rol");
            System.out.println(rol);
            informacion = request.getParameter("informacion");
            System.out.println(informacion);

            User user = new User(login, pass, rol.charAt(0), nif, informacion);

            if (UserDB.exist(login)) {
                request.setAttribute("msg", "El usuario ya existe");
                url = "/add.jsp";
            } else {
                UserDB.insert(user);
                url = "/usuarios.jsp"; //cambiar por donde tenga q ir
            }
        } else if (request.getParameter("Accion").equals("eliminar")) {
            
            User usuario = (User) sesion.getAttribute("usuario");
            UserDB.delete(usuario);
            url = "/usuarios.jsp";

        }else if (request.getParameter("Accion").equals("modificar")){
            System.out.println("Llegamos al post de adduser para modificar");
             login = request.getParameter("login");
            System.out.println(login);
            nif = request.getParameter("nif");
            System.out.println(nif);
            pass = request.getParameter("pass");
            System.out.println(pass);
            rol = request.getParameter("rol");
            System.out.println(rol);
            informacion = request.getParameter("informacion");
            System.out.println(informacion);
            User usuario = new User(login,pass,rol.charAt(0),nif,informacion);
            UserDB.update(usuario);
            url ="/usuarios.jsp";
        } else if (request.getParameter("Accion").equals("actualizaUnUsuario")){
            login = request.getParameter("login");
            User u = UserDB.getUsuario(login);
            sesion.setAttribute("usuario", u);
            url="/vistaUsuario.jsp";
        }else{
            url="/usuarios.jsp";
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
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
