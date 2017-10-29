package com.olympicscompetitions.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
	
	private static EntityManagerFactory factory = Persistence
			.createEntityManagerFactory("olympics-competition-pu");

	public EntityManager getEntityManager() {
		return factory.createEntityManager();
	}	

}
