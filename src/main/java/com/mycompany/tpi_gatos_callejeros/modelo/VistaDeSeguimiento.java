
package com.mycompany.tpi_gatos_callejeros.modelo;



import java.io.Serializable;
import java.time.LocalTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.util.Date;
import javax.persistence.ManyToOne;


@Entity
@Table(name="vistadeseguimiento")
public class VistaDeSeguimiento implements Serializable {
   @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long idVisita;
   
   
    private Date fechaVisita;
    private LocalTime horaVisita;
    private String observaciones;
    
    @ManyToOne
    @JoinColumn(name="idGato")
    private Gato gato;
    
    @ManyToOne
    @JoinColumn(name="idFamilia")
    private FamiliaAdoptante familiaAdoptante;
    
    @ManyToOne
    @JoinColumn(name="idVoluntario")
    private Voluntario voluntario;

    public VistaDeSeguimiento() {
    }

    public VistaDeSeguimiento(Date fechaVisita, LocalTime horaVisita, String observaciones, Gato gato, FamiliaAdoptante familiaAdoptante, Voluntario voluntario) {
        this.fechaVisita = fechaVisita;
        this.horaVisita = horaVisita;
        this.observaciones = observaciones;
        this.gato = gato;
        this.familiaAdoptante = familiaAdoptante;
        this.voluntario = voluntario;
    }

    public long getIdVisita() {
        return idVisita;
    }

    public void setIdVisita(long idVisita) {
        this.idVisita = idVisita;
    }

    public Voluntario getVoluntario() {
        return voluntario;
    }

    public void setVoluntario(Voluntario voluntario) {
        this.voluntario = voluntario;
    }


    public Date getFechaVisita() {
        return fechaVisita;
    }

    public void setFechaVisita(Date fechaVisita) {
        this.fechaVisita = fechaVisita;
    }

    public LocalTime getHoraVisita() {
        return horaVisita;
    }

    public void setHoraVisita(LocalTime horaVisita) {
        this.horaVisita = horaVisita;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Gato getGato() {
        return gato;
    }

    public void setGato(Gato gato) {
        this.gato = gato;
    }

    public FamiliaAdoptante getFamiliaAdoptante() {
        return familiaAdoptante;
    }

    public void setFamiliaAdoptante(FamiliaAdoptante familiaAdoptante) {
        this.familiaAdoptante = familiaAdoptante;
    }
     
}
