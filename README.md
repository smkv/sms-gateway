#SMS gateway

A simple Java application for sending SMS messages using the gammu command line tool via a REST service.

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