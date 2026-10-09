import java.util.Scanner;

// Problem 5 - Home Appliance Energy Report
// Every appliance computes units the same way; only some support saver mode.
public class HomeApplianceEnergyReport {

    // capability interface: appliances with a saver mode implement this
    interface SaverCapable {
    }

    static abstract class Appliance {
        protected String type;
        protected double hours;

        Appliance(String type, double hours) {
            this.type = type;
            this.hours = hours;
        }

        abstract int powerWatts(); // each appliance's fixed rating

        double units() {
            return powerWatts() * hours / 1000.0;
        }
    }

    static class Fridge extends Appliance {
        Fridge(double hours) { super("FRIDGE", hours); }
        int powerWatts() { return 150; }
    }

    static class AirConditioner extends Appliance implements SaverCapable {
        AirConditioner(double hours) { super("AC", hours); }
        int powerWatts() { return 1500; }
    }

    static class Tv extends Appliance {
        Tv(double hours) { super("TV", hours); }
        int powerWatts() { return 100; }
    }

    static class Washer extends Appliance implements SaverCapable {
        Washer(double hours) { super("WASHER", hours); }
        int powerWatts() { return 500; }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine().trim());

        double totalCost = 0;
        for (int i = 0; i < n; i++) {
            String[] parts = sc.nextLine().trim().split(" ");
            double hours = Double.parseDouble(parts[1]);
            boolean saver = parts.length == 3 && parts[2].equals("SAVER");

            Appliance appliance;
            switch (parts[0]) {
                case "AC":     appliance = new AirConditioner(hours); break;
                case "TV":     appliance = new Tv(hours); break;
                case "WASHER": appliance = new Washer(hours); break;
                default:       appliance = new Fridge(hours); break;
            }

            if (saver && !(appliance instanceof SaverCapable)) {
                System.out.println(appliance.type + ": saver mode not supported");
                continue; // report it instead of calculating a cost
            }

            double units = appliance.units();
            if (saver) {
                units *= 0.75; // saver mode cuts energy use by 25%
            }
            double cost = units * 8;
            System.out.printf("%s: Units=%.2f Cost=%.2f%n", appliance.type, units, cost);
            totalCost += cost;
        }
        System.out.printf("Total Cost: %.2f%n", totalCost);
        sc.close();
    }
}
