package com.sunbeam.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "dl_table")
public class DrivingLicence {

	// | dl_id | payment_id | user_id | temp_LL_no | dl_issue_date | dl_expiry_date
	// |
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "dl_id")
	private int id;
	private int dl_no;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private  User user;
	@Temporal(TemporalType.DATE)
	private Date dl_issue_date;
	@Temporal(TemporalType.DATE)
	private Date dl_expiry_date;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "temp_LL_no")
	private LearningLicence learningLicence;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "payment_no")
	private Payment payment;

	public DrivingLicence() {
	}

	
	

	public DrivingLicence(int id, int dl_no, Date dl_issue_date, Date dl_expiry_date,
			LearningLicence learningLicence, Payment payment) {
		this.id = id;
		this.dl_no = dl_no;
		this.dl_issue_date = dl_issue_date;
		this.dl_expiry_date = dl_expiry_date;
		this.learningLicence = learningLicence;
		this.payment = payment;
	}
	
	


	public int getId() {
		return id;
	}




	public int getDl_no() {
		return dl_no;
	}








	public Date getDl_issue_date() {
		return dl_issue_date;
	}




	public Date getDl_expiry_date() {
		return dl_expiry_date;
	}




	public LearningLicence getLearningLicence() {
		return learningLicence;
	}




	public Payment getPayment() {
		return payment;
	}




	public void setId(int id) {
		this.id = id;
	}




	public void setDl_no(int dl_no) {
		this.dl_no = dl_no;
	}








	public void setDl_issue_date(Date dl_issue_date) {
		this.dl_issue_date = dl_issue_date;
	}




	public void setDl_expiry_date(Date dl_expiry_date) {
		this.dl_expiry_date = dl_expiry_date;
	}




	public void setLearningLicence(LearningLicence learningLicence) {
		this.learningLicence = learningLicence;
	}




	public void setPayment(Payment payment) {
		this.payment = payment;
	}




	public User getUser() {
		return user;
	}




	public void setUser(User user) {
		this.user = user;
	}




	@Override
	public String toString() {
		return String.format(
				"DrivingLicence [dl_id=%s, dl_no=%s, dl_issue_date=%s, dl_expiry_date=%s, learningLicence=%s, payment=%s]",
				id, dl_no,  dl_issue_date, dl_expiry_date, learningLicence, payment);
	}

}
