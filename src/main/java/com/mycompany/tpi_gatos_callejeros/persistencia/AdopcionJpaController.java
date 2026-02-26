/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi_gatos_callejeros.persistencia;

import com.mycompany.tpi_gatos_callejeros.modelo.Adopcion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.tpi_gatos_callejeros.modelo.Gato;
import com.mycompany.tpi_gatos_callejeros.modelo.FamiliaAdoptante;
import com.mycompany.tpi_gatos_callejeros.persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author alana
 */
public class AdopcionJpaController implements Serializable {

    public AdopcionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Adopcion adopcion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Gato gato = adopcion.getGato();
            if (gato != null) {
                gato = em.getReference(gato.getClass(), gato.getIdGato());
                adopcion.setGato(gato);
            }
            FamiliaAdoptante familiaAdoptante = adopcion.getFamiliaAdoptante();
            if (familiaAdoptante != null) {
                familiaAdoptante = em.getReference(familiaAdoptante.getClass(), familiaAdoptante.getIdFamilia());
                adopcion.setFamiliaAdoptante(familiaAdoptante);
            }
            em.persist(adopcion);
            if (gato != null) {
                Adopcion oldAdopcionOfGato = gato.getAdopcion();
                if (oldAdopcionOfGato != null) {
                    oldAdopcionOfGato.setGato(null);
                    oldAdopcionOfGato = em.merge(oldAdopcionOfGato);
                }
                gato.setAdopcion(adopcion);
                gato = em.merge(gato);
            }
            if (familiaAdoptante != null) {
                familiaAdoptante.getAdopciones().add(adopcion);
                familiaAdoptante = em.merge(familiaAdoptante);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Adopcion adopcion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Adopcion persistentAdopcion = em.find(Adopcion.class, adopcion.getIdAdopcion());
            Gato gatoOld = persistentAdopcion.getGato();
            Gato gatoNew = adopcion.getGato();
            FamiliaAdoptante familiaAdoptanteOld = persistentAdopcion.getFamiliaAdoptante();
            FamiliaAdoptante familiaAdoptanteNew = adopcion.getFamiliaAdoptante();
            if (gatoNew != null) {
                gatoNew = em.getReference(gatoNew.getClass(), gatoNew.getIdGato());
                adopcion.setGato(gatoNew);
            }
            if (familiaAdoptanteNew != null) {
                familiaAdoptanteNew = em.getReference(familiaAdoptanteNew.getClass(), familiaAdoptanteNew.getIdFamilia());
                adopcion.setFamiliaAdoptante(familiaAdoptanteNew);
            }
            adopcion = em.merge(adopcion);
            if (gatoOld != null && !gatoOld.equals(gatoNew)) {
                gatoOld.setAdopcion(null);
                gatoOld = em.merge(gatoOld);
            }
            if (gatoNew != null && !gatoNew.equals(gatoOld)) {
                Adopcion oldAdopcionOfGato = gatoNew.getAdopcion();
                if (oldAdopcionOfGato != null) {
                    oldAdopcionOfGato.setGato(null);
                    oldAdopcionOfGato = em.merge(oldAdopcionOfGato);
                }
                gatoNew.setAdopcion(adopcion);
                gatoNew = em.merge(gatoNew);
            }
            if (familiaAdoptanteOld != null && !familiaAdoptanteOld.equals(familiaAdoptanteNew)) {
                familiaAdoptanteOld.getAdopciones().remove(adopcion);
                familiaAdoptanteOld = em.merge(familiaAdoptanteOld);
            }
            if (familiaAdoptanteNew != null && !familiaAdoptanteNew.equals(familiaAdoptanteOld)) {
                familiaAdoptanteNew.getAdopciones().add(adopcion);
                familiaAdoptanteNew = em.merge(familiaAdoptanteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                long id = adopcion.getIdAdopcion();
                if (findAdopcion(id) == null) {
                    throw new NonexistentEntityException("The adopcion with id " + id + " no longer exists.");
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
            Adopcion adopcion;
            try {
                adopcion = em.getReference(Adopcion.class, id);
                adopcion.getIdAdopcion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The adopcion with id " + id + " no longer exists.", enfe);
            }
            Gato gato = adopcion.getGato();
            if (gato != null) {
                gato.setAdopcion(null);
                gato = em.merge(gato);
            }
            FamiliaAdoptante familiaAdoptante = adopcion.getFamiliaAdoptante();
            if (familiaAdoptante != null) {
                familiaAdoptante.getAdopciones().remove(adopcion);
                familiaAdoptante = em.merge(familiaAdoptante);
            }
            em.remove(adopcion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Adopcion> findAdopcionEntities() {
        return findAdopcionEntities(true, -1, -1);
    }

    public List<Adopcion> findAdopcionEntities(int maxResults, int firstResult) {
        return findAdopcionEntities(false, maxResults, firstResult);
    }

    private List<Adopcion> findAdopcionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Adopcion.class));
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

    public Adopcion findAdopcion(long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Adopcion.class, id);
        } finally {
            em.close();
        }
    }

    public int getAdopcionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Adopcion> rt = cq.from(Adopcion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
