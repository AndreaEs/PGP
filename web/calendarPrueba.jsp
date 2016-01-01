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
        <script src="plugins/daterangepicker/daterangepicker.js"></script>
        <!-- bootstrap time picker -->
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
                //Date range picker with time picker
                $('#reservationtime').daterangepicker({timePicker: true, timePickerIncrement: 1, format: 'MM/DD/YYYY hh:mm A'});
                $('#calendar').fullCalendar({
                    header: {
                        left: 'prev,next today',
                        center: 'title',
                        right: 'month,agendaWeek,agendaDay'
                    },
                    defaultDate: '2015-12-12',
                    selectable: true,
                    selectHelper: true,
                    select: function (start, end) {
                        var title = prompt('Event Title:');
                        var eventData;
                        if (title) {
                            eventData = {
                                title: title,
                                start: start,
                                end: end
                            }
                            var xhttp = new XMLHttpRequest();
                            xhttp.open("GET", "Prueba?accion=crearEvento&titulo=" + title + "&empieza=" + start.format('DD/MM/YYYY').toString() + "&acaba=" + end.format('DD/MM/YYYY').toString(), true);
                            xhttp.send();

                            $('#calendar').fullCalendar('renderEvent', eventData, true); // stick? = true
                        }
                        $('#calendar').fullCalendar('unselect');
                    },
                    editable: true,
                    eventLimit: true, // allow "more" link when too many events
                    events: [],
                    eventClick: function (event) {
                        var antiguoTitulo = event.title;
                        event.title = prompt('Change Event Title:');
                        $('#calendar').fullCalendar('updateEvent', event);
                        var xhttp = new XMLHttpRequest();
                        xhttp.open("GET", "Prueba?accion=actualizarTitulo&titulo=" + event.title + "&empieza=" + event.start.format('DD/MM/YYYY').toString() + "&acaba=" + event.end.format('DD/MM/YYYY').toString() + "&antiguoTitulo=" + antiguoTitulo, true);
                        xhttp.send();

                    },
                    eventDrop: function (event, delta, revertFunc) {
                        alert(event.title + " was dropped on " + event.start.format('DD/MM/YYYY'));
                        if (!confirm("Are you sure about this change?")) {
                            revertFunc();
                        }
                        var xhttp = new XMLHttpRequest();
                        xhttp.open("GET", "Prueba?accion=actualizarFecha&titulo=" + event.title + "&empieza=" + event.start.format('DD/MM/YYYY').toString() + "&acaba=" + event.end.format('DD/MM/YYYY').toString(), true);
                        xhttp.send();
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
                $('#calendar').fullCalendar('addEventSource', cars)
            });
        </script>
    </body>
</html>
