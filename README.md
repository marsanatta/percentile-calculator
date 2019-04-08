# Percentile Calculator
## Introduction
I modified the requirement to make it more practical.
Rather than calculating all the logs every time, I create a function that
takes a log file path as an input. The function calculates the p90, p95, p99 by calculating the input log
combining with previous calculated logs' data, so that if new log file comes in, it don't have 
to re-calculated previous log file again which is more efficient and practical. 

### Assumptions
Response time is range from `0~9999` ms

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

###How to Run the Demo?
#### Prerequisite
Linux or Mac environment that can execute java(1.8) and gradle

#### Run Demo (src/main/java/demo/Demo.java)
```$java
./gradlew build
./gradlew run
```
#### Generate New Test Cases (src/main/java/util/TestcaseGenerator.java)
There are some values to play with. 

in RandomHelper.java:
```$xslt
/**
 * ABNORMAL_PERCENT% of response time in [MAX_RESPONSE_TIME_MS, ABNORMAL_MAX_RESPONSE_TIME_MS]
 * 100 - ABNORMAL_PERCENT% of response times in [MIN_RESPONSE_TIME_MS, MAX_RESPONSE_TIME_MS]
 */
public static final int ABNORMAL_PERCENT = 5;
public static final int ABNORMAL_MAX_RESPONSE_TIME_MS = 10000;
public static final int MIN_RESPONSE_TIME_MS = 500;
public static final int MAX_RESPONSE_TIME_MS = 5000;
```
The above settings means:  
5% response time in [5000, 10000] (ms)  
95% response time in [500, 5000] (ms)

To generate the test case:
```$java
./gradlew build
./gradlew genTestcase
```

#### Sample Output
5% response time in [5000, 10000] (ms)  
95% response time in [500, 5000] (ms)

```
[2019-4-2.log]
90% of requests return a response in 4789 ms
95% of requests return a response in 5682 ms
99% of requests return a response in 9174 ms

[2019-4-3.log]
90% of requests return a response in 4798 ms
95% of requests return a response in 5804 ms
99% of requests return a response in 9169 ms

[2019-4-4.log]
90% of requests return a response in 4803 ms
95% of requests return a response in 5852 ms
99% of requests return a response in 9184 ms

[2019-4-5.log]
90% of requests return a response in 4808 ms
95% of requests return a response in 5850 ms
99% of requests return a response in 9174 ms

[2019-4-6.log]
90% of requests return a response in 4807 ms
95% of requests return a response in 5833 ms
99% of requests return a response in 9157 ms
```


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

