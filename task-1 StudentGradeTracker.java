import java.util.ArrayList;
import java.util.Scanner;

class Student {
    String name;
    int rollNo;
    int[] marks;
    double average;
    int highest;
    int lowest;

    Student(String name, int rollNo, int[] marks) {
        this.name = name;
        this.rollNo = rollNo;
        this.marks = marks;
        calculateResults();
    }

    void calculateResults() {
        int total = 0;
        highest = marks[0];
        lowest = marks[0];

        for (int mark : marks) {
            total += mark;
            if (mark > highest) highest = mark;
            if (mark < lowest) lowest = mark;
        }

        average = total / (double) marks.length;
    }
}

public class StudentGradeTracker {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();

        System.out.println("=== Student Grade Tracker ===");

        System.out.print("Enter number of students: ");
        int studentCount = sc.nextInt();

        for (int i = 1; i <= studentCount; i++) {
            sc.nextLine(); // clear buffer
            System.out.println("\nEnter details for Student " + i);

            System.out.print("Name: ");
            String name = sc.nextLine();

            System.out.print("Roll Number: ");
            int rollNo = sc.nextInt();

            System.out.print("Number of subjects: ");
            int subjects = sc.nextInt();

            int[] marks = new int[subjects];
            for (int j = 0; j < subjects; j++) {
                System.out.print("Enter marks for subject " + (j + 1) + ": ");
                marks[j] = sc.nextInt();
            }

            students.add(new Student(name, rollNo, marks));
        }

        // Summary Report
        System.out.println("\n===== SUMMARY REPORT =====");
        for (Student s : students) {
            System.out.println("\nName       : " + s.name);
            System.out.println("Roll No    : " + s.rollNo);
            System.out.println("Average    : " + s.average);
            System.out.println("Highest    : " + s.highest);
            System.out.println("Lowest     : " + s.lowest);
        }

        sc.close();
    }
}
