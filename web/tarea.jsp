<%@page import="Business.Actividad"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Business.TareaPersonal"%>
<%@page import="Business.Proyecto"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Nueva tarea</title>
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
        <script type="text/javascript">
            function validar() {
                if (validarFecha())
                    return true;
                else {
                    return false;
                }
            }

            function validarFecha() {
                var fecha = document.tarea.fecha.value;
                if (fecha === "" || fecha === " " || fecha.length < 11) {
                    window.alert("Fecha errónea");
                    return false;
                } else {
                    return true;
                }
            }
        </script>
    </head>
    <body class="hold-transition skin-blue sidebar-mini">
        <div class="wrapper">

            <%@include file="navBar.html" %>
            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        Tarea Personal
                        <small>Crear una nueva tarea personal</small>
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                        <li><a href="#">Tareas Personales</a></li>
                        <li class="active">Nueva tarea personal</li>
                    </ol>
                </section>

                <!-- Main project content -->
                <section class="content">

                    <!-- SELECT2 EXAMPLE -->
                    <div class="box box-default">
                        <div class="box-header with-border">
                            <h3 class="box-title">Nueva tarea personal</h3>
                        </div><!-- /.box-header -->
                        <div class="box-body">
                            <div class="row">
                                <div class="col-md-6">
                                    <%
                                        String usuario = (String) session.getAttribute("user");
                                        boolean actualizar = (Boolean) session.getAttribute("actualizar");
                                        int idTarea;
                                        if (!actualizar) {
                                    %>
                                    <form role="form" action="Tareas?tarea=crearTarea&usuario=<%= usuario%>" name="tarea" value="crearTarea" method="post">
                                        <div class="box-body">
                                            <div class="form-group">
                                                <label for="tipoTarea">Tipo de la tarea</label>
                                                <select class="form-control select2" style="width: 100%;" name="tipoTarea">
                                                    <option selected="selected" value="TU">Trato con usuarios</option>
                                                    <option disabled="disabled" value="RE">Reuniones externas</option>
                                                    <option disabled="disabled" value="RI">Reuniones internas</option>
                                                    <option disabled="disabled" value="LD">Lectura de documentacion</option>
                                                    <option disabled="disabled" value="RV">Revision de documentacion</option>
                                                    <option disabled="disabled" value="ED">Elaboracion de documentacion</option>
                                                    <option disabled="disabled" value="DP">Desarrollo de programas</option>
                                                    <option disabled="disabled" value="VP">Verificacion de programas</option>
                                                    <option disabled="disabled" value="FU">Formacion de usuarios</option>
                                                    <option disabled="disabled" value="FA">Formacion de otras actividades</option>
                                                    
                                                </select>
                                            </div>
                                            <!-- Date dd/mm/yyyy -->
                                            <div class="form-group">
                                              <label>Fecha:</label>
                                              <div class="input-group">
                                                <div class="input-group-addon">
                                                  <i class="fa fa-calendar"></i>
                                                </div>
                                                <input type="text" class="form-control" data-inputmask="'alias': 'dd/mm/yyyy'" data-mask>
                                              </div><!-- /.input group -->
                                            </div><!-- /.form group -->
                                            <div class="form-group">
                                          </div><!-- /.form-group -->
                                        </div><!-- /.box-body -->
                                        <div class="box-footer">
                                            <button type="submit" class="btn btn-primary" name="crearTarea" value="crearTarea" onclick="return validar()">Crear Tarea</button>
                                            <a href="vistaTareas.jsp"><button type="button" class="btn btn-default" name="tarea" value="cancelar">Cancelar</button></a>
                                        </div>

                                    </form>
                                    <% } else {
                                        idTarea = (Integer) session.getAttribute("idTarea");
                                        TareaPersonal t = (TareaPersonal) session.getAttribute("tarea");
                                    %> 
                                    <form role="form" action="Tareas?tarea=actualizarTarea&usuario=<%= usuario%>&idTarea=<%= idTarea%>" name="tarea" value="actualizarTarea" method="post">
                                        <div class="box-body">
                                            <div class="form-group">
                                                <label for="tipoTarea">Tipo de la tarea personal:</label>
                                                <select class="form-control select2" style="width: 100%;" name="tipoTarea">
                                                    <option selected="selected" value="TU">Trato con usuarios</option>
                                                    <option disabled="disabled" value="RE">Reuniones externas</option>
                                                    <option disabled="disabled" value="RI">Reuniones internas</option>
                                                    <option disabled="disabled" value="LD">Lectura de documentacion</option>
                                                    <option disabled="disabled" value="RV">Revision de documentacion</option>
                                                    <option disabled="disabled" value="ED">Elaboracion de documentacion</option>
                                                    <option disabled="disabled" value="DP">Desarrollo de programas</option>
                                                    <option disabled="disabled" value="VP">Verificacion de programas</option>
                                                    <option disabled="disabled" value="FU">Formacion de usuarios</option>
                                                    <option disabled="disabled" value="FA">Formacion de otras actividades</option>
                                                    
                                                </select>
                                            </div>
                                             <!-- Date dd/mm/yyyy -->
                                            <div class="form-group">
                                              <label>Fecha:</label>
                                              <div class="input-group">
                                                <div class="input-group-addon">
                                                  <i class="fa fa-calendar"></i>
                                                </div>
                                                <input type="text" class="form-control" data-inputmask="'alias': 'dd/mm/yyyy'" data-mask>
                                              </div><!-- /.input group -->
                                            </div><!-- /.form group -->
                                        </div><!-- /.box-body -->
                                        <div class="box-footer">
                                            <button type="submit" class="btn btn-primary" name="actualizarTarea" value="actualizarTarea" onclick="return validar()">Actualizar tarea</button>
                                            <a href="vistaTareas.jsp"><button type="button" class="btn btn-default" name="tarea" value="cancelar">Cancelar</button></a>
                                        </div>
                                    </form>
                                    <% }%>
                                </div><!-- /.col -->
                            </div><!-- /.row -->
                        </div><!-- /.box-body -->
                    </div><!-- /.box -->

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

        <!-- Page script -->
        <script>
                                                $(function () {
                                                    //Initialize Select2 Elements
                                                    $(".select2").select2();

                                                    //Date range picker
                                                    $('#reservation').daterangepicker();
                                                });
        </script>
    </body>
</html>
