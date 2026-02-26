/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi_gatos_callejeros.persistencia;

import com.mycompany.tpi_gatos_callejeros.modelo.CalendarioTarea;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.tpi_gatos_callejeros.modelo.TipoTarea;
import com.mycompany.tpi_gatos_callejeros.modelo.Voluntario;
import com.mycompany.tpi_gatos_callejeros.persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author alana
 */
public class CalendarioTareaJpaController implements Serializable {

    public CalendarioTareaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CalendarioTarea calendarioTarea) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoTarea tipoTarea = calendarioTarea.getTipoTarea();
            if (tipoTarea != null) {
                tipoTarea = em.getReference(tipoTarea.getClass(), tipoTarea.getCodTarea());
                calendarioTarea.setTipoTarea(tipoTarea);
            }
            Voluntario voluntario = calendarioTarea.getVoluntario();
            if (voluntario != null) {
                voluntario = em.getReference(voluntario.getClass(), voluntario.getIdVoluntario());
                calendarioTarea.setVoluntario(voluntario);
            }
            em.persist(calendarioTarea);
            if (tipoTarea != null) {
                tipoTarea.getCalendarioTareas().add(calendarioTarea);
                tipoTarea = em.merge(tipoTarea);
            }
            if (voluntario != null) {
                voluntario.getCalendarioDeTareas().add(calendarioTarea);
                voluntario = em.merge(voluntario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CalendarioTarea calendarioTarea) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CalendarioTarea persistentCalendarioTarea = em.find(CalendarioTarea.class, calendarioTarea.getIdCalendario());
            TipoTarea tipoTareaOld = persistentCalendarioTarea.getTipoTarea();
            TipoTarea tipoTareaNew = calendarioTarea.getTipoTarea();
            Voluntario voluntarioOld = persistentCalendarioTarea.getVoluntario();
            Voluntario voluntarioNew = calendarioTarea.getVoluntario();
            if (tipoTareaNew != null) {
                tipoTareaNew = em.getReference(tipoTareaNew.getClass(), tipoTareaNew.getCodTarea());
                calendarioTarea.setTipoTarea(tipoTareaNew);
            }
            if (voluntarioNew != null) {
                voluntarioNew = em.getReference(voluntarioNew.getClass(), voluntarioNew.getIdVoluntario());
                calendarioTarea.setVoluntario(voluntarioNew);
            }
            calendarioTarea = em.merge(calendarioTarea);
            if (tipoTareaOld != null && !tipoTareaOld.equals(tipoTareaNew)) {
                tipoTareaOld.getCalendarioTareas().remove(calendarioTarea);
                tipoTareaOld = em.merge(tipoTareaOld);
            }
            if (tipoTareaNew != null && !tipoTareaNew.equals(tipoTareaOld)) {
                tipoTareaNew.getCalendarioTareas().add(calendarioTarea);
                tipoTareaNew = em.merge(tipoTareaNew);
            }
            if (voluntarioOld != null && !voluntarioOld.equals(voluntarioNew)) {
                voluntarioOld.getCalendarioDeTareas().remove(calendarioTarea);
                voluntarioOld = em.merge(voluntarioOld);
            }
            if (voluntarioNew != null && !voluntarioNew.equals(voluntarioOld)) {
                voluntarioNew.getCalendarioDeTareas().add(calendarioTarea);
                voluntarioNew = em.merge(voluntarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                long id = calendarioTarea.getIdCalendario();
                if (findCalendarioTarea(id) == null) {
                    throw new NonexistentEntityException("The calendarioTarea with id " + id + " no longer exists.");
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
            CalendarioTarea calendarioTarea;
            try {
                calendarioTarea = em.getReference(CalendarioTarea.class, id);
                calendarioTarea.getIdCalendario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The calendarioTarea with id " + id + " no longer exists.", enfe);
            }
            TipoTarea tipoTarea = calendarioTarea.getTipoTarea();
            if (tipoTarea != null) {
                tipoTarea.getCalendarioTareas().remove(calendarioTarea);
                tipoTarea = em.merge(tipoTarea);
            }
            Voluntario voluntario = calendarioTarea.getVoluntario();
            if (voluntario != null) {
                voluntario.getCalendarioDeTareas().remove(calendarioTarea);
                voluntario = em.merge(voluntario);
            }
            em.remove(calendarioTarea);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CalendarioTarea> findCalendarioTareaEntities() {
        return findCalendarioTareaEntities(true, -1, -1);
    }

    public List<CalendarioTarea> findCalendarioTareaEntities(int maxResults, int firstResult) {
        return findCalendarioTareaEntities(false, maxResults, firstResult);
    }

    private List<CalendarioTarea> findCalendarioTareaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CalendarioTarea.class));
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

    public CalendarioTarea findCalendarioTarea(long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CalendarioTarea.class, id);
        } finally {
            em.close();
        }
    }

    public int getCalendarioTareaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CalendarioTarea> rt = cq.from(CalendarioTarea.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
