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
import com.mycompany.tpi_gatos_callejeros.modelo.HistorialMedico;
import com.mycompany.tpi_gatos_callejeros.modelo.Tratamiento;
import com.mycompany.tpi_gatos_callejeros.modelo.Veterinario;
import com.mycompany.tpi_gatos_callejeros.persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author alana
 */
public class TratamientoJpaController implements Serializable {

    public TratamientoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tratamiento tratamiento) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            HistorialMedico historialMedico = tratamiento.getHistorialMedico();
            if (historialMedico != null) {
                historialMedico = em.getReference(historialMedico.getClass(), historialMedico.getCodHistorial());
                tratamiento.setHistorialMedico(historialMedico);
            }
            Veterinario veterinario = tratamiento.getVeterinario();
            if (veterinario != null) {
                veterinario = em.getReference(veterinario.getClass(), veterinario.getIdVeterinario());
                tratamiento.setVeterinario(veterinario);
            }
            em.persist(tratamiento);
            if (historialMedico != null) {
                historialMedico.getTratamientos().add(tratamiento);
                historialMedico = em.merge(historialMedico);
            }
            if (veterinario != null) {
                veterinario.getTratamientos().add(tratamiento);
                veterinario = em.merge(veterinario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tratamiento tratamiento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tratamiento persistentTratamiento = em.find(Tratamiento.class, tratamiento.getCodTratamiento());
            HistorialMedico historialMedicoOld = persistentTratamiento.getHistorialMedico();
            HistorialMedico historialMedicoNew = tratamiento.getHistorialMedico();
            Veterinario veterinarioOld = persistentTratamiento.getVeterinario();
            Veterinario veterinarioNew = tratamiento.getVeterinario();
            if (historialMedicoNew != null) {
                historialMedicoNew = em.getReference(historialMedicoNew.getClass(), historialMedicoNew.getCodHistorial());
                tratamiento.setHistorialMedico(historialMedicoNew);
            }
            if (veterinarioNew != null) {
                veterinarioNew = em.getReference(veterinarioNew.getClass(), veterinarioNew.getIdVeterinario());
                tratamiento.setVeterinario(veterinarioNew);
            }
            tratamiento = em.merge(tratamiento);
            if (historialMedicoOld != null && !historialMedicoOld.equals(historialMedicoNew)) {
                historialMedicoOld.getTratamientos().remove(tratamiento);
                historialMedicoOld = em.merge(historialMedicoOld);
            }
            if (historialMedicoNew != null && !historialMedicoNew.equals(historialMedicoOld)) {
                historialMedicoNew.getTratamientos().add(tratamiento);
                historialMedicoNew = em.merge(historialMedicoNew);
            }
            if (veterinarioOld != null && !veterinarioOld.equals(veterinarioNew)) {
                veterinarioOld.getTratamientos().remove(tratamiento);
                veterinarioOld = em.merge(veterinarioOld);
            }
            if (veterinarioNew != null && !veterinarioNew.equals(veterinarioOld)) {
                veterinarioNew.getTratamientos().add(tratamiento);
                veterinarioNew = em.merge(veterinarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                long id = tratamiento.getCodTratamiento();
                if (findTratamiento(id) == null) {
                    throw new NonexistentEntityException("The tratamiento with id " + id + " no longer exists.");
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
            Tratamiento tratamiento;
            try {
                tratamiento = em.getReference(Tratamiento.class, id);
                tratamiento.getCodTratamiento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tratamiento with id " + id + " no longer exists.", enfe);
            }
            HistorialMedico historialMedico = tratamiento.getHistorialMedico();
            if (historialMedico != null) {
                historialMedico.getTratamientos().remove(tratamiento);
                historialMedico = em.merge(historialMedico);
            }
            Veterinario veterinario = tratamiento.getVeterinario();
            if (veterinario != null) {
                veterinario.getTratamientos().remove(tratamiento);
                veterinario = em.merge(veterinario);
            }
            em.remove(tratamiento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tratamiento> findTratamientoEntities() {
        return findTratamientoEntities(true, -1, -1);
    }

    public List<Tratamiento> findTratamientoEntities(int maxResults, int firstResult) {
        return findTratamientoEntities(false, maxResults, firstResult);
    }

    private List<Tratamiento> findTratamientoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tratamiento.class));
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

    public Tratamiento findTratamiento(long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tratamiento.class, id);
        } finally {
            em.close();
        }
    }

    public int getTratamientoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tratamiento> rt = cq.from(Tratamiento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
