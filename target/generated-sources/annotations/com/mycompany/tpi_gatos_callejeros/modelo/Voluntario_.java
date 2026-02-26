package com.mycompany.tpi_gatos_callejeros.modelo;

import com.mycompany.tpi_gatos_callejeros.modelo.CalendarioTarea;
import com.mycompany.tpi_gatos_callejeros.modelo.PuntoDeAvistamiento;
import com.mycompany.tpi_gatos_callejeros.modelo.TareaRealizada;
import com.mycompany.tpi_gatos_callejeros.modelo.VistaDeSeguimiento;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2026-02-24T02:51:48", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Voluntario.class)
public class Voluntario_ { 

    public static volatile ListAttribute<Voluntario, TareaRealizada> tareasRealizadas;
    public static volatile SingularAttribute<Voluntario, Long> idVoluntario;
    public static volatile ListAttribute<Voluntario, VistaDeSeguimiento> visitasAdminVolun;
    public static volatile SingularAttribute<Voluntario, Integer> puntuacion;
    public static volatile SingularAttribute<Voluntario, String> dniVoluntario;
    public static volatile SingularAttribute<Voluntario, String> nombreVoluntario;
    public static volatile ListAttribute<Voluntario, PuntoDeAvistamiento> puntosdeAvistamiento;
    public static volatile SingularAttribute<Voluntario, String> telefono;
    public static volatile ListAttribute<Voluntario, CalendarioTarea> calendarioDeTareas;

}