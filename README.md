# Week 9 - S9 Assignment Problems

Abstraction & interface — the split this week is deliberate: shared state and
shared rules live on an `abstract` base class, while *optional capabilities*
are interfaces that only the relevant subclasses implement (`instanceof` asks
the capability question at processing time).

## Problems

1. `MovieTicketCounter` — seat price differs; ₹20 convenience fee exists once on
   `Ticket`; a plain untyped ticket can't be created
2. `ParcelShippingDesk` — every `Parcel` has `calculateCharge()`; only
   `ExpressParcel`/`FragileParcel` implement `Insurable`
3. `CollegeFeeCounter` — every `Student` has `tuition()`; only `BusUser`s pay
   transport (day scholars + scholars; hostellers don't)
4. `CityCabFareMeter` — ₹100 minimum shared on `Cab`; `NightService` interface —
   MINI at night is rejected, not charged
5. `HomeApplianceEnergyReport` — same `units()` formula for all; `SaverCapable`
   gates the −25% saver mode (AC + washer only)

## Run

```bash
javac *.java
printf "3\nREGULAR 3\nPREMIUM 2\nRECLINER 1\n" | java MovieTicketCounter
```

The pattern to explain if asked: `abstract` for "what every X has," `interface`
for "what only some X can do," `instanceof` for the capability check.
