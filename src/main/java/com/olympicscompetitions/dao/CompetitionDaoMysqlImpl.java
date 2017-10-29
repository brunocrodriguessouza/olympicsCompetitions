package com.olympicscompetitions.dao;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.olympicscompetitions.Stage;
import com.olympicscompetitions.entity.Competition;
import com.olympicscompetitions.util.JPAUtil;

@Repository
@Qualifier("mysqlData")
public class CompetitionDaoMysqlImpl implements CompetitionDao {

	EntityManager entityManager;

	public CompetitionDaoMysqlImpl() {
		entityManager = new JPAUtil().getEntityManager();
	}

	public Collection<Competition> getAllCompetitions() {
		return this.entityManager.createQuery("select c from Competition c order by startDateTime", Competition.class)
				.getResultList();
	}

	public Competition getCompetitionById(int id) {
		return this.entityManager.find(Competition.class, id);
	}

	@Override
	public List<Competition> getCompetitionByModality(String modality) {
		TypedQuery<Competition> createQuery = this.entityManager.createQuery(
				"select c from Competition c where c.modality like :modalityParam order by startDateTime",
				Competition.class);
		createQuery.setParameter("modalityParam", modality);
		return createQuery.getResultList();
	}

	public void removeCompetitionById(int id) {
		startTransacation();
		Competition competitionToRemove = this.entityManager.find(Competition.class, id);
		this.entityManager.remove(competitionToRemove);
		this.entityManager.getTransaction().commit();
	}

	public Competition updateCompetition(Competition competition) {
		startTransacation();
		this.entityManager.merge(competition);
		this.entityManager.getTransaction().commit();

		return competition;

	}

	public Competition insertCompetitionToDb(Competition competition) {
		startTransacation();
		this.entityManager.persist(competition);
		this.entityManager.getTransaction().commit();

		return competition;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Competition> getTimeConflict(Competition competition) {
		List<Competition> listTimeConflict = this.entityManager.createNamedQuery("Competition.timeConflict")
				.setParameter("dateParam", competition.getStartDateTime())
				.setParameter("localParam", competition.getLocal())
				.setParameter("modalityParam", competition.getModality()).getResultList();
		return listTimeConflict;
	}

	private void startTransacation() {
		if (!entityManager.getTransaction().isActive()) {
			entityManager.getTransaction().begin();
		}
	}
}
