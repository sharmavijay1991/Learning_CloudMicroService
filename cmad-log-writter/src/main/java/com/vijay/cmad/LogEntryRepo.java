package com.vijay.cmad;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogEntryRepo extends MongoRepository<LogDBEntry, String> {
	//public LogEntry create(LogEntry log);

}
