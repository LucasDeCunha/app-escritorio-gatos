package com.mycompany.tpi_gatos_callejeros.modelo;

import com.mycompany.tpi_gatos_callejeros.modelo.CalendarioTarea;
import com.mycompany.tpi_gatos_callejeros.modelo.TareaRealizada;
import java.time.LocalTime;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2026-02-24T02:51:48", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(TipoTarea.class)
public class TipoTarea_ { 

    public static volatile ListAttribute<TipoTarea, TareaRealizada> tareasRealizadas;
    public static volatile SingularAttribute<TipoTarea, String> ubicacionTarea;
    public static volatile SingularAttribute<TipoTarea, LocalTime> horaTarea;
    public static volatile SingularAttribute<TipoTarea, Long> codTarea;
    public static volatile SingularAttribute<TipoTarea, Date> fechaTarea;
    public static volatile ListAttribute<TipoTarea, CalendarioTarea> calendarioTareas;

}