#SMS gateway

A simple Java application for sending SMS messages using the gammu command line tool via a REST service.



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

{"recipient":"+3725432101","text":"Hello world!"}HTTP/1.1 200 OK
Server: Apache-Coyote/1.1
Content-Length: 0
Date: Thu, 26 Feb 2015 12:20:30 GMT
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