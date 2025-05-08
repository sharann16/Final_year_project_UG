package com.courier.demo.model;

public class DTO {
	private String emailid;
	private String password;
	@Override
	public String toString() {
		return "DTO [emailid=" + emailid + ", password=" + password + "]";
	}
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public DTO(String emailid, String password) {
		super();
		this.emailid = emailid;
		this.password = password;
	}
	
	public DTO() {
		
	}
}
