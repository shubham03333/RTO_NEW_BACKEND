package com.sunbeam.dtos;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Email;

import com.sunbeam.entities.DatabaseFile;

public class UserDTO {

	private int id;
	private String name;
	@Email
	private String email;
	private String password;
	private String aadhar_no;
	private String role;
	private Date dob;
	private String address;
	private String gender;
	private String blood_group;
	private long mobile_no;
	private int photo_id;
	private String status1;
	private String notification;

	private int count = 0;

	public UserDTO() {
	}

	

	public UserDTO(int id, String name, @Email String email, String password, String aadhar_no, String role, Date dob,
			String address, String gender, String blood_group, long mobile_no, int photo_id, String status1,
			String notification, int count) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.aadhar_no = aadhar_no;
		this.role = role;
		this.dob = dob;
		this.address = address;
		this.gender = gender;
		this.blood_group = blood_group;
		this.mobile_no = mobile_no;
		this.photo_id = photo_id;
		this.status1 = status1;
		this.notification = notification;
		this.count = count;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getAadhar_no() {
		return aadhar_no;
	}

	public void setAadhar_no(String aadhar_no) {
		this.aadhar_no = aadhar_no;
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

	public int getPhoto_id() {
		return photo_id;
	}

	public void setPhoto_id(int photo_id) {
		this.photo_id = photo_id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getStatus1() {
		return status1;
	}

	public void setStatus1(String status1) {
		this.status1 = status1;
	}



	public String getNotification() {
		return notification;
	}



	public void setNotification(String notification) {
		this.notification = notification;
	}



	@Override
	public String toString() {
		return String.format(
				"UserDTO [id=%s, name=%s, email=%s, password=%s, aadhar_no=%s, role=%s, dob=%s, address=%s, gender=%s, blood_group=%s, mobile_no=%s, photo_id=%s, status1=%s, notification=%s, count=%s]",
				id, name, email, password, aadhar_no, role, dob, address, gender, blood_group, mobile_no, photo_id,
				status1, notification, count);
	}

	

}
