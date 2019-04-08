package util;

import java.util.Random;

public class RandomHelper {

    private static int IP_MAX = 256;
    private static int IPV4_LEN = 4;
    private static Random r = new Random();

    private RandomHelper() {
        //static utility class
    }

    /**
     * get random ip
     * @return random ip
     */
    public static String getIp() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < IPV4_LEN; i++) {
            sb.append(r.nextInt(IP_MAX));
            if (i < IPV4_LEN - 1)
                sb.append('.');
        }
        return sb.toString();
    }

    /**
     * get random http verb
     * @return random http verb
     */
    public static HttpVerb getHttpVerb() {
        int rand = r.nextInt(100);
        if (rand <= 70)
            return HttpVerb.GET;
        else if (rand <= 90)
            return HttpVerb.POST;
        else if (rand <= 95)
            return HttpVerb.PUT;
        else
            return HttpVerb.DELETE;
    }

    public static final int MAX_PLAYER_ID = Integer.MAX_VALUE;
    /**
     * get random player id
     * @return random player id
     */
    public static int getPlayerId() {
        return r.nextInt(MAX_PLAYER_ID);
    }

    /**
     * ABNORMAL_PERCENT% of response times are in range [MAX_RESPONSE_TIME_MS, ABNORMAL_MAX_RESPONSE_TIME_MS]
     * 100 - ABNORMAL_PERCENT% of response times are in range [MIN_RESPONSE_TIME_MS, MAX_RESPONSE_TIME_MS]
     */
    public static final int ABNORMAL_PERCENT = 5;
    public static final int ABNORMAL_MAX_RESPONSE_TIME_MS = 10000;
    public static final int MIN_RESPONSE_TIME_MS = 500;
    public static final int MAX_RESPONSE_TIME_MS = 5000;
    /**
     * get random response time
     * @return
     */
    public static int getResponseTime() {
        int rand = r.nextInt(100);
        if (rand <= ABNORMAL_PERCENT)
            return r.nextInt(ABNORMAL_MAX_RESPONSE_TIME_MS - MAX_RESPONSE_TIME_MS + 1) + MAX_RESPONSE_TIME_MS;
        else
            return r.nextInt(MAX_RESPONSE_TIME_MS - MIN_RESPONSE_TIME_MS + 1) + MIN_RESPONSE_TIME_MS;
    }

    /**
     * get random http code
     * @param httpVerb http verb
     * @return http verbv
     */
    public static HttpCode getHttpCode(HttpVerb httpVerb) {
        int rand = r.nextInt(100);
        HttpCode res = null;
        switch (httpVerb) {
            case GET:
                res = rand >= 1 ? HttpCode.OK : HttpCode.INTERNAL_SERVER_ERROR;
                break;
            case POST:
                res = rand >= 1 ? HttpCode.CREATED : HttpCode.INTERNAL_SERVER_ERROR;
                break;
            case PUT:
                res = rand >= 1 ? HttpCode.OK : HttpCode.INTERNAL_SERVER_ERROR;
                break;
            case DELETE:
                res = rand >= 1 ? HttpCode.OK: HttpCode.INTERNAL_SERVER_ERROR;
                break;
            default:
                throw new IllegalArgumentException("Invalid HTTP VERB");
        }
        return res;
    }

    private static final int MIN_NEXT_SECS = 1;
    private static final int MAX_NEXT_SECS = 3;
    /**
     * get next request appear time (in seconds)
     * @return
     */
    public static int getNextRequestComesInSecs() {
        return r.nextInt(MAX_NEXT_SECS - MIN_NEXT_SECS + 1) + MIN_NEXT_SECS;
    }
}
