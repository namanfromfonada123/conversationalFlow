package com.sentMessageApi.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sentMessageApi.Modal.Obd_summary_data;

public interface obdSummaryRepository extends JpaRepository<Obd_summary_data, Long> {

	@Query(value = "Select * from obd_summary_data where date between :Sdate and :Edate", nativeQuery = true)
	Optional<List<Obd_summary_data>>  findByDate(Date Sdate, Date Edate );
}
