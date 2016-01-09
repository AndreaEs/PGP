<%-- 
    Document   : informeJPPruebas
    Created on : 08-ene-2016, 15:55:30
    Author     : gil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Informe del jefe de proyectos</h1>
        <form action="InformeJP" method="post">
            <label>Login del usuario</label>
            <input type="text" name="login">
            </br>
            <label>Tipo de informe</label>
            <select name="tipoI">
                <option value="TA"> Trabajadores y Actividades </option>
                <option value="APE"> Actividades Pendientes de Envío</option>
                <option value="APA"> Actividades Pendientes de Aprobacion </option>
                <option value="AAF"> Relación de Actividades Activas o Finalizadas </option>
                <option value="AAR"> Actividades a realizar </option>
            </select>
            </br>
            <label>Fecha de inicio</label>
            <input type="text" name="fechaI" data-mask>
            </br>
            <label>Fecha de fin</label>
            <input type="text" name="fechaF" data-mask>
            </br>
            <button type="submit" >Buscar</button>
    </body>
</html>
