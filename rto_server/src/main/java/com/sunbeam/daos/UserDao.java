package com.sunbeam.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sunbeam.entities.User;

public interface UserDao extends JpaRepository<User,Integer>{
	
	User findById(int id);
	
	User findByEmail(String email);
	
	  @Query ("select u.id from User u WHERE u.aadhar_no = ?1")
	  int findIdByaadhar_no(String aadhar);
	  
		@Modifying
		@Query("UPDATE User u SET u.address=?1,u.mobile_no=?2,u.password=?3  WHERE u.id=?4")
		public void updateUser(String address, long mobile_no,String password,int id);

}
