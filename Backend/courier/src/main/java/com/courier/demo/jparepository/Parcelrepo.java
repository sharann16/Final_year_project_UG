package com.courier.demo.jparepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.courier.demo.model.ParcelDetail;
import com.courier.demo.model.User;

public interface Parcelrepo extends JpaRepository<ParcelDetail, String>{
    List<ParcelDetail> findByUser_Userid(Long senderid);
    List<ParcelDetail> findByTravellerid(Long Travellerid);
    List<ParcelDetail> findByTravelleridAndStatusNot(Long travellerid, String status);
    List<ParcelDetail> findByUser_UseridAndStatusNot(Long userid, String status);
    List<ParcelDetail> findByUser_UseridAndStatus(Long userid, String status);
    List<ParcelDetail> findByTravelleridAndStatus(Long travellerid, String status);



    
//    @Query("SELECT p FROM ParcelDetail p WHERE p.traveller IS NULL AND p.senderid <> userid")
//    List<ParcelDetail> findAvailableParcels(@Param("senderid 	") Long userid);
    List<ParcelDetail> findByTravelleridIsNullAndUser_UseridNot(Long senderid);

    Optional<ParcelDetail> findByProductid(String productid);
    
    @Query(value = "SELECT travellerid FROM parcel_detail WHERE productid = :productid", nativeQuery = true)
    Long findTravellerIdByProductid(@Param("productid") String productid);

}
