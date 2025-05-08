package com.courier.demo.controller;

import java.security.Provider.Service;
import java.sql.Blob;
import java.util.List;
import java.util.Map;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.courier.demo.model.DTO;
import com.courier.demo.model.ParcelChoose;
import com.courier.demo.model.ParcelDetail;
//import com.courier.demo.model.Traveller;
import com.courier.demo.model.TravellerDetail;
import com.courier.demo.model.User;
import com.courier.demo.service.Servece;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@RestController
@RequestMapping("/SwiftBridge")
@CrossOrigin(origins = "http://localhost:3000")
public class Controller {
	
	@Autowired
	Servece servece;
//	@Autowired
//	ParcelDetail parcel;
	
	
	@PostMapping("/Signup")
	public ResponseEntity<String> signup(@RequestBody User user) {
		try {
            String response = servece.Signup(user);
            return ResponseEntity.ok(response);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
	}
	
	
	@PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody DTO loginRequest) {
        
        return servece.Login(loginRequest.getEmailid(), loginRequest.getPassword());
    }
	
	//storing product info
	
	
	@PostMapping( "/addproduct")
	public ResponseEntity<String> addProduct(@RequestParam("image") MultipartFile file,
           									 @RequestParam("dimantion") String dimantion,
           									 @RequestParam("weight") Double weight,
           									 @RequestParam("price") Integer price,
           									 @RequestParam("from_address") String fromAddress,
           									 @RequestParam("to_address") String toAddress,
           									 @RequestParam("userId") Long userId) {
	    try {
	        String response = servece.addProduct(file,dimantion,weight,price,fromAddress,toAddress,userId);
	        return ResponseEntity.ok(response);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error processing request");
	    }
	}
	
	
	
	 @GetMapping("/view/{senderid}")
	    public ResponseEntity<List<ParcelDetail>> getParcelsBySenderId(@PathVariable Long senderid) {
	        return servece.getParcelsBySenderId(senderid);
	    }
	 
	 
	 
	 
	 @PostMapping("/traveler")
	 public ResponseEntity<String> traveller(@RequestBody TravellerDetail traveller ){
		return servece.registerTraveller(traveller);
	 }
	
	 
	 
	 @GetMapping("/istraveler")
	 public boolean istraveller (@RequestParam Long userid) {
		 return servece.istravell(userid);
	 }
	 
	 
	 @GetMapping("/getproducts/{userid}")
	 public List<ParcelDetail> getAllParcel(@PathVariable Long userid){
		 return servece.travellerparcel(userid);
	 }

	 
	 
		 @PostMapping("/parcelchoose")
		 public String parcelselect(@RequestBody ParcelChoose details) {
			 System.out.println(details.getParcelid());

			 return servece.selectedParcel(details.getUserid(), details.getParcelid());
	 }
		 
		 
	@GetMapping("/travellerDetail")
	public User travellerdetail(@RequestParam Long userid) {
		return servece.travellerdet(userid);
	}
	
	 
	@GetMapping("/findTraveller")
	public Long findTraveller(@RequestParam String productid) {
		return servece.findTravellerid(productid);
	}
	
	
	@GetMapping("/travellerparcel")
	public List<ParcelDetail> parcel_selected_by_traveller( @RequestParam Long travellerid){
		return servece.traveller_selected_prcel(travellerid);
	}
	
	@GetMapping("/senhis")
	public ResponseEntity<List<ParcelDetail>> history_sender(@RequestParam Long senderid){
		return servece.sender_history(senderid);
	}
	
	@GetMapping("/trahis")
	public ResponseEntity<List<ParcelDetail>> history_traveler(@RequestParam Long travellerid){
		return servece.traveler_history(travellerid);
	}
	
	
	
	@PutMapping("/complete")
	public String completed(@RequestParam String productid) {
		return servece.complete(productid);
	}

}
