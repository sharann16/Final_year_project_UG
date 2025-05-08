package com.courier.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class TravellerDetail {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@OneToOne
    @JoinColumn(name = "userid", referencedColumnName = "userid" )
	private User user;
	
	@Column(length = 12)
	private Long adharNumber;
	
	@Column(length=15)
	private String license;
	
	

	@Override
	public String toString() {
		return "TravellerDetail [id=" + id + ", user=" + user + ", adharNumber=" + adharNumber + ", license=" + license
				+ "]";
	}

	public TravellerDetail() {
		
	}
	
	public TravellerDetail(Integer id, User user, Long adharNumber, String license) {
		super();
		this.id = id;
		this.user = user;
		this.adharNumber = adharNumber;
		this.license = license;
	}



	public TravellerDetail(User user, Long adharNumber, String license) {
		super();
		this.user = user;
		this.adharNumber = adharNumber;
		this.license = license;
	}

	public TravellerDetail(Long adharNumber, String license) {
		super();
		this.adharNumber = adharNumber;
		this.license = license;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getAdharNumber() {
		return adharNumber;
	}

	public void setAdharNumber(Long adharNumber) {
		this.adharNumber = adharNumber;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	

}
