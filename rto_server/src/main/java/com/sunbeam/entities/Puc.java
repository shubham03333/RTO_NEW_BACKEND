
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "puc")
public class Puc {
	
	//| puc_id | registration_id | user_id | from_date  | to_date    | co2  | hc   | payment_id |
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "puc_id")
	private int id;
	private String puc_no;
	@Temporal(TemporalType.DATE)
	private Date from_date;
	@Temporal(TemporalType.DATE)
	private Date to_date;
	private float co2;
	private float hc;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private User user ;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "registration_id")
	private VehicleRegistration vehicleRegistration ;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "payment_no")
	private Payment payment;

	
	public Puc() {
	}


	public Puc(int puc_id, String puc_no, Date from_date, Date to_date, float co2, float hc, User user,
			VehicleRegistration vehicleRegistration, Payment payment) {
		this.id = puc_id;
		this.puc_no = puc_no;
		this.from_date = from_date;
		this.to_date = to_date;
		this.co2 = co2;
		this.hc = hc;
		this.user = user;
		this.vehicleRegistration = vehicleRegistration;
		this.payment = payment;
	}


	public int getId() {
		return id;
	}


	public void setId(int puc_id) {
		this.id = puc_id;
	}


	public String getPuc_no() {
		return puc_no;
	}


	public void setPuc_no(String puc_no) {
		this.puc_no = puc_no;
	}


	public Date getFrom_date() {
		return from_date;
	}


	public void setFrom_date(Date from_date) {
		this.from_date = from_date;
	}


	public Date getTo_date() {
		return to_date;
	}


	public void setTo_date(Date to_date) {
		this.to_date = to_date;
	}


	public float getCo2() {
		return co2;
	}


	public void setCo2(float co2) {
		this.co2 = co2;
	}


	public float getHc() {
		return hc;
	}


	public void setHc(float hc) {
		this.hc = hc;
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


	public Payment getPayment() {
		return payment;
	}


	public void setPayment(Payment payment) {
		this.payment = payment;
	}


	@Override
	public String toString() {
		return String.format(
				"Puc [puc_id=%s, puc_no=%s, from_date=%s, to_date=%s, co2=%s, hc=%s, user=%s, vehicleRegistration=%s, payment=%s]",
				id, puc_no, from_date, to_date, co2, hc, user, vehicleRegistration, payment);
	}

	

}
