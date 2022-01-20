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

	// | payment_refno | user_id | payment_id | payment_mode | amount | payment_date
	// |
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "payment_no")
	private int id;
	private int payment_id;
	private String payment_mode;
	private double amount;
	@Temporal(TemporalType.DATE)
	private Date payment_date;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "registration_id")
	private VehicleRegistration vehicleRegistration;

	public Payment() {
	}

	public Payment(int id, int payment_id, String payment_mode, double amount, Date payment_date, User user,
			VehicleRegistration vehicleRegistration) {
		super();
		this.id = id;
		this.payment_id = payment_id;
		this.payment_mode = payment_mode;
		this.amount = amount;
		this.payment_date = payment_date;
		this.user = user;
		this.vehicleRegistration = vehicleRegistration;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPayment_id() {
		return payment_id;
	}

	public void setPayment_id(int payment_id) {
		this.payment_id = payment_id;
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

	public VehicleRegistration getVehicleRegistration() {
		return vehicleRegistration;
	}

	public void setVehicleRegistration(VehicleRegistration vehicleRegistration) {
		this.vehicleRegistration = vehicleRegistration;
	}

	@Override
	public String toString() {
		return String.format(
				"Payment [id=%s, payment_id=%s, payment_mode=%s, amount=%s, payment_date=%s, user=%s, vehicleRegistration=%s]",
				id, payment_id, payment_mode, amount, payment_date, user, vehicleRegistration);
	}

	

}
