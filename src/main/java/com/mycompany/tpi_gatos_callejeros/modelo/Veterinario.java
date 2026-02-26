
package com.mycompany.tpi_gatos_callejeros.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
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
import javax.persistence.ManyToOne;

@Entity
@Table(name="veterinario")
public class Veterinario implements Serializable {
   @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long idVeterinario;
   
   
    private String dniVeterinario;
    private String nombreVeterinario;
    private String telefono;
    
    @OneToMany(mappedBy = "veterinario",cascade = CascadeType.ALL)
    private List<Tratamiento> tratamientos;

    public Veterinario() {
    }

    public Veterinario(String dniVeterinario, String nombreVeterinario, String telefono) {
        this.dniVeterinario = dniVeterinario;
        this.nombreVeterinario = nombreVeterinario;
        this.telefono = telefono;
        this.tratamientos = new ArrayList<Tratamiento>();
    }

    public long getIdVeterinario() {
        return idVeterinario;
    }

    public void setIdVeterinario(long idVeterinario) {
        this.idVeterinario = idVeterinario;
    }

    public String getDniVeterinario() {
        return dniVeterinario;
    }

    public void setDniVeterinario(String dniVeterinario) {
        this.dniVeterinario = dniVeterinario;
    }

    
    

    public String getNombreVeterinario() {
        return nombreVeterinario;
    }

    public void setNombreVeterinario(String nombreVeterinario) {
        this.nombreVeterinario = nombreVeterinario;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<Tratamiento> getTratamientos() {
        return tratamientos;
    }

    public void setTratamientos(List<Tratamiento> tratamientos) {
        this.tratamientos = tratamientos;
    }
    
     
}
