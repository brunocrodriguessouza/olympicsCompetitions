package com.olympicscompetitions.dao;

import java.util.Collection;
import java.util.List;

import com.olympicscompetitions.entity.Competition;

public interface CompetitionDao {

	Collection<Competition> getAllCompetitions();

	Competition getCompetitionById(int id);
	
	List<Competition> getCompetitionByModality(String modality);

	void removeCompetitionById(int id);

	Competition updateCompetition(Competition competition);

	Competition insertCompetitionToDb(Competition competition);
	

	List<Competition> getTimeConflict(Competition competition);

	List<Competition> getAllCompetitionByDayAndLocation(Competition competition);

}