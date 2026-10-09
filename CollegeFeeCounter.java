import java.util.Scanner;

// Problem 3 - College Fee Counter
// Every Student has a tuition rule; only some are BusUsers who pay transport.
public class CollegeFeeCounter {

    // capability interface - "rides the college bus" - implemented only by those who do
    interface BusUser {
        double busFee();
    }

    static abstract class Student {
        protected String name;

        Student(String name) {
            this.name = name;
        }

        abstract double tuition();

        double totalFee() {
            double fee = tuition();
            if (this instanceof BusUser) {
                fee += ((BusUser) this).busFee();
            }
            return fee;
        }
    }

    static class DayScholar extends Student implements BusUser {
        DayScholar(String name) { super(name); }
        double tuition() { return 40000; }
        public double busFee() { return 12000; }
    }

    static class Hosteller extends Student {
        Hosteller(String name) { super(name); }
        double tuition() { return 40000 + 60000; } // tuition + hostel fee, no bus
    }

    static class ScholarStudent extends Student implements BusUser {
        ScholarStudent(String name) { super(name); }
        double tuition() { return 20000; } // half of normal tuition
        public double busFee() { return 12000; }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine().trim());

        Student[] students = new Student[n];
        for (int i = 0; i < n; i++) {
            String[] parts = sc.nextLine().trim().split(" ");
            String name = parts[1];
            switch (parts[0]) {
                case "DAY_SCHOLAR": students[i] = new DayScholar(name); break;
                case "HOSTELLER":   students[i] = new Hosteller(name); break;
                case "SCHOLAR":     students[i] = new ScholarStudent(name); break;
            }
        }

        double total = 0;
        for (Student s : students) {
            double fee = s.totalFee();
            System.out.printf("%s: %.2f%n", s.name, fee);
            total += fee;
        }
        System.out.printf("Total Collected: %.2f%n", total);
        sc.close();
    }
}
