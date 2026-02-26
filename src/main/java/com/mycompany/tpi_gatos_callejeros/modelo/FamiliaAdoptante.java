
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
@Table(name="familiaadoptante")
public class FamiliaAdoptante implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long idFamilia;
    
    
    private String ubicacionFamilia;
    private Double puntuacion;
    private String tipoFamilia;
    private boolean familiaDisponible;
    
    @OneToMany(mappedBy = "familiaAdoptante",cascade = CascadeType.ALL)
    private List<VistaDeSeguimiento> visitasDeFamilias;
    
    @OneToMany(mappedBy = "familia",cascade = CascadeType.ALL)
    private List<Adopcion> adopciones;

    public FamiliaAdoptante() {
    }

    public FamiliaAdoptante(String ubicacionFamilia, Double puntuacion, String tipoFamilia, boolean familiaDisponible) {
        this.ubicacionFamilia = ubicacionFamilia;
        this.puntuacion = puntuacion;
        this.tipoFamilia = tipoFamilia;
        this.familiaDisponible = familiaDisponible;
        this.visitasDeFamilias = new ArrayList<VistaDeSeguimiento>();
        this.adopciones = new ArrayList<Adopcion>();
    }

    public long getIdFamilia() {
        return idFamilia;
    }

    public void setIdFamilia(long idFamilia) {
        this.idFamilia = idFamilia;
    }


    public String getUbicacionFamilia() {
        return ubicacionFamilia;
    }

    public void setUbicacionFamilia(String ubicacionFamilia) {
        this.ubicacionFamilia = ubicacionFamilia;
    }

    public Double getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Double puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getTipoFamilia() {
        return tipoFamilia;
    }

    public void setTipoFamilia(String tipoFamilia) {
        this.tipoFamilia = tipoFamilia;
    }

    public boolean isFamiliaDisponible() {
        return familiaDisponible;
    }

    public void setFamiliaDisponible(boolean familiaDisponible) {
        this.familiaDisponible = familiaDisponible;
    }

    public List<VistaDeSeguimiento> getVisitasDeFamilias() {
        return visitasDeFamilias;
    }

    public void setVisitasDeFamilias(List<VistaDeSeguimiento> visitasDeFamilias) {
        this.visitasDeFamilias = visitasDeFamilias;
    }

    public List<Adopcion> getAdopciones() {
        return adopciones;
    }

    public void setAdopciones(List<Adopcion> adopciones) {
        this.adopciones = adopciones;
    }

    
}
