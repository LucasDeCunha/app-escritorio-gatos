/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi_gatos_callejeros.persistencia;

import com.mycompany.tpi_gatos_callejeros.modelo.PuntoDeAvistamiento;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.tpi_gatos_callejeros.modelo.Voluntario;
import com.mycompany.tpi_gatos_callejeros.persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author alana
 */
public class PuntoDeAvistamientoJpaController implements Serializable {

    public PuntoDeAvistamientoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PuntoDeAvistamiento puntoDeAvistamiento) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Voluntario voluntario = puntoDeAvistamiento.getVoluntario();
            if (voluntario != null) {
                voluntario = em.getReference(voluntario.getClass(), voluntario.getIdVoluntario());
                puntoDeAvistamiento.setVoluntario(voluntario);
            }
            em.persist(puntoDeAvistamiento);
            if (voluntario != null) {
                voluntario.getPuntosdeAvistamiento().add(puntoDeAvistamiento);
                voluntario = em.merge(voluntario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PuntoDeAvistamiento puntoDeAvistamiento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PuntoDeAvistamiento persistentPuntoDeAvistamiento = em.find(PuntoDeAvistamiento.class, puntoDeAvistamiento.getIdAvistamiento());
            Voluntario voluntarioOld = persistentPuntoDeAvistamiento.getVoluntario();
            Voluntario voluntarioNew = puntoDeAvistamiento.getVoluntario();
            if (voluntarioNew != null) {
                voluntarioNew = em.getReference(voluntarioNew.getClass(), voluntarioNew.getIdVoluntario());
                puntoDeAvistamiento.setVoluntario(voluntarioNew);
            }
            puntoDeAvistamiento = em.merge(puntoDeAvistamiento);
            if (voluntarioOld != null && !voluntarioOld.equals(voluntarioNew)) {
                voluntarioOld.getPuntosdeAvistamiento().remove(puntoDeAvistamiento);
                voluntarioOld = em.merge(voluntarioOld);
            }
            if (voluntarioNew != null && !voluntarioNew.equals(voluntarioOld)) {
                voluntarioNew.getPuntosdeAvistamiento().add(puntoDeAvistamiento);
                voluntarioNew = em.merge(voluntarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                long id = puntoDeAvistamiento.getIdAvistamiento();
                if (findPuntoDeAvistamiento(id) == null) {
                    throw new NonexistentEntityException("The puntoDeAvistamiento with id " + id + " no longer exists.");
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
            PuntoDeAvistamiento puntoDeAvistamiento;
            try {
                puntoDeAvistamiento = em.getReference(PuntoDeAvistamiento.class, id);
                puntoDeAvistamiento.getIdAvistamiento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The puntoDeAvistamiento with id " + id + " no longer exists.", enfe);
            }
            Voluntario voluntario = puntoDeAvistamiento.getVoluntario();
            if (voluntario != null) {
                voluntario.getPuntosdeAvistamiento().remove(puntoDeAvistamiento);
                voluntario = em.merge(voluntario);
            }
            em.remove(puntoDeAvistamiento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PuntoDeAvistamiento> findPuntoDeAvistamientoEntities() {
        return findPuntoDeAvistamientoEntities(true, -1, -1);
    }

    public List<PuntoDeAvistamiento> findPuntoDeAvistamientoEntities(int maxResults, int firstResult) {
        return findPuntoDeAvistamientoEntities(false, maxResults, firstResult);
    }

    private List<PuntoDeAvistamiento> findPuntoDeAvistamientoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PuntoDeAvistamiento.class));
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

    public PuntoDeAvistamiento findPuntoDeAvistamiento(long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PuntoDeAvistamiento.class, id);
        } finally {
            em.close();
        }
    }

    public int getPuntoDeAvistamientoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PuntoDeAvistamiento> rt = cq.from(PuntoDeAvistamiento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
