package com.sunbeam.entities;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "complaints")
public class Complain {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "complain_id")
	private int id;
	private String complain_no;
	private String description;
	private int user_id;
	private Integer pendingCount=0;
	private String status="Pending";
	
//	@JsonIgnore
//	@ManyToOne(cascade = CascadeType.ALL)
//	private User user;
	private String aadhar_no;

	public Complain() {
	}

	
//	public Complain(int id, String complain_no, String description, int user_id, Integer pendingCount, User user,
//			String aadhar_no) {
//		super();
//		this.id = id;
//		this.complain_no = complain_no;
//		this.description = description;
//		this.user_id = user_id;
//		this.pendingCount = pendingCount;
//		this.user = user;
//		this.aadhar_no = aadhar_no;
//	}

	
	

	public Complain(int id, String complain_no, String description, int user_id, Integer pendingCount, String status,
			String aadhar_no) {
		this.id = id;
		this.complain_no = complain_no;
		this.description = description;
		this.user_id = user_id;
		this.pendingCount = pendingCount;
		this.status = status;
		this.aadhar_no = aadhar_no;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	public String getComplain_no() {
		return complain_no;
	}

	public void setComplain_no(String complain_no) {
		this.complain_no = complain_no;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

//	public User getUser() {
//		return user;
//	}
//
//	public void setUser(User user) {
//		this.user = user;
//	}

	public String getAadhar_no() {
		return aadhar_no;
	}

	public void setAadhar_no(String aadhar_no) {
		this.aadhar_no = aadhar_no;
	}
	

	public Integer getPendingCount() {
		return pendingCount;
	}


	public void setPendingCount(Integer pendingCount) {
		this.pendingCount = pendingCount;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	@Override
	public String toString() {
		return String.format(
				"Complain [id=%s, complain_no=%s, description=%s, user_id=%s, pendingCount=%s, status=%s, aadhar_no=%s]",
				id, complain_no, description, user_id, pendingCount, status, aadhar_no);
	}

//	@Override
//	public String toString() {
//		return String.format(
//				"Complain [id=%s, complain_no=%s, description=%s, user_id=%s, pendingCount=%s, status=%s, user=%s, aadhar_no=%s]",
//				id, complain_no, description, user_id, pendingCount, status, user, aadhar_no);
//	}



}
