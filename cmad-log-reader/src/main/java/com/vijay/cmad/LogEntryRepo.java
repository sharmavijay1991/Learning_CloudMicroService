package com.vijay.cmad;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;

@Repository
public interface LogEntryRepo extends MongoRepository<LogDBEntry, String> {

	
	List<LogDBEntry> findByProcesstimeBetween(Date lookup_start_date, Date lookup_end_date) ;
	List<LogDBEntry> findByProcessname(String p_name);
	List<LogDBEntry> findByProcessid(String p_id);
	List<LogDBEntry> findByDevicename(String d_name);
	List<LogDBEntry> findByDeviceid(String d_id);

	//List<LogDBEntry> findAllByQuery(BasicDBObject obj);
	//List<LogEntry> findAllById(List<String> log_ids);
}
