package com.sunbeam.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "payment_table")
public class Payment {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "transaction_no")
	private int id;
	private String payment_for;
	private String payment_mode;
	private double amount;
	private int user_id;
	@Temporal(TemporalType.DATE)
	private Date payment_date;

	@ManyToOne(cascade = CascadeType.ALL)
	private User user;

	@OneToOne(cascade = CascadeType.ALL)
	private VehicleRegistration vehicleRegistration;

	@OneToOne(cascade = CascadeType.ALL)
	private VehicleTransfer vehicleTransfer;

	@OneToOne(cascade = CascadeType.ALL)
	private Puc puc;

	@OneToOne(cascade = CascadeType.ALL)
	private Permit permit;

	@OneToOne(cascade = CascadeType.ALL)
	private LearningLicence learningLicence;

	@OneToOne(cascade = CascadeType.ALL)
	private DrivingLicence drivingLicence;

	private int vehicleRegistration_id;

	private int learningLicence_id;

	private int drivingLicence_id;

	private String lcategory;

	public Payment() {
	}

	public Payment(int id, String payment_for, String payment_mode, double amount, int user_id, Date payment_date,
			User user, VehicleRegistration vehicleRegistration, VehicleTransfer vehicleTransfer, Puc puc, Permit permit,
			LearningLicence learningLicence, DrivingLicence drivingLicence, int vehicleRegistration_id,
			int learningLicence_id, int drivingLicence_id, String lcategory) {
		this.id = id;
		this.payment_for = payment_for;
		this.payment_mode = payment_mode;
		this.amount = amount;
		this.user_id = user_id;
		this.payment_date = payment_date;
		this.user = user;
		this.vehicleRegistration = vehicleRegistration;
		this.vehicleTransfer = vehicleTransfer;
		this.puc = puc;
		this.permit = permit;
		this.learningLicence = learningLicence;
		this.drivingLicence = drivingLicence;
		this.vehicleRegistration_id = vehicleRegistration_id;
		this.learningLicence_id = learningLicence_id;
		this.drivingLicence_id = drivingLicence_id;
		this.lcategory = lcategory;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPayment_for() {
		return payment_for;
	}

	public void setPayment_for(String payment_for) {
		this.payment_for = payment_for;
	}

	public String getPayment_mode() {
		return payment_mode;
	}

	public void setPayment_mode(String payment_mode) {
		this.payment_mode = payment_mode;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public Date getPayment_date() {
		return payment_date;
	}

	public void setPayment_date(Date payment_date) {
		this.payment_date = payment_date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getVehicleRegistration_id() {
		return vehicleRegistration_id;
	}

	public void setVehicleRegistration_id(int vehicleRegistration_id) {
		this.vehicleRegistration_id = vehicleRegistration_id;
	}

	public String getLcategory() {
		return lcategory;
	}

	public void setLcategory(String lcategory) {
		this.lcategory = lcategory;
	}

	public VehicleRegistration getVehicleRegistration() {
		return vehicleRegistration;
	}

	public void setVehicleRegistration(VehicleRegistration vehicleRegistration) {
		this.vehicleRegistration = vehicleRegistration;
	}

	public VehicleTransfer getVehicleTransfer() {
		return vehicleTransfer;
	}

	public void setVehicleTransfer(VehicleTransfer vehicleTransfer) {
		this.vehicleTransfer = vehicleTransfer;
	}

	public Puc getPuc() {
		return puc;
	}

	public void setPuc(Puc puc) {
		this.puc = puc;
	}

	public Permit getPermit() {
		return permit;
	}

	public void setPermit(Permit permit) {
		this.permit = permit;
	}

	public LearningLicence getLearningLicence() {
		return learningLicence;
	}

	public void setLearningLicence(LearningLicence learningLicence) {
		this.learningLicence = learningLicence;
	}

	public DrivingLicence getDrivingLicence() {
		return drivingLicence;
	}

	public void setDrivingLicence(DrivingLicence drivingLicence) {
		this.drivingLicence = drivingLicence;
	}

	public int getLearningLicence_id() {
		return learningLicence_id;
	}

	public void setLearningLicence_id(int learningLicence_id) {
		this.learningLicence_id = learningLicence_id;
	}

	public int getdrivingLicence_id() {
		return drivingLicence_id;
	}

	public void setdrivingLicence_id(int ddrivingLicence_id) {
		this.drivingLicence_id = ddrivingLicence_id;
	}
	@Override
	public String toString() {
		return String.format(
				"Payment [id=%s, payment_for=%s, payment_mode=%s, amount=%s, user_id=%s, payment_date=%s, user=%s, vehicleRegistration=%s, vehicleTransfer=%s, puc=%s, permit=%s, learningLicence=%s, drivingLicence=%s, vehicleRegistration_id=%s, learningLicence_id=%s, drivingLicence_id=%s, lcategory=%s]",
				id, payment_for, payment_mode, amount, user_id, payment_date, user, vehicleRegistration,
				vehicleTransfer, puc, permit, learningLicence, drivingLicence, vehicleRegistration_id,
				learningLicence_id, drivingLicence_id, lcategory);
	}

}
