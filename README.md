#SMS gateway

A simple Java application for sending SMS messages using AT commands via a REST service.


## Configuration
There are two file types with configuration (`application.properties` and `application-{profile}.properties`).
The application.properties file contains the spring.profiles.active property which defines the profile.

```
spring.profiles.active=development
```

The `application-{profile}.properties` (in our case `application-development.properties`) may contains following properties:

* `sms.sender` - the sender implementation, there are available two types (`at` and `gammu`)
    * `at` - the pure AT commands (required property `sms.at.port`)
    * `gammu` - using command line tool [Gammu](http://wammu.eu/gammu/)

* `sms.at.port` - the serial port name (`/dev/ttyUSB0` or `COM1` or etc.) **required for AT sender only**
* `sms.at.pin` - a GSM modem PIN code  **can by applied for AT sender only**
* `sms.whiteList` - the white list of recipients which allowed to send a SMS message, leave it empty if not necessary
* `sms.blackList` - the black list of recipients which not allowed to send a SMS message, leave it empty if not necessary
* `logging.config`  - the logging configuration file

An AT example:
```
sms.sender=at
sms.at.port=/dev/ttyUSB0
sms.at.pin=0000
sms.whiteList=
sms.blackList=
logging.config=config/logback-development.xml
```

A Gammu example:
```
sms.sender=gammu
sms.whiteList=
sms.blackList=
logging.config=config/logback-development.xml
```


## TCP dump of REST service using

An example of TCP stream for sending SMS message:
```tcp
POST /sms HTTP/1.1
Host: localhost:8080
Connection: keep-alive
Content-Length: 49
Content-Type: application/json
Accept: */*
Accept-Encoding: gzip, deflate
Accept-Language: en-US,en;q=0.8,ru;q=0.6

{"recipient":"+3725432101","text":"Hello world!"}

HTTP/1.1 200 OK
Server: Apache-Coyote/1.1
Content-Length: 0
Date: Thu, 26 Feb 2015 12:20:30 GMT
```

An example with an error:
```tcp
POST /sms HTTP/1.1
Host: localhost:8080
Connection: keep-alive
Content-Length: 49
Content-Type: application/json
Accept: */*
Accept-Encoding: gzip, deflate
Accept-Language: en-US,en;q=0.8,ru;q=0.6

{"recipient":"+3725432102","text":"Hello world!"}

HTTP/1.1 500 Internal Server Error
Server: Apache-Coyote/1.1
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Thu, 26 Feb 2015 12:25:09 GMT
Connection: close

d1
{"timestamp":1424953509582,"status":500,"error":"Internal Server Error","exception":"java.lang.IllegalArgumentException","message":"The recipient '+3725432102' is not in allowed recipients list","path":"/sms"}
0
```

## Gammu

Gammu is the name of the project as well as name of command line utility, which you can use to control your phone. It is written in C and built on top of libGammu.

http://wammu.eu/gammu/

An example of gammu configuration file (~/.gammurc):


```ini

[gammu]

port = /dev/ttyUSB0
model = at
connection = at115200
synchronizetime = yes
logfile = gammu.log
logformat = textalldate
use_locking =
gammuloc =

```