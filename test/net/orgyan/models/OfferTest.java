package net.orgyan.models;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import net.orgyan.controllers.OfferJpaController;
import net.orgyan.controllers.exceptions.IllegalOrphanException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author yandry pozo
 */
public class OfferTest {
    
    final String persitence_name = "JPASwingCrudPU";
    OfferJpaController offJC;
    EntityManagerFactory emf;

    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory(persitence_name);
        this.offJC = new OfferJpaController(emf);
    }

    @After
    public void tearDown() {
        emf.close();
    }

    @Test
    public void newOfferCar() throws IllegalOrphanException {
        int amount = offJC.getOfferCount();

        Offer offer = new Offer();
        offer.setName("Car");
        offer.setValue(5);
        offJC.create(offer);
        
        assertEquals(amount + 1, offJC.getOfferCount());
    }
    
}
