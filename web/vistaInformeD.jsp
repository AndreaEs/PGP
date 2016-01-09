<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.HashMap"%>
<%@page import="Business.Actividad"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ver informe desarrollador</title>
    </head>
    <body>
        <%
            String login = (String)session.getAttribute("login");
            String fechaI = (String)session.getAttribute("fechaI");
            String fechaF = (String)session.getAttribute("fechaF");
            HashMap<Integer,ArrayList<Actividad>> inf = (HashMap<Integer,ArrayList<Actividad>>) session.getAttribute("informe");
            Iterator it = inf.entrySet().iterator();
            while(it.hasNext()){
                Entry par = (Entry)it.next();
                int valor = (int) par.getKey();
        %>
        <h3>Semana: <%= valor %> </h3>
        <h3>Actividades</h3>
        <% if(!inf.get(valor).isEmpty()){
            for(int i=0;i<inf.get(valor).size();i++){
        %>
        <h4>Descripcion: <%= inf.get(valor).get(i).getDescripcion()%></h4>
        <h4>Fecha Inicio: <%= inf.get(valor).get(i).getFechaInicio()%></h4>
        <h4>Fecha Fin: <%= inf.get(valor).get(i).getFechaFin()%></h4>
        <h4>Estado: <%= inf.get(valor).get(i).getEstado()%></h4>
        <h4>Fase: <%= inf.get(valor).get(i).getIdFase()%></h4>
        <%
                    }
                }
            }
        %>
    </body>
</html>
