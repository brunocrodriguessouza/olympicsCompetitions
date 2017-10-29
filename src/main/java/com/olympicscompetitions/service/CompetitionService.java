package com.olympicscompetitions.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.olympicscompetitions.Stage;
import com.olympicscompetitions.dao.CompetitionDao;

import com.olympicscompetitions.entity.Competition;
import com.olympicscompetitions.exception.ExceptionDurationOfCompetition;
import com.olympicscompetitions.exception.ExceptionSameCountry;
import com.olympicscompetitions.exception.ExceptionTimeConflict;


@Service
public class CompetitionService {
	
	@Autowired
	@Qualifier("mysqlData")
	private CompetitionDao competitionDao;
	
	public Collection<Competition> getAllCompetitions(){
		return this.competitionDao.getAllCompetitions();
	}
	
	public Competition getCompetitionById(int id){
		return this.competitionDao.getCompetitionById(id);
	}
	
	public List<Competition> getCompetitionByModality(String modality) {
		return this.competitionDao.getCompetitionByModality(modality);
	}

	public void removeCompetitionById(int id) {
		this.competitionDao.removeCompetitionById(id);
	}
	
	public Competition updateCompetition(Competition competition) throws ExceptionTimeConflict, ExceptionSameCountry, ExceptionDurationOfCompetition{
		checkToSameCountry(competition);
		checkTimeConflict(competition);	
		checkDurationOfCompetition(competition);
		return this.competitionDao.updateCompetition(competition);
	}

	public Competition insertCompetition(Competition competition) throws ExceptionTimeConflict, ExceptionSameCountry, ExceptionDurationOfCompetition {
		checkToSameCountry(competition);
		checkTimeConflict(competition);
		checkDurationOfCompetition(competition);
		return this.competitionDao.insertCompetitionToDb(competition);
	}

	public void setCompetitionDao(CompetitionDao competitionDao){
		this.competitionDao = competitionDao;
	}
	
	private void checkTimeConflict(Competition competition) throws ExceptionTimeConflict {
		List<Competition> listTimeConflict = competitionDao.getTimeConflict(competition);
		if (!listTimeConflict.isEmpty() && (competition.getId() == null || listTimeConflict.get(0).getId().compareTo(competition.getId()) != 0)) {
			throw new ExceptionTimeConflict();
		}
	}
	
	public void checkToSameCountry(Competition competition) throws ExceptionSameCountry {
		if (competition.getCountry1().equals(competition.getCountry2()) && 
				(!competition.getStage().equals(Stage.FINAL) &&
				!competition.getStage().equals(Stage.SEMI_FINALS)) ) {
			throw new ExceptionSameCountry();
		}
	}
	
	public void checkDurationOfCompetition(Competition competition) throws ExceptionDurationOfCompetition{
		long startDate = competition.getStartDateTime().getTimeInMillis();
		long endDate = competition.getEndDateTime().getTimeInMillis();
		
		
		if( endDate - startDate < 1800000){
			throw new ExceptionDurationOfCompetition();
		}
	}

}
