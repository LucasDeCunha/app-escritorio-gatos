
package com.mycompany.tpi_gatos_callejeros.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="gato")
public class Gato implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long idGato;
    // codigoQR: no se como se implementaria
    // foto: ver como se implementa
    private String nombre;
    private String color;
    private String caracteristicas;
    private String zona;
    private String ubicacion;
    private String alimentacion;
    private boolean aptitud;
    
    @OneToOne(mappedBy = "gato", cascade = CascadeType.ALL)
    private Adopcion adopcion;
    
    @ManyToOne
    @JoinColumn(name="idEstado")
    private Estado estado;
    
    @OneToOne(mappedBy = "gato", cascade = CascadeType.ALL)
    private HistorialMedico historial;
    
    @OneToMany(mappedBy="gato",cascade = CascadeType.ALL)
    private List<VistaDeSeguimiento> visitas;

    public Gato() {
    }

    public Gato(String nombre, String color, String caracteristicas, String zona, String ubicacion, String alimentacion, boolean aptitud, Adopcion adopcion, Estado estado, HistorialMedico historial) {
        this.nombre = nombre;
        this.color = color;
        this.caracteristicas = caracteristicas;
        this.zona = zona;
        this.ubicacion = ubicacion;
        this.alimentacion = alimentacion;
        this.aptitud = aptitud;
        this.adopcion = adopcion;
        this.estado = estado;
        this.historial = historial;
        this.visitas = new ArrayList<VistaDeSeguimiento>();
    }

    
    public Adopcion getAdopcion() {
        return adopcion;
    }

    public void setAdopcion(Adopcion adopcion) {
        this.adopcion = adopcion;
    }

    
    public long getIdGato() {
        return idGato;
    }

    public void setIdGato(long idGato) {
        this.idGato = idGato;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getAlimentacion() {
        return alimentacion;
    }

    public void setAlimentacion(String alimentacion) {
        this.alimentacion = alimentacion;
    }

    public boolean isAptitud() {
        return aptitud;
    }

    public void setAptitud(boolean aptitud) {
        this.aptitud = aptitud;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public HistorialMedico getHistorial() {
        return historial;
    }

    public void setHistorial(HistorialMedico historial) {
        this.historial = historial;
    }

    public List<VistaDeSeguimiento> getVisitas() {
        return visitas;
    }

    public void setVisitas(List<VistaDeSeguimiento> visitas) {
        this.visitas = visitas;
    }
    
    
}
