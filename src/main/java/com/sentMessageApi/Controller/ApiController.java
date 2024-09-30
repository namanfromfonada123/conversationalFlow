package com.sentMessageApi.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sentMessageApi.DTO.MessageDetailsApiResponse;
import com.sentMessageApi.Modal.MessageDetails;
import com.sentMessageApi.Service.MessageDetailsService;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*", maxAge = 3600)
@RequestMapping("/sentMessage/Api")
public class ApiController {
	
	@Autowired
	MessageDetailsService mdService;

	
	@PostMapping("/SaveMessageDetails")
	public ResponseEntity<MessageDetailsApiResponse> SaveMessageDetails(@RequestBody MessageDetails  messageDetails)
	{		
		MessageDetailsApiResponse mdar = new MessageDetailsApiResponse();
		try {
			MessageDetails mDetails = mdService.SaveMessageService(messageDetails);
			if (mDetails!=null) {
				mdar.setData(null);
				mdar.setStatus(HttpStatus.OK);
				mdar.setStatusCode(200);
				mdar.setMessage("Conversational Message Save Successfully");
				return ResponseEntity.status(HttpStatus.OK).body(mdar);
			}
			
			mdar.setData(null);
			mdar.setStatus(HttpStatus.BAD_REQUEST);
			mdar.setStatusCode(400);
			mdar.setMessage("Details with this CampaignName:"+ messageDetails.getCampaignName()+" Already Exists!!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mdar);
			
		} catch (Exception e) {
			e.printStackTrace();
			mdar.setData(null);
			mdar.setStatusCode(500);
			mdar.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			mdar.setMessage("Please Retry later!!");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mdar);
		}
				
	}
	
	
	@GetMapping("/getMessageDetails")
	public ResponseEntity<MessageDetailsApiResponse> GetMessageDetails(@RequestParam String Username){
		
		MessageDetailsApiResponse mdar = new MessageDetailsApiResponse();

		
		 try {
			 Optional<List<MessageDetails>> messageDetailslList= mdService.getMessageDetails(Username);
			 if(messageDetailslList!=null)
			 {
				 mdar.setData(messageDetailslList);
				 mdar.setMessage("Succesfully Getting MessageDetails List of "+ Username);
				 mdar.setStatus(HttpStatus.OK);
				 mdar.setStatusCode(200);
					return ResponseEntity.status(HttpStatus.OK).body(mdar);
			 }
			 else {
				mdar.setData(null);
				mdar.setMessage("No Message Details Found for "+Username);
				mdar.setStatus(HttpStatus.NOT_FOUND);
				mdar.setStatusCode(404);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mdar);
			}
		} catch (Exception e) {
			e.printStackTrace();
			mdar.setData(null);
			mdar.setStatusCode(500);
			mdar.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			mdar.setMessage("Please Retry later!!");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mdar);
		}
		 
	}
	
	@PatchMapping("/updateMessageDetails")
	public ResponseEntity<MessageDetailsApiResponse> updateMessageDetails(@RequestBody MessageDetails  messageDetails)
	{		
		MessageDetailsApiResponse mdar = new MessageDetailsApiResponse();
		try {
			MessageDetails mDetails = mdService.updateMessageDetails(messageDetails);
			if (mDetails!=null) {
				mdar.setData(null);
				mdar.setStatusCode(200);
				mdar.setStatus(HttpStatus.OK);
				mdar.setMessage("Conversational Message Updated Successfully");
				return ResponseEntity.status(HttpStatus.OK).body(mdar);
			}
			mdar.setData(null);
			mdar.setStatusCode(404);
			mdar.setStatus(HttpStatus.NOT_FOUND);
			mdar.setMessage("Message Details with CampaignName: "+messageDetails.getCampaignName()+" Not Found!!");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mdar);
		} catch (Exception e) {
			e.printStackTrace();
			mdar.setData(null);
			mdar.setStatusCode(500);
			mdar.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			mdar.setMessage("Please Retry later!!");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mdar);
		}
	}
	
	@DeleteMapping("/deleteMessageDetails")
	public ResponseEntity<MessageDetailsApiResponse> deleteMessageDetails(@RequestParam long id ) {
		
		MessageDetailsApiResponse mdar = new MessageDetailsApiResponse();

		try {
			   boolean deleteRes= mdService.deleteMessageDetails(id);
	
			   if (deleteRes==false) {
				 
				   mdar.setData(null);
				   mdar.setMessage(" Campaign and Username not present!!");
				   mdar.setStatus(HttpStatus.NOT_FOUND);
				   mdar.setStatusCode(400);
				
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mdar);
		
			}
			   
			   mdar.setData(null);
			   mdar.setMessage(" Deleted Successfully");
			   mdar.setStatus(HttpStatus.OK);
			   mdar.setStatusCode(200);
			
			return ResponseEntity.status(HttpStatus.OK).body(mdar);
		 

		} catch (Exception e) {
			e.printStackTrace();
			mdar.setData(null);
			mdar.setMessage("Please Try Later");
			mdar.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			mdar.setStatusCode(500);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mdar);
		}
		
	}
	
	
}
