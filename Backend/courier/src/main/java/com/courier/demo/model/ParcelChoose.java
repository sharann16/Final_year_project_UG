package com.courier.demo.model;

public class ParcelChoose {
	private Long userid;
	private String parcelid;
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public String getParcelid() {
		return parcelid;
	}
	public void setParcelid(String parcelid) {
		this.parcelid = parcelid;
	}
	@Override
	public String toString() {
		return "PaecelChoose [userid=" + userid + ", parcelid=" + parcelid + "]";
	}
	public ParcelChoose(Long userid, String parcelid) {
		super();
		this.userid = userid;
		this.parcelid = parcelid;
	}
	public ParcelChoose() {
		
	}
	
}
