package com.mycompany.tpi_gatos_callejeros.modelo;

import com.mycompany.tpi_gatos_callejeros.modelo.Gato;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2026-02-24T02:51:48", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Estado.class)
public class Estado_ { 

    public static volatile SingularAttribute<Estado, Long> idEstado;
    public static volatile SingularAttribute<Estado, String> estado;
    public static volatile ListAttribute<Estado, Gato> gatos;

}