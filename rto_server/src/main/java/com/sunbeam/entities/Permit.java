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
@Table(name = "permit")
public class Permit {

	//permit_id | permit_no| registration_id| user_id| from_date| to_date| from_state| to_state| payment_id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "permit_id")
	private int id;
	private String permit_no;
	@Temporal(TemporalType.DATE)
	private Date from_date;
	@Temporal(TemporalType.DATE)
	private Date to_date;
	private String from_state;
	private String to_state;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="registration_id")
	private VehicleRegistration vehicleRegistration;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="payment_no")
	private Payment payment  ;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private User user ;

	public Permit(int id, String permit_no, Date from_date, Date to_date, String from_state, String to_state,
			VehicleRegistration vehicleRegistration, Payment payment, User user) {
		super();
		this.id = id;
		this.permit_no = permit_no;
		this.from_date = from_date;
		this.to_date = to_date;
		this.from_state = from_state;
		this.to_state = to_state;
		this.vehicleRegistration = vehicleRegistration;
		this.payment = payment;
		this.user = user;
	}

	public Permit() {
	}

	public int getId() {
		return id;
	}

	public User getUser() {
		return user;
	}
//	@JsonBackReference
	public void setUser(User user) {
		this.user = user;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPermit_no() {
		return permit_no;
	}

	public void setPermit_no(String permit_no) {
		this.permit_no = permit_no;
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

	public String getFrom_state() {
		return from_state;
	}

	public void setFrom_state(String from_state) {
		this.from_state = from_state;
	}

	public String getTo_state() {
		return to_state;
	}

	public void setTo_state(String to_state) {
		this.to_state = to_state;
	}
//	@JsonBackReference
	public VehicleRegistration getVehicleRegistration() {
		return vehicleRegistration;
	}

	public void setVehicleRegistration(VehicleRegistration vehicleRegistration) {
		this.vehicleRegistration = vehicleRegistration;
	}
	
//	@JsonBackReference
	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	@Override
	public String toString() {
		return String.format(
				"Permit [permit_id=%s, permit_no=%s, from_date=%s, to_date=%s, from_state=%s, to_state=%s, vehicleRegistration=%s, payment=%s]",
				id, permit_no, from_date, to_date, from_state, to_state, vehicleRegistration, payment);
	}

	
}
