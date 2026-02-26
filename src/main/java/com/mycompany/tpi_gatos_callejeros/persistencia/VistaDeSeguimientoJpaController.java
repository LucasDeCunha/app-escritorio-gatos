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
import com.mycompany.tpi_gatos_callejeros.modelo.Voluntario;
import com.mycompany.tpi_gatos_callejeros.modelo.Gato;
import com.mycompany.tpi_gatos_callejeros.modelo.FamiliaAdoptante;
import com.mycompany.tpi_gatos_callejeros.modelo.VistaDeSeguimiento;
import com.mycompany.tpi_gatos_callejeros.persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author alana
 */
public class VistaDeSeguimientoJpaController implements Serializable {

    public VistaDeSeguimientoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(VistaDeSeguimiento vistaDeSeguimiento) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Voluntario voluntario = vistaDeSeguimiento.getVoluntario();
            if (voluntario != null) {
                voluntario = em.getReference(voluntario.getClass(), voluntario.getIdVoluntario());
                vistaDeSeguimiento.setVoluntario(voluntario);
            }
            Gato gato = vistaDeSeguimiento.getGato();
            if (gato != null) {
                gato = em.getReference(gato.getClass(), gato.getIdGato());
                vistaDeSeguimiento.setGato(gato);
            }
            FamiliaAdoptante familiaAdoptante = vistaDeSeguimiento.getFamiliaAdoptante();
            if (familiaAdoptante != null) {
                familiaAdoptante = em.getReference(familiaAdoptante.getClass(), familiaAdoptante.getIdFamilia());
                vistaDeSeguimiento.setFamiliaAdoptante(familiaAdoptante);
            }
            em.persist(vistaDeSeguimiento);
            if (voluntario != null) {
                voluntario.getVisitasAdminVolun().add(vistaDeSeguimiento);
                voluntario = em.merge(voluntario);
            }
            if (gato != null) {
                gato.getVisitas().add(vistaDeSeguimiento);
                gato = em.merge(gato);
            }
            if (familiaAdoptante != null) {
                familiaAdoptante.getVisitasDeFamilias().add(vistaDeSeguimiento);
                familiaAdoptante = em.merge(familiaAdoptante);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(VistaDeSeguimiento vistaDeSeguimiento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            VistaDeSeguimiento persistentVistaDeSeguimiento = em.find(VistaDeSeguimiento.class, vistaDeSeguimiento.getIdVisita());
            Voluntario voluntarioOld = persistentVistaDeSeguimiento.getVoluntario();
            Voluntario voluntarioNew = vistaDeSeguimiento.getVoluntario();
            Gato gatoOld = persistentVistaDeSeguimiento.getGato();
            Gato gatoNew = vistaDeSeguimiento.getGato();
            FamiliaAdoptante familiaAdoptanteOld = persistentVistaDeSeguimiento.getFamiliaAdoptante();
            FamiliaAdoptante familiaAdoptanteNew = vistaDeSeguimiento.getFamiliaAdoptante();
            if (voluntarioNew != null) {
                voluntarioNew = em.getReference(voluntarioNew.getClass(), voluntarioNew.getIdVoluntario());
                vistaDeSeguimiento.setVoluntario(voluntarioNew);
            }
            if (gatoNew != null) {
                gatoNew = em.getReference(gatoNew.getClass(), gatoNew.getIdGato());
                vistaDeSeguimiento.setGato(gatoNew);
            }
            if (familiaAdoptanteNew != null) {
                familiaAdoptanteNew = em.getReference(familiaAdoptanteNew.getClass(), familiaAdoptanteNew.getIdFamilia());
                vistaDeSeguimiento.setFamiliaAdoptante(familiaAdoptanteNew);
            }
            vistaDeSeguimiento = em.merge(vistaDeSeguimiento);
            if (voluntarioOld != null && !voluntarioOld.equals(voluntarioNew)) {
                voluntarioOld.getVisitasAdminVolun().remove(vistaDeSeguimiento);
                voluntarioOld = em.merge(voluntarioOld);
            }
            if (voluntarioNew != null && !voluntarioNew.equals(voluntarioOld)) {
                voluntarioNew.getVisitasAdminVolun().add(vistaDeSeguimiento);
                voluntarioNew = em.merge(voluntarioNew);
            }
            if (gatoOld != null && !gatoOld.equals(gatoNew)) {
                gatoOld.getVisitas().remove(vistaDeSeguimiento);
                gatoOld = em.merge(gatoOld);
            }
            if (gatoNew != null && !gatoNew.equals(gatoOld)) {
                gatoNew.getVisitas().add(vistaDeSeguimiento);
                gatoNew = em.merge(gatoNew);
            }
            if (familiaAdoptanteOld != null && !familiaAdoptanteOld.equals(familiaAdoptanteNew)) {
                familiaAdoptanteOld.getVisitasDeFamilias().remove(vistaDeSeguimiento);
                familiaAdoptanteOld = em.merge(familiaAdoptanteOld);
            }
            if (familiaAdoptanteNew != null && !familiaAdoptanteNew.equals(familiaAdoptanteOld)) {
                familiaAdoptanteNew.getVisitasDeFamilias().add(vistaDeSeguimiento);
                familiaAdoptanteNew = em.merge(familiaAdoptanteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                long id = vistaDeSeguimiento.getIdVisita();
                if (findVistaDeSeguimiento(id) == null) {
                    throw new NonexistentEntityException("The vistaDeSeguimiento with id " + id + " no longer exists.");
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
            VistaDeSeguimiento vistaDeSeguimiento;
            try {
                vistaDeSeguimiento = em.getReference(VistaDeSeguimiento.class, id);
                vistaDeSeguimiento.getIdVisita();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vistaDeSeguimiento with id " + id + " no longer exists.", enfe);
            }
            Voluntario voluntario = vistaDeSeguimiento.getVoluntario();
            if (voluntario != null) {
                voluntario.getVisitasAdminVolun().remove(vistaDeSeguimiento);
                voluntario = em.merge(voluntario);
            }
            Gato gato = vistaDeSeguimiento.getGato();
            if (gato != null) {
                gato.getVisitas().remove(vistaDeSeguimiento);
                gato = em.merge(gato);
            }
            FamiliaAdoptante familiaAdoptante = vistaDeSeguimiento.getFamiliaAdoptante();
            if (familiaAdoptante != null) {
                familiaAdoptante.getVisitasDeFamilias().remove(vistaDeSeguimiento);
                familiaAdoptante = em.merge(familiaAdoptante);
            }
            em.remove(vistaDeSeguimiento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<VistaDeSeguimiento> findVistaDeSeguimientoEntities() {
        return findVistaDeSeguimientoEntities(true, -1, -1);
    }

    public List<VistaDeSeguimiento> findVistaDeSeguimientoEntities(int maxResults, int firstResult) {
        return findVistaDeSeguimientoEntities(false, maxResults, firstResult);
    }

    private List<VistaDeSeguimiento> findVistaDeSeguimientoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VistaDeSeguimiento.class));
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

    public VistaDeSeguimiento findVistaDeSeguimiento(long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(VistaDeSeguimiento.class, id);
        } finally {
            em.close();
        }
    }

    public int getVistaDeSeguimientoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<VistaDeSeguimiento> rt = cq.from(VistaDeSeguimiento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
