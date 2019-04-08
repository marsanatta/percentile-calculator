package demo;

import calculator.PercentileCalculator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static util.TestcaseGenerator.TESTCASE_DIR;

public class Demo {
    public static void main(String[] args) throws Exception {
        PercentileCalculator percentileCalculator = new PercentileCalculator();
        File testcaseDir = new File(TESTCASE_DIR);
        File[] logFiles = testcaseDir.listFiles();
        List<String> logFilePaths = new ArrayList<>();
        for (File logFile : logFiles) {
            if (logFile.isFile()) {
                logFilePaths.add(TESTCASE_DIR + logFile.getName());
            }
        }
        for (String logFilePath : logFilePaths) {
            System.out.println("Processing log file: " + logFilePath);
            percentileCalculator.processLogFile(logFilePath);
            System.out.println("Current percentiles:");
            percentileCalculator.printPercentiles();
        }
    }
}
