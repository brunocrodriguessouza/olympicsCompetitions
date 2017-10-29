package com.olympicscompetitions.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.olympicscompetitions.entity.Competition;

@Repository
@Qualifier("hsqldbData")
public class CompetitionDaoHsqldbImpl implements CompetitionDao{

	@Override
	public Collection<Competition> getAllCompetitions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Competition getCompetitionById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Competition> getCompetitionByModality(String modality) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeCompetitionById(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Competition updateCompetition(Competition competition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Competition insertCompetitionToDb(Competition competition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Competition> getTimeConflict(Competition competition) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
