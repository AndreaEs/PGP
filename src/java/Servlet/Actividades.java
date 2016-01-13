/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Business.Actividad;
import Business.Fase;
import Data.ActividadBD;
import Data.PredecesorasDB;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
@WebServlet(name = "Actividades", urlPatterns = {"/Actividades"})
public class Actividades extends HttpServlet {

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
        int idFase = Integer.parseInt(request.getParameter("idFase"));
        String user = (String) sesion.getAttribute("user");
        String tipo = (String) sesion.getAttribute("tipo");
        sesion.setAttribute("idFase", idFase);
        String url = null;
        if (idFase != 0) {
            String accion = request.getParameter("actividad");
            if (accion != null) {
                if (accion.equals("crearActividad")) {
                    if(!comprobarFechas(request)){
                         sesion.setAttribute("mensaje","Las fechas no se encuentran dentro de la indicada");
                    } else {
                        Actividad.guardarNuevaActividad(getActividadFromParameters(request, 0, idFase, user, tipo));
                    }
                    int id = ActividadBD.obtenerUltimaEntrada();
                    String fechaIAct = ActividadBD.obtenerFechaInicio(id);
                    ArrayList<Actividad> predecesoras = new ArrayList<Actividad>();
                    predecesoras = (ArrayList<Actividad>) sesion.getAttribute("predecesoras");
                    for(int i=0;i<predecesoras.size();i++)
                        if(comprobarFechas(fechaIAct,predecesoras.get(i).getFechaFin()))
                            PredecesorasDB.crearPredecesoras(id, predecesoras.get(i).getIdentificador());
                    url = getActividades(idFase, sesion, user);
                } else if (accion.equals("verActividades")) {
                    url = getActividades(idFase, sesion, user);
                } else if (accion.equals("crearNuevaActividad")) {
                    //Obtener actividades del proyecto al que pertenece la fase
                    ArrayList<Actividad> act = new ArrayList<Actividad>();
                    act = ActividadBD.selectActividadesProyecto(idFase);
                    sesion.setAttribute("predecesoras", act);
                    sesion.setAttribute("actualizar", false);
                    sesion.setAttribute("user", user);
                    url = "/actividad.jsp";
                } else if (accion.equals("actualizarUnaActividad")) {
                    int idActividad = Integer.parseInt(request.getParameter("idActividad"));
                    sesion.setAttribute("actualizar", true);
                    Actividad a = Actividad.getActivity(idActividad);
                    sesion.setAttribute("actividad", a);
                    sesion.setAttribute("user", user);
                    url = "/actividad.jsp";
                } else if (accion.equals("actualizarActividad")) {
                    int idActividad = Integer.parseInt(request.getParameter("idActividad"));
                    Actividad a = Actividad.getActivity(idActividad);
                    if(sesion.getAttribute("tipo").equals("D")){
                        Actividad.actualizarActividad(getActividadFromParameters(request, idActividad, idFase, user, tipo));
                    } else {
                        if(a.getEstado()!='C'){
                            if(!comprobarFechas(request)){
                                 sesion.setAttribute("mensaje","Las fechas no se encuentran dentro de la indicada, inténtelo de nuevo");
                            } else {
                                Actividad.actualizarActividad(getActividadFromParameters(request, idActividad, idFase, user, tipo));
                            }
                        } else {
                            sesion.setAttribute("mensaje", "No se puede actualizar porque la actividad está cerrada.");
                        }
                    }
                    url = getActividades(idFase, sesion, user);
                } else if(accion.equals("finalizar")){
                    int idActividad = Integer.parseInt(request.getParameter("idActividad"));
                    Actividad a = Actividad.getActivity(idActividad);
                    a.setEstado('C');
                    Actividad.actualizarActividad(a);
                    url="/vistaActividades.jsp";
                }
                
                RequestDispatcher respuesta = getServletContext().getRequestDispatcher(url);
                respuesta.forward(request, response);
            }
        } else {
            String accion = request.getParameter("actividad");
            if (accion.equals("verActividadesDeUsuario")) {
                ArrayList<Actividad> actividades = Actividad.getActividadesLoginOrdenadas(user);
                ArrayList<Actividad> tmp = new ArrayList<Actividad>();
                for (Actividad actividad : actividades) {
                    if(actividad.getEstado()!='C'){
                        tmp.add(actividad);
                    }
                }
                sesion.setAttribute("actividades", tmp);
                sesion.setAttribute("user", user);
                url = "/vistaActividades.jsp";
            }
            RequestDispatcher respuesta = getServletContext().getRequestDispatcher(url);
            respuesta.forward(request, response);
        }

    }

    /**
     * 
     * @param request
     * @param idActividad
     * @param idFase
     * @param user
     * @param tipo
     * @return 
     */
    private Actividad getActividadFromParameters(HttpServletRequest request, int idActividad, int idFase, String user, String tipo) {
        if (!tipo.equals("D")) {
            String descripcion = request.getParameter("descripcion");
            String rol = request.getParameter("rol");

            int duracionEstimada = Integer.parseInt(request.getParameter("duracionEstimada"));
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

            int duracionReal = 0;
            if (!request.getParameter("duracionReal").equals("") && request.getParameter("duracionReal") != null) {
                duracionReal = Integer.parseInt(request.getParameter("duracionReal"));
            }

            if (idActividad == 0) {
                return new Actividad(user, descripcion, rol, duracionEstimada, fechaInicio, fechaFin, duracionReal, estado, idFase);
            } else {
                return new Actividad(idActividad, user, descripcion, rol, duracionEstimada, fechaInicio, fechaFin, duracionReal, estado, idFase);
            }
        } else {
            Actividad a = Actividad.getActivity(idActividad);
            char estado = request.getParameter("estado").charAt(0);
            a.setEstado(estado);
            return a;
        }

    }

    /**
     * 
     * @param idFase
     * @param sesion
     * @param user
     * @return 
     */
    private String getActividades(int idFase, HttpSession sesion, String user) {
        ArrayList<Actividad> actividades = Actividad.getFase(idFase);
        sesion.setAttribute("idFase", idFase);
        sesion.setAttribute("actividades", actividades);
        sesion.setAttribute("user", user);
        return "/vistaActividades.jsp";
    }
    
    /*date1.comparetp(date2) > 0 --> date1 esta después de date2
     date1.comparetp(date2) < 0 --> date1 esta antes de date2*/
    /**
     * 
     * @param fechaInicio
     * @param fechaFin
     * @return 
     */
    private boolean comprobarFechas(String fechaInicio, String fechaFin) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            java.util.Date fechaI = formatter.parse(fechaInicio);
            java.util.Date fechaF = formatter.parse(fechaFin);
            if (fechaI.compareTo(fechaF)==0)
                return true;
            if (fechaI.compareTo(fechaF)>0)
                return true;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    private boolean comprobarFechas(HttpServletRequest request){
        String fechaInicioyFin = request.getParameter("fechaInicioyFin");
        System.err.println(fechaInicioyFin);
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
        Date f1 = new Date(fechaInicio);
        Date f2 = new Date(fechaFin);
        Fase p = Fase.getPhase(Integer.parseInt(request.getParameter("idFase")));
        Date p1 = new Date(p.getFechaInicio());
        Date p2 = new Date(p.getFechaFin());
        return !(f1.before(p1) || f2.after(p2));
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
