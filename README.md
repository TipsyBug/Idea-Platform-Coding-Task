# Java Coding Assessment

This program reads the file tickets.json and calculates the following from the obtained data:

- The minimum flight time between the cities of Vladivostok and Tel Aviv for each airline;
- The difference between the average price and the median price for flights between the cities of Vladivostok and Tel Aviv.

## Prerequisites

- This program requires Java to compile and run. 
- Execution from the Linux command line.

## Installation

- Download this repository and unzip the .zip file to your desired location.
- Open a terminal and navigate to the project root directory.
- Make the script executable by running ```chmod +x run.sh```.
- Execute the script to compile and run the Java program by running ```./run.sh```.

## Example of use

The symbol > represents user input in the Linux environment.

```
> java -cp ".:libs/jackson-annotations-2.17.1.jar:libs/jackson-core-2.17.1.jar:libs/jackson-databind-2.17.1.jar" Main 

The minimum flight time between the cities of Vladivostok and Tel Aviv for each airline:
Airline: SU, Flight time: 6 hours 0 minutes
Airline: S7, Flight time: 6 hours 30 minutes
Airline: TK, Flight time: 5 hours 50 minutes
Airline: BA, Flight time: 8 hours 5 minutes
The difference between the average price and the median price for flights between the cities of Vladivostok and Tel Aviv: 460.0
                  
```                                                                  
