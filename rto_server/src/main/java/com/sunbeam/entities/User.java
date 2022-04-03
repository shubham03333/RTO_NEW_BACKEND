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
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(name = "aadhar_no", columnNames = { "aadhar_no" }))
public class User {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "user_id")
	private int id;
	private String aadhar_no;
	private String name;
	private String role;
	@Temporal(TemporalType.DATE)
	private Date dob;
	private String address;
	private String gender;
	private String blood_group;
	private long mobile_no;
	private String email;
	private String password;
	private int photo_id;
	private String status="pending";

	// ############ Working #########
//	@OneToOne(cascade=CascadeType.REMOVE)
//	private DrivingLicence drivingLicence;

	// ##########################

//	@JoinColumn(name = "photo_id")
//	@OneToOne( fetch=FetchType.LAZY ,cascade = CascadeType.ALL)
//	private DatabaseFile databaseFile;

	// ############ Working #########
//	@OneToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "registration_id")
//	private VehicleRegistration registration;

	// ##########################

//	@OneToOne(cascade=CascadeType.ALL,orphanRemoval = true,mappedBy = "user")
//	@JoinColumn(referencedColumnName = "user_id")
//	@OneToOne(cascade=CascadeType.ALL)
//	private DrivingLicence drivingLicence;

	// ############ Working #########
//	@JsonManagedReference
//public VehicleRegistration getRegistration() {
//		return registration;
//	}
//
//	public void setRegistration(VehicleRegistration registration) {
//		this.registration = registration;
//	}
	// ############ Working #########

	// public VehicleRegistration getRegistration() {
//		return registration;
//	}
//
//	public void setRegistration(VehicleRegistration registration) {
//		this.registration = registration;
//	}
//
	public User() {
	}

	public User(int id, String aadhar_no, String name, String role, Date dob, String address, String gender,
			String blood_group, long mobile_no, String email, String password, int photo_id, String status) {
		this.id = id;
		this.aadhar_no = aadhar_no;
		this.name = name;
		this.role = role;
		this.dob = dob;
		this.address = address;
		this.gender = gender;
		this.blood_group = blood_group;
		this.mobile_no = mobile_no;
		this.email = email;
		this.password = password;
		this.photo_id = photo_id;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAadhar_no() {
		return aadhar_no;
	}

	public void setAadhar_no(String aadhar_no) {
		this.aadhar_no = aadhar_no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBlood_group() {
		return blood_group;
	}

	public void setBlood_group(String blood_group) {
		this.blood_group = blood_group;
	}

	public long getMobile_no() {
		return mobile_no;
	}

	public void setMobile_no(long mobile_no) {
		this.mobile_no = mobile_no;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPhoto_id() {
		return photo_id;
	}

	public void setPhoto_id(int photo_id) {
		this.photo_id = photo_id;
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
				"User [id=%s, aadhar_no=%s, name=%s, role=%s, dob=%s, address=%s, gender=%s, blood_group=%s, mobile_no=%s, email=%s, password=%s, photo_id=%s, status=%s]",
				id, aadhar_no, name, role, dob, address, gender, blood_group, mobile_no, email, password, photo_id,
				status);
	}

}
