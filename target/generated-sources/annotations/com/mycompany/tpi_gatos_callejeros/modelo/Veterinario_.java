package com.mycompany.tpi_gatos_callejeros.modelo;

import com.mycompany.tpi_gatos_callejeros.modelo.Tratamiento;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2026-02-24T02:51:48", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Veterinario.class)
public class Veterinario_ { 

    public static volatile SingularAttribute<Veterinario, String> nombreVeterinario;
    public static volatile ListAttribute<Veterinario, Tratamiento> tratamientos;
    public static volatile SingularAttribute<Veterinario, Long> idVeterinario;
    public static volatile SingularAttribute<Veterinario, String> telefono;
    public static volatile SingularAttribute<Veterinario, String> dniVeterinario;

}