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
import com.mycompany.tpi_gatos_callejeros.modelo.Tratamiento;
import com.mycompany.tpi_gatos_callejeros.modelo.Veterinario;
import com.mycompany.tpi_gatos_callejeros.persistencia.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author alana
 */
public class VeterinarioJpaController implements Serializable {

    public VeterinarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Veterinario veterinario) {
        if (veterinario.getTratamientos() == null) {
            veterinario.setTratamientos(new ArrayList<Tratamiento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Tratamiento> attachedTratamientos = new ArrayList<Tratamiento>();
            for (Tratamiento tratamientosTratamientoToAttach : veterinario.getTratamientos()) {
                tratamientosTratamientoToAttach = em.getReference(tratamientosTratamientoToAttach.getClass(), tratamientosTratamientoToAttach.getCodTratamiento());
                attachedTratamientos.add(tratamientosTratamientoToAttach);
            }
            veterinario.setTratamientos(attachedTratamientos);
            em.persist(veterinario);
            for (Tratamiento tratamientosTratamiento : veterinario.getTratamientos()) {
                Veterinario oldVeterinarioOfTratamientosTratamiento = tratamientosTratamiento.getVeterinario();
                tratamientosTratamiento.setVeterinario(veterinario);
                tratamientosTratamiento = em.merge(tratamientosTratamiento);
                if (oldVeterinarioOfTratamientosTratamiento != null) {
                    oldVeterinarioOfTratamientosTratamiento.getTratamientos().remove(tratamientosTratamiento);
                    oldVeterinarioOfTratamientosTratamiento = em.merge(oldVeterinarioOfTratamientosTratamiento);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Veterinario veterinario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Veterinario persistentVeterinario = em.find(Veterinario.class, veterinario.getIdVeterinario());
            List<Tratamiento> tratamientosOld = persistentVeterinario.getTratamientos();
            List<Tratamiento> tratamientosNew = veterinario.getTratamientos();
            List<Tratamiento> attachedTratamientosNew = new ArrayList<Tratamiento>();
            for (Tratamiento tratamientosNewTratamientoToAttach : tratamientosNew) {
                tratamientosNewTratamientoToAttach = em.getReference(tratamientosNewTratamientoToAttach.getClass(), tratamientosNewTratamientoToAttach.getCodTratamiento());
                attachedTratamientosNew.add(tratamientosNewTratamientoToAttach);
            }
            tratamientosNew = attachedTratamientosNew;
            veterinario.setTratamientos(tratamientosNew);
            veterinario = em.merge(veterinario);
            for (Tratamiento tratamientosOldTratamiento : tratamientosOld) {
                if (!tratamientosNew.contains(tratamientosOldTratamiento)) {
                    tratamientosOldTratamiento.setVeterinario(null);
                    tratamientosOldTratamiento = em.merge(tratamientosOldTratamiento);
                }
            }
            for (Tratamiento tratamientosNewTratamiento : tratamientosNew) {
                if (!tratamientosOld.contains(tratamientosNewTratamiento)) {
                    Veterinario oldVeterinarioOfTratamientosNewTratamiento = tratamientosNewTratamiento.getVeterinario();
                    tratamientosNewTratamiento.setVeterinario(veterinario);
                    tratamientosNewTratamiento = em.merge(tratamientosNewTratamiento);
                    if (oldVeterinarioOfTratamientosNewTratamiento != null && !oldVeterinarioOfTratamientosNewTratamiento.equals(veterinario)) {
                        oldVeterinarioOfTratamientosNewTratamiento.getTratamientos().remove(tratamientosNewTratamiento);
                        oldVeterinarioOfTratamientosNewTratamiento = em.merge(oldVeterinarioOfTratamientosNewTratamiento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                long id = veterinario.getIdVeterinario();
                if (findVeterinario(id) == null) {
                    throw new NonexistentEntityException("The veterinario with id " + id + " no longer exists.");
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
            Veterinario veterinario;
            try {
                veterinario = em.getReference(Veterinario.class, id);
                veterinario.getIdVeterinario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The veterinario with id " + id + " no longer exists.", enfe);
            }
            List<Tratamiento> tratamientos = veterinario.getTratamientos();
            for (Tratamiento tratamientosTratamiento : tratamientos) {
                tratamientosTratamiento.setVeterinario(null);
                tratamientosTratamiento = em.merge(tratamientosTratamiento);
            }
            em.remove(veterinario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Veterinario> findVeterinarioEntities() {
        return findVeterinarioEntities(true, -1, -1);
    }

    public List<Veterinario> findVeterinarioEntities(int maxResults, int firstResult) {
        return findVeterinarioEntities(false, maxResults, firstResult);
    }

    private List<Veterinario> findVeterinarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Veterinario.class));
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

    public Veterinario findVeterinario(long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Veterinario.class, id);
        } finally {
            em.close();
        }
    }

    public int getVeterinarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Veterinario> rt = cq.from(Veterinario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
