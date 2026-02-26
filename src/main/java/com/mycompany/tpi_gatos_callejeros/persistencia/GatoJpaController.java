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
import com.mycompany.tpi_gatos_callejeros.modelo.Adopcion;
import com.mycompany.tpi_gatos_callejeros.modelo.Estado;
import com.mycompany.tpi_gatos_callejeros.modelo.Gato;
import com.mycompany.tpi_gatos_callejeros.modelo.HistorialMedico;
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
public class GatoJpaController implements Serializable {

    public GatoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Gato gato) {
        if (gato.getVisitas() == null) {
            gato.setVisitas(new ArrayList<VistaDeSeguimiento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Adopcion adopcion = gato.getAdopcion();
            if (adopcion != null) {
                adopcion = em.getReference(adopcion.getClass(), adopcion.getIdAdopcion());
                gato.setAdopcion(adopcion);
            }
            Estado estado = gato.getEstado();
            if (estado != null) {
                estado = em.getReference(estado.getClass(), estado.getIdEstado());
                gato.setEstado(estado);
            }
            HistorialMedico historial = gato.getHistorial();
            if (historial != null) {
                historial = em.getReference(historial.getClass(), historial.getCodHistorial());
                gato.setHistorial(historial);
            }
            List<VistaDeSeguimiento> attachedVisitas = new ArrayList<VistaDeSeguimiento>();
            for (VistaDeSeguimiento visitasVistaDeSeguimientoToAttach : gato.getVisitas()) {
                visitasVistaDeSeguimientoToAttach = em.getReference(visitasVistaDeSeguimientoToAttach.getClass(), visitasVistaDeSeguimientoToAttach.getIdVisita());
                attachedVisitas.add(visitasVistaDeSeguimientoToAttach);
            }
            gato.setVisitas(attachedVisitas);
            em.persist(gato);
            if (adopcion != null) {
                Gato oldGatoOfAdopcion = adopcion.getGato();
                if (oldGatoOfAdopcion != null) {
                    oldGatoOfAdopcion.setAdopcion(null);
                    oldGatoOfAdopcion = em.merge(oldGatoOfAdopcion);
                }
                adopcion.setGato(gato);
                adopcion = em.merge(adopcion);
            }
            if (estado != null) {
                estado.getGatos().add(gato);
                estado = em.merge(estado);
            }
            if (historial != null) {
                Gato oldGatoOfHistorial = historial.getGato();
                if (oldGatoOfHistorial != null) {
                    oldGatoOfHistorial.setHistorial(null);
                    oldGatoOfHistorial = em.merge(oldGatoOfHistorial);
                }
                historial.setGato(gato);
                historial = em.merge(historial);
            }
            for (VistaDeSeguimiento visitasVistaDeSeguimiento : gato.getVisitas()) {
                Gato oldGatoOfVisitasVistaDeSeguimiento = visitasVistaDeSeguimiento.getGato();
                visitasVistaDeSeguimiento.setGato(gato);
                visitasVistaDeSeguimiento = em.merge(visitasVistaDeSeguimiento);
                if (oldGatoOfVisitasVistaDeSeguimiento != null) {
                    oldGatoOfVisitasVistaDeSeguimiento.getVisitas().remove(visitasVistaDeSeguimiento);
                    oldGatoOfVisitasVistaDeSeguimiento = em.merge(oldGatoOfVisitasVistaDeSeguimiento);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Gato gato) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Gato persistentGato = em.find(Gato.class, gato.getIdGato());
            Adopcion adopcionOld = persistentGato.getAdopcion();
            Adopcion adopcionNew = gato.getAdopcion();
            Estado estadoOld = persistentGato.getEstado();
            Estado estadoNew = gato.getEstado();
            HistorialMedico historialOld = persistentGato.getHistorial();
            HistorialMedico historialNew = gato.getHistorial();
            List<VistaDeSeguimiento> visitasOld = persistentGato.getVisitas();
            List<VistaDeSeguimiento> visitasNew = gato.getVisitas();
            if (adopcionNew != null) {
                adopcionNew = em.getReference(adopcionNew.getClass(), adopcionNew.getIdAdopcion());
                gato.setAdopcion(adopcionNew);
            }
            if (estadoNew != null) {
                estadoNew = em.getReference(estadoNew.getClass(), estadoNew.getIdEstado());
                gato.setEstado(estadoNew);
            }
            if (historialNew != null) {
                historialNew = em.getReference(historialNew.getClass(), historialNew.getCodHistorial());
                gato.setHistorial(historialNew);
            }
            List<VistaDeSeguimiento> attachedVisitasNew = new ArrayList<VistaDeSeguimiento>();
            for (VistaDeSeguimiento visitasNewVistaDeSeguimientoToAttach : visitasNew) {
                visitasNewVistaDeSeguimientoToAttach = em.getReference(visitasNewVistaDeSeguimientoToAttach.getClass(), visitasNewVistaDeSeguimientoToAttach.getIdVisita());
                attachedVisitasNew.add(visitasNewVistaDeSeguimientoToAttach);
            }
            visitasNew = attachedVisitasNew;
            gato.setVisitas(visitasNew);
            gato = em.merge(gato);
            if (adopcionOld != null && !adopcionOld.equals(adopcionNew)) {
                adopcionOld.setGato(null);
                adopcionOld = em.merge(adopcionOld);
            }
            if (adopcionNew != null && !adopcionNew.equals(adopcionOld)) {
                Gato oldGatoOfAdopcion = adopcionNew.getGato();
                if (oldGatoOfAdopcion != null) {
                    oldGatoOfAdopcion.setAdopcion(null);
                    oldGatoOfAdopcion = em.merge(oldGatoOfAdopcion);
                }
                adopcionNew.setGato(gato);
                adopcionNew = em.merge(adopcionNew);
            }
            if (estadoOld != null && !estadoOld.equals(estadoNew)) {
                estadoOld.getGatos().remove(gato);
                estadoOld = em.merge(estadoOld);
            }
            if (estadoNew != null && !estadoNew.equals(estadoOld)) {
                estadoNew.getGatos().add(gato);
                estadoNew = em.merge(estadoNew);
            }
            if (historialOld != null && !historialOld.equals(historialNew)) {
                historialOld.setGato(null);
                historialOld = em.merge(historialOld);
            }
            if (historialNew != null && !historialNew.equals(historialOld)) {
                Gato oldGatoOfHistorial = historialNew.getGato();
                if (oldGatoOfHistorial != null) {
                    oldGatoOfHistorial.setHistorial(null);
                    oldGatoOfHistorial = em.merge(oldGatoOfHistorial);
                }
                historialNew.setGato(gato);
                historialNew = em.merge(historialNew);
            }
            for (VistaDeSeguimiento visitasOldVistaDeSeguimiento : visitasOld) {
                if (!visitasNew.contains(visitasOldVistaDeSeguimiento)) {
                    visitasOldVistaDeSeguimiento.setGato(null);
                    visitasOldVistaDeSeguimiento = em.merge(visitasOldVistaDeSeguimiento);
                }
            }
            for (VistaDeSeguimiento visitasNewVistaDeSeguimiento : visitasNew) {
                if (!visitasOld.contains(visitasNewVistaDeSeguimiento)) {
                    Gato oldGatoOfVisitasNewVistaDeSeguimiento = visitasNewVistaDeSeguimiento.getGato();
                    visitasNewVistaDeSeguimiento.setGato(gato);
                    visitasNewVistaDeSeguimiento = em.merge(visitasNewVistaDeSeguimiento);
                    if (oldGatoOfVisitasNewVistaDeSeguimiento != null && !oldGatoOfVisitasNewVistaDeSeguimiento.equals(gato)) {
                        oldGatoOfVisitasNewVistaDeSeguimiento.getVisitas().remove(visitasNewVistaDeSeguimiento);
                        oldGatoOfVisitasNewVistaDeSeguimiento = em.merge(oldGatoOfVisitasNewVistaDeSeguimiento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                long id = gato.getIdGato();
                if (findGato(id) == null) {
                    throw new NonexistentEntityException("The gato with id " + id + " no longer exists.");
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
            Gato gato;
            try {
                gato = em.getReference(Gato.class, id);
                gato.getIdGato();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The gato with id " + id + " no longer exists.", enfe);
            }
            Adopcion adopcion = gato.getAdopcion();
            if (adopcion != null) {
                adopcion.setGato(null);
                adopcion = em.merge(adopcion);
            }
            Estado estado = gato.getEstado();
            if (estado != null) {
                estado.getGatos().remove(gato);
                estado = em.merge(estado);
            }
            HistorialMedico historial = gato.getHistorial();
            if (historial != null) {
                historial.setGato(null);
                historial = em.merge(historial);
            }
            List<VistaDeSeguimiento> visitas = gato.getVisitas();
            for (VistaDeSeguimiento visitasVistaDeSeguimiento : visitas) {
                visitasVistaDeSeguimiento.setGato(null);
                visitasVistaDeSeguimiento = em.merge(visitasVistaDeSeguimiento);
            }
            em.remove(gato);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Gato> findGatoEntities() {
        return findGatoEntities(true, -1, -1);
    }

    public List<Gato> findGatoEntities(int maxResults, int firstResult) {
        return findGatoEntities(false, maxResults, firstResult);
    }

    private List<Gato> findGatoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Gato.class));
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

    public Gato findGato(long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Gato.class, id);
        } finally {
            em.close();
        }
    }

    public int getGatoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Gato> rt = cq.from(Gato.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
