import java.util.Scanner;

// Problem 4 - City Cab Fare Meter
// Min fare (100) is shared on the base; NightService is a capability interface.
public class CityCabFareMeter {

    // capability interface: only cabs that offer night rides implement this
    interface NightService {
    }

    static abstract class Cab {
        protected String type;
        protected double km;

        Cab(String type, double km) {
            this.type = type;
            this.km = km;
        }

        abstract double ratePerKm();

        // the Rs 100 minimum applies to EVERY cab - written once, here
        double baseFare() {
            return Math.max(km * ratePerKm(), 100);
        }
    }

    static class MiniCab extends Cab {
        MiniCab(double km) { super("MINI", km); }
        double ratePerKm() { return 10; }
        // no NightService - a mini never takes night trips
    }

    static class SedanCab extends Cab implements NightService {
        SedanCab(double km) { super("SEDAN", km); }
        double ratePerKm() { return 14; }
    }

    static class SuvCab extends Cab implements NightService {
        SuvCab(double km) { super("SUV", km); }
        double ratePerKm() { return 18; }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine().trim());

        double total = 0;
        for (int i = 0; i < n; i++) {
            String[] parts = sc.nextLine().trim().split(" ");
            double km = Double.parseDouble(parts[1]);
            boolean night = parts[2].equals("NIGHT");

            Cab cab;
            switch (parts[0]) {
                case "SEDAN": cab = new SedanCab(km); break;
                case "SUV":   cab = new SuvCab(km); break;
                default:      cab = new MiniCab(km); break;
            }

            if (night && !(cab instanceof NightService)) {
                System.out.println(cab.type + ": night service not available");
                continue; // rejected trips don't add to the total
            }

            double fare = cab.baseFare();
            if (night) {
                fare *= 1.20; // 20% night surcharge, after the minimum
            }
            System.out.printf("%s: %.2f%n", cab.type, fare);
            total += fare;
        }
        System.out.printf("Total: %.2f%n", total);
        sc.close();
    }
}
