
package com.sunbeam.entities;

import java.util.Date;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private User user ;
	private String aadhar_no;
	private String status;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "registration_id")
	private VehicleRegistration vehicleRegistration ;

	
	private String registration_no;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "payment_no")
	private Payment payment;

	
	public Puc() {
	}


	


public Puc(int id, String puc_no, Date from_date, Date to_date, float co2, float hc, User user, String aadhar_no,
			String status, VehicleRegistration vehicleRegistration, String registration_no, Payment payment) {
		super();
		this.id = id;
		this.puc_no = puc_no;
		this.from_date = from_date;
		this.to_date = to_date;
		this.co2 = co2;
		this.hc = hc;
		this.user = user;
		this.aadhar_no = aadhar_no;
		this.status = status;
		this.vehicleRegistration = vehicleRegistration;
		this.registration_no = registration_no;
		this.payment = payment;
	}





/////////////////////////////////////////////////////////////////////

	

	public int getId() {
	return id;
}





public void setId(int id) {
	this.id = id;
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





public String getAadhar_no() {
	return aadhar_no;
}





public void setAadhar_no(String aadhar_no) {
	this.aadhar_no = aadhar_no;
}





public String getStatus() {
	return status;
}





public void setStatus(String status) {
	this.status = status;
}





public VehicleRegistration getVehicleRegistration() {
	return vehicleRegistration;
}





public void setVehicleRegistration(VehicleRegistration vehicleRegistration) {
	this.vehicleRegistration = vehicleRegistration;
}





public String getRegistration_no() {
	return registration_no;
}





public void setRegistration_no(String registration_no) {
	this.registration_no = registration_no;
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
			"Puc [id=%s, puc_no=%s, from_date=%s, to_date=%s, co2=%s, hc=%s, user=%s, aadhar_no=%s, status=%s, vehicleRegistration=%s, registration_no=%s, payment=%s]",
			id, puc_no, from_date, to_date, co2, hc, user, aadhar_no, status, vehicleRegistration, registration_no,
			payment);
}





	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Puc))
			return false;
		Puc other = (Puc) obj;
		return id == other.id;
	}

	

}
