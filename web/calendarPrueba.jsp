<%-- 
    Document   : calendarPrueba
    Created on : 31-dic-2015, 14:06:25
    Author     : andreaescribano
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>AdminLTE 2 | Calendar</title>
        <!-- Tell the browser to be responsive to screen width -->
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <!-- Bootstrap 3.3.5 -->
        <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
        <!-- fullCalendar 2.2.5-->
        <link rel="stylesheet" href="plugins/fullcalendar/fullcalendar.min.css">
        <link rel="stylesheet" href="plugins/fullcalendar/fullcalendar.print.css" media="print">
        <!-- daterange picker -->
        <link rel="stylesheet" href="plugins/daterangepicker/daterangepicker-bs3.css">

        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
        <!-- Ionicons -->
        <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
        <!-- daterange picker -->
        <link rel="stylesheet" href="plugins/daterangepicker/daterangepicker-bs3.css">
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
        <link rel="stylesheet" href="https://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
        <script src="https://code.jquery.com/jquery-1.10.2.js"></script>
        <script src="https://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
        <style>
            body { font-size: 62.5%; }
            label, input { display:block; }
            input.text { margin-bottom:12px; width:95%; padding: .4em; }
            fieldset { padding:0; border:0; margin-top:25px; }
            h1 { font-size: 1.2em; margin: .6em 0; }
            div#users-contain { width: 350px; margin: 20px 0; }
            div#users-contain table { margin: 1em 0; border-collapse: collapse; width: 100%; }
            div#users-contain table td, div#users-contain table th { border: 1px solid #eee; padding: .6em 10px; text-align: left; }
            .ui-dialog .ui-state-error { padding: .3em; }
            .validateTips { border: 1px solid transparent; padding: 0.3em; }
        </style>
    </head>
    <body class="hold-transition skin-blue sidebar-mini">
        <div class="wrapper">
            <%@include file="navBar.html" %>
            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        Calendar
                        <small>Control panel</small>
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                        <li class="active">Calendar</li>
                    </ol>
                </section>
                <!-- Main content -->
                <section class="content">
                    <div class="row">
                        <div class="col-md-9">
                            <div class="box box-primary">
                                <div class="box-body no-padding">
                                    <div class="box-header with-border">
                                        <h3 class="box-title">Calendario</h3>
                                    </div>
                                    <!-- THE CALENDAR -->
                                    <div id="calendar"></div>
                                    <div id="dialog-form" title="Create new user">
                                        <p class="validateTips">All form fields are required.</p>

                                        <form>
                                            <fieldset>
                                                <label for="titulo">Titulo</label>
                                                <input type="text" name="title" id="title" class="text ui-widget-content ui-corner-all" placeholder="Título del evento">
                                                <label for="tE" >Tipo de evento</label>
                                                <select class="text ui-widget-content ui-corner-all" style="width: 100%;" name="tipoEvento" id="tipoEvento">
                                                    <option value="V"> Vacaciones </option>
                                                    <option value="T"> Tarea personal </option>
                                                </select>
                                                <label for="tT" >Tipo de tarea</label>
                                                <select class="text ui-widget-content ui-corner-all" style="width: 100%;" name="tipoTarea" id="tipoTarea">
                                                    <option value="TU"> Trato con Usuarios </option>
                                                    <option value="RE"> Reuniones Externas </option>
                                                    <option value="RI"> Reuniones Internas </option>
                                                    <option value="LD"> Lectura de Documentación </option>
                                                    <option value="RV"> Revisión de documentación </option>
                                                    <option value="ED"> Elaboración de documentación </option>
                                                    <option value="DP"> Desarrollo de Programas </option>
                                                    <option value="VP"> Verificación de Programas </option>
                                                    <option value="FU"> Formación de Usuarios </option>
                                                    <option value="FA"> Formación de Otras Actividades </option>
                                                </select>
                                                <label for="duracion">Duración</label>
                                                <input type="number" name="duracion" id="duracion" class="text ui-widget-content ui-corner-all">
                                                <input type="hidden" id="apptStartTime"/>
                                                <input type="date" id="apptEndTime"/>
                                                <input type="submit" tabindex="-1" style="position:absolute; top:-1000px">

                                            </fieldset>
                                        </form>
                                    </div>
                                </div><!-- /.box-body -->
                            </div><!-- /. box -->
                        </div><!-- /.col -->
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
        <!-- InputMask -->
        <script src="plugins/input-mask/jquery.inputmask.js"></script>
        <script src="plugins/input-mask/jquery.inputmask.date.extensions.js"></script>
        <script src="plugins/input-mask/jquery.inputmask.extensions.js"></script>
        <!-- date-range-picker -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.10.2/moment.min.js"></script>
        <script src="plugins/daterangepicker/daterangepicker.js"></script>        <!-- bootstrap time picker -->
        <script src="plugins/timepicker/bootstrap-timepicker.min.js"></script>
        <!-- jQuery UI 1.11.4 -->
        <script src="https://code.jquery.com/ui/1.11.4/jquery-ui.min.js"></script>
        <!-- Slimscroll -->
        <script src="plugins/slimScroll/jquery.slimscroll.min.js"></script>
        <!-- FastClick -->
        <script src="plugins/fastclick/fastclick.min.js"></script>
        <!-- AdminLTE App -->
        <script src="dist/js/app.min.js"></script>
        <!-- AdminLTE for demo purposes -->
        <script src="dist/js/demo.js"></script>
        <!-- fullCalendar 2.2.5 -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.10.2/moment.min.js"></script>
        <script src="plugins/fullcalendar/fullcalendar.min.js"></script>
        <!-- Page specific script -->
        <script>
            $(function () {
                var dialog, form,
                        // From http://www.whatwg.org/specs/web-apps/current-work/multipage/states-of-the-type-attribute.html#e-mail-state-%28type=email%29
                        title = $("#title"),
                        tipoEvento = $("#tipoEvento"),
                        tipoTarea = $("#tipoTarea"),
                        duracion = $("#duracion"),
                        allFields = $([]).add(title).add(tipoEvento).add(tipoTarea).add(duracion);
                        
                $('#apptEndTime').daterangepicker({singleDatePicker: true, format: 'ddd MMM DD YYYY 00:00:00 [GMT]ZZ'});
                function crearEvento() {
                    var valid = true;
                    allFields.removeClass("ui-state-error");
                    if (valid) {
                        $("#calendar").fullCalendar('renderEvent',
                                {
                                    title: $('#title').val(),
                                    start: $('#apptStartTime').val(),
                                    end: $('#apptEndTime').val(),
                                    editable: true
                                },
                        true);
                        dialog.dialog("close");
                    }
                    return valid;

                }
                dialog = $("#dialog-form").dialog({
                    autoOpen: false,
                    height: 300,
                    width: 350,
                    modal: true,
                    buttons: {
                        "Create an account": crearEvento,
                        Cancel: function () {
                            dialog.dialog("close");
                        }
                    },
                    close: function () {
                        form[ 0 ].reset();
                        allFields.removeClass("ui-state-error");
                    }
                });
                form = dialog.find("form").on("submit", function (event) {
                    event.preventDefault();
                    crearEvento();
                });
                $('#calendar').fullCalendar({
                    header: {
                        left: 'prev,next today',
                        center: 'title',
                        right: 'month,agendaWeek,agendaDay'
                    },
                    selectable: true,
                    selectHelper: true,
                    select: function (start, end) {
                        $('#dialog-form #apptStartTime').val(start);
                        $('#dialog-form #apptEndTime').val(end);
                        dialog.dialog("open");
                        $('#calendar').fullCalendar('unselect');
                    },
                    editable: true,
                    eventLimit: true, // allow "more" link when too many events
                    events: [],
                    eventDrop: function (event, delta, revertFunc) {
                        alert(event.title + " was dropped on " + event.start.format('DD/MM/YYYY'));
                        if (!confirm("Are you sure about this change?")) {
                            revertFunc();
                        }
                        var xhttp = new XMLHttpRequest();
                        xhttp.open("GET", "Prueba?accion=actualizarFecha&titulo=" + event.title + "&empieza=" + event.start.format('DD/MM/YYYY').toString() + "&acaba=" + event.end.format('DD/MM/YYYY').toString(), true);
                        xhttp.send();
                    },
                    eventResize: function (event, delta, revertFunc) {

                        alert(event.title + " end is now " + event.end.format());

                        if (!confirm("is this okay?")) {
                            revertFunc();
                        }

                    }
                });
                var cars = [
                    {
                        title: 'Long Event',
                        start: '2015-12-07',
                        end: '2015-12-10'
                    },
                    {
                        id: 999,
                        title: 'Repeating Event',
                        start: '2015-12-09T16:00:00'
                    },
                    {
                        id: 999,
                        title: 'Repeating Event',
                        start: '2015-12-16T16:00:00'
                    },
                    {
                        title: 'Conference',
                        start: '2015-12-11',
                        end: '2015-12-13'
                    },
                    {
                        title: 'Meeting',
                        start: '2015-12-12T10:30:00',
                        end: '2015-12-12T12:30:00'
                    },
                    {
                        title: 'Lunch',
                        start: '2015-12-12T12:00:00'
                    },
                    {
                        title: 'Meeting',
                        start: '2015-12-12T14:30:00'
                    },
                    {
                        title: 'Happy Hour',
                        start: '2015-12-12T17:30:00'
                    },
                    {
                        title: 'Dinner',
                        start: '2015-12-12T20:00:00'
                    },
                    {
                        title: 'Birthday Party',
                        start: '2015-12-13T07:00:00'
                    },
                    {
                        title: 'Click for Google',
                        url: 'http://google.com/',
                        start: '2015-12-28'
                    }];
                $('#calendar').fullCalendar('addEventSource', cars);

            });
        </script>
    </body>
</html>
