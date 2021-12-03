import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class Day01 {

    public static LinkedList<Integer> readInput(File report) {
        LinkedList<Integer> data = new LinkedList<>();
        try{
            Scanner depth = new Scanner(report);
            while (depth.hasNextInt()) {
                data.add(depth.nextInt());
            }
            depth.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error, file not found");
        }
        return data;
    }

    public static int consecutiveIncrease(LinkedList<Integer> report) {
        int count = 0;
        int previous = report.getFirst();
        for (int i = 1; i < report.size(); i++) {
            if (report.get(i) > previous) {
                ++count;
            }
            previous = report.get(i);
        }
        return count;
    }

    public static int increaseEveryThree(LinkedList<Integer> report) {
        int count = 0;
        int previous = 0;
        for (int i = 0; i < 3; i++) {
            previous += report.get(i);
        }
        for (int i = 3; i < report.size(); i++) {
            int current = report.get(i - 2) + report.get(i - 1) + report.get(i);
            if (current > previous) {
                ++count;
            }
            previous = current;
        }
        return count;
    }

    public static void main(String[] args) {
        File sweepReport = new File("src\\Sonar_Sweep_Report.txt");
        LinkedList<Integer> reportList = readInput(sweepReport);
        System.out.println("Task 1: " + consecutiveIncrease(reportList));
        System.out.println("Task 2: " + increaseEveryThree(reportList));
    }
}
