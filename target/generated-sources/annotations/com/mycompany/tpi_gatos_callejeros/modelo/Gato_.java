package com.mycompany.tpi_gatos_callejeros.modelo;

import com.mycompany.tpi_gatos_callejeros.modelo.Adopcion;
import com.mycompany.tpi_gatos_callejeros.modelo.Estado;
import com.mycompany.tpi_gatos_callejeros.modelo.HistorialMedico;
import com.mycompany.tpi_gatos_callejeros.modelo.VistaDeSeguimiento;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2026-02-24T02:51:48", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Gato.class)
public class Gato_ { 

    public static volatile SingularAttribute<Gato, Boolean> aptitud;
    public static volatile SingularAttribute<Gato, String> zona;
    public static volatile SingularAttribute<Gato, String> caracteristicas;
    public static volatile SingularAttribute<Gato, String> ubicacion;
    public static volatile SingularAttribute<Gato, Estado> estado;
    public static volatile SingularAttribute<Gato, String> alimentacion;
    public static volatile SingularAttribute<Gato, String> color;
    public static volatile SingularAttribute<Gato, HistorialMedico> historial;
    public static volatile SingularAttribute<Gato, Adopcion> adopcion;
    public static volatile SingularAttribute<Gato, String> nombre;
    public static volatile SingularAttribute<Gato, Long> idGato;
    public static volatile ListAttribute<Gato, VistaDeSeguimiento> visitas;

}