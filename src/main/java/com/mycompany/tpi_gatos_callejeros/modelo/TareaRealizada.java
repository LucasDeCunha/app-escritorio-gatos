
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
@Table(name="tarearealizada")
public class TareaRealizada implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long codTarea;
    
    
    private Date FechaTareaRealizada;
    private LocalTime horaTareaok;
    private String ubicacionTarea;
    
    @ManyToOne
    @JoinColumn(name="idVoluntario")
    private Voluntario voluntario;
    
    @ManyToOne
    @JoinColumn(name="idTipoTarea")
    private TipoTarea tipoTarea;

    public TareaRealizada() {
    }

    public TareaRealizada(Date FechaTareaRealizada, LocalTime horaTareaok, String ubicacionTarea, Voluntario voluntario, TipoTarea tipoTarea) {
        this.FechaTareaRealizada = FechaTareaRealizada;
        this.horaTareaok = horaTareaok;
        this.ubicacionTarea = ubicacionTarea;
        this.voluntario = voluntario;
        this.tipoTarea = tipoTarea;
    }

    public long getCodTarea() {
        return codTarea;
    }

    public void setCodTarea(long codTarea) {
        this.codTarea = codTarea;
    }

    public TipoTarea getTipoTarea() {
        return tipoTarea;
    }

    public void setTipoTarea(TipoTarea tipoTarea) {
        this.tipoTarea = tipoTarea;
    }
    
   

    public Date getFechaTareaRealizada() {
        return FechaTareaRealizada;
    }

    public void setFechaTareaRealizada(Date FechaTareaRealizada) {
        this.FechaTareaRealizada = FechaTareaRealizada;
    }

    public LocalTime getHoraTareaok() {
        return horaTareaok;
    }

    public void setHoraTareaok(LocalTime horaTareaok) {
        this.horaTareaok = horaTareaok;
    }

    public String getUbicacionTarea() {
        return ubicacionTarea;
    }

    public void setUbicacionTarea(String ubicacionTarea) {
        this.ubicacionTarea = ubicacionTarea;
    }

    public Voluntario getVoluntario() {
        return voluntario;
    }

    public void setVoluntario(Voluntario voluntario) {
        this.voluntario = voluntario;
    }
    
}
