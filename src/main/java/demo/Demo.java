package demo;

import calculator.PercentileCalculator;

import static calculator.PercentileCalculator.PERCENTILES;
import static util.TestcaseGenerator.TESTCASE_DIR;

public class Demo {
    public static void main(String[] args) throws Exception {
        PercentileCalculator percentileCalculator = new PercentileCalculator();
        String[] logFilePaths = {
                "2019-4-2.log",
                "2019-4-3.log",
                "2019-4-4.log",
                "2019-4-5.log",
                "2019-4-6.log",
        };
        for (String logFilePath : logFilePaths) {
            System.out.println("[" + logFilePath + "]");
            printPercentiles(
                    percentileCalculator.getCurrentPercentiles(TESTCASE_DIR + logFilePath)
            );
        }
    }

    private static void printPercentiles(int[] percentiles) {
        for (int i = 0; i < percentiles.length; i++)
            System.out.print(PERCENTILES[i] + "% of requests return a response in " + percentiles[i] + " ms\n");
        System.out.println();
    }
}
