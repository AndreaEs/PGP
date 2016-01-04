/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author gil
 */
public class Vacaciones {

    private String fechaInicio;
    private String fechaFin;
    private String usuario;

    public Vacaciones() {
        this.fechaInicio = "";
        this.fechaFin = "";
        this.usuario = "";
    }

    public Vacaciones(String fechaInicio, String fechaFin, String usuario) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.usuario = usuario;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public long comprobarDiasVacaciones(List<Vacaciones> cal) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        long diff = 0;

        try {
            for (int i = 0; i < cal.size(); i++) {
                java.util.Date fechaI = formatter.parse(cal.get(i).getFechaInicio());
                java.util.Date fechaF = formatter.parse(cal.get(i).getFechaFin());
                diff = diff + (fechaF.getTime() - fechaI.getTime());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    /*date1.comparetp(date2) > 0 --> date1 esta después de date2
     date1.comparetp(date2) < 0 --> date1 esta antes de date2*/
    public boolean comprobarRangosEntreFechas(String fechaIn, String fechaFi, Vacaciones cal) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date fechaI = formatter.parse(cal.getFechaInicio());
            java.util.Date fechaF = formatter.parse(cal.getFechaFin());
            java.util.Date fechaIA = formatter.parse(fechaIn);
            java.util.Date fechaFA = formatter.parse(fechaFi);
            if(fechaI.compareTo(fechaIA)==0 || fechaI.compareTo(fechaFA)==0)
                return true;
            if(fechaF.compareTo(fechaIA)==0 || fechaF.compareTo(fechaFA)==0)
                return true;
            if (fechaI.compareTo(fechaFA) < 0 && fechaF.compareTo(fechaIA) > 0)
                return true;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

}
