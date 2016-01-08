<%-- 
    Document   : vistaInformeJP
    Created on : 08-ene-2016, 15:55:45
    Author     : gil
--%>

<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.HashMap"%>
<%@page import="Business.User"%>
<%@page import="Business.Actividad"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            String login = (String)session.getAttribute("login");
            String fechaI = (String)session.getAttribute("fechaI");
            String fechaF = (String)session.getAttribute("fechaF");
            %>
            <h3>Entre <%=fechaI%> y <%=fechaF%> se obtienen las siguientes realciones Usuarios/Actividad:</h3>
            <%
            HashMap<String,ArrayList<Actividad>> inf = (HashMap<String,ArrayList<Actividad>>) session.getAttribute("informe");
            Iterator it = inf.entrySet().iterator();
            while(it.hasNext()){
                Entry par = (Entry)it.next();
                String valor = (String) par.getKey();
        %>
        <h3>usuario <%= valor %></h3>
        
        <% if(!inf.get(valor).isEmpty()){
            for(int i=0;i<inf.get(valor).size();i++){
        %>
        <h4>Descripcion: <%= inf.get(valor).get(i).getDescripcion()%></h4>
        <h4>Rol: <%= inf.get(valor).get(i).getRolNecesario()%></h4>
        <h4>Fecha Inicio: <%= inf.get(valor).get(i).getFechaInicio()%></h4>
        <h4>Fecha Fin: <%= inf.get(valor).get(i).getFechaFin()%></h4>
        <h4>Estado: <%= inf.get(valor).get(i).getEstado()%></h4>
        <h4>Duración estimada: <%= inf.get(valor).get(i).getDuracionEstimada()%></h4>
        <h4>Duración real <%= inf.get(valor).get(i).getDuracionReal()%></h4>
        <h4>Fase: <%= inf.get(valor).get(i).getIdFase()%></h4>
        <%
                    }
                }
            }
        %>
    </body>
</html>
