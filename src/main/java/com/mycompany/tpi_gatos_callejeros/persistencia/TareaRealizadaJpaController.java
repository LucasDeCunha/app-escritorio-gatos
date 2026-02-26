/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi_gatos_callejeros.persistencia;

import com.mycompany.tpi_gatos_callejeros.modelo.TareaRealizada;
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
public class TareaRealizadaJpaController implements Serializable {

    public TareaRealizadaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TareaRealizada tareaRealizada) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoTarea tipoTarea = tareaRealizada.getTipoTarea();
            if (tipoTarea != null) {
                tipoTarea = em.getReference(tipoTarea.getClass(), tipoTarea.getCodTarea());
                tareaRealizada.setTipoTarea(tipoTarea);
            }
            Voluntario voluntario = tareaRealizada.getVoluntario();
            if (voluntario != null) {
                voluntario = em.getReference(voluntario.getClass(), voluntario.getIdVoluntario());
                tareaRealizada.setVoluntario(voluntario);
            }
            em.persist(tareaRealizada);
            if (tipoTarea != null) {
                tipoTarea.getTareasRealizadas().add(tareaRealizada);
                tipoTarea = em.merge(tipoTarea);
            }
            if (voluntario != null) {
                voluntario.getTareasRealizadas().add(tareaRealizada);
                voluntario = em.merge(voluntario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TareaRealizada tareaRealizada) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TareaRealizada persistentTareaRealizada = em.find(TareaRealizada.class, tareaRealizada.getCodTarea());
            TipoTarea tipoTareaOld = persistentTareaRealizada.getTipoTarea();
            TipoTarea tipoTareaNew = tareaRealizada.getTipoTarea();
            Voluntario voluntarioOld = persistentTareaRealizada.getVoluntario();
            Voluntario voluntarioNew = tareaRealizada.getVoluntario();
            if (tipoTareaNew != null) {
                tipoTareaNew = em.getReference(tipoTareaNew.getClass(), tipoTareaNew.getCodTarea());
                tareaRealizada.setTipoTarea(tipoTareaNew);
            }
            if (voluntarioNew != null) {
                voluntarioNew = em.getReference(voluntarioNew.getClass(), voluntarioNew.getIdVoluntario());
                tareaRealizada.setVoluntario(voluntarioNew);
            }
            tareaRealizada = em.merge(tareaRealizada);
            if (tipoTareaOld != null && !tipoTareaOld.equals(tipoTareaNew)) {
                tipoTareaOld.getTareasRealizadas().remove(tareaRealizada);
                tipoTareaOld = em.merge(tipoTareaOld);
            }
            if (tipoTareaNew != null && !tipoTareaNew.equals(tipoTareaOld)) {
                tipoTareaNew.getTareasRealizadas().add(tareaRealizada);
                tipoTareaNew = em.merge(tipoTareaNew);
            }
            if (voluntarioOld != null && !voluntarioOld.equals(voluntarioNew)) {
                voluntarioOld.getTareasRealizadas().remove(tareaRealizada);
                voluntarioOld = em.merge(voluntarioOld);
            }
            if (voluntarioNew != null && !voluntarioNew.equals(voluntarioOld)) {
                voluntarioNew.getTareasRealizadas().add(tareaRealizada);
                voluntarioNew = em.merge(voluntarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                long id = tareaRealizada.getCodTarea();
                if (findTareaRealizada(id) == null) {
                    throw new NonexistentEntityException("The tareaRealizada with id " + id + " no longer exists.");
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
            TareaRealizada tareaRealizada;
            try {
                tareaRealizada = em.getReference(TareaRealizada.class, id);
                tareaRealizada.getCodTarea();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tareaRealizada with id " + id + " no longer exists.", enfe);
            }
            TipoTarea tipoTarea = tareaRealizada.getTipoTarea();
            if (tipoTarea != null) {
                tipoTarea.getTareasRealizadas().remove(tareaRealizada);
                tipoTarea = em.merge(tipoTarea);
            }
            Voluntario voluntario = tareaRealizada.getVoluntario();
            if (voluntario != null) {
                voluntario.getTareasRealizadas().remove(tareaRealizada);
                voluntario = em.merge(voluntario);
            }
            em.remove(tareaRealizada);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TareaRealizada> findTareaRealizadaEntities() {
        return findTareaRealizadaEntities(true, -1, -1);
    }

    public List<TareaRealizada> findTareaRealizadaEntities(int maxResults, int firstResult) {
        return findTareaRealizadaEntities(false, maxResults, firstResult);
    }

    private List<TareaRealizada> findTareaRealizadaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TareaRealizada.class));
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

    public TareaRealizada findTareaRealizada(long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TareaRealizada.class, id);
        } finally {
            em.close();
        }
    }

    public int getTareaRealizadaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TareaRealizada> rt = cq.from(TareaRealizada.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
