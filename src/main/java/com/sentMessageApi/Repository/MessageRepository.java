package com.sentMessageApi.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.sentMessageApi.Modal.MessageDetails;
import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<MessageDetails, Long>{

	@Query(value = "Select * from conversational_message_detail where campaign_name=:CampaignName And username = :Username ", nativeQuery = true)
	Optional<MessageDetails> findMessageDetails(String CampaignName, String Username);
	

	@Query(value = "Select * from conversational_message_detail where username=:username ", nativeQuery = true)
	Optional<List<MessageDetails>> findByUsername(String username);
	
}
