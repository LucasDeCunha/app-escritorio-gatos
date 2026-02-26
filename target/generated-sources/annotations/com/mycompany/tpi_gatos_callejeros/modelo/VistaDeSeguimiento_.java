package com.mycompany.tpi_gatos_callejeros.modelo;

import com.mycompany.tpi_gatos_callejeros.modelo.FamiliaAdoptante;
import com.mycompany.tpi_gatos_callejeros.modelo.Gato;
import com.mycompany.tpi_gatos_callejeros.modelo.Voluntario;
import java.time.LocalTime;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2026-02-24T02:51:48", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(VistaDeSeguimiento.class)
public class VistaDeSeguimiento_ { 

    public static volatile SingularAttribute<VistaDeSeguimiento, Gato> gato;
    public static volatile SingularAttribute<VistaDeSeguimiento, Date> fechaVisita;
    public static volatile SingularAttribute<VistaDeSeguimiento, String> observaciones;
    public static volatile SingularAttribute<VistaDeSeguimiento, FamiliaAdoptante> familiaAdoptante;
    public static volatile SingularAttribute<VistaDeSeguimiento, Long> idVisita;
    public static volatile SingularAttribute<VistaDeSeguimiento, Voluntario> voluntario;
    public static volatile SingularAttribute<VistaDeSeguimiento, LocalTime> horaVisita;

}