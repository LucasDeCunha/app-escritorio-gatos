package com.mycompany.tpi_gatos_callejeros.modelo;

import com.mycompany.tpi_gatos_callejeros.modelo.Gato;
import com.mycompany.tpi_gatos_callejeros.modelo.Tratamiento;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2026-02-24T02:51:48", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(HistorialMedico.class)
public class HistorialMedico_ { 

    public static volatile SingularAttribute<HistorialMedico, Long> codHistorial;
    public static volatile SingularAttribute<HistorialMedico, Gato> gato;
    public static volatile SingularAttribute<HistorialMedico, String> diagnostico;
    public static volatile ListAttribute<HistorialMedico, Tratamiento> tratamientos;
    public static volatile SingularAttribute<HistorialMedico, Date> fechaDiagnostico;

}