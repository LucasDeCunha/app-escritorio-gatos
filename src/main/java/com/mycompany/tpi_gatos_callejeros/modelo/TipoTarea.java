
package com.mycompany.tpi_gatos_callejeros.modelo;


import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="tipotarea")
public class TipoTarea implements Serializable {
   @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long codTarea;
   
    private String ubicacionTarea;
    private Date fechaTarea;
    private LocalTime horaTarea;
    
    @OneToMany(mappedBy="tipoTarea",cascade = CascadeType.ALL)
    private List<TareaRealizada> tareasRealizadas;
    
    @OneToMany(mappedBy = "tipoTarea",cascade = CascadeType.ALL)
    private List<CalendarioTarea> calendarioTareas;

    public TipoTarea() {
    }

    public TipoTarea(String ubicacionTarea, Date fechaTarea, LocalTime horaTarea) {
        this.ubicacionTarea = ubicacionTarea;
        this.fechaTarea = fechaTarea;
        this.horaTarea = horaTarea;
        this.tareasRealizadas = new ArrayList<TareaRealizada>();
        this.calendarioTareas = new ArrayList<CalendarioTarea>();
    }

    public long getCodTarea() {
        return codTarea;
    }

    public void setCodTarea(long codTarea) {
        this.codTarea = codTarea;
    }

    

    public String getUbicacionTarea() {
        return ubicacionTarea;
    }

    public void setUbicacionTarea(String ubicacionTarea) {
        this.ubicacionTarea = ubicacionTarea;
    }

    public Date getFechaTarea() {
        return fechaTarea;
    }

    public void setFechaTarea(Date fechaTarea) {
        this.fechaTarea = fechaTarea;
    }

    public LocalTime getHoraTarea() {
        return horaTarea;
    }

    public void setHoraTarea(LocalTime horaTarea) {
        this.horaTarea = horaTarea;
    }

    public List<TareaRealizada> getTareasRealizadas() {
        return tareasRealizadas;
    }

    public void setTareasRealizadas(List<TareaRealizada> tareasRealizadas) {
        this.tareasRealizadas = tareasRealizadas;
    }

    public List<CalendarioTarea> getCalendarioTareas() {
        return calendarioTareas;
    }

    public void setCalendarioTareas(List<CalendarioTarea> calendarioTareas) {
        this.calendarioTareas = calendarioTareas;
    }
    
     
}
