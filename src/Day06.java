import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day06 {

    public static void main(String[] args) {
        File lanternfishInput = new File("src\\Lanternfish.txt");
        SpawnLanternfish fishSchool = new SpawnLanternfish(lanternfishInput);
        fishSchool.ageSchool(80);
        System.out.println("Part 1: " + "Lanternfish after 80 days = " + fishSchool.getTotalFish());
        fishSchool.ageSchool(176);
        System.out.println("Part 2: " + "Lanternfish after 256 days = " + fishSchool.getTotalFish());
    }
}


class SpawnLanternfish {
    long[] fish;

    public SpawnLanternfish(File lanternInput) {
        fish = new long[9];
        try {
            Scanner readLanterfish = new Scanner(lanternInput);
            readLanterfish.useDelimiter(",");
            while (readLanterfish.hasNextInt()) {
                ++fish[readLanterfish.nextInt()];
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error, File not found");
        }
    }


    public void ageSchool(int daysToAge) {
        for (int i = 0; i < daysToAge; i++) {
            long spawningFish = fish[0];
            for (int j = 0; j < 8; j++) {
                fish[j] = fish[j + 1];
            }
            fish[6] += spawningFish;
            fish[8] = spawningFish;
        }
    }


    public long getTotalFish() {
        long count = 0;
        for (int i = 0; i < 9; i++) {
            count += fish[i];
        }
        return count;
    }
}