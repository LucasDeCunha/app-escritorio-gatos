
package com.mycompany.tpi_gatos_callejeros.modelo;


import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="puntodeavistamiento")
public class PuntoDeAvistamiento implements Serializable {
   @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long idAvistamiento;
   
   
    private String ubicacionAvistamiento;
    private int  cantidad_gatos;
    private String observacionAvistamiento;
    
    @ManyToOne
    @JoinColumn(name="idVoluntario")
    private Voluntario voluntario;

    public PuntoDeAvistamiento() {
    }

    public PuntoDeAvistamiento(String ubicacionAvistamiento, int cantidad_gatos, String observacionAvistamiento, Voluntario voluntario) {
        this.ubicacionAvistamiento = ubicacionAvistamiento;
        this.cantidad_gatos = cantidad_gatos;
        this.observacionAvistamiento = observacionAvistamiento;
        this.voluntario = voluntario;
    }

    public long getIdAvistamiento() {
        return idAvistamiento;
    }

    public void setIdAvistamiento(long idAvistamiento) {
        this.idAvistamiento = idAvistamiento;
    }

    public Voluntario getVoluntario() {
        return voluntario;
    }

    public void setVoluntario(Voluntario voluntario) {
        this.voluntario = voluntario;
    }
    
    
    public String getUbicacionAvistamiento() {
        return ubicacionAvistamiento;
    }

    public void setUbicacionAvistamiento(String ubicacionAvistamiento) {
        this.ubicacionAvistamiento = ubicacionAvistamiento;
    }

    public int getCantidad_gatos() {
        return cantidad_gatos;
    }

    public void setCantidad_gatos(int cantidad_gatos) {
        this.cantidad_gatos = cantidad_gatos;
    }

    public String getObservacionAvistamiento() {
        return observacionAvistamiento;
    }

    public void setObservacionAvistamiento(String observacionAvistamiento) {
        this.observacionAvistamiento = observacionAvistamiento;
    }
    
     
}
