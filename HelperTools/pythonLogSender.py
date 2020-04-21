from datetime import datetime
import requests
import subprocess
import time

CONNECTION_URL = "http://localhost:8081/log/"
device_ids = ["100vMac","101vMac","102vMac"]

def getLastLogLine():
	line = subprocess.check_output(['head', '-1', '/var/log/system.log'])
	fine_line = line.decode('utf8').strip()
	#fine_line = str(line).rstrip()
	return(fine_line);

for id in device_ids:
	full_url = CONNECTION_URL + id;
	print(" *********** Sending request **************")
	curr_time = datetime.now()
	str_format_curr_time = curr_time.strftime("%Y-%m-%d %H:%M:%S")
	log = getLastLogLine() + "----" + str_format_curr_time
	data = {"id": str_format_curr_time,
		"log_line":log}
	print("URL: %s\nData: %s"%(full_url,str(data)))
	req = requests.post(url = full_url, json = data)
	resp = req.text
	print("Response is: %s"%resp)
	print(" ********************************************")
	time.sleep(1)
	
