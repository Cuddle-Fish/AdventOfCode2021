import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class Day03 {

    public static void main(String[] args) {
        File diagnosticsInput = new File("src\\Diagnostic_Report.txt");
        DiagnosticReport report = new DiagnosticReport(diagnosticsInput);
        System.out.println("Task 1: " + report.powerConsumption);
        System.out.println("Task 2: "+ report.lifeSupportRating);
    }
}

class DiagnosticReport {
   LinkedList<Integer> diagnosticReport;

    int binLength;
    int reportSize;

    int gammaRate;
    int epsilonRate;
    int powerConsumption;

    int oxygenRating;
    int CO2rating;
    int lifeSupportRating;

    public DiagnosticReport(File reportInput) {
        diagnosticReport = new LinkedList<>();
        reportSize = 0;
        try {
            Scanner scanReport = new Scanner(reportInput);

            String temp = scanReport.next();
            this.binLength = temp.length();
            diagnosticReport.add(Integer.parseInt(temp, 2));
            ++reportSize;
            while (scanReport.hasNextLine()) {
                diagnosticReport.add(scanReport.nextInt(2));
                ++reportSize;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error, file not found");
        }

        int firstBitsMostCommon = setGammaAndEpsilonRate();
        setPowerConsumption();
        setOxygenRatingAndCO2Rating(firstBitsMostCommon);
        setLifeSupportRating();
    }


    private int setGammaAndEpsilonRate() {
        int[] rate = new int[binLength];

        for (int number : diagnosticReport) {
            int currentRateIndex = binLength - 1;
            for (int i = 0; i < binLength; i++) {
                int mask = 1 << i;
                rate[currentRateIndex--] += (number & mask) > 0 ? 1 : 0;
            }
        }

        StringBuilder gammaRate = new StringBuilder();
        StringBuilder epsilonRate = new StringBuilder();

        for (int i = 0; i < binLength; i++) {
            if (rate[i] > (reportSize - rate[i])) {
                gammaRate.append(1);
                epsilonRate.append(0);
            } else {
                gammaRate.append(0);
                epsilonRate.append(1);
            }
        }

        this.gammaRate = Integer.parseInt(gammaRate.toString(), 2);
        this.epsilonRate = Integer.parseInt(epsilonRate.toString(), 2);

        return rate[0] > (reportSize - rate[0]) ? 1 << (binLength - 1) : 0;
    }


    private void setPowerConsumption() {
        this.powerConsumption = this.gammaRate * this.epsilonRate;
    }


    private void setOxygenRatingAndCO2Rating(int mostCommonBit) {
        this.oxygenRating = findOxygenAndCO2Rating(mostCommonBit, true);
        this.CO2rating = findOxygenAndCO2Rating(mostCommonBit ,false);
    }


    private int findOxygenAndCO2Rating(int mostCommonBit, boolean mostCommonDesired) {
        LinkedList<Integer> currentFilteredList = (LinkedList<Integer>) diagnosticReport.clone();

        for (int i = binLength - 1; i >= 0; i--) {
            LinkedList<Integer> buildNextMostFiltered = new LinkedList<>();
            int totalOfNextSetBits = 0;
            int currentMask = 1 << i;
            int nextMask = 1 << (i-1);

            for (int number : currentFilteredList) {
                int bitStateOfCurrentPos = number & currentMask;

                if(mostCommonDesired) {
                    if (bitStateOfCurrentPos == mostCommonBit) {
                        buildNextMostFiltered.add(number);
                        totalOfNextSetBits += (number & nextMask) > 0 ? 1 : 0;
                    }
                } else {
                    if (bitStateOfCurrentPos != mostCommonBit) {
                        buildNextMostFiltered.add(number);
                        totalOfNextSetBits += (number & nextMask) > 0 ? 1 : 0;
                    }
                }
            }

            mostCommonBit = ((totalOfNextSetBits) >=
                    (buildNextMostFiltered.size() - totalOfNextSetBits)) ? nextMask : 0;
            currentFilteredList = (LinkedList<Integer>) buildNextMostFiltered.clone();

            if (currentFilteredList.size() == 1) {
                break;
            }
        }

        return currentFilteredList.get(0);
    }


    private void setLifeSupportRating() {
        this.lifeSupportRating = this.oxygenRating * this.CO2rating;
    }
}
