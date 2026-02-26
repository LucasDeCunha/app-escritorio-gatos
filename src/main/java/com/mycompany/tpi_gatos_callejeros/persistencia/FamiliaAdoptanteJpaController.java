/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi_gatos_callejeros.persistencia;

import com.mycompany.tpi_gatos_callejeros.modelo.FamiliaAdoptante;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.tpi_gatos_callejeros.modelo.VistaDeSeguimiento;
import com.mycompany.tpi_gatos_callejeros.persistencia.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author alana
 */
public class FamiliaAdoptanteJpaController implements Serializable {

    public FamiliaAdoptanteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FamiliaAdoptante familiaAdoptante) {
        if (familiaAdoptante.getVisitasDeFamilias() == null) {
            familiaAdoptante.setVisitasDeFamilias(new ArrayList<VistaDeSeguimiento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<VistaDeSeguimiento> attachedVisitasDeFamilias = new ArrayList<VistaDeSeguimiento>();
            for (VistaDeSeguimiento visitasDeFamiliasVistaDeSeguimientoToAttach : familiaAdoptante.getVisitasDeFamilias()) {
                visitasDeFamiliasVistaDeSeguimientoToAttach = em.getReference(visitasDeFamiliasVistaDeSeguimientoToAttach.getClass(), visitasDeFamiliasVistaDeSeguimientoToAttach.getIdVisita());
                attachedVisitasDeFamilias.add(visitasDeFamiliasVistaDeSeguimientoToAttach);
            }
            familiaAdoptante.setVisitasDeFamilias(attachedVisitasDeFamilias);
            em.persist(familiaAdoptante);
            for (VistaDeSeguimiento visitasDeFamiliasVistaDeSeguimiento : familiaAdoptante.getVisitasDeFamilias()) {
                FamiliaAdoptante oldFamiliaAdoptanteOfVisitasDeFamiliasVistaDeSeguimiento = visitasDeFamiliasVistaDeSeguimiento.getFamiliaAdoptante();
                visitasDeFamiliasVistaDeSeguimiento.setFamiliaAdoptante(familiaAdoptante);
                visitasDeFamiliasVistaDeSeguimiento = em.merge(visitasDeFamiliasVistaDeSeguimiento);
                if (oldFamiliaAdoptanteOfVisitasDeFamiliasVistaDeSeguimiento != null) {
                    oldFamiliaAdoptanteOfVisitasDeFamiliasVistaDeSeguimiento.getVisitasDeFamilias().remove(visitasDeFamiliasVistaDeSeguimiento);
                    oldFamiliaAdoptanteOfVisitasDeFamiliasVistaDeSeguimiento = em.merge(oldFamiliaAdoptanteOfVisitasDeFamiliasVistaDeSeguimiento);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FamiliaAdoptante familiaAdoptante) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FamiliaAdoptante persistentFamiliaAdoptante = em.find(FamiliaAdoptante.class, familiaAdoptante.getIdFamilia());
            List<VistaDeSeguimiento> visitasDeFamiliasOld = persistentFamiliaAdoptante.getVisitasDeFamilias();
            List<VistaDeSeguimiento> visitasDeFamiliasNew = familiaAdoptante.getVisitasDeFamilias();
            List<VistaDeSeguimiento> attachedVisitasDeFamiliasNew = new ArrayList<VistaDeSeguimiento>();
            for (VistaDeSeguimiento visitasDeFamiliasNewVistaDeSeguimientoToAttach : visitasDeFamiliasNew) {
                visitasDeFamiliasNewVistaDeSeguimientoToAttach = em.getReference(visitasDeFamiliasNewVistaDeSeguimientoToAttach.getClass(), visitasDeFamiliasNewVistaDeSeguimientoToAttach.getIdVisita());
                attachedVisitasDeFamiliasNew.add(visitasDeFamiliasNewVistaDeSeguimientoToAttach);
            }
            visitasDeFamiliasNew = attachedVisitasDeFamiliasNew;
            familiaAdoptante.setVisitasDeFamilias(visitasDeFamiliasNew);
            familiaAdoptante = em.merge(familiaAdoptante);
            for (VistaDeSeguimiento visitasDeFamiliasOldVistaDeSeguimiento : visitasDeFamiliasOld) {
                if (!visitasDeFamiliasNew.contains(visitasDeFamiliasOldVistaDeSeguimiento)) {
                    visitasDeFamiliasOldVistaDeSeguimiento.setFamiliaAdoptante(null);
                    visitasDeFamiliasOldVistaDeSeguimiento = em.merge(visitasDeFamiliasOldVistaDeSeguimiento);
                }
            }
            for (VistaDeSeguimiento visitasDeFamiliasNewVistaDeSeguimiento : visitasDeFamiliasNew) {
                if (!visitasDeFamiliasOld.contains(visitasDeFamiliasNewVistaDeSeguimiento)) {
                    FamiliaAdoptante oldFamiliaAdoptanteOfVisitasDeFamiliasNewVistaDeSeguimiento = visitasDeFamiliasNewVistaDeSeguimiento.getFamiliaAdoptante();
                    visitasDeFamiliasNewVistaDeSeguimiento.setFamiliaAdoptante(familiaAdoptante);
                    visitasDeFamiliasNewVistaDeSeguimiento = em.merge(visitasDeFamiliasNewVistaDeSeguimiento);
                    if (oldFamiliaAdoptanteOfVisitasDeFamiliasNewVistaDeSeguimiento != null && !oldFamiliaAdoptanteOfVisitasDeFamiliasNewVistaDeSeguimiento.equals(familiaAdoptante)) {
                        oldFamiliaAdoptanteOfVisitasDeFamiliasNewVistaDeSeguimiento.getVisitasDeFamilias().remove(visitasDeFamiliasNewVistaDeSeguimiento);
                        oldFamiliaAdoptanteOfVisitasDeFamiliasNewVistaDeSeguimiento = em.merge(oldFamiliaAdoptanteOfVisitasDeFamiliasNewVistaDeSeguimiento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                long id = familiaAdoptante.getIdFamilia();
                if (findFamiliaAdoptante(id) == null) {
                    throw new NonexistentEntityException("The familiaAdoptante with id " + id + " no longer exists.");
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
            FamiliaAdoptante familiaAdoptante;
            try {
                familiaAdoptante = em.getReference(FamiliaAdoptante.class, id);
                familiaAdoptante.getIdFamilia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The familiaAdoptante with id " + id + " no longer exists.", enfe);
            }
            List<VistaDeSeguimiento> visitasDeFamilias = familiaAdoptante.getVisitasDeFamilias();
            for (VistaDeSeguimiento visitasDeFamiliasVistaDeSeguimiento : visitasDeFamilias) {
                visitasDeFamiliasVistaDeSeguimiento.setFamiliaAdoptante(null);
                visitasDeFamiliasVistaDeSeguimiento = em.merge(visitasDeFamiliasVistaDeSeguimiento);
            }
            em.remove(familiaAdoptante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FamiliaAdoptante> findFamiliaAdoptanteEntities() {
        return findFamiliaAdoptanteEntities(true, -1, -1);
    }

    public List<FamiliaAdoptante> findFamiliaAdoptanteEntities(int maxResults, int firstResult) {
        return findFamiliaAdoptanteEntities(false, maxResults, firstResult);
    }

    private List<FamiliaAdoptante> findFamiliaAdoptanteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FamiliaAdoptante.class));
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

    public FamiliaAdoptante findFamiliaAdoptante(long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FamiliaAdoptante.class, id);
        } finally {
            em.close();
        }
    }

    public int getFamiliaAdoptanteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FamiliaAdoptante> rt = cq.from(FamiliaAdoptante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
