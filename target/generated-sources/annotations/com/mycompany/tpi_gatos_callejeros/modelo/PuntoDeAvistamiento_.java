package com.mycompany.tpi_gatos_callejeros.modelo;

import com.mycompany.tpi_gatos_callejeros.modelo.Voluntario;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2026-02-24T02:51:48", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(PuntoDeAvistamiento.class)
public class PuntoDeAvistamiento_ { 

    public static volatile SingularAttribute<PuntoDeAvistamiento, Long> idAvistamiento;
    public static volatile SingularAttribute<PuntoDeAvistamiento, String> observacionAvistamiento;
    public static volatile SingularAttribute<PuntoDeAvistamiento, Integer> cantidad_gatos;
    public static volatile SingularAttribute<PuntoDeAvistamiento, String> ubicacionAvistamiento;
    public static volatile SingularAttribute<PuntoDeAvistamiento, Voluntario> voluntario;

}