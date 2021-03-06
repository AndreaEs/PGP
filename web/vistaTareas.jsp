<%-- 
    Document   : vistaTareas
    Created on : 07-ene-2016, 13:05:05
    Author     : Jennifer
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="Business.TareaPersonal"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Ver Tareas</title>
        <!-- Tell the browser to be responsive to screen width -->
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <!-- Bootstrap 3.3.5 -->
        <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
        <!-- Ionicons -->
        <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
        <!-- Theme style -->
        <link rel="stylesheet" href="dist/css/AdminLTE.min.css">
        <!-- AdminLTE Skins. Choose a skin from the css/skins
             folder instead of downloading all of them to reduce the load. -->
        <link rel="stylesheet" href="dist/css/skins/_all-skins.min.css">
        <!-- iCheck -->
        <link rel="stylesheet" href="plugins/iCheck/flat/blue.css">
        <!-- Morris chart -->
        <link rel="stylesheet" href="plugins/morris/morris.css">
        <!-- jvectormap -->
        <link rel="stylesheet" href="plugins/jvectormap/jquery-jvectormap-1.2.2.css">
        <!-- Date Picker -->
        <link rel="stylesheet" href="plugins/datepicker/datepicker3.css">
        <!-- Daterange picker -->
        <link rel="stylesheet" href="plugins/daterangepicker/daterangepicker-bs3.css">
        <!-- bootstrap wysihtml5 - text editor -->
        <link rel="stylesheet" href="plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css">

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
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <%String mensaje = (String) request.getAttribute("mensaje");
                        if (mensaje != null) {
                    %>
                    <h2><font color="#01DF01"><%=mensaje%></font></h2>
                        <%}
                        %>
                    <h1>
                        Tareas </h1>
                </section>

                <!-- Main content -->
                <section class="content">
                    <div class="row">
                        <div class="col-md-3">
                            <div class="box box-solid">
                                <div class="box-header with-border">
                                    <h3 class="box-title">Lista de tareas</h3>
                                </div>
                                <div class="box-body no-padding">
                                    <%
                                        String usuario = (String) session.getAttribute("user");
                                        ArrayList<TareaPersonal> tareas = (ArrayList<TareaPersonal>) session.getAttribute("tareas");

                                        for (TareaPersonal a : tareas) {
                                    %>
                                    <ul class="nav nav-pills nav-stacked">

                                        <div class="btn pull-left">
                                            <div>
                                                <a href="Tareas?tarea=actualizarUnaTarea&usuario=<%=usuario%>&idTarea=<%= a.getId()%>">
                                                    <i class="fa fa-file-text-o"></i> <b><%= a.getId()%></b>
                                                    <% if (a.getTipo().equals("TU")) {%>
                                                    <i class="fa fa-file-text-o"></i> <b>Trato con Usuarios</b>
                                                    <%}else if (a.getTipo().equals("RE")) {%>
                                                    <i class="fa fa-file-text-o"></i> <b>Reuniones Externas</b>
                                                    <%}else if (a.getTipo().equals("RI")) {%>
                                                    <i class="fa fa-file-text-o"></i> <b>Reuniones Internas</b>
                                                    <%}else if (a.getTipo().equals("LD")) {%>
                                                    <i class="fa fa-file-text-o"></i> <b>Lectura de Documentación</b>
                                                    <%}else if (a.getTipo().equals("RV")) {%>
                                                    <i class="fa fa-file-text-o"></i> <b>Revisión de documentación</b>
                                                    <%}else if (a.getTipo().equals("ED")) {%>
                                                    <i class="fa fa-file-text-o"></i> <b>Elaboración de documentación</b>
                                                    <%}else if (a.getTipo().equals("DP")) {%>
                                                    <i class="fa fa-file-text-o"></i> <b>Desarrollo de Programas</b>
                                                    <%}else if (a.getTipo().equals("VP")) {%>
                                                    <i class="fa fa-file-text-o"></i> <b>Verificación de Programas</b>
                                                    <%}else if (a.getTipo().equals("FU")) {%>
                                                    <i class="fa fa-file-text-o"></i> <b>Formación de Usuarios</b>
                                                    <%}else if (a.getTipo().equals("FA")) {%>
                                                    <i class="fa fa-file-text-o"></i> <b>Formación de Otras Actividades</b>
                                                    <%}%>
                                                    <i class="fa fa-file-text-o"></i> <%= a.getFecha()%>
                                                </a>
                                            </div>
                                        </div>

                                    </ul>
                                    <%
                                        }
                                    %>
                                </div><!-- /.box-body -->
                                <div class="box-footer">
                                    <a class="btn btn-app" href="Tareas?tarea=crearNuevaTarea&usuario=<%= usuario%>">
                                        <i class="fa fa-edit"></i> Nueva Tarea
                                    </a>
                                </div>
                            </div><!-- /. box -->

                        </div><!-- /.row -->
                </section><!-- /.content -->
            </div><!-- /.content-wrapper -->
            <%@include file="footer.html" %>

            <%@include file="settings.html" %>
        </div><!-- ./wrapper -->

        <!-- jQuery 2.1.4 -->
        <script src="plugins/jQuery/jQuery-2.1.4.min.js"></script>
        <!-- Bootstrap 3.3.5 -->
        <script src="bootstrap/js/bootstrap.min.js"></script>
        <!-- Slimscroll -->
        <script src="plugins/slimScroll/jquery.slimscroll.min.js"></script>
        <!-- FastClick -->
        <script src="plugins/fastclick/fastclick.min.js"></script>
        <!-- AdminLTE App -->
        <script src="dist/js/app.min.js"></script>
        <!-- AdminLTE for demo purposes -->
        <script src="dist/js/demo.js"></script>
    </body>
</html>
