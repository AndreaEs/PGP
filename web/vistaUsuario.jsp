<%-- 
    Document   : administrador
    Created on : 29-dic-2015, 19:08:12
    Author     : Sandra
--%>

<%@page import="Data.UserDB"%>
<%@page import="Business.User"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Usuario</title>
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

            
            <!-- Left side column. contains the logo and sidebar -->
             <% if (session.getAttribute("tipo").equals("A")){
             %>
            <%@include file="administradorBar.jsp" %>
            <% }else if(session.getAttribute("tipo").equals("D")) { %>
            <%@include file="desarrolladorBar.jsp" %>
            <% } else {%>
            <%@include file="jefeProyectoBar.jsp" %>
            <% }%>

                        <!-- Content Wrapper. Contains page content -->
                        <div class="content-wrapper">
                            <!-- Content Header (Page header) -->
                            <section class="content-header">
                                <h1>
                                    Usuarios
                                    
                                </h1>
                                
                            </section>

                            <!-- Main content -->
                            <section class="content">

                                <!-- Main row -->
                                <div class="row">
                                    <div class="box box-info">
                                        <div class="box-header with-border">
                                            <%
                                                List usuarios;
                                                User usuario = new User();
                                                usuarios = (List) session.getAttribute("todosUsuarios");
                                                String login ="" ;

                                                
                                                for (int i = 0; i < usuarios.size(); i++) {
                                                    if (request.getParameter("usuario" + i) != null) {
                                                        
                                                        login= usuarios.get(i).toString();
                                                    }

                                                }
                                                usuario = UserDB.getUsuario(login);
                                                session.setAttribute("usuario", usuario);
                                               // User usuario = (User)session.getAttribute("usuario");
                                                

                                            %>
                                            <h3 class="box-title">add user</h3>
                                            <form action="AddUser" method="post">
                                            <button type="submit" class="btn btn-info pull-right" name="Accion" value="eliminar">Eliminar</button>
                                            </form>

                                        </div><!-- /.box-header -->
                                        <!-- form start -->
                                        <form class="form-horizontal" action="AddUser" method="post" onsubmit="return verificar(nif, login, pass, repass)">
                                            <div class="box-body">
                                                <div class="form-group">
                                                    <label for="inputUser3" class="col-sm-2 control-label">Login</label>
                                                    <div class="col-sm-10">
                                                        <input type="text" class="form-control" id="inputEmail3" name="login" value= <%= usuario.getLogin() %> />
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="inputUser3" class="col-sm-2 control-label">NIF</label>
                                                    <div class="col-sm-10">
                                                        <input readonly type="text" class="form-control" id="inputEmail3" name="nif" value= <%= usuario.getNif() %> />
                                                        <!-- incluir javascript que compruebe que es un nif-->
                                                    </div>
                                                </div>


                                                <div class="form-group">
                                                    <label for="inputPassword3" class="col-sm-2 control-label">Password</label>
                                                    <div class="col-sm-10">
                                                        <input type="password" class="form-control" id="inputPassword3" name="pass" value= <%= usuario.getPass() %>/>
                                                    </div>
                                                </div>
                                                
                                                <div class="form-group"> 
                                                    <label  class="col-sm-2 control-label">Tipo de usuario</label>
                                                    
                                                    <select disabled name="rol" class="form-control-static"  >

                                                        <option class="option-control" value="Desarrollador" <% if(usuario.getTipo() == 'D') out.println("selected");%>> 
                                                            Desarrollador </option>
                                                        <option class="option-control" value="Administrador" <% if(usuario.getTipo() == 'A') out.println("selected");%>>
                                                            Administrador </option>
                                                        <option class="option-control" value="Jefe de Proyecto" <% if(usuario.getTipo() == 'J') out.println("selected");%>> 
                                                            Jefe Proyecto </option>

                                                    </select>
                                                </div>
                                                            <div class="form-group"> 
                                                    <label  class="col-sm-2 control-label">Máximo de Proyectos</label>
                                                    
                                                    <select name="maxProy" class="form-control-static"  >
                                                       

                                                        <option class="option-control" value="1" <% if(usuario.getMaxProy() == 1) out.println("selected");%>> 
                                                            1 </option>
                                                        <option class="option-control" value="2" <% if(usuario.getMaxProy() == 2) out.println("selected");%>>
                                                            2 </option>
                                                        

                                                    </select>
                                                </div>

                                                <div class="form-group">
                                                    <label class="col-sm-2 control-label">InformaciÃ³n general</label>
                                                    <textarea class="form-control-static" rows="3"  name="informacion"><%= usuario.getInfoGeneral()%></textarea>
                                                </div>
                                            </div><!-- /.box-body -->
                                            <div class="box-footer">
                                                <button type="submit" class="btn btn-default" name="Accion" value="cancelar">Cancel</button>
                                                <button type="submit" class="btn btn-info pull-right" name="Accion" value="modificar">Modificar</button>
                                            </div><!-- /.box-footer -->
                                        </form>
                                    </div><!-- /.box -->


                                    <!--</section><!-- right col -->
                                </div><!-- /.row (main row) -->

                            </section><!-- /.content -->
                        </div>




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


