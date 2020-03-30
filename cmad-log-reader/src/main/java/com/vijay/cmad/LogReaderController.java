package com.vijay.cmad;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
 * {"start_time": "2020-02-23 20:20:33","lookback_duration":"5"}
 */

@RestController
@CrossOrigin
public class LogReaderController {
	@Autowired
	private LogEntryRepo repository;
	
	@RequestMapping(path="/log/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<LogEntry>> read(@RequestBody Query req_data, @PathVariable int id)
	{	
		String incoming_date = null;
		String new_id = null;
		String format_str = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(format_str);
		List<String> query_ids = new ArrayList<String>();
		List<LogEntry> result = new ArrayList<LogEntry>();
		
		System.out.println(" Debug: [ ID: " + id +" ]");
		try {
			incoming_date = req_data.getStart_time();
			System.out.println("Debug: [ Incoming date: " + incoming_date +" ]");
			Date dt = sdf.parse(incoming_date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dt);
			calendar.set(13,0);	
			query_ids.add(id + "_" + (calendar.getTimeInMillis() + (60000)));
			for(int index =0; index < Integer.parseInt(req_data.getLookback_duration()); index++)
			{
				long epoc_time = (calendar.getTimeInMillis() - (60000 * index));
				new_id = id +"_" + epoc_time;
				query_ids.add(new_id);
			}

			System.out.println("Debug: [Query list size: " + query_ids.size() +" ]");
			result = (List<LogEntry>) repository.findAllById(query_ids);
			System.out.println("Debug: [Output list size: " + result.size() + " ]");
			
			return new ResponseEntity<List<LogEntry>> (result, HttpStatus.CREATED);
		}
		catch (Exception e)
		{
			return new ResponseEntity<List<LogEntry>> (result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

}
