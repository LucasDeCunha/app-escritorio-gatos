package com.mycompany.tpi_gatos_callejeros.modelo;

import com.mycompany.tpi_gatos_callejeros.modelo.TipoTarea;
import com.mycompany.tpi_gatos_callejeros.modelo.Voluntario;
import java.time.LocalTime;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2026-02-24T02:51:48", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(TareaRealizada.class)
public class TareaRealizada_ { 

    public static volatile SingularAttribute<TareaRealizada, Date> FechaTareaRealizada;
    public static volatile SingularAttribute<TareaRealizada, String> ubicacionTarea;
    public static volatile SingularAttribute<TareaRealizada, TipoTarea> tipoTarea;
    public static volatile SingularAttribute<TareaRealizada, Long> codTarea;
    public static volatile SingularAttribute<TareaRealizada, LocalTime> horaTareaok;
    public static volatile SingularAttribute<TareaRealizada, Voluntario> voluntario;

}