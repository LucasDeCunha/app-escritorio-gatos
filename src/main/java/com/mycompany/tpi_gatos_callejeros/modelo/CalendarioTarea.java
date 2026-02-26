
package com.mycompany.tpi_gatos_callejeros.modelo;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="calendariotarea")
public class CalendarioTarea implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long idCalendario;
    private Date fechaCalendario;
    private LocalTime hora;
    private String ubicacion;
    
    @ManyToOne
    @JoinColumn(name="idVoluntario")
    private Voluntario voluntario;
    
    @ManyToOne
    @JoinColumn(name="idTipoTarea")
    private TipoTarea tipoTarea;

    public CalendarioTarea() {
    }

    public CalendarioTarea(Date fechaCalendario, LocalTime hora, String ubicacion, Voluntario voluntario, TipoTarea tipoTarea) {
        this.fechaCalendario = fechaCalendario;
        this.hora = hora;
        this.ubicacion = ubicacion;
        this.voluntario = voluntario;
        this.tipoTarea = tipoTarea;
    }

    public long getIdCalendario() {
        return idCalendario;
    }

    public void setIdCalendario(long idCalendario) {
        this.idCalendario = idCalendario;
    }

    public TipoTarea getTipoTarea() {
        return tipoTarea;
    }

    public void setTipoTarea(TipoTarea tipoTarea) {
        this.tipoTarea = tipoTarea;
    }


    public Date getFechaCalendario() {
        return fechaCalendario;
    }

    public void setFechaCalendario(Date fechaCalendario) {
        this.fechaCalendario = fechaCalendario;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Voluntario getVoluntario() {
        return voluntario;
    }

    public void setVoluntario(Voluntario voluntario) {
        this.voluntario = voluntario;
    }
    
    
}
