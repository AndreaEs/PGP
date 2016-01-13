<%-- 
    Document   : add
    Created on : 22-dic-2015, 17:23:09
    Author     : Jennifer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">

        <SCRIPT language="Javascript" type="text/JavaScript">
            function validarDni(nif) {
            var numero
            var let
            var letra
            var expresion_regular_dni

            expresion_regular_dni = /^\d{8}[a-zA-Z]$/;

            if(expresion_regular_dni.test (nif.value) == true){
            numero = nif.value.substr(0,nif.value.length-1);
            let = nif.value.substr(nif.value.length-1,1);
            numero = numero % 23;
                return true;
            }else{
            alert('Dni erroneo, formato no válido');
            return false;
            }
            }

            function validarLogin(login) {
            var usuario = login.value;
            if (alfanumerico(usuario)) {
                alert ("Introduzca su login");
                return false;
            }else {
                return true;
            }
            }

            function alfanumerico(txt) {
            // Devuelve 0 si la cadena esta vacia, 1 si es numerica 
            //o 2 si es alfanumerica
            var i;
                if (txt.length!=0) {
                    for (i=0;i<txt.length;i++){
                        if (txt.substr(i,1)<"0" || txt.substr(i,1)>"9") {
                            return false;
                        }
                    }
                return false;
                }
                    else {
                        return true;
                    }
            }

            function validarPass(pass, repass){
                if(pass.value=="" || pass.value==" "){
                    alert ("Contraseña incorrecta");
                    return false;
                }
                
                if(repass.value=="" || repass.value==" "){
                    alert("Repite la contraseña");
                    return false;
                }
                
                if(repass.value!=pass.value){
                    alert("Las constraseñas no coinciden");
                    return false;
                }
                return true;
            }

            function verificar(nif, login, pass, repass){
            
                if(validarLogin(login) && validarDni(nif) && validarPass(pass,repass)){
                    alert("todo ok");
                    return true;
                } else {
                    alert("mal");
                    return false;
                }
            }

        </script>

        <title>Añadir un usuario</title>
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
                    <h1>
                        Añadir un usuario
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                        <li class="active">Añadir Usuario</li>
                    </ol>
                </section>

                <!-- Main content -->
                <section class="content">
                     <%if(session.getAttribute("msg")!=null){%>
                     <h1><%=session.getAttribute("msg")%></h1>
                     <%}
                     session.setAttribute("msg",null);%>
                    <!-- Main row -->
                    <div class="row">
                        <div class="box box-info">
                            <div class="box-header with-border">
                                <h3 class="box-title">add user</h3>
                            </div><!-- /.box-header -->
                            <!-- form start -->
                            <form class="form-horizontal" action="AddUser?Accion=anadir" method="post">
                                <div class="box-body">
                                    <div class="form-group">
                                        <label for="inputUser3" class="col-sm-2 control-label">Login</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="inputEmail3" name="login" placeholder="Login">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="inputUser3" class="col-sm-2 control-label">NIF</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="inputEmail3" name="nif" placeholder="Nif">
                                            <!-- incluir javascript que compruebe que es un nif-->
                                        </div>
                                    </div>


                                    <div class="form-group">
                                        <label for="inputPassword3" class="col-sm-2 control-label">Password</label>
                                        <div class="col-sm-10">
                                            <input type="password" class="form-control" id="inputPassword3" name="pass" placeholder="Password">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="inputPassword3" class="col-sm-2 control-label">Repetir Password</label>
                                        <div class="col-sm-10">
                                            <input type="password" class="form-control" id="inputEmail3" name="repass" placeholder="RePassword">
                                        </div>
                                    </div>
                                    <div class="form-group"> 
                                        <label  class="col-sm-2 control-label">Tipo de usuario</label>
                                        <select name="rol" class="form-control-static" >

                                            <option class="option-control" value="Desarrollador"> Desarrollador </option>
                                            <option class="option-control" value="Administrador"> Administrador </option>
                                            <option class="option-control" value="Jefe de Proyecto"> Jefe Proyecto </option>

                                        </select>
                                    </div>
                                    <div class="form-group"> 
                                        <label  class="col-sm-2 control-label">Máximo Proyectos</label>
                                        <select name="maxProy" class="form-control-static" >

                                            <option class="option-control" value="1"> 1 </option>
                                            <option class="option-control" value="2"> 2 </option>


                                        </select>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">Información general</label>
                                        <textarea class="form-control-static" rows="3" placeholder="Enter ..." name="informacion"></textarea>
                                    </div>
                                </div><!-- /.box-body -->
                                <div class="box-footer">
                                    <a href="/usuarios.jsp"><button type="button" class="btn btn-default" name = "Accion" value="cancelar">Cancelar</button></a>
                                    <button type="submit" class="btn btn-info pull-right" name="Accion" value="Add" onclick="return verificar(nif, login, pass, repass,Accion)">Add</button>
                                </div><!-- /.box-footer -->
                            </form>
                        </div><!-- /.box -->


                        <!--</section><!-- right col -->
                    </div><!-- /.row (main row) -->

                </section><!-- /.content -->
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


