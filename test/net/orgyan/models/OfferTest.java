package net.orgyan.models;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import net.orgyan.controllers.OfferJpaController;
import net.orgyan.controllers.exceptions.IllegalOrphanException;
import net.orgyan.utils.JPAUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author yandry pozo
 */
public class OfferTest {

    OfferJpaController offerJPAController;

    @Before
    public void setUp() {
        this.offerJPAController = new OfferJpaController(JPAUtil.getEntityManagerFactory());
    }

    @After
    public void tearDown() {
    }

    @Test
    public void newOfferCar() throws IllegalOrphanException {
        int amount = offerJPAController.getOfferCount();

        Offer offer = new Offer();
        offer.setName("Car");
        offer.setValue(5);
        offerJPAController.create(offer);

        assertEquals(amount + 1, offerJPAController.getOfferCount());
    }

}
