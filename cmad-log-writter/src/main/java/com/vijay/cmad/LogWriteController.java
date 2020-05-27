package com.vijay.cmad;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;


/* Sample data for testing:
 * {"log_id": "2020-02-19 16:18:03","log_line":"Very...... Big.... Log.... Line....."}
 */

@RestController
@CrossOrigin
public class LogWriteController {
	@Autowired
	private LogEntryRepo repository;
	
	@RequestMapping(path="/log/{id}", method = RequestMethod.POST)
	public ResponseEntity<String> create(@RequestBody LogEntry log, @PathVariable String id)
	{
		String new_id = null;

		try {
			long epoc_time = getEpocTime(log);
			new_id = id + "_" + epoc_time;
			
			LogDBEntry logdb = new LogDBEntry();
			
			PrepareDBEntry(log, logdb, new_id,id);			
			repository.save(logdb);
			
			return new ResponseEntity<String> (new_id, HttpStatus.CREATED);
		}
		catch (Exception e)
		{
			return new ResponseEntity<String> (new_id, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	private long getEpocTime(LogEntry log) {
		String incoming_date = null;
		String format_str = "yyyy-MM-dd HH:mm:ss";
		incoming_date = log.getId();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format_str);
			Date dt = sdf.parse(incoming_date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dt);
			long epoc_time = calendar.getTimeInMillis();
			return(epoc_time);
		}
		catch(Exception e){
			return 0;
		}
	}

	private void PrepareDBEntry(LogEntry log, LogDBEntry logdb, String new_id, String id) {
		/* 
		 * Populate fields => 
		 * String id;
		 * String log_line;
		 * String log_time;
		 * String device_id;
		 * String device_name;
		 * String process_name;
		 * int process_id;
		*/
		Date process_time = new Date();
		logdb.setId(new_id);
		logdb.setLog_line(log.getLog_line());
		logdb.setLog_time(getLogTime(log.getLog_line()));
		logdb.setDevice_id(id);
		logdb.setDevice_name(getDeviceName(log.getLog_line()));
		logdb.setProcess_name(getProcessName(log.getLog_line()));
		logdb.setProcess_id(getProcessID(log.getLog_line()));
		logdb.setProcess_time(process_time);
		
	}
	
	private String splitAndGetForIndex(String log_line, String pattern, int index) {
		String[] splited = log_line.split(pattern);
		return splited[index];
	}

	private int getProcessID(String log_line) {
		String pname_pid = splitAndGetForIndex(log_line,"\\s+", 4);
		String pid_first = splitAndGetForIndex(pname_pid,"\\[", 1);
		String pid = splitAndGetForIndex(pid_first,"\\]", 0);
		//System.out.println("PID:"+pid);
		return(Integer.parseInt(pid));
		
		//return 0;
	}

	private String getProcessName(String log_line) {
		String pname_pid = splitAndGetForIndex(log_line,"\\s+", 4);
		String pname = splitAndGetForIndex(pname_pid,"\\[", 0);
		//System.out.println("PName:"+pname);
		return pname;
	}

	private String getDeviceName(String log_line) {
		String device_name = splitAndGetForIndex(log_line,"\\s+", 3);
		//System.out.println("DeviceName:"+device_name);
		return device_name;
	}


	private String getLogTime(String log_line) {
		String month = splitAndGetForIndex(log_line,"\\s+", 0);
		String date = splitAndGetForIndex(log_line,"\\s+", 1);
		String time = splitAndGetForIndex(log_line,"\\s+", 2);
		//System.out.println("DateTime:"+month+" "+date+" "+time);
		return (month+" "+date +" "+time);
	}
	

}
