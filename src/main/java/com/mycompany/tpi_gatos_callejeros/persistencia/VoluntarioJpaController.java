/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi_gatos_callejeros.persistencia;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.tpi_gatos_callejeros.modelo.CalendarioTarea;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.tpi_gatos_callejeros.modelo.VistaDeSeguimiento;
import com.mycompany.tpi_gatos_callejeros.modelo.PuntoDeAvistamiento;
import com.mycompany.tpi_gatos_callejeros.modelo.TareaRealizada;
import com.mycompany.tpi_gatos_callejeros.modelo.Voluntario;
import com.mycompany.tpi_gatos_callejeros.persistencia.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author alana
 */
public class VoluntarioJpaController implements Serializable {

    public VoluntarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Voluntario voluntario) {
        if (voluntario.getCalendarioDeTareas() == null) {
            voluntario.setCalendarioDeTareas(new ArrayList<CalendarioTarea>());
        }
        if (voluntario.getVisitasAdminVolun() == null) {
            voluntario.setVisitasAdminVolun(new ArrayList<VistaDeSeguimiento>());
        }
        if (voluntario.getPuntosdeAvistamiento() == null) {
            voluntario.setPuntosdeAvistamiento(new ArrayList<PuntoDeAvistamiento>());
        }
        if (voluntario.getTareasRealizadas() == null) {
            voluntario.setTareasRealizadas(new ArrayList<TareaRealizada>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<CalendarioTarea> attachedCalendarioDeTareas = new ArrayList<CalendarioTarea>();
            for (CalendarioTarea calendarioDeTareasCalendarioTareaToAttach : voluntario.getCalendarioDeTareas()) {
                calendarioDeTareasCalendarioTareaToAttach = em.getReference(calendarioDeTareasCalendarioTareaToAttach.getClass(), calendarioDeTareasCalendarioTareaToAttach.getIdCalendario());
                attachedCalendarioDeTareas.add(calendarioDeTareasCalendarioTareaToAttach);
            }
            voluntario.setCalendarioDeTareas(attachedCalendarioDeTareas);
            List<VistaDeSeguimiento> attachedVisitasAdminVolun = new ArrayList<VistaDeSeguimiento>();
            for (VistaDeSeguimiento visitasAdminVolunVistaDeSeguimientoToAttach : voluntario.getVisitasAdminVolun()) {
                visitasAdminVolunVistaDeSeguimientoToAttach = em.getReference(visitasAdminVolunVistaDeSeguimientoToAttach.getClass(), visitasAdminVolunVistaDeSeguimientoToAttach.getIdVisita());
                attachedVisitasAdminVolun.add(visitasAdminVolunVistaDeSeguimientoToAttach);
            }
            voluntario.setVisitasAdminVolun(attachedVisitasAdminVolun);
            List<PuntoDeAvistamiento> attachedPuntosdeAvistamiento = new ArrayList<PuntoDeAvistamiento>();
            for (PuntoDeAvistamiento puntosdeAvistamientoPuntoDeAvistamientoToAttach : voluntario.getPuntosdeAvistamiento()) {
                puntosdeAvistamientoPuntoDeAvistamientoToAttach = em.getReference(puntosdeAvistamientoPuntoDeAvistamientoToAttach.getClass(), puntosdeAvistamientoPuntoDeAvistamientoToAttach.getIdAvistamiento());
                attachedPuntosdeAvistamiento.add(puntosdeAvistamientoPuntoDeAvistamientoToAttach);
            }
            voluntario.setPuntosdeAvistamiento(attachedPuntosdeAvistamiento);
            List<TareaRealizada> attachedTareasRealizadas = new ArrayList<TareaRealizada>();
            for (TareaRealizada tareasRealizadasTareaRealizadaToAttach : voluntario.getTareasRealizadas()) {
                tareasRealizadasTareaRealizadaToAttach = em.getReference(tareasRealizadasTareaRealizadaToAttach.getClass(), tareasRealizadasTareaRealizadaToAttach.getCodTarea());
                attachedTareasRealizadas.add(tareasRealizadasTareaRealizadaToAttach);
            }
            voluntario.setTareasRealizadas(attachedTareasRealizadas);
            em.persist(voluntario);
            for (CalendarioTarea calendarioDeTareasCalendarioTarea : voluntario.getCalendarioDeTareas()) {
                Voluntario oldVoluntarioOfCalendarioDeTareasCalendarioTarea = calendarioDeTareasCalendarioTarea.getVoluntario();
                calendarioDeTareasCalendarioTarea.setVoluntario(voluntario);
                calendarioDeTareasCalendarioTarea = em.merge(calendarioDeTareasCalendarioTarea);
                if (oldVoluntarioOfCalendarioDeTareasCalendarioTarea != null) {
                    oldVoluntarioOfCalendarioDeTareasCalendarioTarea.getCalendarioDeTareas().remove(calendarioDeTareasCalendarioTarea);
                    oldVoluntarioOfCalendarioDeTareasCalendarioTarea = em.merge(oldVoluntarioOfCalendarioDeTareasCalendarioTarea);
                }
            }
            for (VistaDeSeguimiento visitasAdminVolunVistaDeSeguimiento : voluntario.getVisitasAdminVolun()) {
                Voluntario oldVoluntarioOfVisitasAdminVolunVistaDeSeguimiento = visitasAdminVolunVistaDeSeguimiento.getVoluntario();
                visitasAdminVolunVistaDeSeguimiento.setVoluntario(voluntario);
                visitasAdminVolunVistaDeSeguimiento = em.merge(visitasAdminVolunVistaDeSeguimiento);
                if (oldVoluntarioOfVisitasAdminVolunVistaDeSeguimiento != null) {
                    oldVoluntarioOfVisitasAdminVolunVistaDeSeguimiento.getVisitasAdminVolun().remove(visitasAdminVolunVistaDeSeguimiento);
                    oldVoluntarioOfVisitasAdminVolunVistaDeSeguimiento = em.merge(oldVoluntarioOfVisitasAdminVolunVistaDeSeguimiento);
                }
            }
            for (PuntoDeAvistamiento puntosdeAvistamientoPuntoDeAvistamiento : voluntario.getPuntosdeAvistamiento()) {
                Voluntario oldVoluntarioOfPuntosdeAvistamientoPuntoDeAvistamiento = puntosdeAvistamientoPuntoDeAvistamiento.getVoluntario();
                puntosdeAvistamientoPuntoDeAvistamiento.setVoluntario(voluntario);
                puntosdeAvistamientoPuntoDeAvistamiento = em.merge(puntosdeAvistamientoPuntoDeAvistamiento);
                if (oldVoluntarioOfPuntosdeAvistamientoPuntoDeAvistamiento != null) {
                    oldVoluntarioOfPuntosdeAvistamientoPuntoDeAvistamiento.getPuntosdeAvistamiento().remove(puntosdeAvistamientoPuntoDeAvistamiento);
                    oldVoluntarioOfPuntosdeAvistamientoPuntoDeAvistamiento = em.merge(oldVoluntarioOfPuntosdeAvistamientoPuntoDeAvistamiento);
                }
            }
            for (TareaRealizada tareasRealizadasTareaRealizada : voluntario.getTareasRealizadas()) {
                Voluntario oldVoluntarioOfTareasRealizadasTareaRealizada = tareasRealizadasTareaRealizada.getVoluntario();
                tareasRealizadasTareaRealizada.setVoluntario(voluntario);
                tareasRealizadasTareaRealizada = em.merge(tareasRealizadasTareaRealizada);
                if (oldVoluntarioOfTareasRealizadasTareaRealizada != null) {
                    oldVoluntarioOfTareasRealizadasTareaRealizada.getTareasRealizadas().remove(tareasRealizadasTareaRealizada);
                    oldVoluntarioOfTareasRealizadasTareaRealizada = em.merge(oldVoluntarioOfTareasRealizadasTareaRealizada);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Voluntario voluntario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Voluntario persistentVoluntario = em.find(Voluntario.class, voluntario.getIdVoluntario());
            List<CalendarioTarea> calendarioDeTareasOld = persistentVoluntario.getCalendarioDeTareas();
            List<CalendarioTarea> calendarioDeTareasNew = voluntario.getCalendarioDeTareas();
            List<VistaDeSeguimiento> visitasAdminVolunOld = persistentVoluntario.getVisitasAdminVolun();
            List<VistaDeSeguimiento> visitasAdminVolunNew = voluntario.getVisitasAdminVolun();
            List<PuntoDeAvistamiento> puntosdeAvistamientoOld = persistentVoluntario.getPuntosdeAvistamiento();
            List<PuntoDeAvistamiento> puntosdeAvistamientoNew = voluntario.getPuntosdeAvistamiento();
            List<TareaRealizada> tareasRealizadasOld = persistentVoluntario.getTareasRealizadas();
            List<TareaRealizada> tareasRealizadasNew = voluntario.getTareasRealizadas();
            List<CalendarioTarea> attachedCalendarioDeTareasNew = new ArrayList<CalendarioTarea>();
            for (CalendarioTarea calendarioDeTareasNewCalendarioTareaToAttach : calendarioDeTareasNew) {
                calendarioDeTareasNewCalendarioTareaToAttach = em.getReference(calendarioDeTareasNewCalendarioTareaToAttach.getClass(), calendarioDeTareasNewCalendarioTareaToAttach.getIdCalendario());
                attachedCalendarioDeTareasNew.add(calendarioDeTareasNewCalendarioTareaToAttach);
            }
            calendarioDeTareasNew = attachedCalendarioDeTareasNew;
            voluntario.setCalendarioDeTareas(calendarioDeTareasNew);
            List<VistaDeSeguimiento> attachedVisitasAdminVolunNew = new ArrayList<VistaDeSeguimiento>();
            for (VistaDeSeguimiento visitasAdminVolunNewVistaDeSeguimientoToAttach : visitasAdminVolunNew) {
                visitasAdminVolunNewVistaDeSeguimientoToAttach = em.getReference(visitasAdminVolunNewVistaDeSeguimientoToAttach.getClass(), visitasAdminVolunNewVistaDeSeguimientoToAttach.getIdVisita());
                attachedVisitasAdminVolunNew.add(visitasAdminVolunNewVistaDeSeguimientoToAttach);
            }
            visitasAdminVolunNew = attachedVisitasAdminVolunNew;
            voluntario.setVisitasAdminVolun(visitasAdminVolunNew);
            List<PuntoDeAvistamiento> attachedPuntosdeAvistamientoNew = new ArrayList<PuntoDeAvistamiento>();
            for (PuntoDeAvistamiento puntosdeAvistamientoNewPuntoDeAvistamientoToAttach : puntosdeAvistamientoNew) {
                puntosdeAvistamientoNewPuntoDeAvistamientoToAttach = em.getReference(puntosdeAvistamientoNewPuntoDeAvistamientoToAttach.getClass(), puntosdeAvistamientoNewPuntoDeAvistamientoToAttach.getIdAvistamiento());
                attachedPuntosdeAvistamientoNew.add(puntosdeAvistamientoNewPuntoDeAvistamientoToAttach);
            }
            puntosdeAvistamientoNew = attachedPuntosdeAvistamientoNew;
            voluntario.setPuntosdeAvistamiento(puntosdeAvistamientoNew);
            List<TareaRealizada> attachedTareasRealizadasNew = new ArrayList<TareaRealizada>();
            for (TareaRealizada tareasRealizadasNewTareaRealizadaToAttach : tareasRealizadasNew) {
                tareasRealizadasNewTareaRealizadaToAttach = em.getReference(tareasRealizadasNewTareaRealizadaToAttach.getClass(), tareasRealizadasNewTareaRealizadaToAttach.getCodTarea());
                attachedTareasRealizadasNew.add(tareasRealizadasNewTareaRealizadaToAttach);
            }
            tareasRealizadasNew = attachedTareasRealizadasNew;
            voluntario.setTareasRealizadas(tareasRealizadasNew);
            voluntario = em.merge(voluntario);
            for (CalendarioTarea calendarioDeTareasOldCalendarioTarea : calendarioDeTareasOld) {
                if (!calendarioDeTareasNew.contains(calendarioDeTareasOldCalendarioTarea)) {
                    calendarioDeTareasOldCalendarioTarea.setVoluntario(null);
                    calendarioDeTareasOldCalendarioTarea = em.merge(calendarioDeTareasOldCalendarioTarea);
                }
            }
            for (CalendarioTarea calendarioDeTareasNewCalendarioTarea : calendarioDeTareasNew) {
                if (!calendarioDeTareasOld.contains(calendarioDeTareasNewCalendarioTarea)) {
                    Voluntario oldVoluntarioOfCalendarioDeTareasNewCalendarioTarea = calendarioDeTareasNewCalendarioTarea.getVoluntario();
                    calendarioDeTareasNewCalendarioTarea.setVoluntario(voluntario);
                    calendarioDeTareasNewCalendarioTarea = em.merge(calendarioDeTareasNewCalendarioTarea);
                    if (oldVoluntarioOfCalendarioDeTareasNewCalendarioTarea != null && !oldVoluntarioOfCalendarioDeTareasNewCalendarioTarea.equals(voluntario)) {
                        oldVoluntarioOfCalendarioDeTareasNewCalendarioTarea.getCalendarioDeTareas().remove(calendarioDeTareasNewCalendarioTarea);
                        oldVoluntarioOfCalendarioDeTareasNewCalendarioTarea = em.merge(oldVoluntarioOfCalendarioDeTareasNewCalendarioTarea);
                    }
                }
            }
            for (VistaDeSeguimiento visitasAdminVolunOldVistaDeSeguimiento : visitasAdminVolunOld) {
                if (!visitasAdminVolunNew.contains(visitasAdminVolunOldVistaDeSeguimiento)) {
                    visitasAdminVolunOldVistaDeSeguimiento.setVoluntario(null);
                    visitasAdminVolunOldVistaDeSeguimiento = em.merge(visitasAdminVolunOldVistaDeSeguimiento);
                }
            }
            for (VistaDeSeguimiento visitasAdminVolunNewVistaDeSeguimiento : visitasAdminVolunNew) {
                if (!visitasAdminVolunOld.contains(visitasAdminVolunNewVistaDeSeguimiento)) {
                    Voluntario oldVoluntarioOfVisitasAdminVolunNewVistaDeSeguimiento = visitasAdminVolunNewVistaDeSeguimiento.getVoluntario();
                    visitasAdminVolunNewVistaDeSeguimiento.setVoluntario(voluntario);
                    visitasAdminVolunNewVistaDeSeguimiento = em.merge(visitasAdminVolunNewVistaDeSeguimiento);
                    if (oldVoluntarioOfVisitasAdminVolunNewVistaDeSeguimiento != null && !oldVoluntarioOfVisitasAdminVolunNewVistaDeSeguimiento.equals(voluntario)) {
                        oldVoluntarioOfVisitasAdminVolunNewVistaDeSeguimiento.getVisitasAdminVolun().remove(visitasAdminVolunNewVistaDeSeguimiento);
                        oldVoluntarioOfVisitasAdminVolunNewVistaDeSeguimiento = em.merge(oldVoluntarioOfVisitasAdminVolunNewVistaDeSeguimiento);
                    }
                }
            }
            for (PuntoDeAvistamiento puntosdeAvistamientoOldPuntoDeAvistamiento : puntosdeAvistamientoOld) {
                if (!puntosdeAvistamientoNew.contains(puntosdeAvistamientoOldPuntoDeAvistamiento)) {
                    puntosdeAvistamientoOldPuntoDeAvistamiento.setVoluntario(null);
                    puntosdeAvistamientoOldPuntoDeAvistamiento = em.merge(puntosdeAvistamientoOldPuntoDeAvistamiento);
                }
            }
            for (PuntoDeAvistamiento puntosdeAvistamientoNewPuntoDeAvistamiento : puntosdeAvistamientoNew) {
                if (!puntosdeAvistamientoOld.contains(puntosdeAvistamientoNewPuntoDeAvistamiento)) {
                    Voluntario oldVoluntarioOfPuntosdeAvistamientoNewPuntoDeAvistamiento = puntosdeAvistamientoNewPuntoDeAvistamiento.getVoluntario();
                    puntosdeAvistamientoNewPuntoDeAvistamiento.setVoluntario(voluntario);
                    puntosdeAvistamientoNewPuntoDeAvistamiento = em.merge(puntosdeAvistamientoNewPuntoDeAvistamiento);
                    if (oldVoluntarioOfPuntosdeAvistamientoNewPuntoDeAvistamiento != null && !oldVoluntarioOfPuntosdeAvistamientoNewPuntoDeAvistamiento.equals(voluntario)) {
                        oldVoluntarioOfPuntosdeAvistamientoNewPuntoDeAvistamiento.getPuntosdeAvistamiento().remove(puntosdeAvistamientoNewPuntoDeAvistamiento);
                        oldVoluntarioOfPuntosdeAvistamientoNewPuntoDeAvistamiento = em.merge(oldVoluntarioOfPuntosdeAvistamientoNewPuntoDeAvistamiento);
                    }
                }
            }
            for (TareaRealizada tareasRealizadasOldTareaRealizada : tareasRealizadasOld) {
                if (!tareasRealizadasNew.contains(tareasRealizadasOldTareaRealizada)) {
                    tareasRealizadasOldTareaRealizada.setVoluntario(null);
                    tareasRealizadasOldTareaRealizada = em.merge(tareasRealizadasOldTareaRealizada);
                }
            }
            for (TareaRealizada tareasRealizadasNewTareaRealizada : tareasRealizadasNew) {
                if (!tareasRealizadasOld.contains(tareasRealizadasNewTareaRealizada)) {
                    Voluntario oldVoluntarioOfTareasRealizadasNewTareaRealizada = tareasRealizadasNewTareaRealizada.getVoluntario();
                    tareasRealizadasNewTareaRealizada.setVoluntario(voluntario);
                    tareasRealizadasNewTareaRealizada = em.merge(tareasRealizadasNewTareaRealizada);
                    if (oldVoluntarioOfTareasRealizadasNewTareaRealizada != null && !oldVoluntarioOfTareasRealizadasNewTareaRealizada.equals(voluntario)) {
                        oldVoluntarioOfTareasRealizadasNewTareaRealizada.getTareasRealizadas().remove(tareasRealizadasNewTareaRealizada);
                        oldVoluntarioOfTareasRealizadasNewTareaRealizada = em.merge(oldVoluntarioOfTareasRealizadasNewTareaRealizada);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                long id = voluntario.getIdVoluntario();
                if (findVoluntario(id) == null) {
                    throw new NonexistentEntityException("The voluntario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Voluntario voluntario;
            try {
                voluntario = em.getReference(Voluntario.class, id);
                voluntario.getIdVoluntario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The voluntario with id " + id + " no longer exists.", enfe);
            }
            List<CalendarioTarea> calendarioDeTareas = voluntario.getCalendarioDeTareas();
            for (CalendarioTarea calendarioDeTareasCalendarioTarea : calendarioDeTareas) {
                calendarioDeTareasCalendarioTarea.setVoluntario(null);
                calendarioDeTareasCalendarioTarea = em.merge(calendarioDeTareasCalendarioTarea);
            }
            List<VistaDeSeguimiento> visitasAdminVolun = voluntario.getVisitasAdminVolun();
            for (VistaDeSeguimiento visitasAdminVolunVistaDeSeguimiento : visitasAdminVolun) {
                visitasAdminVolunVistaDeSeguimiento.setVoluntario(null);
                visitasAdminVolunVistaDeSeguimiento = em.merge(visitasAdminVolunVistaDeSeguimiento);
            }
            List<PuntoDeAvistamiento> puntosdeAvistamiento = voluntario.getPuntosdeAvistamiento();
            for (PuntoDeAvistamiento puntosdeAvistamientoPuntoDeAvistamiento : puntosdeAvistamiento) {
                puntosdeAvistamientoPuntoDeAvistamiento.setVoluntario(null);
                puntosdeAvistamientoPuntoDeAvistamiento = em.merge(puntosdeAvistamientoPuntoDeAvistamiento);
            }
            List<TareaRealizada> tareasRealizadas = voluntario.getTareasRealizadas();
            for (TareaRealizada tareasRealizadasTareaRealizada : tareasRealizadas) {
                tareasRealizadasTareaRealizada.setVoluntario(null);
                tareasRealizadasTareaRealizada = em.merge(tareasRealizadasTareaRealizada);
            }
            em.remove(voluntario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Voluntario> findVoluntarioEntities() {
        return findVoluntarioEntities(true, -1, -1);
    }

    public List<Voluntario> findVoluntarioEntities(int maxResults, int firstResult) {
        return findVoluntarioEntities(false, maxResults, firstResult);
    }

    private List<Voluntario> findVoluntarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Voluntario.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Voluntario findVoluntario(long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Voluntario.class, id);
        } finally {
            em.close();
        }
    }

    public int getVoluntarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Voluntario> rt = cq.from(Voluntario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
