package com.courier.demo.model;

import java.sql.Blob;
import java.util.Base64;
import java.util.UUID;

import org.springframework.boot.context.properties.bind.DefaultValue;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;

@Entity
public class ParcelDetail {
	
	@Id
	private String productid;
	 @PrePersist
	    public void generateProductId() {
	        if (this.productid == null || this.productid.isEmpty()) {
	            this.productid = "P-" + UUID.randomUUID().toString().substring(0, 8);
	        }
	    }
	 
	@ManyToOne
	    @JoinColumn(name = "senderid", referencedColumnName = "userid" )
	private User user;
	 
	 @Column
	private Long travellerid;
	 @Column
	private String image;
	 @Column
	private String dimantion;
	 @Column
	private Double weight;
	 @Column
	private Integer price;
	 @Column
	private String from_address;
	 @Column
	private String to_address;
	 @Column 
	 private String status="posted";
	 
	 
	 
	 
	 public ParcelDetail(String productid, User user, Long travellerid, String image, String dimantion, Double weight,
				Integer price, String from_address, String to_address,String status) {
			super();
			this.productid = productid;
			this.user = user;
			this.travellerid = travellerid;
			this.image = image;
			this.dimantion = dimantion;
			this.weight = weight;
			this.price = price;
			this.from_address = from_address;
			this.to_address = to_address;
			this.status=status;
		}


	 
	


	public ParcelDetail(User user, Long travellerid, String image, String dimantion, Double weight, Integer price,
			String from_address, String to_address,String status) {
		super();
		this.user = user;
		this.travellerid = travellerid;
		this.image = image;
		this.dimantion = dimantion;
		this.weight = weight;
		this.price = price;
		this.from_address = from_address;
		this.to_address = to_address;
		this.status=status;
	}

	 public ParcelDetail() {
		 
	 }




	


	public ParcelDetail(String dimantion2, Double weight2, Integer price2, String from_address2, String to_address2,
			byte[] imageBytes) {
		// TODO Auto-generated constructor stub
	}






	@Override
	public String toString() {
		return "ParcelDetail [productid=" + productid + ", user=" + user + ", travellerid=" + travellerid + ", image="
				+ image + ", dimantion=" + dimantion + ", weight=" + weight + ", price=" + price + ", from_address="
				+ from_address + ", to_address=" + to_address + ", status=" + status + "]";
	}






	public String getProductid() {
		return productid;
	}




	public void setProductid(String productid) {
		this.productid = productid;
	}




	public User getUser() {
		return user;
	}




	public void setUser(User user) {
		this.user = user;
	}




	public Long getTravellerid() {
		return travellerid;
	}




	public void setTravellerid(Long travellerid) {
		this.travellerid = travellerid;
	}




	public String getImage() {
		return image;
	}




	public void setImage(String image) {
		this.image = image;
	}




	public String getDimantion() {
		return dimantion;
	}




	public void setDimantion(String dimantion) {
		this.dimantion = dimantion;
	}




	public Double getWeight() {
		return weight;
	}




	public void setWeight(Double weight) {
		this.weight = weight;
	}




	public Integer getPrice() {
		return price;
	}




	public void setPrice(Integer price) {
		this.price = price;
	}




	public String getFrom_address() {
		return from_address;
	}




	public void setFrom_address(String from_address) {
		this.from_address = from_address;
	}




	public String getTo_address() {
		return to_address;
	}
	public void setTo_address(String to_address) {
		this.to_address = to_address;
	}






	public String getStatus() {
		return status;
	}






	public void setStatus(String status) {
		this.status = status;
	}
	
	
	 
}
