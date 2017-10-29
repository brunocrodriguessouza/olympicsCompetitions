package com.olympicscompetitions;

import java.util.Calendar;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.beans.factory.annotation.Autowired;

import com.olympicscompetitions.dao.CompetitionDaoMysqlImpl;
import com.olympicscompetitions.entity.Competition;
import com.olympicscompetitions.entity.Modality;


public class TestApp {
	
	@Autowired
	CompetitionDaoMysqlImpl competitionDao;
		
	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("olympics-competition-pu");
		EntityManager em = factory.createEntityManager();
				
		Calendar startDateCalendar = Calendar.getInstance();
		startDateCalendar.set(2017, Calendar.OCTOBER, 29, 19, 00);
		startDateCalendar.set(Calendar.SECOND, 00);
		startDateCalendar.set(Calendar.MILLISECOND, 00);
		
		Calendar endDateCalendar = Calendar.getInstance();
		endDateCalendar.set(2017, Calendar.OCTOBER, 29, 20, 00);
		endDateCalendar.set(Calendar.SECOND, 00);
		endDateCalendar.set(Calendar.MILLISECOND, 00);
		
//		Modality soccer = new Modality("Soccer");
		
		
		
		Competition competition = new Competition("Soccer","Tokyo", startDateCalendar, endDateCalendar, "Brazil","Germany", Stage.FINAL );
//		Competition competition = new Competition("Futebol2","Campinas", "29/10/2017 17:00:00", "29/10/2017 19:00:00", "São Paulo","Santos", Stage.FINAL_OF_OCTAVES );
		
		em.getTransaction().begin();
//		em.persist(soccer);
		
		em.persist(competition);

		em.getTransaction().commit();
		em.close();
	}

}
