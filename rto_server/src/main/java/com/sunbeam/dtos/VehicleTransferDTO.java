package com.sunbeam.dtos;

public class VehicleTransferDTO {
	
	private int id;
	private int count=0;
	private Integer pendingCount=0;
	public VehicleTransferDTO() {
	}
	public VehicleTransferDTO(int id, int count, Integer pendingCount) {
		this.id = id;
		this.count = count;
		this.pendingCount = pendingCount;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Integer getPendingCount() {
		return pendingCount;
	}
	public void setPendingCount(Integer pendingCount) {
		this.pendingCount = pendingCount;
	}
	@Override
	public String toString() {
		return String.format("VehicleTransferDTO [id=%s, count=%s, pendingCount=%s]", id, count, pendingCount);
	}
	
	
	

}
