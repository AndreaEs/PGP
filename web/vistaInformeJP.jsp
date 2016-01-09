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
            String tipoI = (String)session.getAttribute("tipoI");
            if(tipoI.equals("TA")){
            %>
            <h3>Entre <%=fechaI%> y <%=fechaF%> se obtienen las siguientes relaciones Usuarios/Actividad:</h3>
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
            }else if(tipoI.equals("AAF")){
                if(fechaI.equals(fechaF)){
                %>
                <h3>Relación de actividades activas el <%=fechaI%>:</h3>
                <%
                }else{
                %>
                <h3>Relación de actividades activas entre el <%=fechaI%> y <%=fechaF%>:</h3>
                <%
                }
                ArrayList<Actividad> act = (ArrayList<Actividad>)session.getAttribute("informe");
                for(int i=0;i<act.size();i++){
                %>
                <h4>Identificador: <%= act.get(i).getIdentificador()%></h4>
                <h4>Fecha Inicio: <%= act.get(i).getFechaInicio()%></h4>
                <h4>Fecha Fin: <%= act.get(i).getFechaFin()%></h4>
                <h4>Estado: <%= act.get(i).getEstado()%></h4>
                <h4>Duración estimada: <%= act.get(i).getDuracionEstimada()%></h4>
                <h4>Duración Real: <%= act.get(i).getDuracionReal()%></h4>
                <%
                }
            }else if(tipoI.equals("AAR")){
            %>
                <h3>Actividades a realizar entre las fechas <%=fechaI%> y <%=fechaF%>:</h3>
                <%
                ArrayList<Actividad> act = (ArrayList<Actividad>)session.getAttribute("informe");
                if(!act.isEmpty()){
                    for(int i=0;i<act.size();i++){
                    %>
                        <h4>Identificador: <%= act.get(i).getIdentificador()%></h4>
                        <h4>Fecha Inicio: <%= act.get(i).getFechaInicio()%></h4>
                        <h4>Fecha Fin: <%= act.get(i).getFechaFin()%></h4>
                        <h4>Estado: <%= act.get(i).getEstado()%></h4>
                        <h4>Recursos...</h4>
                        <h4>Duración estimada: <%= act.get(i).getDuracionEstimada()%></h4>
                        <h4>Rol: <%= act.get(i).getRolNecesario()%></h4>
                    <%
                    }
                }
            }
            %>
            
    </body>
</html>
