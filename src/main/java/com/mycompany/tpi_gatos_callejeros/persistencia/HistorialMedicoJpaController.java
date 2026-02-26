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
import com.mycompany.tpi_gatos_callejeros.modelo.Gato;
import com.mycompany.tpi_gatos_callejeros.modelo.HistorialMedico;
import com.mycompany.tpi_gatos_callejeros.modelo.Tratamiento;
import com.mycompany.tpi_gatos_callejeros.persistencia.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author alana
 */
public class HistorialMedicoJpaController implements Serializable {

    public HistorialMedicoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(HistorialMedico historialMedico) {
        if (historialMedico.getTratamientos() == null) {
            historialMedico.setTratamientos(new ArrayList<Tratamiento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Gato gato = historialMedico.getGato();
            if (gato != null) {
                gato = em.getReference(gato.getClass(), gato.getIdGato());
                historialMedico.setGato(gato);
            }
            List<Tratamiento> attachedTratamientos = new ArrayList<Tratamiento>();
            for (Tratamiento tratamientosTratamientoToAttach : historialMedico.getTratamientos()) {
                tratamientosTratamientoToAttach = em.getReference(tratamientosTratamientoToAttach.getClass(), tratamientosTratamientoToAttach.getCodTratamiento());
                attachedTratamientos.add(tratamientosTratamientoToAttach);
            }
            historialMedico.setTratamientos(attachedTratamientos);
            em.persist(historialMedico);
            if (gato != null) {
                HistorialMedico oldHistorialOfGato = gato.getHistorial();
                if (oldHistorialOfGato != null) {
                    oldHistorialOfGato.setGato(null);
                    oldHistorialOfGato = em.merge(oldHistorialOfGato);
                }
                gato.setHistorial(historialMedico);
                gato = em.merge(gato);
            }
            for (Tratamiento tratamientosTratamiento : historialMedico.getTratamientos()) {
                HistorialMedico oldHistorialMedicoOfTratamientosTratamiento = tratamientosTratamiento.getHistorialMedico();
                tratamientosTratamiento.setHistorialMedico(historialMedico);
                tratamientosTratamiento = em.merge(tratamientosTratamiento);
                if (oldHistorialMedicoOfTratamientosTratamiento != null) {
                    oldHistorialMedicoOfTratamientosTratamiento.getTratamientos().remove(tratamientosTratamiento);
                    oldHistorialMedicoOfTratamientosTratamiento = em.merge(oldHistorialMedicoOfTratamientosTratamiento);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(HistorialMedico historialMedico) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            HistorialMedico persistentHistorialMedico = em.find(HistorialMedico.class, historialMedico.getCodHistorial());
            Gato gatoOld = persistentHistorialMedico.getGato();
            Gato gatoNew = historialMedico.getGato();
            List<Tratamiento> tratamientosOld = persistentHistorialMedico.getTratamientos();
            List<Tratamiento> tratamientosNew = historialMedico.getTratamientos();
            if (gatoNew != null) {
                gatoNew = em.getReference(gatoNew.getClass(), gatoNew.getIdGato());
                historialMedico.setGato(gatoNew);
            }
            List<Tratamiento> attachedTratamientosNew = new ArrayList<Tratamiento>();
            for (Tratamiento tratamientosNewTratamientoToAttach : tratamientosNew) {
                tratamientosNewTratamientoToAttach = em.getReference(tratamientosNewTratamientoToAttach.getClass(), tratamientosNewTratamientoToAttach.getCodTratamiento());
                attachedTratamientosNew.add(tratamientosNewTratamientoToAttach);
            }
            tratamientosNew = attachedTratamientosNew;
            historialMedico.setTratamientos(tratamientosNew);
            historialMedico = em.merge(historialMedico);
            if (gatoOld != null && !gatoOld.equals(gatoNew)) {
                gatoOld.setHistorial(null);
                gatoOld = em.merge(gatoOld);
            }
            if (gatoNew != null && !gatoNew.equals(gatoOld)) {
                HistorialMedico oldHistorialOfGato = gatoNew.getHistorial();
                if (oldHistorialOfGato != null) {
                    oldHistorialOfGato.setGato(null);
                    oldHistorialOfGato = em.merge(oldHistorialOfGato);
                }
                gatoNew.setHistorial(historialMedico);
                gatoNew = em.merge(gatoNew);
            }
            for (Tratamiento tratamientosOldTratamiento : tratamientosOld) {
                if (!tratamientosNew.contains(tratamientosOldTratamiento)) {
                    tratamientosOldTratamiento.setHistorialMedico(null);
                    tratamientosOldTratamiento = em.merge(tratamientosOldTratamiento);
                }
            }
            for (Tratamiento tratamientosNewTratamiento : tratamientosNew) {
                if (!tratamientosOld.contains(tratamientosNewTratamiento)) {
                    HistorialMedico oldHistorialMedicoOfTratamientosNewTratamiento = tratamientosNewTratamiento.getHistorialMedico();
                    tratamientosNewTratamiento.setHistorialMedico(historialMedico);
                    tratamientosNewTratamiento = em.merge(tratamientosNewTratamiento);
                    if (oldHistorialMedicoOfTratamientosNewTratamiento != null && !oldHistorialMedicoOfTratamientosNewTratamiento.equals(historialMedico)) {
                        oldHistorialMedicoOfTratamientosNewTratamiento.getTratamientos().remove(tratamientosNewTratamiento);
                        oldHistorialMedicoOfTratamientosNewTratamiento = em.merge(oldHistorialMedicoOfTratamientosNewTratamiento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                long id = historialMedico.getCodHistorial();
                if (findHistorialMedico(id) == null) {
                    throw new NonexistentEntityException("The historialMedico with id " + id + " no longer exists.");
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
            HistorialMedico historialMedico;
            try {
                historialMedico = em.getReference(HistorialMedico.class, id);
                historialMedico.getCodHistorial();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The historialMedico with id " + id + " no longer exists.", enfe);
            }
            Gato gato = historialMedico.getGato();
            if (gato != null) {
                gato.setHistorial(null);
                gato = em.merge(gato);
            }
            List<Tratamiento> tratamientos = historialMedico.getTratamientos();
            for (Tratamiento tratamientosTratamiento : tratamientos) {
                tratamientosTratamiento.setHistorialMedico(null);
                tratamientosTratamiento = em.merge(tratamientosTratamiento);
            }
            em.remove(historialMedico);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<HistorialMedico> findHistorialMedicoEntities() {
        return findHistorialMedicoEntities(true, -1, -1);
    }

    public List<HistorialMedico> findHistorialMedicoEntities(int maxResults, int firstResult) {
        return findHistorialMedicoEntities(false, maxResults, firstResult);
    }

    private List<HistorialMedico> findHistorialMedicoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HistorialMedico.class));
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

    public HistorialMedico findHistorialMedico(long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HistorialMedico.class, id);
        } finally {
            em.close();
        }
    }

    public int getHistorialMedicoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<HistorialMedico> rt = cq.from(HistorialMedico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
