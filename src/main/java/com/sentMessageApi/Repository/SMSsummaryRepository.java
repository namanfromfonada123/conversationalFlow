package com.sentMessageApi.Repository;

import org.hibernate.sql.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sentMessageApi.Modal.Sms_summary_data;
import java.util.List;
import java.util.Optional;
import java.util.Date;


public interface SMSsummaryRepository extends JpaRepository<Sms_summary_data, Long> {

	@Query(value = "Select * from sms_summary_data where date between :Sdate and :Edate ", nativeQuery = true)
	Optional<List<Sms_summary_data>> findByDate(Date Sdate, Date Edate);
}
