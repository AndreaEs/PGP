package Servlet;

import Business.Participante;
import Data.ActividadBD;
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
@WebServlet(name = "Participantes", urlPatterns = {"/Participantes"})
public class Participantes extends HttpServlet {

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
        String url=null;
        if(usuario!=null){
            String accion = request.getParameter("accion");
            if(accion!=null){
                if(accion.equals("crearParticipacion")){
                    Participante.insertar(getParticipanteFromParameter(request));
                    url=getParticipante(sesion,Integer.parseInt(request.getParameter("idActividad")));
                }
                RequestDispatcher respuesta = getServletContext().getRequestDispatcher(url);
                respuesta.forward(request, response);
            }
        }
    }

    private Participante getParticipanteFromParameter(HttpServletRequest request){
        int num = Integer.parseInt(request.getParameter("boton"));
        int idActividad= Integer.parseInt(request.getParameter("idActividad"));
        System.err.println(idActividad);
        String login = request.getParameter("login"+num);
        System.err.println(login);
        double porcentaje=0;
        if(request.getParameter("porcentaje"+num).equals("")){
            porcentaje=100;
        } else {
            porcentaje = Double.parseDouble(request.getParameter("porcentaje"+num));
        }
        System.err.println(porcentaje);
        String rol = ActividadBD.selectActividad(idActividad).getRolNecesario();
        System.err.println(rol);
        String idParticipante = request.getParameter("idParticipante"+num);
        return new Participante(idActividad, login, porcentaje, rol,idParticipante);
    }
    
    private String getParticipante(HttpSession sesion, int idActividad){
        ArrayList<Participante> participaciones = Participante.getParticipantes(idActividad);
        sesion.setAttribute("idActividad", idActividad);
        sesion.setAttribute("participaciones", participaciones);
        return "/vistaParticipantes.jsp";
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

