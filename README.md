[![Java CI with Gradle](https://github.com/AmaiaBerrocal/thermostate/actions/workflows/gradle.yml/badge.svg)](https://github.com/AmaiaBerrocal/thermostate/actions/workflows/gradle.yml)
[![CodeQL](https://github.com/AmaiaBerrocal/thermostate/actions/workflows/gradle.yml/badge.svg)](https://github.com/AmaiaBerrocal/thermostate/actions/workflows/codeql.yml)

# Thermostate

A thermostate to be used with our Raspberry pi 2B (or 3B) at home.

# Make it run

You would need a DB created to run app. Nowadays we don't use any DB change control as liquibase, so just reset the
one that is on _/assets_ or create one with the SQLs in each repository.

It reads, in prod, the temperature from a file which is established in thermostate.roomTemp.file in application.yaml

To be executed run, in ./scripts:

```./run-dev.sh```

Or to run in prod environment:

```./run-prod```

# Debug

To debug server, please add --debug-jvm to prompt and then attach intellij to process  

```./run-dev.sh --debug-jvm```  

Flutter frontend is on the way

# Raspberry configuration

The GPIO configuration is:

  
 | BCM | wPi |   Name  | Mode | V | Phy|Phy | V | Mode | Name    | wPi | BCM |
 | --- | --- | ------- | ---- | --- | --- | --- | --- | --- | --- | --- | --- |
 |     |     |    3.3v |      |   |  1 | 2  |   |      | 5v      |     |     |
 |   2 |   8 |   SDA.1 |   IN | 1 |  3 | 4  |   |      | 5V      |     |     |
 |   3 |   9 |   SCL.1 |   IN | 1 |  5 | 6  |   |      | 0v      |     |     |
 |   4 |   7 | GPIO. 7 |   IN | 1 |  7 | 8  | 1 | ALT0 | TxD     | 15  | 14  |
 |     |     |      0v |      |   |  9 | 10 | 1 | ALT0 | RxD     | 16  | 15  |
 |  17 |   0 | GPIO. 0 |   IN | 0 | 11 | 12 | 0 | IN   | GPIO. 1 | 1   | 18  |
 |  27 |   2 | GPIO. 2 |   IN | 0 | 13 | 14 |   |      | 0v      |     |     |
 |  22 |   3 | GPIO. 3 |   IN | 0 | 15 | 16 | 0 | IN   | GPIO. 4 | 4   | 23  |
 |     |     |    3.3v |      |   | 17 | 18 | 0 | IN   | GPIO. 5 | 5   | 24  |
 |  10 |  12 |    MOSI | ALT0 | 0 | 19 | 20 |   |      | 0v      |     |     |
 |   9 |  13 |    MISO | ALT0 | 0 | 21 | 22 | 1 | IN   | GPIO. 6 | 6   | 25  |
 |  11 |  14 |    SCLK | ALT0 | 0 | 23 | 24 | 0 | OUT  | CE0     | 10  | 8   |
 |     |     |      0v |      |   | 25 | 26 | 1 | OUT  | CE1     | 11  | 7   |
 |   0 |  30 |   SDA.0 |   IN | 1 | 27 | 28 | 1 | IN   | SCL.0   | 31  | 1   |
 |   5 |  21 | GPIO.21 |   IN | 1 | 29 | 30 |   |      | 0v      |     |     |
 |   6 |  22 | GPIO.22 |   IN | 1 | 31 | 32 | 0 | IN   | GPIO.26 | 26  | 12  |
 |  13 |  23 | GPIO.23 |   IN | 0 | 33 | 34 |   |      | 0v      |     |     |
 |  19 |  24 | GPIO.24 |  OUT | 1 | 35 | 36 | 1 | IN   | GPIO.27 | 27  | 16  |
 |  26 |  25 | GPIO.25 |  OUT | 0 | 37 | 38 | 0 | IN   | GPIO.28 | 28  | 20  |
 |     |     |      0v |      |   | 39 | 40 | 1 | OUT  | GPIO.29 | 29  | 21  |
 
 Being the screen in pins 1-20
 Relay: 5V (2, breaking the protective plastic of that pin), 0V (39) and Vin (24, 25) at this point it's set to On state by putting 25 to 0.
 Sensor: GPIOS 28-29 and 34 

# Temp sensor

I use a 5' resistive screen to manage the rasp, a DS18S20 sensor to get ambiental temp and a relee to switch on an off the calefactor. The rasp's SO is raspbian.

It is supposed to let a file at /sys/bus/w1/devices/ as: 

```/sys/bus/w1/devices/28-0415a4ddf9ff/w1_slave```

Which should contain a number which is the temp in milis

More info at: https://en.kompf.de/weather/pionewiremini.html

# Relay use

To switch on / of the relee should be enough with:

```Runtime.getRuntime().exec("gpio write 25 0");```

to switch it on or:

```Runtime.getRuntime().exec("gpio write 25 1");```

to switch it off.
