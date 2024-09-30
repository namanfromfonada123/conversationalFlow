package com.sentMessageApi.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sentMessageApi.Modal.SecondApiData;

@Repository
public interface SecondApiRepository extends JpaRepository<SecondApiData, Long>{

	
}
