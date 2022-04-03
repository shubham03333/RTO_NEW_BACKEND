package com.sunbeam.dtos;

import javax.validation.constraints.NotBlank;

public class Credentials {
	@NotBlank
	private String email;
//	@NotBlank
	private String password;
	
	private int otp;
	
	public Credentials() {
	}
	
	public Credentials(@NotBlank String email, String password, int otp) {
		this.email = email;
		this.password = password;
		this.otp = otp;
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
	
	
	public int getOtp() {
		return otp;
	}

	public void setOtp(int otp) {
		this.otp = otp;
	}

	@Override
	public String toString() {
		return String.format("Credentials [email=%s, password=%s, otp=%s]", email, password, otp);
	}


}
