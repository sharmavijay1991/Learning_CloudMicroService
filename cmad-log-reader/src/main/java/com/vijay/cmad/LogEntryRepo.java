package com.vijay.cmad;

//import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogEntryRepo extends MongoRepository<LogEntry, String> {
	//List<LogEntry> findAllById(List<String> log_ids);
}
