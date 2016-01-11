/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Business.Actividad;
import Business.CalendarDTO;
import Business.Proyecto;
import Business.Vacaciones;
import Business.TareaPersonal;
import Data.VacacionesDB;
import Data.ActividadBD;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
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
        String accion = request.getParameter("accion");
        HttpSession sesion = request.getSession();
        if (accion.equals("mostrarCalendario")) {
            sesion.setAttribute("user", request.getParameter("user"));
            sesion.setAttribute("tipo", request.getParameter("tipo"));
            RequestDispatcher respuesta = getServletContext().getRequestDispatcher("/calendario.jsp");
            respuesta.forward(request, response);
        } else if (accion.equals("mostrarEventos")) {
            String user = (String) sesion.getAttribute("user");
            String tipoRol = (String) sesion.getAttribute("tipo");
            if (tipoRol.equals("D")) {
                ArrayList<Actividad> listaActividades = Actividad.getActividades(user);
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

                ArrayList<Vacaciones> listaVacaciones = Vacaciones.getVacaciones(user);
                for (Vacaciones v : listaVacaciones) {
                    CalendarDTO c = new CalendarDTO();
                    c.setStart(v.getFechaInicio());
                    c.setEnd(v.getFechaFin());
                    c.setTitle("Vacaciones");
                    c.setColor("#58FA82");
                    c.setTextColor("#000000");
                    l.add(c);
                }

                ArrayList<TareaPersonal> listaTareas = TareaPersonal.getTareas(user);
                for (TareaPersonal t : listaTareas) {
                    CalendarDTO c = new CalendarDTO();
                    c.setId(t.getId());
                    c.setStart(t.getFecha());
                    c.setEnd(t.getFecha());
                    if (null != t.getTipo()) {
                        switch (t.getTipo()) {
                            case "TU":
                                c.setTitle("Trato con usuarios");
                                break;
                            case "RE":
                                c.setTitle("Reuniones externas");
                                break;
                            case "RI":
                                c.setTitle("Reuniones internas");
                                break;
                            case "LD":
                                c.setTitle("Lectura de documentacion");
                                break;
                            case "RV":
                                c.setTitle("Revision de documentacion");
                                break;
                            case "ED":
                                c.setTitle("Elaboracion de documentacion");
                                break;
                            case "DP":
                                c.setTitle("Desarrollo de programas");
                                break;
                            case "VP":
                                c.setTitle("Verificacion de programas");
                                break;
                            case "FU":
                                c.setTitle("Formacion de usuarios");
                                break;
                            case "FA":
                                c.setTitle("Formacion de otras actividades");
                                break;
                        }
                    }
                    c.setColor("#F781F3");
                    c.setTextColor("#000000");
                    l.add(c);
                }

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                PrintWriter out = response.getWriter();
                out.write(new Gson().toJson(l));
            } else if (tipoRol.equals("J")) {
                ArrayList<Proyecto> listaProyectos = Proyecto.getProyectosSinOrdenar(user);
                List l = new ArrayList();

                for (Proyecto p : listaProyectos) {
                    CalendarDTO c = new CalendarDTO();
                    c.setId(p.getIdentificador());
                    c.setStart(p.getFechaInicio());
                    c.setEnd(p.getFechaFin());
                    c.setTitle(p.getNombre());
                    if (p.getEstado() == 'S') {
                        c.setColor("#81BEF7");
                    } else if (p.getEstado() == 'E') {
                        c.setColor("#FE2E2E");
                    } else if (p.getEstado() == 'F') {
                        c.setColor("#FFFF00");
                    } else if (p.getEstado() == 'C') {
                        c.setColor("#A4A4A4");
                    }

                    c.setTextColor("#000000");
                    l.add(c);
                }
                
                ArrayList<Vacaciones> listaVacaciones = Vacaciones.getVacaciones(user);
                for (Vacaciones v : listaVacaciones) {
                    CalendarDTO c = new CalendarDTO();
                    c.setStart(v.getFechaInicio());
                    c.setEnd(v.getFechaFin());
                    c.setTitle("Vacaciones");
                    c.setColor("#58FA82");
                    c.setTextColor("#000000");
                    l.add(c);
                }

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                PrintWriter out = response.getWriter();
                out.write(new Gson().toJson(l));
            }
        } else if (accion.equals("anadirVacaciones")) {
            sesion.setAttribute("user", (String) sesion.getAttribute("user"));
            sesion.setAttribute("tipo", (String) sesion.getAttribute("tipo"));
            RequestDispatcher respuesta = getServletContext().getRequestDispatcher("/vacaciones.jsp");
            respuesta.forward(request, response);
        } else if (accion.equals("anadirNuevasVacaciones")) {
            String mensaje = "";
            boolean correcto = false;
            String login = (String) sesion.getAttribute("user");
            String tipo = (String) sesion.getAttribute("tipo");
            String fechaI = request.getParameter("fechaI");
            String fechaF = request.getParameter("fechaF");

            //if (tipo.equals("V")) {
            Vacaciones c = new Vacaciones();
            c.setUsuario(login);
            c.setFechaInicio(fechaI);
            c.setFechaFin(fechaF);

            //Comprobaciones de un evento tipo Vacaciones
            //Primero obtener de la base de datos las fechas de vacaciones del usuario
            List<Vacaciones> vacaciones = VacacionesDB.obtenerVacaciones(login);

            //Comprobar cuantos días lleva de vacaciones
            long dias = c.comprobarDiasVacaciones(vacaciones);

            //Días de vacaciones que ha solicitado
            List<Vacaciones> nuevo = new ArrayList<Vacaciones>();
            nuevo.add(c);
            long nuevos = c.comprobarDiasVacaciones(nuevo);
            if (nuevos > 14) {
                mensaje = "No puedes asignar más de dos semanas seguidas de vacaciones";
            } else if ((dias + nuevos) > 28) {
                mensaje = "No puedes asignar " + nuevos + " días de vacaciones. Te quedan: " + (28 - dias);
            }

            //Comprobar que en esas fechas no tenga ya una actividad asignada
            List<Actividad> actividades = ActividadBD.selectActividades(login);
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
                correcto = true;
                VacacionesDB.insertVacaciones(c);
            }

            try (PrintWriter out = response.getWriter()) {
                String path = "";
                if(!correcto){
                    path = "/CalendarioD?accion=anadirVacaciones";
                }else{
                    path = "/CalendarioD?accion=mostrarCalendario&user=" + login + "&tipo=" + tipo;
                
                }
                request.setAttribute("mensaje", mensaje);
                RequestDispatcher respuesta = getServletContext().getRequestDispatcher(path);
                respuesta.forward(request, response);
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
