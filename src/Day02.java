import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class Day02 {

    public static void main(String[] args) {
        File course = new File("src\\Course.txt");

        Course courseList = new Course(course);
        Position position1 = courseList.getPositionTask1();
        System.out.println("Task 1: " + (position1.horizontalPosition * position1.depth));
        Position position2 = courseList.getPositionTask2();
        System.out.println("Task 2: " + (position2.horizontalPosition * position2.depth));
    }
}

class Command {
    String direction;
    int value;

    public Command(String direction, int value){
        this.direction = direction;
        this.value = value;
    }
}

class Position {
    int horizontalPosition;
    int depth;
    int aim;

    public Position(int horizontalPosition, int depth) {
        this.horizontalPosition = horizontalPosition;
        this.depth = depth;
        this.aim = 0;
    }

    public Position(int horizontalPosition, int depth, int aim) {
        this.horizontalPosition = horizontalPosition;
        this.depth = depth;
        this.aim = aim;
    }
}

class Course {
    LinkedList<Command> course;

    public Course(File courseInput) {
        course = new LinkedList<>();
        try {
            Scanner scanCourse = new Scanner(courseInput);
            while (scanCourse.hasNextLine()) {
                if (scanCourse.hasNext("up") || scanCourse.hasNext("down") || scanCourse.hasNext("forward")) {
                    String direction = scanCourse.next();
                    if (!scanCourse.hasNextInt()) {
                        System.out.println("Error, expected int value");
                        return;
                    }
                    int value = scanCourse.nextInt();

                    course.add(new Command(direction, value));
                } else {
                    System.out.println("Error, expected course command");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error, file not found");
        }
    }

    public Position getPositionTask1() {
        int currentHorizontalPosition = 0;
        int currentDepth = 0;
        for (Command command : course) {
            if (command.direction.equals("up")) {
                currentDepth -= command.value;
            } else if (command.direction.equals("down")) {
                currentDepth += command.value;
            } else { //command.direction == "forward"
                currentHorizontalPosition += command.value;
            }
        }
        return new Position(currentHorizontalPosition, currentDepth);
    }

    public Position getPositionTask2() {
        Position position = new Position(0,0,0);
        for (Command command: course) {
            if (command.direction.equals("up")) {
                position.aim -= command.value;
            } else if (command.direction.equals("down")) {
                position.aim += command.value;
            } else { //command.direction == "forward"
                position.horizontalPosition += command.value;
                position.depth += (position.aim * command.value);
            }
        }
        return position;
    }
}