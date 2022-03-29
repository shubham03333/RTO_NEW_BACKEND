package com.sunbeam.dtos;

public class LearningLicenceDTO {
	private int id;
	private Integer pendingCount=0;
	
	public LearningLicenceDTO() {
	}

	public LearningLicenceDTO(int id, Integer pendingCount) {
		this.id = id;
		this.pendingCount = pendingCount;
	}
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getPendingCount() {
		return pendingCount;
	}

	public void setPendingCount(Integer pendingCount) {
		this.pendingCount = pendingCount;
	}

	@Override
	public String toString() {
		return String.format("LearningLicenceDTO [id=%s, pendingCount=%s]", id, pendingCount);
	}
	
}
