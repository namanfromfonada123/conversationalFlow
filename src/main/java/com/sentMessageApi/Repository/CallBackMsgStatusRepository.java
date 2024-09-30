package com.sentMessageApi.Repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sentMessageApi.Modal.CallBackMsgStatusEntity;


@Repository
public interface CallBackMsgStatusRepository extends JpaRepository<CallBackMsgStatusEntity, Long> {
	@Transactional
	@Query(value = "select * from demo_rcs.call_back_msg_status where user_name=:userName and (text_message is not null or display_text is not null) and created_date between :startDate and :endDate", nativeQuery = true)
	public List<CallBackMsgStatusEntity> findByTextMessageNotNullAndIsCompleteIsOne(@Param("userName") String userName,
			@Param("startDate") String startDate, @Param("endDate") String endDate);
	
	
	@Transactional
	@Query(value = "SELECT"
			+"		tmp.bot_id,"
			+ "     tmp.template_code,"
			+ "	 	demo_rcs.blacklist.id,"
			+ "     demo_rcs.blacklist.blacklist_msisdn,"
			+" 		demo_rcs.blacklist.data,"
			+ "     demo_rcs.blacklist.msg_id, "	
			+ "     lfd.created_by,"
			+ "     li.lead_name,"
			+ "     cp.campaign_name,"
			+ "     cp.campaign_type,"
			+ "     cp.data_source_name,"
			+ "     lfd.additional_data_info_text,"
			+ "     lfd.additional_data_info_text2,"
			+ "     cp.description, "
			+ "     demo_rcs.blacklist.display_text "
			+ " FROM "
			+ " rcsmessaging.lead_info_detail lfd "
			+ " INNER JOIN rcsmessaging.lead_info li ON li.lead_id=lfd.lead_id "
			+ " INNER JOIN  rcsmessaging.campaign cp ON cp.campaign_id = li.lead_campaign_id "
			+ "	and cp.user_id =li.lead_user_id "
			+ "INNER JOIN rcsmessaging.template tmp ON tmp.id = cp.template_id "
			+ "inner join demo_rcs.blacklist on demo_rcs.blacklist.data=lfd.lead_info_detail_id "
			+ "	and timestamp >=date_sub(now(),INTERVAL 2 Minute) "
			+ "where "
			+ "	(text_message is not null or display_text is not null) "
			+ "    and lfd.status !='Created' "
			+ "	and lfd.created_by in('PaisabazzarPro','PaisabazaarPrsnl','Affipedia','Paisabazaaroffers')"
			+ "    and lfd.created_date between date_sub(now(),INTERVAL 2 Minute) and now()"
			+ "	 order by"
			+ "     lfd.lead_info_detail_id "
			+ "     desc limit 20;", nativeQuery = true)
	public List<Object[]> findByTextMessageNotNullAndIsCompleteIsOne2();
	
	
	
}
