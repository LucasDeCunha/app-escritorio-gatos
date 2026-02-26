
package com.mycompany.tpi_gatos_callejeros.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="adopcion")
public class Adopcion implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long idAdopcion;
    
    private Date fecha;
    private String observaciones;
    
    @OneToOne
    @JoinColumn(name = "idGato")
    private Gato gato;
    
    @ManyToOne
    @JoinColumn(name="idFamilia")
    private FamiliaAdoptante familiaAdoptante;

    
    public Adopcion() {
    }

    public Adopcion(Date fecha, String observaciones, Gato gato, FamiliaAdoptante familiaAdoptante) {
        this.fecha = fecha;
        this.observaciones = observaciones;
        this.gato = gato;
        this.familiaAdoptante = familiaAdoptante;
    }

    public long getIdAdopcion() {
        return idAdopcion;
    }

    public void setIdAdopcion(long idAdopcion) {
        this.idAdopcion = idAdopcion;
    }

    
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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
