/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
@Table(name="estado")
public class Estado implements Serializable {
   @Id
   @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long idEstado;
    private String estado;
    
    @OneToMany(mappedBy="estado", cascade = CascadeType.ALL)
    private List<Gato> gatos;

    public Estado() {
    }

    public Estado(String estado) {
        this.estado = estado;
        this.gatos = new ArrayList<Gato>();
    }

    public long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(long idEstado) {
        this.idEstado = idEstado;
    }
    

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Gato> getGatos() {
        return gatos;
    }

    public void setGatos(List<Gato> gatos) {
        this.gatos = gatos;
    }
     
}
