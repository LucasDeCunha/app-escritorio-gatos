
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="historialmedico")
public class HistorialMedico implements Serializable {
    @Id
   @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long codHistorial;
   
    private String diagnostico;
    private Date fechaDiagnostico;
    
    @OneToOne
    @JoinColumn(name="idGato")
    private Gato gato;
    
    @OneToMany(mappedBy = "historialMedico",cascade = CascadeType.ALL)
    private List<Tratamiento> tratamientos;

    
    
    public HistorialMedico() {
    }
    public HistorialMedico(String diagnostico, Date fechaDiagnostico, Gato gato) {
        this.diagnostico = diagnostico;
        this.fechaDiagnostico = fechaDiagnostico;
        this.gato = gato;
        this.tratamientos = new ArrayList<Tratamiento>();
    }

    public long getCodHistorial() {
        return codHistorial;
    }

    public void setCodHistorial(long codHistorial) {
        this.codHistorial = codHistorial;
    }

    

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public Date getFechaDiagnostico() {
        return fechaDiagnostico;
    }

    public void setFechaDiagnostico(Date fechaDiagnostico) {
        this.fechaDiagnostico = fechaDiagnostico;
    }

    public Gato getGato() {
        return gato;
    }

    public void setGato(Gato gato) {
        this.gato = gato;
    }

    public List<Tratamiento> getTratamientos() {
        return tratamientos;
    }

    public void setTratamientos(List<Tratamiento> tratamientos) {
        this.tratamientos = tratamientos;
    }
    
    
}
