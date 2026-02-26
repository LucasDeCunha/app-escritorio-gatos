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
import com.mycompany.tpi_gatos_callejeros.modelo.TareaRealizada;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.tpi_gatos_callejeros.modelo.CalendarioTarea;
import com.mycompany.tpi_gatos_callejeros.modelo.TipoTarea;
import com.mycompany.tpi_gatos_callejeros.persistencia.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author alana
 */
public class TipoTareaJpaController implements Serializable {

    public TipoTareaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoTarea tipoTarea) {
        if (tipoTarea.getTareasRealizadas() == null) {
            tipoTarea.setTareasRealizadas(new ArrayList<TareaRealizada>());
        }
        if (tipoTarea.getCalendarioTareas() == null) {
            tipoTarea.setCalendarioTareas(new ArrayList<CalendarioTarea>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<TareaRealizada> attachedTareasRealizadas = new ArrayList<TareaRealizada>();
            for (TareaRealizada tareasRealizadasTareaRealizadaToAttach : tipoTarea.getTareasRealizadas()) {
                tareasRealizadasTareaRealizadaToAttach = em.getReference(tareasRealizadasTareaRealizadaToAttach.getClass(), tareasRealizadasTareaRealizadaToAttach.getCodTarea());
                attachedTareasRealizadas.add(tareasRealizadasTareaRealizadaToAttach);
            }
            tipoTarea.setTareasRealizadas(attachedTareasRealizadas);
            List<CalendarioTarea> attachedCalendarioTareas = new ArrayList<CalendarioTarea>();
            for (CalendarioTarea calendarioTareasCalendarioTareaToAttach : tipoTarea.getCalendarioTareas()) {
                calendarioTareasCalendarioTareaToAttach = em.getReference(calendarioTareasCalendarioTareaToAttach.getClass(), calendarioTareasCalendarioTareaToAttach.getIdCalendario());
                attachedCalendarioTareas.add(calendarioTareasCalendarioTareaToAttach);
            }
            tipoTarea.setCalendarioTareas(attachedCalendarioTareas);
            em.persist(tipoTarea);
            for (TareaRealizada tareasRealizadasTareaRealizada : tipoTarea.getTareasRealizadas()) {
                TipoTarea oldTipoTareaOfTareasRealizadasTareaRealizada = tareasRealizadasTareaRealizada.getTipoTarea();
                tareasRealizadasTareaRealizada.setTipoTarea(tipoTarea);
                tareasRealizadasTareaRealizada = em.merge(tareasRealizadasTareaRealizada);
                if (oldTipoTareaOfTareasRealizadasTareaRealizada != null) {
                    oldTipoTareaOfTareasRealizadasTareaRealizada.getTareasRealizadas().remove(tareasRealizadasTareaRealizada);
                    oldTipoTareaOfTareasRealizadasTareaRealizada = em.merge(oldTipoTareaOfTareasRealizadasTareaRealizada);
                }
            }
            for (CalendarioTarea calendarioTareasCalendarioTarea : tipoTarea.getCalendarioTareas()) {
                TipoTarea oldTipoTareaOfCalendarioTareasCalendarioTarea = calendarioTareasCalendarioTarea.getTipoTarea();
                calendarioTareasCalendarioTarea.setTipoTarea(tipoTarea);
                calendarioTareasCalendarioTarea = em.merge(calendarioTareasCalendarioTarea);
                if (oldTipoTareaOfCalendarioTareasCalendarioTarea != null) {
                    oldTipoTareaOfCalendarioTareasCalendarioTarea.getCalendarioTareas().remove(calendarioTareasCalendarioTarea);
                    oldTipoTareaOfCalendarioTareasCalendarioTarea = em.merge(oldTipoTareaOfCalendarioTareasCalendarioTarea);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoTarea tipoTarea) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoTarea persistentTipoTarea = em.find(TipoTarea.class, tipoTarea.getCodTarea());
            List<TareaRealizada> tareasRealizadasOld = persistentTipoTarea.getTareasRealizadas();
            List<TareaRealizada> tareasRealizadasNew = tipoTarea.getTareasRealizadas();
            List<CalendarioTarea> calendarioTareasOld = persistentTipoTarea.getCalendarioTareas();
            List<CalendarioTarea> calendarioTareasNew = tipoTarea.getCalendarioTareas();
            List<TareaRealizada> attachedTareasRealizadasNew = new ArrayList<TareaRealizada>();
            for (TareaRealizada tareasRealizadasNewTareaRealizadaToAttach : tareasRealizadasNew) {
                tareasRealizadasNewTareaRealizadaToAttach = em.getReference(tareasRealizadasNewTareaRealizadaToAttach.getClass(), tareasRealizadasNewTareaRealizadaToAttach.getCodTarea());
                attachedTareasRealizadasNew.add(tareasRealizadasNewTareaRealizadaToAttach);
            }
            tareasRealizadasNew = attachedTareasRealizadasNew;
            tipoTarea.setTareasRealizadas(tareasRealizadasNew);
            List<CalendarioTarea> attachedCalendarioTareasNew = new ArrayList<CalendarioTarea>();
            for (CalendarioTarea calendarioTareasNewCalendarioTareaToAttach : calendarioTareasNew) {
                calendarioTareasNewCalendarioTareaToAttach = em.getReference(calendarioTareasNewCalendarioTareaToAttach.getClass(), calendarioTareasNewCalendarioTareaToAttach.getIdCalendario());
                attachedCalendarioTareasNew.add(calendarioTareasNewCalendarioTareaToAttach);
            }
            calendarioTareasNew = attachedCalendarioTareasNew;
            tipoTarea.setCalendarioTareas(calendarioTareasNew);
            tipoTarea = em.merge(tipoTarea);
            for (TareaRealizada tareasRealizadasOldTareaRealizada : tareasRealizadasOld) {
                if (!tareasRealizadasNew.contains(tareasRealizadasOldTareaRealizada)) {
                    tareasRealizadasOldTareaRealizada.setTipoTarea(null);
                    tareasRealizadasOldTareaRealizada = em.merge(tareasRealizadasOldTareaRealizada);
                }
            }
            for (TareaRealizada tareasRealizadasNewTareaRealizada : tareasRealizadasNew) {
                if (!tareasRealizadasOld.contains(tareasRealizadasNewTareaRealizada)) {
                    TipoTarea oldTipoTareaOfTareasRealizadasNewTareaRealizada = tareasRealizadasNewTareaRealizada.getTipoTarea();
                    tareasRealizadasNewTareaRealizada.setTipoTarea(tipoTarea);
                    tareasRealizadasNewTareaRealizada = em.merge(tareasRealizadasNewTareaRealizada);
                    if (oldTipoTareaOfTareasRealizadasNewTareaRealizada != null && !oldTipoTareaOfTareasRealizadasNewTareaRealizada.equals(tipoTarea)) {
                        oldTipoTareaOfTareasRealizadasNewTareaRealizada.getTareasRealizadas().remove(tareasRealizadasNewTareaRealizada);
                        oldTipoTareaOfTareasRealizadasNewTareaRealizada = em.merge(oldTipoTareaOfTareasRealizadasNewTareaRealizada);
                    }
                }
            }
            for (CalendarioTarea calendarioTareasOldCalendarioTarea : calendarioTareasOld) {
                if (!calendarioTareasNew.contains(calendarioTareasOldCalendarioTarea)) {
                    calendarioTareasOldCalendarioTarea.setTipoTarea(null);
                    calendarioTareasOldCalendarioTarea = em.merge(calendarioTareasOldCalendarioTarea);
                }
            }
            for (CalendarioTarea calendarioTareasNewCalendarioTarea : calendarioTareasNew) {
                if (!calendarioTareasOld.contains(calendarioTareasNewCalendarioTarea)) {
                    TipoTarea oldTipoTareaOfCalendarioTareasNewCalendarioTarea = calendarioTareasNewCalendarioTarea.getTipoTarea();
                    calendarioTareasNewCalendarioTarea.setTipoTarea(tipoTarea);
                    calendarioTareasNewCalendarioTarea = em.merge(calendarioTareasNewCalendarioTarea);
                    if (oldTipoTareaOfCalendarioTareasNewCalendarioTarea != null && !oldTipoTareaOfCalendarioTareasNewCalendarioTarea.equals(tipoTarea)) {
                        oldTipoTareaOfCalendarioTareasNewCalendarioTarea.getCalendarioTareas().remove(calendarioTareasNewCalendarioTarea);
                        oldTipoTareaOfCalendarioTareasNewCalendarioTarea = em.merge(oldTipoTareaOfCalendarioTareasNewCalendarioTarea);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                long id = tipoTarea.getCodTarea();
                if (findTipoTarea(id) == null) {
                    throw new NonexistentEntityException("The tipoTarea with id " + id + " no longer exists.");
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
            TipoTarea tipoTarea;
            try {
                tipoTarea = em.getReference(TipoTarea.class, id);
                tipoTarea.getCodTarea();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoTarea with id " + id + " no longer exists.", enfe);
            }
            List<TareaRealizada> tareasRealizadas = tipoTarea.getTareasRealizadas();
            for (TareaRealizada tareasRealizadasTareaRealizada : tareasRealizadas) {
                tareasRealizadasTareaRealizada.setTipoTarea(null);
                tareasRealizadasTareaRealizada = em.merge(tareasRealizadasTareaRealizada);
            }
            List<CalendarioTarea> calendarioTareas = tipoTarea.getCalendarioTareas();
            for (CalendarioTarea calendarioTareasCalendarioTarea : calendarioTareas) {
                calendarioTareasCalendarioTarea.setTipoTarea(null);
                calendarioTareasCalendarioTarea = em.merge(calendarioTareasCalendarioTarea);
            }
            em.remove(tipoTarea);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoTarea> findTipoTareaEntities() {
        return findTipoTareaEntities(true, -1, -1);
    }

    public List<TipoTarea> findTipoTareaEntities(int maxResults, int firstResult) {
        return findTipoTareaEntities(false, maxResults, firstResult);
    }

    private List<TipoTarea> findTipoTareaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoTarea.class));
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

    public TipoTarea findTipoTarea(long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoTarea.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoTareaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoTarea> rt = cq.from(TipoTarea.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
