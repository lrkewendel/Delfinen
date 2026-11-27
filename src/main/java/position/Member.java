package position;

import DelfinenSwimTypes.Discipline;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public class Member {

    private int iD;
    private String name;
    private LocalDate birthDate;
    private String address;
    private String email;
    private int phone;
    private boolean isActive;
    private Discipline discipline;
    private int priceForMembership;
    private boolean paid = false;
    private ActivityType activityType;

    public Member(int iD, String name, LocalDate birthDate, String address, String email, int phone, boolean isActive, Discipline discipline, int priceForMembership, ActivityType activityType) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Navn må ikke være tomt.");
        }
        if (name.chars().anyMatch(Character::isISOControl)) {
            throw new IllegalArgumentException("Navn må ikke indeholde kontroltegn");
        }
        if (birthDate == null || birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Fødselsdato skal være i dag eller tidligere.");
        }
        if (iD <= 0) {
            throw new IllegalArgumentException("Medlemsnummer skal være større end 0.");
        }

        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("Adresse må ikke være tom.");
        }

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email må ikke være tom.");
        }

        if (phone <= 0) {
            throw new IllegalArgumentException("Telefonnummer skal være et positivt tal.");
        }

        if (discipline == null) {
            throw new IllegalArgumentException("Vælg en disciplin.");
        }

        if (priceForMembership < 0) {
            throw new IllegalArgumentException("Kontingent må ikke være negativt.");
        }

        if (activityType == null) {
            throw new IllegalArgumentException("Vælg en aktivitetstype.");
        }

        this.iD = iD;
        this.name = name.strip();
        this.birthDate = birthDate;
        this.address = address.strip();
        this.email = email.strip();
        this.phone = phone;
        this.isActive = isActive;
        this.discipline = discipline;
        this.priceForMembership = priceForMembership;
        this.activityType = activityType;
    }

    public int getiD() {
        return iD;
    }

    public String getName() {
        return name;
    }

    public LocalDate birthDate() {
        return birthDate;
    }

    public int getAge() {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public int getPhone() {
        return phone;
    }

    public boolean isActive() {
        return isActive;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public int getPriceForMembership() {
        priceForMembership = calculateFee(LocalDate.now());
        return priceForMembership;
    }

    public int calculateFee(LocalDate date) {
        if (!isActive) {
            return 500;
        }
        long age = ChronoUnit.YEARS.between(birthDate, date);

        if (age < 18) {
            return 1000;
        }
        if (age >= 60) {
            return 1200;
        }
        return 1600;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    // Denne konstruktør antages gamle kald uden aktivitetstype som motionister
    public Member(int iD, String name, LocalDate birthDate,
                  String address, String email, int phone,
                  boolean isActive, Discipline discipline,
                  int priceForMembership) {

        this(iD, name, birthDate, address, email, phone,
                isActive, discipline, priceForMembership,
                ActivityType.RECREATIONAL);
    }
    public boolean isPaid() {
        return paid;
    }
    public void registerPayment() {
        paid = true;
    }
}
