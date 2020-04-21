package com.vijay.cmad;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class LogDBEntry {
	@Id
	private String id;
	private String log_line;
	private String log_time;
	private String device_id;
	private String device_name;
	private String process_name;
	private int process_id;
	
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
	public String getLog_time() {
		return log_time;
	}
	public void setLog_time(String log_time) {
		this.log_time = log_time;
	}
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public String getDevice_name() {
		return device_name;
	}
	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}
	public String getProcess_name() {
		return process_name;
	}
	public void setProcess_name(String process_name) {
		this.process_name = process_name;
	}
	public int getProcess_id() {
		return process_id;
	}
	public void setProcess_id(int process_id) {
		this.process_id = process_id;
	}
	
	

}
