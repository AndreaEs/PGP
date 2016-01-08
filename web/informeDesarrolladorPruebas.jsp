<%-- 
    Document   : informeDesarrolladorPruebas
    Created on : 07-ene-2016, 16:33:18
    Author     : gil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Página de pruebas de obtención de informe del desarrollador</title>
    </head>
    <body>
        <h1>Informe del desarrollador</h1>
        <form action="InformeD" method="post">
            <label>Login del usuario</label>
            <input type="text" name="login">
            </br>
            <!--Añadir comprobador de que la fecha de Inicio va antes que la del Fin-->
            <label>Fecha de inicio</label>
            <input type="text" name="fechaI" data-mask>
            </br>
            <label>Fecha de fin</label>
            <input type="text" name="fechaF" data-mask>
            </br>
            <button type="submit" >Buscar</button>
        </form>
    </body>
</html>
