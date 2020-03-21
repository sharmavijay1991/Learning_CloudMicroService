package com.glarimy.cmad;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class LogEntry {
	@Id
	private String id;
	private String log_line;
	
	public String getLog_id() {
		return id;
	}
	public void setLog_id(String log_id) {
		this.id = log_id;
	}
	public String getLog_line() {
		return log_line;
	}
	public void setLog_line(String log_line) {
		this.log_line = log_line;
	}

}
