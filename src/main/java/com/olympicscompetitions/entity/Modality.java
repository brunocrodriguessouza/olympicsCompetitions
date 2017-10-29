package com.olympicscompetitions.entity;

public class Modality {
	
	public Modality() {
	}
	
	public Modality(String description) {
		this.description = description;
	}
	

	private Integer id;
	
	String description;



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
