import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Day07 {

    public static void main(String[] args) {
        File crabInput = new File("src\\Crab_Position.txt");
        Crabs crabs = new Crabs(crabInput);
        System.out.println("Part 1: " + crabs.getLeastFuelForAlignment());
        System.out.println("Part 2: " + crabs.getLeastFuelWithIncreasingCost());
    }
}


class Crabs {
    HashMap<Integer, Integer> crabs;
    int minPos;
    int maxPos;

    public Crabs(File crabInput) {
        crabs = new HashMap<>();
        minPos = 0;
        maxPos = 0;

        try {
            Scanner readCrabs = new Scanner(crabInput);
            readCrabs.useDelimiter(",");
            while (readCrabs.hasNextInt()) {
                int currentCrabPos = readCrabs.nextInt();
                if (!crabs.containsKey(currentCrabPos)) {
                    crabs.put(currentCrabPos, 1);
                } else {
                    crabs.replace(currentCrabPos, (crabs.get(currentCrabPos) + 1));
                }

                if (currentCrabPos < minPos) {
                    minPos = currentCrabPos;
                }
                if (currentCrabPos > maxPos) {
                    maxPos = currentCrabPos;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error, File not found");
        }
    }


    public int getLeastFuelForAlignment() {
        // get initial fuel cost for moving to minPos
        int leastFuelUsed = 0;
        for (int key : crabs.keySet()) {
            int distance;
            if (key < minPos) {
                distance = minPos - key;
            } else {
                distance = key - minPos;
            }
            leastFuelUsed += distance * crabs.get(key);
        }

        // find minimum fuel cost
        for (int i = minPos + 1; i <= maxPos; i++) {
            int currentFuelUsed = 0;
            for (int key : crabs.keySet()) {
                int distance;
                if (key < i) {
                    distance = i - key;
                } else {
                    distance = key - i;
                }
                currentFuelUsed += distance * crabs.get(key);
            }

            if (currentFuelUsed < leastFuelUsed) {
                leastFuelUsed = currentFuelUsed;
            }

        }
        return leastFuelUsed;
    }

    public int getLeastFuelWithIncreasingCost() {
        // get initial fuel cost for moving to minPos
        int leastFuelUsed = 0;
        for (int key : crabs.keySet()) {
            int distance;
            if (key < minPos) {
                distance = minPos - key;
            } else {
                distance = key - minPos;
            }
            int fuelCost = IntStream.rangeClosed(1, distance).sum();
            leastFuelUsed += fuelCost * crabs.get(key);
        }

        // find minimum fuel cost
        for (int i = minPos + 1; i <= maxPos; i++) {
            int currentFuelUsed = 0;
            for (int key : crabs.keySet()) {
                int distance;
                if (key < i) {
                    distance = i - key;
                } else {
                    distance = key - i;
                }
                int fuelCost = IntStream.rangeClosed(1, distance).sum();
                currentFuelUsed += fuelCost * crabs.get(key);
            }

            if (currentFuelUsed < leastFuelUsed) {
                leastFuelUsed = currentFuelUsed;
            }

        }
        return leastFuelUsed;
    }
}
