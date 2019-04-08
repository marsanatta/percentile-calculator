package util;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import static util.HttpVerb.POST;

/**
 *  Format:
 *  IP_ADDRESS [timestamp] "HTTP_VERB URI" HTTP_ERROR_CODE RESPONSE_TIME_IN_MILLISECONDS
 *
 *  Example:
 *  10.2.3.4 [2018/13/10:14:02:39] "GET /api/playeritems?playerId=3" 200 1230
 */
public class TestcaseGenerator {
    private static final long START_TIMESTAMP = 1554163200000L;
    private static final int DAYS = 5;
    private static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd:HH:mm:ss").withZone(ZoneOffset.UTC);
    private static String URL = "/api/playeritems";
    private static String PARAM = "?playerId=";
    public static String TESTCASE_DIR = "testcase/";

    public static void main(String[] args) {
        LocalDateTime cur = LocalDateTime.ofInstant(Instant.ofEpochMilli(START_TIMESTAMP), ZoneId.systemDefault());
        BufferedWriter writer = null;
        for (int i = 0; i < DAYS; i++) {
            String fileName = TESTCASE_DIR + cur.getYear() + "-" + cur.getMonthValue() + "-" + cur.getDayOfMonth() + ".log";
            try {
                writer = new BufferedWriter(new FileWriter(fileName));
                LocalDateTime endDay = cur.toLocalDate().atTime(LocalTime.MAX);
                while (cur.compareTo(endDay) <= 0) {
                    HttpVerb httpVerb = RandomHelper.getHttpVerb();
                    StringBuilder urlBuilder = new StringBuilder(URL);
                    if (POST != httpVerb) {
                        urlBuilder.append(PARAM).append(RandomHelper.getPlayerId());
                    }
                    String log = RandomHelper.getIp() + " ["
                            + DATE_TIME_FORMATTER.format(cur) + "] "
                            + "\"" + httpVerb + " "
                            + urlBuilder.toString() + "\" "
                            + RandomHelper.getHttpCode(httpVerb).getCode() + " "
                            + RandomHelper.getResponseTime() + "\n";
                    writer.write(log);
                    cur = cur.plusSeconds(RandomHelper.getNextRequestComeInSeconds());
                }
                writer.close();
            } catch (IOException e) {
                System.out.println("IOException: " + e.getMessage());
                return;
            } finally {
                close(writer);
            }
        }
    }

    /**
     * quietly close
     * @param c closable
     */
    public static void close(Closeable c) {
        if (c == null) return;
        try {
            c.close();
        } catch (IOException e) {
            System.out.println("close failed:" + e.getMessage());
        }
    }


}
