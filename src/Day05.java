import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Day05 {

    public static void main(String[] args) {
        File ventInput = new File("src\\Nearby_Hydrothermal_Vents.txt");
//        File ventInput = new File("src\\Test.txt");
        VentDiagram diagram = new VentDiagram(ventInput);
        System.out.println("Task 1: " + diagram.getTotalVentsAboveTwo());
    }

}


class VentDiagram {
    HashMap<Integer, Columns> row;

    public VentDiagram(File ventInput) {
        row = new HashMap<>();
        readVentList(ventInput);
    }

    public void readVentList(File ventInput) {
        try {
            Scanner readVents = new Scanner(ventInput);
            readVents.useDelimiter(",| -> |\r\n");

            while (readVents.hasNextLine()) {
                int[] currentVent = new int[4];

                for (int i = 0; i < 4; i++) {
                    currentVent[i] = readVents.nextInt();
                }

                if (currentVent[0] == currentVent[2] || currentVent[1] == currentVent[3]) {
                    addHorizontalRows(currentVent);
                }

            }
        } catch (FileNotFoundException e) {
            System.out.println("Error, file not found");
        }
    }

    private void addHorizontalRows(int[] currentVent) {
        // vertical vent
        if (currentVent[0] == currentVent[2]) {
            int startRow, endRow;
            if (currentVent[1] < currentVent[3]) {
                startRow = currentVent[1];
                endRow = currentVent[3];
            } else {
                startRow = currentVent[3];
                endRow = currentVent[1];
            }
            fillRows(currentVent[0], startRow, endRow);
        }
        // horizontal vent
        else if (currentVent[1] == currentVent[3]) {
            int startColumn, endColumn;
            if (currentVent[0] < currentVent[2]) {
                startColumn = currentVent[0];
                endColumn = currentVent[2];
            } else {
                startColumn = currentVent[2];
                endColumn = currentVent[0];
            }
            fillColumns(currentVent[1], startColumn, endColumn);
        }
    }


    private void fillRows(int column, int startRow, int endRow) {
        for (int i = startRow; i <= endRow; i++) {
            if (!row.containsKey(i)) {
                Columns newColumn = new Columns();
                row.put(i, newColumn);
            }
            row.get(i).addColumn(column);
        }
    }


    private void fillColumns(int row, int startColumn, int endColumn) {
        if (!this.row.containsKey(row)) {
            Columns newColumn = new Columns();
            this.row.put(row, newColumn);
        }
        for (int i = startColumn; i <= endColumn; i++) {
            this.row.get(row).addColumn(i);
        }
    }


    public int getTotalVentsAboveTwo() {
        int count = 0;
        for (int key : row.keySet()) {
            count += row.get(key).getNumVentsAboveTwo();
        }
        return count;
    }
}

class Columns {
    HashMap<Integer, Integer> column;

    public Columns() {
        this.column = new HashMap<>();
    }

    public void addColumn(int columnIndex) {
        if (!column.containsKey(columnIndex)) {
            column.put(columnIndex, 1);
        } else {
            column.replace(columnIndex, (column.get(columnIndex) + 1));
        }
    }

    public int getNumVentsAboveTwo() {
        int count = 0;
        for (int key : column.keySet()) {
            if (column.get(key) >= 2) {
                ++count;
            }
        }
        return count;
    }
}