
package com.mycompany.tpi_gatos_callejeros.modelo;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="voluntario")
public class Voluntario implements Serializable {
   @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long idVoluntario;
   
   
    private String dniVoluntario;
    private String nombreVoluntario;
    private String telefono;
    private int puntuacion;
    
    @OneToMany(mappedBy = "voluntario",cascade = CascadeType.ALL)
    private List<VistaDeSeguimiento> visitasAdminVolun; // las visitas administradas por el voluntario
    
    @OneToMany(mappedBy = "voluntario",cascade = CascadeType.ALL)
    private List<PuntoDeAvistamiento> puntosdeAvistamiento;
    
    @OneToMany(mappedBy = "voluntario",cascade = CascadeType.ALL)
    private List<TareaRealizada> tareasRealizadas;
    
    @OneToMany(mappedBy = "voluntario",cascade = CascadeType.ALL)
    private List<CalendarioTarea> calendarioDeTareas;

    public Voluntario() {
    }

    public Voluntario(String dniVoluntario, String nombreVoluntario, String telefono, int puntuacion) {
        this.idVoluntario = idVoluntario;
        this.dniVoluntario = dniVoluntario;
        this.nombreVoluntario = nombreVoluntario;
        this.telefono = telefono;
        this.puntuacion = puntuacion;
        this.visitasAdminVolun = new ArrayList<VistaDeSeguimiento>();
        this.puntosdeAvistamiento = new ArrayList<PuntoDeAvistamiento>();
        this.tareasRealizadas = new ArrayList<TareaRealizada>();
        this.calendarioDeTareas = new ArrayList<CalendarioTarea>();
    }

    public long getIdVoluntario() {
        return idVoluntario;
    }

    public void setIdVoluntario(long idVoluntario) {
        this.idVoluntario = idVoluntario;
    }

    public String getDniVoluntario() {
        return dniVoluntario;
    }

    public void setDniVoluntario(String dniVoluntario) {
        this.dniVoluntario = dniVoluntario;
    }

    public List<CalendarioTarea> getCalendarioDeTareas() {
        return calendarioDeTareas;
    }

    public void setCalendarioDeTareas(List<CalendarioTarea> calendarioDeTareas) {
        this.calendarioDeTareas = calendarioDeTareas;
    }
    
   
    
    public String getNombreVoluntario() {
        return nombreVoluntario;
    }

    public void setNombreVoluntario(String nombreVoluntario) {
        this.nombreVoluntario = nombreVoluntario;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public List<VistaDeSeguimiento> getVisitasAdminVolun() {
        return visitasAdminVolun;
    }

    public void setVisitasAdminVolun(List<VistaDeSeguimiento> visitasAdminVolun) {
        this.visitasAdminVolun = visitasAdminVolun;
    }

    public List<PuntoDeAvistamiento> getPuntosdeAvistamiento() {
        return puntosdeAvistamiento;
    }

    public void setPuntosdeAvistamiento(List<PuntoDeAvistamiento> puntosdeAvistamiento) {
        this.puntosdeAvistamiento = puntosdeAvistamiento;
    }

    public List<TareaRealizada> getTareasRealizadas() {
        return tareasRealizadas;
    }

    public void setTareasRealizadas(List<TareaRealizada> tareasRealizadas) {
        this.tareasRealizadas = tareasRealizadas;
    }
     
}
