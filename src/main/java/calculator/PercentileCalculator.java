package calculator;

import util.HttpVerb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class PercentileCalculator {
    public static final int TIMEOUT_MS = 10000;
    private int[] responseTimeBuckets = new int[TIMEOUT_MS];
    public static final int PARTS_LEN = 6;
    public static final String DELIMITER = " ";
    private int responseTimeTotalCnt = 0;
    public static final int[] PERCENTILES = {90, 95, 99};

    /**
     * update the data from given log file
     * return the latest percentiles
     *
     * @param logFilePath log file path
     * @return percentiles
     * @throws Exception
     */
    public int[] getPercentiles(String logFilePath) throws Exception {
        File file = new File(logFilePath);
        try {
            FileReader fr = new FileReader(file);
            BufferedReader in = new BufferedReader(fr);
            String log;
            while ((log = in.readLine()) != null)
                updateResponseTimeBuckets(log);
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return calcPercentilesFromBuckets();
    }

    /**
     * update the response time buckets
     *
     * @param log log format: IP_ADDRESS [timestamp] "HTTP_VERB URI" HTTP_ERROR_CODE RESPONSE_TIME_IN_MILLISECONDS
     * @throws Exception
     */
    private void updateResponseTimeBuckets(String log) throws Exception {
        String[] parts = log.split(DELIMITER);
        if (parts.length != PARTS_LEN) {
            throw new Exception("Wrong input log:" + log);
        }
        HttpVerb httpVerb = HttpVerb.valueOf(parts[2].substring(1));
        //only calculate the GET request
        if (HttpVerb.GET == httpVerb) {
            int responseTimeMs = Integer.parseInt(parts[5]);
            if (responseTimeMs < TIMEOUT_MS) {
                responseTimeBuckets[responseTimeMs]++;
                responseTimeTotalCnt++;
            }
        }
    }

    /**
     * calculate the percentiles from response time buckets
     *
     * @return percentiles
     */
    private int[] calcPercentilesFromBuckets() {
        int[] percentiles = new int[PERCENTILES.length];
        int percentileIdx = 0;
        int responseTimeCnt = 0;
        double targetPercent = PERCENTILES[percentileIdx];
        for (int responseTime = 0; responseTime < responseTimeBuckets.length; responseTime++) {
            if (responseTimeBuckets[responseTime] < 0)
                continue;
            responseTimeCnt += responseTimeBuckets[responseTime];
            double curPercent = (double)responseTimeCnt * 100 / (double)responseTimeTotalCnt;
            if (curPercent >= targetPercent) {
                percentiles[percentileIdx] = responseTime;
                percentileIdx++;
                if (percentileIdx == PERCENTILES.length)
                    break;
                targetPercent = PERCENTILES[percentileIdx];
            }
        }
        return percentiles;
    }
}
