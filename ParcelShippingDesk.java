import java.util.Scanner;

// Problem 2 - Parcel Shipping Desk
// Every Parcel can calculateCharge(); only some parcels are Insurable.
public class ParcelShippingDesk {

    // the "can it be insured?" capability - a separate question from shipping cost
    interface Insurable {
        double insurance();
    }

    static abstract class Parcel {
        protected String type;
        protected double weightKg;
        protected double declaredValue;

        Parcel(String type, double weightKg, double declaredValue) {
            this.type = type;
            this.weightKg = weightKg;
            this.declaredValue = declaredValue;
        }

        abstract double calculateCharge();
    }

    static class StandardParcel extends Parcel {
        StandardParcel(double weight, double value) { super("STANDARD", weight, value); }
        double calculateCharge() { return 40 + 10 * weightKg; }
        // note: not Insurable - standard parcels can't be insured
    }

    static class ExpressParcel extends Parcel implements Insurable {
        ExpressParcel(double weight, double value) { super("EXPRESS", weight, value); }
        double calculateCharge() { return 80 + 15 * weightKg; }
        public double insurance() { return 0.02 * declaredValue; }
    }

    static class FragileParcel extends Parcel implements Insurable {
        FragileParcel(double weight, double value) { super("FRAGILE", weight, value); }
        double calculateCharge() {
            // standard charge plus the handling fee
            return (40 + 10 * weightKg) + 50;
        }
        public double insurance() { return 0.02 * declaredValue; }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine().trim());

        Parcel[] parcels = new Parcel[n];
        for (int i = 0; i < n; i++) {
            String[] parts = sc.nextLine().trim().split(" ");
            double weight = Double.parseDouble(parts[1]);
            double value = Double.parseDouble(parts[2]);
            switch (parts[0]) {
                case "STANDARD": parcels[i] = new StandardParcel(weight, value); break;
                case "EXPRESS":  parcels[i] = new ExpressParcel(weight, value); break;
                case "FRAGILE":  parcels[i] = new FragileParcel(weight, value); break;
            }
        }

        double grandTotal = 0;
        for (Parcel p : parcels) {
            double charge = p.calculateCharge();
            // instanceof asks the capability question - not the concrete type
            double insurance = (p instanceof Insurable) ? ((Insurable) p).insurance() : 0;
            double total = charge + insurance;
            System.out.printf("%s: Charge=%.2f Insurance=%.2f Total=%.2f%n",
                    p.type, charge, insurance, total);
            grandTotal += total;
        }
        System.out.printf("Grand Total: %.2f%n", grandTotal);
        sc.close();
    }
}
