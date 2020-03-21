package com.glarimy.cmad;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class LogEntry {
	@Id
	private String id;
	private String log_line;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLog_line() {
		return log_line;
	}
	public void setLog_line(String log_line) {
		this.log_line = log_line;
	}
	

}
