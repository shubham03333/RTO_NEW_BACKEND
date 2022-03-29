package com.sunbeam.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sunbeam.entities.VehicleRegistration;
import com.sunbeam.entities.VehicleTransfer;

public interface VehicleTransferDao extends JpaRepository<VehicleTransfer, Integer>{
	
	VehicleTransfer findByid(int id);
	
	 @Query ("select vt.id from VehicleTransfer vt WHERE vt.user_id = ?1")
	  int findIdByUserId(int id);
	
	
	
	 	@Modifying
		@Query("UPDATE VehicleTransfer vt SET vt.status=?1  WHERE vt.id=?2")
		public void updateVTransfer(String status,int id);
	 	
	 	@Modifying
		@Query("UPDATE VehicleTransfer vt SET vt.transfer_no=?1  WHERE vt.id=?2")
		public void updateVTransferNo(String tNo,int id);
	 	
	 	@Query("select count(*) from VehicleTransfer vt WHERE vt.status = 'Pending'")
		Integer pendingCountInVt();
	 	
	 	 @Query ("select vt.id from VehicleTransfer vt WHERE vt.registration_no = ?1")
	 	  int findIdByregistration_no(String id);
}
