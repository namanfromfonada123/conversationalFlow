package com.sentMessageApi.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sentMessageApi.Modal.blacklist;

public interface blackListRepository extends JpaRepository<blacklist, Long> {
	
	@Transactional
	@Query(value = "Select * from demo_rcs.blacklist ", nativeQuery = true)
	public  List<blacklist> findByBlacklist_msisdn();
}
