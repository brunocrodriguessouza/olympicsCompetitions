package com.olympicsCompetitions.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.olympicscompetitions.Stage;
import com.olympicscompetitions.dao.CompetitionDao;
import com.olympicscompetitions.entity.Competition;
import com.olympicscompetitions.exception.ExceptionDurationOfCompetition;
import com.olympicscompetitions.exception.ExceptionSameCountry;
import com.olympicscompetitions.exception.ExceptionTimeConflict;
import com.olympicscompetitions.service.CompetitionService;

public class CompetitionServiceTest {
	
	@Mock
	private CompetitionDao competitionDao;
	CompetitionService competitionService;
	
	@Before
	public void init() {
		competitionService = new CompetitionService();
		competitionDao = Mockito.mock(CompetitionDao.class);
		Mockito.when(competitionDao.insertCompetitionToDb(Mockito.any())).thenReturn(null);
		competitionService.setCompetitionDao(competitionDao);
	}
	
	@Test(expected = ExceptionTimeConflict.class)
	public void insertCompetitionWithTimeConflict() throws ExceptionTimeConflict, ExceptionSameCountry, ExceptionDurationOfCompetition{
		Calendar startDateCalendar = Calendar.getInstance();
		startDateCalendar.set(2017, Calendar.OCTOBER, 29, 12, 00);
		startDateCalendar.set(Calendar.SECOND, 00);
		
		Calendar endDateCalendar = Calendar.getInstance();
		endDateCalendar.set(2017, Calendar.OCTOBER, 29, 12, 30);
		endDateCalendar.set(Calendar.SECOND, 00);
		Competition competition = createCompetitionMockWith(startDateCalendar,endDateCalendar);
		
		Mockito.when(competitionDao.getTimeConflict(Mockito.any())).thenReturn(Arrays.asList(getCompetitionWithDateTimeConflictDB()));
		
		competitionService.insertCompetition(competition);
	}
	
	@Test(expected = ExceptionSameCountry.class)
	public void insertCompetitionWithSameCountryConflict() throws ExceptionTimeConflict, ExceptionSameCountry, ExceptionDurationOfCompetition{
		
		Competition competition = getCompetitionWithSameCountryConflict();
		competitionService.insertCompetition(competition);
		assertEquals(Stage.QUALIFIERS, competition.getStage());
		assertEquals("Brazil", competition.getCountry1());
		assertEquals("Brazil", competition.getCountry2());
		
	}
	
	@Test(expected = ExceptionDurationOfCompetition.class)
	public void insertCompetitionWithInvalidDurationOfCompetition() throws ExceptionTimeConflict, ExceptionSameCountry, ExceptionDurationOfCompetition{
		
		Competition competition = getCompetitionWithInvalidDurationOfCompetition();
		competitionService.insertCompetition(competition);
		assertEquals("2017-10-29 12:00:00", competition.getStartDateTime());
		assertEquals("2017-10-29 12:10:00", competition.getEndDateTime());
		
	}
	
	@Test
	public void insertCompetitionWithSuccess() throws ExceptionTimeConflict, ExceptionSameCountry, ExceptionDurationOfCompetition{
		Calendar startDateCalendar = Calendar.getInstance();
		startDateCalendar.set(2017, Calendar.OCTOBER, 29, 14, 00);
		startDateCalendar.set(Calendar.SECOND, 00);
		
		Calendar endDateCalendar = Calendar.getInstance();
		endDateCalendar.set(2017, Calendar.OCTOBER, 29, 14, 30);
		endDateCalendar.set(Calendar.SECOND, 00);
		Competition competition = createCompetitionMockWith(startDateCalendar,endDateCalendar);
		competition.setId(1);
		
		Mockito.when(competitionDao.getAllCompetitions()).thenReturn(Arrays.asList(competition));
		
		competitionService.insertCompetition(competition);
		List<Competition> allCompetition = (List<Competition>) competitionDao.getAllCompetitions();
		assertTrue(allCompetition.size() == 1);
		assertNotNull(competition.getId());
		assertEquals("Soccer", competition.getModality());
		assertEquals("Brazil", competition.getCountry1());
		assertEquals("Germany", competition.getCountry2());
	}
	
	private Competition getCompetitionWithDateTimeConflictDB() {
		Calendar startDateCalendar = Calendar.getInstance();
		startDateCalendar.set(2017, Calendar.OCTOBER, 29, 12, 00);
		startDateCalendar.set(Calendar.SECOND, 00);
		
		Calendar endDateCalendar = Calendar.getInstance();
		endDateCalendar.set(2017, Calendar.OCTOBER, 29, 12, 30);
		endDateCalendar.set(Calendar.SECOND, 00);
		return createCompetitionMockWith(startDateCalendar, endDateCalendar);
	}
	
	private Competition getCompetitionWithSameCountryConflict() {
		Calendar startDateCalendar = Calendar.getInstance();
		startDateCalendar.set(2017, Calendar.OCTOBER, 29, 12, 00);
		startDateCalendar.set(Calendar.SECOND, 00);
		
		Calendar endDateCalendar = Calendar.getInstance();
		endDateCalendar.set(2017, Calendar.OCTOBER, 29, 12, 30);
		endDateCalendar.set(Calendar.SECOND, 00);
		return createCompetitionMockWithSameCountry(startDateCalendar, endDateCalendar);
	}
	
	private Competition getCompetitionWithInvalidDurationOfCompetition() {
		Calendar startDateCalendar = Calendar.getInstance();
		startDateCalendar.set(2017, Calendar.OCTOBER, 29, 12, 00);
		startDateCalendar.set(Calendar.SECOND, 00);
		
		Calendar endDateCalendar = Calendar.getInstance();
		endDateCalendar.set(2017, Calendar.OCTOBER, 29, 12, 10);
		endDateCalendar.set(Calendar.SECOND, 00);
		return createCompetitionWithInvalidDurationOfCompetition(startDateCalendar, endDateCalendar);
	}

	private Competition createCompetitionMockWith(Calendar startDateCalendar, Calendar endDateCalendar) {
		Competition competition = new Competition("Soccer","Estadium1", startDateCalendar, endDateCalendar, "Brazil","Germany", Stage.QUALIFIERS );
		return competition;
	}
	
	private Competition createCompetitionMockWithSameCountry(Calendar startDateCalendar, Calendar endDateCalendar) {
		Competition competition = new Competition("Soccer","Estadium1", startDateCalendar, endDateCalendar, "Brazil","Brazil", Stage.QUALIFIERS );
		return competition;
	}
	
	private Competition createCompetitionWithInvalidDurationOfCompetition(Calendar startDateCalendar, Calendar endDateCalendar) {
		Competition competition = new Competition("Soccer","Estadium1", startDateCalendar, endDateCalendar, "Brazil","Germany", Stage.QUALIFIERS );
		return competition;
	}

}
