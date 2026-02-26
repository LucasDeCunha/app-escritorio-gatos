package com.mycompany.tpi_gatos_callejeros.modelo;

import com.mycompany.tpi_gatos_callejeros.modelo.TipoTarea;
import com.mycompany.tpi_gatos_callejeros.modelo.Voluntario;
import java.time.LocalTime;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2026-02-24T02:51:48", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(CalendarioTarea.class)
public class CalendarioTarea_ { 

    public static volatile SingularAttribute<CalendarioTarea, String> ubicacion;
    public static volatile SingularAttribute<CalendarioTarea, TipoTarea> tipoTarea;
    public static volatile SingularAttribute<CalendarioTarea, LocalTime> hora;
    public static volatile SingularAttribute<CalendarioTarea, Date> fechaCalendario;
    public static volatile SingularAttribute<CalendarioTarea, Voluntario> voluntario;
    public static volatile SingularAttribute<CalendarioTarea, Long> idCalendario;

}