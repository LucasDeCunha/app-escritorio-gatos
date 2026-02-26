
package com.mycompany.tpi_gatos_callejeros.modelo;


import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.util.Date;
import javax.persistence.ManyToOne;


@Entity
@Table(name="tratamiento")
public class Tratamiento implements Serializable {
   @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long codTratamiento;
   
    private String nombreTratamiento;
    private String medicamento;
    private Date fechaTratemiento;
    private LocalDate horadelMedicamento;
    
    @ManyToOne
    @JoinColumn(name="idVeterinario")
    private Veterinario veterinario;
    
    @ManyToOne
    @JoinColumn(name="idHistorial")
    private HistorialMedico historialMedico;

    public Tratamiento() {
    }

    public Tratamiento(String nombreTratamiento, String medicamento, Date fechaTratemiento, LocalDate horadelMedicamento, Veterinario veterinario, HistorialMedico historialMedico) {
        this.nombreTratamiento = nombreTratamiento;
        this.medicamento = medicamento;
        this.fechaTratemiento = fechaTratemiento;
        this.horadelMedicamento = horadelMedicamento;
        this.veterinario = veterinario;
        this.historialMedico = historialMedico;
    }

    public long getCodTratamiento() {
        return codTratamiento;
    }

    public void setCodTratamiento(long codTratamiento) {
        this.codTratamiento = codTratamiento;
    }

    

    
    public String getNombreTratamiento() {
        return nombreTratamiento;
    }

    public void setNombreTratamiento(String nombreTratamiento) {
        this.nombreTratamiento = nombreTratamiento;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public Date getFechaTratemiento() {
        return fechaTratemiento;
    }

    public void setFechaTratemiento(Date fechaTratemiento) {
        this.fechaTratemiento = fechaTratemiento;
    }

    public LocalDate getHoradelMedicamento() {
        return horadelMedicamento;
    }

    public void setHoradelMedicamento(LocalDate horadelMedicamento) {
        this.horadelMedicamento = horadelMedicamento;
    }

    public HistorialMedico getHistorialMedico() {
        return historialMedico;
    }

    public void setHistorialMedico(HistorialMedico historialMedico) {
        this.historialMedico = historialMedico;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }
     
}
