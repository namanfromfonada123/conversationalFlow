package com.sentMessageApi.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sentMessageApi.Modal.FirstApiData;

@Repository
public interface FirstApiRepository extends JpaRepository<FirstApiData, Long> {
	
	List<FirstApiData> findByFlag(int flag);
	
	@Modifying
	@Transactional
	@Query(value = "INSERT IGNORE  INTO conversationaldata (blacklist_msisdn, display_text, msg_id, timestamp, user_name, flag, clickid,bot_id,campaign_name,data,event,lead_name,status)VALUES (?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12,?13)", nativeQuery = true )
	public void saveUniqueData(String blacklistMsisdn, String displayText, String msgId, String timestamp, String userName, int flag, String clickid, String bot_id, String campaign_name, String data, String event, String lead_name, String status  );
}


