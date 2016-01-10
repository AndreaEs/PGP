<%-- 
    Document   : administrador
    Created on : 29-dic-2015, 19:08:12
    Author     : Sandra
--%>

<%@page import="Business.Participante"%>
<%@page import="Business.User"%>
<%@page import="Data.UserDB"%>
<%@page import="Data.ParticipantesBD" %>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Usuarios</title>
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


        <link rel="stylesheet" href="login.css">
        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
            <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
        <script type="text/javascript">
            function url() {
            <% session.setAttribute("urlAnterior", "Actividades?actividad=verActividades&idFase=" + session.getAttribute("idFase"));%>

            }
        </script>
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
            <!-- Left side column. contains the logo and sidebar -->


            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <div class="administrador-usuarios">
                    <%
                        ArrayList<User> res = new ArrayList<User>();
                        ArrayList<String> participantes = new ArrayList<String>();

                        if (request.getParameter("participante") != null) {
                            int idProyecto = (Integer) session.getAttribute("idProyecto");
                            res = ParticipantesBD.getParticipantesDisponibles();
                            participantes = Participante.getParticipantesDisponibles(idProyecto);
                    %>
                    <br><br>
                    <form action="Participantes?accion=crearParticipacion&idActividad=<%=request.getParameter("idActividad")%>" method="post">
                        <table class="table table-striped text-center">

                            <tr>
                            <div class="margin">
                                <div class="btn pull-left">
                                    <td><select name="login" class="form-control select2 select2-hidden-accessible">
                                            <%for (int i = 0; i < res.size(); i++) {%>
                                            <option value="<%= res.get(i).getLogin()%>"><b><%= res.get(i).getLogin()%></b></option>
                                                <%}%>
                                        </select>
                                    </td>

                                    <td><div class="col-xs-4"><input type="number" class="form-control" placeholder="porcentaje" name="porcentaje"/></div></td>
                                    <td>
                                        <select name="idParticipante">
                                            <% for (int j = 0; j < participantes.size(); j++) {%>
                                            <option value="<%=participantes.get(j)%>"><%=participantes.get(j)%></option>
                                            <%}%>
                                        </select>
                                    </td>
                                    <td><input type="submit" class="btn btn-block btn-info btn-flat" value="Add"></td>
                                    <td></td>

                                </div>
                            </div>
                            </tr>
                        </table>

                    </form>
                    <div class="box-footer">
                        <a href="<%=session.getAttribute("urlAnterior")%>"><input type="button" class="btn btn-default" name="actividad" value="Cancelar" onclick="url()"/></a>
                    </div>

                    <table id="tabla-usuarios">
                        <%} else {
                            ArrayList<String> array = new ArrayList<String>();
                            array = UserDB.getLoginUsuarios();
                      session.setAttribute("todosUsuarios", array);%>
                        <br>
                        <a id ="nuevo-usuario" href="add.jsp">
                            <span>Añadir nuevo usuario</span>  <i class="fa fa-user-plus"></i>
                        </a>
                        <br><br>
                        <%
                                for (int u = 0; u < array.size(); u++) {
                                    out.println("<tr>");
                                    out.println("<td>");

                                    out.println("<form action=\"vistaUsuario.jsp\">");
                                    out.println("<a>");
                                    out.println("<input id=\"user\" style=\"border: none; background:none; width:200px;  height:50px \" type=\"submit\"  name=\"usuario" + u + "\" value=\"" + array.get(u) + "\"  > <i class=\"fa fa-file-text\"></i>");
                                    out.println("</a>");
                                    out.println("</form>");
                                    out.println("</td>");
                                    out.println("</tr>");

                                }
                            }%>

                    </table>
                </div>
            </div><!-- /.content-wrapper -->




            <%@include file="footer.html" %>
            <%@include file="settings.html" %>
        </div><!-- ./wrapper -->

        <!-- jQuery 2.1.4 -->
        <script src="plugins/jQuery/jQuery-2.1.4.min.js"></script>
        <!-- jQuery UI 1.11.4 -->
        <script src="https://code.jquery.com/ui/1.11.4/jquery-ui.min.js"></script>
        <!-- Resolve conflict in jQuery UI tooltip with Bootstrap tooltip -->
        <script>
                                                $.widget.bridge('uibutton', $.ui.button);
        </script>
        <!-- Bootstrap 3.3.5 -->
        <script src="bootstrap/js/bootstrap.min.js"></script>
        <!-- Morris.js charts -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/raphael/2.1.0/raphael-min.js"></script>
        <script src="plugins/morris/morris.min.js"></script>
        <!-- Sparkline -->
        <script src="plugins/sparkline/jquery.sparkline.min.js"></script>
        <!-- jvectormap -->
        <script src="plugins/jvectormap/jquery-jvectormap-1.2.2.min.js"></script>
        <script src="plugins/jvectormap/jquery-jvectormap-world-mill-en.js"></script>
        <!-- jQuery Knob Chart -->
        <script src="plugins/knob/jquery.knob.js"></script>
        <!-- daterangepicker -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.10.2/moment.min.js"></script>
        <script src="plugins/daterangepicker/daterangepicker.js"></script>
        <!-- datepicker -->
        <script src="plugins/datepicker/bootstrap-datepicker.js"></script>
        <!-- Bootstrap WYSIHTML5 -->
        <script src="plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js"></script>
        <!-- Slimscroll -->
        <script src="plugins/slimScroll/jquery.slimscroll.min.js"></script>
        <!-- FastClick -->
        <script src="plugins/fastclick/fastclick.min.js"></script>
        <!-- AdminLTE App -->
        <script src="dist/js/app.min.js"></script>
        <!-- AdminLTE dashboard demo (This is only for demo purposes) -->
        <script src="dist/js/pages/dashboard.js"></script>
        <!-- AdminLTE for demo purposes -->
        <script src="dist/js/demo.js"></script>
    </body>
</html>


