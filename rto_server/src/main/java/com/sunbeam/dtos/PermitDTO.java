package com.sunbeam.dtos;

public class PermitDTO {
	private int id;
	private Integer pendingCount = 0;
	private int count = 0;
	public PermitDTO() {
	}
	public PermitDTO(int id, Integer pendingCount, int count) {
		this.id = id;
		this.pendingCount = pendingCount;
		this.count = count;
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
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return String.format("PermitDTO [id=%s, pendingCount=%s, count=%s]", id, pendingCount, count);
	}
	
	
	
}
