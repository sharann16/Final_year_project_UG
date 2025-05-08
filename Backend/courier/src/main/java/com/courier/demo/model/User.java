package com.courier.demo.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {
	
	@Id
	private Long userid;
	@Column(length = 25)
	private String name;
	@Column(length =50)
	private String emailid;
	@Column(length = 10)
	private Long mobileno;
	@Column(length = 10)
	private String password;
	@Column(length = 50)
	private String Address;
	
	@Column
	private Date joindate;
	
	
	
	@Override
	public String toString() {
		return "User [userid=" + userid + ", name=" + name + ", emailid=" + emailid + ", mobileno=" + mobileno
				+ ", password=" + password + ", Address=" + Address + ", joindate=" + joindate + "]";
	}



	public User(Long userid, String name, String emailid, Long mobileno, String password, String address,
			Date joindate) {
		super();
		this.userid = userid;
		this.name = name;
		this.emailid = emailid;
		this.mobileno = mobileno;
		this.password = password;
		this.Address = address;
		this.joindate = joindate;
	}



	public User(String name, String emailid, Long mobileno, String password, String address, Date joindate) {
		super();
		this.name = name;
		this.emailid = emailid;
		this.mobileno = mobileno;
		this.password = password;
		this.Address = address;
		this.joindate = joindate;
	}

	public User() {
		
	}



	public Long getUserid() {
		return userid;
	}



	public void setUserid(Long userid) {
		this.userid = userid;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getEmailid() {
		return emailid;
	}



	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}



	public Long getMobileno() {
		return mobileno;
	}



	public void setMobileno(Long mobileno) {
		this.mobileno = mobileno;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getAddress() {
		return Address;
	}



	public void setAddress(String address) {
		Address = address;
	}



	public Date getJoindate() {
		return joindate;
	}



	public void setJoindate(Date joindate) {
		this.joindate = joindate;
	}
	
	

}
