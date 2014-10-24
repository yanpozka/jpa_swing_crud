package net.orgyan.controllers;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import net.orgyan.controllers.exceptions.NonexistentEntityException;
import net.orgyan.models.Offer;

/**
 *
 * @author yandry pozo
 */
public class OfferJpaController implements Serializable {

    public OfferJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Offer offer) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(offer);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Offer offer) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            offer = em.merge(offer);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = offer.getId();
                if (findOffer(id) == null) {
                    throw new NonexistentEntityException("The offer with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Offer offer;
            try {
                offer = em.getReference(Offer.class, id);
                offer.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The offer with id " + id + " no longer exists.", enfe);
            }
            em.remove(offer);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Offer> findOfferEntities() {
        return findOfferEntities(true, -1, -1);
    }

    public List<Offer> findOfferEntities(int maxResults, int firstResult) {
        return findOfferEntities(false, maxResults, firstResult);
    }

    private List<Offer> findOfferEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Offer.class));
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

    public Offer findOffer(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Offer.class, id);
        } finally {
            em.close();
        }
    }

    public int getOfferCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Offer> rt = cq.from(Offer.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
