package org.example.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceUtil {

  private static EntityManagerFactory sessionFactory = null;

  public static EntityManagerFactory getSessionFactory() {
    if (sessionFactory == null) {
      sessionFactory = Persistence.createEntityManagerFactory("org.example.jpa");
    }
    return sessionFactory;
  }

}
