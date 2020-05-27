package com.vijay.cmad;

public class Query {
	private String start_time;
	private String lookback_duration;
	private String device_id;
	private String device_name;
	private String process_id;
	private String process_name;
	
	
	
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
	public String getProcess_id() {
		return process_id;
	}
	public void setProcess_id(String process_id) {
		this.process_id = process_id;
	}
	public String getProcess_name() {
		return process_name;
	}
	public void setProcess_name(String process_name) {
		this.process_name = process_name;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getLookback_duration() {
		return lookback_duration;
	}
	public void setLookback_duration(String lookback_duration) {
		this.lookback_duration = lookback_duration;
	}
}
