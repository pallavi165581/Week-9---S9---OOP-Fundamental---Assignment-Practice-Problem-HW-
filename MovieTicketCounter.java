import java.util.Scanner;

// Problem 1 - Movie Ticket Counter
// Abstract Ticket holds the shared fee; a generic "ticket" can't be created.
public class MovieTicketCounter {

    static abstract class Ticket {
        // the convenience fee exists exactly once - here
        private static final double CONVENIENCE_FEE = 20.0;

        protected String seatType;
        protected int count;

        Ticket(String seatType, int count) {
            this.seatType = seatType;
            this.count = count;
        }

        abstract double pricePerTicket(); // each seat type sets its own price

        // shared for every ticket: (seat price + fee) * count
        double total() {
            return (pricePerTicket() + CONVENIENCE_FEE) * count;
        }
    }

    static class RegularTicket extends Ticket {
        RegularTicket(int count) { super("REGULAR", count); }
        double pricePerTicket() { return 150; }
    }

    static class PremiumTicket extends Ticket {
        PremiumTicket(int count) { super("PREMIUM", count); }
        double pricePerTicket() { return 250; }
    }

    static class ReclinerTicket extends Ticket {
        ReclinerTicket(int count) { super("RECLINER", count); }
        double pricePerTicket() { return 400; }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine().trim());

        Ticket[] bookings = new Ticket[n];
        for (int i = 0; i < n; i++) {
            String[] parts = sc.nextLine().trim().split(" ");
            int count = Integer.parseInt(parts[1]);
            switch (parts[0]) {
                case "REGULAR":  bookings[i] = new RegularTicket(count); break;
                case "PREMIUM":  bookings[i] = new PremiumTicket(count); break;
                case "RECLINER": bookings[i] = new ReclinerTicket(count); break;
            }
        }

        double grandTotal = 0;
        for (Ticket t : bookings) {
            double amount = t.total();
            System.out.printf("%s: %.2f%n", t.seatType, amount);
            grandTotal += amount;
        }
        System.out.printf("Total: %.2f%n", grandTotal);
        sc.close();
    }
}
