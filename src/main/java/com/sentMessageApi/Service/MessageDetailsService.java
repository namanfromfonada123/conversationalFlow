package com.sentMessageApi.Service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sentMessageApi.Modal.MessageDetails;
import com.sentMessageApi.Modal.MessageDetailsPreUpdatedRecords;
import com.sentMessageApi.Repository.MessageRepository;
import com.sentMessageApi.Repository.PreUpdatedMessageRepository;


@Service
public class MessageDetailsService {
	
	Logger logger = LoggerFactory.getLogger(MessageDetailsService.class);
	@Autowired
	MessageRepository mRepository;
	
	@Autowired
	PreUpdatedMessageRepository pumRepository;

	public MessageDetails SaveMessageService(MessageDetails md) {
		Optional<MessageDetails> mDetails = mRepository.findMessageDetails(md.getCampaignName(), md.getUsername());
		if(mDetails.isPresent()) {
			logger.info("MessageDetails Already Exists.. ");
			return null;
		}
		else {
			logger.info("Message Details Save Successfully.. ");
			return mRepository.save(md);
		}
		
	}
	
	public Optional<List<MessageDetails>> getMessageDetails(String username){
		
		Optional<List<MessageDetails>> mList= mRepository.findByUsername(username);
		if (mList.isPresent()) {
			return mList;
		}
		logger.info(" Message Details with Username: "+ username+" Not Present..");
		return null;
		
	}
	
	public MessageDetails updateMessageDetails(MessageDetails md) {
		Optional<MessageDetails> mDetails = mRepository.findMessageDetails(md.getCampaignName(), md.getUsername());
		MessageDetailsPreUpdatedRecords messageDetailsPreUpdatedRecords = new MessageDetailsPreUpdatedRecords();
		if (mDetails.isPresent()) {
			messageDetailsPreUpdatedRecords.setCampaignid(mDetails.get().getCampaignid());
			messageDetailsPreUpdatedRecords.setCampaignName(mDetails.get().getCampaignName());
			messageDetailsPreUpdatedRecords.setText(mDetails.get().getText());
			messageDetailsPreUpdatedRecords.setUserId(mDetails.get().getUserId());
			messageDetailsPreUpdatedRecords.setUsername(mDetails.get().getUsername());
			pumRepository.save(messageDetailsPreUpdatedRecords);
			md.setId(mDetails.get().getId());
			return mRepository.save(md);
			
		}else {
			logger.info("Message Detials with CampaignName :"+ md.getCampaignName()+" Not Present..");
			return null;
		}
			
	}

	
	public boolean deleteMessageDetails(long id)throws Exception
	{
		Optional<MessageDetails> mDetails = mRepository.findById(id);
		if (mDetails.isPresent()) {
			 mRepository.deleteById(id);
//			 return "Deleted Successfully";
			 return  true;
			 
		}
				
		logger.info("Message Details with id :"+id + " Not Present..");
//		 return "No MessageDetails Found With this id :"+id;
		return false;
	}
	
	
}
