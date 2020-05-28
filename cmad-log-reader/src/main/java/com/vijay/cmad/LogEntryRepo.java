package com.vijay.cmad;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogEntryRepo extends MongoRepository<LogDBEntry, String> {

	
	//List<LogDBEntry> findByProcess_timeBetween(Date lookup_start_date, Date lookup_end_date) ;
	/*{
		//TODO it should work.
		LogDBEntry entity;
		String s = entity.getClass().getTypeName();
		List<LogDBEntry> result = new ArrayList<LogDBEntry>();
		return result;
	}
	*/
	//List<LogEntry> findAllById(List<String> log_ids);
}
