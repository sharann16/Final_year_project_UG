package com.courier.demo.jparepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.courier.demo.model.TravellerDetail;

public interface Travellerrepo extends JpaRepository<TravellerDetail, Integer>{

	boolean existsById(Long userid);
	boolean existsByUser_userid(Long userid);

}
