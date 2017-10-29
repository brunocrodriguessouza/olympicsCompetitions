package com.olympicscompetitions.entity;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.olympicscompetitions.Stage;

@Entity
@Table(name = "competition")
@NamedQuery(name = "Competition.timeConflict", query = "SELECT c FROM Competition c where c.startDateTime <= :dateParam and c.endDateTime >= :dateParam and c.local = :localParam and c.modality = :modalityParam")

public class Competition {

	@Id
	@GeneratedValue
	private Integer id;
	private String modality;
	private String local;
	private Calendar startDateTime;
	private Calendar endDateTime;
	private String country1;
	private String country2;
	private Stage stage;

	public Competition() {
	}

	public Competition(String modality, String local, Calendar startDateTime, Calendar endDateTime, String country1,
			String country2, Stage stage) {
		super();
		this.modality = modality;
		this.local = local;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.country1 = country1;
		this.country2 = country2;
		this.stage = stage;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getModality() {
		return modality;
	}

	public void setModality(String modality) {
		this.modality = modality;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public Calendar getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(Calendar startDateTime) {
		this.startDateTime = startDateTime;
	}

	public Calendar getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(Calendar endDateTime) {
		this.endDateTime = endDateTime;
	}

	public String getCountry1() {
		return country1;
	}

	public void setCountry1(String country1) {
		this.country1 = country1;
	}

	public String getCountry2() {
		return country2;
	}

	public void setCountry2(String country2) {
		this.country2 = country2;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
}
