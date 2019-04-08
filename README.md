# Percentile Calculator
## Sources
1. **src/main/java/calculator/PercentileCalculator.java:**  
Percentiles Calculator.
2. **src/main/java/demo/Demo.java:**  
Main program for demo.
3. **src/main/java/util/HttpCode.java**  
Http code enums
4. **src/main/java/util/HttpVerb.java**  
Http verb enums
5. **src/main/java/util/RandomHelper.java**  
Helper to generate random log data
6. **src/main/java/util/TestcaseGenerator.java**   
Main program for generating testcase
7. **testcase/\*.log**  
testcases
## Introduction
I modified the requirement to make it more practical.
Rather than calculating all the logs every time, I create a function that
takes a log file path as an input. The function calculates the p90, p95, p99 by calculating the input log
combining with previous calculated logs. 

### Assumptions
- response time is range from 0~9999 ms

### Algorithm

(1) Create respnose time buckets with index from 0~9999. The bucket's index represents
 the response time. The bucket's value is an integer represents the occurrence counter 
 for that response time.  

PercentileCalculator.java:
```$java
public static final int TIMEOUT_MS = 10000;
private int[] responseTimeBuckets = new int[TIMEOUT_MS];
```

(2) To process a log file, iterate each log and increase the corresponding response time's
occurrence counter.

PercentileCalculator.java:
```$java
responseTimeBuckets[responseTimeMs]++;
```
(3) To get the latest percentiles, iterate all the response buckets, count up for the response
 time occurrence, if current percentage is over than the target percentage, it's the point of 
 percentile to be output

PercentileCalculator.java
```$java
private int[] calcPercentilesFromBuckets() {
    //please refer this function
}
```
###Complexity Analysis
#### Time Complexity
step (2): `O(L)`, L: #log in a log file  
step (3): `O(10000) = O(1)`, 10000 is the bucket size  
total: `O(NL)` if there are N log files

#### Space Complexity
Response Time Buckets: `O(10000) = O(1)`
