package com.sentMessageApi.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sentMessageApi.Modal.MessageDetailsPreUpdatedRecords;

public interface PreUpdatedMessageRepository extends JpaRepository<MessageDetailsPreUpdatedRecords, Long> {

}
