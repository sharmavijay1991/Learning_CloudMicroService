package com.vijay.cmad;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
	
	@RequestMapping(path="/getlogs", method = RequestMethod.POST)
	public ResponseEntity<List<LogDBEntry>> read(@RequestBody Query req_data)
	{	
		
		String incoming_date = null;
		List<LogDBEntry> result = new ArrayList<LogDBEntry>();
		List<LogDBEntry> result_ptime_did = new ArrayList<LogDBEntry>();
		List<LogDBEntry> result_pname = new ArrayList<LogDBEntry>();
		List<LogDBEntry> result_pid = new ArrayList<LogDBEntry>();
		List<LogDBEntry> result_dname = new ArrayList<LogDBEntry>();
		incoming_date = req_data.getStarttime();
		String lookback = req_data.getLookbackduration();
		String p_name = req_data.getProcessname();
		String p_id = req_data.getProcessid();
		String d_id = req_data.getDeviceid();
		String d_name = req_data.getDevicename();
		String date_format = "yyyy-MM-dd HH:mm:ss";
		System.out.println("[" + incoming_date + p_name + p_id + d_id + d_name + "]");
		
		try {
			
				/* ** Older design junked it.**
				
				BasicDBObject logdb_query = new BasicDBObject();
				logdb_query.put("processtime", BasicDBObjectBuilder.start("$gte", lookup_start_date).add("$lte", lookup_end_date).get());
				
				//result = (List<LogDBEntry>) repository.findAllByQuery(logdb_query);
				*/
				
			
				result = (List<LogDBEntry>) repository.findByDeviceid(d_id);

				if(isNullOrEmpty(incoming_date) == false) {
					Date lookup_end_date = get_date(date_format,incoming_date);
					Date lookup_start_date = calculate_date_using_lookback(lookup_end_date, lookback);
					result_ptime_did = (List<LogDBEntry>) repository.findByProcesstimeBetween(lookup_start_date,lookup_end_date);
					result = extractCommon(result, result_ptime_did);
					
				}
				
				if(isNullOrEmpty(p_name) == false) {
					result_pname = (List<LogDBEntry>) repository.findByProcessname(p_name);
					result= extractCommon(result,result_pname);
				}
				if(isNullOrEmpty(p_id) == false) {
					result_pid = (List<LogDBEntry>) repository.findByProcessid(p_id);
					result= extractCommon(result,result_pid);
				}
				if(isNullOrEmpty(d_name) == false) {
					result_dname = (List<LogDBEntry>) repository.findByProcessname(d_name);
					result= extractCommon(result,result_dname);
				}
	
				return new ResponseEntity<List<LogDBEntry>> (result, HttpStatus.CREATED);
				

		}
		catch (Exception ex){
			return new ResponseEntity<List<LogDBEntry>> (result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		/*
		//Removed to Try MongoBasedQueries
		String incoming_date = null;
		String new_id = null;
		String format_str = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(format_str);
		List<String> query_ids = new ArrayList<String>();
		List<LogDBEntry> result = new ArrayList<LogDBEntry>();
		
		System.out.println(" Debug: [ ID: " + id +" ]");
		try {
			incoming_date = req_data.getStart_time();
			System.out.println("Debug: [ Incoming date: " + incoming_date +" ]");
			Date dt = sdf.parse(incoming_date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dt);
			//calendar.set(13,0);	
			//query_ids.add(id + "_" + (calendar.getTimeInMillis() + (60000)));
			//for(int index =0; index < Integer.parseInt(req_data.getLookback_duration()); index++)
			for(int index =0; index < (Integer.parseInt(req_data.getLookback_duration())*60); index++)
			{
				//long epoc_time = (calendar.getTimeInMillis() - (60000 * index));
				long epoc_time = (calendar.getTimeInMillis() - (index * 1000));
				new_id = id +"_" + epoc_time;
				query_ids.add(new_id);
			}

			System.out.println("Debug: [Query list size: " + query_ids.size() +" ]");
			for(int i=0; i<query_ids.size();i++) {
				System.out.println(query_ids.get(i));
			}
			result = (List<LogDBEntry>) repository.findAllById(query_ids);
			
			System.out.println("Debug: [Output list size: " + result.size() + " ]");
			
			return new ResponseEntity<List<LogDBEntry>> (result, HttpStatus.CREATED);
		}
		catch (Exception e)
		{
			return new ResponseEntity<List<LogDBEntry>> (result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		*/
		
		
	}
	
	public Date calculate_date_using_lookback(Date end_date, String lookback) {
		LocalDateTime ldt = LocalDateTime.ofInstant(end_date.toInstant(), ZoneId.systemDefault());
		LocalDateTime return_ldt = ldt.minusMinutes(Integer.parseInt(lookback));
		return(Date.from(return_ldt.atZone(ZoneId.systemDefault()).toInstant()));
	}
	
	public Date get_date(String format, String date) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		try {
			Date dt = formatter.parse(date);
			return dt;
		}
		catch(Exception ex) {
			Date dt = new Date();
			return dt;
		}
	}
    public static boolean isNullOrEmpty(String str) {
        if(str != null && !str.trim().isEmpty())
            return false;
        return true;
    }
    
    public static boolean isSimilarDBEntry(LogDBEntry main_entry, LogDBEntry secondary_entry) {
    	/*
    	System.out.println(main_entry.getDevicename() + "--" + secondary_entry.getDevicename());
    	System.out.println(main_entry.getId() + "--" + secondary_entry.getId());
    	System.out.println(main_entry.getProcesstime() + "--" + secondary_entry.getProcesstime());
    	System.out.println(main_entry.getDevicename().equals(secondary_entry.getDevicename()));
    	System.out.println(main_entry.getId().equals(secondary_entry.getId()));
    	System.out.println(main_entry.getProcesstime().equals(secondary_entry.getProcesstime()));
    	System.out.println("------------");
    	*/
    	return( (main_entry.getDevicename().equals(secondary_entry.getDevicename())) && 
    			(main_entry.getId().equals(secondary_entry.getId())) &&
    			(main_entry.getProcesstime().equals(secondary_entry.getProcesstime()))
    			);
    }
    
    public static List<LogDBEntry> extractCommon(List<LogDBEntry> first, List<LogDBEntry> sec){
    	List<LogDBEntry> lresult = new ArrayList<LogDBEntry>(first);
    	for (int i = 0; i < first.size() ; i++) {
    		LogDBEntry main_entry = first.get(i);
    		boolean found = false;
    		for(int j =0; j < sec.size(); j++ ) {
    			if(isSimilarDBEntry(main_entry, sec.get(j))) {
    				found = true;
    				
    			}
    		}
    		if(!found) {
    			lresult.remove(i);
    		}
    		
    	}
    	return lresult;
    }

}
