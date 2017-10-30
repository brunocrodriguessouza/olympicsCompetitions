package com.olympicscompetitions.service;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.olympicscompetitions.dao.CompetitionDao;
import com.olympicscompetitions.entity.Competition;
import com.olympicscompetitions.entity.Stage;
import com.olympicscompetitions.exception.ExceptionDurationOfCompetition;
import com.olympicscompetitions.exception.ExceptionMoreThanFourCompetitionOnTheSameDay;
import com.olympicscompetitions.exception.ExceptionSameCountry;
import com.olympicscompetitions.exception.ExceptionTimeConflict;

@Service
public class CompetitionService {

	@Autowired
	@Qualifier("mysqlData")
	private CompetitionDao competitionDao;

	public Collection<Competition> getAllCompetitions() {
		return this.competitionDao.getAllCompetitions();
	}

	public Competition getCompetitionById(int id) {
		return this.competitionDao.getCompetitionById(id);
	}

	public List<Competition> getCompetitionByModality(String modality) {
		return this.competitionDao.getCompetitionByModality(modality);
	}

	public void removeCompetitionById(int id) {
		this.competitionDao.removeCompetitionById(id);
	}

	public Competition updateCompetition(Competition competition) throws ExceptionTimeConflict, ExceptionSameCountry,
			ExceptionDurationOfCompetition, ExceptionMoreThanFourCompetitionOnTheSameDay {
		checkAllValidation(competition);
		return this.competitionDao.updateCompetition(competition);
	}

	public Competition insertCompetition(Competition competition) throws ExceptionTimeConflict, ExceptionSameCountry,
			ExceptionDurationOfCompetition, ExceptionMoreThanFourCompetitionOnTheSameDay {
		checkAllValidation(competition);
		return this.competitionDao.insertCompetitionToDb(competition);
	}

	private void checkAllValidation(Competition competition) throws ExceptionSameCountry, ExceptionTimeConflict,
			ExceptionDurationOfCompetition, ExceptionMoreThanFourCompetitionOnTheSameDay {
		checkToSameCountry(competition);
		checkTimeConflict(competition);
		checkDurationOfCompetition(competition);
		checkAllCompetitionTheSameDayAndLocation(competition);
	}

	public void setCompetitionDao(CompetitionDao competitionDao) {
		this.competitionDao = competitionDao;
	}

	private void checkTimeConflict(Competition competition) throws ExceptionTimeConflict {
		List<Competition> listTimeConflict = competitionDao.getTimeConflict(competition);
		if (!listTimeConflict.isEmpty() && (competition.getId() == null
				|| listTimeConflict.get(0).getId().compareTo(competition.getId()) != 0)) {
			throw new ExceptionTimeConflict();
		}
	}

	public void checkToSameCountry(Competition competition) throws ExceptionSameCountry {
		if (competition.getCountry1().equals(competition.getCountry2())
				&& (!competition.getStage().equals(Stage.FINAL) && !competition.getStage().equals(Stage.SEMI_FINALS))) {
			throw new ExceptionSameCountry();
		}
	}

	public void checkDurationOfCompetition(Competition competition) throws ExceptionDurationOfCompetition {
		long startDate = competition.getStartDateTime().getTimeInMillis();
		long endDate = competition.getEndDateTime().getTimeInMillis();

		if (endDate - startDate < 1800000) {
			throw new ExceptionDurationOfCompetition();
		}
	}

	public void checkAllCompetitionTheSameDayAndLocation(Competition competition)
			throws ExceptionMoreThanFourCompetitionOnTheSameDay {
		
		List<Competition> competitionTheSameDayAndLocation = competitionDao
				.getAllCompetitionByDayAndLocation(competition);
		if (!competitionTheSameDayAndLocation.isEmpty()) {
			for (int i = 0; i < competitionTheSameDayAndLocation.size(); i++) {
				if ((competition.getId() == null
						|| competitionTheSameDayAndLocation.get(i).getId().compareTo(competition.getId()) != 0)
						&& (competitionTheSameDayAndLocation.size() == 4)) {
					throw new ExceptionMoreThanFourCompetitionOnTheSameDay();
				}
			}
		}
	}
}
