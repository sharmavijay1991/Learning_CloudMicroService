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
	public ResponseEntity<String> create(@RequestBody LogEntry log, @PathVariable int id)
	{
		String incoming_date = null;
		String new_id = null;
		String format_str = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(format_str);
		
		System.out.println(" ##### Got for ID: " + id);
		try {
			incoming_date = log.getId();
			Date dt = sdf.parse(incoming_date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dt);
			System.out.println(" ##### new ID: " + calendar.toString());
			calendar.set(13,0);	//13th field is seconds counting from YEAR [id="Asia/Kolkata",offset=19800000,dstSavings=0,useDaylight=false,transitions=7,lastRule=null],firstDayOfWeek=1,minimalDaysInFirstWeek=1,ERA=1,YEAR=2020,MONTH=1,WEEK_OF_YEAR=8,WEEK_OF_MONTH=4,DAY_OF_MONTH=19,DAY_OF_YEAR=50,DAY_OF_WEEK=4,DAY_OF_WEEK_IN_MONTH=3,AM_PM=1,HOUR=4,HOUR_OF_DAY=16,MINUTE=18,SECOND=3,MILLISECOND=0,ZONE_OFFSET=19800000,DST_OFFSET=0]
			System.out.println(" ##### new ID after modification: " + calendar.toString());
			long epoc_time = calendar.getTimeInMillis();
			//long epoc_time = dt.getTime();
			new_id = id + "_" + epoc_time;
			log.setId(new_id);
			
			
			repository.save(log);
			
			return new ResponseEntity<String> (new_id, HttpStatus.CREATED);
		}
		catch (Exception e)
		{
			return new ResponseEntity<String> (new_id, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

}
