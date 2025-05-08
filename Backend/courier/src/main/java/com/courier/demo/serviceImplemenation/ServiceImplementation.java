package com.courier.demo.serviceImplemenation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Blob;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.courier.demo.exceptions.EmailIdAlreadyExistingException;
import com.courier.demo.exceptions.MobileNumberAlreadyExistingException;
import com.courier.demo.jparepository.Parcelrepo;
import com.courier.demo.jparepository.Travellerrepo;
import com.courier.demo.jparepository.Userrepo;
import com.courier.demo.model.ParcelDetail;
//import com.courier.demo.model.Traveller;
import com.courier.demo.model.TravellerDetail;
import com.courier.demo.model.User;
import com.courier.demo.service.Servece;

import jakarta.transaction.Transactional;

@Service
public class ServiceImplementation implements Servece{
	
	@Autowired
	Userrepo repo;
	
	@Autowired
	Parcelrepo parcelrepo;
	
	@Autowired
	Travellerrepo traveller;
	
	
	
	private static final Random random = new Random();

    public Long generateUniqueUserId() {
        Long userId;
        do {
            userId = 100000 + random.nextLong(900000); // Generate 6-digit ID
        } while (repo.existsByUserid(userId)); // Check for uniqueness
        return userId;
    }
	
	
	//uswer signup
	@Override
	public String Signup(User user) {
		
		Long unique=generateUniqueUserId();
		user.setUserid(unique);
		
		
		if(repo.existsByEmailid(user.getEmailid())) {
			throw new EmailIdAlreadyExistingException("This email id is already in use");
		}
		if(repo.existsByMobileno(user.getMobileno())) {
			throw new MobileNumberAlreadyExistingException("This mobile number is already exiating");
		}
		try {
			repo.save(user);
			return "user register successfully";
		}catch(DataIntegrityViolationException ex){
			throw new RuntimeException("an unexpected errror occur");
		}	
	}
	
	//user login
	
	@Override	
	public ResponseEntity<Map<String, Object>> Login(String emailid,String password) {
		Optional<User> useroptional=repo.findByEmailid(emailid);
		if(useroptional.isPresent()) {
			User user = useroptional.get();
	        
	        // Create a response map
	        Map<String, Object> response = new HashMap<>();
	        response.put("message", "Successfully logged in");
	        response.put("user", user); // Return user details

	        return ResponseEntity.ok(response);	
	        }
		return null;
		}
		
	
	
	
	
	//adding parcel
	@Override
	@Transactional
    public String addProduct(ParcelDetail parcel) {
        try {
           
        	  User existingUser = repo.findById(parcel.getUser().getUserid())
                      .orElseThrow(() -> new RuntimeException("User not found"));
        	  parcel.setUser(existingUser);
            // Save to database
            parcelrepo.save(parcel);

            return "Parcel successfully added with ID: " + parcel.getProductid();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error saving parcel: " + e.getMessage();
        }
        
    }
	private static final String UPLOAD_DIR = "C:\\Users\\mshar\\Desktop\\Project\\images";

	
	
	@Override
	public String addProduct(MultipartFile file, String dimantion, Double weight, Integer price, String fromAddress,
			String toAddress, Long userId) {
		try {
		
		 User existingUser = repo.findById(userId)
                 .orElseThrow(() -> new RuntimeException("User not found"));
   	
		 String fileName = "P-" + UUID.randomUUID().toString().substring(0, 8) + "-" + file.getOriginalFilename();
         Path filePath = Paths.get(UPLOAD_DIR, fileName);
         Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
         
         ParcelDetail parcel = new ParcelDetail();
         parcel.setUser(existingUser);
         parcel.setImage(fileName);  // Store only the file name/path
         parcel.setDimantion(dimantion);
         parcel.setWeight(weight);
         parcel.setPrice(price);
         parcel.setFrom_address(fromAddress);
         parcel.setTo_address(toAddress);

         parcelrepo.save(parcel);
         return "Parcel successfully added with ID: " + parcel.getProductid();
		}catch(IOException e) {
			return("error saeing mssagee"+e.getMessage());
		}catch(Exception a) {
			return("Error saving paercel"+a.getMessage());
		}
		
	}
	
	
	
	
	 public ResponseEntity<List<ParcelDetail>> getParcelsBySenderId(Long senderid) {
	        List<ParcelDetail> parcels = parcelrepo.findByUser_UseridAndStatusNot(senderid,"Delivered");

	        if (parcels.isEmpty()) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        } else {
	            return new ResponseEntity<>(parcels, HttpStatus.OK);
	        }
	    }
	 
	 public ResponseEntity<List<ParcelDetail>> sender_history(Long senderid){
		 List<ParcelDetail> parcel=parcelrepo.findByUser_UseridAndStatus(senderid, "Delivered"	 );
		 
		 if(parcel.isEmpty()) {
			 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		 }
	  else {
         return new ResponseEntity<>(parcel, HttpStatus.OK);
	  }
	 }
	 public ResponseEntity<List<ParcelDetail>> traveler_history(Long travellerid){
		 List<ParcelDetail> parcel=parcelrepo.findByTravelleridAndStatus(travellerid, "Delivered"	 );
		 
		 if(parcel.isEmpty()) {
			 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		 }
	  else {
         return new ResponseEntity<>(parcel, HttpStatus.OK);
	  }
	 }
	
//	@Override
//	public ResponseEntity<List<ParcelDetail>> getParcelsBySenderId(Long senderid) {
//        List<ParcelDetail> parcels = parcelrepo.findByUser_Userid(senderid);
//
//        if (parcels.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        // ✅ EXPLICITLY specify the return type in map()
//        List<ParcelDetail> parcelResponses = parcels.stream()
//            .map((ParcelDetail parcel) -> { // 🔹 Explicitly specify ParcelDetail
//                byte[] imageBytes = null;
//                try {
//                    Path imagePath = Paths.get(UPLOAD_DIR, parcel.getImage());
//                    imageBytes = Files.readAllBytes(imagePath);
//                    System.out.println(imagePath);
//                } catch (IOException e) {
//                    System.err.println("Error loading image: " + e.getMessage());
//                }
//
//                return new ParcelDetail(
//                    parcel.getDimantion(),
//                    parcel.getWeight(),
//                    parcel.getPrice(),
//                    parcel.getFrom_address(),
//                    parcel.getTo_address(),
//                    imageBytes
//                );
//            })
//            .collect(Collectors.toList());
//
//        return ResponseEntity.ok(parcelResponses);
//    }
//	 
	 
	 
	 
	 
	 
	 
	 @Override
	 public ResponseEntity<String>  registerTraveller(TravellerDetail travelledet){
		 traveller.save(travelledet);
         return new ResponseEntity<>("successfully updated", HttpStatus.OK);
	 }
	 
	 
	 
	 @Override
	 public boolean istravell(Long userid) {
		 System.out.println(traveller.existsByUser_userid(userid));

		 return traveller.existsByUser_userid(userid);
	 }
	 
	 
	 @Override
	 public List<ParcelDetail> travellerparcel(Long userid){
		 if(traveller.existsByUser_userid(userid)) {
			  return parcelrepo.findByTravelleridIsNullAndUser_UseridNot(userid);
		 }
		 return Collections.emptyList();
	 }
	 
	 
	 @Override
	 public String selectedParcel(Long userid,String productid){
		 if(traveller.existsByUser_userid(userid)) {
			 System.out.println(traveller.existsByUser_userid(userid));
			 ParcelDetail parceldetail=parcelrepo.findById(productid).get();
			 parceldetail.setTravellerid(userid);
			 parceldetail.setStatus("selected");
			 parcelrepo.save(parceldetail);
			 
			 return "Parcel selected successfully"; 
		 }
		 return "something worong please try again";
	 }
	 
	 
	 @Override
	 public User travellerdet(Long userid) {
		 User user=repo.findByUserid(userid);
		 return user;
	 }
	
	@Override
	public Long findTravellerid (String productid) {
		Long travellerid=parcelrepo.findTravellerIdByProductid(productid);
		return travellerid;
	}
	
	
	
	@Override
	public List<ParcelDetail> traveller_selected_prcel(Long travellerid){
		System.out.println(travellerid);
		try {
			List<ParcelDetail> parcels=parcelrepo.findByTravelleridAndStatusNot(travellerid,"Delivered");
			return parcels;
		}catch (Exception e) {
			return Collections.emptyList();
		}
	}
	
	
	@Override
	public String complete(String productid) {
		ParcelDetail parceldetail=parcelrepo.findById(productid).get();
		try {
			if (parceldetail.getStatus().equals("selected")){
				parceldetail.setStatus("Delivered");
				parcelrepo.save(parceldetail);
				 return "succesfully delivered"; 

			}
		}catch(Exception e) {
			return "error";
		}
		 
		 return "error";
		
	}

}
