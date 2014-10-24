package net.orgyan.utils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author yandry pozo
 */
public class JPAUtil {

    private static final EntityManagerFactory emf;
    private static final String persitence_name = "JPASwingCrudPU";

    static {
        emf = Persistence.createEntityManagerFactory(persitence_name);
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }
}
