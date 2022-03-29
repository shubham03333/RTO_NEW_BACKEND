package com.sunbeam.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
//@Table(name = "dl_table",uniqueConstraints = @UniqueConstraint(name= "ddl_no",columnNames = {"dl_no"} ))
@Table(name = "dl_table")
public class DrivingLicence {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "dl_id")
	private int id;
	private String dl_no;
	private int payment_id = 2;
	private String status = "Pending";
	private int user_id;
	private String L_category;
	private String rto;
	@Temporal(TemporalType.DATE)
	private Date dl_issue_date;
	@Temporal(TemporalType.DATE)
	private Date dl_expiry_date;
	private int transactionId;
	private int tempLLNo;

	// ############ Working #########
//	@OneToOne
//	private  User user;
	// ##########################

//	private  User user;
//	@JsonIgnore
//	@OneToOne(cascade = CascadeType.ALL,orphanRemoval = false)
	@OneToOne(orphanRemoval = false)
//	@OneToOne
	private User user;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "temp_LL_no")
	private LearningLicence learningLicence;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "transaction_no")
	private Payment payment;

	public DrivingLicence() {
	}

	public DrivingLicence(int id, String dl_no, int payment_id, String status, int user_id, String l_category,
			String rto, Date dl_issue_date, Date dl_expiry_date, int transactionId, int tempLLNo, User user,
			LearningLicence learningLicence, Payment payment) {
		this.id = id;
		this.dl_no = dl_no;
		this.payment_id = payment_id;
		this.status = status;
		this.user_id = user_id;
		L_category = l_category;
		this.rto = rto;
		this.dl_issue_date = dl_issue_date;
		this.dl_expiry_date = dl_expiry_date;
		this.transactionId = transactionId;
		this.tempLLNo = tempLLNo;
		this.user = user;
		this.learningLicence = learningLicence;
		this.payment = payment;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDl_no() {
		return dl_no;
	}

	public void setDl_no(String dl_no) {
		this.dl_no = dl_no;
	}

	public int getPayment_id() {
		return payment_id;
	}

	public void setPayment_id(int payment_id) {
		this.payment_id = payment_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getL_category() {
		return L_category;
	}

	public void setL_category(String l_category) {
		L_category = l_category;
	}

	public String getRto() {
		return rto;
	}

	public void setRto(String rto) {
		this.rto = rto;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getDl_issue_date() {
		return dl_issue_date;
	}

	public void setDl_issue_date(Date dl_issue_date) {
		this.dl_issue_date = dl_issue_date;
	}

	public Date getDl_expiry_date() {
		return dl_expiry_date;
	}

	public void setDl_expiry_date(Date dl_expiry_date) {
		this.dl_expiry_date = dl_expiry_date;
	}

	public LearningLicence getLearningLicence() {
		return learningLicence;
	}

	public void setLearningLicence(LearningLicence learningLicence) {
		this.learningLicence = learningLicence;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public int getTempLLNo() {
		return tempLLNo;
	}

	public void setTempLLNo(int tempLLNo) {
		this.tempLLNo = tempLLNo;
	}

	@Override
	public String toString() {
		return String.format(
				"DrivingLicence [id=%s, dl_no=%s, payment_id=%s, status=%s, user_id=%s, L_category=%s, rto=%s, dl_issue_date=%s, dl_expiry_date=%s, transactionId=%s, tempLLNo=%s, user=%s, learningLicence=%s, payment=%s]",
				id, dl_no, payment_id, status, user_id, L_category, rto, dl_issue_date, dl_expiry_date, transactionId,
				tempLLNo, user, learningLicence, payment);
	}

}
