package com.courier.demo.service;

import java.sql.Blob;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.courier.demo.model.ParcelDetail;
import com.courier.demo.model.TravellerDetail;
import com.courier.demo.model.User;

public interface Servece {
	String Signup(User user);
	ResponseEntity<Map<String, Object>> Login(String emailid,String password);
	
	
	
//	String addProduct(ParcelDetail parceldetail);
	 String addProduct(ParcelDetail parcel);
		// TODO Auto-generated method stub
	 ResponseEntity<List<ParcelDetail>> getParcelsBySenderId(Long senderid);

	 
	 
	 ResponseEntity<String> registerTraveller(TravellerDetail traveller);
	 
	 List<ParcelDetail> travellerparcel(Long userid);
	 
	 
	 String selectedParcel( Long userid,String productid);
	String addProduct(String file, String dimantion, Double weight, Integer price, String fromAddress,
			String toAddress, Long userId);
	 
	boolean istravell(Long userid);
	
	User travellerdet(Long userid);
	Long findTravellerid(String productid);
	
	List<ParcelDetail> traveller_selected_prcel(Long travellerid);
	String  complete(String parcelid);
	
	ResponseEntity<List<ParcelDetail>> sender_history(Long senderid);
	ResponseEntity<List<ParcelDetail>> traveler_history(Long travellerid);
	
}
