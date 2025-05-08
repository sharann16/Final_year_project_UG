package com.courier.demo.jparepository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.courier.demo.model.DTO;
import com.courier.demo.model.User;

public interface Userrepo extends JpaRepository<User, Long> {

	Optional<User> findByEmailid(String emailid);
	boolean existsByMobileno(Long mobileno);
	boolean existsByEmailid(String emailid);
	
	boolean existsByUserid(Long userid);
	User findByUserid(Long userid);

}
