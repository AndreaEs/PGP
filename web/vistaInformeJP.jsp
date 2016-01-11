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
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <!--meta http-equiv="Content-Type" content="text/html; charset=UTF-8"-->
        <title>Ver informe jefe de proyectos</title>
        <!-- Tell the browser to be responsive to screen width -->
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <!-- Bootstrap 3.3.5 -->
        <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
        <!-- Ionicons -->
        <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
        <!-- daterange picker -->
        <link rel="stylesheet" href="plugins/daterangepicker/daterangepicker-bs3.css">
        <!-- iCheck for checkboxes and radio inputs -->
        <link rel="stylesheet" href="plugins/iCheck/all.css">
        <!-- Bootstrap Color Picker -->
        <link rel="stylesheet" href="plugins/colorpicker/bootstrap-colorpicker.min.css">
        <!-- Bootstrap time Picker -->
        <link rel="stylesheet" href="plugins/timepicker/bootstrap-timepicker.min.css">
        <!-- Select2 -->
        <link rel="stylesheet" href="plugins/select2/select2.min.css">
        <!-- Theme style -->
        <link rel="stylesheet" href="dist/css/AdminLTE.min.css">
        <!-- AdminLTE Skins. Choose a skin from the css/skins
             folder instead of downloading all of them to reduce the load. -->
        <link rel="stylesheet" href="dist/css/skins/_all-skins.min.css">

        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
            <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
    </head>
    <body class="hold-transition skin-blue sidebar-mini">
        <div class="wrapper">

            <% if (session.getAttribute("tipo").equals("A")) {
            %>
            <%@include file="administradorBar.jsp" %>
            <% } else if (session.getAttribute("tipo").equals("D")) { %>
            <%@include file="desarrolladorBar.jsp" %>
            <% } else {%>
            <%@include file="jefeProyectoBar.jsp" %>
            <% }%>
            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <section class="content">
                    <%
                        String login = (String) session.getAttribute("login");
                        String fechaI = (String) session.getAttribute("fechaI");
                        String fechaF = (String) session.getAttribute("fechaF");
                        String tipoI = (String) session.getAttribute("tipoI");
                        if (tipoI.equals("TA")) {
                    %>
                    <h3>Entre <%=fechaI%> y <%=fechaF%> se obtienen las siguientes relaciones Usuarios/Actividad:</h3>
                    <%
                        HashMap<String, ArrayList<Actividad>> inf = (HashMap<String, ArrayList<Actividad>>) session.getAttribute("informe");
                        Iterator it = inf.entrySet().iterator();
                        while (it.hasNext()) {
                            Entry par = (Entry) it.next();
                            String valor = (String) par.getKey();
                    %>
                    <h3>usuario <%= valor%></h3>

                    <% if (!inf.get(valor).isEmpty()) {
                            for (int i = 0; i < inf.get(valor).size(); i++) {
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
                    } else if (tipoI.equals("AAF")) {
                        if (fechaI.equals(fechaF)) {
                    %>
                    <h3>Relación de actividades activas el <%=fechaI%>:</h3>
                    <%
                    } else {
                    %>
                    <h3>Relación de actividades activas entre el <%=fechaI%> y <%=fechaF%>:</h3>
                    <%
                        }
                        ArrayList<Actividad> act = (ArrayList<Actividad>) session.getAttribute("informe");
                        for (int i = 0; i < act.size(); i++) {
                    %>
                    <h4>Identificador: <%= act.get(i).getIdentificador()%></h4>
                    <h4>Fecha Inicio: <%= act.get(i).getFechaInicio()%></h4>
                    <h4>Fecha Fin: <%= act.get(i).getFechaFin()%></h4>
                    <h4>Estado: <%= act.get(i).getEstado()%></h4>
                    <h4>Duración estimada: <%= act.get(i).getDuracionEstimada()%></h4>
                    <h4>Duración Real: <%= act.get(i).getDuracionReal()%></h4>
                    <%
                        }
                    } else if (tipoI.equals("AAR")) {
                    %>
                    <h3>Actividades a realizar entre las fechas <%=fechaI%> y <%=fechaF%>:</h3>
                    <%
                        ArrayList<Actividad> act = (ArrayList<Actividad>) session.getAttribute("informe");
                        if (!act.isEmpty()) {
                            for (int i = 0; i < act.size(); i++) {
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
                    } else if (tipoI.equals("CA")) {
                    %>
                    <h3> Las actividades que ha dia de hoy han consumido mas tiempo de lo planificado son:</h3>

                    <% ArrayList<Actividad> act = (ArrayList<Actividad>) session.getAttribute("informe");
                        if (!act.isEmpty()) {
                            for (int i = 0; i < act.size(); i++) {
                    %>
                    <h4>-------------------------------------------------</h4>
                    <h4>Identificador: <%= act.get(i).getIdentificador()%></h4>
                    <h4>Fecha Inicio: <%= act.get(i).getFechaInicio()%></h4>
                    <h4>Fecha Fin: <%= act.get(i).getFechaFin()%></h4>
                    <h4> Persona encargada de realizarla <%= act.get(i).getLogin()%></h4>
                    <h4>Duración estimada: <%= act.get(i).getDuracionEstimada()%></h4>
                    <h4>Duracion real  <%= act.get(i).getDuracionReal()%></h4>

                    <%
                            }
                        }
                    } else if (tipoI.equals("AF")) {
                    %>
                    <h3>La relación persona/actividad realizar entre <%=fechaI%> y <%=fechaF%> son :</h3>

                    <%
                        HashMap<String, ArrayList<Actividad>> inf = (HashMap<String, ArrayList<Actividad>>) session.getAttribute("informe");
                        Iterator it = inf.entrySet().iterator();
                        while (it.hasNext()) {
                            Entry par = (Entry) it.next();
                            String valor = (String) par.getKey();
                    %>
                    <h3>usuario <%= valor%></h3>

                    <% if (!inf.get(valor).isEmpty()) {
                            for (int i = 0; i < inf.get(valor).size(); i++) {
                    %>
                    <h4>------------------------------------------------------------</h4>
                    <h4>Identificador: <%= inf.get(valor).get(i).getIdentificador()%> </h4>
                    <h4>Descripcion: <%= inf.get(valor).get(i).getDescripcion()%></h4>
                    <h4>Rol: <%= inf.get(valor).get(i).getRolNecesario()%></h4>
                    <h4>Fecha Inicio: <%= inf.get(valor).get(i).getFechaInicio()%></h4>
                    <h4>Fecha Fin: <%= inf.get(valor).get(i).getFechaFin()%></h4>
                    <h4>Duración estimada: <%= inf.get(valor).get(i).getDuracionEstimada()%></h4>
                    <h4>Fase: <%= inf.get(valor).get(i).getIdFase()%></h4>


                    <%  }
                            }
                        }
                    } else if (tipoI.equals("PC")) {
                    %>   

                    <h3>Los proyectos que se han cerrado entre <%=fechaI%> y <%=fechaF%> son :</h3>

                    <%
                        HashMap<String, HashMap<String, ArrayList<Actividad>>> inf = (HashMap<String, HashMap<String, ArrayList<Actividad>>>) session.getAttribute("informe");
                        System.out.println("inf " + inf);
                        if (inf != null) {
                            Iterator it1 = inf.entrySet().iterator();
                            while (it1.hasNext()) {
                                Entry par = (Entry) it1.next();
                                String valor = (String) par.getKey();
                    %>
                    <h2>El proyecto <%= valor%></h2>

                    <%
                        Iterator it2 = inf.get(valor).entrySet().iterator();
                        while (it2.hasNext()) {
                            Entry par2 = (Entry) it2.next();
                            String valor2 = (String) par2.getKey();
                    %>
                    <h3> Fase : <%= valor2%></h3>

                    <% if (!inf.get(valor).get(valor2).isEmpty()) {
                            for (int i = 0; i < inf.get(valor).get(valor2).size(); i++) {


                    %>
                    <h4> </h4>
                    <h4>----------------Actividad <%= inf.get(valor).get(valor2).get(i).getIdentificador()%>-----------</h4>
                    <h4>Descripcion: <%= inf.get(valor).get(valor2).get(i).getDescripcion()%></h4>
                    <h4>Rol: <%= inf.get(valor).get(valor2).get(i).getRolNecesario()%></h4>
                    <h4>Fecha Inicio: <%= inf.get(valor).get(valor2).get(i).getFechaInicio()%></h4>
                    <h4>Fecha Fin: <%= inf.get(valor).get(valor2).get(i).getFechaFin()%></h4>
                    <h4>Duración real: <%= inf.get(valor).get(valor2).get(i).getDuracionReal()%></h4>


                    <% }
                                        }
                                    }
                                }
                            }
                        }
                    %>
                    <div class="row no-print">
                        <div class="col-xs-12">
                            <div>
                                <a href="informesJP.jsp"><button type="button" class="btn btn-default pull-right">Cancelar</button></a>
                            </div>
                            <button class="btn btn-primary pull-right" style="margin-right: 5px;" onclick="window.print();"><i class="fa fa-print"></i> Print</button>
                            
                        </div>
                    </div>
                </section>
            </div>
            <%@include file="footer.html" %>
            <%@include file="settings.html" %>
        </div> 
        <!-- jQuery 2.1.4 -->
        <script src="plugins/jQuery/jQuery-2.1.4.min.js"></script>
        <!-- Bootstrap 3.3.5 -->
        <script src="bootstrap/js/bootstrap.min.js"></script>
        <!-- Slimscroll -->
        <script src="plugins/slimScroll/jquery.slimscroll.min.js"></script>
        <!-- Select2 -->
        <script src="plugins/select2/select2.full.min.js"></script>
        <!-- date-range-picker -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.10.2/moment.min.js"></script>
        <script src="plugins/daterangepicker/daterangepicker.js"></script>
        <!-- FastClick -->
        <script src="plugins/fastclick/fastclick.min.js"></script>
        <!-- AdminLTE App -->
        <script src="dist/js/app.min.js"></script>
        <!-- AdminLTE for demo purposes -->
        <script src="dist/js/demo.js"></script>

    </body>
</html>
