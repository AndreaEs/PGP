package Servlet;

import Business.Actividad;
import Business.Participante;
import Business.Proyecto;
import Business.Vacaciones;
import Data.ActividadBD;
import Data.ParticipantesBD;
import Data.VacacionesDB;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author grupos06
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
        int idActividad = Integer.parseInt(request.getParameter("idActividad"));
        double porcentaje = Double.parseDouble(request.getParameter("porcentaje"));
        String url = null;
        if (usuario != null) {
            String accion = request.getParameter("accion");
            if (accion != null) {
                if (accion.equals("crearParticipacion")) {
                    if (!comprobarLogin(usuario, idActividad)) {
                        sesion.setAttribute("mensaje", "El usuario ya tiene el maximo de proyecto asignado");
                    } else if (!comprobarPorcentaje(usuario, porcentaje)) {
                        sesion.setAttribute("mensaje", "El porcentaje añadido debe ser menor.");
                    } else if (!comprobarVacaciones(usuario, idActividad)) {
                        sesion.setAttribute("mensaje", "El usuario se encuentra de vacaciones durante la actividad");
                    } else {
                        Participante.insertar(getParticipanteFromParameter(request));
                    }
                    url = getParticipante(sesion, Integer.parseInt(request.getParameter("idActividad")));
                }
                RequestDispatcher respuesta = getServletContext().getRequestDispatcher(url);
                respuesta.forward(request, response);
            }
        }
    }

    /**
     * 
     * @param request
     * @return 
     */
    private Participante getParticipanteFromParameter(HttpServletRequest request) {
        int idActividad = Integer.parseInt(request.getParameter("idActividad"));
        System.err.println(idActividad);
        String login = request.getParameter("login");
        System.err.println(login);
        double porcentaje = 0;
        if (request.getParameter("porcentaje").equals("")) {
            porcentaje = 100;
        } else {
            porcentaje = Double.parseDouble(request.getParameter("porcentaje"));
        }
        System.err.println(porcentaje);
        String rol = ActividadBD.selectActividad(idActividad).getRolNecesario();
        System.err.println(rol);
        String idParticipante = request.getParameter("idParticipante");
        return new Participante(idActividad, login, porcentaje, rol, idParticipante);
    }

    /**
     * 
     * @param sesion
     * @param idActividad
     * @return 
     */
    private String getParticipante(HttpSession sesion, int idActividad) {
        ArrayList<Participante> participaciones = Participante.getParticipantes(idActividad);
        sesion.setAttribute("idActividad", idActividad);
        sesion.setAttribute("participaciones", participaciones);
        return "/vistaParticipantes.jsp";
    }

    /**
     * Comprobar si un usuario a superado el porcentaje de participacion en un proyecto 
     * al que fue asignado
     * @param login
     * @param porcentaje
     * @return 
     */
    private boolean comprobarPorcentaje(String login, double porcentaje) {
        if ((ParticipantesBD.getPorcentaje(login) + porcentaje) < 100) {
            return true;
        }
        return false;
    }

    /**
     * 
     * @param login
     * @param idActividad
     * @return 
     */
    private boolean comprobarLogin(String login, int idActividad) {
        ArrayList<Proyecto> proy = Proyecto.getProyectos(login);
        if (ParticipantesBD.exist(login) && proy.size() > 0) {
            return false;
        }
        if (ParticipantesBD.getParticipaciones(login).size() > 2) {
            return false;
        }
        return comprobarVacaciones(login, idActividad);
    }

    /**
     * Comprobar si un usuario tiene asignadas vacaciones en la fecha de la
     * actividad a asignar
     *
     * @param login
     * @param idActividad
     * @return
     */
    private boolean comprobarVacaciones(String login, int idActividad) {
        Actividad a = Actividad.getActivity(idActividad);
        List<Vacaciones> tmp = VacacionesDB.obtenerVacaciones(login);
        for (Vacaciones tmp1 : tmp) {
            if (tmp1.comprobarRangosEntreFechas(a.getFechaInicio(), a.getFechaFin(), tmp1)) {
                return false;
            }
        }
        return true;
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
