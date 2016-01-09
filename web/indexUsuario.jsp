<%-- 
    Document   : indexUsuario
    Created on : 21-dic-2015, 16:22:33
    Author     : Jennifer
--%>

<%@page import="Data.UserDB"%>
<%@page import="Business.User"%>
<%@page import="Business.Proyecto"%>
<%@page import="Business.TareaPersonal"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>AdminLTE 2 | Dashboard</title>
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
            <% String user = (String) session.getAttribute("user");%>

            <% if (session.getAttribute("tipo").equals("A")) {%>
            <%@include file="administradorBar.jsp" %>
            <% } else if (session.getAttribute("tipo").equals("D")) {%>
            <%@include file="desarrolladorBar.jsp" %>
            <% } else {%>
            <%@include file="jefeProyectoBar.jsp" %>
            <% }%>
            <% session.setAttribute("user", user);%>

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        Dashboard
                        <small>Control panel</small>
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                        <li class="active">Dashboard</li>
                    </ol>
                </section>

                <!-- Main content -->
                <section class="content">

                    <!-- Main row -->
                    <div class="row">
                        <!-- Left col -->
                        <section class="col-lg-7 connectedSortable">


                            <!-- TO DO List -->
                            <div class="box box-primary">
                                <div class="box-header">
                                    <i class="ion ion-clipboard"></i>
                                    <h3 class="box-title">To Do List</h3>
                                    <div class="box-tools pull-right">
                                        <ul class="pagination pagination-sm inline">
                                            <li><a href="#">&laquo;</a></li>
                                            <li><a href="#">1</a></li>
                                            <li><a href="#">2</a></li>
                                            <li><a href="#">3</a></li>
                                            <li><a href="#">&raquo;</a></li>
                                        </ul>
                                    </div>
                                </div><!-- /.box-header -->
                                <div class="box-body">
                                    <ul class="todo-list">
                                        <%
                                            session.setAttribute("urlAnterior", "/PGP/indexUsuario.jsp");
                                            ArrayList<String> tmp = new ArrayList<String>();
                                            String siguiente = "";
                                            if (session.getAttribute("tipo").equals("D")) {
                                                siguiente = "Tareas?tarea=actualizarUnaTarea&usuario=" + session.getAttribute("user") + "&idTarea=";

                                                ArrayList<TareaPersonal> ts = (ArrayList<TareaPersonal>) session.getAttribute("tareas");
                                                for (int i = 0; i < ts.size(); i++) {
                                                    tmp.add(Integer.toString(ts.get(i).getId()));
                                                    tmp.add(ts.get(i).getTipo());
                                                    tmp.add(ts.get(i).getFecha());
                                                }
                                            } else {
                                                if (session.getAttribute("tipo").equals("J")) {
                                                    siguiente = "Proyectos?proyecto=actualizarUnProyecto&usuario=" + session.getAttribute("user") + "&idProyecto=";
                                                    ArrayList<Proyecto> ps = (ArrayList<Proyecto>) session.getAttribute("proyectos");
                                                    for (int i = 0; i < ps.size(); i++) {
                                                        tmp.add(Integer.toString(ps.get(i).getIdentificador()));
                                                        tmp.add(ps.get(i).getNombre());
                                                        tmp.add(ps.get(i).getEstado() + "");
                                                    }

                                                } else {
                                                    if (session.getAttribute("tipo").equals("A")) {
                                                        siguiente = "AddUser?Accion=actualizaUnUsuario&login=";
                                                        ArrayList<User> us = (ArrayList<User>) session.getAttribute("usuarios");
                                                        for (int i = 0; i < us.size(); i++) {
                                                            tmp.add(us.get(i).getLogin());
                                                            tmp.add(us.get(i).getTipo() + "");
                                                            tmp.add(us.get(i).getNif());
                                                        }
                                                    }
                                                }
                                            }
                                            for (int i = 0; i < tmp.size(); i += 3) { %>
                                        <li>
                                            <!-- drag handle -->
                                            <span class="handle">
                                                <i class="fa fa-ellipsis-v"></i>
                                                <i class="fa fa-ellipsis-v"></i>
                                            </span>
                                            <!-- checkbox -->
                                            <input type="checkbox" value="" name="">
                                            <!-- todo text -->
                                            <%
                                                String res = "";
                                                switch (tmp.get(i + 1)) {
                                                    case "TU":
                                                        res = "Trato con usuarios";
                                                        break;
                                                    case "RE":
                                                        res = "Reuniones externas";
                                                        break;
                                                    case "RI":
                                                        res = "Reuniones internas";
                                                        break;
                                                    case "LD":
                                                        res = "Lectura de documentacion";
                                                        break;
                                                    case "RV":
                                                        res = "Revision de documentacion";
                                                        break;
                                                    case "ED":
                                                        res = "Elaboracion de documentacion";
                                                        break;
                                                    case "DP":
                                                        res = "Desarrollo de programas";
                                                        break;
                                                    case "VP":
                                                        res = "Verificacion de programas";
                                                        break;
                                                    case "FU":
                                                        res = "Formacion de usuarios";
                                                        break;
                                                    case "FA":
                                                        res = "Formacion de otras actividades";
                                                        break;
                                                    case "S":
                                                        res = "Sin comenzar";
                                                        break;
                                                    case "E":
                                                        res = "En curso";
                                                        break;
                                                    case "F":
                                                        res = "Finalizado";
                                                        break;
                                                    case "C":
                                                        res = "Cerrado";
                                                        break;
                                                    case "A":
                                                        res = "Administrador";
                                                        break;
                                                    case "J":
                                                        res = "Jefe de proyecto";
                                                        break;
                                                    case "D":
                                                        res = "Desarrollador";
                                                        break;
                                                    default:
                                                        res = tmp.get(i + 1);
                                                }
                                            %>
                                            <a href="<%=siguiente%><%=tmp.get(i)%>">
                                                <span class="text"><%= tmp.get(i)%> : <%= res%> </span>

                                                <!-- Emphasis label -->
                                                <small class="label label-danger"><i class="fa fa-clock-o"></i> <%=tmp.get(i + 2)%></small>
                                                <!-- General tools such as edit or delete-->
                                                <div class="tools">
                                                    <i class="fa fa-edit"></i>
                                                    <i class="fa fa-trash-o"></i>
                                                </div>
                                            </a>
                                        </li>
                                        <%}%>

                                    </ul>
                                </div><!-- /.box-body -->
                                <div class="box-footer clearfix no-border">
                                    <% if (session.getAttribute("tipo").equals("A") || session.getAttribute("tipo").equals("D")) { %><button class="btn btn-default pull-right"><i class="fa fa-plus"></i> Add item</button><%}%>
                                </div>
                            </div><!-- /.box -->


                        </section><!-- /.Left col -->
                        <!-- right col (We are only adding the ID to make the widgets sortable)-->
                        <section class="col-lg-5 connectedSortable">

                            <!-- Calendar -->
                            <div class="box box-solid bg-green-gradient">
                                <div class="box-header">
                                    <i class="fa fa-calendar"></i>
                                    <h3 class="box-title">Calendar</h3>
                                    <!-- tools box -->
                                    <div class="pull-right box-tools">
                                        <!-- button with a dropdown -->
                                        <div class="btn-group">
                                            <button class="btn btn-success btn-sm dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bars"></i></button>
                                            <ul class="dropdown-menu pull-right" role="menu">
                                                <li><a href="#">Add new event</a></li>
                                                <li><a href="#">Clear events</a></li>
                                                <li class="divider"></li>
                                                <li><a href="#">View calendar</a></li>
                                            </ul>
                                        </div>
                                        <button class="btn btn-success btn-sm" data-widget="collapse"><i class="fa fa-minus"></i></button>
                                        <button class="btn btn-success btn-sm" data-widget="remove"><i class="fa fa-times"></i></button>
                                    </div><!-- /. tools -->
                                </div><!-- /.box-header -->
                                <div class="box-body no-padding">
                                    <!--The calendar -->
                                    <div id="calendar" style="width: 100%"></div>
                                </div><!-- /.box-body -->
                                <div class="box-footer text-black">
                                    <div class="row">
                                        <div class="col-sm-6">
                                            <!-- Progress bars -->
                                            <div class="clearfix">
                                                <span class="pull-left">Task #1</span>
                                                <small class="pull-right">90%</small>
                                            </div>
                                            <div class="progress xs">
                                                <div class="progress-bar progress-bar-green" style="width: 90%;"></div>
                                            </div>

                                            <div class="clearfix">
                                                <span class="pull-left">Task #2</span>
                                                <small class="pull-right">70%</small>
                                            </div>
                                            <div class="progress xs">
                                                <div class="progress-bar progress-bar-green" style="width: 70%;"></div>
                                            </div>
                                        </div><!-- /.col -->
                                        <div class="col-sm-6">
                                            <div class="clearfix">
                                                <span class="pull-left">Task #3</span>
                                                <small class="pull-right">60%</small>
                                            </div>
                                            <div class="progress xs">
                                                <div class="progress-bar progress-bar-green" style="width: 60%;"></div>
                                            </div>

                                            <div class="clearfix">
                                                <span class="pull-left">Task #4</span>
                                                <small class="pull-right">40%</small>
                                            </div>
                                            <div class="progress xs">
                                                <div class="progress-bar progress-bar-green" style="width: 40%;"></div>
                                            </div>
                                        </div><!-- /.col -->
                                    </div><!-- /.row -->
                                </div>
                            </div><!-- /.box -->

                        </section><!-- right col -->
                    </div><!-- /.row (main row) -->

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

