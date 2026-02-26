package com.mycompany.tpi_gatos_callejeros.modelo;

import com.mycompany.tpi_gatos_callejeros.modelo.HistorialMedico;
import com.mycompany.tpi_gatos_callejeros.modelo.Veterinario;
import java.time.LocalDate;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2026-02-24T02:51:48", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Tratamiento.class)
public class Tratamiento_ { 

    public static volatile SingularAttribute<Tratamiento, Veterinario> veterinario;
    public static volatile SingularAttribute<Tratamiento, LocalDate> horadelMedicamento;
    public static volatile SingularAttribute<Tratamiento, HistorialMedico> historialMedico;
    public static volatile SingularAttribute<Tratamiento, Date> fechaTratemiento;
    public static volatile SingularAttribute<Tratamiento, String> medicamento;
    public static volatile SingularAttribute<Tratamiento, Long> codTratamiento;
    public static volatile SingularAttribute<Tratamiento, String> nombreTratamiento;

}