package com.mycompany.tpi_gatos_callejeros.modelo;

import com.mycompany.tpi_gatos_callejeros.modelo.Adopcion;
import com.mycompany.tpi_gatos_callejeros.modelo.VistaDeSeguimiento;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2026-02-24T02:51:48", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(FamiliaAdoptante.class)
public class FamiliaAdoptante_ { 

    public static volatile SingularAttribute<FamiliaAdoptante, Long> idFamilia;
    public static volatile SingularAttribute<FamiliaAdoptante, Boolean> familiaDisponible;
    public static volatile ListAttribute<FamiliaAdoptante, Adopcion> adopciones;
    public static volatile SingularAttribute<FamiliaAdoptante, Double> puntuacion;
    public static volatile SingularAttribute<FamiliaAdoptante, String> tipoFamilia;
    public static volatile ListAttribute<FamiliaAdoptante, VistaDeSeguimiento> visitasDeFamilias;
    public static volatile SingularAttribute<FamiliaAdoptante, String> ubicacionFamilia;

}