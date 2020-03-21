# Learning_CloudMicroService
Currently this repository is having 2 microservices in it.
(i) Log-Writer:
        This a listen-write micors-serverice. Which will listen on: "[POST] http://<server-ip>:8081/log/{device-id}"
        the post json on REST should be in below mentioned format:
          {"id": "2020-03-22 20:25:33","log_line":"Some-text..."}
        Where: id indicates publish OR log-time.
               log_line: is log text
               device-id (path variable): is device unique ID which browser gets on device addition. (Browser should automatically send it)
        We store this data in MongoDB.
        
        Status-code:
          On success you will get [ HTTP-Status-Code: 200/OK ]
          On any failure we returning [ HTTP-Status-Code: 500/Server internal error ]
        
(ii) Log-Reader:
        This a listen-read-reply micors-serverice. Which will listen on: "[GET] http://<server-ip>:8081/log/{device-id}"
        the GET json on REST should be in below mentioned format:
          {"start_time": "2020-03-22 20:25:33","lookback_duration":"10"}
        Where: start_time indicates start_time for lookback.
               lookback_duration: is lookback duration in minutes.
               device-id (path variable): is device unique ID which browser gets on device addition. (Browser should automatically send it)
               * Above line can be converted as: get logs between [2020-03-22 20:15:33 & 2020-03-22 20:25:33]
        We retrieve this data from MongoDB which is getting stored by 1st micro-service.
