# Percentile Calculator
## Introduction
I modified the requirement to make it more practical.
Rather than calculating all log files every time, I create a function that
takes a log file path as an input. The function calculates the p90, p95, p99 by calculating the input log
combining with previous calculated logs' data, so that if new log file comes in, it don't have 
to re-calculated previous log file again which is more efficient.   

### Percentile Calculator Class
The main class is PercentileCalculator  
2 public APIs are exposed:
- **processLogFile(String logFilePath)** processes the log file and update the current percentiles
- **printPercentiles()**
print the percentiles (p90, p95, p99) according to the requirement

The usage example could be referred in `src/main/java/demo/Demo.java`

### Project Structure
1. **src/main/java/calculator/PercentileCalculator.java:**  
Percentiles Calculator
2. **src/main/java/demo/Demo.java:**  
Main program for demo
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

## Assumptions
Response time is range from `0~9999` ms  

## Algorithm

Create response time buckets with index from 0~9999. The bucket's index represents
 the response time. The bucket's value is an integer represents the occurrence counter 
 for that response time.  

PercentileCalculator.java:
```java
public static final int TIMEOUT_MS = 10000;
private int[] responseTimeBuckets = new int[TIMEOUT_MS];
```

Process a log file contains two operations:
1. Update response time buckets
2. Update current percentiles

PercentileCalculator.java:
```java
public void processLogFile(String logFilePath) throws Exception {
    updateBuckets(logFilePath);
    updatePercentiles();
}

```

### Update Response Time Buckets
Iterate each log line by line and increase the corresponding response time's
occurrence counter.

PercentileCalculator.java:
```java
private void updateBuckets(String logFilePath) throws Exception {
    //please refere this function
}
private void updateBucket(String log) throws Exception {
    //please refer this function
}
```
### Update Current Percentiles
Iterate all the response buckets, and count up for the response
 time occurrence. If current percentage is over than the target percentage, it's the point
 where the target percentile is.

PercentileCalculator.java:
```java
private void updatePercentiles() {
    //please refer this function
}
```
### Complexity Analysis
#### Time Complexity
Update Response Time Buckets: `O(L)`, L: #log in a log file  
Update Percentiles: `O(10000) = O(1)`, 10000 is the bucket size  

#### Space Complexity
Response Time Buckets requires: `O(10000) = O(1)`

## How to Run the Demo?
### Prerequisite
Linux or Mac environment that can execute java(1.8) and gradle

### Run Demo (src/main/java/demo/Demo.java)
This demo program processes log files in testcases folder and output the current percentiles
```shell
./gradlew build
./gradlew run
```

#### Sample Output

testcases:  
5% response time in [5000, 10000] (ms)  
95% response time in [500, 5000] (ms)

```
Processing log file: testcase/2019-4-6.log
Current percentiles:
90% of requests return a response in 4810 ms
95% of requests return a response in 5912 ms
99% of requests return a response in 9168 ms

Processing log file: testcase/2019-4-5.log
Current percentiles:
90% of requests return a response in 4807 ms
95% of requests return a response in 5776 ms
99% of requests return a response in 9093 ms

Processing log file: testcase/2019-4-4.log
Current percentiles:
90% of requests return a response in 4808 ms
95% of requests return a response in 5824 ms
99% of requests return a response in 9118 ms

Processing log file: testcase/2019-4-3.log
Current percentiles:
90% of requests return a response in 4808 ms
95% of requests return a response in 5838 ms
99% of requests return a response in 9109 ms

Processing log file: testcase/2019-4-2.log
Current percentiles:
90% of requests return a response in 4809 ms
95% of requests return a response in 5840 ms
99% of requests return a response in 9123 ms
```

### Generate New Test Cases (src/main/java/util/TestcaseGenerator.java)
Instead of using original test cases, you can also generate your own test cases.  
There are some values to play with.  

in RandomHelper.java:
```java
/**
 * ABNORMAL_PERCENT% of response times are in range [MAX_RESPONSE_TIME_MS, ABNORMAL_MAX_RESPONSE_TIME_MS]
 * 100 - ABNORMAL_PERCENT% of response times are in range [MIN_RESPONSE_TIME_MS, MAX_RESPONSE_TIME_MS]
 */
public static final int ABNORMAL_PERCENT = 5;
public static final int ABNORMAL_MAX_RESPONSE_TIME_MS = 10000;
public static final int MIN_RESPONSE_TIME_MS = 500;
public static final int MAX_RESPONSE_TIME_MS = 5000;
```
The above settings means:  
5% response time in [5000, 10000] (ms)  
95% response time in [500, 5000] (ms)

To generate the new test cases:
```shell
./gradlew build
./gradlew genTestcase
```
