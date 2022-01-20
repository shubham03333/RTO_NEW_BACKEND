package com.sunbeam.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "vehicle_transfer")
public class VehicleTransfer {
	
//id|user_id|registration_id|new_owner|new_owner_aadhar|new_owner_email|new_owner_mobile|payment_id

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "transfer_id")
	private int id;
	private String transfer_no;
	private String new_owner;
	private long new_owner_aadhar;

	private String new_owner_email;
	private long new_owner_mobile;
	
	public String getTransfer_no() {
		return transfer_no;
	}

	public void setTransfer_no(String transfer_no) {
		this.transfer_no = transfer_no;
	}

	
//	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "registration_id")
	private VehicleRegistration vehicleRegistration1;
	
//	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "payment_no",insertable = false)
	private Payment payment;
//	@Column(insertable = false)
	
	public VehicleTransfer() {
	}

	public VehicleTransfer(int id, int user_id, String new_owner, long new_owner_aadhar,
			String new_owner_email, long new_owner_mobile) {
		this.id = id;
		this.new_owner = new_owner;
		this.new_owner_aadhar = new_owner_aadhar;
		this.new_owner_email = new_owner_email;
		this.new_owner_mobile = new_owner_mobile;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNew_owner() {
		return new_owner;
	}

	public void setNew_owner(String new_owner) {
		this.new_owner = new_owner;
	}

	public long getNew_owner_aadhar() {
		return new_owner_aadhar;
	}

	public void setNew_owner_aadhar(long new_owner_aadhar) {
		this.new_owner_aadhar = new_owner_aadhar;
	}

	public String getNew_owner_email() {
		return new_owner_email;
	}

	public void setNew_owner_email(String new_owner_email) {
		this.new_owner_email = new_owner_email;
	}

	public long getNew_owner_mobile() {
		return new_owner_mobile;
	}

	public void setNew_owner_mobile(long new_owner_mobile) {
		this.new_owner_mobile = new_owner_mobile;
	}


//	@JsonBackReference
	public VehicleRegistration getVehicleRegistration1() {
		return vehicleRegistration1;
	}

	public void setVehicleRegistration1(VehicleRegistration vehicleRegistration1) {
		this.vehicleRegistration1 = vehicleRegistration1;
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
				"VehicleTransfer [id=%s, new_owner=%s, new_owner_aadhar=%s, new_owner_email=%s, new_owner_mobile=%s, vehicleRegistration1=%s, payment=%s]",
				id, new_owner, new_owner_aadhar, new_owner_email, new_owner_mobile, vehicleRegistration1,
				payment);
	}

	
	
}
